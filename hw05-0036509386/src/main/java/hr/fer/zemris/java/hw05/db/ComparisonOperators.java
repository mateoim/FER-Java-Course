package hr.fer.zemris.java.hw05.db;

/**
 * A class that contains comparators for
 * string literals in the {@code StudentDatabase}.
 *
 * @author Mateo Imbrišak
 */

public class ComparisonOperators {

    /**
     * Used to check if the first value is lesser than the second.
     */
    public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;

    /**
     * Used to check if the first value is lesser than or equal to the second.
     */
    public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;

    /**
     * Used to check if the first value is greater than the second.
     */
    public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;

    /**
     * Used to check if the first value is greater than or equal to the second.
     */
    public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;

    /**
     * Used to check if the first value is equal to the second.
     */
    public static final IComparisonOperator EQUALS = (String::equals);

    /**
     * Used to check if the first value is not equal to the second.
     */
    public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> !v1.equals(v2);

    /**
     * Used to check if the first value is "like" the second by using
     * wildcard operator {@code *} that replaces any number of characters
     * (can be zero). Only one wildcard can be used. A {@code RuntimeException}
     * will be thrown if multiple are found.
     */
    public static final IComparisonOperator LIKE = (v1, v2) -> {
        if (v2.contains("*")) {
            String[] parts = v2.split("\\*");

            if (parts.length != 2 && !v2.endsWith("*")) {
                throw new RuntimeException("Multiple wildcard operators found.");
            }

            if (v2.charAt(0) == '*') {
                return v1.endsWith(v2.substring(1));
            } else if (v2.charAt(v2.length() - 1) == '*') {
                return v1.startsWith(v2.substring(0, v2.length() - 1));
            }

            if (v1.startsWith(parts[0])) {
                String end = v1.substring(parts[0].length());

                if (end.endsWith(parts[1])) {
                    return true;
                }
            } else {
                return false;
            }
        }

        return v1.equals(v2);
    };
}
