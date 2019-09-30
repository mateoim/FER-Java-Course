package hr.fer.zemris.java.custom.collections;

/**
 * An interface that defines expected behavior for a
 * {@code List}.
 *
 * @param <T> type of elements stored.
 *
 * @author Mateo Imbrišak
 */

public interface List<T> extends Collection<T> {

    /**
     * Finds the value located at the given index with constant complexity
     * if it's within the bounds of the {@code ArrayIndexedCollection}
     *
     * @param index of the {@code Object} we are looking for.
     *
     * @return {@code Object} located at the given index.
     *
     * @throws IndexOutOfBoundsException if the given index is a negative
     *         number or greater than current size - 1.
     */
    T get(int index);

    /**
     * Inserts given value at a given position and shifts other values by one index.
     * If maximum capacity is reached, capacity is doubled. Average complexity is n
     * since other elements are copied.
     *
     * @param value that is being inserted.
     * @param position at which the value is being inserted.
     */
    void insert(T value, int position);

    /**
     * Finds the first occurrence of a given {@code Object},
     * if it is not found or the value is {@code null}, return -1.
     * Average complexity is n.
     *
     * @param value the {@code Object} we are looking for.
     *
     * @return First occurrence of the {@code Object} or -1 if
     *         it is not found or the parameter is null.
     */
    int indexOf(Object value);

    /**
     * Removes element at the specified index and moves remaining elements to one
     * index lower than they were.
     *
     * @param index at which the element being removed is located.
     *
     * @throws IndexOutOfBoundsException if the given index is a negative number or
     *         greater than current size - 1.
     */
    void remove(int index);
}
