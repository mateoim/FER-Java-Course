package hr.fer.zemris.java.webserver;

/**
 * An interface used to define various web workers.
 *
 * @author Mateo Imbrišak
 */

public interface IWebWorker {

    /**
     * Used to signal to the worker to process the request.
     *
     * @param context being used.
     *
     * @throws Exception if an error occurs.
     */
    void processRequest(RequestContext context) throws Exception;
}
