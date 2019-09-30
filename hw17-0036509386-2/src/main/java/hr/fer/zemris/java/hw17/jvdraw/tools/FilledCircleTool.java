package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A {@link Tool} used to draw {@link hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle}s.
 *
 * @author Mateo Imbrišak
 */

public class FilledCircleTool implements Tool {

    /**
     * Used to check if this is the first click.
     */
    private boolean initialClick = true;

    /**
     * Keeps the current object.
     */
    private FilledCircle current = null;

    /**
     * Keeps the model.
     */
    private DrawingModel model;

    /**
     * Keeps the {@link IColorProvider} for foreground.
     */
    private IColorProvider fgColorProvider;

    /**
     * Keeps the {@link IColorProvider} for background.
     */
    private IColorProvider bgColorProvider;

    /**
     * Keeps the last calculated radius.
     */
    private int currentRadius;

    /**
     * Default constructor that assigns all providers.
     *
     * @param model to be used.
     * @param fgColorProvider to be assigned.
     * @param bgColorProvider to be assigned.
     */
    public FilledCircleTool(DrawingModel model, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
        this.model = model;
        this.fgColorProvider = fgColorProvider;
        this.bgColorProvider = bgColorProvider;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (initialClick) {
            current = new FilledCircle();
            current.setCenter(e.getPoint());
            initialClick = false;
            return;
        }

        initialClick = true;
        current.setColor(fgColorProvider.getCurrentColor());
        current.setFillColor(bgColorProvider.getCurrentColor());

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

        g2d.setColor(bgColorProvider.getCurrentColor());
        g2d.fillOval(current.getCenter().x - currentRadius, current.getCenter().y - currentRadius,
                (currentRadius) * 2, (currentRadius) * 2);

        g2d.setColor(fgColorProvider.getCurrentColor());
        g2d.drawOval(current.getCenter().x - currentRadius, (current.getCenter().y - currentRadius),
                currentRadius * 2, currentRadius * 2);
    }
}
