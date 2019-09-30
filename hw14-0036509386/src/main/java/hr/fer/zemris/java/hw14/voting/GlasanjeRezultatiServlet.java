package hr.fer.zemris.java.hw14.voting;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.voting.util.PollOptionInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A {@code HttpServlet} used to
 * process the voting results.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/servleti/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pollID;

        try {
            pollID = (int) req.getSession().getAttribute("pollID");
        } catch (NullPointerException exc) {
            resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
            return;
        }

        List<PollOptionInfo> option = DAOProvider.getDao().getPollOptions(pollID);

        Set<PollOptionInfo> votes = new TreeSet<>(option);

        Set<PollOptionInfo> winners = votes.stream()
                .filter((r) -> r.getNumberOfVotes() >= votes.stream()
                        .mapToInt(PollOptionInfo::getNumberOfVotes)
                        .max().orElse(0))
                .collect(Collectors.toSet());

        Map<String, Integer> stats = votes.stream()
                .collect(Collectors.toMap(PollOptionInfo::getTitle, PollOptionInfo::getNumberOfVotes));

        req.setAttribute("votes", votes);
        req.setAttribute("winners", winners);
        req.getSession().setAttribute("stats", stats);

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
