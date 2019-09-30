package hr.fer.zemris.java.hw17.jvdraw.objects;

import hr.fer.zemris.java.hw17.jvdraw.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

import java.awt.*;

/**
 * A class that represents a filled drawn circle.
 *
 * @author Mateo Imbrišak
 */

public class FilledCircle extends Circle {

    /**
     * Keeps the fill color.
     */
    private Color fillColor;

    /**
     * Default constructor.
     */
    public FilledCircle() {}

    /**
     * Constructor that assigns all values.
     *
     * @param center to be assigned.
     * @param radius to be assigned.
     * @param color to be assigned.
     * @param fillColor to be assigned.
     */
    public FilledCircle(Point center, int radius, Color color, Color fillColor) {
        super(center, radius, color);
        this.fillColor = fillColor;
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new FilledCircleEditor(this);
    }

    @Override
    public String export() {
        String old = super.export().replace("\n", "");
        return "F" + old + " " + fillColor.getRed() + " " + fillColor.getGreen() + " " + fillColor.getBlue() + "\n";
    }

    @Override
    public String toString() {
        String old = super.toString().toLowerCase();
        return "Filled " + old + ", #" + Integer.toHexString(fillColor.getRGB()).substring(2).toUpperCase();
    }

    /**
     * Creates a new filled circle from the given {@link String}.
     *
     * @param input containing info about the line.
     *
     * @return new filled circle from the given {@link String}.
     */
    public static FilledCircle fromText(String input) {
        String[] parts = input.split(" ");

        try {
            Point center = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            int radius = Integer.parseInt(parts[3]);
            Color fg = new Color(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
            Color bg = new Color(Integer.parseInt(parts[7]), Integer.parseInt(parts[8]), Integer.parseInt(parts[9]));

            return new FilledCircle(center, radius, fg, bg);
        } catch (NumberFormatException | IndexOutOfBoundsException exc) {
            throw new RuntimeException("Invalid format.");
        }
    }

    /**
     * Provides the current {@link #fillColor}.
     *
     * @return current {@link #fillColor}.
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Assigns the given color.
     *
     * @param fillColor to be assigned.
     */
    public void setFillColor(Color fillColor) {
        if (!fillColor.equals(this.fillColor)) {
            this.fillColor = fillColor;

            super.listeners.forEach((current) -> current.geometricalObjectChanged(this));
        }
    }
}
