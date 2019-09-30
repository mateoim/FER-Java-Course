package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An engine used to execute SmartScript code.
 *
 * @author Mateo Imbrišak
 */

public class SmartScriptEngine {

    /**
     * Keeps the document to be executed.
     */
    private DocumentNode documentNode;

    /**
     * Keeps the request context.
     */
    private RequestContext requestContext;

    /**
     * Used as a stack for executing.
     */
    private ObjectMultistack multistack = new ObjectMultistack();

    /**
     * Visitor used to execute each node.
     */
    private INodeVisitor visitor = new INodeVisitor() {
        @Override
        public void visitTextNode(TextNode node) {
            try {
                requestContext.write(node.toString());
            } catch (IOException exc) {
                System.out.println("An error occurred while writing.");
            }
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            multistack.push(node.getVariable().getName(), new ValueWrapper(node.getStartExpression().asText()));

            while (multistack.peek(node.getVariable().getName()).numCompare(node.getEndExpression().asText()) <= 0) {
                for (int i = 0; i < node.numberOfChildren(); i++) {
                    node.getChild(i).accept(visitor);
                }

                multistack.peek(node.getVariable().getName()).add(node.getStepExpression().asText());
            }

            multistack.pop(node.getVariable().getName());
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            Stack<ValueWrapper> tempStack = new Stack<>();

            for (Element element : node.getElements()) {
                if (element instanceof ElementConstantDouble || element instanceof ElementConstantInteger
                || element instanceof ElementString) {
                    tempStack.push(new ValueWrapper(element.asText()));
                } else if (element instanceof ElementVariable) {
                    tempStack.push(new ValueWrapper(multistack.peek(((ElementVariable) element).getName()).getValue()));
                } else if (element instanceof ElementOperator) {
                    if (tempStack.size() < 2) {
                        throw new RuntimeException("Not enough elements on the stack.");
                    }

                    ValueWrapper first = tempStack.pop();
                    ValueWrapper second = tempStack.pop();

                    switch (((ElementOperator) element).getSymbol()) {
                        case "+":
                            first.add(second.getValue());
                            break;
                        case "-":
                            first.subtract(second.getValue());
                            break;
                        case "*":
                            first.multiply(second.getValue());
                            break;
                        case "/":
                            first.divide(second.getValue());
                            break;
                        default:
                            throw new RuntimeException("Invalid operator found.");
                    }

                    tempStack.push(first);
                } else if (element instanceof ElementFunction) {
                    switch (((ElementFunction) element).getName()) {
                        case "sin":
                            ValueWrapper x = tempStack.pop();
                            String value = x.getValue().toString();

                            tempStack.push(new ValueWrapper(Math.sin(Math.toRadians(Double.parseDouble(value)))));
                            break;
                        case "decfmt":
                            ValueWrapper f = tempStack.pop();
                            ValueWrapper toFormat = tempStack.pop();

                            DecimalFormat format = new DecimalFormat(f.getValue().toString());

                            tempStack.push(new ValueWrapper(format.format(Double.parseDouble(
                                    toFormat.getValue().toString()))));
                            break;
                        case "dup":
                            ValueWrapper toDuplicate = tempStack.pop();
                            ValueWrapper duplicate = new ValueWrapper(toDuplicate.getValue());

                            tempStack.push(duplicate);
                            tempStack.push(toDuplicate);
                            break;
                        case "swap":
                            ValueWrapper a = tempStack.pop();
                            ValueWrapper b = tempStack.pop();

                            tempStack.push(a);
                            tempStack.push(b);
                            break;
                        case "setMimeType":
                            requestContext.setMimeType(tempStack.pop().getValue().toString());
                            break;
                        case "paramGet":
                            paramGetter(tempStack, requestContext::getParameter);
                            break;
                        case "pparamGet":
                            paramGetter(tempStack, requestContext::getPersistentParameter);
                            break;
                        case "pparamSet":
                            paramSetter(tempStack, requestContext::setPersistentParameter);
                            break;
                        case "pparamDel":
                            paramRemover(tempStack, requestContext::removePersistentParameter);
                            break;
                        case "tparamGet":
                            paramGetter(tempStack, requestContext::getTemporaryParameter);
                            break;
                        case "tparamSet":
                            paramSetter(tempStack, requestContext::setTemporaryParameter);
                            break;
                        case "tparamDel":
                            paramRemover(tempStack, requestContext::removeTemporaryParameter);
                            break;
                        default:
                            throw new RuntimeException("Unknown function found.");
                    }
                }
            }

            tempStack.forEach((element) -> {
                try {
                    requestContext.write(element.getValue().toString());
                } catch (IOException exc) {
                    System.out.println("Error while writing.");
                }
            });
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            documentNode.accept(visitor);
        }

        /**
         * Used for all getter functions.
         *
         * @param tempStack stack being used.
         * @param getter used to get the necessary map.
         */
        private void paramGetter(Stack<ValueWrapper> tempStack, Function<String, String> getter) {
            ValueWrapper dv = tempStack.pop();
            ValueWrapper name = tempStack.pop();

            String value = getter.apply(name.getValue().toString());

            tempStack.push(value == null ? dv : new ValueWrapper(value));
        }

        /**
         * Used for all setter functions.
         *
         * @param tempStack stack being used.
         * @param setter used to set the value.
         */
        private void paramSetter(Stack<ValueWrapper> tempStack, BiConsumer<String, String> setter) {
            ValueWrapper name = tempStack.pop();
            ValueWrapper value = tempStack.pop();

            setter.accept(name.getValue().toString(), value.getValue().toString());
        }

        /**
         * Used for all del functions.
         *
         * @param tempStack stack being used.
         * @param remover used to remove the value.
         */
        private void paramRemover(Stack<ValueWrapper> tempStack, Consumer<String> remover) {
            remover.accept(tempStack.pop().getValue().toString());
        }
    };

    /**
     * Default constructor that assigns {@code documentNode}
     * and {@code requestContext}.
     *
     * @param documentNode to be assigned.
     * @param requestContext to be assigned.
     */
    public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
        Objects.requireNonNull(documentNode);
        Objects.requireNonNull(requestContext);

        this.documentNode = documentNode;
        this.requestContext = requestContext;
    }

    /**
     * Used to run the script.
     */
    public void execute() {
        documentNode.accept(visitor);
    }
}
