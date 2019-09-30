package hr.fer.zemris.java.hw07.demo2;

/**
 * A class used to demonstrate {@code PrimesCollection}
 * with two {@code Iterator}s.
 *
 * @author marcupic
 */

public class PrimesDemo2 {

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(2);
        for(Integer prime : primesCollection) {
            for(Integer prime2 : primesCollection) {
                System.out.println("Got prime pair: "+prime+", "+prime2);
            }
        }
    }
}
