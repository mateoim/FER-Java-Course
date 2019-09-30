package hr.fer.zemris.java.hw11.jnotepadpp.model;

/**
 * A listener for {@link MultipleDocumentModel}.
 *
 * @author Mateo Imbrišak
 */

public interface MultipleDocumentListener {

    /**
     * Used when the document has been changed.
     *
     * @param previousModel model before the change.
     * @param currentModel model after the change.
     */
    void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

    /**
     * Used when a new model has been added.
     *
     * @param model that has been added.
     */
    void documentAdded(SingleDocumentModel model);

    /**
     * Used when a document has been removed.
     *
     * @param model that has been removed.
     */
    void documentRemoved(SingleDocumentModel model);
}
