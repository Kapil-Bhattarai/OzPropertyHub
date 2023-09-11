package oz.property_application;

import jakarta.persistence.CascadeType;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import java.util.ArrayList;
import java.util.List;
import oz.ApplicationStatus;
import oz.PropertyType;
import oz.SalaryType;
import oz.address.AddressEntity;
import oz.property.PropertyEntity;
import oz.property_image.PropertyApplicationImageEntity;
import oz.property_image.PropertyImageEntity;
import oz.user.UserEntity;

@Entity
@Table(name = "OZ_PROPERTY_APPLICATION")
@NamedQueries({
    
})
public class PropertyApplicationEntity implements Serializable {

    @EmbeddedId
    private PropertyApplicationId id;

    @Column(name = "applicationStatus")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus type;

    @Column(name = "isEmployed")
    private boolean isEmployed = true;
    
    @Column(name = "salary")
    private Float salary;
    
    @Column(name = "salaryType")
    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;
    
    @Column(name = "noOfCats")
    private int noOfCats = 0;
    
    @Column(name = "noOfDogs")
    private int noOfDogs = 0;
    
    @Column(name = "noOfOtherPets")
    private int noOfOtherPets = 0;
    
    @ManyToOne
    @MapsId("propertyId")
    @JoinColumn(name = "propertyId")
    private PropertyEntity property;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private UserEntity user;

    @OneToMany(mappedBy = "propertyApplication", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PropertyApplicationImageEntity> images = new ArrayList<>();

    public PropertyApplicationId getId() {
        return id;
    }

    public void setId(PropertyApplicationId id) {
        this.id = id;
    }

    public ApplicationStatus getType() {
        return type;
    }

    public void setType(ApplicationStatus type) {
        this.type = type;
    }

    public boolean isIsEmployed() {
        return isEmployed;
    }

    public void setIsEmployed(boolean isEmployed) {
        this.isEmployed = isEmployed;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public SalaryType getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(SalaryType salaryType) {
        this.salaryType = salaryType;
    }

    public int getNoOfCats() {
        return noOfCats;
    }

    public void setNoOfCats(int noOfCats) {
        this.noOfCats = noOfCats;
    }

    public int getNoOfDogs() {
        return noOfDogs;
    }

    public void setNoOfDogs(int noOfDogs) {
        this.noOfDogs = noOfDogs;
    }

    public int getNoOfOtherPets() {
        return noOfOtherPets;
    }

    public void setNoOfOtherPets(int noOfOtherPets) {
        this.noOfOtherPets = noOfOtherPets;
    }

    public PropertyEntity getProperty() {
        return property;
    }

    public void setProperty(PropertyEntity property) {
        this.property = property;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<PropertyApplicationImageEntity> getImages() {
        return images;
    }

    public void setImages(List<PropertyApplicationImageEntity> images) {
        this.images = images;
    }

    
    

}
