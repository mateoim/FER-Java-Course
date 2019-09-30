package hr.fer.zemris.java.custom.scripting.demo.engine;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class used to test the {@link SmartScriptEngine}.
 *
 * @author marcupic
 */

public class FibonacciHTMLDemo {

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        String documentBody = Util.readFromDisk("webroot/scripts/fibonaccih.smscr");
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> persistentParameters = new HashMap<String, String>();
        List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

        Path out = Paths.get("fibonacci.html");

        // create engine and execute it
        try (OutputStream os = new BufferedOutputStream(Files.newOutputStream(out))) {
            new SmartScriptEngine(
                    new SmartScriptParser(documentBody).getDocumentNode(),
                    new RequestContext(os, parameters, persistentParameters, cookies)
            ).execute();
        } catch (IOException exc) {
            System.out.println("Error while writing.");
        }
    }
}
