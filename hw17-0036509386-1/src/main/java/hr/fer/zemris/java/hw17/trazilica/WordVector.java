package hr.fer.zemris.java.hw17.trazilica;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a vector of
 * any dimension, used for TF-IDF document search.
 *
 * @author Mateo Imbrišak
 */

public class WordVector {

    /**
     * Keeps all values.
     */
    private List<Double> values;

    /**
     * Default constructor that creates
     * an empty vector.
     */
    public WordVector() {
        this.values = new ArrayList<>();
    }

    /**
     * Adds the given value as the last
     * element of the vector.
     *
     * @param value to be added.
     */
    public void put (double value) {
        values.add(value);
    }

    /**
     * Calculates the dot product between this vector and
     * the given vector.
     *
     * @param other used to calculate dot product.
     *
     * @return dot product between the vectors.
     */
    public double dotProduct(WordVector other) {
        if (values.size() != other.values.size()) {
            throw new RuntimeException("Vectors must be same dimension.");
        }

        double product = 0;

        for (int i = 0, length = values.size(); i < length; i++) {
            product += values.get(i) * other.values.get(i);
        }

        return product;
    }

    /**
     * Calculates the norm of this vector.
     *
     * @return this vector's norm.
     */
    public double norm() {
        double squareSum = 0;

        for (double value : values) {
            squareSum += value * value;
        }

        return Math.sqrt(squareSum);
    }

    /**
     * Calculates the dot product between this vector and
     * the given vector.
     *
     * @param other used to calculate dot product.
     *
     * @return dot product between the vectors.
     */
    public double cosine(WordVector other) {
        return dotProduct(other) / (norm() * other.norm());
    }
}
