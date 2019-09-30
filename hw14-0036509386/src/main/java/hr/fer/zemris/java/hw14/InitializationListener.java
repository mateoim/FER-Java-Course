package hr.fer.zemris.java.hw14;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.Properties;

/**
 * A {@link ServletContextListener} used to
 * initialize the database tables and
 * {@link ComboPooledDataSource}.
 *
 * @author Mateo Imbrišak
 */

@WebListener
public class InitializationListener implements ServletContextListener {

    /**
     * Keeps the number of band entries to be extracted
     * form bands.properties file.
     */
    private static final int DEFAULT_NUMBER_OF_BANDS = 7;

    /**
     * Keeps the number of distro entries to be extracted
     * form distros.properties file.
     */
    private static final int DEFAULT_NUMBER_OF_DISTROS = 5;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String propertiesStringPath = sce.getServletContext().getRealPath("WEB-INF/dbsettings.properties");
        Path propertiesPath = Paths.get(propertiesStringPath);

        String connectionURL;

        try (InputStream is = Files.newInputStream(propertiesPath)) {
            Properties properties = new Properties();
            properties.load(is);

            String name = Objects.requireNonNull(properties.getProperty("title"));
            String port = Objects.requireNonNull(properties.getProperty("port"));
            String host = Objects.requireNonNull(properties.getProperty("host"));
            String user = Objects.requireNonNull(properties.getProperty("user"));
            String password = Objects.requireNonNull(properties.getProperty("password"));

            connectionURL = "jdbc:derby://" + host + ":" + port + "/" + name + ";user=" + user +";password=" + password;
        } catch (IOException | NullPointerException exc) {
            throw new RuntimeException("Error while reading properties.");
        }

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
        } catch (PropertyVetoException e1) {
            throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
        }
        cpds.setJdbcUrl(connectionURL);

        createTable(cpds,"CREATE TABLE Polls " +
                "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "title VARCHAR(150) NOT NULL, " +
                "message CLOB(2048) NOT NULL" +
                ")");
        createTable(cpds, "CREATE TABLE PollOptions " +
                "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                "optionTitle VARCHAR(100) NOT NULL, " +
                "optionLink VARCHAR(150) NOT NULL, " +
                "pollID BIGINT, " +
                "votesCount BIGINT, " +
                "FOREIGN KEY (pollID) REFERENCES Polls(id) " +
                ")");

        createPoll(cpds,
                Paths.get(sce.getServletContext().getRealPath("WEB-INF/bands.properties")),
                DEFAULT_NUMBER_OF_BANDS);
        createPoll(cpds,
                Paths.get(sce.getServletContext().getRealPath("WEB-INF/distros.properties")),
                DEFAULT_NUMBER_OF_DISTROS);

        sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComboPooledDataSource cpds = (ComboPooledDataSource)
                sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
        if (cpds != null) {
            try {
                DataSources.destroy(cpds);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Used internally to create a table with the given
     * SQL code if it doesn't already exist.
     *
     * @param cpds used to generate a {@link Connection}.
     * @param sql code used to create the table.
     *
     * @throws RuntimeException if an error occurred while
     * creating the table.
     */
    private void createTable(ComboPooledDataSource cpds, String sql) {
        try (Connection con = cpds.getConnection()) {
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.execute();
            } catch (SQLException exc) {
                checkIfTableExists(exc);
            }
        } catch (SQLException exc) {
            checkIfTableExists(exc);
        }
    }

    /**
     * Used to check if the {@link SQLException} that occurred
     * was because the already existed.
     *
     * @param exc exception that occurred.
     *
     * @throws RuntimeException if the exception
     * was not caused by table already existing.
     */
    private void checkIfTableExists(SQLException exc) {
        if(exc.getSQLState().equals("X0Y32")) {
            return;
        }
        throw new RuntimeException(exc.getMessage());
    }

    /**
     * Creates a {@code Polls} tuple based on the path to a
     * properties file if it doesn't exist and fills it with
     * tuples specified in the same file up to {@code expectedTuples}.
     *
     * @param cpds used to generate a {@link Connection}.
     * @param path to the properties file.
     * @param expectedTuples number of tuples to be generated.
     *
     * @throws RuntimeException if any of necessary properties is
     * {@code null} or an error occurs while working with the database.
     */
    private void createPoll(ComboPooledDataSource cpds, Path path, int expectedTuples) {
        try (InputStream is = Files.newInputStream(path)) {
            Properties poll = new Properties();
            poll.load(is);

            String title = poll.getProperty("title");
            String message = poll.getProperty("message");

            long key;
            OptionalLong optionalKey = getKeyIfExists(cpds, title);

            if (optionalKey.isPresent()) {
                key = optionalKey.getAsLong();
            } else {
                try (Connection con = cpds.getConnection()) {
                    try (PreparedStatement pst = con.prepareStatement(
                            "INSERT INTO Polls (title, message) VALUES(?, ?)",
                            Statement.RETURN_GENERATED_KEYS)) {
                        pst.setString(1, title);
                        pst.setString(2, message);
                        pst.executeUpdate();

                        try (ResultSet rs = pst.getGeneratedKeys()) {
                            if (rs != null && rs.next()) {
                                key = rs.getLong(1);
                            } else {
                                throw new RuntimeException("Error while getting generated key.");
                            }
                        }
                    }
                } catch (SQLException exc) {
                    throw new RuntimeException(exc);
                }
            }

            fillIfEmpty(cpds, key, poll, expectedTuples);
        } catch (IOException | NullPointerException exc) {
            throw new RuntimeException("Error while reading properties file.");
        }
    }

    /**
     * Used to get the key for a {@code Polls} tuple
     * with the given {@code title} if it exists.
     *
     * @param cpds used to generate a {@link Connection}.
     * @param title of the requested {@code Polls} tuple.
     *
     * @return an {@link OptionalLong} with value of requested key
     * if it exists, otherwise it is empty.
     *
     * @throws RuntimeException if an error occurs while working
     * with the database.
     */
    private OptionalLong getKeyIfExists(ComboPooledDataSource cpds, String title) {
        try (Connection con = cpds.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT id FROM Polls WHERE title=?")) {
                ps.setString(1, title);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return OptionalLong.of(rs.getLong(1));
                    } else {
                        return OptionalLong.empty();
                    }
                }
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    /**
     * Used to fill {@code PollOptions} with {@code key}
     * as {@code pollID}.
     *
     * @param cpds used to get a {@link Connection}.
     * @param key used as {@code pollID}.
     * @param prop used to get other attributes for the tuple.
     * @param expectedTuples number of tuples to be generated.
     *
     * @throws RuntimeException if any of necessary properties is
     * {@code null} or an error occurs while working with the database.
     */
    private void fillIfEmpty(ComboPooledDataSource cpds, long key, Properties prop, int expectedTuples) {
        try (Connection con = cpds.getConnection()) {
            try (PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM PollOptions WHERE pollID=?")) {
                pst.setLong(1, key);

                try (ResultSet resultSet = pst.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) == 0) {
                        for (int i = 0; i < expectedTuples; i++) {
                            String name = Objects.requireNonNull(prop.getProperty("optionTitle" + i));
                            String link = Objects.requireNonNull(prop.getProperty("optionLink" + i));

                            createPollOption(cpds, key, name, link);
                        }
                    }
                }
            }
        } catch (SQLException | NullPointerException exc) {
            throw new RuntimeException(exc);
        }
    }

    /**
     * Used to insert a tuple into {@code PollOptions} table.
     *
     * @param cpds used to get a {@link Connection}.
     * @param key used as {@code pollID}.
     * @param name used as {@code optionTitle}.
     * @param link used as {@code optionLink}.
     */
    private void createPollOption(ComboPooledDataSource cpds, long key, String name, String link) {
        try (Connection con = cpds.getConnection()) {
            try (PreparedStatement pst = con.prepareStatement("INSERT INTO PollOptions " +
                    "(optionTitle, optionLink, pollID, votesCount) VALUES (?, ?, ?, ?)")) {

                pst.setString(1, name);
                pst.setString(2, link);
                pst.setLong(3, key);
                pst.setInt(4, 0);
                pst.executeUpdate();
            }
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }
}
