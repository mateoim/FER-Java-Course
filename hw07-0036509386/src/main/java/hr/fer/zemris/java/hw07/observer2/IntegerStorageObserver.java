package hr.fer.zemris.java.hw07.observer2;

/**
 * An interface used to model all observers for
 * {@code IntegerStorage}.
 *
 * @author Mateo Imbrišak
 */

public interface IntegerStorageObserver {

    /**
     * Used after a value in the storage has been changed.
     *
     * @param istorage copy of the storage where the value has been changed.
     */
    void valueChanged(IntegerStorageChange istorage);
}
