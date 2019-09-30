package hr.fer.zemris.java.hw15.forms;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * A {@link Form} used to model a {@link BlogEntry}.
 *
 * @author Mateo Imbrišak
 */

public class EntryForm implements Form {

    /**
     * Keeps the given title.
     */
    private String title;

    /**
     * Keeps the post's body.
     */
    private String text;

    /**
     * Keeps the author's name.
     */
    private String creator;

    /**
     * Keeps the post's ID if it's being edited.
     */
    private Long existingID = null;

    /**
     * Used internally to create a new instance.
     *
     * @param title to be assigned.
     * @param text to be assigned.
     * @param creator to be assigned.
     */
    private EntryForm(String title, String text, String creator) {
        this.title = title;
        this.text = text;
        this.creator = creator;
    }

    /**
     * Creates an empty instance of {@code EntryForm}.
     *
     * @return empty {@code EntryForm}.
     */
    public static EntryForm emptyForm() {
        return new EntryForm("", "", "");
    }

    /**
     * Creates a new {@code EntryForm} from the given
     * {@link javax.servlet.http.HttpServletRequest}.
     *
     * @param req containing data about the entry.
     *
     * @return a new instance of {@code EntryForm}
     * created from the given request.
     */
    public static EntryForm fromRequest(HttpServletRequest req) {
        String title = Form.prepare(req.getParameter("title"));
        String text = Form.prepare(req.getParameter("text"));

        Object author = req.getSession().getAttribute("current.user.nick");

        if (req.getParameter("id") != null) {
            EntryForm form = fromEntry(DAOProvider.getDAO().getBlogEntry(Long.parseLong(req.getParameter("id"))));
            form.title = title;
            form.text = text;

            return form;
        }

        return new EntryForm(title, text, author == null ? "" : author.toString());
    }

    /**
     * Creates a new instance from
     * the given {@link BlogEntry}.
     *
     * @param entry used to fill the data.
     *
     * @return a new {@code EntryForm} created from
     * the given {@link BlogEntry}.
     */
    public static EntryForm fromEntry(BlogEntry entry) {
        EntryForm ret = new EntryForm(entry.getTitle(), entry.getText(), entry.getCreator().getNick());
        ret.existingID = entry.getId();
        return ret;
    }

    @Override
    public void validate() {
        errors.clear();

        Form.checkAttribute(title, "Title", "title", 200);
        Form.checkAttribute(text, "Text", "text", 4096);
    }

    /**
     * Creates an instance of {@link BlogEntry}
     * after using {@link #validate()}.
     *
     * @return {@link BlogEntry} created from
     * current data.
     */
    public BlogEntry createEntry() {
        if (existingID != null) {
            BlogEntry entry = DAOProvider.getDAO().getBlogEntry(existingID);
            entry.setTitle(title);
            entry.setText(text);
            entry.setLastModifiedAt(new Date());

            return entry;
        }

        BlogEntry entry = new BlogEntry();
        entry.setText(text);
        entry.setTitle(title);
        entry.setCreatedAt(new Date());

        entry.setCreator(DAOProvider.getDAO().getUserByNick(creator));

        return entry;
    }

    /**
     * Provides the entry's title.
     *
     * @return entry's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Provides the entry's body.
     *
     * @return entry's body.
     */
    public String getText() {
        return text;
    }

    /**
     * Provides the post's ID if it already existed.
     *
     * @return post's ID if the post is being modified,
     * otherwise {@code null}.
     */
    public Long getExistingID() {
        return existingID;
    }
}
