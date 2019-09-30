package hr.fer.zemris.java.hw16.listeners;

import hr.fer.zemris.java.hw16.model.PictureDB;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * A {@link ServletContextListener} used to
 * initialize {@link PictureDB}.
 *
 * @author Mateo Imbrišak
 */

@WebListener
public class Initialization implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        PictureDB.initialize(sce.getServletContext().getRealPath("WEB-INF"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
