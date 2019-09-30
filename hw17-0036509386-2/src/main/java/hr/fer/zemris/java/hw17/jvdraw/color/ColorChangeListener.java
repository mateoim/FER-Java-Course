package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.*;

/**
 * A listener used to signal change of color.
 *
 * @author Mateo Imbrišak
 */

public interface ColorChangeListener {

    /**
     * Used to signal that the color has been changed.
     *
     * @param source that changed the color.
     * @param oldColor that was changed.
     * @param newColor that has been set.
     */
    void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
