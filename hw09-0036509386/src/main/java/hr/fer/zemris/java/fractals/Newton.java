package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class that draws an image of a factorial
 * based on the Newton-Raphson iteration.
 *
 * @author Mateo Imbrišak
 */

public class Newton {

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
        System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

        List<Complex> factors = getInput();

        if (factors.size() < 2) {
            System.out.println("Number of roots cannot be zero. Terminating...");
            System.exit(-1);
        } else {
            System.out.println("Image of fractal will appear shortly. Thank you.");
        }

        FractalViewer.show(new NewtonProducer(new ComplexRootedPolynomial(Complex.ONE,
                factors.toArray(new Complex[0]))));
    }

    /**
     * Used internally to get and parse user input.
     *
     * @return a {@code List} of {@code Complex} numbers
     * entered by the user.
     */
    private static List<Complex> getInput() {
        List<Complex> ret = new LinkedList<>();

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("Root " + (ret.size() + 1) + "> ");
                String input = sc.nextLine().trim().replace(" ", "");

                if (input.equalsIgnoreCase("done")) {
                    return ret;
                }

                parseInput(input, ret);
            }
        }
    }

    /**
     * Used internally to parse the input provided by the user.
     *
     * @param input provided by the user.
     * @param ret a {@code List} containing the parsed complex numbers
     *            to be returned by {@link #getInput()}.
     */
    private static void parseInput(String input, List<Complex> ret) {
        if (!input.contains("i")) {
            try {
                ret.add(new Complex(Double.parseDouble(input), 0));
            } catch (NumberFormatException exc) {
                System.out.println("Invalid format.");
            }
        } else {
            if (input.equals("i")) {
                ret.add(Complex.IM);
                return;
            }

            String[] parts = input.split("i");

            if (parts.length == 1) {
                parts = new String[] {parts[0].substring(0, parts[0].length() - 1),
                        parts[0].charAt(parts[0].length() - 1) + "1"};
            } else {
                parts[1] = parts[0].charAt(parts[0].length() - 1) + parts[1];
                parts[0] = parts[0].substring(0, parts[0].length() - 1);
            }

            if (parts[0].isEmpty()) {
                parts[0] = "0";
            }

            try {
                ret.add(new Complex(Double.parseDouble(parts[0]), Double.parseDouble(parts[1])));
            } catch (NumberFormatException exc) {
                System.out.println("Invalid format.");
            }
        }
    }

    /**
     * A class that processes a part of the polynomial
     * to be used for the image.
     */
    private static class NewtonJob implements Callable<Void> {

        /**
         * Represents the acceptable root-distance.
         */
        private static final double ROOT_THRESHOLD = 0.002;

        /**
         * Represents the convergence threshold.
         */
        private static final double CONVERGENCE_THRESHOLD = 0.001;

        /**
         * Keeps the minimal real part.
         */
        private double reMin;

        /**
         * Keeps the maximal real part.
         */
        private double reMax;

        /**
         * Keeps the minimal imaginary part.
         */
        private double imMin;

        /**
         * Keeps the maximal imaginary part.
         */
        private double imMax;

        /**
         * Width of the screen.
         */
        private int width;

        /**
         * Height of the screen.
         */
        private int height;

        /**
         * Starting position on y axis.
         */
        private int yMin;

        /**
         * Ending position on y axis.
         */
        private int yMax;

        /**
         * Maximum number of iterations made.
         */
        private int maxIter;

        /**
         * Keeps the results of the processing.
         */
        private short[] data;

        /**
         * Used to abort the operation.
         */
        private AtomicBoolean cancel;

        /**
         * Keeps the polynomial.
         */
        private ComplexRootedPolynomial polynomial;

        /**
         * Default constructor that assigns all values.
         *
         * @param reMin minimal real part.
         * @param reMax maximal real part.
         * @param imMin minimal imaginary part.
         * @param imMax maximal imaginary part.
         * @param width of the screen.
         * @param height of the screen.
         * @param yMin starting position on y axis.
         * @param yMax ending position on y axis.
         * @param maxIter number of iterations.
         * @param data used to store the results.
         * @param cancel used to abort the operation.
         * @param polynomial used for the image.
         */
        public NewtonJob(double reMin, double reMax, double imMin, double imMax, int width, int height,
                         int yMin, int yMax, int maxIter, short[] data, AtomicBoolean cancel,
                         ComplexRootedPolynomial polynomial) {
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.maxIter = maxIter;
            this.data = data;
            this.cancel = cancel;
            this.polynomial = polynomial;
        }

        /**
         * Computes the necessary data to be used for
         * a part of the image.
         *
         * @return {@code null}.
         *
         * @throws Exception if an error occurs.
         */
        @Override
        public Void call() throws Exception {
            int offset = yMin * width;

            for (int y = yMin; y <= yMax && !cancel.get(); y++) {
                for (int x = 0; x < width && !cancel.get(); x++) {
                    Complex zn = new Complex((double) x / (width - 1d) * (reMax - reMin) + reMin,
                            (height - 1d - y) / (height - 1d) * (imMax - imMin) + imMin);

                    int iter = 0;
                    ComplexPolynomial derived = polynomial.toComplexPolynom().derive();
                    double module;

                    do {
                        Complex numerator = polynomial.apply(zn);
                        Complex denominator = derived.apply(zn);
                        Complex znold = zn;
                        Complex fraction = numerator.divide(denominator);
                        zn = zn.sub(fraction);
                        module = znold.sub(zn).module();
                    } while (module > CONVERGENCE_THRESHOLD && iter < maxIter);

                    int index = polynomial.indexOfClosestRootFor(zn, ROOT_THRESHOLD);

                    data[offset++] = (short) (index + 1);
                }
            }

            return null;
        }
    }

    /**
     * A class used to produce jobs that process the image.
     */
    private static class NewtonProducer implements IFractalProducer {

        /**
         * Keeps the {@code ThreadPool}.
         */
        private ExecutorService pool;

        /**
         * Keeps the polynomial.
         */
        private ComplexRootedPolynomial polynomial;

        /**
         * Default constructor that assigns the polynomial.
         *
         * @param polynomial to be used for the image.
         */
        public NewtonProducer(ComplexRootedPolynomial polynomial) {
            this.polynomial = polynomial;

            pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), r -> {
                Thread ret = new Thread(r);
                ret.setDaemon(true);
                return ret;
            });
        }

        /**
         * Creates a new {@code Job} that processes a part
         * of the image in a new {@code Thread}.
         *
         * @param reMin minimal real part.
         * @param reMax maximal real part.
         * @param imMin minimal imaginary part.
         * @param imMax maximal imaginary part.
         * @param width of the screen.
         * @param height of the screen.
         * @param requestNo used to identify the request.
         * @param observer that receives the processed data.
         * @param cancel used to abort the operation.
         */
        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
            int maxIter = 16 * 16 * 16;
            short[] data = new short[width * height];

            final int tracks = 8 * Runtime.getRuntime().availableProcessors();
            int yByTrack = height / tracks;

            List<Future<Void>> results = new ArrayList<>();

            for (int i = 0; i < tracks && !cancel.get(); i++) {
                int yMin = i * yByTrack;
                int yMax = (i + 1) * yByTrack - 1;

                if (i == tracks - 1) {
                    yMax = height - 1;
                }
                NewtonJob job = new NewtonJob(reMin, reMax, imMin, imMax, width, height,
                        yMin, yMax, maxIter, data, cancel, polynomial);
                results.add(pool.submit(job));
            }

            for (Future<Void> job : results) {
                while (true) {
                    try {
                        job.get();
                    } catch (InterruptedException | ExecutionException ignored) {
                        continue;
                    }

                    break;
                }
            }

            observer.acceptResult(data, (short) (polynomial.toComplexPolynom().order() + 1), requestNo);
        }
    }
}
