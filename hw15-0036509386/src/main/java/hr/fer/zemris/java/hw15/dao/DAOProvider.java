package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * A singleton class used to provide {@link DAO}.
 *
 * @author marcupic
 */

public class DAOProvider {

	/**
	 * Keeps the instance of {@link DAO}.
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Provides the active {@link DAO}.
	 *
	 * @return active {@link DAO}.
	 */
	public static DAO getDAO() {
		return dao;
	}

}
