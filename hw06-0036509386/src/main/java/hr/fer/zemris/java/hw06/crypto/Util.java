package hr.fer.zemris.java.hw06.crypto;

/**
 * A class that contains static methods used to convert
 * {@code String}s into {@code byte} arrays and {@code byte}
 * arrays into {@code String}s.
 *
 * @author Mateo Imbrišak
 */

public class Util {

    /**
     * Converts a {@code String} input to a {@code byte} array.
     *
     * @param keyText input to be converted.
     *
     * @return a {@code byte} array based in the given input.
     *
     * @throws IllegalArgumentException if the input is odd-length
     * or contains non hexadecimal characters.
     */
    public static byte[] hextobyte(String keyText) {
        if (keyText.length() % 2 != 0) {
            throw new IllegalArgumentException("Input cannot be odd-length.");
        }

        byte[] ret = new byte[keyText.length() / 2];

        for (int i = 0; i < keyText.length() / 2; i++) {
            char first = keyText.charAt(2 * i);
            char second = keyText.charAt(2 * i + 1);

            ret[i] = (byte) ((processToNumeric(first) << 4) | processToNumeric(second));
        }

        return ret;
    }

    /**
     * Converts an array of {@code byte}s to a {@code String}
     * in hexadecimal representation.
     *
     * @param bytearray array you want to convert.
     *
     * @return a {@code String} that represents the given array.
     */
    public static String bytetohex(byte[] bytearray) {
        StringBuilder ret = new StringBuilder();

        for (byte b : bytearray) {
            int first = (b & 0xF0) >>> 4;
            int second = b & 0x0F;

            ret.append(processToChar(first)).append(processToChar(second));
        }

        return ret.toString();
    }

    /**
     * Used internally to convert a hexadecimal character
     * to an {@code int}.
     *
     * @param c character to be converted.
     *
     * @return an {@code int} representation of the given {@code char}.
     *
     * @throws IllegalArgumentException if the input is not a hexadecimal
     * character.
     */
    private static int processToNumeric(char c) {
        if (Character.toString(c).matches("[0-9]")) {
            return Integer.parseInt(Character.toString(c));
        } else if (Character.toString(c).matches("[a-f]")) {
            return (c - 87);
        } else if (Character.toString(c).matches("[A-F]")) {
            return (c - 55);
        } else {
            throw new IllegalArgumentException("Invalid character found.");
        }
    }

    /**
     * Used internally to convert an {@code int} into a
     * hexadecimal character.
     *
     * @param b int to be converted.
     *
     * @return a {@code char} representation of the given
     * {@code int}.
     *
     * @throws IllegalArgumentException if the given {@code int}
     * is not a single hexadecimal digit.
     */
    private static char processToChar(int b) {
        if (b >= 0 && b <= 9) {
            return (char) (b + 48);
        } else {
            return (char) (b + 87);
        }
    }
}
