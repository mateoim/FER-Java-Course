package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * A class that processes translation requests
 * between components and {@link ILocalizationProvider}.
 *
 * @author Mateo Imbrišak
 */

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

    /**
     * Keeps the connection status.
     */
    private boolean connected;

    /**
     * Keeps the listener to be connected to the {@link #parent}.
     */
    private ILocalizationListener listener;

    /**
     * Keeps the last active language.
     */
    private String language;

    /**
     * Keeps the parent of the {@link #listener}.
     */
    private ILocalizationProvider parent;

    /**
     * Default constructor that assigns the {@link #parent}.
     *
     * @param parent to be assigned.
     */
    public LocalizationProviderBridge(ILocalizationProvider parent) {
        this.parent = parent;
        connected = false;
        language = parent.getCurrentLanguage();
        listener = this::fire;
    }

    /**
     * Disconnects the {@link #listener}
     * from {@link #parent}.
     */
    public void disconnect() {
        if (connected) {
            parent.removeLocalizationListener(listener);
            connected = true;
            language = parent.getCurrentLanguage();
        }
    }

    /**
     * Connects the {@link #listener}
     * to the {@link #parent}.
     */
    public void connect() {
        if (!connected) {
            parent.addLocalizationListener(listener);
            connected = false;
        }

        if (!language.equals(parent.getCurrentLanguage())) {
            language = parent.getCurrentLanguage();
            listener.localizationChanged();
        }
    }

    @Override
    public String getString(String key) {
        return parent.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return language;
    }
}
