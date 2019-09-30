package hr.fer.zemris.java.custom.scripting.elems;

/**
 * An {@code Element} that represents a
 * String in SmartScript source code.
 *
 * @author Mateo Imbrišak
 */

public class ElementString extends Element {

    /**
     * Keeps the value of the {@code Element}.
     */
    private String value;

    /**
     * Default constructor that initializes
     * the value of this {@code Element}.
     *
     * @param value you want to set.
     */
    public ElementString(String value) {
        this.value = value;
    }

    /**
     * Returns a textual representation of the value.
     *
     * @return a {@code String} representing the value.
     */
    @Override
    public String asText() {
        return getValue();
    }

    /**
     * Turns this {@code Element} into a {@code String}
     * that can be parsed int the same {@code Element}
     * by the {@code SmartScriptParser}.
     *
     * @return a {@code String} that can be parsed again.
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();

        String[] partsSlash = asText().split("\\\\");

        for (String part : partsSlash) {
            ret.append(part).append("\\\\");
        }

        ret.deleteCharAt(ret.length() - 1);
        ret.deleteCharAt(ret.length() - 1);

        String next = ret.toString();
        ret = new StringBuilder();

        String[] parts = next.split("\"");

        for (String part : parts) {
            ret.append(part).append("\\\"");
        }

        ret.deleteCharAt(ret.length() - 1);
        ret.deleteCharAt(ret.length() - 1);

        return "\"" + ret.toString() + "\"";
    }

    /**
     * Returns the actual value of the {@code Element}.
     *
     * @return value of the {@code Element}.
     */
    public String getValue() {
        return value;
    }
}
