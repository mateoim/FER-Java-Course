package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

import java.awt.*;

/**
 * A class that represents a {@code Command} that
 * changes the current {@code Color}.
 *
 * @author Mateo Imbrišak
 */

public class ColorCommand implements Command {

    /**
     * Keeps the color to be assigned.
     */
    private Color color;

    /**
     * Default constructor that assigns a color.
     *
     * @param color to be used in the state.
     */
    public ColorCommand(Color color) {
        this.color = color;
    }

    /**
     * Changes the {@code Color} of this {@code TurtleState}.
     *
     * @param ctx context containing the state you want to use.
     * @param painter used to paint if needed.
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.getCurrentState().setCurrentColor(color);
    }
}
