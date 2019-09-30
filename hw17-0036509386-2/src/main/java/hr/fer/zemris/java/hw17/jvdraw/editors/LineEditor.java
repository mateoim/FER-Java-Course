package hr.fer.zemris.java.hw17.jvdraw.editors;

import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

import javax.swing.*;
import java.awt.*;

/**
 * An {@link GeometricalObjectEditor} used for editing {@link Line}s.
 *
 * @author Mateo Imbrišak
 */

public class LineEditor extends GeometricalObjectEditor {

    /**
     * Keeps the edited {@link Line}.
     */
    private Line line;

    /**
     * Keeps the changed start.
     */
    private Point newStart;

    /**
     * Keeps the changed end.
     */
    private Point newEnd;

    /**
     * Keeps the changed color.
     */
    private Color newColor;

    /**
     * Keeps the x axis of starting point.
     */
    private JTextField startX;

    /**
     * Keeps the y axis of starting point.
     */
    private JTextField startY;

    /**
     * Keeps the x axis of ending point.
     */
    private JTextField endX;

    /**
     * Keeps the y axis of ending point.
     */
    private JTextField endY;

    /**
     * Keeps the value for color red.
     */
    private JTextField red;

    /**
     * Keeps the value for color green.
     */
    private JTextField green;

    /**
     * Keeps the value for color blue.
     */
    private JTextField blue;

    /**
     * Default constructor that assigns the {@link Line}.
     *
     * @param line to be assigned.
     */
    public LineEditor(Line line) {
        this.line = line;

        setLayout(new GridLayout(3, 4));

        add(new JLabel("Start x: "));
        startX = new JTextField();
        startX.setText(String.valueOf(line.getStart().x));
        add(startX);

        add(new JLabel("Start y: "));
        startY = new JTextField();
        startY.setText(String.valueOf(line.getStart().y));
        add(startY);

        add(new JLabel("End x: "));
        endX = new JTextField();
        endX.setText(String.valueOf(line.getEnd().x));
        add(endX);

        add(new JLabel("End y: "));
        endY = new JTextField();
        endY.setText(String.valueOf(line.getEnd().y));
        add(endY);

        add(new JLabel("Color: "));
        red = new JTextField();
        red.setText(String.valueOf(line.getColor().getRed()));
        add(red);

        green = new JTextField();
        green.setText(String.valueOf(line.getColor().getGreen()));
        add(green);

        blue = new JTextField();
        blue.setText(String.valueOf(line.getColor().getBlue()));
        add(blue);
    }

    @Override
    public void checkEditing() {
        try {
            int x = Integer.parseInt(startX.getText());
            int y = Integer.parseInt(startY.getText());

            if (x < 0 || y < 0) {
                throw new RuntimeException("Invalid data.");
            }

            newStart = new Point(x, y);

            x = Integer.parseInt(endX.getText());
            y = Integer.parseInt(endY.getText());

            if (x < 0 || y < 0) {
                throw new RuntimeException("Invalid data.");
            }

            newEnd = new Point(x, y);

            int r = Integer.parseInt(red.getText());
            int g = Integer.parseInt(green.getText());
            int b = Integer.parseInt(blue.getText());

            if (r < 0 || r > 255 | g < 0 || g > 255 || b < 0 || b > 255) {
                throw new RuntimeException("Invalid color.");
            }

            newColor = new Color(r, g, b);
        } catch (NumberFormatException | NullPointerException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void acceptEditing() {
        line.setStart(newStart);
        line.setEnd(newEnd);
        line.setColor(newColor);
    }
}
