package hr.fer.zemris.java.hw07.demo4;

/**
 * A class that represents a single student's record.
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
    private String firstName;

    /**
     * Keeps the student's last name.
     */
    private String lastName;

    /**
     * Keeps the student's midterm points.
     */
    private double pointsMI;

    /**
     * Keeps the student's final points.
     */
    private double pointsZI;

    /**
     * Keeps the student's lab points.
     */
    private double pointsLab;

    /**
     * Keeps the student's final mark.
     */
    private int mark;

    /**
     * Default constructor that assigns all values.
     *
     * @param jmbag student's identification number.
     * @param lastName student's last name.
     * @param firstName student's first name.
     * @param pointsMI student's midterm points.
     * @param pointsZI student's final points.
     * @param pointsLab student's lab points.
     * @param mark student's final mark.
     */
    public StudentRecord(String jmbag, String lastName, String firstName,
                         double pointsMI, double pointsZI, double pointsLab, int mark) {
        this.jmbag = jmbag;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pointsMI = pointsMI;
        this.pointsZI = pointsZI;
        this.pointsLab = pointsLab;
        this.mark = mark;
    }

    /**
     * Provides the student's identification number.
     *
     * @return student's identification number.
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Provides the student's first name.
     *
     * @return student's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Provides the student's last name.
     *
     * @return student's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Provides the student's midterm points.
     *
     * @return student's midterm points.
     */
    public double getPointsMI() {
        return pointsMI;
    }

    /**
     * Provides the student's final points.
     *
     * @return student's final points.
     */
    public double getPointsZI() {
        return pointsZI;
    }

    /**
     * Provides the student's lab points.
     *
     * @return student's lab points.
     */
    public double getPointsLab() {
        return pointsLab;
    }

    /**
     * Provides the student's final mark.
     *
     * @return student's final mark.
     */
    public int getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return jmbag + "\t" +
                lastName + "\t" +
                firstName + "\t" +
                pointsMI + "\t" +
                pointsZI + "\t" +
                pointsLab + "\t" +
                mark;
    }
}
