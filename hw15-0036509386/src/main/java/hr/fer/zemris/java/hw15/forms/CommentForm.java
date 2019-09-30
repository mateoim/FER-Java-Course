package hr.fer.zemris.java.hw15.forms;

import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@link Form} that models a comment.
 *
 * @author Mateo Imbrišak
 */

public class CommentForm implements Form {

    /**
     * Keeps the user's e-mail.
     */
    private String email;

    /**
     * Keeps the comment's text.
     */
    private String comment;

    /**
     * Keeps the parent post's ID.
     */
    private String postID;

    /**
     * Keeps the entry to be used in {@link #createComment()}
     * after it is assigned in {@link #validate()}.
     */
    private BlogEntry instance = null;

    /**
     * Internal constructor that assigns all values.
     *
     * @param email to be assigned.
     * @param comment to be assigned.
     * @param postID to be assigned.
     */
    private CommentForm(String email, String comment, String postID) {
        this.email = email;
        this.comment = comment;
        this.postID = postID;
    }

    /**
     * Creates an empty template.
     *
     * @return empty {@code CommentForm}.
     */
    public static CommentForm emptyForm() {
        return new CommentForm("", "", "");
    }

    /**
     * Creates a {@code CommentForm} from the
     * given {@link HttpServletRequest}.
     *
     * @param req used to obtain info.
     *
     * @return filled, unvalidated {@code CommentForm}.
     */
    public static CommentForm fromRequest(HttpServletRequest req) {
        String id = req.getPathInfo().split("/")[2];
        String text = req.getParameter("text");
        String email;

        if (req.getSession().getAttribute("current.user.id") != null) {
            email = DAOProvider.getDAO()
                    .getUserByNick((String) req.getSession().getAttribute("current.user.nick")).getEmail();
        } else {
            email = req.getParameter("email");
        }

        return new CommentForm(email, text, id);
    }

    @Override
    public void validate() {
        errors.clear();

        Form.checkAttribute(email, "E-mail", "email", 100);
        Form.checkAttribute(comment, "Comment", "text", 100);
        checkEmail();

        try {
            instance = DAOProvider.getDAO().getBlogEntry(Long.parseLong(postID));
        } catch (NumberFormatException | DAOException exc) {
            errors.put("id", "Invalid entry.");
        }
    }

    /**
     * Creates an instance of {@link BlogComment}
     * to be used in the database.
     *
     * @return {@link BlogComment} created from
     * current data.
     */
    public BlogComment createComment() {
        BlogComment ret = new BlogComment();
        ret.setUsersEMail(email);
        ret.setBlogEntry(instance);
        ret.setMessage(comment);
        ret.setPostedOn(new Date());

        return ret;
    }

    /**
     * Provides the current e-mail.
     *
     * @return current e-mail.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Provides the comment's body.
     *
     * @return comment's body.
     */
    public String getComment() {
        return comment;
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
}
