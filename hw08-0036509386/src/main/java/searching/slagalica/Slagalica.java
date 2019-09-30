package searching.slagalica;

import searching.algorithms.Transition;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A class that represents a puzzle game.
 *
 * @author Mateo Imbrišak
 */

public class Slagalica implements Supplier<KonfiguracijaSlagalice>,
        Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {

    /**
     * Keeps the initial configuration.
     */
    private KonfiguracijaSlagalice configuration;

    /**
     * Default constructor that assigns the
     * initial configuration.
     *
     * @param configuration to be assigned.
     */
    public Slagalica(KonfiguracijaSlagalice configuration) {
        this.configuration = configuration;
    }

    /**
     * Creates a {@code List} of all possible next moves.
     *
     * @param konfiguracijaSlagalice current configuration.
     *
     * @return a {@code List} of next possible moves.
     */
    @Override
    public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice konfiguracijaSlagalice) {
        List<Transition<KonfiguracijaSlagalice>> ret = new LinkedList<>();
        int index = konfiguracijaSlagalice.indexOfSpace();

        if (index - 3 >= 0) {
            int[] create = Arrays.copyOf(konfiguracijaSlagalice.getPolje(), konfiguracijaSlagalice.getPolje().length);
            create[index] = create[index - 3];
            create[index - 3] = 0;

            ret.add(new Transition<>(new KonfiguracijaSlagalice(create), 1));
        }

        if (index + 3 <= 8) {
            int[] create = Arrays.copyOf(konfiguracijaSlagalice.getPolje(), konfiguracijaSlagalice.getPolje().length);
            create[index] = create[index + 3];
            create[index + 3] = 0;

            ret.add(new Transition<>(new KonfiguracijaSlagalice(create), 1));
        }

        switch (index % 3) {
            case 1:
                int[] create = Arrays.copyOf(konfiguracijaSlagalice.getPolje(), konfiguracijaSlagalice.getPolje().length);
                create[index] = create[index - 1];
                create[index - 1] = 0;

                ret.add(new Transition<>(new KonfiguracijaSlagalice(create), 1));
            case 0:
                int[] zero = Arrays.copyOf(konfiguracijaSlagalice.getPolje(), konfiguracijaSlagalice.getPolje().length);
                zero[index] = zero[index + 1];
                zero[index + 1] = 0;

                ret.add(new Transition<>(new KonfiguracijaSlagalice(zero), 1));
                break;
            case 2:
                int[] two = Arrays.copyOf(konfiguracijaSlagalice.getPolje(), konfiguracijaSlagalice.getPolje().length);
                two[index] = two[index - 1];
                two[index - 1] = 0;

                ret.add(new Transition<>(new KonfiguracijaSlagalice(two), 1));
                break;
        }

        return ret;
    }

    /**
     * Checks if the puzzle has been solved.
     *
     * @param konfiguracijaSlagalice configuration to be checked.
     *
     * @return {@code true} if the puzzle has been solved,
     * otherwise {@code false}.
     */
    @Override
    public boolean test(KonfiguracijaSlagalice konfiguracijaSlagalice) {
        return Arrays.equals(konfiguracijaSlagalice.getPolje(), new int[] {1, 2, 3, 4, 5, 6, 7, 8, 0});
    }

    /**
     * Provides the starting configuration.
     *
     * @return starting configuration.
     */
    @Override
    public KonfiguracijaSlagalice get() {
        return configuration;
    }
}
