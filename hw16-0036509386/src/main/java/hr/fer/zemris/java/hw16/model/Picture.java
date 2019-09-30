package hr.fer.zemris.java.hw16.model;

import java.util.Objects;

/**
 * An auxiliary class used to model a picture.
 *
 * @author Mateo Imbrišak
 */

public class Picture {

    /**
     * Keeps the picture's name.
     */
    private final String name;

    /**
     * Keeps the picture's description.
     */
    private final String description;

    /**
     * Keeps the picture's tags.
     */
    private final String[] tags;

    /**
     * Default constructor that assigns all values.
     *
     * @param name to be assigned.
     * @param description to be assigned.
     * @param tags to be assigned.
     */
    public Picture(String name, String description, String ... tags) {
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    /**
     * Provides the picture's name.
     *
     * @return picture's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Provides the picture's description.
     *
     * @return picture's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Provides the picture's tags.
     *
     * @return picture's tags.
     */
    public String[] getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Picture picture = (Picture) o;
        return name.equals(picture.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
