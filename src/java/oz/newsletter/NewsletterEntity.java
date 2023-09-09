package oz.newsletter;

import java.io.Serializable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "OZ_NEWSLETTER")
@NamedQueries({
    @NamedQuery(name = "NewsletterEntity.findById", query = "SELECT n FROM NewsletterEntity n WHERE n.id = :id")
})
public class NewsletterEntity implements Serializable {

    public static final String QUERY_GET_NEWSLETTER = "NewsletterEntity.findById";
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "subject", nullable = false)
    private String subject;
    
    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "isSent")
    private boolean isSent = false;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isIsSent() {
        return isSent;
    }

    public void setIsSent(boolean isSent) {
        this.isSent = isSent;
    }


    public NewsletterEntity(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public NewsletterEntity() {

    }
}
