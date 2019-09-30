package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;


/**
 * A visitor for {@link hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject}s.
 *
 * @author Mateo Imbrišak
 */

public interface GeometricalObjectVisitor {

    /**
     * Processes the given {@link Line}.
     *
     * @param line to be processed.
     */
    void visit(Line line);

    /**
     * Processes the given {@link Circle}.
     *
     * @param circle to be processed.
     */
    void visit(Circle circle);

    /**
     * Processes the given {@link FilledCircle}.
     *
     * @param filledCircle to be processed.
     */
    void visit(FilledCircle filledCircle);
}
