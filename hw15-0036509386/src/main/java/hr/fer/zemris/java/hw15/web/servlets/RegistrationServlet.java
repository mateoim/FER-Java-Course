package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.UserForm;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * An {@code HttpServlet} used to
 * register new users.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/servleti/register"})
public class RegistrationServlet extends HttpServlet {

    private static final long serialVersionUID = -2553458378415075720L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("form", UserForm.emptyForm());

        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        UserForm form = UserForm.fromRequest(req);
        form.validate();

        if (form.hasErrors()) {
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
            return;
        }

        BlogUser user = form.createUser();
        DAOProvider.getDAO().createUser(user);

        resp.sendRedirect("main");
    }
}
