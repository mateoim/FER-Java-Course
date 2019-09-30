package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A {@code Node} that represents a for loop
 * in SmartScript language.
 *
 * @author Mateo Imbrišak
 */

public class ForLoopNode extends Node {

    /**
     * Keeps the variable used in for loop.
     */
    private ElementVariable variable;

    /**
     * Keeps the starting number used
     * in for loop.
     */
    private Element startExpression;

    /**
     * Keeps the last number used in for loop.
     */
    private Element endExpression;

    /**
     * Keeps the number that represents how
     * many numbers will be skipped in each
     * iteration, can be {@code null};
     */
    private Element stepExpression;

    /**
     * Default constructor that initializes all
     * values used by the for loop.
     *
     * @param variable that keeps track of current iteration.
     * @param startExpression starting number of the loop.
     * @param endExpression last number in the loop.
     * @param stepExpression how many numbers are skipped
     *                       in each iteration, can be {@code null}.
     *
     * @throws NullPointerException if any of the first three arguments
     * are null.
     */
    public ForLoopNode(ElementVariable variable, Element startExpression,
                       Element endExpression, Element stepExpression) {
        checkConstructor(variable, startExpression, endExpression);

        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * Returns the variable used in the loop.
     *
     * @return variable.
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Returns the first number of the loop.
     *
     * @return starting number.
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Returns the final number of the loop.
     *
     * @return ending number.
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Returns number of steps skipped
     * in each iteration if it exists.
     *
     * @return number of steps skipped
     * if it exists, otherwise {@code null}.
     */
    public Element getStepExpression() {
        return stepExpression;
    }

    /**
     * Used internally to check if the constructor received
     * valid arguments.
     *
     * @param variable that keeps track of current iteration.
     * @param startExpression starting number of the loop.
     * @param endExpression last number in the loop.
     *
     * @throws NullPointerException if any of the arguments
     * is {@code null}.
     */
    private void checkConstructor(ElementVariable variable, Element startExpression, Element endExpression) {
        if (variable == null) {
            throw new NullPointerException("Variable must not be null.");
        } else if (startExpression == null) {
            throw new NullPointerException("Start expression must not be null.");
        } else if (endExpression == null) {
            throw new NullPointerException("End expression must not be null.");
        }
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitForLoopNode(this);
    }

    /**
     * A textual representation of this {@code Node}
     * that can be parsed again by a {@code SmartScriptParser}.
     *
     * @return a {@code String} that can be parsed again.
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();

        ret.append("{$ FOR ");
        ret.append(getVariable()).append(" ");
        ret.append(getStartExpression()).append(" ");
        ret.append(getEndExpression()).append(" ");

        if (getStepExpression() != null) {
            ret.append(getStepExpression()).append(" ");
        }

        ret.append("$}");

        for (int i = 0; i < numberOfChildren(); i++) {
            ret.append(getChild(i));
        }

        ret.append("{$ END $}");

        return ret.toString();
    }
}
