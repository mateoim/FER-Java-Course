package hr.fer.zemris.java.hw16.servlets;

import hr.fer.zemris.java.hw16.model.PictureDB;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * An {@link HttpServlet} that handles
 * image dispatching and creation of thumbnails.
 *
 * @author Mateo Imbrišak
 */

@WebServlet(urlPatterns = {"/img"})
public class ImageServlet extends HttpServlet {

    private static final long serialVersionUID = -715055036447268970L;

    /**
     * Keeps the default thumbnail dimensions.
     */
    private static final int SCALED_DIMENSIONS = 150;

    /**
     * Keeps the name of thumbnails folder.
     */
    private static final String THUMBNAILS_DIRECTORY = "thumbnails";

    /**
     * Keeps the name of the folder with original images.
     */
    private static final String IMAGES_DIRECTORY = "slike";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/jpg");

        String path = req.getParameter("path");
        Path src = PictureDB.getContext().resolve(path);

        if (path.startsWith("thumbnails")) {
            Path thumbnails = PictureDB.getContext().resolve("thumbnails");

            if (!Files.exists(thumbnails)) {
                Files.createDirectory(thumbnails);
            }

            if (!Files.exists(src)) {
                Path thumb = PictureDB.getContext().resolve(path.replace(THUMBNAILS_DIRECTORY, IMAGES_DIRECTORY));

                BufferedImage bim = ImageIO.read(thumb.toFile());
                BufferedImage scaled = new BufferedImage(SCALED_DIMENSIONS, SCALED_DIMENSIONS, BufferedImage.TYPE_INT_RGB);

                Graphics2D g2d = scaled.createGraphics();
                g2d.drawImage(bim, 0, 0, SCALED_DIMENSIONS, SCALED_DIMENSIONS, null);
                g2d.dispose();

                ImageIO.write(scaled, "jpg", src.toFile());
            }
        }

        ImageIO.write(ImageIO.read(src.toFile()), "jpg", resp.getOutputStream());
    }
}
