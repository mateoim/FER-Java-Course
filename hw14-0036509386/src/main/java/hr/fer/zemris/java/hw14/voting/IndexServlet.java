package hr.fer.zemris.java.hw14.voting;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An {@code HttpServlet} used to
 * redirect to the actual index page.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/index.html"})
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = 8641399530417626287L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
    }
}
