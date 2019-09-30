package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A class that represents a {@code Command} that
 * changes the effective step of the turtle by multiplying
 * it with the given factor.
 *
 * @author Mateo Imbrišak
 */

public class ScaleCommand implements Command {

    /**
     * Keeps the factor used to change the step.
     */
    private double factor;

    /**
     * Default constructor that assigns the factor.
     *
     * @param factor to be used on the effective step.
     */
    public ScaleCommand(double factor) {
        this.factor = factor;
    }

    /**
     * Changes the effective step of the turtle using the factor.
     *
     * @param ctx context containing the state you want to use.
     * @param painter used to paint if needed.
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.getCurrentState().setEffectiveStep(ctx.getCurrentState().getEffectiveStep() * factor);
    }
}
