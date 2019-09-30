package hr.fer.zemris.java.hw06.shell.commands.massrename.util;

/**
 * Enumeration that contains all possible types
 * of {@code NameBuilderToken}.
 *
 * @author Mateo Imbrišak
 */

public enum NameBuilderTokenType {

    /**
     * Used when the type is a normal string.
     */
    STRING,

    /**
     * Used when the token is a ${} tag.
     */
    TAG,

    /**
     * Used for the last token.
     */
    EOF
}
