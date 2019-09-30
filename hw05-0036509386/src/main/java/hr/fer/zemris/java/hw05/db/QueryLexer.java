package hr.fer.zemris.java.hw05.db;

/**
 * A lexer used to process the query input.
 *
 * @author Mateo Imbrišak
 */

public class QueryLexer {

    /**
     * Keeps the input as a char array.
     */
    private char[] data;

    /**
     * Keeps track of the current index.
     */
    private int currentIndex;

    /**
     * Keeps the current {@code QueryToken}.
     */
    private QueryToken token;

    /**
     * Default constructor that takes a string input and
     * converts it into a char array.
     *
     * @param input to be processed by the lexer.
     */
    public QueryLexer(String input) {
        data = input.toCharArray();
    }

    /**
     * Returns the current {@code QueryToken}.
     *
     * @return current {@code QueryToken}.
     */
    public QueryToken getToken() {
        return token;
    }

    /**
     * Generates the next token based on input of the lexer.
     *
     * @return next {@code QueryToken}.
     */
    public QueryToken nextToken() {
        while (currentIndex < data.length && Character.toString(data[currentIndex]).matches("[\t\n\r ]")) {
            currentIndex++;
        }

        if (currentIndex >= data.length) {
            token = new QueryToken(QueryTokenType.EOF, null);
            return token;
        }

        char current = data[currentIndex];

        if (Character.isLetter(current)) {
            String nextWord = constructExpression();

            if (nextWord.equalsIgnoreCase("AND")) {
                token = new QueryToken(QueryTokenType.AND, "AND");
                return token;
            } else if (nextWord.equalsIgnoreCase("LIKE")) {
                token = new QueryToken(QueryTokenType.OPERATOR, "LIKE");
                return token;
            }

            token = new QueryToken(QueryTokenType.FIELD, nextWord);
            return token;
        } else if (Character.toString(current).matches("[=<>!]")) {
            token = constructOperator();
            currentIndex++;
            return token;
        } else if (current == '"') {
            token = new QueryToken(QueryTokenType.LITERAL, constructLiteral());
            return token;
        } else {
            throw new QueryException("Invalid input.");
        }
    }

    /**
     * Used internally to process the next token
     * as a string {@code LITERAL}.
     *
     * @return a {@code String} to be used as the
     * token's value.
     */
    private String constructLiteral() {
        StringBuilder ret = new StringBuilder();
        currentIndex++;

        while (currentIndex < data.length && data[currentIndex] != '"') {
            ret.append(data[currentIndex]);
            currentIndex++;
        }

        if (currentIndex == data.length || data[currentIndex] != '"') {
            throw new QueryException("Literal quote was never closed.");
        }

        currentIndex++;

        return ret.toString();
    }

    /**
     * Used internally to process the next token
     * as a word without quotes.
     *
     * @return a {@code String} to be used in the next
     * token as value.
     */
    private String constructExpression() {
        StringBuilder ret = new StringBuilder();

        while (currentIndex  < data.length &&
                !Character.toString(data[currentIndex]).matches("[=<>!\n\r\t ]")) {
            ret.append(data[currentIndex]);
            currentIndex++;
        }

        return ret.toString();

    }

    /**
     * Used internally to process the next token
     * as an operator.
     *
     * @return a {@code QueryToken} of {@code OPERATOR}
     * type with appropriate comparison operator as value.
     */
    private QueryToken constructOperator() {
        switch (data[currentIndex]) {
            case '=':
                return new QueryToken(QueryTokenType.OPERATOR, "=");
            case '<':
                return somethingEqualsOperator('<');
            case '>':
                return somethingEqualsOperator('>');
            case '!':
                QueryToken testToken = somethingEqualsOperator('!');

                if (testToken.getValue().equals("!=")) {
                    return testToken;
                }
            default:
                throw new QueryException("Invalid operator found.");
        }
    }

    /**
     * Used internally to check if the operator contains equals
     * symbol after the first symbol.
     *
     * @param operator first symbol in the operator.
     *
     * @return a {@code QueryToken} of {@code OPERATOR}
     * type with complete operator as value.
     */
    private QueryToken somethingEqualsOperator(char operator) {
        if (currentIndex + 1 < data.length) {
            if (data[++currentIndex] == '=') {
                return new QueryToken(QueryTokenType.OPERATOR, operator + "=");
            } else {
                currentIndex--;
            }
        }

        return new QueryToken(QueryTokenType.OPERATOR, Character.toString(operator));
    }
}
