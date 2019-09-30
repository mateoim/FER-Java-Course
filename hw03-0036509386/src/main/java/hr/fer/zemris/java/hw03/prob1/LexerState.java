package hr.fer.zemris.java.hw03.prob1;

/**
 * An enumeration used for switching states of a {@code Lexer}.
 *
 * @author Mateo Imbrišak
 */

public enum LexerState {
    /**
     * Initial state with basic functionality.
     */
    BASIC,

    /**
     * State used to read all input as
     * a single word.
     */
    EXTENDED
}
