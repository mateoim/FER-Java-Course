package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * A class that represents a {@code Command} that
 * draws on the given {@code Picture}
 *
 * @author Mateo Imbrišak
 */

public class DrawCommand implements Command {

    /**
     * Keeps the factor used to multiply the step.
     */
    private double step;

    /**
     * Default constructor that assigns step value.
     *
     * @param step value to be used in command.
     */
    public DrawCommand(double step) {
        this.step = step;
    }

    /**
     * Draws on the picture using the given {@code Painter}.
     *
     * @param ctx context containing the state you want to use.
     * @param painter used to paint if needed.
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState state = ctx.getCurrentState();
        Vector2D endDirection = state.getDirection().scaled(step * state.getEffectiveStep());
        Vector2D startPosition = state.getPosition().copy();

        Vector2D endPosition = state.getPosition().translated(endDirection);

        painter.drawLine(startPosition.getX(), startPosition.getY(),
                endPosition.getX(), endPosition.getY(),
                state.getCurrentColor(), 1f);

        state.setPosition(endPosition);
    }
}
