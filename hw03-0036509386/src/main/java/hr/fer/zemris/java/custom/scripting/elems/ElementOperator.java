package hr.fer.zemris.java.custom.scripting.elems;

/**
 * An {@code Element} that represents an
 * operator.
 *
 * @author Mateo Imbrišak
 */

public class ElementOperator extends Element {

    /**
     * Keeps the symbol of the {@code Element}.
     */
    private String symbol;

    /**
     * Default constructor that initializes the symbol.
     *
     * @param symbol to be used by this {@code Element}.
     */
    public ElementOperator(String symbol) {
        this.symbol = symbol;
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
        return getSymbol();
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
    public String getSymbol() {
        return symbol;
    }
}
