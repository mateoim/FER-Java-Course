package hr.fer.zemris.java.hw05.db.program;

import hr.fer.zemris.java.hw05.db.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A class used to start the database manager.
 *
 * @author Mateo Imbrišak
 */

public class StudentDB {

    /**
     * Method used to run the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        StudentDatabase database = null;

        try {
            List<String> lines = Files.readAllLines(
                    Paths.get("./database.txt"),
                    StandardCharsets.UTF_8
            );

            database = new StudentDatabase(lines);
        } catch (IOException exc) {
            System.out.println("Error while reading the database. Terminating...");
            System.exit(-1);
        }

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");

                if (sc.hasNext()) {
                    String input = sc.nextLine().trim();

                    if (input.equalsIgnoreCase("exit")) {
                        System.out.println("Goodbye!");
                        break;
                    }

                    if (input.startsWith("query ")) {
                        String query = input.substring(6);

                        try {
                            List<StudentRecord> printList = query(query, database);

                            List<String> output = RecordFormatter.format(printList);
                            output.forEach(System.out::println);
                            System.out.printf("%n");
                        } catch (QueryException exc) {
                            System.out.println("Invalid query.\n");
                        }
                    } else {
                        System.out.println("Invalid command.\n");
                    }
                }
            }
        }
    }

    /**
     * Used internally to filter the records for
     * the query command.
     *
     * @param query input provided after the word "query".
     * @param database being queried.
     *
     * @return a {@code List} of {@code StudentRecord}s from
     * the database.
     *
     * @throws QueryException if the query is in invalid form.
     */
    private static List<StudentRecord> query(String query, StudentDatabase database) {
        QueryParser parser = new QueryParser(query);
        List<StudentRecord> printList = new ArrayList<>();

        if (parser.isDirectQuery()) {
            printList.add(database.forJMBAG(parser.getQueriedJMBAG()));
        } else {
            printList = database.filter(new QueryFilter(parser.getQuery()));
        }

        return printList;
    }
}
