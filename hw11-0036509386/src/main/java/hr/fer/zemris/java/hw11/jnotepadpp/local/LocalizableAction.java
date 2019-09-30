package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;

/**
 * An {@link AbstractAction} that can be localized.
 *
 * @author Mateo Imbrišak
 */

public abstract class LocalizableAction extends AbstractAction {
    private static final long serialVersionUID = 1L;

    /**
     * Keeps the key of the translation.
     */
    private String key;

    /**
     * Default constructor that assigns a key
     * and adds a listener to the provider.
     *
     * @param key to be assigned.
     * @param provider to get the listener.
     */
    public LocalizableAction(String key, ILocalizationProvider provider) {
        this.key = key;

        provider.addLocalizationListener(() -> {
            putValue(Action.NAME, provider.getString(key));
            putValue(Action.SHORT_DESCRIPTION, provider.getString(key + "_description"));
        });
    }
}
