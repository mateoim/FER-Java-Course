package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.LinkedList;
import java.util.List;

/**
 * An abstract implementation of {@link ILocalizationProvider}.
 *
 * @author Mateo Imbrišak
 */

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

    /**
     * Keeps the active listeners.
     */
    private List<ILocalizationListener> listeners = new LinkedList<>();

    @Override
    public void addLocalizationListener(ILocalizationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener listener) {
        listeners.remove(listener);
    }

    /**
     * Used to notify all listeners that the
     * language has been changed.
     */
    public void fire() {
        listeners.forEach(ILocalizationListener::localizationChanged);
    }
}
