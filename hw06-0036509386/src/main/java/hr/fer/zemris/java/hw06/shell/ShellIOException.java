package hr.fer.zemris.java.hw06.shell;

/**
 * A {@code RuntimeException} thrown when there
 * was an error getting user input or writing to
 * the user.
 *
 * @author Mateo Imbrišak
 */

public class ShellIOException extends RuntimeException {
    static final long serialVersionUID = 1L;

    /** Constructs a new {@code ShellIOException} exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ShellIOException() {
        super();
    }

    /** Constructs a new {@code ShellIOException} exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public ShellIOException(String message) {
        super(message);
    }
}
