package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A class containing search algorithms
 * used to solve the puzzle.
 *
 * @author Mateo Imbrišak
 */

public class SearchUtil {

    /**
     * Breadth-First Search algorithm.
     *
     * @param s0 initial element.
     * @param succ {@code Function} used to generate a {@code List} of
     *                             objects to be processed later.
     * @param goal {@code Predicate} used to check if the end
     *                              has been reached.
     * @param <S> type of objects being processed.
     */
    public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
        List<Node<S>> toCheck = new LinkedList<>();
        toCheck.add(new Node<>(null, s0.get(), 0));

        while (!toCheck.isEmpty()) {
            Node<S> ni = toCheck.remove(0);

            if (goal.test(ni.getState())) {
                return ni;
            }

            for (Transition<S> element : succ.apply(ni.getState())) {
                toCheck.add(new Node<>(ni, element.getState(), ni.getCost() + element.getCost()));
            }
        }

        return null;
    }

    /**
     * Breadth-First Search algorithm that keeps all visited
     * objects as a {@code Set}.
     *
     * @param s0 initial element.
     * @param succ {@code Function} used to generate a {@code List} of
     *                             objects to be processed later.
     * @param goal {@code Predicate} used to check if the end
     *                              has been reached.
     * @param <S> type of objects being processed.
     */
    public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
        List<Node<S>> toCheck = new LinkedList<>();
        toCheck.add(new Node<>(null, s0.get(), 0));
        Set<S> checked = new HashSet<>();
        checked.add(s0.get());

        while (!toCheck.isEmpty()) {
            Node<S> ni = toCheck.remove(0);

            if (goal.test(ni.getState())) {
                return ni;
            }

            List<S> toAdd = new LinkedList<>();
            for (Transition<S> element : succ.apply(ni.getState())) {
                toAdd.add(element.getState());
            }

            toAdd.removeAll(checked);
            checked.addAll(toAdd);

            for (S element : toAdd) {
                toCheck.add(new Node<>(ni, element, ni.getCost() + 1));
            }
        }

        return null;
    }
}
