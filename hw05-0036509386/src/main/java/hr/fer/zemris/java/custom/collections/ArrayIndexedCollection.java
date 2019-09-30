package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * A collection that is based on an array.
 * It can store duplicate elements but cannot store {@code null} references.
 *
 * @param <T> type of elements stored.
 *
 * @author Mateo Imbrišak
 */

public class ArrayIndexedCollection<T> implements List<T> {

    /**
     * Current size of the {@code ArrayIndexedCollection}.
     */
    private int size;

    /**
     * The array used to keep the references.
     */
    private T[] elements;

    /**
     * Default capacity of the {@code ArrayIndexedCollection}.
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * Tracks the number of modifications made to this {@code Collection}.
     */
    private long modificationCount = 0;

    /**
     * Default constructor that sets the capacity of the
     * {@code ArrayIndexedCollection} to 16.
     */
    public ArrayIndexedCollection() {
        this(DEFAULT_CAPACITY);
    }
    /**
     * Constructor that allocates an {@code ArrayIndexedCollection}
     * of a specific capacity specified by the argument.
     *
     * @param initialCapacity maximum capacity of the created {@code ArrayIndexedCollection}.
     */
    public ArrayIndexedCollection(int initialCapacity) {
        this(new LinkedListIndexedCollection<>(), initialCapacity);
    }

    /**
     * Constructor that copies all elements from a given {@code Collection}
     * to the new {@code ArrayIndexCollection} and sets its capacity to the current
     * size of the given {@code Collection}.
     *
     * @param collection whose elements are being copied.
     *
     * @throws NullPointerException if the given {@code Collection} is {@code null}.
     */
    public ArrayIndexedCollection(Collection<? extends T> collection) {
        this(collection, collection.size());
    }

    /**
     * Constructor that copies all elements from a given {@code Collection}
     * to the new {@code ArrayIndexCollection} and sets its capacity to the
     * given size if its larger than the current size of the given {@code Collection}.
     *
     * @param collection whose elements are being copied.
     * @param initialCapacity capacity of the new {@code ArrayIndexCollection} if it's
     *                        larger than the current size of the given {@code Collection}
     *
     * @throws NullPointerException if the given {@code Collection} is {@code null}.
     */
    @SuppressWarnings("unchecked")
    public ArrayIndexedCollection(Collection<? extends T> collection, int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Capacity must be a positive number larger than 1");
        }

        if (collection == null) {
            throw new NullPointerException("Collection cannot be null");
        }

        if (collection.size() <= initialCapacity) {
            this.elements = (T[]) new Object[initialCapacity];
        } else {
            this.elements = (T[]) new Object[collection.size()];
        }

        addAll(collection);
        this.size = collection.size();
    }

    /**
     * Checks the current size of the {@code ArrayIndexedCollection}.
     *
     * @return current size of the {@code ArrayIndexedCollection}.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Adds a value to the {@code ArrayIndexedCollection} with a constant
     * complexity unless the internal storage is expanding.
     * If the maximum capacity is reached, double the capacity and copy
     * all elements to the new array.
     * Cannot add {@code null}.
     *
     * @param value element being added.
     *
     * @throws NullPointerException if {@code null} is passed as argument.
     */
    @Override
    public void add(T value) {
        insert(value, size());
    }

    /**
     * Checks if a given value is contained in this {@code ArrayIndexedCollection}.
     *
     * @param value we are trying to find.
     *
     * @return {@code true} if the value is in the {@code ArrayIndexedCollection},
     * otherwise {@code false}.
     */
    @Override
    public boolean contains(Object value) {
        if (value == null) {
            return false;
        }

        for (Object element : elements) {
            if (value.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the given {@code Object} if it's contained in the {@code ArrayIndexedCollection}.
     *
     * @param value {@code Object} to be removed.
     *
     * @return {@code true} if the {@code Object} has been successfully removed,
     * otherwise {@code false}.
     */
    @Override
    public boolean remove(Object value) {
        if (value == null) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                moveArray(i, false);
                size--;
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the contents of this {@code ArrayIndexedCollection} as
     * an array of type {@code T}.
     *
     * @return array elements of this {@code ArrayIndexedCollection}.
     */
    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] returnArray = (T[])  new Object[size];
        System.arraycopy(elements, 0, returnArray, 0, size);
        return returnArray;
    }

    /**
     * Sets all references in the {@code ArrayIndexedCollection} to {@code null}
     * and sets the current size to 0.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        modificationCount++;
        size = 0;
    }

    /**
     * Creates a new {@code ElementsGetter} to iterate
     * this {@code Collection}.
     *
     * @return a new instance of {@code ElementsGetter}.
     */
    @Override
    public ElementsGetter<T> createElementsGetter() {
        return new IndexElementsGetter<>(this);
    }

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
    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index must be between 0 and size - 1");
        }

        return elements[index]; // complexity is constant
    }

    /**
     * Inserts given value at a given position and shifts other values by one index.
     * If maximum capacity is reached, capacity is doubled. Average complexity is n
     * since other elements are copied.
     *
     * @param value that is being inserted.
     * @param position at which the value is being inserted.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void insert(T value, int position) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Position must be a positive number lesser than" +
                    " or equal to the current size.");
        }

        if (value == null) {
            throw new NullPointerException("Null cannot be added to the collection.");
        }

        try {
            moveArray(position, true);
            elements[position] = value;
            size++;
        } catch (IndexOutOfBoundsException exc) {
            T[] backup = elements;
            elements = (T[]) new ObjectStack[size * 2];
            System.arraycopy(backup, 0, elements, 0, position);
            System.arraycopy(backup, position, elements, position + 1, size - position);
            elements[position] = value;
            modificationCount++;
            size++;
        }
    }

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
    @Override
    public int indexOf(Object value) {
        if (value == null) {
            return  -1;
        }

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Removes element at the specified index and moves remaining elements to one
     * index lower than they were.
     *
     * @param index at which the element being removed is located.
     *
     * @throws IndexOutOfBoundsException if the given index is a negative number or
     *         greater than current size - 1.
     */
    @Override
    public void remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index must be between 0 and size - 1");
        }

        moveArray(index, false);
        elements[size] = null;
        size--;
    }

    /**
     * Used internally to move remaining elements of an array after one has been removed.
     *
     * @param index index of the element being removed.
     */
    private void moveArray(int index, boolean direction) {
        if (!direction) {
            if (size - 1 - index >= 0) {
                System.arraycopy(elements, index + 1, elements, index, size - 1 - index);
            }
            elements[size] = null;
            modificationCount++;
            return;
        }

        System.arraycopy(elements, index, elements, index + 1, size - index + 1);
        modificationCount++;
    }

    /**
     * An {@code Object} used to iterate {@code Collection}s.
     *
     * @author Mateo Imbrišak
     */
    private static class IndexElementsGetter<E> implements ElementsGetter<E> {

        /**
         * Used to keep track of the current position in the {@code Collection}.
         */
        private int position = 0;

        /**
         * A reference to the {@code Collection} we are iterating.
         */
        private ArrayIndexedCollection<E> collection;

        /**
         * Keeps the number of modifications of the host {@code Collection}
         * at the time of creation.
         */
        private final long savedModificationCount;

        /**
         * A private constructor used in
         * {@link ArrayIndexedCollection#createElementsGetter()}
         * to pass the reference to this {@code Collection}.
         *
         * @param collection a reference to this {@code Collection}.
         */
        private IndexElementsGetter(ArrayIndexedCollection<E> collection) {
            this.collection = collection;
            this.savedModificationCount = collection.modificationCount;
        }

        /**
         * Checks if the {@code Collection} has another element in it.
         *
         * @return {@code true} if the {@code Collection} has another element,
         * otherwise {@code false}.
         */
        @Override
        public boolean hasNextElement() {
            return position < collection.size();
        }

        /**
         * Returns next element in this {@code Collection}, if there is none
         * throws a {@code NoSuchElementException}.
         *
         * @return next element in the {@code Collection}.
         *
         * @throws NoSuchElementException if there are no more elements left.
         * @throws ConcurrentModificationException if the {@code Collection} has
         * been modified since the creation of the {@code ElementGetter}.
         */
        @Override
        public E getNextElement() {
            if (savedModificationCount != collection.modificationCount) {
                throw new ConcurrentModificationException("The collection has been modified.");
            }

            if (!hasNextElement()) {
                throw new NoSuchElementException();
            }

            return collection.get(position++);
        }
    }
}
