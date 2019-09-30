package searching.algorithms;

/**
 * A class that represents a node of search tree.
 *
 * @param <S> type of the state object.
 *
 * @author Mateo Imbrišak
 */

public class Node<S> {

    /**
     * Keeps the reference to the parent
     * of this {@code Node}.
     */
    private Node<S> parent;

    /**
     * Keeps the state of this {@code Node}.
     */
    private S state;

    /**
     * Keeps the cost necessary to get to
     * this point.
     */
    private double cost;

    /**
     * Default constructor that assigns all values.
     *
     * @param parent of this {@code Node}.
     * @param state of this {@code Node}.
     * @param cost to get to this point.
     */
    public Node(Node<S> parent, S state, double cost) {
        this.parent = parent;
        this.state = state;
        this.cost = cost;
    }

    /**
     * Provides the current state of this {@code Node}.
     *
     * @return state of this node.
     */
    public S getState() {
        return state;
    }

    /**
     * Provides the cost of this {@code Node}.
     *
     * @return cost of this node.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Provides the parent of this {@code Node}.
     *
     * @return parent of this node.
     */
    public Node<S> getParent() {
        return parent;
    }
}
