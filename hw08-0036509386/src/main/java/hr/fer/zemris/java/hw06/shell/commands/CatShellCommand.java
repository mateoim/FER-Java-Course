package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * A class that represents the cat {@code Command}
 * in the shell.
 *
 * @author Mateo Imbrišak
 */

public class CatShellCommand implements ShellCommand {

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
        String forbidden = arguments.replace("\\\"", "\n\t");

        String[] parts = forbidden.split("\" ");
        if (parts.length == 1 && !arguments.endsWith("\"")) {
            parts = arguments.split(" ");
        }

        parts[0] = parts[0].replace("\"", "");
        parts[0] = parts[0].replace("\n\t", "\"");

        if (parts.length == 1) {
            try {
                writeAll(env, env.getCurrentDirectory().resolve(parts[0]), Charset.defaultCharset());
            } catch (UnsupportedCharsetException exc) {
                env.writeln("Invalid charset.");
            }
        } else if (parts.length == 2) {
            try {
                writeAll(env, env.getCurrentDirectory().resolve(parts[0]), Charset.forName(parts[1]));
            } catch (UnsupportedCharsetException exc) {
                env.writeln("Invalid charset.");
            }
        } else {
            env.writeln("Invalid number of arguments.");
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - Cat.
     */
    @Override
    public String getCommandName() {
        return "Cat";
    }

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Used to read a file. If one argument is passed write the given file using the",
                "default charset. If two arguments are given write the file using the given charset.");
    }

    /**
     * Used internally to write all lines from a file
     * to the given {@code Environment} using the given
     * {@code Charset}.
     *
     * @param env {@code Environment} to be written to.
     * @param src file to be written.
     * @param charset to be used for writing.
     */
    private void writeAll(Environment env, Path src, Charset charset) {
        try (InputStream is = Files.newInputStream(src)) {
            byte[] buffer = new byte[4096];

            while (true) {
                int r = is.read(buffer);

                if (r < 1) break;

                String toPrint = new String(Arrays.copyOf(buffer, r), charset);
                env.write(toPrint);
            }
        } catch (IOException exc) {
            env.writeln("Error while reading the file.");
        }
    }
}
