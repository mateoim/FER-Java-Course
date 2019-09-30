package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.math.Vector2D;

import java.awt.*;

/**
 * A class used to keep all details about the turtle's
 * current state.
 *
 * @author Mateo Imbrišak
 */

public class TurtleState {

    /**
     * Keeps the current position of the turtle.
     */
    private Vector2D position;

    /**
     * Keeps the direction in which the turtle
     * is facing currently.
     */
    private Vector2D direction;

    /**
     * Keeps the color with which the turtle
     * currently draws.
     */
    private Color currentColor;

    /**
     * Keeps how much will the turtle move in
     * the current direction when issued to move.
     */
    private double effectiveStep;

    /**
     * Default constructor that initializes all values.
     *
     * @param position where the turtle currently is.
     * @param direction in which the turtle is currently facing.
     * @param currentColor which the turtle is using to draw.
     * @param effectiveStep which the turtle will take when next issued
     *             to move.
     */
    public TurtleState(Vector2D position, Vector2D direction, Color currentColor, double effectiveStep) {
        this.position = position;
        this.direction = direction;
        this.currentColor = currentColor;
        this.effectiveStep = effectiveStep;
    }

    /**
     * Creates a new instance of {@code TurtleState} with
     * the current properties.
     *
     * @return an independent copy of this {@code TurtleState}.
     */
    public TurtleState copy() {
        return new TurtleState(position.copy(), direction.copy(), new Color(currentColor.getRGB()), effectiveStep);
    }

    /**
     * Returns the current position in this {@code TurtleState}.
     *
     * @return current position as {@code Vector2D}.
     */
    public Vector2D getPosition() {
        return position;
    }

    /**
     * Sets the current position to the given vector.
     *
     * @param position you want to set.
     */
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    /**
     * Returns the current direction in this {@code TurtleState}.
     *
     * @return current direction as {@code Vector2D}.
     */
    public Vector2D getDirection() {
        return direction;
    }

    /**
     * Sets the current direction to the given vector.
     *
     * @param direction you want to set.
     */
    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }

    /**
     * Returns the current color in this {@code TurtleState}.
     *
     * @return current color as {@code Color}.
     */
    public Color getCurrentColor() {
        return currentColor;
    }

    /**
     * Sets the current color to the given one.
     *
     * @param currentColor you want to set.
     */
    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    /**
     * Returns the current step in this {@code TurtleState}.
     *
     * @return current step.
     */
    public double getEffectiveStep() {
        return effectiveStep;
    }

    /**
     * Sets the current effective step to the given value.
     *
     * @param effectiveStep you want to set.
     */
    public void setEffectiveStep(double effectiveStep) {
        this.effectiveStep = effectiveStep;
    }
}
