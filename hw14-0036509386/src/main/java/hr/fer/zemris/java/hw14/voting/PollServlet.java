package hr.fer.zemris.java.hw14.voting;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.voting.util.PollInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * An {@code HttpServlet} that renders all
 * available polls from the {@code Polls} table
 * in the database.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/servleti/index.html"})
public class PollServlet extends HttpServlet {

    private static final long serialVersionUID = -2123669105135360720L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<PollInfo> polls = DAOProvider.getDao().getPolls();
        req.setAttribute("polls", polls);

        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }
}
