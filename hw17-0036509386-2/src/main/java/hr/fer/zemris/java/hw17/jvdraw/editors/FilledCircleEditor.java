package hr.fer.zemris.java.hw17.jvdraw.editors;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;

import javax.swing.*;
import java.awt.*;

/**
 * A {@link GeometricalObjectEditor} used to edit {@link FilledCircle}s.
 *
 * @author Mateo Imbrišak
 */

public class FilledCircleEditor extends GeometricalObjectEditor {

    /**
     * Keeps the edited {@link FilledCircle}.
     */
    private FilledCircle circle;

    /**
     * Keeps the changed center.
     */
    private Point newCenter;

    /**
     * Keeps the changed radius.
     */
    private Integer newRadius;

    /**
     * Keeps the changed color.
     */
    private Color newColor;

    /**
     * Keeps the changed background color.
     */
    private Color newBackgroundColor;

    /**
     * Keeps the x axis of center point.
     */
    private JTextField startX;

    /**
     * Keeps the y axis of starting point.
     */
    private JTextField startY;

    /**
     * Keeps the radius.
     */
    private JTextField radius;

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
     * Keeps the value for background color red.
     */
    private JTextField bRed;

    /**
     * Keeps the value for background color green.
     */
    private JTextField bGreen;

    /**
     * Keeps the value for background color blue.
     */
    private JTextField bBlue;

    /**
     * Default constructor that assigns the {@link Circle}.
     *
     * @param circle to be assigned.
     */
    public FilledCircleEditor(FilledCircle circle) {
        this.circle = circle;

        setLayout(new GridLayout(4, 4));

        add(new JLabel("Center x: "));
        startX = new JTextField();
        startX.setText(String.valueOf(circle.getCenter().x));
        add(startX);

        add(new JLabel("Center y: "));
        startY = new JTextField();
        startY.setText(String.valueOf(circle.getCenter().y));
        add(startY);

        add(new JLabel("Radius: "));
        radius = new JTextField();
        radius.setText(String.valueOf(circle.getRadius()));
        add(radius);
        add(new JLabel());
        add(new JLabel());

        add(new JLabel("Foreground color: "));
        red = new JTextField();
        red.setText(String.valueOf(circle.getColor().getRed()));
        add(red);

        green = new JTextField();
        green.setText(String.valueOf(circle.getColor().getGreen()));
        add(green);

        blue = new JTextField();
        blue.setText(String.valueOf(circle.getColor().getBlue()));
        add(blue);

        add(new JLabel("Background color: "));
        bRed = new JTextField();
        bRed.setText(String.valueOf(circle.getFillColor().getRed()));
        add(bRed);

        bGreen = new JTextField();
        bGreen.setText(String.valueOf(circle.getFillColor().getGreen()));
        add(bGreen);

        bBlue = new JTextField();
        bBlue.setText(String.valueOf(circle.getFillColor().getBlue()));
        add(bBlue);
    }

    @Override
    public void checkEditing() {
        try {
            int x = Integer.parseInt(startX.getText());
            int y = Integer.parseInt(startY.getText());

            if (x < 0 || y < 0) {
                throw new RuntimeException("Invalid data.");
            }

            newCenter = new Point(x, y);

            newRadius = Integer.parseInt(radius.getText());

            if (newRadius <= 0) {
                throw new RuntimeException("Invalid radius.");
            }

            int r = Integer.parseInt(red.getText());
            int g = Integer.parseInt(green.getText());
            int b = Integer.parseInt(blue.getText());

            if (r < 0 || r > 255 | g < 0 || g > 255 || b < 0 || b > 255) {
                throw new RuntimeException("Invalid color.");
            }

            newColor = new Color(r, g, b);

            r = Integer.parseInt(bRed.getText());
            g = Integer.parseInt(bGreen.getText());
            b = Integer.parseInt(bBlue.getText());

            if (r < 0 || r > 255 | g < 0 || g > 255 || b < 0 || b > 255) {
                throw new RuntimeException("Invalid color.");
            }

            newBackgroundColor = new Color(r, g, b);
        } catch (NumberFormatException | NullPointerException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void acceptEditing() {
        circle.setCenter(newCenter);
        circle.setRadius(newRadius);
        circle.setColor(newColor);
        circle.setFillColor(newBackgroundColor);
    }
}
