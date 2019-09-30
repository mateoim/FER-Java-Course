package hr.fer.zemris.java.hw15.encryption;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * An auxiliary class that contains
 * methods used to handle user passwords.
 *
 * @author Mateo Imbrišak
 */

public class CryptUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private CryptUtil() {}

    /**
     * Used to encrypt the given {@code password}
     * using SHA-1 algorithm.
     *
     * @param password to be encrypted.
     *
     * @return hex encoded string representing
     * the given password.
     *
     * @throws RuntimeException if SHA-1 algorithm
     * doesn't exist. Should never happen.
     */
    public static String encrypt(String password) {
        try {
            password = password.trim();
            MessageDigest algorithm = MessageDigest.getInstance("SHA-1");

            algorithm.update(password.getBytes(StandardCharsets.UTF_8), 0, password.length());

            return byteToHex(algorithm.digest());
        } catch (NoSuchAlgorithmException exc) {
            throw new RuntimeException(exc);
        }
    }

    /**
     * Checks if the given {@code password} matches
     * the expected encrypted hash.
     *
     * @param password to be checked.
     * @param expected encrypted password hash.
     *
     * @return {@code true} if the password matches,
     * otherwise {@code false}.
     */
    public static boolean checkPassword(String password, String expected) {
        if (password == null) {
            return false;
        }

        return (expected.equals(encrypt(password)));
    }

    /**
     * Used internally to convert a {@code byte} array
     * to a hex encoded string.
     *
     * @param input {@code byte} array to be converted.
     *
     * @return a {@code String} representation of the
     * given {@code byte} array.
     */
    private static String byteToHex(byte[] input) {
        StringBuilder ret = new StringBuilder();

        for (byte b : input) {
            int first = (b & 0xF0) >>> 4;
            int second = b & 0x0F;

            ret.append(process(first)).append(process(second));
        }

        return ret.toString();
    }

    /**
     * Used internally to convert a given number
     * to it's {@code char} representation in hex.
     *
     * @param n number to be converted.
     *
     * @return hex {@code char} representation of the
     * given number.
     */
    private static char process(int n) {
        return (char) ((n >= 0 && n <= 9) ? (n + 48) : (n + 87));
    }
}
