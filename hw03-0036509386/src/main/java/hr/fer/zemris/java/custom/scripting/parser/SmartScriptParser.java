package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * A simple parser used to parse SmartScript language.
 *
 * @author Mateo Imbrišak
 */

public class SmartScriptParser {

    /**
     * A {@code SmartScriptLexer} that passes
     * {@code SmartScriptToken}s to this {SmartScriptParser}.
     */
    private SmartScriptLexer lexer;

    /**
     * A stack that keeps track of {@code Node}s processed
     * by the {@code SmartScriptParser}.
     */
    private ObjectStack stack;

    /**
     * Default constructor that initializes the internal
     * {@code SmartScriptLexer} with the given {@code String}.
     *
     * @param input {@code String} used to initialize the
     *                            {@code SmartScriptLexer}.
     */
    public SmartScriptParser(String input) {
        lexer = new SmartScriptLexer(input);
        stack = new ObjectStack();

        DocumentNode document = new DocumentNode();

        stack.push(document);

        parseNext();
    }

    /**
     * Used to process next {@code SmartScriptToken} passed by
     * the {@code SmartScriptLexer}.
     * Calls itself recursively until it receives an {@code EOF}
     * type token.
     */
    private void parseNext() {
        SmartScriptToken token;

        try {
            token = lexer.nextToken();
        } catch (SmartScriptLexerException exc) {
            throw new SmartScriptParserException(exc.getMessage());
        }

        if (token.getType() == SmartScriptTokenType.EOF) {
            if (stack.size() != 1) {
                Object node = stack.peek();

                if (node.getClass() != DocumentNode.class) {
                    throw new SmartScriptParserException("No document node found.");
                }
            }
            return;
        }

        if (token.getType() == SmartScriptTokenType.TAG) {
            String current = token.getValue().toString().toLowerCase();

            switch (current) {
                case "for":
                    createFor();
                    break;
                case "=":
                    createEcho();
                    break;
                case "end":
                    try {
                        if (stack.size() > 1) {
                            stack.pop();
                            SmartScriptToken endToken = lexer.nextToken();


                            if (endToken.getType() != SmartScriptTokenType.EOT) {
                                throw new SmartScriptParserException("{$ END $} tag wasn't properly closed.");
                            }
                        }
                    } catch (EmptyStackException exc) {
                        throw new SmartScriptParserException("Found more {$ END $} tags then non-empty tags.");
                    } catch (SmartScriptLexerException exc) {
                        throw new SmartScriptParserException(exc.getMessage());
                    }
                    break;
                default:
                    throw new SmartScriptParserException("Unsupported tag found.");
            }

            parseNext();
        } else if (token.getType() == SmartScriptTokenType.TEXT) {
            TextNode node = new TextNode(token.getValue().toString());
            addToFirst(node);
            parseNext();
        } else {
            throw new SmartScriptParserException("Unsupported token revived.");
        }
    }

    /**
     * Creates an {@code EchoNode} if the current tag is "="
     * and adds it as a child of the first element in the stack.
     */
    private void createEcho() {
        Collection elements = elementsInTag();
        Element[] elementArray = new Element[elements.size()];

        for (int i = 0; i < elements.size(); i++) {
            elementArray[i] = (Element) ((ArrayIndexedCollection) elements).get(i);
        }

        EchoNode node = new EchoNode(elementArray);
        addToFirst(node);
    }

    /**
     * Creates a {@code ForLoopNode} if valid number of arguments is found
     * and pushes it to the stack.
     *
     * @throws SmartScriptParserException if number of arguments found is
     * not 3 or 4.
     */
    private void createFor() {
        Collection elements = elementsInTag();

        if (elements.size() > 4 || elements.size() < 3) {
            throw new SmartScriptParserException("Wrong number of arguments in {$ FOR $} tag.");
        } else {
            if (((ArrayIndexedCollection) elements).get(0).getClass() == ElementVariable.class) {
                for (int i = 1; i < elements.size(); i++) {
                    Element current = (Element) ((ArrayIndexedCollection) elements).get(i);

                    // checks if all elements are of valid type
                    if (current.getClass() != ElementVariable.class && current.getClass() != ElementString.class &&
                    current.getClass() != ElementConstantDouble.class &&
                            current.getClass() != ElementConstantInteger.class) {
                        throw new SmartScriptParserException("Invalid element type found.");
                    }
                }

                ForLoopNode node;

                if (elements.size() == 4) {
                    node = new ForLoopNode((ElementVariable) ((ArrayIndexedCollection) elements).get(0),
                            (Element) ((ArrayIndexedCollection) elements).get(1),
                            (Element) ((ArrayIndexedCollection) elements).get(2),
                            (Element) ((ArrayIndexedCollection) elements).get(3));

                    addToFirst(node);
                    stack.push(node);
                    return;
                }
                if (elements.size() == 3) {
                    node = new ForLoopNode((ElementVariable) ((ArrayIndexedCollection) elements).get(0),
                            (Element) ((ArrayIndexedCollection) elements).get(1),
                            (Element) ((ArrayIndexedCollection) elements).get(2),
                            null);

                    addToFirst(node);
                    stack.push(node);
                }
            }
        }
    }

    /**
     * Adds a given {@code Node} to the
     * first node in the stack.
     *
     * @param node to be added to the stack.
     *
     * @throws SmartScriptParserException if the
     * {@code ObjectStack} is empty.
     */
    private void addToFirst(Node node) {
        try {
            Node firstInStack = (Node) stack.peek();
            firstInStack.addChildNode(node);
        } catch (EmptyStackException exc) {
            throw new SmartScriptParserException("The stack is empty.");
        }
    }

    /**
     * Creates a {@code Collection} and fills it with
     * {@code Element}s found in the current tag.
     *
     * @return a {@code Collection} of {@code Element}s.
     */
    private Collection elementsInTag() {
        Collection elements = new ArrayIndexedCollection();
        SmartScriptToken token;

        try {
            do {
                token = lexer.nextToken();

                switch (token.getType()) {
                    case EOT:
                        break;
                    case VARIABLE:
                        ElementVariable variable = new ElementVariable(token.getValue().toString());
                        elements.add(variable);
                        break;
                    case INTEGER:
                        Element elementConstantInteger = new ElementConstantInteger((Integer) token.getValue());
                        elements.add(elementConstantInteger);
                        break;
                    case DOUBLE:
                        Element elementConstantDouble = new ElementConstantDouble((Double) token.getValue());
                        elements.add(elementConstantDouble);
                        break;
                    case FUNCTION:
                        Element elementFunction = new ElementFunction(token.getValue().toString());
                        elements.add(elementFunction);
                        break;
                    case OPERATOR:
                        Element element = new ElementOperator(token.getValue().toString());
                        elements.add(element);
                        break;
                    case STRING:
                        Element string = new ElementString(token.getValue().toString());
                        elements.add(string);
                        break;
                    default:
                        throw new SmartScriptParserException("Illegal token in a tag.");
                }

            } while (token.getType() != SmartScriptTokenType.EOT);
        } catch (SmartScriptLexerException exc) {
            throw new SmartScriptParserException(exc.getMessage());
        }
        return elements;
    }

    /**
     * Returns a {@code DocumentNode} that represents
     * the SmartScript source code hierarchy.
     *
     * @return a {@code DocumentNode} that represents
     * the model of a file parsed by this {@code SmartScriptParser}.
     */
    public DocumentNode getDocumentNode() {
        try {
            Object document = stack.pop();
            if (document.getClass() == DocumentNode.class && stack.size() == 0) {
                return (DocumentNode) document;
            } else {
                throw new SmartScriptParserException("Invalid type found.");
            }
        } catch (EmptyStackException exc) {
            throw new SmartScriptParserException("No document found, the stack is empty.");
        }
    }
}
