package hr.fer.zemris.java.hw05.db;

/**
 * A class that contains field value
 * getters for values of {@code StudentRecord}.
 *
 * @author Mateo Imbrišak
 */

public class FieldValueGetters {

    /**
     * Used to get the student's first name.
     */
    public static final IFieldValueGetter FIRST_NAME = (StudentRecord::getFirstName);

    /**
     * Used to get the student's last name.
     */
    public static final IFieldValueGetter LAST_NAME = (StudentRecord::getLastName);

    /**
     * Used to get the student's identification number
     */
    public static final IFieldValueGetter JMBAG = (StudentRecord::getJmbag);
}
