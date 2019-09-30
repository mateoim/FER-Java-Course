package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * A class that represents mkdir {@code Command}
 * in the shell.
 *
 * @author Mateo Imbrišak
 */

public class MkdirShellCommand implements ShellCommand {

    /**
     * Executes this command with the given arguments.
     *
     * @param env       {@code Environment} in which the {@code Command}
     *                  is executed.
     * @param arguments of the {@code Command}.
     *
     * @return a signal to the shell in form of a {@code ShellStatus}.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        Path dir = Util.extractQuotes(arguments, env);

        try {
            Files.createDirectories(dir);
        } catch (IOException exc) {
            env.writeln("Error creating directories.");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - mkdir.
     */
    @Override
    public String getCommandName() {
        return "mkdir";
    }

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Creates the given directory and all missing directories in the path.");
    }
}
