package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

import java.awt.*;

/**
 * A visitor used to draw {@link hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject}s.
 *
 * @author Mateo Imbrišak
 */

public class GeometricalObjectPainter implements GeometricalObjectVisitor {

    /**
     * Keeps the {@link Graphics2D} used to draw the
     * {@link hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject}s.
     */
    private Graphics2D g2d;

    /**
     * Default constructor that assigns the {@link Graphics2D}.
     *
     * @param g2d to be assigned.
     */
    public GeometricalObjectPainter(Graphics2D g2d) {
        this.g2d = g2d;
    }

    @Override
    public void visit(Line line) {
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(line.getColor());
        g2d.drawLine(line.getStart().x, line.getStart().y, line.getEnd().x, line.getEnd().y);
    }

    @Override
    public void visit(Circle circle) {
        g2d.setColor(circle.getColor());
        g2d.drawOval(circle.getCenter().x - circle.getRadius(), circle.getCenter().y - circle.getRadius(),
                circle.getRadius() * 2, circle.getRadius() * 2);
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        g2d.setColor(filledCircle.getFillColor());
        g2d.fillOval(filledCircle.getCenter().x - filledCircle.getRadius(),
                filledCircle.getCenter().y - filledCircle.getRadius(),
                filledCircle.getRadius() * 2, filledCircle.getRadius() * 2);

        g2d.setColor(filledCircle.getColor());
        g2d.drawOval(filledCircle.getCenter().x - filledCircle.getRadius(),
                filledCircle.getCenter().y - filledCircle.getRadius(),
                filledCircle.getRadius() * 2, filledCircle.getRadius() * 2);
    }
}
