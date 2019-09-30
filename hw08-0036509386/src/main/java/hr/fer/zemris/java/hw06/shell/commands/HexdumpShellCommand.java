package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * A class that represents hexdump {@code Command}
 * in the shell.
 *
 * @author Mateo Imbrišak
 */
public class HexdumpShellCommand implements ShellCommand {

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
        Path src = Util.extractQuotes(arguments, env);

        if (Files.isDirectory(src)) {
            env.writeln("Argument must be a file.");
            return ShellStatus.CONTINUE;
        }

        try {
            hexdump(env, src);
        } catch (ShellIOException exc) {
            env.writeln("Error while reading the file");
            return ShellStatus.CONTINUE;
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - Hexdump.
     */
    @Override
    public String getCommandName() {
        return "Hexdump";
    }

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Takes a single argument - path to a file.",
                "Creates hexdump output of the given file.");
    }

    /**
     * Used internally to create hexdump and write it
     * to the given {@code Environment}.
     *
     * @param env {@code Environment} to be written to.
     * @param file to convert to hexdump.
     *
     * @throws ShellIOException if the file cannot be opened.
     */
    private void hexdump(Environment env, Path file) {
        try (InputStream is = Files.newInputStream(file)) {
            byte[] buffer = new byte[16];
            StringBuilder toPrint = new StringBuilder();
            int currentLine = 0;

            while (true) {
                int r = is.read(buffer);

                if (r < 1) break;

                String hex = Integer.toHexString(currentLine);
                toPrint.append("0".repeat(8 - hex.length())).append(hex).append(": ");

                for (int i = 0; i < 16; i++) {
                    toPrint.append((i < r) ? String.format("%02X", buffer[i]) : " ".repeat(2));

                    toPrint.append((i == 7) ? "|" : " ");
                }

                toPrint.append("| ");

                for (int i = 0; i < r; i++) {
                    toPrint.append((buffer[i] < 32 || buffer[i] > 127) ? "." : (char) buffer[i]);
                }

                env.writeln(toPrint.toString());
                toPrint.delete(0, toPrint.length());
                currentLine += 16;
            }
        } catch (IOException exc) {
            throw new ShellIOException("Error.");
        }
    }
}
