package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A class that represents a {@code Command} that
 * removes the current state from the top of the stack.
 *
 * @author Mateo Imbrišak
 */

public class PopCommand implements Command {

    /**
     * Removes the state from the stack.
     *
     * @param ctx context containing the state you want to use.
     * @param painter used to paint if needed.
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.popState();
    }
}
