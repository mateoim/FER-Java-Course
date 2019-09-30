package hr.fer.zemris.java.custom.scripting.exec;

/**
 * A class that stores an {@code Object} and
 * can perform various arithmetic operations or
 * compare two {@code Object}s.
 *
 * @author Mateo Imbrišak
 */

public class ValueWrapper {

    /**
     * Keeps the value of this node.
     */
    private Object value;

    /**
     * Default constructor that assigns a value.
     *
     * @param value to be assigned.
     */
    public ValueWrapper(Object value) {
        this.value = value;
    }

    /**
     * Adds the given value to the current value
     * if both are of valid class.
     *
     * @param incValue value to be added.
     *
     * @throws RuntimeException if any value is not
     * an {@code Integer}, a {@code Double}, a {@code String}
     * or {@code null}.
     */
    public void add(Object incValue) {
        performAction(convertToNumeric(value), convertToNumeric(incValue), '+');
    }

    /**
     * Subtracts the given value from the current value
     * if both are of valid class.
     *
     * @param decValue value to be subtracted.
     *
     * @throws RuntimeException if any value is not
     * an {@code Integer}, a {@code Double}, a {@code String}
     * or {@code null}.
     */
    public void subtract(Object decValue) {
        performAction(convertToNumeric(value), convertToNumeric(decValue), '-');
    }

    /**
     * Multiplies the current value with the
     * given value if both are of valid class.
     *
     * @param mulValue value to be multiplied with.
     *
     * @throws RuntimeException if any value is not
     * an {@code Integer}, a {@code Double}, a {@code String}
     * or {@code null}.
     */
    public void multiply(Object mulValue) {
        performAction(convertToNumeric(value), convertToNumeric(mulValue), '*');
    }

    /**
     * Divides the current value with the
     * given value if both are of valid class.
     *
     * @param divValue value to be divided with.
     *
     * @throws RuntimeException if any value is not
     * an {@code Integer}, a {@code Double}, a {@code String}
     * or {@code null}.
     */
    public void divide(Object divValue) {
        performAction(convertToNumeric(value), convertToNumeric(divValue), '/');
    }

    /**
     * Compares the current value with the given one.
     *
     * @param withValue value to compare with.
     *
     * @return {@code 0} if {@code first == second}; a value less
     * than {@code 0} if {@code first < second} as unsigned values; and
     * a value greater than {@code 0} if {@code first > second} as
     * unsigned values.
     *
     * @throws RuntimeException if any value is not
     * an {@code Integer}, a {@code Double}, a {@code String}
     * or {@code null}.
     */
    public int numCompare(Object withValue) {
        return performAction(convertToNumeric(value), convertToNumeric(withValue), 'c');
    }

    /**
     * Provides the current value.
     *
     * @return current value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Assigns the given value.
     *
     * @param value to be assigned.
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Used internally to check if the argument is of valid class.
     *
     * @param arg to be checked.
     *
     * @throws RuntimeException if the argument is of invalid class.
     */
    private void checkArgument(Object arg) {
        if (arg != null && !(arg instanceof Integer) && !(arg instanceof Double) && !(arg instanceof String)) {
            throw new RuntimeException("Provided argument is of invalid class.");
        }
    }

    /**
     * Used internally to try to parse the {@code String}
     * into a number.
     *
     * @param arg {@code String} to be parsed.
     *
     * @return a {@code Number} based on the {@code String}.
     *
     * @throws RuntimeException if an error occurred while parsing
     * the {@code String}.
     */
    private Number parseString(String arg) {
        try {
            if (arg.contains(".") || arg.contains("E") || arg.contains("e")) {
                return Double.parseDouble(arg);
            } else {
                return Integer.parseInt(arg);
            }
        } catch (NumberFormatException exc) {
            throw new RuntimeException("String contains illegal characters.");
        }
    }

    /**
     * Used internally to perform the calculation or comparison.
     *
     * @param first number being used.
     * @param second number being used.
     * @param operation being performed.
     *
     * @return Value {@code 0} if the operation is not a comparison,
     * otherwise value {@code 0} if {@code first == second}; a value less
     * than {@code 0} if {@code first < second} as unsigned values; and
     * a value greater than {@code 0} if {@code first > second} as
     * unsigned values.
     */
    private int performAction(Number first, Number second, char operation) {
        Number result;
        boolean isDouble = false;

        if (first instanceof Double || second instanceof Double) {
            isDouble = true;
        }

        switch (operation) {
            case '+':
                result = isDouble ? (first.doubleValue() + second.doubleValue()) :
                        (first.intValue() + second.intValue());
                break;
            case '-':
                result = isDouble ? (first.doubleValue() - second.doubleValue()) :
                        (first.intValue() - second.intValue());
                break;
            case '*':
                result = isDouble ? (first.doubleValue() * second.doubleValue()) :
                        (first.intValue() * second.intValue());
                break;
            case '/':
                result = isDouble ? (first.doubleValue() / second.doubleValue()) :
                        (first.intValue() / second.intValue());
                break;
            case 'c':
                return Integer.compare(first.intValue(), second.intValue());
            default:
                throw new RuntimeException("Unsupported operator.");
        }

        if (!isDouble) {
            value = result.intValue();
        } else {
            value = result.doubleValue();
        }

        return 0;
    }

    /**
     * Converts the given {@code Object} to a {@code Number}.
     *
     * @param arg {@code Object} to be converted.
     *
     * @return a {@code Number} based on the given argument.
     */
    private Number convertToNumeric(Object arg) {
        checkArgument(arg);

        if (arg == null) {
            return 0;
        } else if (arg instanceof String) {
            return parseString((String) arg);
        }

        return (Number) arg;
    }
}
