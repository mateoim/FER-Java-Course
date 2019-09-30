package hr.fer.zemris.java.hw07.observer1;

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
     * @param istorage storage where the value has been changed.
     */
    void valueChanged(IntegerStorage istorage);
}
