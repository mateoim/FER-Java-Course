package hr.fer.zemris.java.hw17.jvdraw.editors;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;

import javax.swing.*;
import java.awt.*;

/**
 * A {@link GeometricalObjectEditor} used to edit {@link Circle}s.
 *
 * @author Mateo Imbrišak
 */

public class CircleEditor extends GeometricalObjectEditor {

    /**
     * Keeps the edited {@link Circle}.
     */
    private Circle circle;

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
     * Default constructor that assigns the {@link Circle}.
     *
     * @param circle to be assigned.
     */
    public CircleEditor(Circle circle) {
        this.circle = circle;

        setLayout(new GridLayout(3, 4));

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

        add(new JLabel("Color: "));
        red = new JTextField();
        red.setText(String.valueOf(circle.getColor().getRed()));
        add(red);

        green = new JTextField();
        green.setText(String.valueOf(circle.getColor().getGreen()));
        add(green);

        blue = new JTextField();
        blue.setText(String.valueOf(circle.getColor().getBlue()));
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
        } catch (NumberFormatException | NullPointerException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void acceptEditing() {
        circle.setCenter(newCenter);
        circle.setRadius(newRadius);
        circle.setColor(newColor);
    }
}
