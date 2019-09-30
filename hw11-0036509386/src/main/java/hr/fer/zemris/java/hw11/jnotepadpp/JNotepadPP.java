package hr.fer.zemris.java.hw11.jnotepadpp;

import hr.fer.zemris.java.hw11.jnotepadpp.local.*;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * An advanced text editor that supports multiple
 * documents open at the same time.
 *
 * @author Mateo Imbrišak
 */

public class JNotepadPP extends JFrame {
    private static final long serialVersionUID = 1L;

    /**
     * Keeps the model containing all documents.
     */
    private DefaultMultipleDocumentModel model;

    /**
     * Clock used on the statusbar.
     */
    private JLabel clock;

    /**
     * Used to signal to the clock that
     * it should stop working.
     */
    private volatile boolean terminated;

    /**
     * Provider used for translation.
     */
    private ILocalizationProvider provider =
            new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

    /**
     * Used to switch to english.
     */
    private final Action englishAction = new LocalizableAction("eng", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("en");
        }
    };

    /**
     * Used to switch to croatian.
     */
    private final Action croatianAction = new LocalizableAction("cro", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("hr");
        }
    };

    /**
     * Used to switch to german.
     */
    private final Action germanAction = new LocalizableAction("ger", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("de");
        }
    };


    /**
     * Used to create a new document.
     */
    private final Action newAction = new LocalizableAction("new", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            newImpl();
        }
    };

    /**
     * Used to open a document.
     */
    private final Action openAction = new LocalizableAction("open", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            openImpl();
        }
    };

    /**
     * Used to save a document with a new name.
     */
    private final Action saveAsAction = new LocalizableAction("save_as", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            saveAsImpl();
        }
    };

    /**
     * Used to save a document to its current source.
     */
    private final Action saveAction = new LocalizableAction("save", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            saveImpl();
        }
    };

    /**
     * Used to close a document.
     */
    private final Action closeAction = new LocalizableAction("close", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            closeImpl();
        }
    };

    /**
     * Used to quit the application.
     */
    private final Action quitAction = new LocalizableAction("quit", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            quitImpl();
        }
    };

    /**
     * Used to cut selected text.
     */
    private final Action cutAction = new LocalizableAction("cut", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            (new DefaultEditorKit.CutAction()).actionPerformed(e);
        }
    };

    /**
     * Used to copy selected text.
     */
    private final Action copyAction = new LocalizableAction("copy", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            (new DefaultEditorKit.CopyAction()).actionPerformed(e);
        }
    };

    /**
     * Used to paste text from the clipboard.
     */
    private final Action pasteAction = new LocalizableAction("paste", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            (new DefaultEditorKit.PasteAction()).actionPerformed(e);
        }
    };

    /**
     * Used to show statistics.
     */
    private final Action statsAction = new LocalizableAction("stats", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            statsImpl();
        }
    };

    /**
     * Used to invert letter casing.
     */
    private final Action toggleAction = new LocalizableAction("toggle", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            caseImpl(JNotepadPP.this::toggleCase);
        }
    };

    /**
     * Used to switch to lower casing.
     */
    private final Action lowerAction = new LocalizableAction("lower", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            caseImpl(String::toLowerCase);
        }
    };

    /**
     * Used to switch to upper case.
     */
    private final Action upperAction = new LocalizableAction("upper", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            caseImpl(String::toUpperCase);
        }
    };

    /**
     * Used to sort in ascending order.
     */
    private final Action ascAction = new LocalizableAction("asc", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            Locale current = new Locale(provider.getCurrentLanguage());
            sortImpl(Collator.getInstance(current), false);
        }
    };

    /**
     * Used to sort in descending order.
     */
    private final Action descAction = new LocalizableAction("desc", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            Locale current = new Locale(provider.getCurrentLanguage());
            sortImpl(Collator.getInstance(current), true);
        }
    };

    /**
     * Used to remove duplicates.
     */
    private final Action uniqueAction = new LocalizableAction("unique", provider) {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            uniqueImpl();
        }
    };

    /**
     * Default constructor that initializes the GUI.
     */
    public JNotepadPP() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        WindowAdapter closingAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                quitImpl();
            }
        };
        addWindowListener(closingAdapter);

        initGUI();
        setVisible(true);
    }

    /**
     * Used internally to initialize the GUI.
     */
    private void initGUI() {
        model = new DefaultMultipleDocumentModel();
        add(model, BorderLayout.CENTER);
        setSize(700, 700);
        setTitle("(unnamed) - JNotepad++");

        model.addMultipleDocumentListener(new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                Path currentPath = currentModel.getFilePath();
                JNotepadPP.this.setTitle((currentPath == null ? provider.getString("unnamed")
                        : currentPath.toAbsolutePath().toString()) + " - JNotepad++");
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {}

            @Override
            public void documentRemoved(SingleDocumentModel model) {}
        });

        createActions();
        initMenuBar();
        getContentPane().add(createToolbar(), BorderLayout.PAGE_START);
        getContentPane().add(createStatusBar(), BorderLayout.PAGE_END);
    }

    /**
     * Used internally to create actions.
     */
    private void createActions() {
        newAction.putValue(Action.NAME, provider.getString("new"));
        newAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
        newAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
        newAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("new_description"));

        openAction.putValue(Action.NAME, provider.getString("open"));
        openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
        openAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("open_description"));

        saveAsAction.putValue(Action.NAME, provider.getString("save_as"));
        saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
        saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        saveAsAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("save_description"));

        saveAction.putValue(Action.NAME, provider.getString("save"));
        saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
        saveAction.putValue(Action.SHORT_DESCRIPTION, "save_description");

        closeAction.putValue(Action.NAME, provider.getString("close"));
        closeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
        closeAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
        closeAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("close_description"));

        quitAction.putValue(Action.NAME, provider.getString("quit"));
        quitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
        quitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
        quitAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("quit_description"));

        cutAction.putValue(Action.NAME, provider.getString("cut"));
        cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
        cutAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("cut_description"));

        copyAction.putValue(Action.NAME, provider.getString("copy"));
        copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        copyAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("copy_description"));

        pasteAction.putValue(Action.NAME, provider.getString("paste"));
        pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
        pasteAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("paste_description"));

        statsAction.putValue(Action.NAME, provider.getString("stats"));
        statsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
        statsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        statsAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("stats_description"));

        croatianAction.putValue(Action.NAME, provider.getString("cro"));
        croatianAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control H"));
        croatianAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
        croatianAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("cro_description"));

        englishAction.putValue(Action.NAME, provider.getString("eng"));
        englishAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
        englishAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
        englishAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("eng_description"));

        germanAction.putValue(Action.NAME, provider.getString("ger"));
        germanAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
        germanAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        germanAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("ger_description"));

        toggleAction.putValue(Action.NAME, provider.getString("toggle"));
        toggleAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control G"));
        toggleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
        toggleAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("toggle_description"));
        toggleAction.setEnabled(false);

        lowerAction.putValue(Action.NAME, provider.getString("lower"));
        lowerAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
        lowerAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
        lowerAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("lower_description"));
        lowerAction.setEnabled(false);

        upperAction.putValue(Action.NAME, provider.getString("upper"));
        upperAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
        upperAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
        upperAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("upper_description"));
        upperAction.setEnabled(false);

        ascAction.putValue(Action.NAME, provider.getString("asc"));
        ascAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift A"));
        ascAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        ascAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("asc_description"));
        ascAction.setEnabled(false);

        descAction.putValue(Action.NAME, provider.getString("desc"));
        descAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift D"));
        descAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        descAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("desc_description"));
        descAction.setEnabled(false);

        uniqueAction.putValue(Action.NAME, provider.getString("unique"));
        uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift Q"));
        uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
        uniqueAction.putValue(Action.SHORT_DESCRIPTION, provider.getString("unique_description"));
        uniqueAction.setEnabled(false);
    }

    /**
     * Used internally to initialize the menus.
     */
    private void initMenuBar() {
        JMenuBar menu = new JMenuBar();

        JMenu file = new LJMenu("file", provider);

        file.add(newAction);
        file.add(openAction);
        file.addSeparator();
        file.add(saveAction);
        file.add(saveAsAction);
        file.add(closeAction);
        file.addSeparator();
        file.add(quitAction);

        menu.add(file);

        JMenu edit = new LJMenu("edit", provider);

        edit.add(cutAction);
        edit.add(copyAction);
        edit.add(pasteAction);
        edit.addSeparator();
        edit.add(statsAction);

        menu.add(edit);

        JMenu languages = new LJMenu("languages", provider);

        languages.add(englishAction);
        languages.add(croatianAction);
        languages.add(germanAction);

        menu.add(languages);

        JMenu casing = new LJMenu("casing", provider);

        casing.add(upperAction);
        casing.add(lowerAction);
        casing.add(toggleAction);

        menu.add(casing);

        JMenu sorting = new LJMenu("sorting", provider);

        sorting.add(ascAction);
        sorting.add(descAction);
        sorting.addSeparator();
        sorting.add(uniqueAction);

        menu.add(sorting);

        setJMenuBar(menu);
    }

    /**
     * Used internally to create the toolbar.
     *
     * @return created toolbar.
     */
    private JToolBar createToolbar() {
        JToolBar toolbar = new JToolBar();

        toolbar.add(newAction);
        toolbar.add(openAction);
        toolbar.add(saveAction);
        toolbar.add(saveAsAction);
        toolbar.add(closeAction);
        toolbar.addSeparator();
        toolbar.add(cutAction);
        toolbar.add(copyAction);
        toolbar.add(pasteAction);
        toolbar.add(statsAction);
        toolbar.addSeparator();
        toolbar.add(quitAction);

        return toolbar;
    }

    /**
     * Used internally to initialize the status bar.
     *
     * @return created status bar.
     */
    private JPanel createStatusBar() {
        JLabel length = new JLabel("Length: 0");
        JLabel stats = new JLabel(" Ln: 1 Col: 1");

        MultipleDocumentListener statusBarListener = new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                updateLength(currentModel);
                updatePosition(currentModel);
                checkSelection(currentModel);
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {
                model.getTextComponent().addCaretListener((e) -> {
                    updateLength(model);
                    updatePosition(model);
                    checkSelection(model);
                });
            }

            @Override
            public void documentRemoved(SingleDocumentModel model) {}

            private void updateLength(SingleDocumentModel currentModel) {
                length.setText(provider.getString("Length") + ": "
                        + currentModel.getTextComponent().getDocument().getLength());
            }

            private void updatePosition(SingleDocumentModel currentModel) {
                JTextArea editor = currentModel.getTextComponent();

                try {
                    int line = editor.getLineOfOffset(editor.getCaretPosition());
                    int column = editor.getCaretPosition() - editor.getLineStartOffset(line);
                    int selected = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

                    String sb = provider.getString("ln") + (line + 1) +
                            provider.getString("col") + (column + 1) +
                            (selected == 0 ? "" : provider.getString("sel") + selected);

                    stats.setText(sb);
                } catch (BadLocationException ignored) {}
            }

            private void checkSelection(SingleDocumentModel model) {
                boolean sel = model.getTextComponent().getCaret().getDot()
                        != model.getTextComponent().getCaret().getMark();

                toggleAction.setEnabled(sel);
                upperAction.setEnabled(sel);
                lowerAction.setEnabled(sel);
                ascAction.setEnabled(sel);
                descAction.setEnabled(sel);
                uniqueAction.setEnabled(sel);
            }
        };

        model.addMultipleDocumentListener(statusBarListener);
        statusBarListener.documentAdded(model.getCurrentDocument());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(length, BorderLayout.WEST);
        panel.add(stats, BorderLayout.CENTER);

        clockThread.setDaemon(true);
        clockThread.start();

        clock = new JLabel();
        terminated = false;

        panel.add(clock, BorderLayout.EAST);

        return panel;
    }

    /**
     * A {@code Thread} that handles the clock.
     */
    private Thread clockThread = new Thread(new Runnable() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        @Override
        public void run() {
            while (!terminated) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}

                SwingUtilities.invokeLater(() -> {
                    clock.setText(format.format(LocalDateTime.now()));
                    repaint();
                });
            }
        }
    });

    /**
     * Implementation of {@link #newAction}.
     */
    private void newImpl() {
        model.createNewDocument();
    }

    /**
     * Implementation of {@link #openAction}.
     */
    private void openImpl() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Open file");

        if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path src = jfc.getSelectedFile().toPath();

        if (!Files.isReadable(src)) {
            JOptionPane.showOptionDialog(
                    this,
                    "File " + src.toString() + " is not readable.",
                    "Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null, null, null);
        }

        try {
            model.loadDocument(src);
        } catch (RuntimeException exc) {
            JOptionPane.showOptionDialog(
                    this,
                    "Error while opening " + src.toString(),
                    "Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null, null, null);
        }
    }

    /**
     * Implementation of {@link #saveAsAction}.
     *
     * @return {@code false} if user canceled the action,
     * otherwise {@code true}.
     */
    private boolean saveAsImpl() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save as");

        if (jfc.showSaveDialog(this) == JFileChooser.CANCEL_OPTION) {
            return false;
        }

        Path openFilePath = jfc.getSelectedFile().toPath();

        if (Files.exists(openFilePath)) {
            int result = JOptionPane.showOptionDialog(
                    this,
                    "File " + openFilePath.getFileName() + " already exists.\nDo You want to overwrite it?",
                    "Save as",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null
            );

            if (result == JOptionPane.NO_OPTION) {
                return saveAsImpl();
            }
        }

        return performSave(openFilePath);
    }

    /**
     * Implementation of {@link #saveAction}.
     *
     * @return {@code false} if user canceled the action,
     * otherwise {@code true}.
     */
    private boolean saveImpl() {
        if (model.getCurrentDocument().getFilePath() == null) {
            return saveAsImpl();
        }

        return performSave(null);
    }

    /**
     * Used internally to save the file.
     *
     * @param dest where the file is being saved.
     *
     * @return {@code true} if the file has been saved
     * successfully, otherwise {@code false} and an
     * appropriate dialog is shown.
     */
    private boolean performSave(Path dest) {
        try {
            model.saveDocument(model.getCurrentDocument(), dest);
        } catch (ConcurrentModificationException exc) {
            JOptionPane.showOptionDialog(
                    this,
                    "File is already open..",
                    "Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null, null, null
            );
            return false;
        }
        return true;
    }

    /**
     * Implementation of {@link #closeAction}.
     */
    private void closeImpl() {
        SingleDocumentModel current = model.getCurrentDocument();
        if (checkIfSaved(current)) {
            model.closeDocument(current);
        }
    }

    /**
     * Implementation of {@link #quitAction}.
     */
    private void quitImpl() {
        for (int i = 0; i < model.getNumberOfDocuments(); i++) {
            if (!checkIfSaved(model.getDocument(i))) {
                return;
            }
        }

        dispose();
        terminated = true;
    }

    /**
     * Implementation of {@link #statsAction}.
     */
    private void statsImpl() {
        Document current = model.getCurrentDocument().getTextComponent().getDocument();

        long all = current.getLength();
        long nonBlank = 0;
        long lines = 0;

        try {
            String nonBlankString = current.getText(0, (int) all);
            nonBlankString = nonBlankString.replace("\n", "");
            lines =  all - nonBlankString.length() + 1;
            nonBlankString = nonBlankString.replace(" ", "")
                                            .replace("\t", "");
            nonBlank = nonBlankString.length();
        } catch (BadLocationException ignored) {}

        JOptionPane.showOptionDialog(this,
                "Your document has " + all + " characters, " + nonBlank + " non-blank characters and " +
                        lines + " lines.",
                "Statistics",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, null, null
                );
    }

    /**
     * Used to sort selected lines.
     *
     * @param collator used to compare.
     * @param desc whether the comparison is reversed or not.
     */
    private void sortImpl(Collator collator, boolean desc) {
        Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
        JTextArea tc = model.getCurrentDocument().getTextComponent();

        try {
            int start = Math.min(tc.getLineStartOffset(tc.getLineOfOffset(caret.getDot())),
                    tc.getLineStartOffset(tc.getLineOfOffset(caret.getMark())));
            int end = Math.max(tc.getLineEndOffset(tc.getLineOfOffset(caret.getDot())),
                    tc.getLineEndOffset(tc.getLineOfOffset(caret.getMark())));

            String text = model.getCurrentDocument().getTextComponent().getDocument().getText(start, end);
            List<String> split = Arrays.asList(text.split("\n"));

            split.sort(desc ? collator.reversed() : collator);

            StringBuilder sb = new StringBuilder();

            split.forEach((current) -> sb.append(current).append("\n"));

            model.getCurrentDocument().getTextComponent().getDocument().remove(start, end);
            model.getCurrentDocument().getTextComponent().getDocument().insertString(start, sb.toString(), null);
        } catch (BadLocationException ignored) {}
    }

    /**
     * Used to eliminate duplicates.
     */
    private void uniqueImpl() {
        Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
        JTextArea tc = model.getCurrentDocument().getTextComponent();

        try {
            int start = Math.min(tc.getLineStartOffset(tc.getLineOfOffset(caret.getDot())),
                    tc.getLineStartOffset(tc.getLineOfOffset(caret.getMark())));
            int end = Math.max(tc.getLineEndOffset(tc.getLineOfOffset(caret.getDot())),
                    tc.getLineEndOffset(tc.getLineOfOffset(caret.getMark())));

            String text = model.getCurrentDocument().getTextComponent().getDocument().getText(start, end);
            List<String> split = Arrays.asList(text.split("\n"));

            Set<String> filter = new LinkedHashSet<>(split);

            StringBuilder sb = new StringBuilder();

            filter.forEach((current) -> sb.append(current).append("\n"));

            model.getCurrentDocument().getTextComponent().getDocument().remove(start, end);
            model.getCurrentDocument().getTextComponent().getDocument().insertString(start, sb.toString(), null);
        } catch (BadLocationException ignored) {}
    }

    /**
     * Implementation of all methods that affect the letter casing.
     *
     * @param toggle being used on the letters.
     */
    private void caseImpl(UnaryOperator<String> toggle) {
        int start = Math.min(model.getCurrentDocument().getTextComponent().getCaret().getDot(),
                model.getCurrentDocument().getTextComponent().getCaret().getMark());
        int len = Math.abs(model.getCurrentDocument().getTextComponent().getCaret().getDot()
                - model.getCurrentDocument().getTextComponent().getCaret().getMark());

        if (len < 1) return;

        Document doc = model.getCurrentDocument().getTextComponent().getDocument();

        try {
            String text = doc.getText(start, len);
            text = toggle.apply(text);

            doc.remove(start, len);
            doc.insertString(start, text, null);

            model.getCurrentDocument().getTextComponent().setSelectionStart(start);
            model.getCurrentDocument().getTextComponent().setSelectionEnd(start + len);
        } catch (BadLocationException ignored) {}
    }

    /**
     * Switches the casing on the given text.
     *
     * @param text to switch casing.
     *
     * @return same text with switched casing.
     */
    private String toggleCase(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                chars[i] = Character.toLowerCase(chars[i]);
            } else if (Character.isLowerCase(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
            }
        }

        return new String(chars);
    }

    /**
     * Used to check if user wants to save an
     * edited document before closing.
     *
     * @param model document being closed.
     *
     * @return {@code false} if user selected
     * cancel option, otherwise {@code true}.
     */
    private boolean checkIfSaved(SingleDocumentModel model) {
        if (model.isModified()) {
            int result = JOptionPane.showOptionDialog(this,
                    "Do you want to save " +
                            (model.getFilePath() == null ? "this file" : model.getFilePath().getFileName() ) + "?",
                    "Save",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null
                    );
            switch (result) {
                case JOptionPane.YES_OPTION:
                    return saveImpl();
                case JOptionPane.NO_OPTION:
                    return true;
                case JOptionPane.CANCEL_OPTION:
                    return false;
            }
        }

        return true;
    }

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JNotepadPP::new);
    }
}
