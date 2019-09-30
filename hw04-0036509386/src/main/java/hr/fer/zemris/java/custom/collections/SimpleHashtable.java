package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A class that represents a hash table.
 * It calculates positions of elements based on their hashcode.
 *
 * @param <K> type used for the key.
 * @param <V> type used for the value.
 *
 * @author Mateo Imbrišak
 */

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

    /**
     * Keeps the table with
     */
    private TableEntry<K, V>[] table;

    /**
     * Keeps the current size.
     */
    private int size;

    /**
     * Keeps track of modifications made to
     * this {@code SimpleHashTable}.
     */
    private long modificationCount;

    /**
     * Defines the default capacity used for the default constructor.
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * Represents a percentage of maximum optimal occupation.
     */
    private static final double OPTIMAL_OCCUPATION = 0.75;

    /**
     * Default constructor that sets the number of
     * slots to default of 16.
     */
    public SimpleHashtable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * A constructor that sets the number of
     * slots to the given capacity.
     *
     * @param capacity number of slots in
     *                 the {@code SimpleHashtable}.
     *
     * @throws IllegalArgumentException if the given
     * capacity is less than zero.
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be a positive number greater than zero.");
        }

        size = 0;
        int correctCapacity = 1;

        while (correctCapacity < capacity) {
            correctCapacity *= 2;
        }

        table = (TableEntry<K, V>[]) new TableEntry[correctCapacity];
    }

    /**
     * Adds a pair to the {@code SimpleHashtable}.
     * If the key already exists, the value is overwritten.
     *
     * @param key of the pair we want to add.
     * @param value of the pair we want to add.
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key);

        if (size + 1 >= OPTIMAL_OCCUPATION * table.length) {
            backupAndExpand();
        }

        TableEntry<K, V> currentSlot = getStartingEntry(key);
        TableEntry<K, V> toAdd = new TableEntry<>();
        toAdd.key = key;
        toAdd.value = value;

        if (currentSlot == null) {
            table[Math.abs(key.hashCode()) % table.length] = toAdd;
            size++;
            modificationCount++;
        } else {
            while (currentSlot.next != null) {
                if (currentSlot.key.equals(key)) {
                    currentSlot.setValue(value);
                    return;
                }

                currentSlot = currentSlot.next;
            }

            if (currentSlot.getKey().equals(key)) {
                currentSlot.setValue(value);
                return;
            }

            currentSlot.next = toAdd;
            size++;
            modificationCount++;
        }
    }

    /**
     * Returns the value of a given key if it exists.
     *
     * @param key whose value you want to get.
     *
     * @return value paired with the given key if
     * it exists, otherwise {@code null}.
     */
    public V get(Object key) {
        TableEntry<K, V> currentSlot = getStartingEntry(key);

        if (currentSlot == null) {
            return null;
        }

        while (currentSlot != null) {
            if (currentSlot.key.equals(key)) {
                return currentSlot.value;
            }

            currentSlot = currentSlot.next;
        }

        return null;
    }

    /**
     * Returns the current size of the {@code SimpleHashtable}.
     *
     * @return current size.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the given key is in the {@code SimpleHashtable}.
     *
     * @param key being checked.
     *
     * @return {@code true} if the key is in the {@code SimpleHashtable},
     * otherwise {@code false}.
     */
    public boolean containsKey(Object key) {
        TableEntry<K, V> current = getStartingEntry(key);

        while (current != null) {
            if (current.getKey().equals(key)) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    /**
     * Checks if the given value is in the {@code SimpleHashtable}.
     *
     * @param value being checked.
     *
     * @return {@code true} if the value is in the {@code SimpleHashtable},
     * otherwise {@code false}.
     */
    public boolean containsValue(Object value) {
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                if (entry.getValue().equals(value)) {
                    return true;
                }

                entry = entry.next;
            }
        }

        return false;
    }

    /**
     * Removes a pair with a given key.
     * If the key doesn't exist or is {@code null},
     * do nothing.
     *
     * @param key you want to remove.
     */
    public void remove(Object key) {
        if (key == null) {
            return;
        }

        TableEntry<K, V> current = getStartingEntry(key);

        // if it is the first in the slot
        if (current.getKey().equals(key)) {
            table[Math.abs(key.hashCode()) % table.length] = current.next;
            size--;
            modificationCount++;
        }

        while (current.next != null) {
            if (current.next.getKey().equals(key)) {
                current.next = current.next.next;
                size--;
                modificationCount++;
                return;
            }

            current = current.next;
        }
    }

    /**
     * Checks if the {@code SimpleHashtable} is empty.
     *
     * @return {@code true} if it is empty, otherwise
     * {@code false}.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a textual representation of this
     * {@code SimpleHashtable}'s contents.
     *
     * @return a {@code String} containing a list
     * of keys and values in the {@code SimpleHashtable}.
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();

        ret.append("[");

        for (TableEntry<K, V> entry : table) {
            if (entry != null) {
                while (entry != null) {
                    ret.append(entry.getKey()).append("=");
                    if (entry.value != null) {
                        ret.append(entry.getValue());
                    } else {
                        ret.append("null");
                    }

                    ret.append(", ");
                    entry = entry.next;
                }
            }
        }

        return ret.toString().substring(0, ret.length() - 2) + "]";
    }

    /**
     * Removes all elements from the {@code SimpleHashtable}.
     */
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }

        size = 0;
        modificationCount++;
    }

    /**
     * Creates a new {@code Iterator} to
     * iterate the {@code SimpleHashtable}.
     *
     * @return a new {@code Iterator}.
     */
    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    /**
     * Used internally to find the starting element
     * of a slot based on the given key.
     *
     * @param key used to calculate the slot.
     *
     * @return first element in the calculate slot.
     */
    private TableEntry<K, V> getStartingEntry(Object key) {
        int slotNumber = Math.abs(key.hashCode()) % table.length;
        return table[slotNumber];
    }

    /**
     * Expands the internal storage if the
     * occupation has reached 75% of available
     * slots.
     */
    @SuppressWarnings("unchecked")
    private void backupAndExpand() {
        int newCapacity = table.length * 2;
        TableEntry<K, V>[] backup = table;

        table = (TableEntry<K, V>[]) new TableEntry[newCapacity];
        size = 0;

        for (TableEntry<K, V> entry : backup) {
            while (entry != null) {
                put(entry.key, entry.value);

                entry = entry.next;
            }
        }
    }

    /**
     * A package-private method used in JUnit tests to check
     * if the internal table expands correctly.
     *
     * @return maximum capacity of the internal table.
     */
    int elementsCapacity() {
        return table.length;
    }

    /**
     * An auxiliary class used as slots in the
     * {@code SimpleHashtable}.
     * It stores elements as pairs of key and value
     * and keeps a reference to the next element.
     * Key cannot be {@code null}.
     *
     * @param <K> type used for the keys.
     * @param <V> type used for the values.
     */
    public static class TableEntry<K, V> {

        /**
         * Keeps the key of this pair.
         */
        private K key;

        /**
         * Keeps the value of this pair.
         */
        private V value;

        /**
         * Reference to the next element.
         */
        private TableEntry<K, V> next;

        /**
         * Returns the key of this pair.
         *
         * @return key of the pair.
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the current value
         * of this pair.
         *
         * @return current value of the pair.
         */
        public V getValue() {
            return value;
        }

        /**
         * Changes the value of this pair
         * to the given value.
         *
         * @param value to be set.
         */
        public void setValue(V value) {
            this.value = value;
        }
    }

    /**
     * A class that iterates {@code SimpleHashcode}.
     */
    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

        /**
         * Keeps track of whether the current element can be removed.
         */
        private boolean isRemoveLegal = false;

        /**
         * Keeps track of the current element's index.
         */
        private int nextIndex;

        /**
         * Keeps track of current slot in the table
         * for faster access.
         */
        private int currentSlot;

        /**
         * Keeps the current node for removal or faster
         * advancement.
         */
        private TableEntry<K, V> currentElement;

        /**
         * Saves the number of modifications made to the
         * {@code SimpleHashtable} at the time of creation
         * of this {@code Iterator}.
         */
        private long savedModificationCount = modificationCount;

        /**
         * Checks if the {@code Iterator} has any more
         * elements.
         *
         * @return {@code true} if the last element has not been passed,
         * otherwise {@code false}.
         *
         * @throws ConcurrentModificationException if the
         * {@code SimpleHashtable} has been modified.
         */
        @Override
        public boolean hasNext() {
            checkForModifications();

            return nextIndex < size();
        }

        /**
         * Returns the next element in the {@code SimpleHashtable}
         * if it exists.
         *
         * @return next element if the las one has not been passed.
         *
         * @throws ConcurrentModificationException if the
         * {@code SimpleHashtable} has been modified.
         */
        @Override
        public TableEntry<K, V> next() {
            checkForModifications();

            if (!hasNext()) {
                throw new NoSuchElementException("There are no more elements.");
            }

            if (currentElement == null || currentElement.next == null) {
                currentElement = table[currentSlot++];

                while (currentElement == null) {
                    currentElement = table[currentSlot++];
                }

                isRemoveLegal = true;
                nextIndex++;
                return currentElement;
            }

            currentElement = currentElement.next;
            nextIndex++;
            isRemoveLegal = true;
            return currentElement;
        }

        /**
         * Removes the last passed element.
         *
         * @throws ConcurrentModificationException if the
         * {@code SimpleHashtable} has been modified.
         * @throws IllegalStateException if {@link #next}
         * has never been called or the element has already
         * been removed.
         */
        @Override
        public void remove() {
            checkForModifications();

            if (isRemoveLegal) {
                SimpleHashtable.this.remove(currentElement.getKey());
                isRemoveLegal = false;
                savedModificationCount++;
                nextIndex--;
            } else {
                throw new IllegalStateException("Elements cannot be removed.");
            }
        }

        /**
         * Used internally to check if the
         * {@code SimpleHashtable} has been
         * modified and throw an appropriate
         * exception if it has.
         *
         * @throws ConcurrentModificationException if
         * the {@code SimpleHashtable} has been modified.
         */
        private void checkForModifications() {
            if (modificationCount != savedModificationCount) {
                throw new ConcurrentModificationException("The hash table has been modified.");
            }
        }
    }
}
