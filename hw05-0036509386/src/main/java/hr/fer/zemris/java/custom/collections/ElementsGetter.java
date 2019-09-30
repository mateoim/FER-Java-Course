package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;

/**
 * A template for a class used to iterate various {@code Collections}.
 *
 * @param <T> type of elements returned by this {@code ElementGetter}.
 *
 * @author Mateo Imbrišak
 */

public interface ElementsGetter<T> {

    /**
     * Checks if the {@code Collection} has another element in it.
     *
     * @return {@code true} if the {@code Collection} has another element,
     * otherwise {@code false}.
     */
    boolean hasNextElement();

    /**
     * Returns next element in this {@code Collection}, if there is none
     * throws a {@code NoSuchElementException}.
     *
     * @return next element in the {@code Collection}.
     *
     * @throws NoSuchElementException if there are no more elements left.
     */
    T getNextElement();

    /**
     * Calls {@link hr.fer.zemris.java.custom.collections.Processor#process(Object)}
     * on all remaining elements.
     *
     * @param p {@code Processor} used to process the elements.
     */
    default void processRemaining(Processor<? super T> p) {
        while (hasNextElement()) {
            p.process(getNextElement());
        }
    }
}
