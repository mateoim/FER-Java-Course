package hr.fer.zemris.java.hw17.jvdraw.drawing;

/**
 * A listener for {@link DrawingModel}.
 *
 * @author Mateo Imbrišak
 */

public interface DrawingModelListener {

    /**
     * Signals that objects have been added.
     *
     * @param source where the objects have been added.
     * @param index0 starting index.
     * @param index1 final index.
     */
    void objectsAdded(DrawingModel source, int index0, int index1);

    /**
     * Signals that objects have been removed.
     *
     * @param source where the objects have been removed.
     * @param index0 starting index.
     * @param index1 final index.
     */
    void objectsRemoved(DrawingModel source, int index0, int index1);

    /**
     * Signals that objects have been changed.
     *
     * @param source where the objects have been changed.
     * @param index0 starting index.
     * @param index1 final index.
     */
    void objectsChanged(DrawingModel source, int index0, int index1);
}
