package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import java.awt.*;

/**
 * A program that has two scrollable panes
 * that show prime numbers.
 *
 * @author Mateo Imbrišak
 */

public class PrimDemo extends JFrame {
    private static final long serialVersionUID = 1L;

    /**
     * Keeps the model.
     */
    private PrimListModel model;

    /**
     * Default constructor that initializes the
     * {@link #model} and GUI.
     */
    public PrimDemo() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        model = new PrimListModel();

        initGUI();
        pack();
        setVisible(true);
    }

    /**
     * Used internally to initialize the GUI.
     */
    private void initGUI() {
        JButton next = new JButton("next");

        next.addActionListener((e) -> {
            model.next();
            pack();
        });

        add(next, BorderLayout.SOUTH);

        JPanel main = new JPanel();

        main.add(new JScrollPane(new JList<>(model)), BorderLayout.EAST);
        main.add(new JScrollPane(new JList<>(model)), BorderLayout.WEST);

        add(main, BorderLayout.CENTER);
    }

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PrimDemo::new);
    }
}
