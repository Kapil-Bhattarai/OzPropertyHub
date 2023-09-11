package oz.property_image;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import oz.property.PropertyEntity;
import oz.property_application.PropertyApplicationEntity;

@Entity
@Table(name = "OZ_REFERENCE")
public class ReferencesEntity implements Serializable {

    @Id
    @jakarta.persistence.GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "firstName")
    private String firstName;
    
    @Column(name = "lastName")
    private String lastName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "propertyId", referencedColumnName = "propertyId"),
        @JoinColumn(name = "userId", referencedColumnName = "userId")
    })
    private PropertyApplicationEntity propertyApplication;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PropertyApplicationEntity getPropertyApplication() {
        return propertyApplication;
    }

    public void setPropertyApplication(PropertyApplicationEntity propertyApplication) {
        this.propertyApplication = propertyApplication;
    }

    public ReferencesEntity() {
    }
}
