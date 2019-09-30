package hr.fer.zemris.java.hw14.voting;

import hr.fer.zemris.java.hw14.dao.DAOProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A {@code HttpServlet} used to
 * register a vote.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/servleti/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int vote = Integer.parseInt(req.getParameter("id"));

        DAOProvider.getDao().vote(vote);

        resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati");
    }
}
