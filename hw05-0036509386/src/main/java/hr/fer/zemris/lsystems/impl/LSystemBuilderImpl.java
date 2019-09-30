package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

import java.awt.*;
import java.text.ParseException;

/**
 * An implementation of {@code LSystemBuilder}.
 * All setters return a reference to the same
 * {@code LSystemBuilderImpl}.
 *
 * @author Mateo Imbrišak
 */

public class LSystemBuilderImpl implements LSystemBuilder {

    /**
     * Keeps pairs of a symbol and a command to be executed
     * when {@link #build()} is called.
     */
    private Dictionary<Character, Command> registeredCommands;

    /**
     * Keeps pairs of {@code char} and {@code String} used
     * in production when {@link #build()} is called.
     */
    private Dictionary<Character, String> registeredProduction;

    /**
     * Keeps current unit length.
     */
    private double unitLength = 0.1;

    /**
     * Keeps the scaler used to calculate the angle.
     */
    private double unitLengthDegreeScaler = 1;

    /**
     * Keeps the origin position.
     */
    private Vector2D origin = new Vector2D(0, 0);

    /**
     * Keeps the angle in degrees.
     */
    private double angle = 0;

    /**
     * Keeps the current axiom.
     */
    private String axiom = "";

    /**
     * Default constructor that initializes the dictionaries.
     */
    public LSystemBuilderImpl() {
        registeredCommands = new Dictionary<>();
        registeredProduction = new Dictionary<>();
    }

    /**
     * Sets the unit length to the given value.
     *
     * @param unitLength to be assigned.
     *
     * @return reference to this {@code LSystemBuilder}.
     */
    @Override
    public LSystemBuilder setUnitLength(double unitLength) {
        this.unitLength = unitLength;
        return this;
    }

    /**
     * Sets the origin to the given coordinates.
     *
     * @param x coordinate of the point.
     * @param y coordinate of the point.
     *
     * @return reference to this {@code LSystemBuilder}.
     */
    @Override
    public LSystemBuilder setOrigin(double x, double y) {
        origin = new Vector2D(x, y);
        return this;
    }

    /**
     * Updates the angle and returns a reference to this
     * {@code LSystemBuilderImpl}.
     *
     * @param angle to be updated.
     *
     * @return reference to this {@code LSystemBuilderImpl}.
     */
    @Override
    public LSystemBuilder setAngle(double angle) {
        this.angle = angle;
        return this;
    }

    /**
     * Sets the axiom to the given {@code String}.
     *
     * @param axiom to be assigned.
     *
     * @return reference to this {@code LSystemBuilder}.
     */
    @Override
    public LSystemBuilder setAxiom(String axiom) {
        this.axiom = axiom;
        return this;
    }

    /**
     * Sets the scaler to the given value.
     *
     * @param unitLengthDegreeScaler to be assigned.
     *
     * @return reference to this {@code LSystemBuilder}.
     */
    @Override
    public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
        this.unitLengthDegreeScaler = unitLengthDegreeScaler;
        return this;
    }

    /**
     * Adds the given pair to the production {@code dictionary}.
     *
     * @param symbol used for conversion.
     * @param production with which the symbol is replaced in production.
     *
     * @return reference to this {@code LSystemBuilder}.
     */
    @Override
    public LSystemBuilder registerProduction(char symbol, String production) {
        registeredProduction.put(symbol, production);
        return this;
    }

    /**
     * Adds the given pair to the command {@code Dictionary}.
     *
     * @param symbol used to identify the command in string.
     * @param action {@code String} used to construct the appropriate {@code Command}.
     *
     * @return reference to this {@code LSystemBuilder}.
     */
    @Override
    public LSystemBuilder registerCommand(char symbol, String action) {
        try {
            registeredCommands.put(symbol, actionProtocol(action));
        } catch (ParseException exc) {
            System.out.println(exc.getMessage());
        }
        return this;
    }

    /**
     * Configures everything from a given {@code String} array.
     *
     * @param lines a {@code String} array containing all instructions.
     *
     * @return reference to this {@code LSystemBuilder}.
     */
    @Override
    public LSystemBuilder configureFromText(String[] lines) {
        for (String line : lines) {
            String[] lineParts = line.split("\\s+");
            try {
                protocol(lineParts);
            } catch (ParseException exc) {
                System.out.println(exc.getMessage());
            }
        }
        return this;
    }

    /**
     * Creates a new {@code LSystem} from the
     * current configuration.
     *
     * @return a new {@code LSystem}.
     */
    @Override
    public LSystem build() {

        return new LSystem() {

            /**
             * Generates a {@code String} used to draw
             * the picture.
             *
             * @param level of the L-system.
             *
             * @return a {@code String} used to paint
             * the picture.
             */
            @Override
            public String generate(int level) {
                String ret = axiom;

                for (int i = 0; i < level; i++) {
                    char[] copy = ret.toCharArray();
                    StringBuilder nextLevel = new StringBuilder();

                    for (char c: copy) {
                        String toAdd = registeredProduction.get(c);

                        if (toAdd != null) {
                            nextLevel.append(toAdd);
                        } else {
                            nextLevel.append(c);
                        }
                    }

                    ret = nextLevel.toString();
                }

                return ret;
            }

            /**
             * Draws the picture using the configuration info.
             *
             * @param level of the L-system.
             * @param painter which draws the picture.
             */
            @Override
            public void draw(int level, Painter painter) {
                Context ctx = new Context();

                TurtleState currentState = new TurtleState(origin,
                        new Vector2D(1, 0).rotated(angle * Math.PI / 180), Color.BLACK,
                        unitLength * Math.pow(unitLengthDegreeScaler, level));
                ctx.pushState(currentState);

                char[] toExecute = generate(level).toCharArray();

                for (char c : toExecute) {
                    Command cmd = registeredCommands.get(c);

                    if (cmd != null) {
                        cmd.execute(ctx, painter);
                    }
                }
            }
        };
    }

    /**
     * Used internally to process configuration from text.
     *
     * @param parts of the configuration.
     *
     * @throws ParseException if the input cannot be parsed.
     */
    private void protocol(String[] parts) throws ParseException {
        switch (parts[0]) {
            case "":
                return;
            case "origin":
                setOrigin(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
                break;
            case "angle":
                setAngle(Double.parseDouble(parts[1]));
                break;
            case "unitLength":
                setUnitLength(Double.parseDouble(parts[1]));
                break;
            case "unitLengthDegreeScaler":
                if (parts.length == 4) {
                    setUnitLengthDegreeScaler(Double.parseDouble(parts[1]) / Double.parseDouble(parts[3]));
                    break;
                }

                if (parts.length == 2) {
                    String[] formula = parts[1].split("/");
                    if (formula.length == 1) {
                        setUnitLengthDegreeScaler(Double.parseDouble(formula[0]));
                    } else {
                        setUnitLengthDegreeScaler(Double.parseDouble(formula[0]) / Double.parseDouble(formula[1]));
                    }
                }

                if (parts.length == 3) {
                    if (parts[1].charAt(parts[1].length() - 1) == '/') {
                        setUnitLengthDegreeScaler(Double.parseDouble(parts[1].substring(0, parts[1].length() - 2))
                                / Double.parseDouble(parts[2]));
                    } else if (parts[2].charAt(0) == '/') {
                        setUnitLengthDegreeScaler(Double.parseDouble(parts[1]) /
                                Double.parseDouble(parts[2].substring(1, parts[2].length() - 1)));
                    }
                }
                break;
            case "axiom":
                setAxiom(parts[1]);
                break;
            case "production":
                registerProduction(parts[1].charAt(0), parts[2]);
                break;
            case "command":
                StringBuilder builder = new StringBuilder();

                for (int i = 2; i < parts.length; i++) {
                    builder.append(parts[i]).append(" ");
                }

                registerCommand(parts[1].charAt(0), builder.toString());
                break;
            default:
                throw new ParseException("Unsupported command found.", 0);
        }
    }

    /**
     * Used internally to create a {@code Command} form text.
     *
     * @param action a {@code String} representing a {@code Command}.
     *
     * @return a {@code Command} generated from text.
     *
     * @throws ParseException if the input cannot be parsed.
     */
    private Command actionProtocol(String action) throws ParseException{
        String[] parts = action.split("\\s+");

        switch (parts[0]) {
            case "draw":
                return new DrawCommand(Double.parseDouble(parts[1]));
            case "skip":
                return new SkipCommand(Double.parseDouble(parts[1]));
            case "scale":
                return new ScaleCommand(Double.parseDouble(parts[1]));
            case "rotate":
                return new RotateCommand(Double.parseDouble(parts[1]));
            case "push":
                return new PushCommand();
            case "pop":
                return new PopCommand();
            case "color":
                return new ColorCommand(Color.decode("0x" + parts[1]));
            default:
                throw new ParseException("Unsupported action found.", 0);
        }
    }
}
