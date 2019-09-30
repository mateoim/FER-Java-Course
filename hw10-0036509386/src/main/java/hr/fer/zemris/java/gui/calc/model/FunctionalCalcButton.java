package hr.fer.zemris.java.gui.calc.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A button that adds an action listener to itself.
 *
 * @author Mateo Imbrišak
 */

public class FunctionalCalcButton extends JButton {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor that assigns a name and adds
     * an {@code ActionListener}.
     *
     * @param value text to be assigned.
     * @param first {@code ActionListener} to be added.
     */
    public FunctionalCalcButton(String value, ActionListener first) {
        super(value);
        addActionListener(first);
        setBackground(new Color(120, 150,150));
    }
}
