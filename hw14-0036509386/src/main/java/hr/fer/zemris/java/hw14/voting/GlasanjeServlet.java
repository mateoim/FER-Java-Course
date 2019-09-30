package hr.fer.zemris.java.hw14.voting;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.voting.util.PollInfo;
import hr.fer.zemris.java.hw14.voting.util.PollOptionInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * An {@code HttpServlet} used for voting
 * in a poll.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/servleti/glasanje"})
public class GlasanjeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(req.getParameter("pollID"));
        } catch (NullPointerException | NumberFormatException exc) {
            resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
            return;
        }

        PollInfo selected = DAOProvider.getDao().getPoll(id);
        List<PollOptionInfo> options = DAOProvider.getDao().getPollOptions(id);

        req.setAttribute("poll", selected);
        req.setAttribute("options", options);
        req.getSession().setAttribute("pollID", id);

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }
}
