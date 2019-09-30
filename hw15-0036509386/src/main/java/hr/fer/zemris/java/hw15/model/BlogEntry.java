package hr.fer.zemris.java.hw15.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A Java-bean that represents
 * a blog entry in the database.
 *
 * @author marcupic
 */

@NamedQueries({
		@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when"),
		@NamedQuery(name = "getEntriesByAuthor", query = "SELECT b FROM BlogEntry as b WHERE b.creator.nick=:reqNick")
})
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * Keeps the entry's ID.
	 */
	private Long id;

	/**
	 * Keeps the entry's comments.
	 */
	private List<BlogComment> comments = new ArrayList<>();

	/**
	 * Keeps the entry's date of creation.
	 */
	private Date createdAt;

	/**
	 * Keeps the entry's last edit timestamp.
	 */
	private Date lastModifiedAt;

	/**
	 * Keeps the entry's title.
	 */
	private String title;

	/**
	 * Keeps the entry's body.
	 */
	private String text;

	/**
	 * Keeps the entry's author.
	 */
	private BlogUser creator;

	/**
	 * Provides the post's ID.
	 *
	 * @return post's ID.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Assigns the post's ID.
	 *
	 * @param id to be assigned.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Provides the post's comments.
	 *
	 * @return post's comments.
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Assigns the post's comments.
	 *
	 * @param comments to be assigned.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Provides the post's date of creation.
	 *
	 * @return post's date of creation.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Assigns the post's date of creation..
	 *
	 * @param createdAt to be assigned.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Provides the post's last edit timestamp.
	 *
	 * @return post's last edit timestamp.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Assigns the post's last edit timestamp.
	 *
	 * @param lastModifiedAt to be assigned.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Provides the post's title.
	 *
	 * @return post's title.
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}

	/**
	 * Assigns the post's title.
	 *
	 * @param title to be assigned.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Provides the post's body.
	 *
	 * @return post's body.
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}

	/**
	 * Assigns the post's body.
	 *
	 * @param text to be assigned.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Provides the post's author.
	 *
	 * @return post's author.
	 */
	@ManyToOne
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Assigns the post's author.
	 *
	 * @param creator to be assigned.
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
