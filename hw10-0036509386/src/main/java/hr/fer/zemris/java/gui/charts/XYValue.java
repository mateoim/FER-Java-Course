package hr.fer.zemris.java.gui.charts;

/**
 * A class that represents a pair of x and y values
 * to be used on a {@code BarChart}.
 *
 * @author Mateo Imbrišak
 */

public class XYValue {

    /**
     * Keeps the x coordinate.
     */
    private final int x;

    /**
     * Keeps the y coordinate.
     */
    private final int y;

    /**
     * Default constructor that assigns all values.
     *
     * @param x coordinate.
     * @param y coordinate.
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Provides the value of x coordinate.
     *
     * @return x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Provides the value of y coordinate.
     *
     * @return y coordinate.
     */
    public int getY() {
        return y;
    }
}
