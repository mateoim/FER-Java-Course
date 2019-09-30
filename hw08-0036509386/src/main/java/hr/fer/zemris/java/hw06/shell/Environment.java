package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * An interface that defines the environment used
 * to communicate with the user.
 *
 * @author Mateo Imbrišak
 */

public interface Environment {

    /**
     * Used to receive user input.
     *
     * @return user's input as a {@code String}.
     *
     * @throws ShellIOException if an error occurred
     * while getting the input.
     */
    String readLine() throws ShellIOException;

    /**
     * Writes the given {@code String} to the user.
     *
     * @param text to be written.
     *
     * @throws ShellIOException if an error occurred
     * while writing.
     */
    void write(String text) throws ShellIOException;

    /**
     * Writes the given {@code String} to the user
     * as a line.
     *
     * @param text to be written.
     *
     * @throws ShellIOException if an error occurred
     * while writing.
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Provides a {@code List} of all available {@code ShellCommands}.
     *
     * @returna {@code List} of {@code ShellCommands}.
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Provides the multiline symbol.
     *
     * @return multiline symbol.
     */
    Character getMultilineSymbol();

    /**
     * Sets the multiline symbol to the given value.
     *
     * @param symbol to be set.
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Provides the prompt symbol.
     *
     * @return prompt symbol.
     */
    Character getPromptSymbol();

    /**
     * Sets the prompt symbol to the given value.
     *
     * @param symbol to be set.
     */
    void setPromptSymbol(Character symbol);

    /**
     * Provides the morelines symbol.
     *
     * @return morelines symbol.
     */
    Character getMorelinesSymbol();

    /**
     * Sets the morelines symbol to the given value.
     *
     * @param symbol to be set.
     */
    void setMorelinesSymbol(Character symbol);

    /**
     * Provides the current working directory.
     *
     * @return current working directory as a {@code Path}.
     */
    Path getCurrentDirectory();

    /**
     * Sets the current working directory to the provided one.
     *
     * @param path to the new working directory.
     */
    void setCurrentDirectory(Path path);

    /**
     * Provides data stored based on the given key.
     *
     * @param key of the stored data.
     *
     * @return data stored under the given key.
     */
    Object getSharedData(String key);

    /**
     * Stores the given data with the given key.
     *
     * @param key of the data to be stored.
     * @param value to be stored.
     */
    void setSharedData(String key, Object value);
}
