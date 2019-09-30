package hr.fer.zemris.math;

import java.util.Objects;

/**
 * A class that represents a 3D vector.
 *
 * @author Mateo Imbrišak
 */

public class Vector3 {

    /**
     * Keeps the x coordinate.
     */
    private final double x;

    /**
     * Keeps the y coordinate.
     */
    private final double y;

    /**
     * Keeps the z coordinate.
     */
    private final double z;

    /**
     * Default constructor that assigns all values.
     *
     * @param x coordinate of the vector.
     * @param y coordinate of the vector.
     * @param z coordinate of the vector.
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculates the length of the vector.
     *
     * @return length of the vector.
     */
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Calculates the normalized vector
     * for this vector.
     *
     * @return normalized vector.
     */
    public Vector3 normalized() {
        return new Vector3(x / norm(), y / norm(), z / norm());
    }

    /**
     * Adds the given vector to this one.
     *
     * @param other vector to be added.
     *
     * @return a new {@code Vector3} created by
     * adding the given vector to this one.
     *
     * @throws NullPointerException if the given
     * vector is {@code null}.
     */
    public Vector3 add(Vector3 other) {
        Objects.requireNonNull(other);

        return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
    }

    /**
     * Subtracts the given vector from this one.
     *
     * @param other vector to be subtracted.
     *
     * @return a new {@code Vector3} created by
     * subtracting the given vector from this one.
     *
     * @throws NullPointerException if the given
     * vector is {@code null}.
     */
    public Vector3 sub(Vector3 other) {
        Objects.requireNonNull(other);

        return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
    }

    /**
     * Calculates the dot product of this vector
     * and given vector.
     *
     * @param other vector used to calculate the product.
     *
     * @return dot product.
     *
     * @throws NullPointerException if the given
     * vector is {@code null}.
     */
    public double dot(Vector3 other) {
        Objects.requireNonNull(other);

        return x * other.getX() + y * other.getY() + z * other.getZ();
    }

    /**
     * Creates a new vector that is a cross product
     * of this vector and the given vector.
     *
     * @param other vector used for the product.
     *
     * @return cross product vector.
     *
     * @throws NullPointerException if the given
     * vector is {@code null}.
     */
    public Vector3 cross(Vector3 other) {
        return new Vector3(y * other.getZ() - z * other.getY(),
                z * other.getX() - x * other.getZ(),
                x * other.getY() - y * other.getX());
    }

    /**
     * Creates a new vector by scaling this one.
     *
     * @param s amount to scale the vector.
     *
     * @return a new scaled vector.
     */
    public Vector3 scale(double s) {
        return new Vector3(x * s, y * s, z * s);
    }

    /**
     * Calculates the cosine of angle between
     * this vector and given one.
     *
     * @param other vector used in the calculation.
     *
     * @return cosine of angle between two vectors.
     *
     * @throws NullPointerException if the given
     * vector is {@code null}.
     */
    public double cosAngle(Vector3 other) {
        Objects.requireNonNull(other);

        return dot(other) / (norm() * other.norm());
    }

    /**
     * Provides the x coordinate.
     *
     * @return x coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Provides the y coordinate.
     *
     * @return y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Provides the z coordinate.
     *
     * @return z coordinate.
     */
    public double getZ() {
        return z;
    }

    /**
     * Creates an array of {@code double}s with
     * three elements each representing a coordinate
     * of this vector.
     *
     * @return an array of {@code double}s containing
     * this vector's coordinates.
     */
    public double[] toArray() {
        return new double[] {x, y, z};
    }

    @Override
    public String toString() {
        return "(" + String.format("%f", x) + ", " +
                String.format("%f", y) + ", " +
                String.format("%f", z) + ")";

    }
}
