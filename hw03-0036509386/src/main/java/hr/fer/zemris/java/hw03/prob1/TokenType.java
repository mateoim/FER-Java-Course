package hr.fer.zemris.java.hw03.prob1;

/**
 * An enumeration that represents possible types of a {@code Token}.
 *
 * @author Mateo Imbrišak
 */

public enum TokenType {

    /**
     * Used for the last {@code Token}, represents
     * end of file.
     */
    EOF,

    /**
     *Used when the {@code Token}'s value is is a
     * string of letters and numbers represented as letters.
     */
    WORD,

    /**
     * Used when the {@code Token}'s value is numerical
     * and can be represented as a {@code Long}.
     */
    NUMBER,

    /**
     * Used when the type is not a {@code WORD} or a
     * {@code NUMBER}.
     */
    SYMBOL
}
