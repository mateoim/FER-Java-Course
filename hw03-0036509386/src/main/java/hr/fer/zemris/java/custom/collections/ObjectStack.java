package hr.fer.zemris.java.custom.collections;

/**
 * A stack that is backed by the {@code ArrayIndexedCollection}.
 *
 * @author Mateo Imbrišak
 */

public class ObjectStack {

    /**
     * The {@code ArrayIndexCollection} used for actual storage.
     */
    private ArrayIndexedCollection memory;

    /**
     * Default constructor that creates a new instance of {@code ArrayIndexCollection}
     * for storage in the new {@code ObjectStack}.
     */
    public ObjectStack() {
        this.memory = new ArrayIndexedCollection();
    }

    /**
     * Checks if the {@code ObjectStack} is empty.
     *
     * @return {@code true} if the {@code ObjectStack} is empty,
     * otherwise {@code false}.
     */
    public boolean isEmpty() {
        return memory.isEmpty();
    }

    /**
     * Checks the current size of the {@code ObjectStack}.
     *
     * @return current size of the {@code ObjectStack}.
     */
    public int size() {
        return memory.size();
    }

    /**
     * Pushes the element to the top of the {@code ObjectStack}.
     *
     * @param value the {@code Object} being pushed.
     *
     * @throws NullPointerException if the given parameter is {@code null}.
     */
    public void push(Object value) {
        memory.add(value);
    }

    /**
     * Removes an element from the top of the {@code ObjectStack}
     * and returns it to the user.
     *
     * @return {@code Object} being removed.
     *
     * @throws EmptyStackException if the {@code ObjectStack} is empty when the method is called.
     */
    public Object pop() {
        Object result = peek();
        memory.remove(size() - 1);

        return result;
    }

    /**
     * Returns the element from the top of the {@code ObjectStack},
     * but does not remove it.
     *
     * @return the {@code Object} at the top of the {@code ObjectStack}.
     *
     * @throws EmptyStackException if the {@code ObjectStack} is empty when the method is called.
     */
    public Object peek() {
        if (size() == 0) {
            throw new EmptyStackException("Cannot pop an element from an empty stack.");
        }

        return memory.get(size() - 1);
    }

    /**
     * Deletes all elements form the {@code ObjectStack}.
     */
    public void clear() {
        memory.clear();
    }
}
