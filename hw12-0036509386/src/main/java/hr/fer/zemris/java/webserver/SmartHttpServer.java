package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A class that represents a simple HTTP server.
 *
 * @author Mateo Imbrišak
 */

public class SmartHttpServer {

    /**
     * Keeps the server address.
     */
    private String address;

    /**
     * Keeps the domain name.
     */
    private String domainName;

    /**
     * Keeps the current port.
     */
    private int port;

    /**
     * Keeps hte number of worker threads.
     */
    private int workerThreads;

    /**
     * Keeps the time until session timeout.
     */
    private int sessionTimeout;

    /**
     * Keeps the pairs of mime types.
     */
    private Map<String, String> mimeTypes = new HashMap<>();

    /**
     * Keeps the {@link ServerThread}.
     */
    private ServerThread serverThread;

    /**
     * Keeps the thread pool.
     */
    private ExecutorService threadPool;

    /**
     * Keeps the path to document root.
     */
    private Path documentRoot;

    /**
     * Keeps the {@link IWebWorker}s.
     */
    private Map<String,IWebWorker> workersMap = new HashMap<>();

    /**
     * Keeps active sessions and their IDs.
     */
    private Map<String, SessionMapEntry> sessions = new HashMap<>();

    /**
     * Used to generate session IDs.
     */
    private Random sessionRandom = new Random();

    /**
     * A {@code Thread} used to cleanup inactive cookies.
     */
    private Thread cleanup = new Thread(() -> {
        Set<String> toRemove = new HashSet<>();

        while (serverThread.running) {
            sessions.forEach((key, value) -> {
                if (value.validUntil < System.currentTimeMillis() / TO_SECONDS) {
                    toRemove.add(key);
                }
            });
            toRemove.forEach((current) -> sessions.remove(current));
            toRemove.clear();
            try {
                Thread.sleep(300 * TO_SECONDS);
            } catch (InterruptedException ignored) {}
        }
    });

    /**
     * Used to convert time generated from
     * {@link System#currentTimeMillis()} to seconds.
     */
    private static final int TO_SECONDS = 1_000;

    /**
     * Default constructor that assigns values from a config file.
     *
     * @param configFileName path to the config file.
     */
    public SmartHttpServer(String configFileName) {
        Path src = Paths.get(configFileName);

        try (InputStream is = new BufferedInputStream(Files.newInputStream(src))) {
            Properties properties = new Properties();
            properties.load(is);

            address = properties.getProperty("server.address");
            domainName = properties.getProperty("server.domainName");
            port = Integer.parseInt(properties.getProperty("server.port"));
            workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
            sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
            documentRoot = Paths.get(".").resolve(Paths.get(properties.getProperty("server.documentRoot")));

            Path mimes = Paths.get(".").resolve(Paths.get(properties.getProperty("server.mimeConfig")));

            List<String> mimeTypesList = Files.readAllLines(mimes);

            mimeTypesList.forEach((current) -> {
                String[] parts = current.split("=");
                mimeTypes.put(parts[0].strip(), parts[1].strip());
            });

            Path workers = Paths.get(".").resolve(Paths.get(properties.getProperty("server.workers")));
            List<String> workersList = Files.readAllLines(workers);

            workersList.forEach((current) -> {
                String[] parts = current.split("=");

                String path = parts[0].trim();
                String fqcn = parts[1].trim();

                if (workersMap.containsKey(path)) {
                    throw new RuntimeException("Worker with this path already exists.");
                }

                Object newObject = null;
                try {
                    Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
                    newObject = referenceToClass.newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException exc) {
                    exc.printStackTrace();
                }

                IWebWorker iww = (IWebWorker) newObject;

                workersMap.put(path, iww);
            });
        } catch (IOException exc) {
            exc.printStackTrace();
            System.out.println("Error while reading configurations.");
        }

        serverThread = new ServerThread();
    }

    /**
     * Used to signal to the server to start running.
     */
    protected synchronized void start() {
        if (!serverThread.running) {
            serverThread.start();
            cleanup.setDaemon(true);
            cleanup.start();
        }

        threadPool = Executors.newFixedThreadPool(workerThreads);
    }

    /**
     * Used to signal to the server to stop running.
     */
    protected synchronized void stop() {
        serverThread.running = false;
    }

    /**
     * A {@link Thread} used to process client requests.
     */
    protected class ServerThread extends Thread {

        /**
         * Keeps track of whether this thread is running.
         */
        private volatile boolean running;

        @Override
        public void run() {
            running = true;

            try (ServerSocket serverSocket = new ServerSocket()) {
                serverSocket.bind(new InetSocketAddress((InetAddress) null, port));
                serverSocket.setSoTimeout(5 * TO_SECONDS);

                while (running) {
                    try {
                        Socket client = serverSocket.accept();
                        ClientWorker cw = new ClientWorker(client);

                        threadPool.submit(cw);
                    } catch (SocketTimeoutException ignored) {}
                }

                threadPool.shutdown();
            } catch (IOException exc) {
                System.out.println("Error occurred while using the server socket.");
            }
        }
    }

    /**
     * A worker used to serve the client.
     */
    private class ClientWorker implements Runnable, IDispatcher {

        /**
         * Keeps the client socket.
         */
        private Socket csocket;

        /**
         * Keeps the input stream.
         */
        private PushbackInputStream istream;

        /**
         * Keeps the output stream.
         */
        private OutputStream ostream;

        /**
         * Keeps the HTTP version.
         */
        private String version;

        /**
         * Keeps the request method.
         */
        private String method;

        /**
         * Keeps the host name.
         */
        private String host;

        /**
         * Keeps the parameters.
         */
        private Map<String, String> params = new HashMap<>();

        /**
         * Keeps the temporary parameters.
         */
        private Map<String, String> tempParams = new HashMap<>();

        /**
         * Keeps the permanent parameters.
         */
        private Map<String, String> permPrams = new HashMap<>();

        /**
         * Keeps the cookies.
         */
        private List<RequestContext.RCCookie> outputCookies = new ArrayList<>();

        /**
         * Keeps the session identification.
         */
        private String SID;

        /**
         * Keeps the context.
         */
        private RequestContext context = null;

        /**
         * Keeps the package of {@link IWebWorker}s.
         */
        private static final String PATH_TO_WORKERS = "hr.fer.zemris.java.webserver.workers.";

        /**
         * Default constructor that assigns the client socket.
         *
         * @param csocket client socket.
         */
        public ClientWorker(Socket csocket) {
            super();
            this.csocket = csocket;
            this.host = null;
        }

        /**
         * Used to analyze and process the request.
         *
         * @param urlPath path to the requested location.
         * @param directCall whether the call was direct.
         *
         * @throws Exception if an error occurs.
         */
        public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
            Path path = documentRoot.resolve(urlPath);

            if (!path.toString().startsWith(documentRoot.toString())) {
                sendError(403, "Trying to access forbidden location.");
                return;
            }

            if (directCall && path.startsWith(documentRoot.resolve("private"))) {
                sendError(404, "Cannot access private.");
                return;
            }

            String workerPath = path.toString().substring(path.toString().lastIndexOf("/"));
            IWebWorker worker = workersMap.get(workerPath);

            if (urlPath.startsWith("ext/")) {
                String name = urlPath.substring(urlPath.lastIndexOf("/") + 1);
                worker = generateWorker(name);
            }

            if (worker == null) {
                if (!Files.exists(path) || !Files.isRegularFile(path) ||
                        !Files.isReadable(path)) {
                    sendError(404, "File not found.");
                    return;
                }
            }

            String extension = path.toString().substring(path.toString().lastIndexOf(".") + 1);

            String mimeType = mimeTypes.get(extension);
            mimeType = mimeType == null ? "application/octet-stream" : mimeType;

            context = context == null ? new RequestContext(ostream, params, permPrams, outputCookies,
                    tempParams, this, SID) : context;
            context.setMimeType(mimeType);
            context.setStatusCode(200);

            if (extension.equals("smscr")) {
                processSmartScript(path, context);
                return;
            }

            if (worker != null) {
                worker.processRequest(context);
                closeProtocol();
                return;
            }

            returnFile(context, path);
        }

        /**
         * Used to dispatch the request publicly.
         *
         * @param urlPath to the requested location.
         *
         * @throws Exception if an error occurs.
         */
        public void dispatchRequest(String urlPath) throws Exception {
            internalDispatchRequest(urlPath, false);
        }


        @Override
        public void run() {
            try {
                istream = new PushbackInputStream(csocket.getInputStream());
                ostream = csocket.getOutputStream();

                byte[] request = readRequest();

                if (request == null) {
                    sendError(400, "Bad request.");
                    closeProtocol();
                    return;
                }

                String requestedString = new String(request, StandardCharsets.US_ASCII);
                List<String> headers = extractHeaders(requestedString);

                String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");

                if (firstLine == null || firstLine.length != 3) {
                    sendError(400, "Bad request");
                    closeProtocol();
                    return;
                }

                method = firstLine[0].toUpperCase();
                version = firstLine[2].toUpperCase();

                if (!method.equals("GET") || !(version.equals("HTTP/1.1") || version.equals("HTTP/1.0"))) {
                    sendError(400, "Invalid version or method.");
                    closeProtocol();
                    return;
                }

                for (String header : headers) {
                    if (header.startsWith("Host: ")) {
                        String[] parts = header.split(":");
                        host = parts[1].trim();
                        break;
                    }
                }

                host = host == null ? domainName : host;

                checkSession(headers);

                String[] pathAndParam = firstLine[1].split("\\?");
                String path = pathAndParam[0];
                String paramString = pathAndParam.length == 1 ? "" : pathAndParam[1];

                if (!paramString.isEmpty()) {
                    parseParams(paramString);
                }

                internalDispatchRequest(path.substring(1), true);

                closeProtocol();
            } catch (Exception exc) {
                System.out.println("Error while working with the socket.");
            }
        }

        /**
         * Used to check the session for cookies.
         *
         * @param headers extracted from the session.
         */
        private synchronized void checkSession(List<String> headers) {
            String sidCandidate = null;

            for (String header : headers) {
                if (header.startsWith("Cookie:")) {
                    String[] cookies = header.split(";");
                    for (String cookie : cookies) {
                        String[] parts = cookie.split("=");

                        if (parts[0].trim().endsWith("sid")) {
                            sidCandidate = parts[1].trim();
                        }
                    }
                }
            }

            if (sidCandidate != null) {
                sidCandidate = sidCandidate.substring(1, sidCandidate.length() - 1);
                SessionMapEntry session = sessions.get(sidCandidate);
                if (session != null && session.host.equals(host)) {
                    if (session.validUntil < System.currentTimeMillis() / TO_SECONDS) {
                        sessions.remove(sidCandidate);
                    } else {
                        permPrams = session.map;
                        session.validUntil = System.currentTimeMillis() / TO_SECONDS + sessionTimeout;
                        return;
                    }
                }
            }

            StringBuilder generateSid = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                generateSid.append((char) (sessionRandom.nextInt(26) + 'A'));
            }
            String newSid = generateSid.toString();

            SessionMapEntry entry = new SessionMapEntry();
            entry.sid = newSid;
            entry.validUntil = (System.currentTimeMillis() / TO_SECONDS) + sessionTimeout;
            entry.host = host;

            permPrams = entry.map;

            sessions.put(newSid, entry);
            outputCookies.add(new RequestContext.RCCookie("sid", newSid,
                    null, host, "/"));
            SID = newSid;
        }

        /**
         * Used to serve the client if the requested file is a Smart Script.
         *
         * @param script path to the script.
         * @param rc {@link RequestContext} being used.
         *
         * @throws IOException if an error occurs while reading the script.
         */
        private void processSmartScript(Path script, RequestContext rc) throws IOException {
            String input = Files.readString(script);
            SmartScriptParser parser = new SmartScriptParser(input);

            SmartScriptEngine engine = new SmartScriptEngine(parser.getDocumentNode(), rc);
            engine.execute();
        }

        /**
         * Creates an instance of {@link IWebWorker}
         * with the given {@code name}.
         *
         * @param name of the {@link IWebWorker}.
         *
         * @return generated {@link IWebWorker}.
         *
         * @throws IOException if an error occurs while
         * sending an error to the client.
         */
        private IWebWorker generateWorker(String name) throws IOException {
            Object newObject = null;
            try {
                Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(PATH_TO_WORKERS + name);
                newObject = referenceToClass.newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException exc) {
                sendError(400, "Worker does not exist.");
            }

            return (IWebWorker) newObject;
        }

        /**
         * Used internally to read the request.
         *
         * @return client's request as an array of {@code byte}s.
         *
         * @throws IOException if an error occurs while writing to the
         * output stream.
         */
        private byte[] readRequest() throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int state = 0;
            l:
            while (true) {
                int b = istream.read();
                if (b == -1) return null;
                if (b != 13) {
                    bos.write(b);
                }
                switch (state) {
                    case 0:
                        if (b == 13) {
                            state = 1;
                        } else if (b == 10) state = 4;
                        break;
                    case 1:
                        if (b == 10) {
                            state = 2;
                        } else state = 0;
                        break;
                    case 2:
                        if (b == 13) {
                            state = 3;
                        } else state = 0;
                        break;
                    case 3:
                    case 4:
                        if (b == 10) {
                            break l;
                        } else state = 0;
                        break;
                }
            }
            return bos.toByteArray();
        }

        /**
         * Used internally to send an error to the client.
         *
         * @param statusCode error's code.
         * @param statusText message to be sent.
         *
         * @throws IOException if an error occurs while
         * writing the message.
         */
        private void sendError(int statusCode, String statusText) throws IOException {
            ostream.write(
                    ("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
                            "Server: simple java server\r\n" +
                            "Content-Type: text/plain;charset=UTF-8\r\n" +
                            "Content-Length: 0\r\n" +
                            "Connection: close\r\n" +
                            "\r\n").getBytes(StandardCharsets.US_ASCII)
            );
            ostream.flush();
        }

        /**
         * Used internally to extract the headers from input.
         *
         * @param requestHeader request generated by the client.
         *
         * @return a {@code List} of headers.
         */
        private List<String> extractHeaders(String requestHeader) {
            List<String> headers = new ArrayList<>();
            StringBuilder currentLine = new StringBuilder();

            for(String s : requestHeader.split("\n")) {
                if (s.isEmpty()) break;

                char c = s.charAt(0);

                if (c == 9 || c == 32) {
                    currentLine.append(s);
                } else {
                    if (currentLine.length() != 0) {
                        headers.add(currentLine.toString());
                    }
                    currentLine = new StringBuilder(s);
                }
            }

            if (currentLine.length() != 0) {
                headers.add(currentLine.toString());
            }

            return headers;
        }

        /**
         * Used internally to parse the parameters.
         *
         * @param paramString parameters as a single {@code String}.
         */
        private void parseParams(String paramString) {
            String[] parts = paramString.split("&");

            for (String param : parts) {
                String[] split = param.split("=");

                if (split.length == 2) {
                    params.put(split[0], split[1]);
                } else {
                    params.put(split[0], "");
                }
            }
        }

        /**
         * Used to provide the requested file to the client.
         *
         * @param rc client's context.
         * @param requestedFile path to the file.
         *
         * @throws IOException if an error occurs while writing
         * the requested file.
         */
        private void returnFile(RequestContext rc, Path requestedFile) throws IOException {
            try (InputStream fis = new BufferedInputStream(Files.newInputStream(requestedFile))) {
                byte[] buf = new byte[1024];

                while (true) {
                    int r = fis.read(buf);
                    if (r < 1) break;
                    rc.write(buf, 0, r);
                }
            }
        }

        /**
         * Used internally to close the client socket
         * and flush the {@link #ostream}.
         *
         * @throws IOException if an error occurs while
         * working with the socket.
         */
        private void closeProtocol() throws IOException {
            ostream.flush();
            csocket.close();
        }
    }

    /**
     * An auxiliary class that represents a session.
     */
    private static class SessionMapEntry {

        /**
         * Keeps the session ID.
         */
        String sid;

        /**
         * Keeps the host name.
         */
        String host;

        /**
         * Keeps the point in time when this
         * session stops being valid.
         */
        long validUntil;

        /**
         * Keeps the parameters.
         */
        Map<String, String> map = new ConcurrentHashMap<>();
    }

    /**
     * Used to start the server.
     *
     * @param args path to the configuration file.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments. Terminating...");
            return;
        }

        SmartHttpServer server = new SmartHttpServer(args[0]);
        server.start();

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Type \"stop\" to terminate.");
            while (true) {
                String input = sc.nextLine().trim();

                if (input.equalsIgnoreCase("stop")) {
                    break;
                }
            }
        }

        System.out.println("This may take up to 5 seconds...");
        server.stop();
    }
}
