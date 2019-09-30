package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * An interface used to model all tools.
 *
 * @author Mateo Imbrišak
 */

public interface Tool {

    /**
     * Represents a click.
     *
     * @param e {@link MouseEvent}.
     */
    void mousePressed(MouseEvent e);

    /**
     * Represents release of a click.
     *
     * @param e {@link MouseEvent}.
     */
    void mouseReleased(MouseEvent e);

    /**
     * Represents a click.
     *
     * @param e {@link MouseEvent}.
     */
    void mouseClicked(MouseEvent e);

    /**
     * Represents mouse movement.
     *
     * @param e {@link MouseEvent}.
     */
    void mouseMoved(MouseEvent e);

    /**
     * Represents mouse movement.
     *
     * @param e {@link MouseEvent}.
     */
    void mouseDragged(MouseEvent e);

    /**
     * Paints the mouse movement.
     *
     * @param g2d used to do the painting.
     */
    void paint(Graphics2D g2d);
}
