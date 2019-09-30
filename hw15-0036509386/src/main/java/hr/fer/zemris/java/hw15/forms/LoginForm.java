package hr.fer.zemris.java.hw15.forms;

import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.encryption.CryptUtil;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * A {@link Form} used to check
 * the login information.
 *
 * @author Mateo Imbrišak
 */

public class LoginForm implements Form {

    /**
     * Keeps the given username.
     */
    private String username;

    /**
     * Keeps the given password as
     * encrypted password hash.
     */
    private String passwordHash;

    /**
     * Keeps the {@link BlogUser} used
     * to check the {@link #username}.
     */
    private BlogUser user = null;

    /**
     * Private constructor used to assign all values.
     *
     * @param username to be assigned.
     * @param passwordHash to be assigned.
     */
    private LoginForm(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    /**
     * Creates a {@code LoginForm} with empty values.
     *
     * @return an empty {@code LoginForm}.
     */
    public static LoginForm emptyForm() {
        return new LoginForm("", "");
    }

    /**
     * Creates a {@code LoginForm} form {@link HttpServletRequest}.
     *
     * @param req used to get info to fill the form.
     *
     * @return a new {@code LoginForm} filled with info
     * from the given request.
     */
    public static LoginForm fromRequest(HttpServletRequest req) {
        String username = Form.prepare(req.getParameter("nick"));
        String password = req.getParameter("password");

        String passwordHash = (password == null || password.equals("")) ? "" : CryptUtil.encrypt(password);

        return new LoginForm(username, passwordHash);
    }

    /**
     * Provides the {@link BlogUser} being
     * logged in if it exists.
     *
     * @return {@link BlogUser} being logged in.
     *
     * @throws NullPointerException if the {@link #user}
     * hasn't been assigned yet.
     */
    public BlogUser getUser() {
        return Objects.requireNonNull(user);
    }

    /**
     * Provides the given {@link #username}.
     *
     * @return given {@link #username}.
     */
    public String getUsername() {
        return username;
    }

    @Override
    public void validate() {
        errors.clear();

        Form.checkAttribute(username, "Nickname", "nick", 30);
        Form.checkAttribute(passwordHash, "Password", "password", 40);

        if (!hasErrors()) {
            authenticate();
        }
    }

    /**
     * Used internally to check if the username and
     * password is valid.
     */
    private void authenticate() {
        try {
            user = DAOProvider.getDAO().getUserByNick(username);

            if (!user.getPasswordHash().equals(passwordHash)) {
                errors.put("password", "Password doesn't match.");
            }
        } catch (DAOException exc) {
            errors.put("nick", "Nick does not exist.");
        }
    }
}
