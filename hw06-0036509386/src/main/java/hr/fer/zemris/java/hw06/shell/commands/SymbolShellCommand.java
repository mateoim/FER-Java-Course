package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.List;

/**
 * A class that represents the symbol {@code Command}
 * in the shell.
 *
 * @author Mateo Imbrišak
 */

public class SymbolShellCommand implements ShellCommand {

    /**
     * Executes the {@code Command}.
     *
     * @param env {@code Environment} in which the {@code Command}
     *                               is executed.
     * @param arguments of the {@code Command}.
     *
     * @return status used by the shell.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split("\\s+");

        switch (args.length) {
            case 1:
                switch (args[0]) {
                    case "PROMPT":
                        env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
                        break;
                    case "MORELINES":
                        env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
                        break;
                    case "MULTILINE":
                        env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
                        break;
                    default:
                        env.writeln("Unknown symbol requested.");
                }
                break;
            case 2:
                if (args[1].length() != 1) {
                    env.writeln("Provided symbol must be a single character.");
                    break;
                }

                switch (args[0]) {
                    case "PROMPT":
                        env.write("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "'");
                        env.setPromptSymbol(args[1].charAt(0));
                        env.writeln("' to '" + env.getPromptSymbol() + "'");
                        break;
                    case "MORELINES":
                        env.write("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "'");
                        env.setMorelinesSymbol(args[1].charAt(0));
                        env.writeln("' to '" + env.getMorelinesSymbol() + "'");
                        break;
                    case "MULTILINE":
                        env.write("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "'");
                        env.setMultilineSymbol(args[1].charAt(0));
                        env.writeln("' to '" + env.getMultilineSymbol() + "'");
                        break;
                    default:
                        env.writeln("Unknown symbol change requested.");
                }
                break;
            default:
                env.writeln("Invalid number of arguments.");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Provides the name of this {@code Command}.
     *
     * @return name of this {@code Command} - "SymbolShellCommand".
     */
    @Override
    public String getCommandName() {
        return "Symbol";
    }

    /**
     * Provides the description of this command as an
     * unmodifiable {@code List} of {@code String}s.
     *
     * @return description of this {@code Command}.
     */
    @Override
    public List<String> getCommandDescription() {
        return List.of(getCommandName(),
                "Used to view or change various symbols used in the shell.",
                "When used with a single argument that represents the usage of this symbol in the shell,",
                "it displays the symbol used for that function.",
                "When used with two arguments the second argument represents a new symbol you want to use",
                "with the given function.");
    }
}
