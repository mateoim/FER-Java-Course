package hr.fer.zemris.java.hw03.prob1;

/**
 * A class that represents tokens for the {@code Lexer}.
 *
 * @author Mateo Imbrišak
 */

public class Token {

    /**
     * Type of this {@code Token}.
     * Always a member of {@code TokenType}.
     */
    private TokenType type;

    /**
     * Value of this {@code Token}.
     */
    private Object value;

    /**
     * Default constructor that assigns value and {@code TokenType}.
     *
     * @param type {@code TokenType} of this {@code Token}.
     *
     * @param value of this {@code Token}.
     */
    public Token(TokenType type, Object value) {
        if (type == null) {
            throw new NullPointerException("Token type cannot be null");
        }

        this.type = type;
        this.value = value;
    }

    /**
     * Returns the type of this {@code Token}.
     *
     * @return {@code TokenType} of this {@code Token}.
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Returns the value of this {@code Token}.
     *
     * @return value of thes {@code Token}.
     */
    public Object getValue() {
        return value;
    }
}
