package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.voting.util.PollInfo;
import hr.fer.zemris.java.hw14.voting.util.PollOptionInfo;

import java.util.List;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 *
 * @author Mateo Imbrišak
 *
 */
public interface DAO {

    /**
     * Selects all tuples in the {@code Polls} table.
     *
     * @return all polls as a {@link List}.
     */
    List<PollInfo> getPolls();

    /**
     * Selects all attributes for the poll
     * with the given {@code id}.
     *
     * @param id of the requested poll.
     *
     * @return {@link PollInfo} for the requested poll.
     */
    PollInfo getPoll(int id);

    /**
     * Selects all {@link PollOptionInfo}s where
     * {@code pollID} attribute if the given {@code pollId}.
     *
     * @param pollId used to select requested {@link PollOptionInfo}s.
     *
     * @return a {@link List} of all selected {@link PollOptionInfo}s.
     */
    List<PollOptionInfo> getPollOptions(int pollId);

    /**
     * Used to vote for a an option.
     *
     * @param id of the option.
     */
    void vote(int id);
}
