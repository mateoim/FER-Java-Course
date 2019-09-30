package hr.fer.zemris.java.hw01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FactorialTest {

    @Test
    void calculateFactorialTest() {
        assertEquals(6, Factorial.calculateFactorial(3));
        assertEquals(24, Factorial.calculateFactorial(4));
        assertEquals(2432902008176640000L, Factorial.calculateFactorial(20));
    }

    @Test
    void calculateFactorialExceptionTest() {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> Factorial.calculateFactorial(-4));
        assertEquals(exc.getMessage(), "'-4' nije broj u dozvoljenom rasponu.");
    }
}
