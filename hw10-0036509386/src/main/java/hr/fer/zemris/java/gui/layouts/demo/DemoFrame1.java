package hr.fer.zemris.java.gui.layouts.demo;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;

/**
 * A class used to demonstrate {@code CalcLayout}.
 *
 * @author marcupic
 */

public class DemoFrame1 extends JFrame{
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor that initializes the GUI.
     */
    public DemoFrame1() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        setSize(500, 500);
        initGUI();
        pack();
    }

    /**
     * Initializes the GUI.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(3));
        cp.add(l("tekst 1"), new RCPosition(1,1));
        cp.add(l("tekst 2"), new RCPosition(2,3));
        cp.add(l("tekst stvarno najdulji"), new RCPosition(2,7));
        cp.add(l("tekst kraći"), new RCPosition(4,2));
        cp.add(l("tekst srednji"), new RCPosition(4,5));
        cp.add(l("tekst"), new RCPosition(4,7));
    }

    /**
     * Creates a yellow label with
     * the given text.
     *
     * @param text to be assigned.
     *
     * @return a new {@code JLabel}.
     */
    private JLabel l(String text) {
        JLabel l = new JLabel(text);
        l.setBackground(Color.YELLOW);
        l.setOpaque(true);
        return l;
    }

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new DemoFrame1().setVisible(true);
        });
    }
}
