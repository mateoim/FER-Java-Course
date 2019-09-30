package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Used as a template for all commands in the shell.
 *
 * @author Mateo Imbrišak
 */

public interface ShellCommand {

    /**
     * Executes this command with the given arguments.
     *
     * @param env {@code Environment} in which the {@code Command}
     *                               is executed.
     * @param arguments of the {@code Command}.
     *
     * @return a signal to the shell in form of a {@code ShellStatus}.
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command}.
     */
    String getCommandName();

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    List<String> getCommandDescription();
}
