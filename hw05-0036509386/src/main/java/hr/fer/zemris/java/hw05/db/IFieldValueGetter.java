package hr.fer.zemris.java.hw05.db;

/**
 * Used to get a given value type from {@code StudentRecord}.
 *
 * @author Mateo Imbrišak
 */

public interface IFieldValueGetter {

    /**
     * Returns a requested value type from a
     * given {@code StudentRecord}.
     *
     * @param record whose value you want.
     *
     * @return requested value as a {@code String}.
     */
    String get(StudentRecord record);
}
