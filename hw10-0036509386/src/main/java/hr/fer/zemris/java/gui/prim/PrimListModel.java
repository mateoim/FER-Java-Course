package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A {@code ListModel} that generates
 * prime numbers.
 *
 * @author Mateo Imbrišak
 */

public class PrimListModel implements ListModel<Integer> {

    /**
     * Keeps the elements generated so far.
     */
    private List<Integer> elements;

    /**
     * Keeps the listeners added to the model.
     */
    private List<ListDataListener> listeners;

    public PrimListModel() {
        elements = new ArrayList<>();
        elements.add(1);

        listeners = new LinkedList<>();
    }

    @Override
    public int getSize() {
        return elements.size();
    }

    @Override
    public Integer getElementAt(int index) {
        return elements.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    /**
     * Generates the next prime number,
     * adds it to {@link #elements} and
     * notifies all {@link #listeners}.
     */
    public void next() {
        int current = elements.get(elements.size() - 1);
        current++;

        while (true) {
            int root = (int) Math.floor(Math.sqrt(current));
            boolean isPrime = true;

            for (int i = 2; i <= root; i++) {
                if (current % i == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                elements.add(current);

                listeners.forEach((listener) -> listener.intervalAdded(new
                        ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, elements.size(), elements.size())));
                return;
            } else {
                current++;
            }
        }
    }
}
