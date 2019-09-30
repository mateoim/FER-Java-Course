package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * A class that represents a {@code Command} that
 * moves the turtle without drawing.
 *
 * @author Mateo Imbrišak
 */

public class SkipCommand implements Command {

    /**
     * Keeps the step used to move the turtle.
     */
    private double step;

    /**
     * Default constructor that assigns the step.
     *
     * @param step to be used when moving the turtle.
     */
    public SkipCommand(double step) {
        this.step = step;
    }

    /**
     * Moves the turtle without drawing.
     *
     * @param ctx context containing the state you want to use.
     * @param painter used to paint if needed.
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState state = ctx.getCurrentState();
        Vector2D endDirection = state.getDirection().scaled(step * state.getEffectiveStep());

        state.getPosition().translate(endDirection);
    }
}
