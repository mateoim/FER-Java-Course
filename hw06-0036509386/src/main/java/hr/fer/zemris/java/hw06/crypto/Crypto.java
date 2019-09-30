package hr.fer.zemris.java.hw06.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

/**
 * A class used to check digest of a file or
 * encrypt/decrypt a file.
 *
 * @author Mateo Imbrišak
 */

public class Crypto {

    /**
     * Used to start the program.
     *
     * @param args 2 or 3 elements:
     *             operation being performed
     *             input file
     *             output file if encrypting/decrypting.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Invalid number of arguments. Terminating...");
            System.exit(-1);
        }

        switch (args[0]) {
            case "checksha":
                checkValidity(args, 2, "checksha");

                try (Scanner sc = new Scanner(System.in)) {
                    Path path = Paths.get(args[1]);

                    byte[] result = checksha(path);

                    if (result != null) {
                        System.out.println("Please provide expected sha-256 digest for " + path.getFileName() + ":");
                        System.out.print("> ");
                        String expected = sc.next().trim();

                        if (expected.equals(Util.bytetohex(result))) {
                            System.out.println("Digesting completed. Digest of " + path.getFileName()
                                    + " matches expected digest.");
                        } else {
                            System.out.println("Digesting completed. Digest of " + path.getFileName()
                                    + " does not match the expected digest. Digest was: " + Util.bytetohex(result));
                        }
                    }
                }

                break;
            case "encrypt":
            case "decrypt":
                checkValidity(args, 3, "encrypt or decrypt");

                try (Scanner sc = new Scanner(System.in)) {
                    Path input = Paths.get(args[1]);
                    Path output = Paths.get(args[2]);

                    System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
                    System.out.print("> ");
                    String keyText = sc.nextLine().trim();
                    System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
                    System.out.print("> ");
                    String ivText = sc.nextLine().trim();

                    if (encrypt(keyText, ivText, input, output, args[0])) {

                        System.out.println(args[0].substring(0, 1).toUpperCase() + args[0].substring(1)
                                + "ion completed. Generated file " + output.getFileName()
                                + " based on file " + input.getFileName() + ".");
                    }

                    break;
                }
            default:
                System.out.println("Invalid operation found.");
        }

    }

    /**
     * Used internally to calculate the digest
     * of a given file.
     *
     * @param path to the file.
     *
     * @return an array of {@code byte}s that when converted to
     * hexadecimal represents the calculated digest.
     */
    private static byte[] checksha(Path path) {
        try (InputStream is = Files.newInputStream(path)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[4096]; // 4kb

            while (true) {
                int r = is.read(buffer);
                if (r < 1) {
                    break;
                }

                digest.update(buffer, 0, r);
            }
            return digest.digest();
        } catch (IOException exc) {
            System.out.println("Error while opening the file.");
        } catch (NoSuchAlgorithmException ignored) {}

        return null;
    }

    /**
     * Used to encrypt or decrypt a given file
     * based on the {@code encrypt} parameter.
     *
     * @param keyText password provided by the user.
     * @param ivText initialization vector provided by the user.
     * @param input file being encrypted/decrypted.
     * @param output encrypted/decrypted file.
     * @param encrypt "encrypt" if operation is encryption,
     *                anything else for decryption.
     *
     * @return {@code true} if the operation was successful,
     * otherwise {@code false}.
     */
    private static boolean encrypt(String keyText, String ivText, Path input, Path output, String encrypt) {
        try (OutputStream os = Files.newOutputStream(output);
             InputStream is = Files.newInputStream(input)) {
            SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(encrypt.equalsIgnoreCase("encrypt") ?
                    Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

            byte[] buffer = new byte[4096]; // 4kb
            while (true) {
                int r = is.read(buffer);
                if (r < 1) {
                    break;
                }

                os.write(cipher.update(buffer, 0, r));
            }

            try {
                os.write(cipher.doFinal());
            } catch (IllegalBlockSizeException | BadPaddingException exc) {
                System.out.println("Error occurred while finalizing encryption.");
                return false;
            }

        } catch (IOException exc) {
            System.out.println("Error while opening the file");
            return false;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException
                | NoSuchAlgorithmException | NoSuchPaddingException ignored) {}
        return true;
    }

    /**
     * Checks if the number of given arguments is equal to the
     * expected number for the given operation.
     *
     * @param args given to the program.
     * @param size required for the operation.
     * @param operation being performed.
     */
    private static void checkValidity(String[] args, int size, String operation) {
        if (args.length != size) {
            System.out.println("Invalid number of arguments for operation " + operation + ". Terminating...");
            System.exit(-1);
        }
    }
}
