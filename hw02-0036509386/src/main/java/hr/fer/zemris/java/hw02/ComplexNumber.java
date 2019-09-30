package hr.fer.zemris.java.hw02;

import java.util.Objects;

/**
 * A class that represents a complex number.
 * An instance of a {@code ComplexNumber} is immutable
 * and every method returns a new instance of {@code ComplexNumber}.
 *
 * @author Mateo Imbrišak
 */

public class ComplexNumber {

    /**
     * Represents the real part of a {@code ComplexNumber}.
     */
    private double real;

    /**
     * Represents the imaginary part of a {@code ComplexNumber}.
     */
    private double imaginary;

    /**
     * Used when comparing parts of the {@code ComplexNumber}.
     */
    static final double TOLERABLE_DIFFERENCE = 1E-5; // not private since it's used in tests

    /**
     * The default constructor that creates a new instance of a
     * {@code ComplexNumber} from given real and imaginary parts.
     *
     * @param real part of the new {@code ComplexNumber}.
     * @param imaginary part of the new {@code ComplexNumber}.
     */
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Creates a new {@code ComplexNumber} with the given real part
     * and sets the imaginary part to 0.0;
     *
     * @param real part of the new {@code ComplexNumber}.
     *
     * @return a new {@code ComplexNumber} with the given real part.
     */
    public static ComplexNumber fromReal(double real) {
        return new ComplexNumber(real, 0);
    }

    /**
     * Creates a new {@code ComplexNumber} with the given imaginary part
     * and sets the real part to 0.0;
     *
     * @param imaginary part of the new {@code ComplexNumber}.
     *
     * @return a new {@code ComplexNumber} with the given imaginary part.
     */
    public static ComplexNumber fromImaginary(double imaginary) {
        return new ComplexNumber(0, imaginary);
    }

    /**
     * Creates a new instance of {@code ComplexNumber} based on
     * the given magnitude and angle.
     *
     * @param magnitude of the {@code ComplexNumber}.
     * @param angle of the {@code ComplexNumber}.
     *
     * @return new instance of {@code ComplexNumber} based on given arguments.
     */
    public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
        return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    }

    /**
     * Converts a valid {@code String} into a {@code ComplexNumber}.
     *
     * @param s {@code String} being converted.
     *
     * @return a new {@code ComplexNumber} based on the given {@code String}.
     *
     * @throws NumberFormatException if the given string cannot be converted
     * into a {@code ComplexNumber}.
     */
    public static ComplexNumber parse(String s) {
        if (s.charAt(s.length() - 1) == 'i') {
            if (s.length() == 1) { // if the argument is just "i"
                return new ComplexNumber(0, 1);
            }

            if (s.length() == 2) { // used if string is valid but doesn't contain any numbers
                switch (s.charAt(0)) {
                    case '+':
                        return new ComplexNumber(0, 1);
                    case '-':
                        return new ComplexNumber(0, -1);
                    default:
                        break;
                }
            }

            try {
                return new ComplexNumber(0, Double.parseDouble(s.substring(0, s.length() - 1)));
            } catch (NumberFormatException exc) {
                if (s.charAt(0) == '-' && (s.charAt(1) != '+' && s.charAt(1) != '-')) {
                    return findSeparator(s, 1);
                }
            }

            if (s.charAt(0) == '+') {
                return findSeparator(s, 1);
            } else {
                return findSeparator(s, 0);
            }

        }
        return new ComplexNumber(Double.parseDouble(s), 0);
    }

    /**
     * Used internally to find the separator
     * of the real and imaginary part of the {@code ComplexNumber}
     * in the parser and create a new instance of {@code ComplexNumber}
     * if possible.
     *
     * @param s containing the {@code ComplexNumber}.
     * @param startIndex index of the first numeric value in the {@code String}.
     *
     * @return a new {@code ComplexNumber} based on the given {@code String}
     * if a separator is found.
     *
     * @throws NumberFormatException if the separator is not found.
     */
    private static ComplexNumber findSeparator(String s, int startIndex) {
        int separator = -1;

        for (int i = startIndex; i < s.length() - 1; i++) {
            if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                separator = i;
                break;
            }
        }
        if (separator == -1) {
            throw new NumberFormatException("Separator not found.");
        }

        return new ComplexNumber(Double.parseDouble(s.substring(0, separator)),
                Double.parseDouble(s.substring(separator, s.length() - 1)));
    }

    /**
     * Returns the real part of the {@code ComplexNumber}.
     *
     * @return real part of the {@code ComplexNumber}.
     */
    public double getReal() {
        return real;
    }

    /**
     * Returns the imaginary part of the {@code ComplexNumber}.
     *
     * @return imaginary part of the {@code ComplexNumber}.
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Calculates the magnitude of the {@code ComplexNumber}.
     *
     * @return magnitude of the {@code VomplexNumber}.
     */
    public double getMagnitude() {
        return Math.sqrt(getReal() * getReal() + getImaginary() * getImaginary());
    }

    /**
     * Calculate the angle of the {@code ComplexNumber}.
     *
     * @return angle of the {@code ComplexNumber}.
     */
    public double getAngle() {
        double angle = Math.atan(getImaginary() / getReal());

//        if (angle < 0) {
//            angle += 2 * Math.PI;
//        }

        while (angle > 2 * Math.PI) {
            angle /= 2 * Math.PI;
        }

        return angle;
    }

    /**
     * Creates a new instance of {@code ComplexNumber}
     * by adding the real and imaginary parts of the
     * current and given {@code ComplexNumber}.
     *
     * @param c {@code ComplexNumber} to add to the current {@code ComplexNumber}.
     *
     * @return a new {@code ComplexNumber} with real and imaginary parts
     * calculated by adding current and give {@code ComplexNumber}s.
     */
    public ComplexNumber add(ComplexNumber c) {
        return new ComplexNumber(getReal() + c.getReal(), getImaginary() + c.getImaginary());
    }

    /**
     * Creates a new instance of {@code ComplexNumber}
     * by subtracting the real and imaginary parts of the
     * current and given {@code ComplexNumber}.
     *
     * @param c {@code ComplexNumber} to subtract from the current {@code ComplexNumber}.
     *
     * @return a new {@code ComplexNumber} with real and imaginary parts
     * calculated by subtracting given {@code ComplexNumber} from the current.
     */
    public ComplexNumber sub(ComplexNumber c) {
        return new ComplexNumber(getReal() - c.getReal(), getImaginary() - c.getImaginary());
    }

    /**
     * Creates a new instance of {@code ComplexNumber}
     * by multiplying the current and given {@code ComplexNumber}.
     *
     * @param c {@code ComplexNumber} to multiply the current one with.
     *
     * @return a new {@code ComplexNumber} calculated by multiplying
     * this one with a given one.
     */
    public ComplexNumber mul(ComplexNumber c) {
        return new ComplexNumber(getReal() * c.getReal() - getImaginary() * c.getImaginary(),
                getImaginary() * c.getReal() + getReal() * c.getImaginary());
    }

    /**
     * Creates a new instance of {@code ComplexNumber}
     * by dividing this one by the given one.
     *
     * @param c {@code ComplexNumber} to divide by.
     *
     * @return a new {@code ComplexNumber} calculated by dividing
     * this one with a given one.
     */
    public ComplexNumber div(ComplexNumber c) {
        double rootDiv = getMagnitude() / c.getMagnitude();
        return new ComplexNumber(rootDiv * (Math.cos(getAngle() - c.getAngle())),
                rootDiv * (Math.sin(getAngle() - c.getAngle())));

//        return new ComplexNumber((getReal() * c.getReal() + getImaginary() * c.getImaginary())
//                / (c.getMagnitude() * c.getMagnitude()),
//                (-getReal() * c.getImaginary() + getImaginary() * c.getReal())
//                        / (c.getMagnitude() * c.getMagnitude()));
    }

    /**
     * Calculates current {@code ComplexNumber} to the power of n.
     *
     * @param n exponent which to calculate.
     *
     * @return a new {@code ComplexNumber} to the power of n of
     * the current one.
     */
    public ComplexNumber power(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be a positive number.");
        }

        return new ComplexNumber(Math.pow(getMagnitude(), n) * Math.cos(getAngle() * n),
                Math.pow(getMagnitude(), n) * Math.sin(getAngle() * n));
    }

    /**
     * Calculates n roots of this {@code ComplexNumber}.
     *
     * @param n roots to calculate.
     *
     * @return an array of roots as {@code ComplexNumber}s.
     */
    public ComplexNumber[] root(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be a positive number greater than 0.");
        }

        ComplexNumber[] roots = new ComplexNumber[n];

        for (int i = 0; i < n; i++) {
            double nRootMagnitude = Math.pow(getMagnitude(), 1.0/n);

            roots[i] = new ComplexNumber(nRootMagnitude * Math.cos((getAngle() + i * 2 * Math.PI) / n),
                    nRootMagnitude * Math.sin((getAngle() + i * 2 * Math.PI) / n));
        }

        return roots;
    }

    /**
     * Checks if two instances of {@code ComplexNumber} represent
     * the same  number with a difference tolerance of 1E-5.
     *
     * @param o a {@code ComplexNumber} being compared to this one.
     *
     * @return {@code true} if they are the same number number,
     * otherwise {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Math.abs(that.getReal() - getReal()) < TOLERABLE_DIFFERENCE &&
                Math.abs(that.getImaginary() - getImaginary()) < TOLERABLE_DIFFERENCE;
    }

    /**
     * Generates hash code for this {@code ComplexNumber}.
     *
     * @return hash code of this {@code ComplexNumber}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getReal(), getImaginary());
    }

    /**
     * Provides a representation of {@code ComplexNumber} as {@code String}.
     *
     * @return representation of this {@code ComplexNumber} as a {@code String}.
     */
    @Override
    public String toString() {
        if (getImaginary() == 0) {
            return Double.toString(getReal());
        }

        if (getImaginary() != 0 && getReal() == 0) {
            return getImaginary() + "i";
        }

        if (getImaginary() > 0) {
            return getReal() + "+" + getImaginary() + "i";
        }

        return getReal() + getImaginary() + "i";
    }
}
