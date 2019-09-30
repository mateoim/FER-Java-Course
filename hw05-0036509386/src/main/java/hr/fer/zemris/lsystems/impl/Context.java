package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class Context {

    /**
     * Keeps the states of the turtle.
     * Current state is on top.
     */
    private ObjectStack<TurtleState> stack;

    /**
     * Default constructor that initializes the stack.
     */
    public Context() {
        stack = new ObjectStack<>();
    }

    /**
     * Returns the current state without removing it.
     *
     * @return current state of the turtle.
     *
     * @throws hr.fer.zemris.java.custom.collections.EmptyStackException
     * if the stack with states is empty.
     */
    public TurtleState getCurrentState() {
        return stack.peek();
    }

    /**
     * Pushes the given state tp the top of the stack.
     *
     * @param state to be pushed to the stack.
     */
    public void pushState(TurtleState state) {
        stack.push(state);
    }

    /**
     * Removes the top state from the stack.
     *
     * @throws hr.fer.zemris.java.custom.collections.EmptyStackException
     * if the stack was empty when called.
     */
    public void popState() {
        stack.pop();
    }
}
