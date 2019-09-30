package hr.fer.zemris.java.hw05.db;

/**
 * An exception thrown if something went wrong with
 * query command.
 *
 * @author Mateo Imbrišak
 */

public class QueryException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /** Constructs a new {@code QueryException} with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public QueryException() {
        super();
    }

    /** Constructs a new {@code QueryException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public QueryException(String message) {
        super(message);
    }
}
