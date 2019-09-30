package hr.fer.zemris.java.servlets.voting.util;

import java.util.Objects;

/**
 * An auxiliary class used to keep
 * info about a band.
 */
public class BandInfo {

    /**
     * Keeps the band's id used for voting.
     */
    private final int id;

    /**
     * Keeps the band's name.
     */
    private final String name;

    /**
     * Keeps a link to the band's song on YouTube.
     */
    private final String exampleSong;

    /**
     * Default constructor that assigns all values.
     *
     * @param id to be assigned.
     * @param name to be assigned.
     * @param exampleSong to be assigned.
     */
    public BandInfo(int id, String name, String exampleSong) {
        this.id = id;
        this.name = name;
        this.exampleSong = exampleSong;
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
     * Provides the band's name.
     *
     * @return band's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Provides the link to a song.
     *
     * @return link to a song.
     */
    public String getExampleSong() {
        return exampleSong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BandInfo bandInfo = (BandInfo) o;
        return id == bandInfo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
