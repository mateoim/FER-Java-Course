package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A {@link IWebWorker} that changes
 * the background color.
 *
 * @author Mateo Imbrišak
 */

public class BgColorWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        String color = context.getParameter("bgcolor");

        if (color.length() == 6 && color.matches("[0-9a-fA-F]+")) {
            context.setPersistentParameter("bgcolor", color);
            context.getDispatcher().dispatchRequest("private/pages/success.smscr");
        } else {
            context.getDispatcher().dispatchRequest("private/pages/failed.smscr");
        }
    }
}
