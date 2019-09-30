package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class that is used to help with irregular path names.
 *
 * @author Mateo Imbrišak
 */

public class Util {

    /**
     * Used to extract path names form quotes and preserve escaped
     * quotes by replacing them with an illegal directory name sequence.
     *
     * @param arguments {@code String} containing path name.
     *
     * @return requested {@code Path}.
     */
    public static Path extractQuotes(String arguments) {
        String forbidden = arguments.replace("\\\"", "\n\t");
        forbidden = forbidden.replace("\"", "");

        return Paths.get(forbidden.replace("\n\t", "\""));
    }
}
