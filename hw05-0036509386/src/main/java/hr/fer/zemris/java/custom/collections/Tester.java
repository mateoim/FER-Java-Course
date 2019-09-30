package hr.fer.zemris.java.custom.collections;

/**
 * An interface that tests whether a given {@code Object}
 * is acceptable or not.
 *
 * @param <T> type being tested.
 *
 * @author Mateo Imbrišak
 */

public interface Tester<T> {

    /**
     * Tests if the {@code Object} of type <T> is acceptable.
     *
     * @param obj the {@code Object} being tested.
     *
     * @return {@code true} if the {@code Object} is
     * acceptable, otherwise false.
     */
    boolean test(T obj);
}
