package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * A token used by the {@code SmartScriptLexer}.
 *
 * @author Mateo Imbrišak
 */

public class SmartScriptToken {

    /**
     * Type of this {@code SmartScriptToken}.
     * Always a member of {@code SmartScriptTokenType}.
     */
    private SmartScriptTokenType type;

    /**
     * Value of this {@code SmartScriptToken}.
     */
    private Object value;

    /**
     * Default constructor that assigns value and {@code TokenType}.
     *
     * @param type {@code SmartScriptTokenType} of this {@code SmartScriptToken}.
     *
     * @param value of this {@code SmartScriptToken}.
     */
    public SmartScriptToken(SmartScriptTokenType type, Object value) {
        if (type == null) {
            throw new NullPointerException("Token type cannot be null");
        }

        this.type = type;
        this.value = value;
    }

    /**
     * Returns the type of this {@code SmartScriptToken}.
     *
     * @return {@code SmartScriptTokenType} of this {@code SmartScriptToken}.
     */
    public SmartScriptTokenType getType() {
        return type;
    }

    /**
     * Returns the value of this {@code SmartScriptToken}.
     *
     * @return value of thes {@code SmartScriptToken}.
     */
    public Object getValue() {
        return value;
    }
}
