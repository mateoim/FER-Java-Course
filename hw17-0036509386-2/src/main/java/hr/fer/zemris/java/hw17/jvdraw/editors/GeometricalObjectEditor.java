package hr.fer.zemris.java.hw17.jvdraw.editors;

import javax.swing.*;

/**
 * An editor used as a model for
 * all other object editors.
 *
 * @author Mateo Imbrišak
 */

public abstract class GeometricalObjectEditor extends JPanel {

    /**
     * Used to check if the edit is valid.
     */
    public abstract void checkEditing();

    /**
     * Used to apply the edit.
     */
    public abstract void acceptEditing();
}
