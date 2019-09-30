package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A class that registers a {@link JFrame}
 * to a {@link LocalizationProviderBridge}.
 *
 * @author Mateo Imbrišak
 */

public class FormLocalizationProvider extends LocalizationProviderBridge {

    /**
     * Default constructor that assigns parent
     * and frame.
     *
     * @param parent to be assigned.
     * @param frame to be assigned.
     */
    public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
        super(parent);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                connect();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosing(e);
                disconnect();
            }
        });
    }
}
