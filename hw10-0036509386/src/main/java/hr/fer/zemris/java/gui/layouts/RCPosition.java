package hr.fer.zemris.java.gui.layouts;

/**
 * A class used as constraint for {@code CalcLayout}.
 *
 * @author Mateo Imbrišak
 */

public class RCPosition {

    /**
     * Keeps the row index.
     */
    private final int row;

    /**
     * Keeps the column index.
     */
    private final int column;

    /**
     * Default constructor that assigns all values.
     *
     * @param row index.
     * @param column index.
     */
    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Provides the row index.
     *
     * @return row index.
     */
    public int getRow() {
        return row;
    }

    /**
     * Provides the column index.
     *
     * @return column index.
     */
    public int getColumn() {
        return column;
    }
}
