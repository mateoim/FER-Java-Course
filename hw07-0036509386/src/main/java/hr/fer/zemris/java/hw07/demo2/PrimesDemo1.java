package hr.fer.zemris.java.hw07.demo2;

/**
 * A class used to demonstrate {@code PrimesCollection}
 * with a single {@code Iterator}.
 *
 * @author marcupic
 */

public class PrimesDemo1 {

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
        for(Integer prime : primesCollection) {
            System.out.println("Got prime: " + prime);
        }
    }
}
