package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;

/**
 * A {@link JMenu} that can be localized.
 *
 * @author Mateo Imbrišak
 */

public class LJMenu extends JMenu {
    private static final long serialVersionUID = 1L;

    /**
     * Keeps the key for translation.
     */
    private String key;

    /**
     * Default constructor that assigns a key
     * and adds a listener to the provider.
     *
     * @param key to be assigned.
     * @param provider to get the listener.
     */
    public LJMenu(String key, ILocalizationProvider provider) {
        this.key = key;
        setText(provider.getString(key));

        provider.addLocalizationListener(() -> setText(provider.getString(key)));
    }
}
