package hr.fer.zemris.java.servlets.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * A {@code ServletContextListener} used to
 * track time since server boot.
 *
 * @author Mateo Imbrišak
 */

@WebListener
public class LifeCycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        long creationTime = System.currentTimeMillis();
        servletContextEvent.getServletContext().setAttribute("creationTime", creationTime);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
