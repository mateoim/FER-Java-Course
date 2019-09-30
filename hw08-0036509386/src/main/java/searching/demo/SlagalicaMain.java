package searching.demo;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that runs a visual demonstration of
 * {@code Slagalica}.
 *
 * @author Mateo Imbrišak
 */

public class SlagalicaMain {

    /**
     * Used to start the program.
     *
     * @param args a single argument - a string containing
     *             all numbers 0-9 exactly once.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments. Terminating...");
            System.exit(-1);
        }

        int[] input = new int[9];

        if (args[0].length() == 9) {
            try {
                boolean[] valid = new boolean[9];
                char[] data = args[0].toCharArray();

                for (int i = 0; i < 9; i++) {
                    input[i] = data[i] - '0';
                    valid[data[i] - '0'] = true;
                }

                for (boolean element : valid) {
                    if (!element) {
                        terminate();
                    }
                }
            } catch (IndexOutOfBoundsException exc) {
                terminate();
            }
        } else {
            terminate();
        }

        Slagalica slagalica = new Slagalica(
                new KonfiguracijaSlagalice(input)
        );

        Node<KonfiguracijaSlagalice> rjesenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);

        if(rjesenje==null) {
            System.out.println("Nisam uspio pronaći rješenje.");
        } else {
            System.out.println(
                    "Imam rješenje. Broj poteza je: " + rjesenje.getCost()
            );

            List<KonfiguracijaSlagalice> lista = new ArrayList<>();
            Node<KonfiguracijaSlagalice> trenutni = rjesenje;
            while (trenutni != null) {
                lista.add(trenutni.getState());
                trenutni = trenutni.getParent();
            }
            Collections.reverse(lista);
            lista.stream().forEach(k -> {
                System.out.println(k);
                System.out.println();
            });

            SlagalicaViewer.display(rjesenje);
        }
    }

    /**
     * Used internally to terminate if
     * the input is invalid.
     */
    private static void terminate() {
        System.out.println("Invalid input. Terminating...");
        System.exit(-1);
    }
}
