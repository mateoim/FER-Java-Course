package coloring.algorithms;

import marcupic.opjj.statespace.coloring.Picture;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A class that contains everything necessary
 * for coloring a {@code Picture}.
 *
 * @author Mateo Imbrišak
 */

public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel> {

    /**
     * Keeps the pixel selected by the user.
     */
    private Pixel reference;

    /**
     * Keeps the picture being colored.
     */
    private Picture picture;

    /**
     * Keeps the color being used.
     */
    private int fillColor;

    /**
     * Keeps the original color of the first pixel.
     */
    private int refColor;

    /**
     * Default constructor that assigns all values.
     *
     * @param reference to the pixel clicked by the user,
     * @param picture   being colored.
     * @param fillColor color being used to fill the space.
     */
    public Coloring(Pixel reference, Picture picture, int fillColor) {
        this.reference = reference;
        this.picture = picture;
        this.fillColor = fillColor;

        this.refColor = picture.getPixelColor(reference.x, reference.y);
    }

    /**
     * Changes the color of the given picture
     * to the {@code fillColor}.
     *
     * @param pixel whose color should be changed.
     */
    @Override
    public void accept(Pixel pixel) {
        picture.setPixelColor(pixel.x, pixel.y, fillColor);
    }

    /**
     * Adds all neighbors of the given pixel to a {@code List}.
     *
     * @param pixel whose neighbors should be added.
     *
     * @return a {@code List} of neighbors.
     */
    @Override
    public List<Pixel> apply(Pixel pixel) {
        List<Pixel> list = new ArrayList<>();

        if (pixel.x - 1 >= 0) {
            list.add(new Pixel(pixel.x - 1, pixel.y));
        }

        if (pixel.x + 1 <= picture.getWidth() - 1) {
            list.add(new Pixel(pixel.x + 1, pixel.y));
        }

        if (pixel.y - 1 >= 0) {
            list.add(new Pixel(pixel.x, pixel.y - 1));
        }

        if (pixel.y + 1 <= picture.getHeight() - 1) {
            list.add(new Pixel(pixel.x, pixel.y + 1));
        }

        return list;
    }

    /**
     * Checks if the given pixel's color
     * is equal to {@code refColor}.
     *
     * @param pixel whose color is being checked.
     *
     * @return {@code true} if the pixel's color is
     * {@code refColor}, otherwise {@code false}.
     */
    @Override
    public boolean test(Pixel pixel) {
        return picture.getPixelColor(pixel.x, pixel.y) == refColor;
    }

    /**
     * Provides the pixel selected by the user.
     *
     * @return {@code Pixel} selected by the user.
     */
    @Override
    public Pixel get() {
        return reference;
    }
}
