package hr.fer.zemris.java.hw06.shell;

import hr.fer.zemris.java.hw06.shell.commands.*;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A class that represents a simple interactive shell.
 *
 * @author Mateo Imbrišak
 */

public class MyShell {

    /**
     * Used to start the program.
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            DefaultEnvironment env = new DefaultEnvironment(sc);

            env.addCommand("exit", new ExitShellCommand());
            env.addCommand("symbol", new SymbolShellCommand());
            env.addCommand("charsets", new CharsetsShellCommand());
            env.addCommand("cat", new CatShellCommand());
            env.addCommand("ls", new LsShellCommand());
            env.addCommand("tree", new TreeShellCommand());
            env.addCommand("mkdir", new MkdirShellCommand());
            env.addCommand("copy", new CopyShellCommand());
            env.addCommand("hexdump", new HexdumpShellCommand());
            env.addCommand("help", new HelpShellCommand());

            env.setPromptSymbol('>');
            env.setMorelinesSymbol('\\');
            env.setMultilineSymbol('|');

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
         * Default constructor that passes the {@code Scanner} to
         * be used for user input.
         *
         * @param sc {@code Scanner} to be used.
         */
        public DefaultEnvironment(Scanner sc) {
            registeredCommands = new TreeMap<>();
            this.sc = sc;
        }

        /**
         * Used by the shell to add available {@code ShellCommand}s to
         * the {@code Environment}.
         *
         * @param name of the command.
         * @param command represented as an {@code Object}.
         */
        private void addCommand(String name, ShellCommand command) {
            registeredCommands.put(name, command);
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
