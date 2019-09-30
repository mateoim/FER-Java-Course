package hr.fer.zemris.java.hw05.db;

/**
 * An enumeration that contains all possible
 * types for {@code QueryToken}.
 *
 * @author Mateo Imbrišak
 */

public enum QueryTokenType {

    /**
     * Used for field in the database.
     */
    FIELD,

    /**
     * Used for a comparison operator.
     */
    OPERATOR,

    /**
     * Used for a {@code String} literal.
     */
    LITERAL,

    /**
     * Used for AND logical operator.
     */
    AND,

    /**
     * Used to indicate the end of input.
     */
    EOF
}
