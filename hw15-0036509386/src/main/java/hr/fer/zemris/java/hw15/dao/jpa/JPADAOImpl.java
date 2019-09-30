package hr.fer.zemris.java.hw15.dao.jpa;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * An implementation of {@link DAO}
 * used in {@link hr.fer.zemris.java.hw15.dao.DAOProvider}.
 *
 * @author Mateo Imbrišak
 */

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		try {
			return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		} catch (NoResultException exc) {
			throw new DAOException(exc.getMessage());
		}
	}

	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		return JPAEMProvider.getEntityManager().createNamedQuery("listUsers", BlogUser.class).getResultList();
	}

	@Override
	public boolean checkForNick(String nick) throws DAOException {
		Long count = JPAEMProvider.getEntityManager()
				.createNamedQuery("checkForNick", Long.class)
				.setParameter("reqNick", nick).getSingleResult();

		return count == 1;
	}

	@Override
	public BlogUser getUserByNick(String nick) throws DAOException {
		try {
			return JPAEMProvider.getEntityManager()
					.createNamedQuery("getByNick", BlogUser.class)
					.setParameter("reqNick", nick).getSingleResult();
		} catch (NoResultException exc) {
			throw new DAOException(exc.getMessage());
		}
	}

	@Override
	public void createUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);
	}

	@Override
	public List<BlogEntry> getEntriesByAuthor(String author) throws DAOException {
		return JPAEMProvider.getEntityManager()
				.createNamedQuery("getEntriesByAuthor", BlogEntry.class)
				.setParameter("reqNick", author).getResultList();
	}

	@Override
	public void confirmPost(BlogEntry entry) throws DAOException {
		if (entry.getId() == null) {
			JPAEMProvider.getEntityManager().persist(entry);
		} else {
			JPAEMProvider.getEntityManager().merge(entry);
		}
	}

	@Override
	public void confirmComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);
	}
}
