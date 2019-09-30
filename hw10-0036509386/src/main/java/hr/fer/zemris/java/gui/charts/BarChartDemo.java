package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that creates a visual representation of
 * the given data as a bar chart.
 *
 * @author Mateo Imbrišak
 */

public class BarChartDemo extends JFrame {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor that creates a {@code BarChartComponent}
     * from the given {@code BarChart} and writes the file given to
     * the label on top.
     *
     * @param chart containing the data.
     * @param input file used to generate the table.
     */
    public BarChartDemo(BarChart chart, Path input) {
        BarChartComponent component = new BarChartComponent(chart);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        add(component, BorderLayout.CENTER);
        add(new JLabel(input.toAbsolutePath().toString(), SwingConstants.CENTER), BorderLayout.NORTH);

        setVisible(true);
    }

    /**
     * Used to start the program.
     *
     * @param args path to a file containing the data
     *             to be used to create the bar chart.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments. Terminating...");
            System.exit(-1);
        }

        Path src = Paths.get(args[0]);

        try {
            List<String> input = Files.readAllLines(src);
            BarChart chart = generateChart(input);

            SwingUtilities.invokeLater(() -> new BarChartDemo(chart, src));
        } catch (IOException exc) {
            System.out.println("Error while reading the file. Terminating...");
            System.exit(-1);
        } catch (RuntimeException exc) {
            System.out.println("File has invalid formatting.");
        }
    }

    /**
     * Used internally to generate the table from input.
     *
     * @param input a {@code List} containing all lines from
     *              the input file, only first six are used.
     *
     * @return a {@code BarChart} created from the input.
     *
     * @throws RuntimeException if input doesn't have enough elements.
     * @throws NumberFormatException if numerical input is not in
     * expected format.
     */
    private static BarChart generateChart(List<String> input) {
        if (input.size() < 5) {
            throw new RuntimeException("Invalid input.");
        }

        String x = input.get(0);
        String y = input.get(1);

        String[] points = input.get(2).split("\\s+");
        List<XYValue> values = new LinkedList<>();

        for (String value : points) {
            String[] point = value.split(",");
            values.add(new XYValue(Integer.parseInt(point[0]), Integer.parseInt(point[1])));
        }

        int yMin = Integer.parseInt(input.get(3));
        int yMax = Integer.parseInt(input.get(4));

        int space = (input.size() == 5 || input.get(5).isBlank()) ? 1 : Integer.parseInt(input.get(5));

        return new BarChart(values, x, y, yMin, yMax, space);
    }
}
