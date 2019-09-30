package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

/**
 * A class that represents the charset {@code Command}
 * in the shell.
 *
 * @author Mateo Imbrišak
 */

public class CharsetsShellCommand implements ShellCommand {

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
        if (arguments.length() != 0) {
            env.writeln("Charset command doesn't take any arguments");
            return ShellStatus.CONTINUE;
        }

        Set<String> charsets = Charset.availableCharsets().keySet();

        for (String charset : charsets) {
            env.writeln(charset);
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - "Charsets".
     */
    @Override
    public String getCommandName() {
        return "Charsets";
    }

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Used to list all available charsets. Takes no arguments.");
    }
}
