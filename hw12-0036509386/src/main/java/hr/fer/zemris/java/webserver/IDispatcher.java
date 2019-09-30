package hr.fer.zemris.java.webserver;

/**
 * An interface that models a dispatcher
 * used to dispatch web requests.
 *
 * @author Mateo Imbrišak
 */

public interface IDispatcher {

    /**
     * Dispatches the request for the given path.
     *
     * @param urlPath to the requested location.
     *
     * @throws Exception if an error occurs.
     */
    void dispatchRequest(String urlPath) throws Exception;
}
