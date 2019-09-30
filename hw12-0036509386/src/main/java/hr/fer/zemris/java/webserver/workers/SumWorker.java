package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * A {@link IWebWorker} that adds two
 * numbers passed as parameters.
 *
 * @author Mateo Imbrišak
 */

public class SumWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        int a = initializeValue(1, context.getParameter("a"));
        int b = initializeValue(2, context.getParameter("b"));

        context.setTemporaryParameter("zbroj", String.valueOf(a + b));
        context.setTemporaryParameter("varA", String.valueOf(a));
        context.setTemporaryParameter("varB", String.valueOf(b));
        context.setTemporaryParameter("imgName", (a + b) % 2 == 0 ? "images/image1.png" : "images/image2.png");

        context.getDispatcher().dispatchRequest("private/pages/calc.smscr");
    }

    /**
     * Used internally to initialize the value.
     *
     * @param value to be set if argument is invalid.
     * @param name of the argument.
     *
     * @return value of the requested argument.
     */
    private static int initializeValue(int value, String name) {
        try {
            return name == null ? value : Integer.parseInt(name);
        } catch (NumberFormatException ignored) {}
        return value;
    }
}
