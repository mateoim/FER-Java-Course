package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * A class which holds all elements necessary to
 * execute a single conditional expression.
 *
 * @author Mateo Imbrišak
 */
public class ConditionalExpression {

    /**
     * Getter for the desired field.
     */
    private IFieldValueGetter fieldGetter;

    /**
     * A {@code String} stringLiteral used in
     * the expression.
     */
    private String stringLiteral;

    /**
     * Operator used to compare the stringLiteral
     * and the value.
     */
    private IComparisonOperator comparisonOperator;

    /**
     * Default constructor that assigns all parameters.
     *
     * @param fieldGetter for the desired field.
     * @param stringLiteral being compared.
     * @param operator being used to compare the values.
     *
     * @throws NullPointerException if any argument is {@code null}.
     */
    public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator operator) {
        Objects.requireNonNull(fieldGetter);
        Objects.requireNonNull(stringLiteral);
        Objects.requireNonNull(operator);

        this.stringLiteral = stringLiteral;
        this.fieldGetter = fieldGetter;
        this.comparisonOperator = operator;
    }

    /**
     * Returns the field fieldGetter used in comparison.
     *
     * @return {@code IFieldValueGetter}
     * used to get the value.
     */
    public IFieldValueGetter getFieldGetter() {
        return fieldGetter;
    }

    /**
     * Returns the stringLiteral used in comparison.
     *
     * @return a {@code String} used in comparison.
     */
    public String getStringLiteral() {
        return stringLiteral;
    }

    /**
     * Returns an operator that determines
     * how to compare the values.
     *
     * @return a {@code IComparisonOperator}
     * that performs the comparison.
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }
}
