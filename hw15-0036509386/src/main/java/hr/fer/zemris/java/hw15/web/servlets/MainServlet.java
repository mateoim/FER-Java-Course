package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.LoginForm;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * An {@code HttpServlet} used to
 * handle user login.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/servleti/main"})
public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = 2883257679449396573L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        listUsers(req);

        req.setAttribute("form", LoginForm.emptyForm());

        req.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        listUsers(req);

        req.setCharacterEncoding("UTF-8");

        LoginForm form = LoginForm.fromRequest(req);
        form.validate();

        if (form.hasErrors()) {
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
            return;
        }

        BlogUser user = form.getUser();

        req.getSession().setAttribute("current.user.id", user.getId());
        req.getSession().setAttribute("current.user.fn", user.getFirstName());
        req.getSession().setAttribute("current.user.ln", user.getLastName());
        req.getSession().setAttribute("current.user.nick", user.getNick());

        resp.sendRedirect("main");
    }

    /**
     * Used internally to list all
     * registered users.
     *
     * @param req request where the
     *            users are being stored.
     */
    private void listUsers(HttpServletRequest req) {
        List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();

        req.setAttribute("users", users);
    }
}
