package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.components.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.components.JColorInfo;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.visitors.SaveVisitor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple drawing program.
 *
 * @author Mateo Imbrišak
 */

public class JVDraw extends JFrame {

    /**
     * Keeps the {@link DrawingModel}.
     */
    private DrawingModel model;

    /**
     * Keeps the {@link JDrawingCanvas}.
     */
    private JDrawingCanvas canvas;

    /**
     * Keeps the list of {@link GeometricalObject}s.
     */
    private JList<GeometricalObject> list;

    /**
     * Keeps the foreground color.
     */
    private JColorArea fgColorArea;

    /**
     * Keeps the background color.
     */
    private JColorArea bgColorArea;

    /**
     * Keeps the {@link LineTool}.
     */
    private LineTool lineTool;

    /**
     * Keeps the {@link CircleTool}.
     */
    private CircleTool circleTool;

    /**
     * Keeps the {@link FilledCircleTool}.
     */
    private FilledCircleTool filledCircleTool;

    /**
     * Keeps the {@link Path} to the current file.
     */
    private Path path = null;

    /**
     * Used to save the file to the current {@link #path}.
     */
    private final Action save = new AbstractAction() {
        private static final long serialVersionUID = -4274265200398310412L;

        @Override
        public void actionPerformed(ActionEvent e) {
            saveImpl(false);
        }
    };

    /**
     * Used to save the file to a new {@link #path}.
     */
    private final Action saveAs = new AbstractAction() {
        private static final long serialVersionUID = -4274265200398310412L;

        @Override
        public void actionPerformed(ActionEvent e) {
            saveImpl(true);
        }
    };

    /**
     * Used to open a saved file.
     */
    private final Action open = new AbstractAction() {
        private static final long serialVersionUID = -1249824525445902450L;

        @Override
        public void actionPerformed(ActionEvent e) {
            loadImpl();
        }
    };

    /**
     * Used to export the image.
     */
    private final Action export = new AbstractAction() {
        private static final long serialVersionUID = -6327001929941034000L;

        @Override
        public void actionPerformed(ActionEvent e) {
            exportImpl();
        }
    };

    /**
     * Used to quit the application.
     */
    private final Action exit = new AbstractAction() {
        private static final long serialVersionUID = -6327001929941034000L;

        @Override
        public void actionPerformed(ActionEvent e) {
            exitImpl();
        }
    };

    /**
     * Default constructor that initializes the GUI.
     */
    public JVDraw() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(700, 500);
        setTitle("JVDraw");

        initGUI();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitImpl();
            }
        });

        setVisible(true);
    }

    /**
     * Used internally to initialize the GUI.
     */
    private void initGUI() {
        initToolBars();

        model = new DrawingModelImpl();
        list = new JList<>(new DrawingObjectListModel(model));

        lineTool = new LineTool(model, fgColorArea);
        circleTool = new CircleTool(model, fgColorArea);
        filledCircleTool = new FilledCircleTool(model, fgColorArea, bgColorArea);

        canvas = new JDrawingCanvas(model, lineTool);

        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        pane.setResizeWeight(0.65);
        pane.setLeftComponent(canvas);
        pane.setRightComponent(list);

        getContentPane().add(pane, BorderLayout.CENTER);

        createActions();
        initMenuBar();
    }

    /**
     * Used internally to initialize the toolbars.
     */
    private void initToolBars() {
        JToolBar top = new JToolBar();
        JToolBar bottom = new JToolBar();

        fgColorArea = new JColorArea(Color.RED);
        bgColorArea = new JColorArea(Color.BLUE);

        JColorInfo label = new JColorInfo(fgColorArea, bgColorArea);

        bottom.add(label);
        bottom.setFloatable(false);

        top.add(fgColorArea);
        top.add(bgColorArea);
        top.addSeparator();

        JToggleButton line = new JToggleButton("Line");
        top.add(line);
        line.addActionListener((e) -> canvas.setTool(lineTool));

        JToggleButton circle = new JToggleButton("Circle");
        top.add(circle);
        circle.addActionListener((e) -> canvas.setTool(circleTool));

        JToggleButton filledCircle = new JToggleButton("Filled circle");
        top.add(filledCircle);
        filledCircle.addActionListener((e) -> canvas.setTool(filledCircleTool));

        ButtonGroup buttons = new ButtonGroup();
        buttons.add(line);
        buttons.add(circle);
        buttons.add(filledCircle);

        getContentPane().add(top, BorderLayout.PAGE_START);
        getContentPane().add(bottom, BorderLayout.PAGE_END);
    }

    /**
     * Used internally to create actions.
     */
    private void createActions() {
        open.putValue(Action.NAME, "Open");
        open.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        open.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

        save.putValue(Action.NAME, "Save");
        save.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        save.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

        saveAs.putValue(Action.NAME, "Save As");
        saveAs.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
        saveAs.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

        exit.putValue(Action.NAME, "Quit");
        exit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
        exit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);

        export.putValue(Action.NAME, "Export");
        export.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        export.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

        list.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                GeometricalObject selected = list.getSelectedValue();

                if (selected == null) {
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    model.remove(selected);
                } else {
                    if (e.getKeyChar() == '+') {
                        model.changeOrder(selected, -1);
                    } else if (e.getKeyChar() == '-') {
                        model.changeOrder(selected, 1);
                    }
                }
            }
        });

        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    GeometricalObject clicked = list.getSelectedValue();

                    if (clicked == null) {
                        return;
                    }

                    GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();

                    if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit",
                            JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        try {
                            editor.checkEditing();
                            editor.acceptEditing();
                        } catch (RuntimeException exc) {
                            JOptionPane.showMessageDialog(JVDraw.this, "Invalid data.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    /**
     * Used internally to initialize the menus.
     */
    private void initMenuBar() {
        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu("File");

        file.add(open);
        file.addSeparator();
        file.add(save);
        file.add(saveAs);
        file.addSeparator();
        file.add(export);
        file.addSeparator();
        file.add(exit);

        menu.add(file);

        setJMenuBar(menu);
    }

    /**
     * Implementation of {@link #save} and {@link #saveAs}.
     *
     * @param saveAs {@code true} if called by {@link #saveAs},
     *                           otherwise {@code false}.
     */
    private void saveImpl(boolean saveAs) {
        Path path = this.path;

        if (saveAs || path == null) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Save as");

            if (jfc.showSaveDialog(this) == JFileChooser.CANCEL_OPTION) {
                return;
            }

            path = jfc.getSelectedFile().toPath();

            if (!checkExtension(path)) {
                saveImpl(saveAs);
                return;
            }

            if (Files.exists(path)) {
                int result = JOptionPane.showOptionDialog(
                        this,
                        "File " + path.getFileName() + " already exists.\nDo You want to overwrite it?",
                        "Save as",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, null, null
                );

                if (result == JOptionPane.NO_OPTION) {
                    saveImpl(saveAs);
                    return;
                }
            }
        }

        SaveVisitor visitor = new SaveVisitor();

        for (int i = 0, size = model.getSize(); i < size; i++) {
            model.getObject(i).accept(visitor);
        }

        try {
            Files.writeString(path, visitor.getSaveText(), StandardCharsets.UTF_8);

            model.clearModifiedFlag();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Implementation of {@link #open}.
     */
    private void loadImpl() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Open file");

        if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path src = jfc.getSelectedFile().toPath();

        if (!checkExtension(src)) {
            loadImpl();
            return;
        }

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
            List<String> lines = Files.readAllLines(src);
            List<GeometricalObject> objects = new ArrayList<>();

            for (String line : lines) {
                switch (line.split(" ")[0]) {
                    case "LINE":
                       objects.add(Line.fromText(line));
                       break;
                    case "CIRCLE":
                        objects.add(Circle.fromText(line));
                        break;
                    case "FCIRCLE":
                        objects.add(FilledCircle.fromText(line));
                }

                model.clear();

                objects.forEach((current) -> model.add(current));
                model.clearModifiedFlag();
                path = src;
            }
        } catch (IOException | RuntimeException exc) {
            exc.printStackTrace();
            JOptionPane.showOptionDialog(
                    this,
                    "Error while opening " + src.getFileName(),
                    "Error",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null, null, null);
        }
    }

    /**
     * Checks the given {@link Path} for correct extension.
     *
     * @param path to be checked.
     *
     * @return {@code true} if the extension matches,
     * otherwise {@code false}.
     */
    private boolean checkExtension(Path path) {
        if (!path.toString().endsWith(".jvd")) {
            JOptionPane.showMessageDialog(this,
                    "Invalid file extension.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE,
                    null);

            return false;
        }
        return true;
    }

    /**
     * Implementation of {@link #export}.
     */
    private void exportImpl() {
        GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();

        for (int i = 0, size = model.getSize(); i < size; i++) {
            model.getObject(i).accept(bbcalc);
        }

        Rectangle box = bbcalc.getBoundingBox();
        BufferedImage image = new BufferedImage(
                box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
        );
        Graphics2D g = image.createGraphics();

        g.translate(-box.x, -box.y);

        g.setColor(Color.WHITE);
        g.fillRect(box.x, box.y, box.width, box.height);

        GeometricalObjectPainter painter = new GeometricalObjectPainter(g);

        for (int i = 0, size = model.getSize(); i < size; i++) {
            model.getObject(i).accept(painter);
        }

        g.dispose();

        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Export");

        if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path src = jfc.getSelectedFile().toPath();
        String strPath = src.toString();

        if (!strPath.endsWith(".jpg") & !strPath.endsWith(".gif") & !strPath.endsWith(".png")) {
            JOptionPane.showMessageDialog(this,
                    "Invalid file extension.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE,
                    null);
            return;
        }

        String extension = strPath.substring(strPath.lastIndexOf(".") + 1);

        try {
            ImageIO.write(image, extension, src.toFile());
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this,
                    "Error while exporting the file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE,
                    null);
        }
    }

    /**
     * Implementation of {@link #exit}.
     */
    private void exitImpl() {
        if (model.isModified()) {
            int result = JOptionPane.showOptionDialog(this,
                    "Do you want to save this file?",
                    "Save",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null
            );

            if (result == JOptionPane.YES_OPTION) {
                saveImpl(false);
            }
        }

        dispose();
    }

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JVDraw::new);
    }
}
