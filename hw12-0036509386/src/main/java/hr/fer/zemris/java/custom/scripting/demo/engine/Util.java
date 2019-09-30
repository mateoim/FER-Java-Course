package hr.fer.zemris.java.custom.scripting.demo.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class that contains utilities for
 * reading SmartScript files from the disk.
 *
 * @author Mateo Imbrišak
 */

public class Util {

    /**
     * Used to read a SmartScript file form the disk.
     *
     * @param path to the file.
     *
     * @return given file as a single {@code String}.
     *
     * @throws RuntimeException if an error occurs
     * while reading the file.
     */
    public static String readFromDisk(String path) {
        Path src = Paths.get(path);

        try {
            return Files.readString(src);
        } catch (IOException exc) {
            throw new RuntimeException("Error while reading the file.");
        }
    }
}
