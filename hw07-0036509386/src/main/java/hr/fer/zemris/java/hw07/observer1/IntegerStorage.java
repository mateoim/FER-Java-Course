package hr.fer.zemris.java.hw07.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a simple Subject.
 *
 * @author Mateo Imbrišak
 */

public class IntegerStorage {

    /**
     * Keeps track of the current value.
     */
    private int value;

    /**
     * Keeps all currently active observers.
     */
    private List<IntegerStorageObserver> observers;

    /**
     * Default constructor that assigns the value and
     * initializes the observers list.
     *
     * @param initialValue to be assigned.
     */
    public IntegerStorage(int initialValue) {
        this.value = initialValue;
        this.observers = new ArrayList<>();
    }

    /**
     * Adds the observer to the list.
     *
     * @param observer to be added.
     */
    public void addObserver(IntegerStorageObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes the given observer if it exists in the list.
     *
     * @param observer to be removed.
     */
    public void removeObserver(IntegerStorageObserver observer) {
        observers.remove(observer);
    }

    /**
     * Removes all observers from the list.
     */
    public void clearObservers() {
        observers.clear();
    }

    /**
     * Provides the current value of this {@code IntegerStorage}.
     *
     * @return current value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value to the provide one and informs all observers.
     *
     * @param value to be set.
     */
    public void setValue(int value) {
        if (this.value!=value) {
            this.value = value;
            if (observers!=null) {

//                CopyOnWriteArrayList<IntegerStorageObserver> observersCopy = new CopyOnWriteArrayList<>(observers);
//                for(IntegerStorageObserver observer : observersCopy) {
//                    observer.valueChanged(this);
//                }

                for (int i = 0, backupSize = observers.size(); i < backupSize; i++) {
                    observers.get(i).valueChanged(this);

                    if (backupSize > observers.size()) {

                        i -= (backupSize - observers.size());
                        backupSize = observers.size();
                    }
                }
            }
        }
    }
}
