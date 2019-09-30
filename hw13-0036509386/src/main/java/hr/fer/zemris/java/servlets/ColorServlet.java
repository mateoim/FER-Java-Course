package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An {@code HttpServlet} used to change
 * the background color.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/setcolor"})
public class ColorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String color = req.getParameter("selected");

        req.getSession().setAttribute("pickedBgCol", color == null ? "white" : color);

        req.getRequestDispatcher("colors.jsp").forward(req, resp);
    }
}
