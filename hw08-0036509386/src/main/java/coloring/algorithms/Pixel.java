package coloring.algorithms;

import java.util.Objects;

/**
 * A class that represents a single pixel.
 *
 * @author Mateo Imbrišak
 */

public class Pixel {

    /**
     * Keeps the x coordinate of the pixel.
     */
    public int x;

    /**
     * Keeps the y coordinate of the pixel.
     */
    public int y;

    /**
     * Default constructor that initializes all values.
     *
     * @param x coordinate of the pixel.
     * @param y coordinate of the pixel.
     */
    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return x == pixel.x &&
                y == pixel.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
