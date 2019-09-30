package hr.fer.zemris.java.hw05.db;

/**
 * Used to create comparators for values in the database.
 *
 * @author Mateo Imbrišak
 */

public interface IComparisonOperator {

    /**
     * Checks if the given values satisfy the
     * comparison condition.
     *
     * @param value1 first value being compared.
     * @param value2 second value being compared.
     *
     * @return {@code true} if the comparison condition
     * is satisfied, otherwise {@code false}.
     */
    boolean satisfied(String value1, String value2);
}
