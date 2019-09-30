package hr.fer.zemris.java.custom.scripting.parser;

/**
 * A {@code RuntimeException} used by {@code SmartScriptParser}
 * when it encounters an error.
 *
 * @author Mateo Imbrišak
 */

public class SmartScriptParserException extends RuntimeException {
    static final long serialVersionUID = 1L;

    /**
     * A constructor used when no message is being delegated to
     * the user.
     */
    public SmartScriptParserException() {
        super();
    }

    /**
     * Constructor used to delegate a message to the
     * user.
     *
     * @param s message being sent.
     */
    public SmartScriptParserException(String s) {
        super(s);
    }

}
