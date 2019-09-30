package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class that generates a given number of
 * prime numbers.
 *
 * @author Mateo Imbrišak
 */

public class PrimesCollection implements Iterable<Integer> {

    /**
     * Keeps the maximum number of primes generated
     * by this collection.
     */
    private final int maxPrimes;

    /**
     * Default constructor that assigns the maximum
     * number of primes.
     *
     * @param maxPrimes maximum number of primes.
     */
    public PrimesCollection(int maxPrimes) {
        this.maxPrimes = maxPrimes;
    }

    /**
     * Used internally to generate the next number.
     *
     * @param prime number used as a starting point.
     *
     * @return next prime number.
     */
    private int findNextPrime(int prime) {
        if (prime == 2) {
            return prime;
        }

        while (true) {
            int root = (int) Math.floor(Math.sqrt(prime));
            boolean isPrime = true;

            for (int i = 2; i <= root; i++) {
                if (prime % i == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                return prime;
            } else {
                prime++;
            }
        }
    }

    /**
     * Returns an iterator over elements.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        return new PrimeIterator();
    }

    /**
     * A class that represents an {@code Iterator} for {@code PrimesCollection}.
     */
    private class PrimeIterator implements Iterator<Integer> {

        /**
         * Keeps the current prime number.
         */
        private int currentPrime = 2;

        /**
         * Keeps track of primes generated so far.
         */
        private int primesGenerated;

        @Override
        public boolean hasNext() {
            return primesGenerated < maxPrimes;
        }

        @Override
        public Integer next() {
            if (hasNext()) {
                primesGenerated++;
                int ret = findNextPrime(currentPrime);
                currentPrime = ret + 1;
                return ret;
            } else {
                throw new NoSuchElementException("Last element has been reached.");
            }
        }
    }

}
