package hr.fer.zemris.java.hw17.jvdraw.objects;

import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A class that represents a line.
 *
 * @author Mateo Imbrišak
 */

public class Line extends GeometricalObject {

    /**
     * Keeps the starting point.
     */
    private Point start;

    /**
     * Keeps the ending point.
     */
    private Point end;

    /**
     * Keeps the color.
     */
    private Color color;

    /**
     * Keeps all active listeners.
     */
    private Set<GeometricalObjectListener> listeners;

    /**
     * Default constructor.
     */
    public Line() {
        listeners = new HashSet<>();
    }

    /**
     * Constructor that assigns all values.
     *
     * @param start to be assigned.
     * @param end to be assigned.
     * @param color to be assigned.
     */
    public Line(Point start, Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;

        listeners = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Line (" + start.getX() + "," + start.getY() + ")-(" + end.getX() + "," + end.getY() + ")";
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
        return new LineEditor(this);
    }

    /**
     * Exports the line to a format used to save the file.
     *
     * @return line formatted to a format used for saving.
     */
    public String export() {
        return "LINE " + start.x + " " + start.y + " " + end.x + " " + end.y + " " +  color.getRed()
                + " " + color.getGreen() + " " + color.getBlue() + "\n";
    }

    /**
     * Creates a new line from the given {@link String}.
     *
     * @param input containing info about the line.
     *
     * @return new line from the given {@link String}.
     */
    public static Line fromText(String input) {
        String[] parts = input.split(" ");

        try {
            Point start = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            Point end = new Point(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
            Color color = new Color(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), Integer.parseInt(parts[7]));

            return new Line(start, end, color);
        } catch (NumberFormatException | IndexOutOfBoundsException exc) {
            throw new RuntimeException("Invalid format.");
        }
    }

    /**
     * Provides the {@link #start}.
     *
     * @return {@link #start}.
     */
    public Point getStart() {
        return start;
    }

    /**
     * Assigns the {@link #start}.
     *
     * @param start to be assigned.
     */
    public void setStart(Point start) {
        if (!start.equals(this.start)) {
            this.start = start;

            listeners.forEach((current) -> current.geometricalObjectChanged(this));
        }
    }

    /**
     * Provides the {@link #end}.
     *
     * @return {@link #end}.
     */
    public Point getEnd() {
        return end;
    }

    /**
     * Assigns the {@link #end}.
     *
     * @param end to be assigned.
     */
    public void setEnd(Point end) {
        if (!end.equals(this.end)) {
            this.end = end;

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
}
