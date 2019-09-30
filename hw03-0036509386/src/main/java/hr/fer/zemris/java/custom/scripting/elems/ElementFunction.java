package hr.fer.zemris.java.custom.scripting.elems;

/**
 * An {@code Element} used to represent a
 * function.
 *
 * @author Mateo Imbrišak
 */

public class ElementFunction extends Element {

    /**
     * Keeps the name of the function.
     */
    private String name;

    /**
     * Default constructor that initializes the name.
     *
     * @param name to be used by this {@code Element}.
     */
    public ElementFunction(String name) {
        this.name = name;
    }

    /**
     * Returns textual representation of the value
     * of this {@code Element}.
     *
     * @return a {@code String} that represents the
     * value of this {@code Element}.
     */
    @Override
    public String asText() {
        return getName();
    }

    /**
     * Turns this {@code Element} into a
     * {@code String} that can be parsed
     * again by a {@code SmartScriptParser}.
     *
     * @return a {@code String} that can be
     * parsed again.
     */
    @Override
    public String toString() {
        return "@" + asText();
    }

    /**
     * Returns the actual value of the {@code Element}.
     *
     * @return value of the {@code Element}.
     */
    public String getName() {
        return name;
    }
}
