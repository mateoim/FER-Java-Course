package hr.fer.zemris.java.hw06.shell.commands.massrename.util;

import java.util.Objects;

/**
 * A class that represents a token for
 * {@code NameBuilderLexer}.
 *
 * @author Mateo Imbrišak
 */

public class NameBuilderToken {

    /**
     * Keeps the type of this token.
     */
    private NameBuilderTokenType type;

    /**
     * Keeps the value of this token.
     */
    private String value;

    /**
     * Default constructor that assigns all values.
     *
     * @param value to be assigned.
     * @param type  to be assigned.
     */
    public NameBuilderToken(String value, NameBuilderTokenType type) {
        Objects.requireNonNull(type);

        this.type = type;
        this.value = value;
    }

    /**
     * Provides the type of this token.
     *
     * @return type of this token.
     */
    public NameBuilderTokenType getType() {
        return type;
    }

    /**
     * Provides the value of this token.
     *
     * @return value of this token.
     */
    public String getValue() {
        return value;
    }
}
