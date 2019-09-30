package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * An enumeration that keeps all possible
 * states of a {@code SmartScriptToken}.
 *
 * @author Mateo Imbrišak
 */

public enum SmartScriptTokenType {

    /**
     * Used when reaching an end of file.
     */
    EOF,

    /**
     * Used to represent a variable.
     */
    VARIABLE,

    /**
     * Used to represent a constant integer.
     */
    INTEGER,

    /**
     * Used to represent a string.
     */
    STRING,

    /**
     * Used to represent a constant double.
     */
    DOUBLE,

    /**
     * Used to represent a valid operator.
     */
    OPERATOR,

    /**
     * Used to represent a function.
     */
    FUNCTION,

    /**
     * Used to represent a tag within "{$" "$}"
     * marks.
     */
    TAG,

    /**
     * Used to represent the end of a tag.
     */
    EOT,

    /**
     * Used to represent text outside of tags.
     */
    TEXT
}
