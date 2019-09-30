package hr.fer.zemris.java.servlets;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A {@code HttpServlet} used to create
 * an MS Excel table with numbers within
 * the {@code a} and {@code b} parameters
 * to the power of up to {@code n} parameter.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/powers"})
public class PowerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int a, b, n;

        try {
            a = Integer.parseInt(req.getParameter("a"));
            b = Integer.parseInt(req.getParameter("b"));
            n = Integer.parseInt(req.getParameter("n"));
        } catch (NumberFormatException | NullPointerException exc) {
            req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
            req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        try (HSSFWorkbook workbook = new HSSFWorkbook()) {

            for (int i = 0; i < n; i++) {
                HSSFSheet sheet = workbook.createSheet();
                HSSFRow title = sheet.createRow(0);
                title.createCell(0).setCellValue("number");
                title.createCell(1).setCellValue("result");

                for (int j = 0; j < b - a + 1; j++) {
                    HSSFRow row = sheet.createRow(j + 1);

                    row.createCell(0).setCellValue(a + j);
                    row.createCell(1).setCellValue(Math.pow(a + j, i + 1));
                }
            }

            resp.setHeader("Content-Disposition", "attachment; filename=\"powers.xls\"");
            workbook.write(resp.getOutputStream());
        }
    }
}
