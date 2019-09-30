package hr.fer.zemris.java.hw15.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An {@code HttpServlet} used
 * to logout the current user.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/servleti/logout"})
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 2930990965925199668L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();

        req.getRequestDispatcher("main").forward(req, resp);
    }
}
