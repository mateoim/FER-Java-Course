package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A {@link Tool} used for drawing {@link hr.fer.zemris.java.hw17.jvdraw.objects.Line}s.
 *
 * @author Mateo Imbrišak
 */

public class LineTool implements Tool {

    /**
     * Used to check if this is the first click.
     */
    private boolean initialClick = true;

    /**
     * Keeps the current object.
     */
    private Line current = null;

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
    private Point currentPoint;

    /**
     * Default constructor that assigns all providers.
     *
     * @param model to be used.
     * @param fgColorProvider to be assigned.
     */
    public LineTool(DrawingModel model, IColorProvider fgColorProvider) {
        this.model = model;
        this.fgColorProvider = fgColorProvider;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (initialClick) {
            current = new Line();
            current.setStart(e.getPoint());
            initialClick = false;
            return;
        }

        initialClick = true;
        current.setColor(fgColorProvider.getCurrentColor());
        current.setEnd(e.getPoint());
        model.add(current);

        current = null;
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        currentPoint = e.getPoint();
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

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(fgColorProvider.getCurrentColor());
        g2d.drawLine(current.getStart().x, current.getStart().y, currentPoint.x, currentPoint.y);
    }
}
