package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.*;

/**
 * An interface used to manage object's color
 * and its {@link ColorChangeListener}s.
 *
 * @author Mateo Imbrišak
 */

public interface IColorProvider {

    /**
     * Provides currently selected color.
     *
     * @return currently selected color.
     */
    Color getCurrentColor();

    /**
     * Adds the given {@link ColorChangeListener} to
     * the list of active listeners.
     *
     * @param l listener to be added.
     */
    void addColorChangeListener(ColorChangeListener l);

    /**
     * Removes the given {@link ColorChangeListener} from
     * the list of active listeners.
     *
     * @param l listener to be removed.
     */
    void removeColorChangeListener(ColorChangeListener l);
}
