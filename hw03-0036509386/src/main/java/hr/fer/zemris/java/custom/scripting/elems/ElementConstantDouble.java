package hr.fer.zemris.java.custom.scripting.elems;

/**
 * An {@code Element} that represents a
 * constant double.
 *
 * @author Mateo Imbrišak
 */

public class ElementConstantDouble extends Element {

    /**
     * Keeps the value of this {@code Element}.
     */
    private double value;

    /**
     * Default constructor that initializes the value.
     *
     * @param value to be used by this {@code Element}.
     */
    public ElementConstantDouble(double value) {
        this.value = value;
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
        return Double.toString(getValue());
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
        return asText();
    }

    /**
     * Returns the actual value of the {@code Element}.
     *
     * @return value of the {@code Element}.
     */
    public double getValue() {
        return value;
    }
}
