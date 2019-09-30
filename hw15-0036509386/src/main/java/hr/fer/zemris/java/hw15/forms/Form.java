package hr.fer.zemris.java.hw15.forms;

import java.util.HashMap;
import java.util.Map;

/**
 * An interface used to model other forms.
 *
 * @author Mateo Imbrišak
 */

public interface Form {

    /**
     * Keeps track of errors that occurred.
     */
    Map<String, String> errors = new HashMap<>();

    /**
     * Checks if any errors occurred.
     *
     * @return {@code true} if any errors
     * occurred, otherwise {@code false}.
     */
    default boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Checks if a particular error
     * occurred.
     *
     * @param error to be checked.
     *
     * @return {@code true} if the error
     * occurred, otherwise {@code false}.
     */
    default boolean hasError(String error) {
        return errors.get(error) != null;
    }

    /**
     * Provides the particular error message.
     *
     * @param error to be found.
     *
     * @return error message if it exists,
     * otherwise {@code null}.
     */
    default String getError(String error) {
        return errors.get(error);
    }

    /**
     * Checks for errors in the form.
     */
    void validate();

    /**
     * Used to ensure that a {@code null} value
     * is never passed and removes extra whitespaces.
     *
     * @param input to be checked.
     *
     * @return trimmed input or an empty string
     * if input was {@code null}.
     */
     static String prepare(String input) {
        if (input == null) {
            return "";
        }

        return input.trim();
    }

    /**
     * Performs a basic check of length and whether
     * the given attribute is empty.
     *
     * @param attribute to be checked.
     * @param attributeName to be used in the message.
     * @param attributeKey to be used in {@link #errors}.
     * @param maxLength of the attribute.
     */
    static void checkAttribute(String attribute, String attributeName, String attributeKey, int maxLength) {
        if (attribute.isEmpty()) {
            errors.put(attributeKey, attributeName + " cannot be empty.");
        } else if (attribute.length() > maxLength) {
            errors.put(attributeKey, attributeName + " cannot be longer than " + maxLength + " characters.");
        }
    }
}
