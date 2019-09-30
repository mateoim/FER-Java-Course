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

public class Glavni1 {

    /**
     * Used to run the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
    }

    /**
     * Used internally to configure a {@code LSystem}
     * using commands.
     *
     * @param provider that creates the builder.
     *
     * @return an instance of {@code LSystem}.
     */
    private static LSystem createKochCurve(LSystemBuilderProvider provider) {
        return provider.createLSystemBuilder()
                .registerCommand('F', "draw 1")
                .registerCommand('+', "rotate 60")
                .registerCommand('-', "rotate -60")
                .setOrigin(0.05, 0.4)
                .setAngle(0)
                .setUnitLength(0.9)
                .setUnitLengthDegreeScaler(1.0 / 3.0)
                .registerProduction('F', "F+F--F+F")
                .setAxiom("F")
                .build();
    }
}
