package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * A class used to filter the database based on
 * given {@code ConditionalExpression}s.
 *
 * @author Mateo Imbrišak
 */

public class QueryFilter implements IFilter {

    /**
     * Keeps the {@code ConditionalExpression}s used
     * for filtering.
     */
    private List<ConditionalExpression> query;

    /**
     * Default constructor that assigns the {@code ConditionalExpression}s
     * used for filtering.
     *
     * @param query a {@code List} of {@code ConditionalExpression}s.
     */
    public QueryFilter(List<ConditionalExpression> query) {
        this.query = query;
    }

    /**
     * Tests whether the given record is acceptable
     * based on the internal list of {@code ConditionalExpression}s.
     *
     * @param record being tested.
     *
     * @return {@code true} if the record is acceptable,
     * otherwise {@code false}.
     */
    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression expression : query) {
            if (!expression.getComparisonOperator().satisfied(expression.getFieldGetter().get(record),
                    expression.getStringLiteral())) {
                return false;
            }
        }

        return true;
    }
}
