package hr.fer.zemris.java.servlets.voting;

import hr.fer.zemris.java.servlets.voting.util.ParseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * A {@code HttpServlet} used to
 * register a vote.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int vote = Integer.parseInt(req.getParameter("id"));
        String fileName = req.getServletContext().getRealPath("/WEB-INF/voting/glasanje-rezultati.txt");
        Path results = Paths.get(fileName);

        if (Files.exists(results) && Files.isReadable(results) && Files.isRegularFile(results)) {
            List<String> lines = Files.readAllLines(results);
            Map<Integer, Integer> resultsTable = ParseUtil.parseResults(lines);

            resultsTable.putIfAbsent(vote, 0);
            resultsTable.merge(vote, 1, (o, n) -> ++o);

            StringBuilder output = new StringBuilder();

            for (Map.Entry<Integer, Integer> entry : resultsTable.entrySet()) {
                output.append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
            }

            Files.writeString(results, output.toString());
        } else {
            Files.writeString(results, vote + "\t1\n");
        }

        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }
}
