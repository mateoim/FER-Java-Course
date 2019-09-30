package hr.fer.zemris.java.hw06.shell.commands.massrename.util;

/**
 * A lexer for the {@code NameBuilderParser}.
 *
 * @author Mateo Imbrišak
 */

public class NameBuilderLexer {

    /**
     * Keeps the current token.
     */
    private NameBuilderToken token;

    /**
     * Keeps the input as an array of characters.
     */
    private char[] data;

    /**
     * Keeps the current index.
     */
    private int currentIndex;

    /**
     * Default constructor that assigns input data.
     *
     * @param input being analyzed.
     */
    public NameBuilderLexer(String input) {
        data = input.toCharArray();
    }

    /**
     * Generates the next token.
     *
     * @return next token.
     */
    public NameBuilderToken nextToken() {
        if (currentIndex >= data.length && token.getType() != NameBuilderTokenType.EOF) {
            token = new NameBuilderToken(null, NameBuilderTokenType.EOF);
            return token;
        } else if (currentIndex >= data.length) {
            throw new RuntimeException("Last token has been reached.");
        }

        if (currentIndex + 1 < data.length && data[currentIndex] == '$' && data[currentIndex + 1] == '{') {
            token = tagProtocol();
            return token;
        } else {
            token =  stringProtocol();
            return token;
        }
    }

    /**
     * Provides the current token.
     *
     * @return current token.
     */
    public NameBuilderToken getToken() {
        return token;
    }

    /**
     * Used internally to generate the next token
     * if the type is {@code STRING}.
     *
     * @return next token.
     */
    private NameBuilderToken stringProtocol() {
        boolean isValid = true;
        StringBuilder ret = new StringBuilder();

        while (isValid && currentIndex < data.length) {
            if (data[currentIndex] == '$') {
                if (currentIndex + 1 < data.length && data[currentIndex + 1] == '{') {
                    isValid = false;
                } else {
                    ret.append(data[currentIndex]);
                    currentIndex++;
                }
            } else {
                ret.append(data[currentIndex]);
                currentIndex++;
            }
        }

        return new NameBuilderToken(ret.toString(), NameBuilderTokenType.STRING);
    }

    /**
     * Used internally to generate the next token
     * if the type is {@code TAG}.
     *
     * @return next token.
     */
    private NameBuilderToken tagProtocol() {
        StringBuilder ret = new StringBuilder();

        while (currentIndex < data.length && data[currentIndex] != '}') {
            ret.append(data[currentIndex]);
            currentIndex++;
        }

        if (currentIndex < data.length && data[currentIndex] == '}') {
            ret.append(data[currentIndex]);
            currentIndex++;

            return new NameBuilderToken(ret.toString().substring(2, ret.length() - 1), NameBuilderTokenType.TAG);
        } else {
            throw new RuntimeException("Tag was never closed.");
        }
    }
}
