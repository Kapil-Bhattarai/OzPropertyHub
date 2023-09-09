package oz.newsletter_subscriber;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import oz.UserType;

@Entity
@Table(name = "OZ_NEWSLETTER_SUBSCRIBER")
@NamedQueries({
    @NamedQuery(name = "NewsletterEntity.findByEmail", query = "SELECT n FROM NewsletterEntity n WHERE n.email = :email")
})
public class NewsletterSubscriberEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NewsletterSubscriberEntity(String email) {
        this.email = email;
    }

    public NewsletterSubscriberEntity() {

    }
}
