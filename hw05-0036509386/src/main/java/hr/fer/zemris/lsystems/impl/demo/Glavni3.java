package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * A class that runs a GUI where you can
 * select a file from which to load a {@code LSystem}.
 *
 * @author marcupic
 */

public class Glavni3 {

    /**
     * Used to run the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        LSystemViewer.showLSystem(LSystemBuilderImpl::new);
    }
}
