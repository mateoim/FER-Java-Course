package hr.fer.zemris.java.servlets.voting.util;

import java.util.*;

/**
 * Keeps utilities for parsing various
 * input types used in voting.
 *
 * @author Mateo Imbrišak
 */

public class ParseUtil {

    /**
     * Cannot be created as an {@code Object}.
     */
    private ParseUtil() {}

    /**
     * Used internally to parse the results file.
     *
     * @param lines from the file.
     *
     * @return a {@link Map} containing bands id as
     * {@code key} and number of votes as {@code value}.
     */
    public static Map<Integer, Integer> parseResults(List<String> lines) {
        Map<Integer, Integer> ret = new HashMap<>();

        lines.forEach((current) -> {
            String[] parts = current.split("\t");

            if (parts.length == 2) {
                ret.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            } else {
                throw new RuntimeException("Invalid formatting.");
            }
        });

        return ret;
    }

    /**
     * Used internally to parse the input file
     * to a {@link Set} pf {@link BandInfo} objects.
     *
     * @param lines from the input file.
     *
     * @return a {@link Set} pf {@link BandInfo} objects.
     */
    public static Set<BandInfo> parseBands(List<String> lines) {
        Set<BandInfo> bands = new HashSet<>();

        for (String line : lines) {
            String[] parts = line.split("\t");

            if (parts.length == 3) {
                bands.add(new BandInfo(Integer.parseInt(parts[0]), parts[1], parts[2]));
            } else {
                throw new RuntimeException("Invalid band entry.");
            }
        }

        return bands;
    }
}
