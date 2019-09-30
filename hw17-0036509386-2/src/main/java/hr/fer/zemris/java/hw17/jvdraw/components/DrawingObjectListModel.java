package hr.fer.zemris.java.hw17.jvdraw.components;

import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

import javax.swing.*;

/**
 * A {@link ListModel} used for {@link GeometricalObject}s.
 *
 * @author Mateo Imbrišak
 */

public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

    private static final long serialVersionUID = 4110391296778837555L;

    /**
     * Keeps the model used for reference.
     */
    private DrawingModel model;

    /**
     * Default constructor that assigns the model.
     *
     * @param model to be assigned.
     */
    public DrawingObjectListModel(DrawingModel model) {
        this.model = model;

        model.addDrawingModelListener(this);
    }

    @Override
    public int getSize() {
        return model.getSize();
    }

    @Override
    public GeometricalObject getElementAt(int index) {
        return model.getObject(index);
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        fireIntervalAdded(source, index0, index1);
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        fireIntervalRemoved(source, index0, index1);
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        fireContentsChanged(source, index0, index1);
    }
}
