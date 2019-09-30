package hr.fer.zemris.math;

import java.util.Objects;

/**
 * A simple class that represents a vector in 2D space.
 * Presumed origin point is (0, 0) and translation does
 * not change that point.
 *
 * @author Mateo Imbrišak
 */

public class Vector2D {

    /**
     * Position of the {@code Vector2D} on x-axis.
     */
    private double x;

    /**
     * Position of the {@code Vector2D} on y-axis.
     */
    private double y;

    /**
     * Maximum tolerable difference when comparing two {@code Vector2D}s.
     */
    private static final double TOLERABLE_DIFFERENCE = 1E-6;

    /**
     * Default constructor that initializes x and y
     * positions of a new {@code Vector2D}.
     *
     * @param x position on the x-axis.
     * @param y position on the y-axis.
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the {@code Vector2D}'s position on
     * the x-axis.
     *
     * @return position in the x-axis.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the {@code Vector2D}'s position on
     * the y-axis.
     *
     * @return position in the y-axis.
     */
    public double getY() {
        return y;
    }

    /**
     * Translates the current {@code Vector2D} using
     * the given one. Does not change origin point.
     *
     * @param offset a {@code Vector2D} used to
     *               translate this one.
     */
    public void translate(Vector2D offset) {
        x += offset.getX();
        y += offset.getY();
    }

    /**
     * Creates a new {@code Vector2D} by translating this one
     * using the given one.
     *
     * @param offset a {@code Vector2D} used to
     *               create the translated one.
     *
     * @return a new {@code Vector2D} based on this one
     * and the given one. Keeps the origin point in (0, 0).
     */
    public Vector2D translated(Vector2D offset) {
        return new Vector2D(x + offset.getX(), y + offset.getY());
    }

    /**
     * Rotates this {@code Vector2D} by a given angle.
     *
     * @param angle in rad by which to rotate this vector.
     */
    public void rotate(double angle) {
        double newX = x * Math.cos(angle) - y * Math.sin(angle);
        double newY = y * Math.cos(angle) + x * Math.sin(angle);

        x = newX;
        y = newY;
    }

    /**
     * Creates a new instance of a {@code Vector2D} created
     * by rotating this one by a given angle.
     *
     * @param angle in rad by which to rotate this vector.
     *
     * @return a new {@code Vector2D} created by rotating
     * this one.
     */
    public Vector2D rotated(double angle) {
        return new Vector2D(x * Math.cos(angle) - y * Math.sin(angle),
                y * Math.cos(angle) + x * Math.sin(angle));
    }

    /**
     * Scales this vector with a given scaler.
     *
     * @param scaler you want to scale this vector
     *               with.
     */
    public void scale(double scaler) {
        x *= scaler;
        y *= scaler;
    }

    /**
     * Creates a new instance of {@code vector2D} by
     * scaling this one with the given scaler.
     *
     * @param scaler you want to scale the vector with.
     *
     * @return a new {@code Vector2D} created by scaling
     * this one.
     */
    public Vector2D scaled(double scaler) {
        return new Vector2D(x * scaler, y * scaler);
    }

    /**
     * Creates a new copy of this {@code Vector2D}.
     *
     * @return a new instance of {@code Vector2D} with
     * same parameters as this one.
     */
    public Vector2D copy() {
        return new Vector2D(getX(), getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return Math.abs(vector2D.x - x) <= TOLERABLE_DIFFERENCE &&
                Math.abs(vector2D.y - y) <= TOLERABLE_DIFFERENCE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
