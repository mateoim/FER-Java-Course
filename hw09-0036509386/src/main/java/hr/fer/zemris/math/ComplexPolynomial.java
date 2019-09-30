package hr.fer.zemris.math;

import java.util.*;

/**
 * A class that represents a polynomial.
 *
 * @author Mateo Imbrišak
 */

public class ComplexPolynomial {

    /**
     * Keeps the factors.
     */
    private final List<Complex> factors;

    /**
     * Default constructor that assigns the factors.
     *
     * @param factors to be assigned.
     *
     * @throws NullPointerException if any factor
     * is {@code null}.
     */
    public ComplexPolynomial(Complex ... factors) {
        this.factors = Arrays.asList(factors);

        this.factors.forEach(Objects::requireNonNull);
    }

    /**
     * Provides the order of this polynomial.
     *
     * @return order of this polynomial.
     */
    public short order() {
        return (short) (factors.size() - 1);
    }

    /**
     * Multiplies this polynomial with the given one.
     *
     * @param p polynomial to multiply with.
     *
     * @return a new {@code ComplexPolynomial} created
     * by multiplying this one with the given one.
     */
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        List<Complex> result = new ArrayList<>(factors);

        for (int i = 0; i < p.order() + 1; i++) {
            for (int j = i; j < result.size(); j++) {
                result.set(j, i == 0 ? result.get(j).multiply(p.getFactors().get(i)) :
                        result.get(j).add(factors.get(j - i).multiply(p.getFactors().get(i))));
            }

            if (i != 0) {
                result.add(factors.get(order()).multiply(p.getFactors().get(i)));
            }
        }

        return new ComplexPolynomial(result.toArray(new Complex[0]));
    }

    /**
     * Derives this polynomial.
     *
     * @return first derivative of this polynomial
     * as a new {@code ComplexPolynomial}.
     */
    public ComplexPolynomial derive() {
        Complex[] derivative = new Complex[factors.size() - 1];

        for (int i = 1; i < factors.size(); i++) {
            derivative[i - 1] = factors.get(i).multiply(new Complex(i, 0));
        }

        return new ComplexPolynomial(derivative);
    }

    /**
     * Calculates the solution for the given
     * complex number.
     *
     * @param z number being used to calculate the solution.
     *
     * @return result for the given number.
     *
     * @throws NullPointerException if the given number is {@code null}.
     */
    public Complex apply(Complex z) {
        Objects.requireNonNull(z);

        Complex result = factors.get(0);

        for (int i = 1; i < order() + 1; i++) {
            result = result.add(factors.get(i).multiply(z.power(i)));
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();

        for (int i = order(); i >= 0; i--) {
            ret.append(factors.get(i)).append(i == 0 ? "" : "*z^" + i + "+");
        }

        return ret.toString();
    }

    /**
     * Provides a {@code List} of factors.
     *
     * @return a {@code List} of factors.
     */
    public List<Complex> getFactors() {
        return factors;
    }
}
