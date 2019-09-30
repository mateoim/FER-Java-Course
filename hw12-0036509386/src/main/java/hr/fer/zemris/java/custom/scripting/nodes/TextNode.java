package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A {@code Node} that represents plain text.
 *
 * @author Mateo Imbrišak
 */

public class TextNode extends Node {

    /**
     * Keeps the value of the {@code Node}.
     */
    private String text;

    /**
     * Default constructor that initializes
     * the value of this {@code TextNode}.
     *
     * @param text value you want this {@code Node}
     *             to represent.
     */
    public TextNode(String text) {
        this.text = text;
    }

    /**
     * Returns the value this {@code Node} represents.
     *
     * @return value of this {@code Node}.
     */
    public String getText() {
        return text;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitTextNode(this);
    }

    /**
     * Turns this {@code Node} into a {@code String}
     * that can be parsed correctly by a
     * {@code SmartScriptParser} again.
     *
     * @return a parsable {@code String}.
     */
    @Override
    public String toString() {
        String[] splitSlash = getText().split("\\\\");
        StringBuilder ret = new StringBuilder();

        for (String part : splitSlash) {
            ret.append(part).append("\\\\");
        }

        ret.deleteCharAt(ret.length() - 1);
        ret.deleteCharAt(ret.length() - 1);

        String[] escaped = ret.toString().split("\\{");
        ret = new StringBuilder();

        for (String part : escaped) {
            if (part.charAt(0) == '$') {
                ret.append("\\{").append(part);
            } else {
                ret.append("{").append(part);
            }
        }

        ret.deleteCharAt(0);

        return ret.toString();
    }
}
