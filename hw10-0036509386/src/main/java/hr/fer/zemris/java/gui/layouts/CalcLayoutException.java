package hr.fer.zemris.java.gui.layouts;

/**
 * An {@code Exception} thrown by the {@code CalcLayout}.
 *
 * @author Mateo Imbrišak
 */

public class CalcLayoutException extends RuntimeException {
    static final long serialVersionUID = 1L;

    /** Constructs a new {@code CalcLayoutException} with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public CalcLayoutException() {
        super();
    }

    /** Constructs a new {@code CalcLayoutException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public CalcLayoutException(String message) {
        super(message);
    }
}
