package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This class calculates factorials of numbers between 3 and 20 entered using standard input.
 * Input "kraj" exits the program.
 */

public class Factorial {

    /**
     * Method used to run the program
     *
     * @param args command line arguments - ignored in this case,
     *            numbers are entered using standard input
     */

    public static void main(String[] args) {
        System.out.print("Unesite broj > ");

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {
                String line = sc.next();

                if (line.equals("kraj")) {
                    break;
                }

                try {
                    int number = Integer.parseInt(line);
                    System.out.println(number + "! = " +  calculateFactorial(number));
                } catch (NumberFormatException exc) {
                    System.out.println("'" + line + "' nije cijeli broj.");
                } catch (IllegalArgumentException exc) {
                    System.out.println(exc.getMessage());
                }

                System.out.print("Unesite broj > ");
            }
        }

        System.out.println("Doviđenja.");
    }

    /**
     * Method used to calculate the factorial of a given number if it's between 3 and 20.
     *
     * @param number calculated factorial
     *
     * @return factorial of the given number.
     */

    public static long calculateFactorial(int number) {
        if (number < 3 || number > 20) {
            throw new IllegalArgumentException("'" + number + "' nije broj u dozvoljenom rasponu.");
        }

        long result = 1;

        for (int i = 1; i <= number; i++) {
            result *= i;
        }

        return result;
    }
}
