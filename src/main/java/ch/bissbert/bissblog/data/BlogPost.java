package ch.bissbert.bissblog.data;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A blog post that contains the following attributes:
 * <ul>
 *     <li>title</li>
 *     <li>content</li>
 *     <li>tags</li>
 *     <li>author</li>
 *     <li>comments</li>
 *     <li>published</li>
 *     <li>preview image</li>
 *     <li>images</li>
 * </ul>
 * @author Bissbert
 * @since 1.0.0
 * @version 1.0.0
 * @see ch.bissbert.bissblog.data.Image
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "blog_post")
public class BlogPost {
    @Id
    private int id;

    private String title;

    private String content;

    private String author;

    private String date;
    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "tag_id"))
    @Column(name = "tag")
    private List<String> tags;

    private byte[] previewImage;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "blog_post_image", joinColumns = @JoinColumn(name = "blog_post_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    @MapKey(name = "id")
    private Map<String, Image> images;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BlogPost blogPost = (BlogPost) o;
        return Objects.equals(id, blogPost.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
