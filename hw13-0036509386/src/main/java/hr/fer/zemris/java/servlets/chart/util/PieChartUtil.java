package hr.fer.zemris.java.servlets.chart.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import java.util.Map;

/**
 * A class that contains utilities
 * used to create {@code JFreeChart}s.
 *
 * @author Mateo Imbrišak
 */

public class PieChartUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private PieChartUtil() {}

    /**
     * Used to create a sample dataset.
     *
     * @return a new {@code PieDataset}.
     */
    public static PieDataset createDataset(Map<String, Integer> values) {
        DefaultPieDataset result = new DefaultPieDataset();
        values.forEach(result::setValue);
        return result;
    }

    /**
     * Used to create a {@code JFreeChart} from
     * the given dataset.
     *
     * @param dataset used to create the chart.
     *
     * @return a {@code JFreeChart} based on the
     * give data.
     */
    public static JFreeChart createChart(PieDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createPieChart3D(
                title,
                dataset,
                true,
                true,
                false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
}
