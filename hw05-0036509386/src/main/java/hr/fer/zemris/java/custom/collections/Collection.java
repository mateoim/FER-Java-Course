package hr.fer.zemris.java.custom.collections;

/**
 * Class used as a template with basic functionality contract
 * for other collections.
 *
 * @param <T> type of elements stored in this {@code Collection}.
 *
 * @author Mateo Imbrišak
 */

public interface Collection<T> {

    /**
     * Checks if a {@code Collection} is empty.
     *
     * @return {@code true} if the {@code Collection} is empty,
     * otherwise {@code false}.
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Provides the current size of the {@code Collection}.
     *
     * @return current size of this {@code Collection}.
     */
    int size();

    /**
     * Adds an element to this {@code Collection}.
     *
     * @param value element to be added.
     */
    void add(T value);

    /**
     * Checks whether this {@code Collection} contains
     * the given element.
     *
     * @param value element we are looking for.
     *
     * @return {@code true} if the element is found,
     * otherwise {@code false}.
     */
    boolean contains(Object value);

    /**
     * Removes an {@code Object} from the {@code Collection}
     * if it is present.
     *
     * @param value an {@code Object} we want to remove.
     *
     * @return {@code true} if the {@code Object} was successfully
     * removed, otherwise {@code false}.
     */
     boolean remove(Object value);

    /**
     * Returns current contents of this {@code Collection} as
     * an array.
     *
     * @return all elements of this {@code Collection} in an array.
     */
     T[] toArray();

    /**
     * Calls the method {@link hr.fer.zemris.java.custom.collections.Processor#process(Object)}
     * on each element of the {@code LinkedListIndexedCollection}.
     *
     * @param processor used to process the elements.
     */
     default void forEach(Processor<? super T> processor) {
         ElementsGetter<? extends T> getter = createElementsGetter();

         while (getter.hasNextElement()) {
             processor.process(getter.getNextElement());
         }
     }

    /**
     * Adds all elements from a given {@code Collection} to this
     * {@code Collection} without changing the first one.
     *
     * @param other {@code Collection} whose elements are being copied.
     */
    default void addAll(Collection<? extends T> other) {
        Processor<? super T> processor = this::add;
        other.forEach(processor);
    }

    /**
     * Removes all elements from this {@code Collection}.
     */
     void clear();

    /**
     * Creates an {@code ElementsGetter} to iterate this {@code Collection}.
     * All {@code ElementsGetters} work independently.
     *
     * @return a new instance of {@code ElementsGetter}.
     */
     ElementsGetter<T> createElementsGetter();

    /**
     * Adds all elements from a given {@code Collection} if
     * the given {@code Tester} accepts them.
     *
     * @param col {@code Collection} from which the elements are
     *            being added.
     * @param tester which is being used to test the elements.
     */
     default void addAllSatisfying(Collection<? extends T> col, Tester<T> tester) {
         ElementsGetter<? extends T> getter = col.createElementsGetter();

         while (getter.hasNextElement()) {
             T next = getter.getNextElement();
             if (tester.test(next)) {
                 add(next);
             }
         }
     }
}
