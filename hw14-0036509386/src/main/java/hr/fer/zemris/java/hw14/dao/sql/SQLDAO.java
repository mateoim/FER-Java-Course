package hr.fer.zemris.java.hw14.dao.sql;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.voting.util.PollInfo;
import hr.fer.zemris.java.hw14.voting.util.PollOptionInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *
 * @author Mateo Imbrišak
 */
public class SQLDAO implements DAO {

    @Override
    public List<PollInfo> getPolls() {
        List<PollInfo> ret = new LinkedList<>();

        Connection con = SQLConnectionProvider.getConnection();

        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM polls")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String title = rs.getString(2);
                    String message = rs.getString(3);

                    ret.add(new PollInfo(id, title, message));
                }
            }
        } catch (SQLException exc) {
            throw new DAOException(exc);
        }

        return ret;
    }

    @Override
    public PollInfo getPoll(int id) {
        Connection con = SQLConnectionProvider.getConnection();

        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM polls WHERE id=?")) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString(2);
                    String message = rs.getString(3);

                    return new PollInfo(id, title, message);
                }

                return null;
            }
        } catch (SQLException exc) {
            throw new DAOException(exc);
        }
    }

    @Override
    public List<PollOptionInfo> getPollOptions(int pollId) {
        List<PollOptionInfo> ret = new LinkedList<>();

        Connection con = SQLConnectionProvider.getConnection();

        try (PreparedStatement ps = con.prepareStatement("SELECT id, optionTitle, optionLink, votesCount" +
                " FROM PollOptions WHERE pollID=?")) {
            ps.setInt(1, pollId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String title = rs.getString(2);
                    String link = rs.getString(3);
                    int numberOfVotes = rs.getInt(4);

                    ret.add(new PollOptionInfo(id, title, link, numberOfVotes));
                }
            }
        } catch (SQLException exc) {
            throw new DAOException(exc);
        }

        return ret;
    }

    @Override
    public void vote(int id) {
        Connection con = SQLConnectionProvider.getConnection();
        int numberOfVotes;

        try (PreparedStatement ps = con.prepareStatement("SELECT votesCount FROM PollOptions WHERE id=(?)")) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    numberOfVotes = rs.getInt(1);
                } else {
                    numberOfVotes = 0;
                }
            }
        } catch (SQLException exc) {
            throw new DAOException(exc);
        }

        try (PreparedStatement ps = con.prepareStatement("UPDATE PollOptions SET votesCount = (?) WHERE id=(?)")) {
            ps.setInt(1, numberOfVotes + 1);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException exc) {
            throw new DAOException(exc);
        }
    }
}
