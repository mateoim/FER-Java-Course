package hr.fer.zemris.java.hw15.model;

import javax.persistence.*;
import java.util.List;

/**
 * A Java-bean that represents a
 * user in the database.
 *
 * @author Mateo Imbrišak
 */

@Entity
@NamedQueries({
        @NamedQuery(name = "listUsers", query = "SELECT users FROM BlogUser AS users"),
        @NamedQuery(name = "getByNick", query = "SELECT user FROM BlogUser AS user WHERE user.nick=:reqNick"),
        @NamedQuery(name = "checkForNick", query = "SELECT COUNT(user.nick) FROM BlogUser AS user WHERE user.nick=:reqNick")
})
@Table(name = "blog_users")
public class BlogUser {

    /**
     * Keeps the user's ID.
     */
    private long id;

    /**
     * Keeps the user's first name.
     */
    private String firstName;

    /**
     * Keeps the user's last name.
     */
    private String lastName;

    /**
     * Keeps the user's nickname.
     */
    private String nick;

    /**
     * Keeps the user's e-mail.
     */
    private String email;

    /**
     * Keeps the user's password as SHA-1 text.
     */
    private String passwordHash;

    /**
     * Keeps a {@link List} of {@link BlogEntry}
     * made by this user.
     */
    private List<BlogEntry> entries;

    /**
     * Provides the user's ID.
     *
     * @return user's ID.
     */
    @Id @GeneratedValue
    public long getId() {
        return id;
    }

    /**
     * Assigns the user's ID.
     *
     * @param id to be assigned.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Provides the user's first name.
     *
     * @return user's first name.
     */
    @Column(length = 20, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Assigns the user's first name.
     *
     * @param firstName to be assigned.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Provides the user's last name.
     *
     * @return user's last name.
     */
    @Column(length = 50, nullable = false)
    public String getLastName() {
        return lastName;
    }

    /**
     * Assigns the user's last name.
     *
     * @param lastName to be assigned.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Provides the user's nickname.
     *
     * @return user's nickname.
     */
    @Column(unique = true, length = 30, nullable = false)
    public String getNick() {
        return nick;
    }

    /**
     * Assigns the user's nickname.
     *
     * @param nick to be assigned.
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Provides the user's e-mail.
     *
     * @return user's e-mail.
     */
    @Column(length = 100, nullable = false)
    public String getEmail() {
        return email;
    }

    /**
     * Assigns the user's e-mail.
     *
     * @param email to be assigned.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Provides the user's password hash.
     *
     * @return user's password hash.
     */
    @Column(length = 40, nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Assigns the user's encrypted password as a
     * password hash.
     *
     * @param passwordHash to be assigned.
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Provides list of users {@link BlogEntry}.
     *
     * @return user's {@link BlogEntry}.
     */
    @OneToMany(mappedBy = "creator")
    public List<BlogEntry> getEntries() {
        return entries;
    }

    /**
     * Assigns the user's blog entries.
     *
     * @param entries to be assigned.
     */
    public void setEntries(List<BlogEntry> entries) {
        this.entries = entries;
    }
}
