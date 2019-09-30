package hr.fer.zemris.java.custom.collections;

/**
 * An "abstract" class used to model objects capable of performing
 * an operation on a passed object.
 *
 * @param <T> type of objects processed.
 *
 * @author Mateo Imbrišak
 */

public interface Processor<T> {

    /**
     * Method used to perform an action defined by a class that extends this one.
     *
     * @param value object being processed.
     */
    void process(T value);
}
