package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser used to parse the query command
 * in the database.
 *
 * @author Mateo Imbrišak
 */

public class QueryParser {

    /**
     * Lexer used to process the input.
     */
    private QueryLexer lexer;

    /**
     * Keeps all query expressions as a {@code List}
     * of {@code ConditionalExpressions}.
     */
    private List<ConditionalExpression> query;

    /**
     * Default constructor that provides a {@code String} as
     * input and passes it to the internal {@code QueryLexer}.
     *
     * @param input a {@code String} used as input.
     */
    public QueryParser(String input) {
        lexer = new QueryLexer(input);
        query = new ArrayList<>();

        parseQuery();
    }

    /**
     * Checks it the query only has a single expression
     * asking for a specific jmbag.
     *
     * @return {@code true} if the query is direct,
     * otherwise {@code false}.
     */
    public boolean isDirectQuery() {
        if (query.size() == 1) {
            return query.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG) &&
                    query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS);
        }

        return false;
    }

    /**
     * Returns the jmbag from the query if the query is direct.
     *
     * @return jmbag from the query if it is direct.
     *
     * @throws IllegalStateException if the query is not direct.
     */
    public String getQueriedJMBAG() {
        if (!isDirectQuery()) {
            throw new IllegalStateException("Query type is not direct.");
        }

        return query.get(0).getStringLiteral();
    }

    /**
     * Returns the query expressions as a {@code List} of
     * {@code ConditionalExpression}s.
     *
     * @return {@code ConditionalExpresion}s as a {@code List}.
     */
    public List<ConditionalExpression> getQuery() {
        return query;
    }

    /**
     * Used internally to parse the input and create the list
     * of {@code ConditionalExpression}s.
     */
    private void parseQuery() {
        lexer.nextToken();

        while (lexer.getToken().getType() != QueryTokenType.EOF) {
            IFieldValueGetter getter = null;
            IComparisonOperator operator = null;
            String literal = null;

            for (int i = 0; i < 3; i++) {
                QueryToken token = lexer.getToken();

                switch (i) {
                    case 0:
                        getter = getterProtocol(token.getValue());
                        break;
                    case 1:
                        operator = operatorProtocol(token.getValue());
                        break;
                    case 2:
                        if (token.getType().equals(QueryTokenType.LITERAL)) {
                            literal = token.getValue();
                        } else {
                            throw new QueryException("Invalid element passed as literal.");
                        }
                        break;
                }

                lexer.nextToken();
            }

            try {
                ConditionalExpression expression = new ConditionalExpression(getter, literal, operator);
                query.add(expression);
            } catch (NullPointerException exc) {
                throw new QueryException("Invalid elements in query.");
            }

            if (lexer.getToken().getType() == QueryTokenType.AND) {
                lexer.nextToken();
            } else if (lexer.getToken().getType() != QueryTokenType.EOF) {
                throw new QueryException("Invalid operation after expression.");
            }
        }
    }

    /**
     * Used internally to create assign a field value
     * getter based on provided input.
     *
     * @param getter field name as a {@code String}.
     *
     * @return an instance of {@code IFieldValueGetter}
     * representing the provided getter.
     *
     * @throws QueryException if the provided input
     * does not represent a supported field value getter.
     */
    private IFieldValueGetter getterProtocol(String getter) {
        switch (getter) {
            case "firstName":
                return FieldValueGetters.FIRST_NAME;
            case "lastName":
                return FieldValueGetters.LAST_NAME;
            case "jmbag":
                return FieldValueGetters.JMBAG;
            default:
                throw new QueryException("Invalid field name found.");
        }
    }

    /**
     * Used internally to create assign an operator
     * based on provided input.
     *
     * @param op operator as a {@code String}.
     *
     * @return an instance of {@code IComparisonOperator}
     * representing the provided operator.
     *
     * @throws QueryException if the provided input
     * does not represent a supported operator.
     */
    private IComparisonOperator operatorProtocol(String op) {
        switch (op) {
            case "=":
                return ComparisonOperators.EQUALS;
            case "LIKE":
                return ComparisonOperators.LIKE;
            case "<":
                return ComparisonOperators.LESS;
            case ">":
                return ComparisonOperators.GREATER;
            case "!=":
                return ComparisonOperators.NOT_EQUALS;
            case "<=":
                return ComparisonOperators.LESS_OR_EQUALS;
            case ">=":
                return ComparisonOperators.GREATER_OR_EQUALS;
            default:
                throw new QueryException("Invalid comparator found.");
        }
    }
}
