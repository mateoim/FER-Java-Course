package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * An interface that represents a
 * localization provider.
 *
 * @author Mateo Imbrišak
 */

public interface ILocalizationProvider {

    /**
     * Adds the given {@code ILocalizationListener}.
     *
     * @param listener to be added.
     */
    void addLocalizationListener(ILocalizationListener listener);

    /**
     * Removes the given {@code ILocalizationListener}.
     *
     * @param listener to be removed.
     */
    void removeLocalizationListener(ILocalizationListener listener);

    /**
     * Used to get the translation.
     *
     * @param key of the translation.
     *
     * @return translated input.
     */
    String getString(String key);

    /**
     * Provides the currently active language.
     *
     * @return currently active language.
     */
    String getCurrentLanguage();
}
