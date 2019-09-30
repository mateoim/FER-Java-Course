package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.CommentForm;
import hr.fer.zemris.java.hw15.forms.EntryForm;
import hr.fer.zemris.java.hw15.model.BlogEntry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * An {@code HttpServlet} used to handle
 * displaying, adding adn editing posts
 * and comments.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/servleti/author/*"})
public class AuthorServlet extends HttpServlet {

    private static final long serialVersionUID = 2493972265448878003L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String[] parts = req.getPathInfo().split("/");

        if (parts.length < 1) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        req.setAttribute("author", parts[1]);

        switch (parts.length) {
            case 2:
                List<BlogEntry> entries = DAOProvider.getDAO().getEntriesByAuthor(parts[1]);
                req.setAttribute("posts", entries);

                req.getRequestDispatcher("/WEB-INF/pages/blog.jsp").forward(req, resp);
                return;
            case 3:
                if (parts[2].equals("new")) {
                    if (!parts[1].equals(req.getSession().getAttribute("current.user.nick"))) {
                        req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
                        return;
                    }

                    req.setAttribute("form", EntryForm.emptyForm());
                    req.getRequestDispatcher("/WEB-INF/pages/createEntry.jsp").forward(req, resp);
                    return;
                } else if (parts[2].equals("edit")) {
                    if (!parts[1].equals(req.getSession().getAttribute("current.user.nick"))) {
                        req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
                        return;
                    }

                    BlogEntry entry;

                    try {
                        long id = Long.parseLong(req.getParameter("id"));
                        entry = DAOProvider.getDAO().getBlogEntry(id);
                    } catch (NumberFormatException | DAOException exc) {
                        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                        return;
                    }

                    if (!entry.getCreator().getNick().equals(parts[1])) {
                        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                        return;
                    }

                    EntryForm form = EntryForm.fromEntry(entry);

                    req.setAttribute("form", form);
                    req.getRequestDispatcher("/WEB-INF/pages/createEntry.jsp").forward(req, resp);
                    return;
                } else {
                    BlogEntry entry;

                    try {
                        long id = Long.parseLong(parts[2]);
                        entry = DAOProvider.getDAO().getBlogEntry(id);
                    } catch (NumberFormatException | DAOException exc) {
                        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                        return;
                    }

                    if (!entry.getCreator().getNick().equals(parts[1])) {
                        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                        return;
                    }

                    req.setAttribute("form", CommentForm.emptyForm());
                    req.setAttribute("post", entry);
                    req.getRequestDispatcher("/WEB-INF/pages/post.jsp").forward(req, resp);
                    return;
                }
            default:
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String[] parts = req.getPathInfo().split("/");
        req.setAttribute("author", parts[1]);

        if (parts.length == 3) {
            if (parts[2].equals("new")) {
                if (!parts[1].equals(req.getSession().getAttribute("current.user.nick"))) {
                    req.getRequestDispatcher("/WEB-INF/pages/forbidden.jsp").forward(req, resp);
                    return;
                }

                EntryForm form = EntryForm.fromRequest(req);
                form.validate();

                if (form.hasErrors()) {
                    req.setAttribute("form", form);
                    req.getRequestDispatcher("/WEB-INF/pages/createEntry.jsp").forward(req, resp);
                    return;
                }

                DAOProvider.getDAO().confirmPost(form.createEntry());

                resp.sendRedirect(req.getContextPath() + "/servleti/author/" + parts[1]);
                return;
            } else {
                BlogEntry entry;

                try {
                    long id = Long.parseLong(parts[2]);
                    entry = DAOProvider.getDAO().getBlogEntry(id);
                } catch (NumberFormatException | DAOException exc) {
                    req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                    return;
                }

                if (!entry.getCreator().getNick().equals(parts[1])) {
                    req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                    return;
                }

                CommentForm form = CommentForm.fromRequest(req);
                form.validate();

                if (form.hasErrors()) {
                    req.setAttribute("form", form);
                    req.setAttribute("post", entry);
                    req.getRequestDispatcher("/WEB-INF/pages/post.jsp").forward(req, resp);
                    return;
                }

                DAOProvider.getDAO().confirmComment(form.createComment());

                resp.sendRedirect(parts[2]);
                return;
            }
        }

        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
