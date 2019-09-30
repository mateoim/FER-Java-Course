package hr.fer.zemris.java.hw05.db;

/**
 * Filters the records in {@code StudentDatabase} based
 * on the given criteria.
 *
 * @author Mateo Imbrišak
 */

public interface IFilter {

    /**
     * Tests whether the given record is acceptable.
     *
     * @param record being tested.
     *
     * @return {@code true} if the record is acceptable,
     * otherwise {@code false}.
     */
    boolean accepts(StudentRecord record);
}
