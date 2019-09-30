package hr.fer.zemris.java.hw11.jnotepadpp;

import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a single document.
 *
 * @author Mateo Imbrišak
 */

public class DefaultSingleDocumentModel implements SingleDocumentModel {

    /**
     * Keeps the editor.
     */
    private JTextArea editor;

    /**
     * Keeps the current path to the file.
     */
    private Path filePath;

    /**
     * Keeps track of whether this document has been modified.
     */
    private boolean modified;

    /**
     * Keeps currently active listeners.
     */
    private List<SingleDocumentListener> listeners;

    /**
     * Default constructor that assigns all values.
     *
     * @param src path to file.
     * @param text from the file.
     */
    public DefaultSingleDocumentModel(Path src, String text) {
        filePath = src;
        editor = new JTextArea(text);
        modified = false;
        listeners = new LinkedList<>();

        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setModified(true);
            }
        });
    }

    @Override
    public JTextArea getTextComponent() {
        return editor;
    }

    @Override
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(Path path) {
        Objects.requireNonNull(path);

        if (filePath == null || !filePath.equals(path)) {
            filePath = path;
            listeners.forEach((current) -> current.documentFilePathUpdated(this));
        }
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        if (this.modified != modified) {
            this.modified = modified;
            listeners.forEach((current) -> current.documentModifyStatusUpdated(this));
        }
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(l);
    }
}
