package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A {@code Node} used to represent echo (=)
 * function in SmartScriptLanguage.
 *
 * @author Mateo Imbrisak.
 */

public class EchoNode extends Node {

    /**
     * Keeps all {@code Elements} used in
     * the function.
     */
    private Element[] elements;

    /**
     * Default constructor that receives all
     * {@code Element}s used in the function.
     *
     * @param elements used in the function.
     */
    public EchoNode(Element[] elements) {
        this.elements = elements;
    }

    /**
     * Returns all {@code Elements} used in the
     * function as an array.
     *
     * @return an array of {@code Element}s
     */
    public Element[] getElements() {
        return elements;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitEchoNode(this);
    }

    /**
     * Creates a textual representation of
     * this {@code Node} that can be parsed
     * again by a {@code SmartScriptParser}.
     *
     * @return a {@code String} representation
     * of this {@code Node} that can be parsed
     * again.
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();

        ret.append("{$= ");

        for (Element element : elements) {
            ret.append(element).append(" ");
        }

        ret.append("$}");

        return ret.toString();
    }
}
