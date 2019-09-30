package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class used to test the {@code SmartScriptParser}.
 *
 * @author Mateo Imbrišak
 */

public class SmartScriptTester {

    /**
     * Used to start the program.
     *
     * @param args a single line representing the path to
     *             the file you want to parse.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments.");
            return;
        }

        try {
            String docBody = new String(
                    Files.readAllBytes(Paths.get(args[0])),
                    StandardCharsets.UTF_8
            );

            SmartScriptParser parser = null;
            try {
                parser = new SmartScriptParser(docBody);
            } catch (SmartScriptParserException e) {
                System.out.println("Unable to parse document!");
                e.printStackTrace();
                System.exit(-1);
            } catch (Exception e) {
                System.out.println("If this line ever executes, you have failed this class!");
                System.exit(-1);
            }
            DocumentNode document = parser.getDocumentNode();
            String originalDocumentBody = createOriginalDocumentBody(document);
            System.out.println(originalDocumentBody); // should write something like original
            // content of docBody
        } catch (IOException ignored) {
            System.out.println("Cannot open file.");
            System.exit(1);
        }
    }

    /**
     * Used to structurally replicate the source code
     * of SmartScript language based on the given document
     * structure.
     *
     * @param node a {@code DocumentNode} with document
     *             structure you want to replicate.
     *
     * @return reconstructed source code.
     */
    public static String createOriginalDocumentBody(Node node) {
        StringBuilder ret = new StringBuilder();

        for (int i = 0, number = node.numberOfChildren(); i < number; i++) {
            Node child = node.getChild(i);
            ret.append(child.toString());
        }

        return ret.toString();
    }
}
