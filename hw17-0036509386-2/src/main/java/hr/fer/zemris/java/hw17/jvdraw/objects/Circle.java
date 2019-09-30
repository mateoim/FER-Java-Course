package hr.fer.zemris.java.hw17.jvdraw.objects;

import hr.fer.zemris.java.hw17.jvdraw.editors.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A class that represents a drawn circle.
 *
 * @author Mateo Imbrišak
 */

public class Circle extends GeometricalObject {

    /**
     * Keeps the center.
     */
    private Point center;

    /**
     * Keeps the radius length.
     */
    private int radius;

    /**
     * Keeps the color.
     */
    private Color color;

    /**
     * Keeps all active listeners.
     */
    Set<GeometricalObjectListener> listeners;

    /**
     * Default constructor.
     */
    public Circle() {
        listeners = new HashSet<>();
    }

    /**
     * Constructor that assigns all values.
     *
     * @param center to be assigned.
     * @param radius to be assigned.
     * @param color to be assigned.
     */
    public Circle(Point center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;

        listeners = new HashSet<>();
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public void addGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.add(l);
    }

    @Override
    public void removeGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.remove(l);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new CircleEditor(this);
    }

    /**
     * Exports the circle to a format used to save the file.
     *
     * @return circle formatted to a format used for saving.
     */
    public String export() {
        return "CIRCLE " + center.x + " " + center.y + " " + radius + " " + color.getRed() + " " + color.getGreen() +
                " " + color.getBlue() + "\n";
    }

    /**
     * Creates a new circle from the given {@link String}.
     *
     * @param input containing info about the line.
     *
     * @return new circle from the given {@link String}.
     */
    public static Circle fromText(String input) {
        String[] parts = input.split(" ");

        try {
            Point center = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            int radius = Integer.parseInt(parts[3]);
            Color color = new Color(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));

            return new Circle(center, radius, color);
        } catch (NumberFormatException | IndexOutOfBoundsException exc) {
            throw new RuntimeException("Invalid format.");
        }
    }

    /**
     * Provides the {@link #center}.
     *
     * @return {@link #center}.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Assigns the {@link #center}.
     *
     * @param center to be assigned.
     */
    public void setCenter(Point center) {
        if (!center.equals(this.center)) {
            this.center = center;

            listeners.forEach((current) -> current.geometricalObjectChanged(this));
        }
    }

    /**
     * Provides the {@link #radius}.
     *
     * @return {@link #radius}.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Assigns the {@link #radius}.
     *
     * @param radius to be assigned.
     */
    public void setRadius(int radius) {
        if (radius != this.radius) {
            this.radius = radius;

            listeners.forEach((current) -> current.geometricalObjectChanged(this));
        }
    }

    /**
     * Provides the {@link #color}.
     *
     * @return {@link #color}.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Assigns the {@link #color}.
     *
     * @param color to be assigned.
     */
    public void setColor(Color color) {
        if (!color.equals(this.color)) {
            this.color = color;

            listeners.forEach((current) -> current.geometricalObjectChanged(this));
        }
    }

    @Override
    public String toString() {
        return "Circle (" + center.getX() + "," + center.getY() + "), " + radius;
    }
}
