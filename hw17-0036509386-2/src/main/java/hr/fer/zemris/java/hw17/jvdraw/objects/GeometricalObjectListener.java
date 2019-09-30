package hr.fer.zemris.java.hw17.jvdraw.objects;

/**
 * An interface that models listeners for {@link GeometricalObject}s.
 *
 * @author Mateo Imbrišak
 */

public interface GeometricalObjectListener {

    /**
     * Used to signal that a change has occurred.
     *
     * @param o that has been changed.
     */
    void geometricalObjectChanged(GeometricalObject o);
}
