package oz.property_application_image;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import oz.DocumentType;
import oz.property_application.PropertyApplicationEntity;


public class PropertyApplicationImageEntity implements Serializable {

    @Id
    @jakarta.persistence.GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "propertyId", referencedColumnName = "propertyId"),
        @JoinColumn(name = "userId", referencedColumnName = "userId"),
        @JoinColumn(name = "agentId", referencedColumnName = "agentId")
    })
    private PropertyApplicationEntity propertyApplication;
    
    @Column(name = "documentType")
    @Enumerated(EnumType.STRING)
    private DocumentType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PropertyApplicationEntity getPropertyApplication() {
        return propertyApplication;
    }

    public void setPropertyApplication(PropertyApplicationEntity propertyApplication) {
        this.propertyApplication = propertyApplication;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public PropertyApplicationImageEntity() {
    }
}
