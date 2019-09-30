package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Used to represent the possible states
 * of {@code SmartScriptLexer}.
 *
 * @author Mateo Imbrišak
 */

public enum SmartScriptLexerState {

    /**
     * Used When reading normal text.
     */
    TEXT,

    /**
     * Used when inside a tag.
     */
    TAG
}
