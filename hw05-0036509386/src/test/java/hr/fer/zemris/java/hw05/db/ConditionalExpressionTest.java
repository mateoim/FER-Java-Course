package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConditionalExpressionTest {

    @Test
    void testConstructorException() {
        assertThrows(NullPointerException.class, () -> new ConditionalExpression(null, null, null));
    }

    @Test
    void testLiteralGetter() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Bos*",
                ComparisonOperators.LIKE
        );

        assertEquals("Bos*", expr.getStringLiteral());
    }

    @Test
    void testFiledValueGetter() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Bos*",
                ComparisonOperators.LIKE
        );

        assertEquals(FieldValueGetters.LAST_NAME, expr.getFieldGetter());
    }

    @Test
    void testComparisonOperatorGetter() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Bos*",
                ComparisonOperators.LIKE
        );

        assertEquals(ComparisonOperators.LIKE, expr.getComparisonOperator());
    }

    @Test
    void testExecutesCorrectly() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Bos*",
                ComparisonOperators.LIKE
        );

        StudentRecord record = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);

        assertTrue(expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral())
        );
    }
}
