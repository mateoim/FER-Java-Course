package hr.fer.zemris.java.hw15.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An {@code HttpServlet} used to redirect
 * to the home page.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/index.jsp"})
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = -2603440439632343203L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("servleti/main");
    }
}
