package hr.fer.zemris.java.hw07.observer2;

/**
 * A class used to keep old and new value of
 * an {@code IntegerStorage} when passed to
 * the observers.
 *
 * @author Mateo Imbrišak
 */

public class IntegerStorageChange {

    /**
     * Keeps the reference of the {@code IntegerStorage}
     * being changed.
     */
    private IntegerStorage storage;

    /**
     * Keeps the old value.
     */
    private int oldValue;

    /**
     * Keeps the new value.
     */
    private int newValue;

    /**
     * Default constructor that assigns all values.
     *
     * @param storage reference to the original storage.
     * @param oldValue of the storage.
     * @param newValue of the storage.
     */
    public IntegerStorageChange(IntegerStorage storage, int oldValue, int newValue) {
        this.storage = storage;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Provides the reference to the original storage.
     *
     * @return reference to the original storage.
     */
    public IntegerStorage getStorage() {
        return storage;
    }

    /**
     * Provides the old value.
     *
     * @return old value.
     */
    public int getOldValue() {
        return oldValue;
    }

    /**
     * Provides the new value.
     *
     * @return new value.
     */
    public int getNewValue() {
        return newValue;
    }
}
