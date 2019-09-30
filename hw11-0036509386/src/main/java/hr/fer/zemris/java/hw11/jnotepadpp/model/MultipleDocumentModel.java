package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.nio.file.Path;

/**
 * A class that represents a model
 * that holds multiple {@link SingleDocumentModel}s.
 *
 * @author Mateo Imbrišak
 */

public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

    /**
     * Used to create a new document.
     *
     * @return created document.
     */
    SingleDocumentModel createNewDocument();

    /**
     * Provides the current document.
     *
     * @return current document.
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * Used to load an existing document.
     *
     * @param path to the document.
     *
     * @return loaded document.
     */
    SingleDocumentModel loadDocument(Path path);

    /**
     * Saves the given document.
     *
     * @param model document to be saved.
     * @param newPath to the document.
     */
    void saveDocument(SingleDocumentModel model, Path newPath);

    /**
     * Closes the given document.
     *
     * @param model document to be closed.
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * Adds the given listener to this model.
     *
     * @param l listener to be added.
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Removes the given listener form this model.
     *
     * @param l listener to be removed.
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Provides the number of open documents.
     *
     * @return number of open documents.
     */
    int getNumberOfDocuments();

    /**
     * Provides the document at the given index.
     *
     * @param index of the document you want.
     *
     * @return document at the given index.
     */
    SingleDocumentModel getDocument(int index);
}
