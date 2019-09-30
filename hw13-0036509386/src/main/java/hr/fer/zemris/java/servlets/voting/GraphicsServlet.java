package hr.fer.zemris.java.servlets.voting;

import hr.fer.zemris.java.servlets.chart.util.PieChartUtil;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.PieDataset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * A {@code HttpServlet} that generates
 * a pie chart based on the {@code stats} map.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/glasanje-grafika"})
public class GraphicsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Integer> stats = (Map<String, Integer>) req.getSession().getAttribute("stats");

        PieDataset dataset = PieChartUtil.createDataset(stats);
        JFreeChart chart = PieChartUtil.createChart(dataset, "People's favourite bands");
        ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 400, 400);
    }
}
