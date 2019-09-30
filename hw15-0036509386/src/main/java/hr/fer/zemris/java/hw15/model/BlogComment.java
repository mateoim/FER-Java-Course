package hr.fer.zemris.java.hw15.model;

import javax.persistence.*;
import java.util.Date;

/**
 * A Java-bean that represents
 * a comment in the database.
 *
 * @author marcupic
 */

@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * Keeps the comment's ID.
	 */
	private Long id;

	/**
	 * Keeps the parent post.
	 */
	private BlogEntry blogEntry;

	/**
	 * Keeps the poster's e-mail.
	 */
	private String usersEMail;

	/**
	 * Keeps the comment's body.
	 */
	private String message;

	/**
	 * Keeps the comment's date of creation.
	 */
	private Date postedOn;

	/**
	 * Provides the comment's ID.
	 *
	 * @return comment's ID.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Assigns the comment's ID.
	 *
	 * @param id to be assigned.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Provides the comment's parent post.
	 *
	 * @return comment's parent post.
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Assigns the comment's parent post.
	 *
	 * @param blogEntry to be assigned.
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Provides the poster's e-mail.
	 *
	 * @return poster's e-mail.
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Assigns the poster's e-mail.
	 *
	 * @param usersEMail to be assigned.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Provides the comment's body.
	 *
	 * @return comment's body.
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 * Assigns the comment's body.
	 *
	 * @param message to be assigned.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Provides the comment's date of creation.
	 *
	 * @return comment's date of creation.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Assigns the comment's date of creation.
	 *
	 * @param postedOn to be assigned.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
