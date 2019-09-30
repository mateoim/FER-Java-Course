package hr.fer.zemris.java.servlets.voting;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * A {@code HttpServlet} used to
 * generate an XLS file containing the
 * results in {@code stats} attribute.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/glasanje-xls"})
public class XLSServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {

            HSSFSheet sheet = workbook.createSheet();
            HSSFRow title = sheet.createRow(0);
            title.createCell(0).setCellValue("band");
            title.createCell(1).setCellValue("number of votes");

            Map<String, Integer> stats = (Map<String, Integer>) req.getSession().getAttribute("stats");

            int counter = 1;
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                HSSFRow row = sheet.createRow(counter++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            resp.setHeader("Content-Disposition", "attachment; filename=\"stats.xls\"");
            workbook.write(resp.getOutputStream());
        }
    }
}
