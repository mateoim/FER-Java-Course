package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A class that acts as a stack with multiple
 * entries under unique names.
 *
 * @author Mateo Imbrišak
 */

public class ObjectMultistack {

    /**
     * Keeps the pairs of stack name and stack storage.
     */
    private Map<String, MultistackEntry> map;

    /**
     * Default constructor that initializes the {@code Map}.
     */
    public ObjectMultistack() {
        map = new HashMap<>();
    }

    /**
     * Pushes the given value to the top of the given stack.
     *
     * @param keyName name of the stack.
     * @param valueWrapper a {@code ValueWrapper} containing the value.
     *
     * @throws NullPointerException if any of the arguments is {@code null}.
     */
    public void push(String keyName, ValueWrapper valueWrapper) {
        Objects.requireNonNull(keyName);
        Objects.requireNonNull(valueWrapper);

        MultistackEntry currentHead = map.get(keyName);
        MultistackEntry toAdd = new MultistackEntry(valueWrapper);

        if (currentHead != null) {
            toAdd.next = currentHead;
        }

        map.put(keyName, toAdd);
    }

    /**
     * Removes an element form the top of the given stack
     * and returns it.
     *
     * @param keyName name of the stack.
     *
     * @return element that has just been removed.
     *
     * @throws RuntimeException if the stack is empty.
     * @throws NullPointerException if the given key
     * is {@code null}.
     */
    public ValueWrapper pop(String keyName) {
        MultistackEntry current = getCurrent(keyName);
        map.put(keyName, current.next);

        return current.value;
    }

    /**
     * Returns the element from the top of the
     * stack without removing it.
     *
     * @param keyName name of the stack.
     *
     * @return element on the top of
     * the given stack.
     *
     * @throws RuntimeException if the stack is empty.
     * @throws NullPointerException if the given key
     * is {@code null}.
     */
    public ValueWrapper peek(String keyName) {
        return getCurrent(keyName).value;
    }

    /**
     * Checks if the given stack is empty.
     *
     * @param keyName name of the stack.
     *
     * @return {@code true} if the stack is empty,
     * otherwise {@code false}.
     */
    public boolean isEmpty(String keyName) {
        return map.get(keyName) == null;
    }

    /**
     * Used internally when getting the element
     * on the top of the stack.
     *
     * @param keyName key of the stack.
     *
     * @return {@code MultistackEntry} on the
     * top of the given stack.
     *
     * @throws RuntimeException if the stack is empty.
     * @throws NullPointerException if the given key
     * is {@code null}.
     */
    private MultistackEntry getCurrent(String keyName) {
        Objects.requireNonNull(keyName);

        if (isEmpty(keyName)) {
            throw new RuntimeException("Stack is empty.");
        }

        return map.get(keyName);
    }

    /**
     * An auxiliary class used to keep the reference
     * to the {@code ValueWrapper} and the next element
     * in on stack.
     */
    private static class MultistackEntry {

        /**
         * Keeps the reference to the next node
         * if it exists.
         */
        private MultistackEntry next;

        /**
         * Keeps the reference to the {@code ValueWrapper}
         * containing this node's value.
         */
        private ValueWrapper value;

        /**
         * Default constructor that assigns a value.
         *
         * @param value to be assigned.
         */
        private MultistackEntry(ValueWrapper value) {
            this.value = value;
        }
    }
}
