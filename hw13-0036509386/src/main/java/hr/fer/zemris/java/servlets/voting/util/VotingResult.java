package hr.fer.zemris.java.servlets.voting.util;

/**
 * An auxiliary class used to
 * keep pairs of {@link BandInfo}
 * and number of that band's votes.
 *
 * @author Mateo Imbrišak
 */

public class VotingResult implements Comparable<VotingResult> {

    /**
     * Keeps the band's info.
     */
    private final BandInfo band;

    /**
     * Keeps the current number of votes.
     */
    private final int numberOfVotes;

    /**
     * Default constructor that assigns all values.
     *
     * @param band to be assigned.
     * @param numberOfVotes to be assigned.
     */
    public VotingResult(BandInfo band, int numberOfVotes) {
        this.band = band;
        this.numberOfVotes = numberOfVotes;
    }

    /**
     * Provides the band's info.
     *
     * @return band's info as a {@link BandInfo}
     * Object.
     */
    public BandInfo getBand() {
        return band;
    }

    /**
     * Provides the current number of votes.
     *
     * @return current number of votes.
     */
    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    @Override
    public int compareTo(VotingResult o) {
        int result = Integer.compare(o.numberOfVotes, numberOfVotes);
        if (result == 0) {
            return band.getName().compareTo(o.band.getName());
        }

        return result;
    }
}
