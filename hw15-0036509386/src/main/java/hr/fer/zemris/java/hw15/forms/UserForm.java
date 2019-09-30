package hr.fer.zemris.java.hw15.forms;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.encryption.CryptUtil;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@link Form} that models a
 * {@link hr.fer.zemris.java.hw15.model.BlogUser}
 *
 * @author Mateo Imbrišak
 */

public class UserForm implements Form {

    /**
     * Keeps the given first name.
     */
    private String firstName;

    /**
     * Keeps the given last name.
     */
    private String lastName;

    /**
     * Keeps the given nickname.
     */
    private String nick;

    /**
     * Keeps the given e-mail.
     */
    private String email;

    /**
     * Keeps the given password as encrypted hash.
     */
    private String passwordHash;

    /**
     * An internal constructor used to assign all values.
     *
     * @param firstName to be assigned.
     * @param lastName to be assigned.
     * @param nick to be assigned.
     * @param email to be assigned.
     * @param passwordHash to be assigned.
     */
    private UserForm(String firstName, String lastName, String nick, String email, String passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    /**
     * Creates a new empty instance of {@code UserForm}.
     *
     * @return empty instance of {@code UserForm}.
     */
    public static UserForm emptyForm() {
        return new UserForm("", "", "", "", "");
    }

    /**
     * Creates a new instance of {@code UserForm} from
     * the given {@link HttpServletRequest}.
     *
     * @param req used to get info to fill the form.
     *
     * @return a new instance of {@code UserForm} with
     * data from the given request.
     */
    public static UserForm fromRequest(HttpServletRequest req) {
        String firstName = Form.prepare(req.getParameter("fName"));
        String lastName = Form.prepare(req.getParameter("lName"));
        String nick = Form.prepare(req.getParameter("nick"));
        String email = Form.prepare(req.getParameter("email"));

        String password = req.getParameter("password");

        String passwordHash = (password == null || password.equals("")) ? "" : CryptUtil.encrypt(password);

        return new UserForm(firstName, lastName, nick, email, passwordHash);
    }

    @Override
    public void validate() {
        errors.clear();

        Form.checkAttribute(firstName, "First name", "fName", 20);
        Form.checkAttribute(lastName, "Last name", "lName", 50);
        Form.checkAttribute(nick, "Nickname", "nick", 30);
        Form.checkAttribute(passwordHash, "Password", "password", 40);
        Form.checkAttribute(email, "E-mail", "email", 100);
        checkEmail();
        checkNick();
    }

    /**
     * Creates a {@link BlogUser} from this form.
     *
     * @return a {@link BlogUser} to be used in
     * the database.
     */
    public BlogUser createUser() {
        BlogUser user = new BlogUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setNick(nick);
        user.setPasswordHash(passwordHash);

        return user;
    }

    /**
     * Provides the current {@link #firstName}.
     *
     * @return current {@link #firstName}.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Provides the current {@link #lastName}.
     *
     * @return current {@link #lastName}.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Provides the current {@link #nick}.
     *
     * @return current {@link #nick}.
     */
    public String getNick() {
        return nick;
    }

    /**
     * Provides the current {@link #email}.
     *
     * @return current {@link #email}.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Used internally to check if the
     * given e-mail is in valid format.
     */
    private void checkEmail() {
        if (hasError("email")) {
            return;
        }

        Pattern mail = Pattern.compile("^[0-9A-Z._]+@[0-9A-Z._]+\\.[A-Z]+$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = mail.matcher(email);

        if (!matcher.matches()) {
            errors.put("email", "Invalid e-mail format.");
        }
    }

    /**
     * Used internally to check if the
     * assigned nick already exists.
     */
    private void checkNick() {
        if (DAOProvider.getDAO().checkForNick(nick)) {
            errors.put("nick", "Nickname already exists.");
        }
    }
}
