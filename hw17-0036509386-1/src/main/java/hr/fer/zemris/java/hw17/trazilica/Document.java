package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 * A class that keeps info about scanned documents
 * or queries used to find a document.
 *
 * @author Mateo Imbrišak
 */

public class Document implements Comparable<Document> {

    /**
     * Keeps the path to this document, or to the current
     * directory if a query is being represented.
     */
    private final Path path;

    /**
     * Keeps the similarity coefficient.
     */
    private double sim = 0;

    /**
     * Keeps the TF-IDF vector.
     */
    private WordVector vector;

    /**
     * Keeps the {@link Pattern} used to split text from the document file.
     */
    private static final Pattern SPLIT_REGEX = Pattern.compile("[^\\wćčžšđČĆŽŠĐ]|\\d+");

    /**
     * Default constructor used when creating a real document.
     *
     * @param path to the document.
     */
    public Document(Path path) {
        this.path = path;

        try {
            initWords(Files.readAllLines(path), true, Konzola.getVocabulary());
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * A constructor used when the document
     * is a query.
     *
     * @param line given as the query.
     */
    public Document(String line) {
        this.path = Paths.get(".");
        Map<String, Double> wordOccurrences = new HashMap<>();
        processLine(line, false, wordOccurrences);
        initVector(wordOccurrences);
    }

    /**
     * Provides the {@link #path} to the document.
     *
     * @return {@link #path}.
     */
    public Path getPath() {
        return path;
    }

    /**
     * Provides the last calculated {@link #sim}.
     *
     * @return {@link #sim}.
     */
    public double getSim() {
        return sim;
    }

    /**
     * Fills the given {@link Map} with number of
     * occurrences of found words in the given {@code lines}.
     *
     * @param lines used to extract words.
     * @param isVocabulary whether the vocabulary is being initialized.
     * @param map to be filled.
     */
    private void initWords(List<String> lines, boolean isVocabulary, Map<String, Double> map) {
        lines.forEach((line) -> processLine(line, isVocabulary, map));
    }

    /**
     * Processes a single line passed form {@link #initWords(List, boolean, Map)}.
     *
     * @param line used to extract words.
     * @param isVocabulary whether the vocabulary is being initialized.
     * @param map to be filled.
     */
    private void processLine(String line, boolean isVocabulary, Map<String, Double> map) {
        String[] parts = SPLIT_REGEX.split(line);
        HashSet<String> vocabularyWords = new HashSet<>();

        for (String part : parts) {
            if (part.equals("")) continue;
            part = part.toLowerCase();

            if (!Konzola.getStopWords().contains(part)) {
                if (!isVocabulary) {
                    map.merge(part, 1d, (o, n) -> ++o);
                } else if (!vocabularyWords.contains(part)) {
                    vocabularyWords.add(part);

                    map.merge(part, 1d, (o, n) -> ++o);
                }
            }
        }
    }

    /**
     * Used to match the given query and assign {@link #sim}.
     *
     * @param query to be matched.
     */
    public void matchQuery(Document query) {
        sim = vector.cosine(query.vector);
    }

    /**
     * Creates a {@link Map} with pairs of words found in the document
     * and number of times the occurred.
     *
     * @return a {@link Map} with word occurrences.
     *
     * @throws RuntimeException if {@link #path} is invalid.
     */
    public Map<String, Double> getWordOccurrences() {
        try {
            Map<String, Double> wordOccurrences = new HashMap<>();
            initWords(Files.readAllLines(path), false, wordOccurrences);

            return wordOccurrences;
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    /**
     * Used by {@link Konzola} to initialize the {@link #vector}.
     */
    public void initVector(Map<String, Double> wordOccurrences) {
        this.vector = new WordVector();

        for (String word : Konzola.getVocabulary().keySet()) {
            double tf = wordOccurrences.get(word) == null ? 0 : wordOccurrences.get(word);
            double idf = Konzola.getVocabulary().get(word);

            vector.put(tf * idf);
        }
    }

    @Override
    public int compareTo(Document o) {
        return Double.compare(o.sim, sim);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return path.equals(document.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
