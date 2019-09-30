package hr.fer.zemris.java.hw14.voting.util;

import java.util.Objects;

/**
 * An auxiliary class used to keep
 * info about a poll option.
 *
 * @author Mateo Imbrišak
 */
public class PollOptionInfo implements Comparable<PollOptionInfo> {

    /**
     * Keeps the band's id used for voting.
     */
    private final int id;

    /**
     * Keeps the option's title.
     */
    private final String title;

    /**
     * Keeps a link to an example of this option.
     */
    private final String link;

    /**
     * Keeps the number of votes.
     */
    private final int numberOfVotes;

    /**
     * Default constructor that assigns all values.
     *
     * @param id to be assigned.
     * @param title to be assigned.
     * @param link to be assigned.
     * @param numberOfVotes to be assigned.
     */
    public PollOptionInfo(int id, String title, String link, int numberOfVotes) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.numberOfVotes = numberOfVotes;
    }

    /**
     * Provides the band's id.
     *
     * @return band's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Provides the band's title.
     *
     * @return band's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Provides the link to a song.
     *
     * @return link to a song.
     */
    public String getLink() {
        return link;
    }

    /**
     * Provides the number of votes.
     *
     * @return number of votes.
     */
    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    @Override
    public int compareTo(PollOptionInfo o) {
        int result = Integer.compare(o.numberOfVotes, numberOfVotes);
        if (result == 0) {
            return title.compareTo(o.title);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollOptionInfo pollOptionInfo = (PollOptionInfo) o;
        return id == pollOptionInfo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
