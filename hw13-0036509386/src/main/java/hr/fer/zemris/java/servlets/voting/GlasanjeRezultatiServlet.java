package hr.fer.zemris.java.servlets.voting;

import hr.fer.zemris.java.servlets.voting.util.BandInfo;
import hr.fer.zemris.java.servlets.voting.util.ParseUtil;
import hr.fer.zemris.java.servlets.voting.util.VotingResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A {@code HttpServlet} used to
 * process the voting results.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<BandInfo> bands = (Set<BandInfo>) req.getSession().getAttribute("bands");
        String fileName = req.getServletContext().getRealPath("/WEB-INF/voting/glasanje-rezultati.txt");
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        Map<Integer, Integer> results = ParseUtil.parseResults(lines);

        Set<VotingResult> votes = new TreeSet<>();
        for (BandInfo band : bands) {
            votes.add(new VotingResult(band, results.get(band.getId()) == null ? 0 : results.get(band.getId())));
        }

        Set<BandInfo> winners = votes.stream()
                .filter((r) -> r.getNumberOfVotes() >= votes.stream()
                        .mapToInt(VotingResult::getNumberOfVotes)
                        .max().orElse(0))
                .map(VotingResult::getBand)
                .collect(Collectors.toSet());

        Map<String, Integer> stats = votes.stream()
                .collect(Collectors.toMap((k) -> k.getBand().getName(), VotingResult::getNumberOfVotes));

        req.setAttribute("votes", votes);
        req.setAttribute("winners", winners);
        req.getSession().setAttribute("stats", stats);

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
