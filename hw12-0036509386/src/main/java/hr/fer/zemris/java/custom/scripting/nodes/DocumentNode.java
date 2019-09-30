package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A class that represents the entire document
 * by adding all contents as child {@code Node}s.
 *
 * @author Mateo Imbrišak
 */

public class DocumentNode extends Node {

    @Override
    public void accept(INodeVisitor visitor) {
        for (int i = 0; i < numberOfChildren(); i++) {
            Node current = getChild(i);
            current.accept(visitor);
        }
    }
}
