package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Basic class used to represent all {@code Node}s in this
 * parser.
 *
 * @author Mateo Imbrišak
 */

public class Node {

    /**
     * Used for storage of children of this {@code Node}.
     */
    private ArrayIndexedCollection children = null;

    /**
     * Adds the given {@code Node} as a child of this one.
     * if there were no children previously initializes
     * internal storage and adds the given child to it.
     *
     * @param child {@code Node} to be added.
     *
     * @throws NullPointerException if the given child is null.
     */
    public void addChildNode(Node child) {
        if (child == null) {
            throw new NullPointerException("Child cannot be null.");
        }

        if (children == null) {
            children = new ArrayIndexedCollection();
        }

        children.add(child);
    }

    /**
     * Returns the number of direct children of this {@code Node}.
     *
     * @return current number of children.
     */
    public int numberOfChildren() {
        if (children == null) {
            return 0;
        }

        return children.size();
    }

    /**
     * Returns the child at the given index if it is valid.
     *
     * @param index at which we want to get the child.
     *
     * @return {@code Node} at a given index.
     *
     * @throws IndexOutOfBoundsException if the given index is a negative
     *         number or greater than current size - 1.
     */
    public Node getChild(int index) {
        return (Node) children.get(index);
    }

    /**
     * Used to process this node and all of it's children.
     *
     * @param visitor being used.
     */
    public void accept(INodeVisitor visitor) {
        if (children != null) {
            for (int i = 0; i < numberOfChildren(); i++) {
                Node current = (Node) children.get(i);
                current.accept(visitor);
            }
        }
    }
}
