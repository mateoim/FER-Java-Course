package hr.fer.zemris.java.hw07.observer1;

/**
 * An observer that prints the square value of the new value.
 *
 * @author Mateo Imbrišak
 */

public class SquareValue implements IntegerStorageObserver {

    /**
     * Used after a value in the storage has been changed.
     *
     * @param istorage storage where the value has been changed.
     */
    @Override
    public void valueChanged(IntegerStorage istorage) {
        System.out.println("Provided new value: " + istorage.getValue() + ", square is "
                + istorage.getValue() * istorage.getValue());
    }
}
