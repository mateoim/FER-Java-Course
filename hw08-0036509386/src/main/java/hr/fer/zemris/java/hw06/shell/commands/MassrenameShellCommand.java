package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.massrename.util.FilterResult;
import hr.fer.zemris.java.hw06.shell.commands.massrename.util.NameBuilder;
import hr.fer.zemris.java.hw06.shell.commands.massrename.util.NameBuilderParser;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that represents the massrename
 * {@code ShellCommand} in the shell.
 *
 * @author Mateo Imbrišak
 */

public class MassrenameShellCommand implements ShellCommand {

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
        List<String> argParts = parseArgs(arguments);
        if (argParts.size() < 4) {
            env.writeln("Number of arguments must be 4 or 5");
        }

        Path src = env.getCurrentDirectory().resolve(argParts.get(0));
        Path dest = env.getCurrentDirectory().resolve(argParts.get(1));

        switch (argParts.get(2)) {
            case "filter":
                if (argParts.size() != 4) {
                    env.writeln("Number of arguments must be 4.");
                }

                try {
                    List<FilterResult> results = filter(src, argParts.get(3));
                    for (FilterResult result : results) {
                        env.writeln(result.toString());
                    }
                    return ShellStatus.CONTINUE;
                } catch (IOException exc) {
                    env.writeln("Error while reading the files.");
                    return ShellStatus.CONTINUE;
                }
            case "groups":
                if (argParts.size() != 4) {
                    env.writeln("Number of arguments must be 4.");
                }

                try {
                    List<FilterResult> results = filter(src, argParts.get(3));
                    for (FilterResult result : results) {
                        env.write(result.toString() + " ");
                        for (int i = 0; i < result.numberOfGroups(); i++) {
                            env.write(i + ": " + result.group(i) + " ");
                        }
                        env.write("\n");
                    }
                    return ShellStatus.CONTINUE;
                } catch (IOException exc) {
                    env.writeln("Error while reading the files.");
                    return ShellStatus.CONTINUE;
                }
            case "show":
                if (argParts.size() != 5) {
                    env.writeln("Number of arguments must be 5.");
                }

                try {
                    List<FilterResult> results = filter(src, argParts.get(3));
                    NameBuilderParser parser = new NameBuilderParser(argParts.get(4));
                    NameBuilder builder = parser.getNameBuilder();

                    for (FilterResult result : results) {
                        StringBuilder sb = new StringBuilder();
                        builder.execute(result, sb);
                        env.writeln(result.toString() + " => " + sb.toString());
                    }

                    return ShellStatus.CONTINUE;
                } catch (IOException exc) {
                    env.writeln("Error while reading the files.");
                    return ShellStatus.CONTINUE;
                } catch (RuntimeException exc) {
                    env.writeln(exc.getMessage());
                    return ShellStatus.CONTINUE;
                }
            case "execute":
                if (argParts.size() != 5) {
                    env.writeln("Number of arguments must be 5.");
                }

                try {
                    List<FilterResult> results = filter(src, argParts.get(3));
                    NameBuilderParser parser = new NameBuilderParser(argParts.get(4));
                    NameBuilder builder = parser.getNameBuilder();

                    for (FilterResult result : results) {
                        StringBuilder sb = new StringBuilder();
                        builder.execute(result, sb);

                        env.writeln(src.toString().endsWith("/") ? src.toString() : (src.toString() + "/")
                                + result.toString() + " => "
                                + (dest.toString().endsWith("/") ? dest.toString() : (dest.toString() + "/"))
                                + sb.toString());

                        Files.move(src.resolve(Paths.get(result.toString())), dest.resolve(sb.toString()),
                                StandardCopyOption.REPLACE_EXISTING);
                    }

                    return ShellStatus.CONTINUE;
                } catch (IOException exc) {
                    env.writeln("Error while reading the files.");
                    return ShellStatus.CONTINUE;
                } catch (RuntimeException exc) {
                    env.writeln(exc.getMessage());
                    return ShellStatus.CONTINUE;
                }
            default:
                env.writeln("Unknown command.");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - massrename.
     */
    @Override
    public String getCommandName() {
        return "massrename";
    }

    /**
     * Provides a description of this {@code ShellCommand}.
     *
     * @return description as a {@code List} of {@code String}s.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Takes 4 or 5 arguments. First argument is source directory.",
                "Second argument is destination directory. Third argument is function name.",
                "Fourth argument is a regular expression representing the name style of files to be moved.",
                "Fifth argument represents the new names of files that are moved.",
                "Commands:",
                "filter - prints names of all selected files.",
                "groups - prints all groups of selected files.",
                "show - prints old names => new names",
                "execute - moves and renames the selected files");
    }

    /**
     * Used internally to filter the files in source directory.
     *
     * @param dir source directory.
     * @param pattern to be used on the files.
     *
     * @return a {@code List} of acceptable files.
     *
     * @throws IOException if an error occurred while reading
     * the files in source directory.
     */
    private static List<FilterResult> filter(Path dir, String pattern) throws IOException {
        ListVisitor visitor = new ListVisitor(pattern);

        Files.walkFileTree(dir, visitor);

        return visitor.list;
    }

    /**
     * Used internally to parse the input arguments.
     *
     * @param args arguments being parsed.
     *
     * @return a {@code List} of split arguments.
     */
    private List<String> parseArgs(String args) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (!args.isBlank()) {
                int newStart = parsePart(list, args);

                args = args.substring(newStart).trim();
            }
        }

        if (!args.isBlank()) {
            list.add(args.trim());
        }

        return list;
    }

    /**
     * Used internally to parse the next part of the argument.
     *
     * @param list of all parts so far.
     * @param args a {@code String} containing the remaining arguments.
     *
     * @return position of the next letter in the arguments to be parsed.
     */
    private int parsePart(List<String> list, String args) {
        if (args.startsWith("\"")) {
            boolean quote = true;
            char[] array = args.toCharArray();
            int index = 1;
            StringBuilder sb = new StringBuilder();

            do {
                if (array[index] == '\"') {
                    if (array[index - 1] == '\\') {
                        sb.deleteCharAt(sb.length() - 1);
                        sb.append("\"");
                        index++;
                    } else {
                        quote = false;
                    }
                } else {
                    sb.append(array[index]);
                    index++;
                }
            } while (quote);

            list.add(sb.toString());
            return index + 1;
        } else {
            int lastIndex = args.indexOf(" ");

            if (lastIndex == -1) {
                list.add(args.trim());
                return args.length();
            } else {
                list.add(args.substring(0, lastIndex));
                return lastIndex;
            }
        }
    }

    /**
     * A {@code FileVisitor} used to select files
     * that match the pattern and create a {@code List}
     * of {@code FilterResults}.
     */
    private static class ListVisitor implements FileVisitor<Path> {

        /**
         * Keeps the pattern used to test the files.
         */
        private Pattern pattern;

        /**
         * Keeps the list of acceptable results.
         */
        private List<FilterResult> list;

        /**
         * Keeps track if the current directory is the one given
         * in command argument.
         */
        private boolean source = true;

        /**
         * Default constructor that assigns the pattern and
         * initializes the list.
         *
         * @param pattern to be assigned.
         */
        public ListVisitor(String pattern) {
            this.pattern = Pattern.compile(pattern);
            list = new ArrayList<>();
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            if (!source) {
                return FileVisitResult.SKIP_SUBTREE;
            }

            source = false;

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Matcher matcher = pattern.matcher(file.getFileName().toString());

            if (matcher.matches()) {
                list.add(new FilterResult(file, pattern));
            }

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
    }
}
