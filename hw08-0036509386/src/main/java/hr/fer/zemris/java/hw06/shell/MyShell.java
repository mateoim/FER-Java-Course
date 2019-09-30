package hr.fer.zemris.java.hw06.shell;

import hr.fer.zemris.java.hw06.shell.commands.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * A class that represents a simple interactive shell.
 *
 * @author Mateo Imbrišak
 */

public class MyShell {

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            DefaultEnvironment env = new DefaultEnvironment(sc);

            env.setPromptSymbol('>');
            env.setMorelinesSymbol('\\');
            env.setMultilineSymbol('|');

            env.setCurrentDirectory(Paths.get(""));

            env.writeln("Welcome to MyShell v 1.0");

            ShellStatus status = ShellStatus.CONTINUE;

            do {
                env.write(env.getPromptSymbol() + " ");
                StringBuilder inputSB = new StringBuilder();

                do {
                    try {
                        inputSB.append(sc.nextLine());
                        if (inputSB.toString().endsWith(Character.toString(env.getMorelinesSymbol()))) {
                            env.write(env.getMultilineSymbol() + " ");
                            inputSB.append("\n");
                        }
                    } catch (ShellIOException exc) {
                        env.writeln(exc.getMessage());
                        status = ShellStatus.TERMINATE;
                        break;
                    }
                } while (inputSB.toString().endsWith(env.getMorelinesSymbol() + "\n"));

                if (status == ShellStatus.TERMINATE) {
                    break;
                }

                String input = inputSB.toString().replace(env.getMorelinesSymbol() + "\n", "");
                input = input.replace("\\\\", "\\");

                if (input.isBlank()) {
                    status = ShellStatus.CONTINUE;
                    continue;
                }

                String[] parts = input.split(" ");

                String commandName = parts[0];
                String arguments = "";

                if (parts.length > 1) {
                    arguments = input.substring(input.indexOf(" ") + 1);
                    if (!commandName.equalsIgnoreCase("symbol") &&
                            !commandName.equalsIgnoreCase("help") &&
                            !commandName.equalsIgnoreCase("exit") &&
                    !commandName.equalsIgnoreCase("charsets")) {
                        if (!checkValidity(arguments)) {
                            env.writeln("Invalid path.");
                            status = ShellStatus.CONTINUE;
                            continue;
                        }
                    }
                }

                ShellCommand command = env.commands().get(commandName);
                if (command != null) {
                    try {
                        status = command.executeCommand(env, arguments);
                    } catch (ShellIOException exc) {
                        env.writeln(exc.getMessage());
                        status = ShellStatus.CONTINUE;
                    }
                } else {
                    env.writeln("Unknown command.");
                    status = ShellStatus.CONTINUE;
                }
            } while (status != ShellStatus.TERMINATE);
        }
    }

    /**
     * Used as the default {@code Environment} in the shell.
     */
    private static class DefaultEnvironment implements Environment {

        /**
         * Keeps all available commands.
         */
        private SortedMap<String, ShellCommand> registeredCommands;

        /**
         * keeps the prompt symbol.
         */
        private char promptSymbol;

        /**
         * keeps the multiline symbol.
         */
        private char multilineSymbol;

        /**
         * keeps the morelines symbol.
         */
        private char morelinesSymbol;

        /**
         * Keeps the scanner used by the user.
         */
        private Scanner sc;

        /**
         * Keeps the {@code Path} to the current directory.
         */
        private Path currentDirectory;

        /**
         * Keeps data shared between commands.
         */
        private Map<String, Object> sharedData;

        /**
         * Default constructor that passes the {@code Scanner} to
         * be used for user input.
         *
         * @param sc {@code Scanner} to be used.
         */
        public DefaultEnvironment(Scanner sc) {
            registeredCommands = new TreeMap<>();
            this.sc = sc;
            sharedData = new HashMap<>();

            registeredCommands.put("exit", new ExitShellCommand());
            registeredCommands.put("symbol", new SymbolShellCommand());
            registeredCommands.put("charsets", new CharsetsShellCommand());
            registeredCommands.put("cat", new CatShellCommand());
            registeredCommands.put("ls", new LsShellCommand());
            registeredCommands.put("tree", new TreeShellCommand());
            registeredCommands.put("mkdir", new MkdirShellCommand());
            registeredCommands.put("copy", new CopyShellCommand());
            registeredCommands.put("hexdump", new HexdumpShellCommand());
            registeredCommands.put("help", new HelpShellCommand());

            registeredCommands.put("pwd", new PwdShellCommand());
            registeredCommands.put("cd", new CdShellCommand());
            registeredCommands.put("pushd", new PushdShellCommand());
            registeredCommands.put("popd", new PopdShellCommand());
            registeredCommands.put("listd", new ListdShellCommand());
            registeredCommands.put("dropd", new DropdShellCommand());
            registeredCommands.put("massrename", new MassrenameShellCommand());
        }

        @Override
        public String readLine() throws ShellIOException {
            try {
                return sc.nextLine().trim();
            } catch (NoSuchElementException | IllegalStateException exc) {
                throw new ShellIOException("Error while reading user input.");
            }
        }

        @Override
        public void write(String text) throws ShellIOException {
            System.out.print(text);
        }

        @Override
        public void writeln(String text) throws ShellIOException {
            System.out.println(text);
        }

        @Override
        public SortedMap<String, ShellCommand> commands() {
            return registeredCommands;
        }

        @Override
        public Character getMultilineSymbol() {
            return multilineSymbol;
        }

        @Override
        public void setMultilineSymbol(Character symbol) {
            multilineSymbol = symbol;
        }

        @Override
        public Character getPromptSymbol() {
            return promptSymbol;
        }

        @Override
        public void setPromptSymbol(Character symbol) {
            promptSymbol = symbol;
        }

        @Override
        public Character getMorelinesSymbol() {
            return morelinesSymbol;
        }

        @Override
        public void setMorelinesSymbol(Character symbol) {
            morelinesSymbol = symbol;
        }

        @Override
        public Path getCurrentDirectory() {
            return currentDirectory;
        }

        @Override
        public void setCurrentDirectory(Path path) {
            if (Files.exists(path) && Files.isDirectory(path)) {
                currentDirectory = path.toAbsolutePath();
            } else {
                throw new RuntimeException("Path is not a directory.");
            }
        }

        @Override
        public Object getSharedData(String key) {
            return sharedData.get(key);
        }

        @Override
        public void setSharedData(String key, Object value) {
            sharedData.put(key, value);
        }
    }

    /**
     * Used internally to check it the given input contains a valid path.
     *
     * @param arg {@code String} representing a path.
     *
     * @return {@code true} if the path is valid,
     * otherwise {@code false}.
     */
    private static boolean checkValidity(String arg) {
        boolean isQuoteOpen = false;

        char[] argChars = arg.toCharArray();

        if (arg.startsWith("\"")) {
            isQuoteOpen = true;
        }

        for (int i = 1; i < argChars.length; i++) {
            if (argChars[i] == '\"') {
                if (isQuoteOpen) {
                    if (argChars[i - 1] != '\\') {
                        isQuoteOpen = false;
                        if (i + 1 < argChars.length && argChars[i + 1] != ' ') {
                            return false;
                        }
                    }
                } else if (argChars[i - 1] == ' ') {
                    isQuoteOpen = true;
                } else {
                    return false;
                }
            }
        }

        return !isQuoteOpen;
    }
}
