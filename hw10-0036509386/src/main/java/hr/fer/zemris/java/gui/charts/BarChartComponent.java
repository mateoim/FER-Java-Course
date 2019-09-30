package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;
import java.util.OptionalInt;

/**
 * A component that represents a bar chart.
 *
 * @author Mateo Imbrišak
 */

public class BarChartComponent extends JComponent {
    private static final long serialVersionUID = 1L;

    /**
     * Space used between description and axis.
     */
    private static final int SPACE = 5;

    /**
     * Chart containing the data.
     */
    private BarChart chart;

    /**
     * Default constructor that assigns the {@link #chart}.
     *
     * @param chart to be assigned.
     */
    public BarChartComponent(BarChart chart) {
        Objects.requireNonNull(chart);

        this.chart = chart;
    }

    /**
     * Draws the necessary components.
     *
     * @param g used to draw the components.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        Graphics2D g2d = (Graphics2D) g;

        setDescriptionFont(g2d);

        int yOrigin = SPACE + g2d.getFontMetrics().getHeight() + SPACE;
        int xOrigin = yOrigin;

        setNumbersFont(g2d);

        yOrigin += g2d.getFontMetrics().stringWidth(String.valueOf(chart.getyMax())) + 2 * SPACE;
        xOrigin += g2d.getFontMetrics().getHeight() + 2 * SPACE;
        yOrigin = getHeight() - yOrigin;

        setAxisStroke(g2d);

        g2d.drawLine(xOrigin, yOrigin, getWidth() - SPACE, yOrigin); // x axis
        g2d.drawLine(xOrigin, yOrigin, xOrigin, SPACE); // y axis

        // arrow on x axis
        int arrowY = yOrigin - 5;
        for (int arrowX = getWidth() - SPACE - 5, counter = 10; arrowX < getWidth() - SPACE; arrowX++) {
            g2d.drawLine(arrowX, arrowY, arrowX, arrowY + counter);
            arrowY++;
            counter -= 2;
        }

        // arrow on y axis
        int arrowX = xOrigin - 5;
        for (int arrowTop = SPACE + 5, counter = 10; arrowTop > SPACE; arrowTop--) {
            g2d.drawLine(arrowX, arrowTop, arrowX + counter, arrowTop);
            arrowX++;
            counter -= 2;
        }

        int amountOfY = (chart.getyMax() - chart.getyMin()) / chart.getSpace();
        int ySize = (int) Math.round((double) (SPACE + 10 - yOrigin) / amountOfY);

        OptionalInt optionalMaxX = chart.getValues().stream().mapToInt(XYValue::getX).max();
        int xMax = optionalMaxX.isEmpty() ? 0 : optionalMaxX.getAsInt();
        int xSize = (int) Math.round((double) (getWidth() - (SPACE + 10) - xOrigin) / xMax);

        // fill y axis
        for (int y = chart.getyMin(), yPos = yOrigin; y <= chart.getyMax(); y += chart.getSpace()) {
            setAxisStroke(g2d);
            g2d.drawLine(xOrigin, yPos, xOrigin - 5, yPos);

            if (y != chart.getyMin()) {
                setGridColor(g2d);
                g2d.drawLine(xOrigin + 2, yPos, xMax * xSize + xOrigin, yPos);
            }

            setNumbersFont(g2d);
            g2d.drawString(String.valueOf(y), xOrigin - 10 - g2d.getFontMetrics().stringWidth(String.valueOf(y)),
                    yPos + g2d.getFontMetrics().getHeight() / 3); // for some reason /3 looks more centered than /2
            yPos += ySize;
        }

        // fill x axis
        for (int x = 1, xPos = xOrigin; x <= xMax; x++) {
            setAxisStroke(g2d);
            g2d.drawLine(xPos, yOrigin, xPos, yOrigin + 5);

            if (x != 1) {
                setGridColor(g2d);
                g2d.drawLine(xPos, yOrigin - 2, xPos, yOrigin + ySize * chart.getyMax());
            }

            setNumbersFont(g2d);
            g2d.drawString(String.valueOf(x),
                    xPos + (xSize + g2d.getFontMetrics().stringWidth(String.valueOf(x))) / 2,
                    yOrigin + 10 + g2d.getFontMetrics().getHeight() / 2);
            xPos += xSize;

            if (x == xMax) {
                setGridColor(g2d);
                g2d.drawLine(xPos, yOrigin - 2, xPos, yOrigin + ySize * chart.getyMax());
            }
        }

        // draw columns
        final int originX = xOrigin + 1;
        final int originY = yOrigin - 1;
        g2d.setColor(new Color(255, 149, 58));
        chart.getValues().forEach((value) -> g2d.fillRect(originX + (value.getX() - 1) * xSize,
                originY + ySize * ((value.getY() - chart.getyMin()) / chart.getSpace()),
                xSize - 1, -1 * ySize * ((value.getY() - chart.getyMin()) / chart.getSpace())));

        setDescriptionFont(g2d);

        g2d.drawString(chart.getxDescription(),
                ((getWidth() - SPACE - xOrigin) / 2) - g2d.getFontMetrics().stringWidth(chart.getxDescription()) / 3,
                getHeight() - 2 * SPACE);

        AffineTransform defaultTransform = g2d.getTransform();
        AffineTransform toRotate = new AffineTransform();
        toRotate.rotate(3 * Math.PI / 2);

        g2d.transform(toRotate);

        g2d.drawString(chart.getyDescription(),
                (SPACE - 5 - yOrigin) / 2 - g2d.getFontMetrics().stringWidth(chart.getyDescription()) / 2,
                xOrigin - 2 * SPACE - 2 - g2d.getFontMetrics().stringWidth(String.valueOf(chart.getyMax())));

        g2d.setTransform(defaultTransform);
    }

    /**
     * Used to set the font and color used for numbers.
     *
     * @param g2d used to draw the elements.
     */
    private void setNumbersFont(Graphics2D g2d) {
        g2d.setFont(new Font("Helvetica", Font.BOLD, 10));
        g2d.setColor(Color.BLACK);
    }

    /**
     * Used to set the font and color used for description.
     *
     * @param g2d used to draw the elements.
     */
    private void setDescriptionFont(Graphics2D g2d) {
        g2d.setFont(new Font("Helvetica", Font.PLAIN, 16));
        g2d.setColor(Color.BLACK);
    }

    /**
     * Used to set the color and width used for drawing axis.
     *
     * @param g2d used to draw the elements.
     */
    private void setAxisStroke(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.GRAY);
    }

    /**
     * Used to set the color and width used for drawing the grid.
     *
     * @param g2d used to draw the elements.
     */
    private void setGridColor(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(255, 192, 112));
    }
}
