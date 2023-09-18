package oz.property_image;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import oz.property.PropertyEntity;

@Entity
@Table(name = "OZ_PROPERTY_IMAGE")
public class PropertyImageEntity implements Serializable {

    @Id
    @jakarta.persistence.GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "pid")
    private PropertyEntity property;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PropertyEntity getProperty() {
        return property;
    }

    public void setProperty(PropertyEntity property) {
        this.property = property;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PropertyImageEntity() {
    }

    @Override
    public String toString() {
        return "PropertyImageEntity{" + "id=" + id + ", image=" + image + '}';
    }
    
    
}
