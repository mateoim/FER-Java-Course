package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComparisonOperatorsTest {

    @Test
    void testLess() {
        IComparisonOperator operator = ComparisonOperators.LESS;
        assertTrue(operator.satisfied("Ana", "Jasna"));
        assertFalse(operator.satisfied("Ana", "Ana"));
        assertFalse(operator.satisfied("Jasna", "Ana"));
    }

    @Test
    void testLessOrEquals() {
        IComparisonOperator operator = ComparisonOperators.LESS_OR_EQUALS;
        assertTrue(operator.satisfied("Ana", "Jasna"));
        assertTrue(operator.satisfied("Ana", "Ana"));
        assertFalse(operator.satisfied("Jasna", "Ana"));
    }

    @Test
    void testGreater() {
        IComparisonOperator operator = ComparisonOperators.GREATER;
        assertTrue(operator.satisfied("Jasna", "Ana"));
        assertFalse(operator.satisfied("Ana", "Ana"));
        assertFalse(operator.satisfied("Ana", "Jasna"));
    }

    @Test
    void testGreaterOrEqual() {
        IComparisonOperator operator = ComparisonOperators.GREATER_OR_EQUALS;
        assertTrue(operator.satisfied("Jasna", "Ana"));
        assertTrue(operator.satisfied("Ana", "Ana"));
        assertFalse(operator.satisfied("Ana", "Jasna"));
    }

    @Test
    void testEquals() {
        IComparisonOperator operator = ComparisonOperators.EQUALS;
        assertTrue(operator.satisfied("Ana", "Ana"));
        assertFalse(operator.satisfied("Ana", "Jasna"));
    }

    @Test
    void testNotEquals() {
        IComparisonOperator operator = ComparisonOperators.NOT_EQUALS;
        assertFalse(operator.satisfied("Ana", "Ana"));
        assertTrue(operator.satisfied("Ana", "Jasna"));
    }

    @Test
    void testLikeMiddle() {
        IComparisonOperator operator = ComparisonOperators.LIKE;
        assertTrue(operator.satisfied("Martin", "Ma*n"));
    }

    @Test
    void testLikeBeginning() {
        IComparisonOperator operator = ComparisonOperators.LIKE;
        assertTrue(operator.satisfied("testString", "*String"));
    }

    @Test
    void testLikeEnd() {
        IComparisonOperator operator = ComparisonOperators.LIKE;
        assertTrue(operator.satisfied("testString", "testS*"));
    }

    @Test
    void testLikeFail() {
        IComparisonOperator operator = ComparisonOperators.LIKE;
        assertFalse(operator.satisfied("ABC", "abc"));
    }

    @Test
    void testLikeEdgeCase() {
        IComparisonOperator operator = ComparisonOperators.LIKE;
        assertFalse(operator.satisfied("AAA", "AA*AA"));
        assertTrue(operator.satisfied("AAAA", "AA*AA"));
    }

    @Test
    void testLikeException() {
        IComparisonOperator operator = ComparisonOperators.LIKE;
        assertThrows(RuntimeException.class, () -> operator.satisfied("AAA", "*AA*AA"));
    }

}
