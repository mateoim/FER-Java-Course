package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A class that represents list {@code Command}
 * in the shell.
 *
 * @author Mateo Imbrišak
 */
public class LsShellCommand implements ShellCommand {

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
        Path src = Util.extractQuotes(arguments);

        if (!Files.isDirectory(src)) {
            env.writeln("Argument must be a directory.");
            return ShellStatus.CONTINUE;
        }

        try {
            Files.walkFileTree(src, new ListVisitor(env));
        } catch (IOException exc) {
            env.writeln("Listing files failed.");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - ls.
     */
    @Override
    public String getCommandName() {
        return "ls";
    }

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Lists all files and directories in the given directory.");
    }

    /**
     * Used to go through the files in the given directory.
     */
    private static class ListVisitor implements FileVisitor<Path> {

        /**
         * Used to write the output to the user.
         */
        private Environment env;

        /**
         * Keeps track if the current directory is the one given
         * in command argument.
         */
        private boolean source = true;

        /**
         * Default constructor that passes the {@code Environment} to
         * be written to.
         *
         * @param env {@code Environment} to be written to.
         */
        public ListVisitor(Environment env) {
            this.env = env;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            if (!source) {
                StringBuilder toPrint = checkStats(dir);
                toPrint.append(" ");

                toPrint.append(formatSize(dir)).append(" ");

                toPrint.append(formatDateTime(dir)).append(" ");

                toPrint.append(dir.getFileName());

                env.writeln(toPrint.toString());

                return FileVisitResult.SKIP_SUBTREE;
            }

            source = false;

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            StringBuilder toPrint = checkStats(file);
            toPrint.append(" ");

            toPrint.append(formatSize(file)).append(" ");

            toPrint.append(formatDateTime(file)).append(" ");

            toPrint.append(file.getFileName());

            env.writeln(toPrint.toString());

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        /**
         * Used internally to check and write d/r/w/e
         * tags of the given file.
         *
         * @param file to be checked.
         *
         * @return a {@code StringBuilder} initialized
         * with d/r/w/e values for the current file.
         */
        private StringBuilder checkStats(Path file) {
            StringBuilder ret = new StringBuilder();

            ret.append(Files.isDirectory(file) ? "d" : "-");
            ret.append(Files.isReadable(file) ? "r" : "-");
            ret.append(Files.isWritable(file) ? "w" : "-");
            ret.append(Files.isExecutable(file) ? "x" : "-");

            return ret;
        }

        /**
         * Used internally to format the file size of the
         * given file.
         *
         * @param file to be checked.
         *
         * @return a {@code String} with correct size format.
         *
         * @throws IOException if file reading fails.
         */
        private String formatSize(Path file) throws IOException {
            String size = Long.toString(Files.size(file));
            return " ".repeat(10 - size.length()) + size;
        }

        /**
         * Used internally to print the correct date format.
         *
         * @param file to be checked.
         *
         * @return a {@code String} with correct date format.
         *
         * @throws IOException if file reading fails.
         */
        private String formatDateTime(Path file) throws IOException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            BasicFileAttributeView faView = Files.getFileAttributeView(
                    file, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS );
            BasicFileAttributes attributes = faView.readAttributes();
            FileTime fileTime = attributes.creationTime();
            return sdf.format(new Date(fileTime.toMillis()));
        }
    }
}
