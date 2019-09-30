package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

/**
 * A class that represents the pushd
 * {@code ShellCommand} in the shell.
 *
 * @author Mateo Imbrišak
 */

public class PushdShellCommand implements ShellCommand {

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
    @SuppressWarnings("unchecked")
    public ShellStatus executeCommand(Environment env, String arguments) {
        Path backup = env.getCurrentDirectory();
        Path newPath = Util.extractQuotes(arguments, env).normalize();
        env.commands().get("cd").executeCommand(env, arguments);

        if (!env.getCurrentDirectory().equals(newPath)) {
            return ShellStatus.CONTINUE;
        }

        if (env.getSharedData("cdstack") == null) {
            env.setSharedData("cdstack", new Stack<Path>());
        }

        Stack<Path> currentPath = (Stack<Path>) env.getSharedData("cdstack");

        currentPath.push(backup);

        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - pushd.
     */
    @Override
    public String getCommandName() {
        return "pushd";
    }

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Pushes the current directory to the stack and sets the argument as the current directory.");
    }
}
