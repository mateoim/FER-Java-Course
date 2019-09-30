package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

import java.awt.*;

/**
 * A {@link GeometricalObjectVisitor} used to
 * get the bounds of actual image.
 *
 * @author Mateo Imbrišak
 */

public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

    /**
     * Used to check if the values should be inherited.
     */
    private boolean first = true;

    /**
     * Keeps the closest x point to {@code 0}.
     */
    private int x = 0;

    /**
     * Keeps the closest y point to {@code 0}.
     */
    private int y = 0;

    /**
     * Keeps the farthest x coordinate.
     */
    private int bottomX = 0;

    /**
     * Keeps the farthest y coordinate.
     */
    private int bottomY = 0;

    @Override
    public void visit(Line line) {
        if (first) {
            first = false;

            x = Math.min(line.getStart().x, line.getEnd().x);
            y = Math.min(line.getStart().y, line.getEnd().y);
            bottomX = Math.max(line.getStart().x, line.getEnd().x);
            bottomY = Math.max(line.getStart().y, line.getEnd().y);
            return;
        }

        int x = Math.min(line.getStart().x, line.getEnd().x);
        int y = Math.min(line.getStart().y, line.getEnd().y);
        int width = Math.max(line.getStart().x, line.getEnd().x);
        int height = Math.max(line.getStart().y, line.getEnd().y);

        checkDimensions(x, y, width, height);
    }

    @Override
    public void visit(Circle circle) {
        checkCircles(circle);
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        checkCircles(filledCircle);
    }

    /**
     * Provides a {@link Rectangle} of optimal dimensions.
     *
     * @return a {@link Rectangle} of optimal dimensions.
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, bottomX - x, bottomY - y);
    }

    /**
     * Used internally to visit {@link Circle}s.
     *
     * @param circle to be visited.
     */
    private void checkCircles(Circle circle) {
        if (first) {
            first = false;

            x = circle.getCenter().x - circle.getRadius();
            y = circle.getCenter().y - circle.getRadius();
            bottomX = circle.getCenter().x + circle.getRadius();
            bottomY = circle.getCenter().y + circle.getRadius();
            return;
        }

        int x = circle.getCenter().x - circle.getRadius();
        int y = circle.getCenter().y - circle.getRadius();
        int width = circle.getCenter().x + circle.getRadius();
        int height = circle.getCenter().y + circle.getRadius();

        checkDimensions(x, y, width, height);
    }

    /**
     * Checks new dimensions for optimum.
     *
     * @param newX to be checked.
     * @param newY to be checked.
     * @param newWidth to be checked.
     * @param newHeight to be checked.
     */
    private void checkDimensions(int newX, int newY, int newWidth, int newHeight) {
        x = Math.min(x, newX);
        y = Math.min(y, newY);

        bottomX = Math.max(bottomX, newWidth);
        bottomY = Math.max(bottomY, newHeight);
    }
}
