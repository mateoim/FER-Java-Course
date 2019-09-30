package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * A class that represents a {@code Command} that
 * rotates the current position of the turtle.
 *
 * @author Mateo Imbrišak
 */

public class RotateCommand implements Command {

    /**
     * Used to rotate the direction of the turtle in
     * the given {@code Context}.
     */
    private double angle;

    /**
     * Default constructor that assigns an angle.
     *
     * @param angle used to rotate the turtle.
     */
    public RotateCommand(double angle) {
        this.angle = angle;
    }

    /**
     * Rotates the direction of the turtle.
     *
     * @param ctx context containing the state you want to use.
     * @param painter used to paint if needed.
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState currentState = ctx.getCurrentState();
        Vector2D direction = currentState.getDirection();
        direction.rotate(angle * Math.PI / 180);
        currentState.setDirection(direction);
    }
}
