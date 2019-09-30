package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * A Singleton class that provides the
 * {@link EntityManagerFactory}.
 *
 * @author marcupic
 */

public class JPAEMFProvider {

	/**
	 * Keeps the instance.
	 */
	public static EntityManagerFactory emf;

	/**
	 * Provides the {@link #emf}.
	 *
	 * @return {@link #emf}.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Assigns a new {@link #emf}.
	 *
	 * @param emf to be assigned.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}
