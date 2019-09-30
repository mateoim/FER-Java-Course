package hr.fer.zemris.java.hw14.voting.util;

/**
 * An auxiliary class used to keep data
 * about a poll.
 *
 * @author Mateo Imbrišak
 */

public class PollInfo {

    /**
     * Keeps the poll's id.
     */
    private final int id;

    /**
     * Keeps the poll's title.
     */
    private final String title;

    /**
     * Keeps the poll's message.
     */
    private final String message;

    /**
     * Default constructor that assigns all values.
     *
     * @param id to be assigned.
     * @param title to be assigned.
     * @param message to be assigned.
     */
    public PollInfo(int id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    /**
     * Provides the poll's ID.
     *
     * @return poll's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Provides the poll's title.
     *
     * @return poll's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Provides the poll's description.
     *
     * @return poll's description.
     */
    public String getMessage() {
        return message;
    }
}
