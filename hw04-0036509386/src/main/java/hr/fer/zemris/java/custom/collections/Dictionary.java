package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * A simple dictionary that keeps pairs of a unique key and a value.
 *
 * @param <K> type used for the key.
 * @param <V> type used for the value.
 *
 * @author Mateo Imbrišak
 */

public class Dictionary<K, V> {

    /**
     * Used to store the pairs in this {@code Dictionary}.
     */
    private ArrayIndexedCollection<Entry> memory;

    /**
     * Default constructor that initializes an instance
     * of {@code ArrayIndexedCollection} for storage.
     */
    public Dictionary() {
        this.memory = new ArrayIndexedCollection<>();
    }

    /**
     * Checks if the {@code Dictionary} is currently
     * empty.
     *
     * @return {@code true} if the {@code Dictionary}
     * is empty, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return memory.isEmpty();
    }

    /**
     * Returns the current size of the {@code Dictionary}.
     *
     * @return current size of this {@code Dictionary}.
     */
    public int size() {
        return memory.size();
    }

    /**
     * Removes all elements form this {@code Dictionary}.
     */
    public void clear() {
        memory.clear();
    }

    /**
     * Adds this pair to the {@code Dictionary}.
     * If a pair with the same key is already in the
     * {@code Dictionary}, it is replaced.
     *
     * @param key of the pair to be added.
     * @param value of the pair to be added.
     *
     * @throws NullPointerException if the given key is {@code null}.
     */
    void put(K key, V value) {
        Objects.requireNonNull(key);

        Entry current = new Entry();
        current.key = key;
        current.value = value;

        memory.remove(current);
        memory.add(current);
    }

    /**
     * Returns a value of the pair with a given key
     * if it exists and the key is of valid class.
     *
     * @param key of the pair whose value you want.
     *
     * @return the value of the pair with the given
     * key, {@code null} if the key doesn't exist or
     * the given key is of invalid class.
     *
     * @throws NullPointerException if the given key
     * is {@code null}.
     */
    public V get(Object key) {
        Objects.requireNonNull(key);

        Entry toFind = new Entry();

        try {
            toFind.key = (K) key;
        } catch (ClassCastException exc) {
            System.out.println("Given key is of invalid class.");
            return null;
        }

        int index = memory.indexOf(toFind);

        if (index != -1) {
            return memory.get(index).value;
        }

        return null;
    }

    /**
     * A class used internally to store a pair
     * of a key and a value for the {@code Dictionary}.
     */
    private class Entry {

        /**
         * Represents a key of the pair in {@code Dictionary}.
         * Must not be {@code null}.
         */
        private K key;

        /**
         * Represents a value of the pair in {@code Dictionary}
         * and can be {@code null}.
         */
        private V value;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return key.equals(entry.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }
}
