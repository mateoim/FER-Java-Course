package hr.fer.zemris.java.hw17.jvdraw.components;

import hr.fer.zemris.java.hw17.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * A clickable {@link JComponent} that
 * keeps and displays info about it's
 * selected color.
 *
 * @author Mateo Imbrišak
 */

public class JColorArea extends JComponent implements IColorProvider {

    /**
     * Keeps the currently selected color.
     */
    private Color selectedColor;

    /**
     * Keeps all active {@link ColorChangeListener}s.
     */
    private Set<ColorChangeListener> listeners;

    /**
     * Keeps the default size.
     */
    private static final int DEFAULT_SIZE = 15;

    /**
     * Default constructor that assigns {@link #selectedColor}.
     *
     * @param selectedColor to be assigned.
     */
    public JColorArea(Color selectedColor) {
        this.selectedColor = selectedColor;
        this.setBackground(selectedColor);

        listeners = new HashSet<>();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color selected = JColorChooser.showDialog(JColorArea.this, "Select new color", selectedColor);

                if (selected == null) {
                    return;
                }

                Color old = JColorArea.this.selectedColor;
                JColorArea.this.selectedColor = selected;

                listeners.forEach((current) -> current.newColorSelected(JColorArea.this, old, selected));

                repaint();
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(selectedColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public Color getCurrentColor() {
        return selectedColor;
    }

    @Override
    public void addColorChangeListener(ColorChangeListener l) {
        listeners.add(l);
    }

    @Override
    public void removeColorChangeListener(ColorChangeListener l) {
        listeners.remove(l);
    }
}
