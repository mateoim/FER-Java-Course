package hr.fer.zemris.java.hw16.rest;

import com.google.gson.Gson;
import hr.fer.zemris.java.hw16.model.Picture;
import hr.fer.zemris.java.hw16.model.PictureDB;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * A class that generates JSON files
 * for pictures.
 *
 * @author Mateo Imbrišak
 */

@Path("/picture")
public class PictureJSON {

    /**
     * Provides tags found using {@link PictureDB#getTags()}.
     *
     * @return tags for pictures.
     */
    @GET
    @Path("/tags")
    @Produces("application/json")
    public Response getTags() {
        Set<String> tags = PictureDB.getTags();

        return Response.status(Response.Status.OK).entity(new Gson().toJson(tags)).build();
    }

    /**
     * Provides {@link Picture} with the given name.
     *
     * @param name of the picture.
     *
     * @return {@link Picture} with the given name.
     */
    @GET
    @Path("/{name}")
    @Produces("application/json")
    public Response getPictureDescription(@PathParam("name") String name) {
        Picture pic = PictureDB.getPicture(name);

        return Response.status(Response.Status.OK).entity(new Gson().toJson(pic)).build();
    }

    /**
     * Provides all {@link Picture}s with the given tag.
     *
     * @param tag to be filtered.
     *
     * @return all {@link Picture}s provided by {@link PictureDB#getForTag(String)}.
     */
    @GET
    @Path("/tags/{tag}")
    @Produces("application/json")
    public Response getPicturesForTag(@PathParam("tag") String tag) {
        Set<Picture> pics = PictureDB.getForTag(tag);

        return Response.status(Response.Status.OK).entity(new Gson().toJson(pics)).build();
    }
}
