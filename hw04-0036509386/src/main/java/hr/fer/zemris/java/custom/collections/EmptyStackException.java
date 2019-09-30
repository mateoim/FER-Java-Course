package hr.fer.zemris.java.custom.collections;

/**
 * An exception thrown when attempting to remove an element from an empty {@code ObjectStack}.
 *
 * @author Mateo Imbrišak
 */
public class EmptyStackException extends RuntimeException {
    static final long serialVersionUID = 1L;

    /**
     * Constructs an {@code EmptyStackException} with no detail
     * message.
     */
    public EmptyStackException() {
        super();
    }

    /**
     * Constructs an {@code EmptyStackException} class with the
     * specified detail message.
     *
     * @param s the detail message.
     */
    public EmptyStackException(String s) {
        super(s);
    }
}
