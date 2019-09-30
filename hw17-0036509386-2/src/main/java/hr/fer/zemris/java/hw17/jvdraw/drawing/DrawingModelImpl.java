package hr.fer.zemris.java.hw17.jvdraw.drawing;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An implementation of {@link DrawingModel}.
 *
 * @author Mateo Imbrišak
 */

public class DrawingModelImpl implements DrawingModel {

    /**
     * Keeps all {@link GeometricalObject}s.
     */
    private List<GeometricalObject> objects;

    /**
     * Keeps all active {@link DrawingModelListener}s.
     */
    private Set<DrawingModelListener> listeners;

    /**
     * Keeps track of whether any object has been modified.
     */
    private boolean modified = false;

    /**
     * Default constructor that initializes all collections.
     */
    public DrawingModelImpl() {
        objects = new ArrayList<>();
        listeners = new HashSet<>();
    }

    @Override
    public int getSize() {
        return objects.size();
    }

    @Override
    public GeometricalObject getObject(int index) {
        return objects.get(index);
    }

    @Override
    public void add(GeometricalObject object) {
        objects.add(object);
        modified = true;
        object.addGeometricalObjectListener(this);

        listeners.forEach((current) -> current.objectsAdded(this, indexOf(object), indexOf(object)));
    }

    @Override
    public void remove(GeometricalObject object) {
        int index = indexOf(object);

        if (objects.remove(object)) {
            modified = true;
            object.removeGeometricalObjectListener(this);

            listeners.forEach((current) -> current.objectsRemoved(this, index, index));
        }
    }

    @Override
    public void changeOrder(GeometricalObject object, int offset) {
        int initialIndex = indexOf(object);

        if (initialIndex == -1) {
            return;
        }

        objects.remove(object);

        initialIndex += offset;
        int index = (initialIndex < 0) ? 0 : (initialIndex >= objects.size()) ? objects.size() : initialIndex;

        objects.add(index, object);

        listeners.forEach((current) -> current.objectsChanged(this, index, index));
    }

    @Override
    public int indexOf(GeometricalObject object) {
        return objects.indexOf(object);
    }

    @Override
    public void clear() {
        if (objects.isEmpty()) {
            return;
        }

        int size = getSize();
        objects.forEach((current) -> current.removeGeometricalObjectListener(this));

        objects.clear();
        modified = true;

        listeners.forEach((current) -> current.objectsRemoved(this, 0, size - 1));
    }

    @Override
    public void clearModifiedFlag() {
        modified = false;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void addDrawingModelListener(DrawingModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener l) {
        listeners.remove(l);
    }

    @Override
    public void geometricalObjectChanged(GeometricalObject o) {
        listeners.forEach((current) -> current.objectsChanged(this, indexOf(o), indexOf(o)));
    }
}
