package hr.fer.zemris.java.custom.scripting.elems;

/**
 * An {@code Element} that represents a variable
 * in SmartScript language.
 *
 * @author Mateo Imbrišak
 */

public class ElementVariable extends Element {

    /**
     * Represents the name of this {@code ElementVariable}.
     */
    private String name;

    /**
     * Default constructor that sets a name for this {@code ElementVariable}.
     *
     * @param name to be given to this {@code ElementVariable}.
     */
    public ElementVariable(String name) {
        this.name = name;
    }

    /**
     * Return the name of this {@code ElementVariable}.
     *
     * @return name of this {@code ElementVariable}.
     */
    @Override
    public String asText() {
        return getName();
    }

    /**
     * Creates a {@code String} representation of
     * this {@code ElementVariable}.
     *
     * @return this {@code ElementVariable} as
     * a {@code String}.
     */
    @Override
    public String toString() {
        return asText();
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
