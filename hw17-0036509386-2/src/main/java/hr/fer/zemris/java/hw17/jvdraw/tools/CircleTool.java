package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A {@link Tool} used to draw circles.
 *
 * @author Mateo Imbrišak
 */

public class CircleTool implements Tool {

    /**
     * Used to check if this is the first click.
     */
    private boolean initialClick = true;

    /**
     * Keeps the current object.
     */
    private Circle current = null;

    /**
     * Keeps the model.
     */
    private DrawingModel model;

    /**
     * Keeps the {@link IColorProvider}.
     */
    private IColorProvider fgColorProvider;

    /**
     * Keeps the last calculated radius.
     */
    private int currentRadius;

    /**
     * Default constructor that assigns all providers.
     *
     * @param model to be used.
     * @param fgColorProvider to be assigned.
     */
    public CircleTool(DrawingModel model, IColorProvider fgColorProvider) {
        this.model = model;
        this.fgColorProvider = fgColorProvider;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (initialClick) {
            current = new Circle();
            current.setCenter(e.getPoint());
            initialClick = false;
            return;
        }

        initialClick = true;
        current.setColor(fgColorProvider.getCurrentColor());

        Point point = e.getPoint();
        current.setRadius((int) Math.sqrt(Math.pow(current.getCenter().getX() - point.getX(), 2) +
                (Math.pow(current.getCenter().getY() - point.getY(), 2))));
        model.add(current);

        current = null;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (current == null) {
            return;
        }

        Point point = e.getPoint();
        currentRadius = (int) Math.sqrt(Math.pow(current.getCenter().getX() - point.getX(), 2) +
                (Math.pow(current.getCenter().getY() - point.getY(), 2)));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void paint(Graphics2D g2d) {
        if (current == null) {
            return;
        }

        g2d.setColor(fgColorProvider.getCurrentColor());
        g2d.drawOval(current.getCenter().x - currentRadius, (current.getCenter().y - currentRadius),
                 currentRadius * 2, currentRadius * 2);
    }
}
