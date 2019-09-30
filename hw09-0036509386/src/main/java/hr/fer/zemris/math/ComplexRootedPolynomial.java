package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a polynomial
 * in z0*(z-z1)*(z-z2)*...*(z-zn) form.
 *
 * @author Mateo Imbrišak
 */

public class ComplexRootedPolynomial {

    /**
     * Keeps the constant.
     */
    private final Complex constant;

    /**
     * Keeps the roots.
     */
    private final List<Complex> roots;

    /**
     * Default constructor that assigns all values.
     *
     * @param constant of this polynomial.
     * @param roots of this polynomial.
     *
     * @throws NullPointerException if any argument
     * is {@code null}.
     */
    public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
        this.constant = constant;
        this.roots = Arrays.asList(roots);

        Objects.requireNonNull(constant);
        this.roots.forEach(Objects::requireNonNull);
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

        Complex result = constant;

        for (Complex current : roots) {
            result = result.multiply(z.sub(current));
        }

        return result;
    }

    /**
     * Converts this polynomial to
     * a {@code ComplexPolynomial}.
     *
     * @return a {@code ComplexPolynomial}
     * representation of this polynomial.
     */
    public ComplexPolynomial toComplexPolynom() {
        ComplexPolynomial ret = new ComplexPolynomial(constant);

        for (Complex current : roots) {
            ret = ret.multiply(new ComplexPolynomial(current.negete(), Complex.ONE)); // Z-Zn
        }

        return ret;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(constant);

        for (Complex current : roots) {
            ret.append("*").append("(z-").append(current).append(")");
        }

        return ret.toString();
    }

    /**
     * Finds the closest root to the given number.
     *
     * @param z complex number being checked.
     * @param threshold maximum distance for the root
     *                 to be acceptable.
     *
     * @return index of the closest root if it exists,
     * otherwise {@code -1}.
     */
    public int indexOfClosestRootFor(Complex z, double threshold) {
        Objects.requireNonNull(z);

        int index = -1;

        for (int i = 0; i < roots.size(); i++) {
            if (z.sub(roots.get(i)).module() < threshold) {
                index = i;
                threshold = z.sub(roots.get(i)).module();
            }
        }

        return index;
    }
}
