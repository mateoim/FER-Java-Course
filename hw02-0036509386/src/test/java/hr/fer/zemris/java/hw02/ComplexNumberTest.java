package hr.fer.zemris.java.hw02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplexNumberTest {

    @Test
    void testConstructor() {
        ComplexNumber number = new ComplexNumber(5, 6);

        assertEquals(5, number.getReal());
        assertEquals(6, number.getImaginary());
        assertEquals(new ComplexNumber(5, 6), number);
    }

    @Test
    void testFromReal() {
        ComplexNumber number = ComplexNumber.fromReal(3.14);

        assertEquals(new ComplexNumber(3.14, 0), number);
    }

    @Test
    void testFromImaginary() {
        ComplexNumber number = ComplexNumber.fromImaginary(3.14);

        assertEquals(new ComplexNumber(0, 3.14), number);
    }

    @Test
    void testFromMagnitudeAndAngle() {
        ComplexNumber number = new ComplexNumber(5, -2);

        assertEquals(number, ComplexNumber.fromMagnitudeAndAngle(number.getMagnitude(), number.getAngle()));
        assertEquals(new ComplexNumber(3, 7), ComplexNumber.fromMagnitudeAndAngle(7.61577,
                Math.toRadians(66.8014)));
    }

    @Test
    void testParse() {
        assertEquals(new ComplexNumber(0, 1), ComplexNumber.parse("i"));
        assertEquals(new ComplexNumber(0, 1), ComplexNumber.parse("+i"));
        assertEquals(new ComplexNumber(0, -1), ComplexNumber.parse("-i"));

        assertEquals(new ComplexNumber(0, 5), ComplexNumber.parse("5i"));
        assertEquals(new ComplexNumber(0, -198.5), ComplexNumber.parse("-198.5i"));

        assertEquals(new ComplexNumber(15, 0), ComplexNumber.parse("15"));
        assertEquals(new ComplexNumber(-5, 0), ComplexNumber.parse("-5"));
        assertEquals(new ComplexNumber(10.3, 0), ComplexNumber.parse("+10.3"));

        assertEquals(new ComplexNumber(-5, 15), ComplexNumber.parse("-5+15i"));
        assertEquals(new ComplexNumber(5, 15), ComplexNumber.parse("+5+15i"));
        assertEquals(new ComplexNumber(-5, -15.36), ComplexNumber.parse("-5-15.36i"));
        assertEquals(new ComplexNumber(5, -15.36), ComplexNumber.parse("5-15.36i"));
    }

    @Test
    void testParseException() {
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("+-54"));
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("--54+43i"));
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("-+2.71"));
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("-i3.17"));
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("i1.75"));
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("15+-14i"));
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("-15++14i"));
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("test"));
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("-testi"));
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("testi"));
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("+te sti"));
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("1.0+1.0"));
    }

    @Test
    void testGetReal() {
        ComplexNumber number = new ComplexNumber(5, 6);

        assertEquals(5, number.getReal());
    }

    @Test
    void testGetImaginary() {
        ComplexNumber number = new ComplexNumber(5, 6);

        assertEquals(6, number.getImaginary());
    }

    @Test
    void testGetMagnitude() {
        ComplexNumber number1 = new ComplexNumber(4, 3);
        assertEquals(5, number1.getMagnitude());

        ComplexNumber number2 = ComplexNumber.fromReal(15);
        assertEquals(15, number2.getMagnitude());

        ComplexNumber number3 = ComplexNumber.fromImaginary(3.14);
        assertEquals(3.14, number3.getMagnitude());

        ComplexNumber number4 = new ComplexNumber(7, 6);
        assertEquals(9.21954, number4.getMagnitude(), ComplexNumber.tolerableDifference);
    }

    @Test
    void testGetAngle() {
        ComplexNumber number1 = new ComplexNumber(7, 6);
        assertEquals(Math.toRadians(40.6013), number1.getAngle(), ComplexNumber.tolerableDifference);

        ComplexNumber number2 = new ComplexNumber(5, 0);
        assertEquals(0, number2.getAngle());

        ComplexNumber number3 = new ComplexNumber(0, 5);
        assertEquals(Math.PI / 2, number3.getAngle());

        ComplexNumber number4 = new ComplexNumber(7, -6);
        assertEquals(Math.toRadians(-40.6013), number4.getAngle(), ComplexNumber.tolerableDifference);
    }

    @Test
    void testAdd() {
        ComplexNumber number1 = new ComplexNumber(7, 6);
        ComplexNumber number2 = new ComplexNumber(3, -2);

        assertEquals(new ComplexNumber(10, 4), number1.add(number2));
    }

    @Test
    void testSub() {
        ComplexNumber number1 = new ComplexNumber(7, 6);
        ComplexNumber number2 = new ComplexNumber(3, -2);

        assertEquals(new ComplexNumber(4, 8), number1.sub(number2));
    }

    @Test
    void testMul() {
        ComplexNumber number1 = new ComplexNumber(7, 6);
        ComplexNumber number2 = new ComplexNumber(3, -2);

        assertEquals(new ComplexNumber(33, 4), number1.mul(number2));
        assertEquals(new ComplexNumber(-9, 12),
                new ComplexNumber(0, 3).mul(new ComplexNumber(4, 3)));
        assertEquals(new ComplexNumber(12, 9),
                new ComplexNumber(3, 0).mul(new ComplexNumber(4, 3)));
    }

    @Test
    void testDiv() {
        ComplexNumber first = new ComplexNumber(1, -3);
        ComplexNumber second = new ComplexNumber(1, 2);

        assertEquals(new ComplexNumber(-1, -1), first.div(second));
        assertEquals(new ComplexNumber(0, 1), new ComplexNumber(-5, -5).div(new
                ComplexNumber(-5, 5)));

        assertEquals(Double.NaN + "i",
                new ComplexNumber(-5, -5).div(new ComplexNumber(0, 0)).toString());
        assertEquals(new ComplexNumber(1, -1),
                new ComplexNumber(-5, -5).div(new ComplexNumber(0, 5)));
    }

    @Test
    void testPower() {
        ComplexNumber number = new ComplexNumber(7, 6);

        assertEquals(new ComplexNumber(-413, 666), number.power(3));
        assertEquals(new ComplexNumber(1, 0), number.power(0));
        assertEquals(new ComplexNumber(25, 0), new ComplexNumber(5, 0).power(2));
    }

    @Test
    void testPowerException() {
        ComplexNumber number = new ComplexNumber(7, 6);

        assertThrows(IllegalArgumentException.class, () -> number.power(-1));
    }

    @Test
    void testRoot() {
        ComplexNumber number = new ComplexNumber(7, 6);

        ComplexNumber[] roots = {new ComplexNumber(2.03864, 0.49070),
                new ComplexNumber(-1.44428, 1.52016),
                new ComplexNumber(-0.59436, -2.01086)
        };

        assertArrayEquals(roots, number.root(3));
    }

    @Test
    void testRootException() {
        ComplexNumber number = new ComplexNumber(7, 6);

        assertThrows(IllegalArgumentException.class, () -> number.root(0));
        assertThrows(IllegalArgumentException.class, () -> number.root(-5));
    }

    @Test
    void testEquals() {
        ComplexNumber number = new ComplexNumber(7, 6);

        assertEquals(new ComplexNumber(6.999999876, 5.999999876), number);
    }
}
