package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * A class that represents the cd
 * {@code ShellCommand} in the shell.
 *
 * @author Mateo Imbrišak
 */

public class CdShellCommand implements ShellCommand {

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
        Path newDir = Util.extractQuotes(arguments, env);

        if (!Files.exists(newDir)) {
            env.writeln("No such directory.");
            return ShellStatus.CONTINUE;
        } else if (!Files.isDirectory(newDir)) {
            env.writeln("Path must be to a directory.");
            return ShellStatus.CONTINUE;
        }

        env.setCurrentDirectory(newDir.normalize());

        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - cd.
     */
    @Override
    public String getCommandName() {
        return "cd";
    }

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Sets the working directory to the given one if it exists.");
    }
}
