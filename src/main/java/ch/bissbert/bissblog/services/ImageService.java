package ch.bissbert.bissblog.services;

import ch.bissbert.bissblog.dao.GenericDao;
import ch.bissbert.bissblog.data.Image;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * A spring boot service for images that can be used in the blog.
 * The services use the image class to store the images in the database.
 * To manage the data the {@link ch.bissbert.bissblog.dao.GenericDao} is used.
 *
 * @author Bissbert
 * @version 1.0.0
 * @since 1.0.0
 * @see ch.bissbert.bissblog.dao.Dao
 * @see ch.bissbert.bissblog.dao.GenericDao
 */
@Controller
public class ImageService {
    /**
     * The dao for the images.
     */
    private GenericDao<Image> imageDao = new GenericDao<Image>(Image.class);

    /**
     * Service that returns a image in base64 format by the id given as a PATH parameter.
     * Transforms the data from the database to base64 format using the mimtype and returns it.
     * Uses the get web mapping.
     *
     * @param id the id of the image in the path
     * @return the image data in the class from the database in base64 format
     */
    @GetMapping(value = "/image/{id}")
    public String getImage(@PathVariable("id") int id) {
        Image image = imageDao.read(id);
        //transform the data to base64 format
        return "data:" + image.getMimeType() + ";base64," + image.getData();
    }

}
