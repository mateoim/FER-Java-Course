package hr.fer.zemris.java.hw17.jvdraw.components;

import hr.fer.zemris.java.hw17.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;

import javax.swing.*;
import java.awt.*;

/**
 * A {@link JLabel} used to display
 * info about currently selected colors.
 *
 * @author Mateo Imbrišak
 */

public class JColorInfo extends JLabel implements ColorChangeListener {

    /**
     * Keeps the foreground {@link IColorProvider}.
     */
    private IColorProvider fgColorProvider;

    /**
     * Keeps the background {@link IColorProvider}.
     */
    private IColorProvider bgColorProvider;

    /**
     * Default constructor that assigns all values.
     *
     * @param fgColorProvider to be assigned.
     * @param bgColorProvider to be assigned.
     */
    public JColorInfo(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
        this.fgColorProvider = fgColorProvider;
        this.bgColorProvider = bgColorProvider;

        fgColorProvider.addColorChangeListener(this);
        bgColorProvider.addColorChangeListener(this);

        changeText();
    }

    /**
     * Used internally to update text.
     */
    private void changeText() {
        Color fgColor = fgColorProvider.getCurrentColor();
        Color bgColor = bgColorProvider.getCurrentColor();

        setText("Foreground color: (" + fgColor.getRed() + ", " + fgColor.getGreen() + ", " + fgColor.getBlue() + ")," +
                " background color: (" + bgColor.getRed() + ", " + bgColor.getGreen() + ", " + bgColor.getBlue() + ").");
    }

    @Override
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
        changeText();
    }
}
