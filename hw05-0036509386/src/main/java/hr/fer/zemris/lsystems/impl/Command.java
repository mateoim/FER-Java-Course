package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * An interface used to model all {@code Command}s
 * for the turtle will have to execute.
 *
 * @author Mateo Imbrišak
 */

public interface Command {

    /**
     * Performs a command on the given {@code Context} using the
     * given {@code Painter} if necessary.
     *
     * @param ctx context containing the state you want to use.
     * @param painter used to paint if needed.
     */
    void execute(Context ctx, Painter painter);
}
