package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * A command-line program that searches
 * for words in the given directory and
 * it's subdirectories.
 *
 * @author Mateo Imbrišak
 */

public class Konzola {

    /**
     * Keeps the list of found stopwords.
     */
    private static List<String> stopWords;

    /**
     * Keeps a {@link List} of all read {@link Document}s.
     */
    private static List<Document> documents = new ArrayList<>();

    /**
     * Keeps the vocabulary that also holds the idf vector values.
     */
    private static Map<String, Double> vocabulary = new HashMap<>();

    /**
     * Keeps the results from last query.
     */
    private static TreeSet<Document> lastResults = null;

    /**
     * Keeps the relative location of the file
     * with stopwords.
     */
    private static final String STOPWORDS_LOCATION = "hrvatski_stoprijeci.txt";

    /**
     * Don't let anyone instantiate this class.
     */
    private Konzola() {}

    /**
     * Used to start the program.
     *
     * @param args one element - path to the
     *             folder with files to be searched.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments. Terminating...");
            return;
        }

        Path dir = Paths.get(args[0]);

        initStopWords();
        initDocuments(dir);

        System.out.println("Veličina riječnika je " + vocabulary.size() + " riječi.\n");
        System.out.print("Enter command > ");

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {

                String line = sc.nextLine();
                String[] parts = line.split("\\s+");

                switch (parts[0]) {
                    case "query":
                        if (!line.contains(" ") || line.length() == line.indexOf(" ") + 1) {
                            System.out.println("Invalid query.\n");
                            break;
                        }

                        String queryLine = line.substring(line.indexOf(" ") + 1);
                        String[] query = queryLine.split("\\s+");
                        System.out.println("Query is: " + Arrays.toString(query) + "\nNajboljih 10 rezultata:");

                        Document queryDoc = new Document(queryLine);

                        processQuery(queryDoc);
                        printResults();
                        break;
                    case "results":
                        if (lastResults == null) {
                            System.out.println("No query has been given.\n");
                            break;
                        }

                        printResults();
                        break;
                    case "type":
                        if (parts.length != 2) {
                            System.out.println("Invalid number of arguments.\n");
                            break;
                        } else if (lastResults == null) {
                            System.out.println("No query has been given.\n");
                            break;
                        }

                        try {
                            int index = Integer.parseInt(parts[1]);
                            int limit = lastResults.size() > 10 ? 10 : lastResults.size() - 1;

                            if (index < 0 || index > limit) {
                                System.out.println("Entry " + index + " does not exist.\n");
                                break;
                            }

                            int counter = 0;

                            for (Document doc : lastResults) {
                                if (counter != index) {
                                    counter++;
                                    continue;
                                }

                                try {
                                    System.out.println("-".repeat(64));
                                    System.out.println("Dokument: " + doc.getPath());
                                    System.out.println("-".repeat(64));

                                    List<String> lines = Files.readAllLines(doc.getPath());
                                    lines.forEach((System.out::println));
                                    break;
                                } catch (IOException exc) {
                                    System.out.println("Error while reading the document.\n");
                                    break;
                                }
                            }

                            break;
                        } catch (NumberFormatException exc) {
                            System.out.println("Second argument must be a number.\n");
                            break;
                        }
                    case "exit":
                        return;
                    default:
                        System.out.println("Nepoznata naredba.\n");
                }

                System.out.print("Enter command > ");
            }
        }
    }

    /**
     * Used internally to read all stopwords from
     * the file defined in {@link #STOPWORDS_LOCATION}.
     */
    private static void initStopWords() {
        try {
            stopWords = Files.readAllLines(Paths.get(STOPWORDS_LOCATION));
        } catch (IOException exc) {
            System.out.println("Error while reading stopwords. Terminating...");
            System.exit(-1);
        }
    }

    /**
     * Initializes {@link #documents}.
     *
     * @param dir path to the directory containing the documents.
     */
    private static void initDocuments(Path dir) {
        try {
            Files.walkFileTree(dir, new DocumentVisitor());

            initIdf();

            documents.forEach((doc) -> doc.initVector(doc.getWordOccurrences()));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Converts {@link #vocabulary}'s number of occurrences
     * to idf values.
     */
    private static void initIdf() {
        for (String word : vocabulary.keySet()) {
            vocabulary.merge(word, 0d, (o, n) -> Math.log((double) getNumberOfDocuments() / o));
        }
    }

    /**
     * Used internally to print the query results.
     */
    private static void printResults() {
        int limit = lastResults.size() > 10 ? 10 : lastResults.size();
        int current = 0;

        for (Document doc : lastResults) {
            if (current >= limit) {
                break;
            }

            System.out.println("[ " + current + "] (" + String.format("%.4f", doc.getSim()) + ") " + doc.getPath());
            current++;
        }

        System.out.println();
    }

    /**
     * Used to match the given query with all
     * available documents.
     *
     * @param query to be matched.
     */
    private static void processQuery(Document query) {
        lastResults = new TreeSet<>();

        for (Document doc : documents) {
            doc.matchQuery(query);

            if (doc.getSim() > 0) {
                lastResults.add(doc);
            }
        }
    }

    /**
     * Provides the list of {@link #stopWords}.
     *
     * @return {@link #stopWords}.
     */
    public static List<String> getStopWords() {
        return stopWords;
    }

    /**
     * Provides the {@link #vocabulary}.
     *
     * @return {@link #vocabulary}.
     */
    public static Map<String, Double> getVocabulary() {
        return vocabulary;
    }

    /**
     * Provides the number of {@link #documents}.
     *
     * @return number of {@link #documents}.
     */
    public static int getNumberOfDocuments() {
        return documents.size();
    }

    /**
     * A {@link FileVisitor} used to read all documents
     * from the path given in {@link #main(String[])}.
     */
    private static class DocumentVisitor implements FileVisitor<Path> {

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            documents.add(new Document(file));
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }
}
