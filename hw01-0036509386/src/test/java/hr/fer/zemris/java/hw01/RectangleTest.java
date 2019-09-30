package hr.fer.zemris.java.hw01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

    @Test
    void calculateAreaTest() {
        assertEquals(16d, Rectangle.calculateArea(2, 8));
        assertEquals(4.25, Rectangle.calculateArea(2.5, 1.7));
    }

    @Test
    void calculatePerimeterTest() {
        assertEquals(20d, Rectangle.calculatePerimeter(2, 8));
        assertEquals(8.4, Rectangle.calculatePerimeter(2.5, 1.7));
    }
}
