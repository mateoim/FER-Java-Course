package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a database containing
 * {@code StudentRecord}s.
 *
 * @author Mateo Imbrišak
 */

public class StudentDatabase {

    /**
     * Keeps all records in a list for normal access.
     */
    private List<StudentRecord> students;

    /**
     * Keeps pairs of jmbags as keys and
     * {@code StudentRecord}s as values for
     * instant access.
     */
    private Map<String, StudentRecord> jmbag;

    /**
     * Default constructor that takes an array of
     * {@code String}s and converts them into
     * {@code StudentRecord}s if they are in valid
     * format, otherwise terminates the program.
     *
     * @param records an array of {@code String}s to
     *                be converted into {@code StudentRecord}s.
     *                Each line is a {@code StudentRecord}.
     */
    public StudentDatabase(List<String> records) {
        students = new ArrayList<>();
        jmbag = new HashMap<>();

        for (String record : records) {
            String[] parts = record.split("\t");
            int grade = Integer.parseInt(parts[3]);

            if (grade >= 1 && grade <= 5) {
                StudentRecord student = new StudentRecord(parts[0], parts[1], parts[2], grade);

                if (jmbag.put(parts[0], student) == null) {
                    students.add(student);
                } else {
                    System.out.println("Student already exists. Terminating...");
                    System.exit(-1);
                }
            } else {
                System.out.println("Invalid grade. Terminating...");
                System.exit(-1);
            }
        }
    }

    /**
     * Provides the {@code StudentRecord} with the given
     * jmbag, if it doesn't exist return {@code null}.
     *
     * @param jmbag of the student you want.
     *
     * @return {@code StudentRecord} with the given jmbag,
     * if it doesn't exist return {@code null}.
     */
    public StudentRecord forJMBAG(String jmbag) {
        return this.jmbag.get(jmbag);
    }

    /**
     * Filters students in the database using given
     * {@link IFilter#accepts(StudentRecord)} method
     * to test them.
     *
     * @param filter applied to the records.
     *
     * @return a {@code List} containing all records
     * accepted by the filter.
     */
    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> acceptable = new ArrayList<>();

        for (StudentRecord student : students) {
            if (filter.accepts(student)) {
                acceptable.add(student);
            }
        }

        return acceptable;
    }
}
