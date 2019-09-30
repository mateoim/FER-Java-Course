package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * A class that holds values for a {@code BarChartComponent}.
 *
 * @author Mateo Imbrišak
 */

public class BarChart {

    /**
     * Keeps the values of bars.
     */
    private final List<XYValue> values;

    /**
     * Keeps the description on x axis.
     */
    private final String xDescription;

    /**
     * Keeps the description on y axis.
     */
    private final String yDescription;

    /**
     * Keeps the starting y value.
     */
    private final int yMin;

    /**
     * Keeps the final y value.
     */
    private final int yMax;

    /**
     * Keeps the amount of numbers skipped
     * between points on y axis.
     */
    private final int space;

    /**
     * Default constructor that assigns all values.
     *
     * @param values of bars on the chart.
     * @param xDescription description on x axis.
     * @param yDescription description on y axis.
     * @param yMin starting y.
     * @param yMax final y.
     * @param space between points on y axis.
     *
     * @throws IllegalArgumentException if {@code yMin < 0} or
     * {@code yMin >= yMax} or any of {@code value}s has y
     * less than {@code yMin}.
     */
    public BarChart(List<XYValue> values, String xDescription, String yDescription, int yMin, int yMax, int space) {
        if (yMin < 0) {
            throw new IllegalArgumentException("Y min must be greater than or equal to zero.");
        } else if (!(yMax > yMin)) {
            throw new IllegalArgumentException("Y max must be greater than y min.");
        }

        values.forEach((current) -> {
            if (current.getY() < yMin) {
                throw new IllegalArgumentException("Y in a value cannot be less than y min.");
            }
        });

        this.values = values;
        this.xDescription = xDescription;
        this.yDescription = yDescription;
        this.yMin = yMin;
        this.yMax = (int) (space * Math.ceil((double) (yMax - yMin) / space));
        this.space = space;
    }

    /**
     * Provides a {@code List} of values.
     *
     * @return a {@code List} containing {@code XYValues}.
     */
    public List<XYValue> getValues() {
        return values;
    }

    /**
     * Provides the description on x axis.
     *
     * @return description on x axis.
     */
    public String getxDescription() {
        return xDescription;
    }

    /**
     * Provides the description on y axis.
     *
     * @return description on y axis.
     */
    public String getyDescription() {
        return yDescription;
    }

    /**
     * Provides the starting y.
     *
     * @return starting y.
     */
    public int getyMin() {
        return yMin;
    }

    /**
     * Provides the last y.
     *
     * @return final y.
     */
    public int getyMax() {
        return yMax;
    }

    /**
     * Provides the amount of numbers
     * skipped each step.
     *
     * @return amount of numbers skipped.
     */
    public int getSpace() {
        return space;
    }
}
