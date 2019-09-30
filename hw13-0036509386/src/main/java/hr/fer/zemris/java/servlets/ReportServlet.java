package hr.fer.zemris.java.servlets;

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
import java.util.HashMap;
import java.util.Map;

/**
 * A {@code HttpServlet} used to create an
 * image of a pie chart used for the report.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/reportImage"})
public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");

        Map <String, Integer> data = new HashMap<>();
        data.put("Linux", 80);
        data.put("Mac", 18);
        data.put("Windows", 2);
        PieDataset dataset = PieChartUtil.createDataset(data);
        JFreeChart chart = PieChartUtil.createChart(dataset, "Which operating system are you using?");
        ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 400, 400);
    }
}
