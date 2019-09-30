package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to format the filtered {@code StudentRecord}s
 * and prepare them for output.
 *
 * @author Mateo Imbrišak
 */

public class RecordFormatter {

    /**
     * Creates a {@code List} of {@code String}s representing
     * each line of the filtered output.
     *
     * @param records to be printed.
     *
     * @return a {@code List} that can be printed to {@code System.out}
     * using method {@link java.io.PrintStream#println()}
     */
    public static List<String> format(List<StudentRecord> records) {
        List<String> ret = new ArrayList<>();

        if (records.size() == 0) {
            ret.add("Records selected: 0");
            return ret;
        }

        int[] lengths = findLongest(records);

        int jmbagLength = lengths[0];
        int firstNameLength = lengths[2];
        int lastNameLength = lengths[1];

        ret.add(border(firstNameLength, lastNameLength, jmbagLength));

        for (StudentRecord record : records) {

            String element = "| " + record.getJmbag() +
                    " ".repeat(Math.max(0, jmbagLength - record.getJmbag().length())) +
                    " | " + record.getLastName() +
                    " ".repeat(Math.max(0, lastNameLength - record.getLastName().length())) +
                    " | " + record.getFirstName() +
                    " ".repeat(Math.max(0, firstNameLength - record.getFirstName().length())) +
                    " | " + record.getFinalGrade() + " |";

            ret.add(element);
        }

        ret.add(border(firstNameLength, lastNameLength, jmbagLength));
        ret.add("Records selected: " + records.size());

        return ret;
    }

    /**
     * Used internally to calculate the longest element for
     * all properties.
     *
     * @param records whose longest element you want.
     *
     * @return length of the longest elements in each field.
     */
    private static int[] findLongest(List<StudentRecord> records) {
        int[] longest = new int[3];

        for (StudentRecord record : records) {
            if (FieldValueGetters.JMBAG.get(record).length() > longest[0]) {
                longest[0] = FieldValueGetters.JMBAG.get(record).length();
            }

            if (FieldValueGetters.LAST_NAME.get(record).length() > longest[1]) {
                longest[1] = FieldValueGetters.LAST_NAME.get(record).length();
            }

            if (FieldValueGetters.FIRST_NAME.get(record).length() > longest[2]) {
                longest[2] = FieldValueGetters.FIRST_NAME.get(record).length();
            }
        }

        return longest;
    }

    /**
     * Used internally to generate the border of the output table.
     *
     * @param firstNameLength longest first name.
     * @param lastNameLength longest last name.
     * @param jmbagLength longest jmbag.
     *
     * @return a {@code String} representing top or bottom
     * border of the table.
     */
    private static String border(int firstNameLength, int lastNameLength, int jmbagLength) {
        return "+" + "=".repeat(jmbagLength + 2) +
                "+" + "=".repeat(lastNameLength + 2) +
                "+" + "=".repeat(firstNameLength + 2) +
                "+" + "=".repeat(3) + "+";
    }
}
