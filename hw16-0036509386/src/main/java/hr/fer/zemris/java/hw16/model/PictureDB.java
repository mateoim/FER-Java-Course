package hr.fer.zemris.java.hw16.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A class that serves as a database for {@link Picture}s.
 *
 * @author Mateo Imbrišak
 */

public class PictureDB {

    /**
     * Keeps the context of resource files.
     */
    private static Path context;

    /**
     * Keeps the description of all pictures.
     */
    private static Set<Picture> pictures = new HashSet<>();

    /**
     * Keeps all found tags.
     */
    private static Set<String> tags = new HashSet<>();

    /**
     * Don't let anyone instantiate this class.
     */
    private PictureDB() {}

    /**
     * Provides all found {@link #tags}.
     *
     * @return all found {@link #tags}.
     */
    public static Set<String> getTags() {
        return tags;
    }

    /**
     * Provides the context to the resources.
     *
     * @return {@link #context}.
     */
    public static Path getContext() {
        return context;
    }

    /**
     * Provides the {@link Picture} with the given name.
     *
     * @param name of the picture.
     *
     * @return {@link Picture} with the given name.
     */
    public static Picture getPicture(String name) {
        for (Picture pic : pictures) {
            if (pic.getName().equals(name))
                return pic;
        }

        throw new RuntimeException("Requested picture not found.");
    }

    /**
     * Provides all {@link Picture}s containing the given tag.
     *
     * @param tag used to filter the {@link Picture}s.
     *
     * @return a {@link Set} of all {@link Picture}s
     * containing the given tag.
     */
    public static Set<Picture> getForTag(String tag) {
        return pictures.stream().filter((c) -> Arrays.asList(c.getTags()).contains(tag)).collect(Collectors.toSet());
    }

    /**
     * Used during listener initialization to initialize the database.
     *
     * @param path to the description file.
     */
    public static void initialize(String path) {
        try {
            context = Paths.get(path);

            List<String> lines = Files.readAllLines(context.resolve("opisnik.txt"));

            for (int i = 0, length = lines.size(); i < length; i += 3) {
                String[] foundTags = lines.get(i + 2).split(", |,");

                pictures.add(new Picture(lines.get(i), lines.get(i + 1), foundTags));
                tags.addAll(Arrays.asList(foundTags));
            }
        } catch (IOException exc) {
            throw new RuntimeException("Error while initializing...", exc);
        }
    }
}
