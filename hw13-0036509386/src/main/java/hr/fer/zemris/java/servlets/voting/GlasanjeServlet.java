package hr.fer.zemris.java.servlets.voting;

import hr.fer.zemris.java.servlets.voting.util.BandInfo;
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
import java.util.Set;

/**
 * A {@code HttpServlet} used for voting
 * for your favourite band.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/glasanje"})
public class GlasanjeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/voting/glasanje-definicija.txt");

        Path input = Paths.get(fileName);
        List<String> lines = Files.readAllLines(input);

        Set<BandInfo> bands = ParseUtil.parseBands(lines);

        req.getSession().setAttribute("bands", bands);
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }
}
