package hr.fer.zemris.java.hw11.jnotepadpp;

import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * A class that represents a model capable of
 * holding multiple documents open as tabs in
 * a {@code JTabbedPane}.
 *
 * @author Mateo Imbrišak
 */

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
    private static final long serialVersionUID = 1L;

    /**
     * Keeps the list of currently opened documents.
     */
    private List<SingleDocumentModel> documents;

    /**
     * Keeps the currently displayed document.
     */
    private SingleDocumentModel current;

    /**
     * Keeps active listeners.
     */
    private List<MultipleDocumentListener> listeners;

    /**
     * Default constructor that initializes all values
     * and creates a new document to be used as {@link #current}.
     */
    public DefaultMultipleDocumentModel() {
        documents = new ArrayList<>();
        listeners = new LinkedList<>();
        current = createNewDocument();

        addChangeListener((e) -> {
            SingleDocumentModel old = current;
            current = documents.get(getSelectedIndex());
            listeners.forEach((listener) -> listener.currentDocumentChanged(old, current));
        });
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        SingleDocumentModel created = new DefaultSingleDocumentModel(null, "");

        addCreatedDocument(created);

        return created;
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return current;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) {
        for (int i = 0; i < documents.size(); i++) {
            SingleDocumentModel doc = documents.get(i);

            if (doc.getFilePath() != null && doc.getFilePath().toAbsolutePath().equals(path.toAbsolutePath())) {
                SingleDocumentModel old = current;
                current = doc;

                listeners.forEach((listener) -> listener.currentDocumentChanged(old, current));

                setSelectedIndex(i);
                return doc;
            }
        }

        String input;
        try {
            input = Files.readString(path, Charset.forName("UTF-8"));
        } catch (IOException ignored) {
            throw new RuntimeException("Error while reading file.");
        }

        SingleDocumentModel ret = new DefaultSingleDocumentModel(path, input == null ? "" : input);
        addCreatedDocument(ret);

        return ret;
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        if (newPath != null) {
            forEach((doc) -> {
                if (newPath.equals(doc.getFilePath()) && doc != model) {
                    throw new ConcurrentModificationException("File already open.");
                }
            });
        }

        try {
            Files.writeString(newPath == null ? model.getFilePath() : newPath,
                    model.getTextComponent().getText(), Charset.forName("UTF-8"));
        } catch (IOException ignored) {
            throw new RuntimeException("An error occurred while writing the file.");
        }

        model.setModified(false);
        model.setFilePath(newPath == null ? model.getFilePath() : newPath);
    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        if (documents.size() == 1) {
            createNewDocument();
        }

        int index = documents.indexOf(model);
        removeTabAt(index);
        documents.remove(index);

        index = index == 0 ? 0 : index - 1;

        current = documents.get(index);
        setSelectedIndex(index);

        listeners.forEach((listener) -> listener.documentRemoved(model));
        listeners.forEach((listener) -> listener.currentDocumentChanged(model, current));
    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);
    }

    @Override
    public int getNumberOfDocuments() {
        return documents.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return documents.get(index);
    }

    /**
     * Used internally to add a newly created document
     * to the list and create a new tab for it.
     *
     * @param created document that has been created.
     */
    private void addCreatedDocument(SingleDocumentModel created) {
        documents.add(created);
        addTab(created.getFilePath() == null ? "(untitled)" : created.getFilePath().getFileName().toString(),
                new JScrollPane(created.getTextComponent()));
        setToolTipTextAt(documents.indexOf(created),
                created.getFilePath() == null ? "(untitled)" : created.getFilePath().toAbsolutePath().toString());
        setIconAt(documents.indexOf(created), getImage("icons/greenDisk.png"));

        created.addSingleDocumentListener(new SingleDocumentListener() {
            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                setTitleAt(getSelectedIndex(), (created.isModified() ? "* " : "") +
                        (created.getFilePath() == null ?
                                "(untitled)" : created.getFilePath().getFileName().toString()));
                setIconAt(getSelectedIndex(), getImage(model.isModified() ? "icons/redDisk.png" : "icons/greenDisk.png"));
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                documentModifyStatusUpdated(model);
                setToolTipTextAt(documents.indexOf(created),
                        created.getFilePath() == null ? "(untitled)" : created.getFilePath().toAbsolutePath().toString());
            }
        });

        SingleDocumentModel old = current;
        current = created;

        listeners.forEach((listener) -> listener.documentAdded(created));
        listeners.forEach((listener) -> listener.currentDocumentChanged(old, created));

        setSelectedIndex(documents.indexOf(created));
    }

    /**
     * Used internally to get an image to be used
     * for an icon.
     *
     * @param path to the image.
     *
     * @return a new {@code ImageIcon} from the
     * given image.
     */
    private ImageIcon getImage(String path) {
        try (InputStream is = this.getClass().getResourceAsStream(path)) {

            if (is == null) {
                throw new RuntimeException("Cannot access file.");
            }

            byte[] bytes = is.readAllBytes();

            return new ImageIcon(bytes);
        } catch (IOException exc) {
            throw new RuntimeException("Error while reading file.");
        }
    }

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return documents.iterator();
    }
}
