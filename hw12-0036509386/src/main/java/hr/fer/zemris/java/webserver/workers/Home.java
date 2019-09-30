package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A {@link IWebWorker} that changes the
 * background color of the page.
 *
 * @author Mateo Imbrišak
 */

public class Home implements IWebWorker {

    /**
     * Keeps the default color.
     */
    private static final String COLOR = "7F7F7F";

    @Override
    public void processRequest(RequestContext context) throws Exception {
        String background = context.getPersistentParameter("bgcolor");
        context.setTemporaryParameter("background", background == null ? COLOR : background);

        context.getDispatcher().dispatchRequest("private/pages/home.smscr");
    }
}
