package hr.fer.zemris.java.hw07.observer1;

/**
 * An observer that prints the double value
 * of the new value.
 *
 * @author Mateo Imbrišak
 */

public class DoubleValue implements IntegerStorageObserver {

    /**
     * Keeps the maximum number of outputs that
     * can be made.
     */
    private final long maxOutput;

    /**
     * Keeps the current number of outputs made.
     */
    private long currentOutput;

    /**
     * Default constructor that assigns the maximum number
     * of outputs that can be produced.
     *
     * @param maxOutput maximum number of outputs
     *                  that can be produced.
     */
    public DoubleValue(long maxOutput) {
        this.maxOutput = maxOutput;
    }

    /**
     * Used after a value in the storage has been changed.
     *
     * @param istorage storage where the value has been changed.
     */
    @Override
    public void valueChanged(IntegerStorage istorage) {
        System.out.println("Double value: " + istorage.getValue() * 2);
        currentOutput++;

        if (currentOutput == maxOutput) {
            istorage.removeObserver(this);
        }
    }
}
