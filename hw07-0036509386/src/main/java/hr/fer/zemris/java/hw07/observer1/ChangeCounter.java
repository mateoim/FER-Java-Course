package hr.fer.zemris.java.hw07.observer1;

/**
 * An observer that prints the number of changes made
 * after each change.
 *
 * @author Mateo Imbrišak
 */

public class ChangeCounter implements IntegerStorageObserver {

    /**
     * Keeps track of changes made.
     */
    private long changes;

    /**
     * Used after a value in the storage has been changed.
     *
     * @param istorage storage where the value has been changed.
     */
    @Override
    public void valueChanged(IntegerStorage istorage) {
        System.out.println("Number of value changes since tracking: " + ++changes);
    }
}
