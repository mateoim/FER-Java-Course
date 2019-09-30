package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.List;

/**
 * A class that represents the exit {@code Command}
 * in the shell.
 *
 * @author Mateo Imbrišak
 */

public class ExitShellCommand implements ShellCommand {

    /**
     * Executes the command attempting to exit the shell.
     *
     * @param env       {@code Environment} in which the {@code Command}
     *                                     is executed.
     * @param arguments of the {@code Command}.
     *
     * @return continue signal if passed with arguments,
     * otherwise terminate.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("Exit command doesn't take any arguments");
            return ShellStatus.CONTINUE;
        }

        return ShellStatus.TERMINATE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - "Exit".
     */
    @Override
    public String getCommandName() {
        return "Exit";
    }

    /**
     * Provides the description of this command as an
     * unmodifiable {@code List} of {@code String}s.
     *
     * @return description of this {@code Command}.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Used to quit the shell");
    }
}
