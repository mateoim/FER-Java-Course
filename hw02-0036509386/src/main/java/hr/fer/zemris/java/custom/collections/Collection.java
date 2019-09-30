package hr.fer.zemris.java.custom.collections;

/**
 * Class used as a template with basic functionality contract
 * for other collections.
 *
 * @author Mateo Imbrišak
 */

public class Collection {

    /**
     * Default constructor, empty since it is a template that cannot be created.
     */
    protected Collection() {}

    /**
     * Checks if a {@code Collection} is empty.
     *
     * @return {@code true} if the {@code Collection} is empty,
     * otherwise {@code false}.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Provides the current size of the {@code Collection}.
     *
     * @return current size of this {@code Collection}.
     */
    public int size() {
        return 0;
    }

    /**
     * Adds an element to this {@code Collection}.
     *
     * @param value element to be added.
     */
    public void add(Object value) {}

    /**
     * Checks whether this {@code Collection} contains
     * the given element.
     *
     * @param value element we are looking for.
     *
     * @return {@code true} if the element is found,
     * otherwise {@code false}.
     */
    public boolean contains(Object value) {
        return false;
    }

    /**
     * Removes an {@code Object} from the {@code Collection}
     * if it is present.
     *
     * @param value an {@code Object} we want to remove.
     *
     * @return {@code true} if the {@code Object} was successfully
     * removed, otherwise {@code false}.
     */
    public boolean remove(Object value) {
        return false;
    }

    /**
     * Returns current contents of this {@code Collection} as
     * an array.
     *
     * @return all elements of this {@code Collection} in an array.
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls the method {@link hr.fer.zemris.java.custom.collections.Processor#process(Object)}
     * on each element of the {@code LinkedListIndexedCollection}.
     *
     * @param processor used to process the elements.
     */
    public void forEach(Processor processor) {}

    /**
     * Adds all elements from a given {@code Collection} to this
     * {@code Collection} without changing the first one.
     *
     * @param other {@code Collection} whose elements are being copied.
     */
    public void addAll(Collection other) {
        class CollectionProcessor extends Processor {

            @Override
            public void process(Object value) {
                add(value);
            }
        }

        Processor processor = new CollectionProcessor();
        other.forEach(processor);
    }

    /**
     * Removes all elements from this {@code Collection}.
     */
    public void clear() {}
}
