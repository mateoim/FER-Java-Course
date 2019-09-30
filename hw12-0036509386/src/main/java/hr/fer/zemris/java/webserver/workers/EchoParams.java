package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;

/**
 * A {@link IWebWorker} that prints
 * all parameters in a table.
 *
 * @author Mateo Imbrišak
 */

public class EchoParams implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        context.setMimeType("text/html");

        context.write("<html><body>");

        context.write("<style>\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "}\n" +
                "</style>");

        context.write("<table style=\"width:50%\">\r\n");
        context.write("<tr><th>Name</th><th>Value</th></tr>");

        context.getParameterNames().forEach((name) -> {
            String value = context.getParameter(name);
            try {
                context.write("<tr><td>" + name + "</td><td>" + value + "</td></tr>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        context.write("</table></body></html>");
    }
}
