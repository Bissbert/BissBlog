package ch.bissbert.bissblog.data;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "images")
/**
 * Represents an image in the database with a unique id and a description.
 *
 * It is used to store the images of the blog posts.
 * @author Bissbert
 * @version 1.0.0
 * @since 1.0.0
 * @see ch.bissbert.bissblog.data.BlogPost
 */
public class Image {
    @Id
    private int id;
    @Lob
    private byte[] data;
    private String mimeType;
    private String description;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private BlogPost blogPost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
