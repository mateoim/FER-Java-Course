package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * A class used to keep data from a single
 * student's record.
 *
 * @author Mateo Imbrišak
 */

public class StudentRecord {

    /**
     * Keeps the student's identification number.
     */
    private String jmbag;

    /**
     * Keeps the student's first name.
     */
    private String lastName;

    /**
     * Keeps the student's last name.
     */
    private String firstName;

    /**
     * Keeps the student's final grade.
     */
    private int finalGrade;

    /**
     * Default constructor that initializes all values.
     *
     * @param jmbag identification number.
     * @param lastName student's name.
     * @param firstName student's last name.
     * @param finalGrade student's final grade.
     */
    public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    /**
     * Returns the student's jmbag.
     *
     * @return student's jmbag.
     */
    String getJmbag() {
        return jmbag;
    }

    /**
     * Returns the student's last name.
     *
     * @return student's last name.
     */
    String getLastName() {
        return lastName;
    }

    /**
     * Returns the student's first name.
     *
     * @return student's first name.
     */
    String getFirstName() {
        return firstName;
    }

    /**
     * Returns the student's final grade.
     *
     * @return student's final grade.
     */
    int getFinalGrade() {
        return finalGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return jmbag.equals(that.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }
}
