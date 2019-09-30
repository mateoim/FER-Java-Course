package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * An {@code Exception} thrown by the {@code SmartScriptLexer}
 * when encountering an error.
 *
 * @author Mateo Imbrišak
 */

public class SmartScriptLexerException extends RuntimeException {
    static final long serialVersionUID = 1L;

    /**
     * A constructor used when no message is being delegated to
     * the user.
     */
    public SmartScriptLexerException() {
        super();
    }

    /**
     * Constructor used to delegate a message to the
     * user.
     *
     * @param s message being sent.
     */
    public SmartScriptLexerException(String s) {
        super(s);
    }
}
