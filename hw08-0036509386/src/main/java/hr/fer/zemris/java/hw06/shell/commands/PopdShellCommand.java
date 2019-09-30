package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Stack;

/**
 * A class that represents the popd
 * {@code ShellCommand} in the shell.
 *
 * @author Mateo Imbrišak
 */

public class PopdShellCommand implements ShellCommand {

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
        if (arguments.length() != 0) {
            env.writeln("Command popd doesn't take any arguments.");
            return ShellStatus.CONTINUE;
        }

        Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");

        if (stack == null || stack.empty()) {
            env.writeln("The stack is empty.");
            return ShellStatus.CONTINUE;
        }

        Path newDir = stack.pop();

        if (!Files.exists(newDir)) {
            env.writeln("Directory " + newDir.toString() + " no longer exists.");
            return ShellStatus.CONTINUE;
        }

        env.commands().get("cd").executeCommand(env, newDir.toString());

        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - popd.
     */
    @Override
    public String getCommandName() {
        return "popd";
    }

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Sets the working directory to the one on the top of the stack.",
                "Doesn't take any arguments.");
    }
}
