package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This class calculates area and perimeter of a rectangle based on the entered height and width.
 */

public class Rectangle {

    /**
     * Method used to start the program.
     *
     * @param args command line arguments, should be height and width
     */

    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                double width = Double.parseDouble(args[0]);
                double height = Double.parseDouble(args[1]);

                if (width < 0 || height < 0) {
                    System.out.println("Unijeli ste negativnu vrijednost.");
                    return;
                }
                else if (width == 0 || height == 0) {
                    System.out.println("Duljina ne može biti 0.");
                    return;
                }

                printResults(height, width);
                return;
            } catch (NumberFormatException exc) {
                System.out.println("Uneseni argumenti nisu brojevi.");
                return;
            }
        }

        if (args.length != 0) {
            System.out.println("Pogrešan broj argumenata.");
            return;
        }

        try (Scanner sc = new Scanner(System.in)) {
            double width = getInput(sc, "širinu");
            double height = getInput(sc, "visinu");

            printResults(height, width);
        }
    }

    /**
     * A method used to calculate the area of a given rectangle.
     *
     * @param height height of the rectangle
     * @param width width of the rectangle
     *
     * @return area of the rectangle
     */

    public static double calculateArea(double height, double width) {
        return height * width;
    }

    /**
     * A method used to calculate the perimeter of the rectangle.
     *
     * @param height height of the rectangle
     * @param width width of the rectangle
     *
     * @return perimeter of the rectangle
     */

    public static double calculatePerimeter(double height, double width) {
        return height * 2 + width * 2;
    }

    /**
     * Used to display the final results on standard output.
     *
     * @param height height of the rectangle
     * @param width width of the rectangle
     */

    private static void printResults(double height, double width) {
        System.out.println("Pravokutnik širine " + width + " i visine " + height + " ima površinu "
                + calculateArea(height, width) + " te opseg " + calculatePerimeter(height, width) + ".");
    }

    /**
     * Used to get the values of height and width from the user if not entered using command line.
     *
     * @param sc scanner used to get the input from user
     * @param valueType string used to give the user information which value is being entered
     *
     * @return final value entered by the user
     */

    private static double getInput(Scanner sc, String valueType) {
        while (true) {
            System.out.print("Unesite " + valueType + " > ");
            if (sc.hasNext()) {
                String line = sc.nextLine();

                try {
                    double value = Double.parseDouble(line);
                    if (value > 0) {
                        return value;
                    }

                    if (value == 0) {
                        System.out.println("Duljina ne može biti 0.");
                        continue;
                    }

                    System.out.println("Unijeli ste negativnu vrijednost.");
                } catch(IllegalArgumentException exc) {
                    System.out.println("'" + line + "' se ne može protumačiti kao broj.");
                }
            }
        }
    }
}
