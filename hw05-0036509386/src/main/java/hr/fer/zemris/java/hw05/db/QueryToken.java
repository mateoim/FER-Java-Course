package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * A token type used by the {@code QueryLexer}.
 *
 * @author Mateo Imbrišak
 */

public class QueryToken {

    /**
     * A {@code QueryTokenType} of this
     * {@code QueryToken}.
     */
    private QueryTokenType type;

    /**
     * Value of this {@code QueryToken}.
     */
    private String value;

    /**
     * Default constructor that assigns all values.
     *
     * @param type of the {@code QueryToken}.
     * @param value of the {@code QueryToken}.
     *
     * @throws NullPointerException if the type is
     * {@code null}.
     */
    public QueryToken(QueryTokenType type, String value) {
        Objects.requireNonNull(type);

        this.type = type;
        this.value = value;
    }

    /**
     * Returns the type of this {@code QueryToken}.
     *
     * @return type of this token.
     */
    public QueryTokenType getType() {
        return type;
    }

    /**
     * Returns the value os this {@code QueryToken}.
     *
     * @return value of this token.
     */
    public String getValue() {
        return value;
    }
}
