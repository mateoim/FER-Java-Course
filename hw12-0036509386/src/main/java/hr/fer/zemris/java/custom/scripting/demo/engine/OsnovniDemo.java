package hr.fer.zemris.java.custom.scripting.demo.engine;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class used to test the {@link SmartScriptEngine}.
 *
 * @author marcupic
 */

public class OsnovniDemo {

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        String documentBody = Util.readFromDisk("webroot/scripts/osnovni.smscr");
        Map<String,String> parameters = new HashMap<String, String>();
        Map<String,String> persistentParameters = new HashMap<String, String>();
        List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

        // create engine and execute it
        new SmartScriptEngine(
                new SmartScriptParser(documentBody).getDocumentNode(),
                new RequestContext(System.out, parameters, persistentParameters, cookies)
        ).execute();
    }
}
