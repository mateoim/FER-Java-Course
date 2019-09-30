package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.function.Function;

/**
 * A layout used for a calculator app.
 *
 * @author Mateo Imbrišak
 */

public class CalcLayout implements LayoutManager2 {

    /**
     * Number of available rows.
     */
    private static final int NUMBER_OF_ROWS = 5;

    /**
     * Number of available columns.
     */
    private static final int NUMBER_OF_COLUMNS = 7;

    /**
     * Keeps the {@code Component}s occupying slots.
     */
    private Component[] occupied;

    /**
     * Keeps the number of pixels used for spacing.
     */
    private int space;

    /**
     * Default constructor that initializes all values.
     */
    public CalcLayout() {
        occupied = new Component[31];
        space = 0;
    }

    /**
     * Constructor that assigns the space between components
     * and initializes the {@code occupied} array.
     *
     * @param space to be used between components.
     */
    public CalcLayout(int space) {
        this.space = space;
        this.occupied = new Component[31];
    }

    /**
     * Adds the given {@code Component} to the layout
     * at the position specified by the constraints.
     *
     * @param comp component to be added.
     * @param constraints used to position the component.
     *
     * @throws CalcLayoutException if invalid position is passed
     * or a component already exists at the given position.
     * @throws NumberFormatException if the constraints argument is
     * a {@code String} in invalid format.
     * @throws UnsupportedOperationException if the constraints argument
     * is not a {@code RCPosition} or a {@code String}.
     */
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof RCPosition) {
            RCPosition con = (RCPosition) constraints;

            addToOccupied(con, comp);
            return;
        } else if (constraints instanceof String) {
            String[] parts = ((String) constraints).split(",");

            addToOccupied(new RCPosition(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])), comp);
            return;
        }
        throw new UnsupportedOperationException("Invalid constraint.");
    }

    /**
     * Provides the maximum size for this layout.
     *
     * @param target containing the components.
     *
     * @return maximum size for this layout.
     */
    @Override
    public Dimension maximumLayoutSize(Container target) {
        return calculateSize(target, Component::getMaximumSize);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the given component if it exists.
     *
     * @param comp to be removed.
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        for (int i = 0; i < occupied.length; i++) {
            if (occupied[i] == comp) {
                occupied[i] = null;
            }
        }
    }

    /**
     * Provides the preferred size for this layout.
     *
     * @param parent containing the components.
     *
     * @return preferred size for this layout.
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return calculateSize(parent, Component::getPreferredSize);
    }

    /**
     * Provides the minimum size for this layout.
     *
     * @param parent containing the components.
     *
     * @return minimum size for this layout.
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return calculateSize(parent, Component::getMinimumSize);
    }

    /**
     * Places the components on the layout and
     * calculates the necessary dimensions.
     *
     * @param parent containing the components.
     */
    @Override
    public void layoutContainer(Container parent) {
        double width = (double) (parent.getWidth() - (NUMBER_OF_COLUMNS - 1) * space) / NUMBER_OF_COLUMNS;
        double height = (double) (parent.getHeight() - (NUMBER_OF_ROWS - 1) * space) / NUMBER_OF_ROWS;
        int functionalWidth = (int) Math.round(width);
        int functionalHeight = (int) Math.round(height);
        int currentY = 0;
        int currentX = 0;

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                if (i == 0 && j > 2) {
                    break;
                }

                Component current = occupied[i == 0 ? j : (2 + NUMBER_OF_COLUMNS * (i - 1) + j + 1)];

                if (occupied[0] != null && current == occupied[0]) {
                    current.setBounds(currentX, currentY, (5 * functionalWidth + 4 * space), functionalHeight);
                    currentX += 5 * (functionalWidth + space);
                } else {
                    if (current != null) {
                        current.setBounds(currentX, currentY, (j == 6 || (i == 0 && j == 2)) ?
                                        parent.getWidth() - currentX : functionalWidth,
                                i == 4 ? parent.getHeight() - currentY : functionalHeight);
                    }
                    currentX += functionalWidth + space;
                }
            }

            currentY += functionalHeight + space;
            currentX = 0;
        }
    }

    /**
     * Used internally to calculate the position
     * of a component and check if it's valid.
     *
     * @param constraints used to determine the position.
     *
     * @return position based on the given constraints.
     *
     * @throws CalcLayoutException if the given position is
     * illegal.
     */
    private int calculatePosition(RCPosition constraints) {
        if (constraints.getRow() > 1 && constraints.getRow() <= NUMBER_OF_ROWS) {
            if (constraints.getColumn() >= 1 && constraints.getColumn() <= NUMBER_OF_COLUMNS) {
                return 2 + NUMBER_OF_COLUMNS * (constraints.getRow() - 2) + (constraints.getColumn());
            }
        } else if (constraints.getRow() == 1) {
            switch (constraints.getColumn()) {
                case 1:
                    return 0;
                case 6:
                    return 1;
                case 7:
                    return 2;
            }
        }
        throw new CalcLayoutException("Invalid index.");
    }

    /**
     * Adds the given component to the layout.
     *
     * @param con constraints to be used.
     * @param comp component to be added.
     *
     * @throws CalcLayoutException if the position is
     * already occupied.
     */
    private void addToOccupied(RCPosition con, Component comp) {
        int position = calculatePosition(con);

        if (occupied[position] == null) {
            occupied[position] = comp;
        } else {
            throw new CalcLayoutException("Position is already occupied.");
        }
    }

    /**
     * Used internally to calculate requested size.
     *
     * @param parent containing the components.
     * @param condition used to get the right size.
     *
     * @return requested size.
     */
    private Dimension calculateSize(Container parent, Function<Component, Dimension> condition) {
        int currentHeight = 0;
        int currentWidth = 0;

        for (Component comp : parent.getComponents()) {
            Dimension dimension = condition.apply(comp);

            if (occupied[0] != null && comp == occupied[0]) {
                int normalizedWidth = (dimension.width - 4 * space) / 5 ;
                currentWidth = normalizedWidth > currentWidth ? normalizedWidth : currentWidth;
            } else {
                currentWidth = dimension.width > currentWidth ? dimension.width : currentWidth;
            }
            currentHeight = dimension.height > currentHeight ? dimension.height : currentHeight;
        }

        Insets ins = parent.getInsets();
        return new Dimension(
                ins.left + (currentWidth * NUMBER_OF_COLUMNS + space * (NUMBER_OF_COLUMNS - 1)) + ins.right,
                ins. top + (currentHeight * NUMBER_OF_ROWS + space * (NUMBER_OF_ROWS - 1)) + ins.bottom
        );
    }
}
