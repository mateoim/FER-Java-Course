package hr.fer.zemris.java.hw17.jvdraw.drawing;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectListener;

/**
 * An interface that represents a drawing model.
 *
 * @author Mateo Imbrišak
 */

public interface DrawingModel extends GeometricalObjectListener {

    /**
     * Provides the model's current size.
     *
     * @return model's current size.
     */
    int getSize();

    /**
     * Provides the {@link GeometricalObject} at the requested index.
     *
     * @param index of the requested {@link GeometricalObject}.
     *
     * @return {@link GeometricalObject} at the requested index.
     */
    GeometricalObject getObject(int index);

    /**
     * Adds the given object to the model.
     *
     * @param object to be added.
     */
    void add(GeometricalObject object);

    /**
     * Removes the given object from the model.
     *
     * @param object to be removed
     */
    void remove(GeometricalObject object);

    /**
     * Moves the given object to a new index {@code currentIndex + offset}.
     *
     * @param object to be moved.
     * @param offset by which it should be moved.
     */
    void changeOrder(GeometricalObject object, int offset);

    /**
     * Provides the index of the given object.
     *
     * @param object whose index is being looked up.
     *
     * @return requested object's index.
     */
    int indexOf(GeometricalObject object);

    /**
     * Removes all objects from the model.
     */
    void clear();

    /**
     * Clears the modified status.
     */
    void clearModifiedFlag();

    /**
     * Checks if the model has been modified.
     *
     * @return {@code true} if the model has been modified,
     * otherwise {@code false}.
     */
    boolean isModified();

    /**
     * Registers the given listener.
     *
     * @param l listener to be added.
     */
    void addDrawingModelListener(DrawingModelListener l);

    /**
     * Removes the given listener.
     *
     * @param l listener to be removed.
     */
    void removeDrawingModelListener(DrawingModelListener l);
}
