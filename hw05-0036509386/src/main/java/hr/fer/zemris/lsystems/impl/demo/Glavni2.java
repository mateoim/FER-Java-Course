package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * A class that runs a GUI that shows
 * the configured {@code LSystem}.
 *
 * @author marcupic
 */

public class Glavni2 {

    /**
     * Used to run the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
    }

    /**
     * Used internally to configure a {@code LSystem}
     * using a {@code String} array.
     *
     * @param provider that creates the builder.
     *
     * @return an instance of {@code LSystem}.
     */
    private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
        String[] data = new String[]{
            "origin                 0.05 0.4",
            "angle                  0",
            "unitLength             0.9",
            "unitLengthDegreeScaler 1.0 / 3.0",
            "",
            "command F draw 1",
            "command + rotate 60",
            "command - rotate -60",
            "",
            "axiom F",
            "",
            "production F F+F--F+F"
        };
        return provider.createLSystemBuilder().configureFromText(data).build();
    }

}
