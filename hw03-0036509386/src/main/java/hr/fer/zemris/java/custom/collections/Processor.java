package hr.fer.zemris.java.custom.collections;

/**
 * An "abstract" class used to model objects capable of performing
 * an operation on a passed object.
 *
 * @author Mateo Imbrišak
 */

public interface Processor {

    /**
     * Method used to perform an action defined by a class that extends this one.
     *
     * @param value object being processed.
     */
    void process(Object value);
}
