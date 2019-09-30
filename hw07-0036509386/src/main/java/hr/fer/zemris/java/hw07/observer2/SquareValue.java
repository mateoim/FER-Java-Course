package hr.fer.zemris.java.hw07.observer2;

/**
 * An observer that prints the square value of the new value.
 *
 * @author Mateo Imbrišak
 */

public class SquareValue implements IntegerStorageObserver {

    /**
     * Used after a value in the storage has been changed.
     *
     * @param istorage copy of the storage where the value has been changed.
     */
    @Override
    public void valueChanged(IntegerStorageChange istorage) {
        System.out.println("Provided new value: " + istorage.getNewValue() + ", square is "
                + istorage.getNewValue() * istorage.getNewValue());
    }
}
