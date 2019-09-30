package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A class that checks various properties of
 * students in the database.
 *
 * @author Mateo Imbrišak
 */

public class StudentDemo {

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        List<StudentRecord> records = null;

        try {
            List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"));
            records = convert(lines);
        } catch (IOException exc) {
            System.out.println("Error while reading the file. Terminating...");
            System.exit(-1);
        }

        if (records != null) {
            int taskCounter = 1;

            printName(taskCounter++);
            System.out.println(vratiBodovaViseOd25(records));

            printName(taskCounter++);
            System.out.println(vratiBrojOdlikasa(records));

            printName(taskCounter++);
            vratiListuOdlikasa(records).forEach(System.out::println);

            printName(taskCounter++);
            vratiSortiranuListuOdlikasa(records).forEach(System.out::println);

            printName(taskCounter++);
            vratiPopisNepolozenih(records).forEach(System.out::println);

            printName(taskCounter++);
            razvrstajStudentePoOcjenama(records).entrySet().forEach(System.out::println);

            printName(taskCounter++);
            vratiBrojStudenataPoOcjenama(records).entrySet().forEach(System.out::println);

            printName(taskCounter);
            razvrstajProlazPad(records).entrySet().forEach(System.out::println);
        }
    }

    /**
     * Counts the number of students that have more than 25 points.
     *
     * @param records of students.
     *
     * @return number of students that have more than 25 points.
     */
    private static long vratiBodovaViseOd25(List<StudentRecord> records) {
        return records.stream().filter((r) -> (r.getPointsLab() + r.getPointsMI() + r.getPointsZI()) > 25)
                .count();
    }

    /**
     * Counts the number of students with mark 5.
     *
     * @param records of students.
     *
     * @return number of students with mark 5.
     */
    private static long vratiBrojOdlikasa(List<StudentRecord> records) {
        return records.stream().filter((r) -> r.getMark() == 5)
                .count();
    }

    /**
     * Provide a {@code List} of students with mark 5.
     *
     * @param records of students.
     *
     * @return a {@code List} of students with mark 5.
     */
    private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
        return records.stream().filter((r) -> r.getMark() == 5)
                .collect(Collectors.toList());
    }

    /**
     * Provides a sorted {@code List} of students with mark 5.
     *
     * @param records of students.
     *
     * @return a sorted {@code List} of students with mark 5.
     */
    private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
        return records.stream().filter((r) -> r.getMark() == 5)
                .sorted((s1, s2) -> Double.compare((s2.getPointsMI() + s2.getPointsZI() + s2.getPointsLab()),
                        (s1.getPointsMI() + s1.getPointsZI() + s1.getPointsLab())))
                .collect(Collectors.toList());
    }

    /**
     * Provides a sorted {@code List} of student's identification numbers
     * for students that failed the class.
     *
     * @param records of students.
     * @return a sorted {@code List} of student's identification numbers
     * for students with mark 1.
     */
    private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
        return records.stream().filter((r) -> r.getMark() == 1)
                .map(StudentRecord::getJmbag)
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }

    /**
     * Provides a {@code Map} whose key is student's mark
     * and value isa {@code List} of student record with that mark.
     *
     * @param records of students.
     *
     * @return a {@code Map} with mark for key and a {@code List}
     * of records as value.
     */
    private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
        return records.stream().collect(Collectors.groupingBy(StudentRecord::getMark));
    }

    /**
     * Provides a {@code Map} with mark as key and number of
     * students with that mark as value.
     *
     * @param records of students.
     *
     * @return a {@code Map} with mark as key and number of
     * students with that mark as value.
     */
    private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
        return records.stream().collect(Collectors.toMap(StudentRecord::getMark, (v) -> 1, (v1, v2) -> ++v1));
    }

    /**
     * Provides a {@code Map} with {@code true} as key if the student passed
     * the class and {@code false} if they didn't. Value is a {@code List} of
     * records.
     *
     * @param records of students.
     *
     * @return a {@code Map} of students grouped by whether they passed or failed.
     */
    private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
        return records.stream().collect(Collectors.partitioningBy((r) -> r.getMark() != 1));
    }

    /**
     * Used internally to print the task separator.
     *
     * @param number of the next task.
     */
    private static void printName(int number) {
        System.out.println("Zadatak " + number);
        System.out.println("=========");
    }

    /**
     * Used internally to convert read lines into
     * usable {@code StudentRecord}s.
     *
     * @param lines read from a file.
     *
     * @return a {@code List} of created {@code StudentRecord}s.
     */
    private static List<StudentRecord> convert(List<String> lines) {
        List<StudentRecord> ret = new ArrayList<>();

        for (String record : lines) {
            String[] parts = record.split("\t");
            if (record.isBlank()) {
                continue;
            }

            ret.add(new StudentRecord(parts[0], parts[1], parts[2],
                    Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5]),
                    Integer.parseInt(parts[6])));
        }

        return ret;
    }
}
