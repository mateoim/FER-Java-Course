package hr.fer.zemris.java.hw06.shell;

/**
 * An enumeration used to communicate
 * the behavior of {@code Command}s to
 * the shell.
 *
 * @author Mateo Imbrišak
 */

public enum ShellStatus {
    /**
     * Used to indicate that the shell should
     * continue working.
     */
    CONTINUE,

    /**
     * Used to indicate that the shell should
     * stop and exit the program.
     */
    TERMINATE
}
