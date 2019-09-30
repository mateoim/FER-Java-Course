package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * A class that represents tree {@code Command}
 * in the shell.
 *
 * @author Mateo Imbrišak
 */

public class TreeShellCommand implements ShellCommand {

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

        if (!Files.isDirectory(src)) {
            env.writeln("Argument must be a directory.");
            return ShellStatus.CONTINUE;
        }

        try {
            Files.walkFileTree(src, new TreeVisitor(env));
        } catch (IOException exc) {
            env.writeln("Listing files failed.");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - Tree.
     */
    @Override
    public String getCommandName() {
        return "Tree";
    }

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Lists all subdirectories and files in the given directory as a tree with two spaces per level.");
    }

    /**
     * Used to go through the files in the given directory.
     */
    private static class TreeVisitor implements FileVisitor<Path> {

        /**
         * Keeps track of current indentation level.
         */
        private int level = 0;

        /**
         * Used to write the output to the user.
         */
        private Environment env;

        /**
         * Default constructor that passes the {@code Environment} to
         * be written to.
         *
         * @param env {@code Environment} to be written to.
         */
        public TreeVisitor(Environment env) {
            this.env = env;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            env.writeln(" ".repeat(level) + dir.getFileName());
            level += 2;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            env.writeln(" ".repeat(level) + file.getFileName());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            level -= 2;
            return FileVisitResult.CONTINUE;
        }
    }
}
