package hr.fer.zemris.java.hw11.jnotepadpp.model;

import javax.swing.*;
import java.nio.file.Path;

/**
 * A class that represents a single document.
 *
 * @author Mateo Imbrišak
 */

public interface SingleDocumentModel {

    /**
     * Provides the document's text editor.
     *
     * @return document's text editor.
     */
    JTextArea getTextComponent();

    /**
     * Provides the path to this document.
     *
     * @return {@code path} to the document if it
     * is saved, otherwise {@code null}.
     */
    Path getFilePath();

    /**
     * Sets the path to the given path.
     *
     * @param path to be set.
     * @throws NullPointerException if the
     * given path is {@code null}.
     */
    void setFilePath(Path path);

    /**
     * Used to check if this document has been modified.
     *
     * @return {@code true} if the document has been modified,
     * otherwise {@code false}.
     */
    boolean isModified();

    /**
     * Changes the modified status.
     *
     * @param modified status to be set.
     */
    void setModified(boolean modified);

    /**
     * Adds the given listener to this document.
     *
     * @param l listener to be added.
     */
    void addSingleDocumentListener(SingleDocumentListener l);

    /**
     * Removes the given listener from this document.
     *
     * @param l listener to be removed.
     */
    void removeSingleDocumentListener(SingleDocumentListener l);
}
