package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2DTest {

    @Test
    void testConstructor() {
        Vector2D vector = new Vector2D(5, 3);

        assertEquals(5d, vector.getX());
        assertEquals(3d, vector.getY());
    }

    @Test
    void testGetX() {
        Vector2D vector = new Vector2D(5, 13);

        assertEquals(5d, vector.getX());
    }

    @Test
    void testGetY() {
        Vector2D vector = new Vector2D(5, 13);

        assertEquals(13d, vector.getY());
    }

    @Test
    void testTranslate() {
        Vector2D vector = new Vector2D(3, 3);
        Vector2D offset = new Vector2D(2, 5);

        vector.translate(offset);

        assertEquals(5, vector.getX());
        assertEquals(8, vector.getY());

        vector.translate(new Vector2D(-6, -1));

        assertEquals(-1, vector.getX());
        assertEquals(7, vector.getY());
    }

    @Test
    void testTranslated() {
        Vector2D vector = new Vector2D(5, 7);
        Vector2D offset = new Vector2D(3.14, 3);

        assertEquals(new Vector2D(8.14, 10), vector.translated(offset));
    }

    @Test
    void testRotate() {
        Vector2D vector = new Vector2D(5, 7);

        vector.rotate(Math.PI);

        assertEquals(new Vector2D(-5, -7), vector);
    }

    @Test
    void testRotated() {
        Vector2D vector = new Vector2D(4, 0);

        assertEquals(vector, vector.rotated(Math.PI * 2));
        assertEquals(new Vector2D(0, 4), vector.rotated(Math.PI / 2));

        Vector2D midway = new Vector2D(2, 2);

        assertEquals(new Vector2D(0, Math.sqrt(8)), midway.rotated(Math.PI / 4));
    }

    @Test
    void testScale() {
        Vector2D vector = new Vector2D(4, 3);

        vector.scale(3);

        assertEquals(12d, vector.getX());
        assertEquals(9d, vector.getY());
    }

    @Test
    void testScaled() {
        Vector2D vector = new Vector2D(2, 5);

        assertEquals(new Vector2D(-4, -10), vector.scaled(-2));
    }

    @Test
    void testCopy() {
        Vector2D vector = new Vector2D(5.99, 3.14);

        assertEquals(vector, vector.copy());
    }
}
