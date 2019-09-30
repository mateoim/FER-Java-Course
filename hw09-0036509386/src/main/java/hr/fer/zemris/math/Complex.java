package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a complex number.
 *
 * @author Mateo Imbrišak
 */

public class Complex {

    /**
     * Keeps the real part of the number.
     */
    private final double real;

    /**
     * Keeps the imaginary part of the number.
     */
    private final double imaginary;

    /**
     * Represents zero.
     */
    public static final Complex ZERO = new Complex(0,0);

    /**
     * Represents one.
     */
    public static final Complex ONE = new Complex(1,0);

    /**
     * Represents minus one.
     */
    public static final Complex ONE_NEG = new Complex(-1,0);

    /**
     * Represents imaginary one.
     */
    public static final Complex IM = new Complex(0,1);

    /**
     * Represents imaginary minus one.
     */
    public static final Complex IM_NEG = new Complex(0,-1);

    /**
     * Default constructor that assigns all values.
     *
     * @param real part of the number.
     * @param imaginary part of the number.
     */
    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Provides the real part of this
     * complex number.
     *
     * @return real part.
     */
    public double getReal() {
        return real;
    }

    /**
     * Provides the imaginary part
     * of this complex number.
     *
     * @return imaginary part.
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Calculates module of this complex number.
     *
     * @return module of this complex number.
     */
    public double module() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    /**
     * Multiplies this number with the given complex number.
     *
     * @param c number used in multiplication.
     *
     * @return result of multiplication as
     * a new {@code Complex} number.
     *
     * @throws NullPointerException if the given number is {@code null}.
     */
    public Complex multiply(Complex c) {
        Objects.requireNonNull(c);

        return new Complex(real * c.getReal() - imaginary * c.getImaginary(),
                real * c.getImaginary() + imaginary * c.getReal());
    }

    /**
     * Divides this number with the given complex number.
     *
     * @param c number used in the calculation.
     *
     * @return result of division as
     * a new {@code Complex} number.
     *
     * @throws NullPointerException if the given number is {@code null}.
     * @throws ArithmeticException if attempting to divide by zero.
     */
    public Complex divide(Complex c) {
        Objects.requireNonNull(c);

        double toDivide = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();

        if (toDivide == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }

        return new Complex((real * c.getReal() + imaginary * c.getImaginary()) / toDivide,
                (imaginary * c.getReal() - real * c.getImaginary()) / toDivide);
    }

    /**
     * Adds this number to the given complex number.
     *
     * @param c number to add.
     *
     * @return result as a new {@code Complex} number.
     *
     * @throws NullPointerException if the given number is {@code null}.
     */
    public Complex add(Complex c) {
        Objects.requireNonNull(c);

        return new Complex(real + c.getReal(), imaginary + c.getImaginary());
    }

    /**
     * Subtracts the given complex number from this number.
     *
     * @param c number to subtracted.
     *
     * @return result as a new {@code Complex} number.
     *
     * @throws NullPointerException if the given number is {@code null}.
     */
    public Complex sub(Complex c) {
        Objects.requireNonNull(c);

        return new Complex(real - c.getReal(), imaginary - c.getImaginary());
    }

    /**
     * Negates this number.
     *
     * @return negation of this number as
     * a new {@code Complex} number.
     */
    public Complex negete() {
        return new Complex(-1 * real, -1 * imaginary);
    }

    /**
     * Calculates n-th power of this number.
     *
     * @param n power being calculated.
     *
     * @return n-th power of this number as
     * a new {@code Complex} number.
     *
     * @throws IllegalArgumentException if provided
     * n is less than one.
     */
    public Complex power(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("N must be a positive number.");
        }

        return new Complex(Math.pow(module(), n) * Math.cos(n * Math.atan2(imaginary, real)),
                Math.pow(module(), n) * Math.sin(n * Math.atan2(imaginary, real)));
    }

    /**
     * Calculates roots of this number.
     *
     * @param n root being calculated.
     *
     * @return a {@code List} of roots.
     */
    public List<Complex> root(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("N must be a positive number.");
        }

        List<Complex> ret = new ArrayList<>(n);

        double newModule = Math.pow(module(), 1d / n);

        for (int i = 0; i < n; i++) {
            Complex toAdd = new Complex(newModule * Math.cos((Math.atan2(imaginary, real) + i * 2 * Math.PI) / n),
                    newModule * Math.sin((Math.atan2(imaginary, real) + i * 2 * Math.PI) / n));
            ret.add(toAdd);
        }

        return ret;
    }

    @Override
    public String toString() {
        return "(" + real + (imaginary >= 0 ? "+i" : "-i") + Math.abs(imaginary) + ")";
    }
}
