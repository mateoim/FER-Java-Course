package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * A {@code Collection} that is based on a double linked list.
 * It can store duplicate values, but cannot store {@code null} values.
 *
 * @param <T> type of elements stored.
 *
 * @author Mateo Imbrišak
 */

public class LinkedListIndexedCollection<T> implements List<T> {

    /**
     * Current size of the {@code LinkedListIndexedCollection}.
     */
    private int size = 0;

    /**
     * A reference to the first element of the {@code LinkedListIndexedCollection}.
     */
    private ListNode<T> first;

    /**
     * A reference to the last element of the {@code LinkedListIndexedCollection}.
     */
    private ListNode<T> last;

    /**
     * Keeps track of the number of modifications made to
     * this {@code LinkedListIndexedCollection}.
     */
    private long modificationCount = 0;

    /**
     * An auxiliary class that represents an element of the {@code LinkedListIndexedCollection}.
     */
    private static class ListNode<E> {
        private ListNode<E> previousNode;
        private ListNode<E> nextNode;
        private E value;
    }

    /**
     * Default constructor that sets the first and last node to {@code null}.
     */
    public LinkedListIndexedCollection() {}

    /**
     * Constructor that copies all elements from a given {@code Collection}
     * to the newly created {@code LinkedListIndexedCollection}.
     *
     * @param collection that is being copied.
     *
     * @throws NullPointerException if the given {@code Collection} is {@code null}.
     */
    public LinkedListIndexedCollection(Collection<? extends T> collection) {
        if (collection == null) {
            throw new NullPointerException("Collection cannot be null.");
        }

        if (collection.isEmpty()) {
            first = null;
            last = null;
        } else {
            addAll(collection);
            size = collection.size();
        }
    }

    /**
     * Checks the current size of the {@code LinkedListIndexedCollection}.
     *
     * @return current size of the {@code LinkedListIndexedCollection}.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Adds an element to the end of the {@code LinkedListIndexedCollection}.
     *
     * @param value of the element being added.
     *
     * @throws NullPointerException if the passed argument is {@code null}.
     */
    @Override
    public void add(T value) {
        if (value == null) {
            throw new NullPointerException("Null cannot be added to the Collection.");
        }

        if (first == null) {
            first = new ListNode<>();
            first.value = value;
            last = first;
            size++;
            modificationCount++;
            return;
        }

        ListNode<T> node = new ListNode<>();
        node.value = value;
        node.previousNode = last;
        last.nextNode = node;
        last = node;
        size++;
        modificationCount++;
    }

    /**
     * Checks if a given value is contained in this {@code LinkedListIndexedCollection}.
     *
     * @param value we are trying to find.
     *
     * @return {@code true} if the value is in the {@code LinkedListIndexedCollection},
     * otherwise {@code false}.
     */
    @Override
    public boolean contains(Object value) {
        if (first == null) {
            return false;
        }

        ListNode<T> node = first;

        do {
            if (node.value.equals(value)) {
                return true;
            }
            node = node.nextNode;
        } while (node != null);

        return  false;
    }

    /**
     * Removes the given value from the {@code LinkedListIndexedCollection}.
     *
     * @param value being removed.
     *
     * @return {@code true} if the element has been successfully removed,
     * otherwise {@code false}.
     */
    @Override
    public boolean remove(Object value) {
        if (value == null) {
            return false;
        }

        if (first.value.equals(value)) {
            first = first.nextNode;
            size--;
            modificationCount++;
            return true;
        } else if (last.value.equals(value)) {
            last = last.previousNode;
            last.nextNode = null;
            size--;
            modificationCount++;
            return true;
        }

        ListNode<T> node = first;

        do {
            if (node.value.equals(value)) {
                ListNode<T> previous = node.previousNode;
                ListNode<T> next = node.nextNode;

                previous.nextNode = node.nextNode;
                next.previousNode = node.previousNode;

                size--;
                modificationCount++;

                return true;
            }

            node = node.nextNode;
        } while (node.nextNode != null);

        return false;
    }

    /**
     * Generates an array based on the current contents of
     * the {@code LinkedListIndexedCollection}.
     *
     * @return array of type <T> elements currently
     * in the {@code LinkedListIndexedCollection}.
     */
    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        if (first == null) {
            return (T[]) new Object[0];
        }

        T[] returnArray = (T[]) new Object[size];
        ListNode<T> node = first;
        int counter = 0;

        do {
            returnArray[counter] = node.value;
            node = node.nextNode;
            counter++;
        } while (node != null);

        return returnArray;
    }

    /**
     * Removes the references to the first and last element,
     * effectively emptying the {@code LinkedListIndexedCollection}.
     */
    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
        modificationCount++;
    }

    /**
     * Creates a new {@code ElementsGetter} to iterate
     * this {@code Collection}.
     *
     * @return a new instance of {@code ElementsGetter}.
     */
    @Override
    public ElementsGetter<T> createElementsGetter() {
        return new LinkedElementsGetter<>(this);
    }

    /**
     * Returns the element at a given index.
     *
     * @param index at which the element is located.
     *
     * @return the {@code Object} at the located index.
     *
     * @throws IndexOutOfBoundsException if the index is a negative number
     * or greater than the current size of the {@code LinkedListIndexedCollection}.
     */
    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index must be between 0 and size - 1.");
        }

        return getToIndex(index).value;
    }

    /**
     * Used internally to get to a given index with (n / 2) + 1 complexity
     *
     * @param index desired index.
     *
     * @return {@code ListNode} at the given index.
     */
    private ListNode<T> getToIndex(int index) {
        if (index <= size / 2) {
            ListNode<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.nextNode;
            }

            return node;
        }

        ListNode<T> node = last;
        for (int i = 0; i < size - index - 1; i++) {
            node = node.previousNode;
        }

        return node;
    }

    /**
     * Inserts given {@code Object} at a given position.
     *
     * @param value of the {@code Object} being inserted.
     * @param position of the {@code Object} being inserted.
     *
     * @throws NullPointerException if the given {@code Object} is null.
     * @throws IndexOutOfBoundsException if the given position is
     * a negative number or greater than current size + 1.
     */
    @Override
    public void insert(T value, int position) {
        if (value == null) {
            throw new NullPointerException("Null cannot be added to the collection.");
        }

        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Position bust be a positive number lesser than" +
                    "or equal to the current size.");
        }

        ListNode<T> newNode = new ListNode<>();
        newNode.value = value;

        if (position == size) { // if this is the last node
            last.nextNode = newNode;
            newNode.previousNode = last;
            last = last.nextNode;
            size++;
            modificationCount++;
            return;
        }

        ListNode<T> positionNode = getToIndex(position);

        if (positionNode.previousNode == null) { // if this is the first node
            positionNode.previousNode = newNode;
            newNode.nextNode = positionNode;
            first = newNode;
            size++;
            modificationCount++;
            return;
        }

        newNode.previousNode = positionNode.previousNode;
        positionNode.previousNode.nextNode = newNode;
        positionNode.previousNode = newNode;
        newNode.nextNode = positionNode;
        size++;
        modificationCount++;
    }

    /**
     * Finds the first occurrence of a given {@code Object},
     * if it is not found or the value is {@code null}, return -1
     *
     * @param value the {@code Object} we are looking for.
     *
     * @return First occurrence of the {@code Object} or -1 if
     *         it is not found or the parameter is null.
     */
    @Override
    public int indexOf(Object value) {
        if (value == null) {
            return -1;
        }

        int index = 0;
        ListNode<T> node = first;

        do {
            if (node.value.equals(value)) {
                return index;
            }
            index++;
            node = node.nextNode;
        } while (node != null);

        return -1;
    }

    /**
     * Removes the {@code Object} at a given index.
     *
     * @param index of the {@code Object} we want to remove.
     *
     * @throws IndexOutOfBoundsException if the index is a negative number or greater than the current size - 1.
     */
    @Override
    public void remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index must be a positive number lesser than the current size.");
        }

        if (index == 0) { // first
            first = first.nextNode;
            first.previousNode = null;
            size--;
            modificationCount++;
            return;
        }

        if (index == size - 1) { // last
            last = last.previousNode;
            last.nextNode = null;
            size--;
            modificationCount++;
            return;
        }

        ListNode<T> removed = getToIndex(index);

        removed.previousNode.nextNode = removed.nextNode;
        removed.nextNode.previousNode = removed.previousNode;
        size--;
        modificationCount++;
    }

    private static class LinkedElementsGetter<E> implements ElementsGetter<E> {
        /**
         * Used to keep track of the current position in the {@code Collection}.
         */
        private int position = 0;

        /**
         * A reference to the {@code Collection} we are iterating.
         */
        private LinkedListIndexedCollection<E> collection;

        /**
         * Keeps the number of modifications of the host {@code Collection}
         * at the time of creation.
         */
        private final long savedModificationCount;

        /**
         * A private constructor used in
         * {@link LinkedListIndexedCollection#createElementsGetter()}
         * to pass the reference to this {@code Collection}.
         *
         * @param collection a reference to this {@code Collection}.
         */
        private LinkedElementsGetter(LinkedListIndexedCollection<E> collection) {
            this.collection = collection;
            savedModificationCount = collection.modificationCount;
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
