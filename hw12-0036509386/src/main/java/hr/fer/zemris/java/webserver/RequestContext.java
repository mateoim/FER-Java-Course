package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;

/**
 * A class that provides context for requests.
 *
 * @author Mateo Imbrišak
 */

public class RequestContext {

    /**
     * Keeps the output stream.
     */
    private OutputStream outputStream;

    /**
     * Keeps the charset.
     */
    private Charset charset;

    /**
     * Keeps the current encoding.
     */
    private String encoding;

    /**
     * Keeps the current status code.
     */
    private int statusCode;

    /**
     * Keeps the current status text..
     */
    private String statusText;

    /**
     * Keeps the current mime type.
     */
    private String mimeType;

    /**
     * Keeps the current content length.
     */
    private Long contentLength;

    /**
     * Keeps the parameters.
     */
    private final Map<String,String> parameters;

    /**
     * Keeps the temporary parameters.
     */
    private Map<String,String> temporaryParameters;

    /**
     * Keeps the persistent parameters.
     */
    private Map<String,String> persistentParameters;

    /**
     * Keeps the output cookies.
     */
    private List<RCCookie> outputCookies;

    /**
     * Keeps the dispatcher used for this request.
     */
    private IDispatcher dispatcher;

    /**
     * Keeps the session ID.
     */
    private String SID;

    /**
     * Used to check if the header has been written.
     */
    private boolean first;

    /**
     * Constructor that assigns {@code outputStream},
     * unmodifiable {@code Map} {@code parameters}, {@code persistentParameters},
     * {@code outputCookies} and initializes all other values.
     *
     * @param outputStream to be assigned.
     * @param parameters to be assigned.
     * @param persistentParameters to be assigned.
     * @param outputCookies to be assigned.
     *
     * @throws NullPointerException if {@code outputStream} is {@code null}.
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters,
                          Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
        this(outputStream, parameters, persistentParameters, outputCookies,
                null, null, null);
    }

    /**
     * Default constructor that assigns all values.
     *
     * @param outputStream to be assigned.
     * @param parameters to be assigned.
     * @param persistentParameters to be assigned.
     * @param outputCookies to be assigned.
     * @param temporaryParameters to be assigned.
     * @param dispatcher to be assigned.
     * @param SID session ID to be assigned.
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters,
                          Map<String, String> persistentParameters, List<RCCookie> outputCookies,
                          Map<String,String> temporaryParameters, IDispatcher dispatcher, String SID) {
        Objects.requireNonNull(outputStream);

        this.outputStream = outputStream;
        this.parameters = Collections.unmodifiableMap(parameters == null ? new HashMap<>() : parameters);
        this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
        this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
        this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
        this.dispatcher = dispatcher;
        this.SID = SID;

        encoding = "UTF-8";
        statusCode = 200;
        statusText = "OK";
        mimeType = "text/html";
        contentLength = null;
        first = true;
    }

    /**
     * Provides the parameter for the given name.
     *
     * @param name used to look for the parameter.
     *
     * @return parameter under the given name.
     */
    public String getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * Provides a {@code Set} of all names of parameters.
     *
     * @return an unmodifiable {@code Set} of parameter names.
     */
    public Set<String> getParameterNames() {
        return Collections.unmodifiableSet(parameters.keySet());
    }

    /**
     * Provides the persistent parameter for the given name.
     *
     * @param name used to look for the parameter.
     *
     * @return persistent parameter under the given name.
     */
    public String getPersistentParameter(String name) {
        return persistentParameters.get(name);
    }

    /**
     * Provides a {@code Set} of all names of persistent parameters.
     *
     * @return an unmodifiable {@code Set} of persistent parameter names.
     */
    public Set<String> getPersistentParameterNames() {
        return Collections.unmodifiableSet(persistentParameters.keySet());
    }

    /**
     * Adds the given pair to {@link #persistentParameters}.
     *
     * @param name key to be added.
     * @param value value linked to the key.
     */
    public void setPersistentParameter(String name, String value) {
        persistentParameters.put(name, value);
    }

    /**
     * Removes the pair under the given name from {@link #persistentParameters}.
     *
     * @param name key of the pair to be removed.
     */
    public void removePersistentParameter(String name) {
        persistentParameters.remove(name);
    }

    /**
     * Provides the temporary parameter for the given name.
     *
     * @param name used to look for the parameter.
     *
     * @return temporary parameter under the given name.
     */
    public String getTemporaryParameter(String name) {
        return temporaryParameters.get(name);
    }

    /**
     * Provides a {@code Set} of all names of temporary parameters.
     *
     * @return an unmodifiable {@code Set} of temporary parameter names.
     */
    public Set<String> getTemporaryParameterNames() {
        return Collections.unmodifiableSet(temporaryParameters.keySet());
    }

    /**
     * Provides the session ID.
     *
     * @return session ID.
     */
    public String getSessionID() {
        return SID;
    }

    /**
     * Adds the given pair to {@link #temporaryParameters}.
     *
     * @param name key to be added.
     * @param value value linked to the key.
     */
    public void setTemporaryParameter(String name, String value) {
        temporaryParameters.put(name, value);
    }

    /**
     * Removes the pair under the given name from {@link #temporaryParameters}.
     *
     * @param name key of the pair to be removed.
     */
    public void removeTemporaryParameter(String name) {
        temporaryParameters.remove(name);
    }

    /**
     * Writes the given data to {@link #outputStream}.
     * If the header hasn't been written, first writes the header.
     *
     * @param data to be written.
     *
     * @return a reference to this {@code RequestContext}.
     *
     * @throws IOException if an error while writing occurs.
     */
    public RequestContext write(byte[] data) throws IOException {
        return write(data, 0, data.length);
    }

    /**
     * Writes the given data to {@link #outputStream}.
     * If the header hasn't been written, first writes the header.
     *
     * @param data to be written.
     * @param offset from which to begin writing.
     * @param len to be written.
     *
     * @return a reference to this {@code RequestContext}.
     *
     * @throws IOException if an error while writing occurs.
     */
    public RequestContext write(byte[] data, int offset, int len) throws IOException {
        if (first) {
            writeHeader();
        }

        outputStream.write(data, offset, len);
        return this;
    }

    /**
     * Writes the given data to {@link #outputStream}.
     * If the header hasn't been written, first writes the header.
     *
     * @param text to be written.
     *
     * @return a reference to this {@code RequestContext}.
     *
     * @throws IOException if an error while writing occurs.
     */
    public RequestContext write(String text) throws IOException {
        if (first) {
            writeHeader();
        }

        return write(text.getBytes(charset));
    }

    /**
     * Adds the given {@code RCCookie} th the {@link #outputCookies}.
     *
     * @param cookie to be added.
     */
    public void addRCCookie(RCCookie cookie) {
        outputCookies.add(cookie);
    }

    /**
     * Provides the {@link #dispatcher}.
     *
     * @return this context's dispatcher.
     */
    public IDispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     * Sets the given encoding.
     *
     * @param encoding to be assigned.
     *
     * @throws RuntimeException if the header has been written.
     */
    public void setEncoding(String encoding) {
        checkIfFirst();

        this.encoding = encoding;
    }

    /**
     * Sets the given status code.
     *
     * @param statusCode to be assigned.
     *
     * @throws RuntimeException if the header has been written.
     */
    public void setStatusCode(int statusCode) {
        checkIfFirst();

        this.statusCode = statusCode;
    }

    /**
     * Sets the given status text.
     *
     * @param statusText to be assigned.
     *
     * @throws RuntimeException if the header has been written.
     */
    public void setStatusText(String statusText) {
        checkIfFirst();

        this.statusText = statusText;
    }

    /**
     * Sets the given mime type.
     *
     * @param mimeType to be assigned.
     *
     * @throws RuntimeException if the header has been written.
     */
    public void setMimeType(String mimeType) {
        checkIfFirst();

        this.mimeType = mimeType;
    }

    /**
     * Sets the given content length.
     *
     * @param contentLength to be assigned.
     *
     * @throws RuntimeException if the header has been written.
     */
    public void setContentLength(Long contentLength) {
        checkIfFirst();

        this.contentLength = contentLength;
    }

    /**
     * Used to write the header.
     *
     * @throws IOException if an error occurs while writing.
     */
    private void writeHeader() throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append("\r\n");
        sb.append("Content-Type: ").append(mimeType).append(mimeType.startsWith("text/") ? "; charset=" + encoding :
                "").append("\r\n");
        sb.append(contentLength == null ? "" : "Content-Length: " + contentLength + "\r\n");

        outputCookies.forEach((rcCookie -> {
            sb.append("Set-Cookie: ").append(rcCookie.getName()).append("=\"").append(rcCookie.getValue()).append("\"");
            sb.append(rcCookie.getDomain() == null ? "" : "; Domain=" + rcCookie.getDomain());
            sb.append(rcCookie.getPath() == null ? "" : "; Path=" + rcCookie.getPath());
            sb.append(rcCookie.getMaxAge() == null ? "" : "; Max-Age=" + rcCookie.getMaxAge());
            sb.append("\r\n");
        }));

        sb.append("\r\n");

        outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));

        try {
            charset = Charset.forName(encoding);
        } catch (UnsupportedCharsetException exc) {
            System.out.println("Invalid charset.");
        }

        first = false;
    }

    /**
     * Used internally to check if the header has been written.
     *
     * @throws RuntimeException if the header has been written.
     */
    private void checkIfFirst() {
        if (!first) {
            throw new RuntimeException("Header has already been written.");
        }
    }

    /**
     * A class that represents a cookie
     * for {@link RequestContext}.
     */
    public static class RCCookie {

        /**
         * Keeps the name.
         */
        private String name;

        /**
         * Keeps the value.
         */
        private String value;

        /**
         * Keeps the domain name.
         */
        private String domain;

        /**
         * Keeps the path.
         */
        private String path;

        /**
         * Keeps the maximum age.
         */
        private Integer maxAge;

        /**
         * Default constructor that assigns all values.
         *
         * @param name to be assigned.
         * @param value to be assigned.
         * @param domain to be assigned.
         * @param path to be assigned.
         * @param maxAge to be assigned.
         */
        public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
            this.name = name;
            this.value = value;
            this.domain = domain;
            this.path = path;
            this.maxAge = maxAge;
        }

        /**
         * Provides the cookie's name.
         *
         * @return cookie's name.
         */
        public String getName() {
            return name;
        }

        /**
         * Provides the cookie's value.
         *
         * @return cookie's value.
         */
        public String getValue() {
            return value;
        }

        /**
         * Provides the domain name.
         *
         * @return domain name.
         */
        public String getDomain() {
            return domain;
        }

        /**
         * Provides the path.
         *
         * @return path.
         */
        public String getPath() {
            return path;
        }

        /**
         * Provides the maximum age.
         *
         * @return maximum age.
         */
        public Integer getMaxAge() {
            return maxAge;
        }
    }
}
