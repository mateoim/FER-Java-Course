package hr.fer.zemris.java.gui.calc.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A calculator button that represents a number.
 *
 * @author Mateo Imbrišak
 */

public class NumericCalcButton extends JButton {
    private static final long serialVersionUID = 1L;

    /**
     * Keeps the value of the button.
     */
    private int value;

    /**
     * Default constructor that assigns a name and adds
     * an {@code ActionListener}.
     *
     * @param value of the number on the button.
     * @param first {@code ActionListener} to be added.
     */
    public NumericCalcButton(int value, ActionListener first) {
        this.value = value;
        super.setText(String.valueOf(value));
        addActionListener(first);
        setBackground(new Color(120, 150,150));
    }

    /**
     * Provides the value of this button's number.
     *
     * @return button's value.
     */
    public int getValue() {
        return value;
    }
}
