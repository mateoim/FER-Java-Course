package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;

import java.nio.file.Path;

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
     * @param env       {@code Environment} containing the current directory.
     *
     * @return requested {@code Path}.
     */
    public static Path extractQuotes(String arguments, Environment env) {
        String forbidden = arguments.replace("\\\"", "\n\t");
        forbidden = forbidden.replace("\"", "");

        return env.getCurrentDirectory().resolve(forbidden.replace("\n\t", "\""));
    }
}
