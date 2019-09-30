package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * An interface used to define
 * observers for localisation.
 *
 * @author Mateo Imbrišak
 */

public interface ILocalizationListener {

    /**
     * Used to inform that the localization has been changed.
     */
    void localizationChanged();
}
