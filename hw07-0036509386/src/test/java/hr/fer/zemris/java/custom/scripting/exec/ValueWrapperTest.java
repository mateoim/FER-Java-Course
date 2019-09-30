package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueWrapperTest {

    @Test
    void testAddInt() {
        ValueWrapper test = new ValueWrapper(5);

        test.add(15);

        assertTrue(test.getValue() instanceof Integer);
        assertEquals(20, test.getValue());
    }

    @Test
    void testAddNull() {
        ValueWrapper test = new ValueWrapper(null);

        test.add(7);

        assertTrue(test.getValue() instanceof Integer);
        assertEquals(7, test.getValue());
    }

    @Test
    void  testAddDouble() {
        ValueWrapper test = new ValueWrapper(5);

        test.add(12.3);

        assertTrue(test.getValue() instanceof Double);
        assertEquals(17.3, test.getValue());
    }

    @Test
    void testAddIntString() {
        ValueWrapper test = new ValueWrapper("15");

        test.add("3");

        assertTrue(test.getValue() instanceof Integer);
        assertEquals(18, test.getValue());
    }

    @Test
    void testAddDoubleString() {
        ValueWrapper test = new ValueWrapper("15.9");

        test.add("3.1");

        assertTrue(test.getValue() instanceof Double);
        assertEquals(19d, test.getValue());
    }

    @Test
    void testInvalidObject() {
        ValueWrapper test1 = new ValueWrapper(Boolean.TRUE);

        assertThrows(RuntimeException.class, () -> test1.add(15));

        ValueWrapper test2 = new ValueWrapper(15);

        assertThrows(RuntimeException.class, () -> test2.add(Boolean.TRUE));
    }

    @Test
    void testExponentialParsing() {
        ValueWrapper test = new ValueWrapper("5E-2");

        test.add(1);

        assertTrue(test.getValue() instanceof Double);
        assertEquals((1 + 5E-2), test.getValue());
    }

    @Test
    void testStringParsingException() {
        ValueWrapper test = new ValueWrapper("5E-2Q");

        assertThrows(RuntimeException.class, () -> test.subtract(6));
    }

    @Test
    void testAddNulls() {
        ValueWrapper v1 = new ValueWrapper(null);
        ValueWrapper v2 = new ValueWrapper(null);
        v1.add(v2.getValue());

        assertTrue(v1.getValue() instanceof Integer);
        assertEquals(0, v1.getValue());
        assertNull(v2.getValue());
    }

    @Test
    void testIntegerSub() {
        ValueWrapper test = new ValueWrapper(15);

        test.subtract(5);

        assertEquals(10, test.getValue());
        assertTrue(test.getValue() instanceof Integer);
    }

    @Test
    void testDoubleSub() {
        ValueWrapper test = new ValueWrapper(3.14);

        test.subtract(5);

        assertEquals(-1.86, (double) test.getValue(), 1E-5);
        assertTrue(test.getValue() instanceof Double);
    }

    @Test
    void testStringSub() {
        ValueWrapper test = new ValueWrapper("150");

        test.subtract("50.0");

        assertEquals(100d, test.getValue());
        assertTrue(test.getValue() instanceof Double);
    }

    @Test
    void testSubWithNull() {
        ValueWrapper test = new ValueWrapper(null);

        test.subtract("1e1");

        assertTrue(test.getValue() instanceof Double);
        assertEquals(-10d, test.getValue());
    }

    @Test
    void testMulInteger() {
        ValueWrapper test = new ValueWrapper(3);

        test.multiply(5);

        assertEquals(15, test.getValue());
        assertTrue(test.getValue() instanceof Integer);
    }

    @Test
    void testMulDouble() {
        ValueWrapper test = new ValueWrapper(3.14);

        test.multiply(2);

        assertEquals(6.28, (double) test.getValue(), 1E-5);
        assertTrue(test.getValue() instanceof Double);
    }

    @Test
    void testMulString() {
        ValueWrapper test = new ValueWrapper("15");

        test.multiply(1);

        assertTrue(test.getValue() instanceof Integer);
        assertEquals(15, test.getValue());
    }

    @Test
    void testMulNull() {
        ValueWrapper test = new ValueWrapper("15");

        test.multiply(new ValueWrapper(null).getValue());

        assertTrue(test.getValue() instanceof Integer);
        assertEquals(0, test.getValue());
    }

    @Test
    void testDivInteger() {
        ValueWrapper test = new ValueWrapper(15);

        test.divide(6);

        assertEquals(2, test.getValue());
        assertTrue(test.getValue() instanceof Integer);
    }

    @Test
    void testDivDouble() {
        ValueWrapper test = new ValueWrapper(3.14);

        test.divide(2);

        assertEquals(1.57, (double) test.getValue(), 1E-5);
        assertTrue(test.getValue() instanceof Double);
    }

    @Test
    void testDivString() {
        ValueWrapper test = new ValueWrapper("150");

        test.divide("3.5");

        assertTrue(test.getValue() instanceof Double);
        assertEquals(42.857, (double) test.getValue(), 1E-3);
    }

    @Test
    void testDivNull() {
        ValueWrapper test = new ValueWrapper(null);

        test.divide(5);

        assertTrue(test.getValue() instanceof Integer);
        assertEquals(0, test.getValue());
    }

    @Test
    void testDivError() {
        ValueWrapper test = new ValueWrapper("57");

        assertThrows(ArithmeticException.class, () -> test.divide(null));
    }

    @Test
    void testInfinity() {
        ValueWrapper test = new ValueWrapper(5.6);

        test.divide("0");

        assertTrue(test.getValue() instanceof Double);
        assertEquals(Double.POSITIVE_INFINITY, test.getValue());
    }

    @Test
    void testIntComparison() {
        ValueWrapper test = new ValueWrapper(50);

        assertEquals(0, test.numCompare(50));
        assertTrue(test.numCompare("39") > 0);
        assertTrue(test.numCompare(66.6) < 0);

        assertTrue(test.getValue() instanceof Integer);
        assertEquals(50, test.getValue());
    }

    @Test
    void testDoubleComparison() {
        ValueWrapper test = new ValueWrapper(58.6);

        assertEquals(0, test.numCompare("58.6"));
        assertTrue(test.numCompare(-3.14) > 0);
        assertTrue(test.numCompare(66) < 0);

        assertTrue(test.getValue() instanceof Double);
        assertEquals(58.6, test.getValue());
    }

    @Test
    void testStringComparison() {
        ValueWrapper test = new ValueWrapper("66");

        assertEquals(0, test.numCompare(66.0));
        assertTrue(test.numCompare("-3.14") > 0);
        assertTrue(test.numCompare(660) < 0);

        assertTrue(test.getValue() instanceof String);
        assertEquals("66", test.getValue());
    }
}
