package hr.fer.zemris.java.hw17.jvdraw.objects;

import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * A class used to model all other objects.
 *
 * @author Mateo Imbrišak
 */

public abstract class GeometricalObject {

    /**
     * allows the {@link GeometricalObjectVisitor} to process this object.
     *
     * @param v visitor used to process this object.
     */
    public abstract void accept(GeometricalObjectVisitor v);

    /**
     * Registers the given listener.
     *
     * @param l listener to be registered.
     */
    public abstract void addGeometricalObjectListener(GeometricalObjectListener l);

    /**
     * Removes the given listener.
     *
     * @param l listener to be removed.
     */
    public abstract void removeGeometricalObjectListener(GeometricalObjectListener l);

    /**
     * Creates the appropriate {@link GeometricalObjectEditor}.
     *
     * @return appropriate {@link GeometricalObjectEditor}.
     */
    public abstract GeometricalObjectEditor createGeometricalObjectEditor();
}
