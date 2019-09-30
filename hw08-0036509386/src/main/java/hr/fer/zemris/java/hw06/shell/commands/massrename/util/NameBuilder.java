package hr.fer.zemris.java.hw06.shell.commands.massrename.util;

/**
 * An interface used to model classes that
 * build a part of the new name for
 * {@code MassrenameShellCommand}.
 *
 * @author Mateo Imbrišak
 */

public interface NameBuilder {

    /**
     * Writes a part of a name to the given {@code StringBuilder}.
     *
     * @param result being renamed.
     * @param sb {@code StringBuilder} that keeps all parts of the name.
     */
    void execute(FilterResult result, StringBuilder sb);
}
