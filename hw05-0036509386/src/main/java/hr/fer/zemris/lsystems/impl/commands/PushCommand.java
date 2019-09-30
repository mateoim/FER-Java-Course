package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A class that represents a {@code Command} that
 * copies the current state of the turtle and pushes
 * it to the top of the stack.
 *
 * @author Mateo Imbrišak
 */

public class PushCommand implements Command {

    /**
     * Copies the state form the top of the stack and pushes the
     * copy back.
     *
     * @param ctx context containing the state you want to use.
     * @param painter used to paint if needed.
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.pushState(ctx.getCurrentState().copy());
    }
}
