package searching.algorithms;

/**
 * A class that represents a pair of
 * state and cost to get to that state.
 *
 * @param <S> type of state.
 *
 * @author Mateo Imbrišak
 */

public class Transition<S> {

    /**
     * Keeps the state of this pair.
     */
    private S state;

    /**
     * Keeps the cost of this pair.
     */
    private double cost;

    /**
     * Default constructor that assigns all values.
     *
     * @param state of this pair.
     * @param cost  of this pair.
     */
    public Transition(S state, double cost) {
        this.state = state;
        this.cost = cost;
    }

    /**
     * Provides the state of this pair.
     *
     * @return state of this pair.
     */
    public S getState() {
        return state;
    }

    /**
     * Provides the cost of this pair.
     *
     * @return cost of this pair.
     */
    public double getCost() {
        return cost;
    }
}
