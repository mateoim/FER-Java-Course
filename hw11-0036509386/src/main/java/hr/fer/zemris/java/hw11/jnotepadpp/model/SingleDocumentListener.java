package hr.fer.zemris.java.hw11.jnotepadpp.model;

/**
 * An observer used to track changes made to
 * the {@link SingleDocumentModel}.
 *
 * @author Mateo Imbrišak
 */

public interface SingleDocumentListener {

    /**
     * Used to notify that the modified
     * status has been changed.
     *
     * @param model where the change occurred.
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);

    /**
     * Used to notify that the file path
     * has changed.
     *
     * @param model where the change occurred.
     */
    void documentFilePathUpdated(SingleDocumentModel model);
}
