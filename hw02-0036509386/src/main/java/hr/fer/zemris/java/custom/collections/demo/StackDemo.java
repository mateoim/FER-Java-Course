package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * A command-line program used to solve simple
 * mathematical problems using a {@code ObjectStack}.
 *
 * @author Mateo Imbrišak
 */

public class StackDemo {

    /**
     * A method that starts the program.
     *
     * @param args a single line of text containing
     *             numbers and operators in postfix representation.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Incorrect number of arguments.");
            return;
        }

        String[] parts = args[0].split("\\s+");

        ObjectStack stack = new ObjectStack();

        for (String element: parts) {
            if (element.equals("")) { // skip empty elements
                continue;
            }

            try { // if it is not a number proceed to calculation
                stack.push(Integer.parseInt(element));
            } catch (NumberFormatException exc) {
                try { // fail if not enough numbers in the stack
                    int first = (Integer) stack.pop();
                    int second = (Integer) stack.pop();

                    switch (element) {
                        case "+":
                            stack.push(second + first);
                            break;
                        case "-":
                            stack.push(second - first);
                            break;
                        case "/":
                            try { // if dividing by zero inform user
                                stack.push(second / first);
                                break;
                            } catch (ArithmeticException zero) {
                                System.out.println("Cannot divide by zero. Terminating...");
                                return;
                            }
                        case "*":
                            stack.push(second * first);
                            break;
                        case "%":
                            try { // if dividing by zero inform user
                                stack.push(second % first);
                                break;
                            } catch (ArithmeticException zero) {
                                System.out.println("Cannot get remainder of" +
                                        "division by zero. Terminating...");
                                return;
                            }
                        default:
                            System.out.println("'" + element + "' is not an operator or a number. Terminating...");
                            return;

                    }
                } catch (EmptyStackException empty) {
                    System.out.println("Not enough elements on the stack. Terminating...");
                    return;
                }
            }
        }

        if (stack.size() == 1) {
            System.out.println("Expression evaluates to " + stack.pop());
        }
    }
}
