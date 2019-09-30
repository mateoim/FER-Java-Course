package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A base class used by all other {@code Element}-type
 * classes to inherit.
 *
 * @author Mateo Imbrišak
 */

public class Element {

    /**
     * Used by other {@code Element}-type classes
     * to return their value.
     *
     * @return an empty {@code String}.
     */
    public String asText() {
        return "";
    }
}
