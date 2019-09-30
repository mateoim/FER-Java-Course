package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A class that contains static methods
 * representing various search algorithms.
 *
 * @author Mateo Imbrišak
 */

public class SubspaceExploreUtil {

    /**
     * Breadth-First Search algorithm.
     *
     * @param s0 initial element.
     * @param process {@code Consumer} used to process accepted objects.
     * @param succ {@code Function} used to generate a {@code List} of
     *                             objects to be processed later.
     * @param acceptable {@code Predicate} used to check if an object is valid.
     * @param <S> type of objects being processed.
     */
    public static <S> void bfs(Supplier<S> s0, Consumer<S> process,
                               Function<S,List<S>> succ, Predicate<S> acceptable) {
        List<S> zaIstraziti = new LinkedList<>();
        zaIstraziti.add(s0.get());

        while (!zaIstraziti.isEmpty()) {
            S sn = zaIstraziti.remove(0);

            if (!acceptable.test(sn)) {
                continue;
            }

            process.accept(sn);
            zaIstraziti.addAll(succ.apply(sn));
        }
    }

    /**
     * Depth-First Search algorithm.
     *
     * @param s0 initial element.
     * @param process {@code Consumer} used to process accepted objects.
     * @param succ {@code Function} used to generate a {@code List} of
     *                             objects to be processed later.
     * @param acceptable {@code Predicate} used to check if an object is valid.
     * @param <S> type of objects being processed.
     */
    public static <S> void dfs( Supplier<S> s0, Consumer<S> process,
                                Function<S,List<S>> succ, Predicate<S> acceptable) {
        List<S> zaIstraziti = new LinkedList<>();
        zaIstraziti.add(s0.get());

        while (!zaIstraziti.isEmpty()) {
            S sn = zaIstraziti.remove(0);

            if (!acceptable.test(sn)) {
                continue;
            }

            process.accept(sn);
            zaIstraziti.addAll(0, succ.apply(sn));
        }
    }

    /**
     * Breadth-First Search algorithm that keeps all visited
     * objects as a {@code Set}.
     *
     * @param s0 initial element.
     * @param process {@code Consumer} used to process accepted objects.
     * @param succ {@code Function} used to generate a {@code List} of
     *                             objects to be processed later.
     * @param acceptable {@code Predicate} used to check if an object is valid.
     * @param <S> type of objects being processed.
     */
    public static <S> void bfsv( Supplier<S> s0, Consumer<S> process,
                                 Function<S,List<S>> succ, Predicate<S> acceptable) {
        List<S> zaIstraziti = new LinkedList<>();
        zaIstraziti.add(s0.get());
        Set<S> posjeceno = new HashSet<>();
        posjeceno.add(s0.get());

        while (!zaIstraziti.isEmpty()) {
            S sn = zaIstraziti.remove(0);

            if (!acceptable.test(sn)) {
                continue;
            }

            process.accept(sn);
            List<S> djeca = succ.apply(sn);
            djeca.removeAll(posjeceno);
            zaIstraziti.addAll(djeca);
            posjeceno.addAll(djeca);
        }
    }
}
