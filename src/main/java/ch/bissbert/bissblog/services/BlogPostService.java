package ch.bissbert.bissblog.services;

import ch.bissbert.bissblog.dao.GenericDao;
import ch.bissbert.bissblog.data.BlogPost;
import ch.bissbert.bissblog.data.Image;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A Spring boot controller containing the following services relating to blog posts and their images:
 * <ul>
 *     <li>read BlogPost</li>
 *     <li>create BlogPost</li>
 *     <li>update BlogPost</li>
 *     <li>delete BlogPost</li>
 *     <li>list BlogPosts</li>
 *     <li>save an image and add it to a BlogPost</li>
 *     <li>delete an image from a BlogPost</li>
 *     <li>list images of a BlogPost</li>
 * </ul>
 * <p>
 * The persistence of the BlogPosts and the images are handled using separate generic DAOs ({@link GenericDao}).
 *
 * @author Bissbert
 * @version 1.0.0
 * @see GenericDao
 * @see BlogPost
 * @see Image
 * @since 1.0.0
 */
@Controller
public class BlogPostService {
    /**
     * The DAO for BlogPosts.
     */
    private GenericDao<BlogPost> blogPostDao = new GenericDao<>(BlogPost.class);

    /**
     * The DAO for Images.
     */
    private GenericDao<Image> imageDao = new GenericDao<>(Image.class);

    /**
     * Spring Boot Service method to read all BlogPosts from the database and return them in JSON format.
     * Service uses the get web method
     *
     * @return all BlogPosts in a List to be returned to the client
     * @see BlogPost
     * @see GenericDao
     * @since 1.0.0
     */
    @GetMapping(value = "/blogposts/list", produces = "application/json")
    public List<BlogPost> getBlogPosts() {
        // read all BlogPosts from the database
        return blogPostDao.readAll();
    }

    /**
     * Spring Boot Service method to create a new BlogPost in the database.
     * Service uses the post web method
     *
     * @param blogPost the BlogPost to be created
     *                 (the BlogPost must have a title, a content and a published date)
     * @return the BlogPost that was created
     * @throws IllegalArgumentException if the BlogPost is null or has no title, content or published date
     * @throws IllegalStateException    if the BlogPost could not be created
     * @throws IllegalStateException    if the BlogPost already exists in the database
     * @see BlogPost
     * @see GenericDao
     * @since 1.0.0
     */
    @PostMapping(value = "/blogposts", produces = "application/json")
    public BlogPost createBlogPost(BlogPost blogPost) {
        if (blogPost == null) {
            throw new IllegalArgumentException("BlogPost must not be null");
        }
        if (blogPost.getTitle() == null || blogPost.getTitle().isEmpty()) {
            throw new IllegalArgumentException("BlogPost must have a title");
        }
        if (blogPost.getContent() == null || blogPost.getContent().isEmpty()) {
            throw new IllegalArgumentException("BlogPost must have a content");
        }
        if (blogPost.getDate() == null) {
            throw new IllegalArgumentException("BlogPost must have a published date");
        }
        if (blogPostDao.read(blogPost.getId()) != null) {
            throw new IllegalStateException("BlogPost already exists");
        }
        // create the BlogPost and save it to the database
        return blogPostDao.create(blogPost);
    }

    /**
     * Spring Boot Service method to update an existing BlogPost in the database.
     * Service uses the put web method
     *
     * @param blogPost the BlogPost to be updated
     *                 (the BlogPost must have an id, a title, a content and a published date)
     * @return the BlogPost that was updated
     * @throws IllegalArgumentException if the BlogPost is null or has no id, title, content or published date
     * @throws IllegalStateException    if the BlogPost could not be updated
     * @throws IllegalStateException    if the BlogPost does not exist in the database
     * @see BlogPost
     * @see GenericDao
     * @since 1.0.0
     */
    @PutMapping(value = "/blogposts", produces = "application/json")
    public BlogPost updateBlogPost(BlogPost blogPost) {
        if (blogPost == null) {
            throw new IllegalArgumentException("BlogPost must not be null");
        }
        if (blogPost.getId() == null) {
            throw new IllegalArgumentException("BlogPost must have an id");
        }
        if (blogPost.getTitle() == null || blogPost.getTitle().isEmpty()) {
            throw new IllegalArgumentException("BlogPost must have a title");
        }
        if (blogPost.getContent() == null || blogPost.getContent().isEmpty()) {
            throw new IllegalArgumentException("BlogPost must have a content");
        }
        if (blogPost.getDate() == null) {
            throw new IllegalArgumentException("BlogPost must have a published date");
        }
        if (blogPostDao.read(blogPost.getId()) == null) {
            throw new IllegalStateException("BlogPost does not exist");
        }
        // update the BlogPost in the database
        return blogPostDao.update(blogPost);
    }

    /**
     * Spring Boot Service method to delete an existing BlogPost from the database.
     * Service uses the delete web method
     *
     * @param id the id of the BlogPost to be deleted
     *           (the id must be greater than 0)
     * @return the BlogPost that was deleted
     * @throws IllegalArgumentException if the id is less than or equal to 0
     * @throws IllegalStateException    if the BlogPost could not be deleted
     * @throws IllegalStateException    if the BlogPost does not exist in the database
     * @see BlogPost
     * @see GenericDao
     * @since 1.0.0
     */
    @DeleteMapping(value = "/blogposts/{id}", produces = "application/json")
    public BlogPost deleteBlogPost(@PathVariable("id") int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be greater than 0");
        }
        BlogPost blogPost = blogPostDao.read(id);
        if (blogPost == null) {
            throw new IllegalStateException("BlogPost does not exist");
        }
        // delete the blog post from the database
        blogPostDao.delete(blogPost);
        return blogPost;
    }

    /**
     * Spring Boot Service method to get a single BlogPost from the database.
     * Service uses the get web method
     * <p>
     * The id of the BlogPost to be retrieved must be greater than 0
     * The BlogPost must exist in the database
     * </p>
     *
     * @param id the id of the BlogPost to be retrieved
     *           (the id must be greater than 0)
     *           (the BlogPost must exist in the database)
     * @return the BlogPost that was retrieved
     * @throws IllegalArgumentException if the id is less than or equal to 0
     * @throws IllegalStateException    if the BlogPost does not exist in the database
     * @see BlogPost
     * @see GenericDao
     * @since 1.0.0
     */
    @GetMapping(value = "/blogposts/{id}", produces = "application/json")
    public BlogPost getBlogPost(@PathVariable("id") int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be greater than 0");
        }
        // read the BlogPost from the database
        BlogPost blogPost = blogPostDao.read(id);
        if (blogPost == null) {
            throw new IllegalStateException("BlogPost does not exist");
        }
        return blogPost;
    }

    /**
     * Spring Boot Service method to save an image to the database and add it to a BlogPost.
     * Service uses the post web method
     * <ul>
     *     <li>The id of the BlogPost to be updated must be greater than 0</li>
     *     <li>The BlogPost must exist in the database</li>
     *     <li>The image must not be null</li>
     *     <li>The image must have a description</li>
     * </ul>
     *
     * @param id    the id of the BlogPost to be updated
     *              (the id must be greater than 0)
     *              (the BlogPost must exist in the database)
     * @param image the image to be added to the BlogPost
     *              (the image must not be null)
     *              (the image must have a description)
     * @return the BlogPost that was updated
     * @throws IllegalArgumentException if the id is less than or equal to 0
     * @throws IllegalStateException    if the BlogPost does not exist in the database
     * @throws IllegalArgumentException if the image is null
     * @throws IllegalArgumentException if the image does not have a description
     * @see BlogPost
     * @see Image
     * @see GenericDao
     * @since 1.0.0
     */
    @PostMapping(value = "/blogposts/{id}/images", produces = "application/json")
    public BlogPost addImageToBlogPost(@PathVariable("id") int id, @RequestBody Image image) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be greater than 0");
        }
        BlogPost blogPost = blogPostDao.read(id);
        if (blogPost == null) {
            throw new IllegalStateException("BlogPost does not exist");
        }
        if (image == null) {
            throw new IllegalArgumentException("Image must not be null");
        }
        if (image.getDescription() == null) {
            throw new IllegalArgumentException("Image must have a description");
        }
        // create images list if it doesn't exist
        if (blogPost.getImages() == null) {
            blogPost.setImages(new ArrayList<>());
        }
        // add image to images list
        blogPost.getImages().add(image);

        // save image to database
        imageDao.create(image);

        // save blog post to database
        return blogPostDao.update(blogPost);
    }

    /**
     * Spring Boot Service method to delete an image from a BlogPost and from the database.
     * Service uses the delete web method
     * <ul>
     *     <li>The id of the BlogPost to be updated must be greater than 0</li>
     *     <li>The BlogPost must exist in the database</li>
     *     <li>The id of the image to be deleted must be greater than 0</li>
     *     <li>The image must exist in the database</li>
     *     <li>The image must be associated with the BlogPost</li>
     * </ul>
     *
     * @param id the id of the BlogPost to be updated
     *           (the id must be greater than 0)
     *           (the BlogPost must exist in the database)
     *
     * @param imageId the id of the image to be deleted
     *                (the id must be greater than 0)
     *                (the image must exist in the database)
     *                (the image must be associated with the BlogPost)
     *
     * @return the BlogPost that was updated
     * @throws IllegalArgumentException if the id is less than or equal to 0
     * @throws IllegalStateException    if the BlogPost does not exist in the database
     * @throws IllegalArgumentException if the image id is less than or equal to 0
     * @throws IllegalStateException    if the image does not exist in the database
     * @throws IllegalStateException    if the image is not associated with the BlogPost
     * @see BlogPost
     * @see Image
     * @see GenericDao
     * @since 1.0.0
     */
    @DeleteMapping(value = "/blogposts/{id}/images/{imageId}", produces = "application/json")
    public BlogPost deleteImageFromBlogPost(@PathVariable("id") int id, @PathVariable("imageId") int imageId) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be greater than 0");
        }
        BlogPost blogPost = blogPostDao.read(id);
        if (blogPost == null) {
            throw new IllegalStateException("BlogPost does not exist");
        }
        if (imageId <= 0) {
            throw new IllegalArgumentException("image id must be greater than 0");
        }
        Image image = imageDao.read(imageId);
        if (image == null) {
            throw new IllegalStateException("Image does not exist");
        }
        if (!blogPost.getImages().contains(image)) {
            throw new IllegalStateException("Image is not associated with the BlogPost");
        }
        // remove image from blog post
        blogPost.getImages().remove(image);

        // delete image from database
        imageDao.delete(image);

        // update BlogPost in database
        return blogPostDao.update(blogPost);
    }

    /**
     * Spring Boot Service method to list all Images associated with a BlogPost.
     * Service uses the get web method
     * <ul>
     *     <li>The id of the BlogPost to be read must be greater than 0</li>
     *     <li>The BlogPost must exist in the database</li>
     * </ul>
     *
     * @param id the id of the BlogPost to be read
     *           (the id must be greater than 0)
     *           (the BlogPost must exist in the database)
     *
     * @return the list of Images associated with the BlogPost
     * @throws IllegalArgumentException if the id is less than or equal to 0
     * @throws IllegalStateException    if the BlogPost does not exist in the database
     * @see BlogPost
     * @see Image
     * @see GenericDao
     * @since 1.0.0
     */
    @GetMapping(value = "/blogposts/{id}/images", produces = "application/json")
    public List<Image> listImagesForBlogPost(@PathVariable("id") int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be greater than 0");
        }
        BlogPost blogPost = blogPostDao.read(id);
        if (blogPost == null) {
            throw new IllegalStateException("BlogPost does not exist");
        }
        // return list of images associated with blog post
        return blogPost.getImages();
    }


}
