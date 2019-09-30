package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class that demonstrates {@link INodeVisitor}
 * and the visitor design pattern.
 *
 * @author Mateo Imbrišak
 */

public class TreeWriter {

    /**
     * Used to start the program.
     *
     * @param args a single argument,
     *             path to a file to be
     *             parsed and written to
     *             std out.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("You must provide a path to a file");
            return;
        }

        Path src = Paths.get(args[0]);
        String docBody;

        try {
            docBody = Files.readString(src);
        } catch (IOException exc) {
            System.out.println("An error occurred while reading the file");
            return;
        }

        SmartScriptParser parser = new SmartScriptParser(docBody);
        WriterVisitor visitor = new WriterVisitor();

        parser.getDocumentNode().accept(visitor);
    }

    /**
     * An {@link INodeVisitor} used to reproduce a SmartScript
     * document.
     */
    private static class WriterVisitor implements INodeVisitor {

        @Override
        public void visitTextNode(TextNode node) {
            System.out.print(node.toString());
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            System.out.print(node.toString());
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            System.out.print(node.toString());
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            System.out.print(node.toString());
        }
    }
}
