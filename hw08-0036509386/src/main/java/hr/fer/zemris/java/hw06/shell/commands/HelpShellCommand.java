package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.List;

/**
 * A class that represents the help {@code ShellCommand}
 * in the shell.
 *
 * @author Mateo Imbrišak
 */

public class HelpShellCommand implements ShellCommand {

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
        arguments = arguments.trim();

        if (arguments.length() == 0) {
            env.writeln(getCommandName());

            for (ShellCommand command : env.commands().values()) {
                env.writeln(command.getCommandName());
            }

            return ShellStatus.CONTINUE;
        }

        for (String command : env.commands().keySet()) {
            if (command.equalsIgnoreCase(arguments)) {
                writeDescription(env, env.commands().get(command));
                return ShellStatus.CONTINUE;
            }
        }

        env.writeln("Unknown command.");

        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - Help.
     */
    @Override
    public String getCommandName() {
        return "Help";
    }

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "If given an argument writes the description of that command.",
                "If given no argument lists all commands.");
    }

    /**
     * Used internally to write the description of the
     * given {@code ShellCommand} to the given {@code Environment}.
     *
     * @param env {@code Environment} to be written to.
     * @param cmd {@code ShellCommand} whose description you want.
     */
    private void writeDescription(Environment env, ShellCommand cmd) {
        for (String line : cmd.getCommandDescription()) {
            env.writeln(line);
        }
    }
}
