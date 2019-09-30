package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A singleton class used for translation.
 *
 * @author Mateo Imbrišak
 */

public class LocalizationProvider extends AbstractLocalizationProvider {

    /**
     * Keeps the only instance.
     */
    private static LocalizationProvider instance = new LocalizationProvider();

    /**
     * Keeps the currently used language.
     */
    private String language;

    /**
     * Keeps the resource bundle.
     */
    private ResourceBundle bundle;

    /**
     * Private constructor so no more than one
     * instance can be created.
     */
    private LocalizationProvider() {
        setLanguage("en");
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return language;
    }

    /**
     * Provides the instance of this object.
     *
     * @return instance of this object.
     */
    public static LocalizationProvider getInstance() {
        return instance;
    }

    /**
     * Used to change the language.
     *
     * @param language to be set.
     */
    public void setLanguage(String language) {
        this.language = language;

        Locale current = Locale.forLanguageTag(this.language);
        bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.translation.translation", current);

        fire();
    }
}
