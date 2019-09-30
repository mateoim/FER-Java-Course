package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

import java.util.List;

/**
 * An interface used to handle
 * requests for database data.
 *
 * @author Mateo Imbrišak
 */

public interface DAO {

	/**
	 * Provides the entry with the requested {@code id}.
	 *
	 * @param id of the requested {@link BlogEntry}.
	 *
	 * @return requested entry if it exists, otherwise {@code null}.
	 *
	 * @throws DAOException if an error occurs while working with the database.
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Provides a list of all {@link BlogUser}s.
	 *
	 * @return list of all {@link BlogUser}s.
	 *
	 * @throws DAOException if an error occurs while working with the database.
	 */
	List<BlogUser> getBlogUsers() throws DAOException;

	/**
	 * Checks if the given {@code nick} exists in the database.
	 *
	 * @param nick to be checked.
	 *
	 * @return {@code true} if it exists, otherwise {@code false}.
	 *
	 * @throws DAOException if an error occurs while working with the database.
	 */
	boolean checkForNick(String nick) throws DAOException;

	/**
	 * Provides the user with the given {@code nick}.
	 *
	 * @param nick of the requested {@link BlogUser}.
	 *
	 * @return {@link BlogUser} with the given {@code nick}.
	 *
	 * @throws DAOException if the requested user doesn't exist.
	 */
	BlogUser getUserByNick(String nick) throws DAOException;

	/**
	 * Adds the given user to the database.
	 *
	 * @param user to be added.
	 *
	 * @throws DAOException if an error occurs while working with the database.
	 */
	void createUser(BlogUser user) throws DAOException;

	/**
	 * Provides all {@link BlogEntry} made by the requested {@code author}.
	 *
	 * @param author whose posts will be provided.
	 *
	 * @return a list of all posts made by the requested author.
	 *
	 * @throws DAOException if an error occurs while working with the database.
	 */
	List<BlogEntry> getEntriesByAuthor(String author) throws DAOException;

	/**
	 * Adds the given {@code entry} to the database if it doesnt exist
	 * or merges it with the existing one if it has been edited.
	 *
	 * @param entry to be added.
	 *
	 * @throws DAOException if an error occurs while working with the database.
	 */
	void confirmPost(BlogEntry entry) throws DAOException;

	/**
	 * Adds the given {@code comment} to the database.
	 *
	 * @param comment to be added.
	 *
	 * @throws DAOException if an error occurs while working with the database.
	 */
	void confirmComment(BlogComment comment) throws DAOException;
}
