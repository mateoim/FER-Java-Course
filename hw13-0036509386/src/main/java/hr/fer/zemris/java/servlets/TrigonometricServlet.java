package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@code HttpServlet} that calculates
 * sine and cosine values for angles between
 * the given {@code a} and {@code b} values.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/trigonometric"})
public class TrigonometricServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int a = 0;
        int b = 360;

        try {
            a = req.getParameter("a") == null ? 0 : Integer.parseInt(req.getParameter("a"));
            b = req.getParameter("b") == null ? 360 : Integer.parseInt(req.getParameter("b"));
        } catch (NumberFormatException ignored) {}

        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        b = b > a + 720 ? a + 720 : b;

        List<Double> sin = new ArrayList<>(b - a + 1);
        List<Double> cos = new ArrayList<>(b - a + 1);

        for (int i = a; i <= b; i++) {
            sin.add(Math.sin(Math.toRadians(i)));
            cos.add(Math.cos(Math.toRadians(i)));
        }

        req.setAttribute("sin", sin);
        req.setAttribute("cos", cos);
        req.setAttribute("a", a);
        req.setAttribute("b", b);

        req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
    }
}
