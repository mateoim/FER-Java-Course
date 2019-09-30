package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A class that represents the copy {@code Command}
 * in the shell.
 *
 * @author Mateo Imbrišak
 */
public class CopyShellCommand implements ShellCommand {

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
        String[] parts = forbidden.split("[ \"][\" ]");

        if (parts.length == 1) {
            parts = arguments.split(" ");
        } else if (parts.length == 2) {
            parts[0] = parts[0].replace("\"", "");
            parts[1] = parts[1].replace("\"", "");
        } else {
            env.writeln("Invalid number of arguments.");
            return ShellStatus.CONTINUE;
        }

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].replace("\n\t", "\"");
        }

        Path src = Paths.get(parts[0]);
        Path dest = Paths.get(parts[1]);

        if (Files.isDirectory(src)) {
            env.writeln("Source file cannot be a directory");
        }

        if (!Files.isDirectory(dest) && Files.exists(dest)) {
            checkForOverwrite(env, src, dest);
        } else if (Files.isDirectory(dest)) {

            String pathString = dest.toString();

            if (pathString.endsWith("/")) {
                pathString += src.getFileName();
            } else {
                pathString += "/" + src.getFileName();
            }

            Path newDest = Paths.get(pathString);

            if (Files.exists(newDest)) {
                if (!checkForOverwrite(env, src, newDest)) {
                    return ShellStatus.CONTINUE;
                }
            }

            try {
                copyFile(src, newDest);
            } catch (IOException exc) {
                env.writeln("Error while copying the file.");
            }
        } else {
            try {
                copyFile(src, dest);
            } catch (IOException exc) {
                env.writeln("Error while copying the file.");
            }
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - Copy.
     */
    @Override
    public String getCommandName() {
        return "Copy";
    }

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Copies the file in the first parameter to the path in the second.",
                "If the second argument is a file that exists asks the user whether to overwrite it.",
                "If no name is provided, copies it with the same name.");
    }

    /**
     * Used internally to copy the file.
     *
     * @param src file being copied.
     * @param dest destination of the copied file.
     *
     * @throws IOException if there was an error while copying.
     */
    private void copyFile(Path src, Path dest) throws IOException {
        try (InputStream is = Files.newInputStream(src);
             OutputStream os = Files.newOutputStream(dest)) {
            byte[] buffer = new byte[4096];

            while (true) {
                int r = is.read(buffer);

                if (r < 1) break;

                os.write(buffer, 0, r);
            }

        }
    }

    /**
     * Used to ask the user if they want to overwrite the
     * destination file.
     *
     * @param env {@code Environment} used to communicate with the user.
     * @param src source file.
     * @param dest destination file.
     *
     * @return {@code true} if the user answered with yes,
     * {@code false} if the user answered with no.
     */
    private boolean checkForOverwrite(Environment env, Path src, Path dest) {
        env.writeln("File " + dest.getFileName() + " already exists.\nDo you want to overwrite it? [y/n]");
        env.write(env.getPromptSymbol() + " ");
        String userInput = env.readLine();

        switch (userInput) {
            case "y":
                try {
                    copyFile(src, dest);
                } catch (IOException exc) {
                    env.writeln("Error while copying the file.");
                }
                return true;
            case "n":
                env.writeln("Operation aborted.");
                return false;
            default:
                env.writeln("Invalid input.");
                return checkForOverwrite(env, src, dest);
        }
    }
}
