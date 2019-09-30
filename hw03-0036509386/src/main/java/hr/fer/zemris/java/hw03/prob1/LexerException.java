package hr.fer.zemris.java.hw03.prob1;

/**
 * An {@code Exception} thrown by a {@code Lexer}.
 *
 * @author Nateo Imbrišak
 */

public class LexerException extends RuntimeException {
    static final long serialVersionUID = 1L;

    /**
     * A constructor used when no message is being delegated to
     * the user.
     */
    public LexerException() {
        super();
    }

    /**
     * Constructor used to delegate a message to the
     * user.
     *
     * @param s message being sent.
     */
    public LexerException(String s) {
        super(s);
    }
}
