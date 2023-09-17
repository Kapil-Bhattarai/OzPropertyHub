package oz.property;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
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
import jakarta.persistence.QueryHint;
import java.util.ArrayList;
import java.util.List;
import oz.PropertyType;
import oz.address.AddressEntity;
import oz.property_image.PropertyImageEntity;
import oz.user.UserEntity;

@Entity
@Table(name = "OZ_PROPERTY")
@NamedQueries({
    @NamedQuery(name = "PropertyEntity.getPropertiesByAgent", query = "SELECT p FROM PropertyEntity p WHERE p.agent.id = :id"),
    @NamedQuery(name = "PropertyEntity.getPropertiesByID", query = "SELECT p FROM PropertyEntity p WHERE p.pid = :pid"),
    @NamedQuery(name = "PropertyEntity.getProperty", query = "SELECT p FROM PropertyEntity p WHERE p.pid = :id"),
    @NamedQuery(name = "PropertyEntity.getPropertyByDate", query = "SELECT p FROM PropertyEntity p WHERE p.listedDate = :dateValue"),
    @NamedQuery(name = "PropertyEntity.getAll", query = "SELECT p FROM PropertyEntity p"),
    @NamedQuery(name = "PropertyEntity.getPropertiesForGallery", query = "SELECT p FROM PropertyEntity p WHERE p.isInGallery = :isInGallery"),
    @NamedQuery(name = "PropertyEntity.getFeaturedProperties", query = "SELECT p FROM PropertyEntity p WHERE p.isFeatured = :isInGallery"),
    @NamedQuery(
            name = "PropertyEntity.search",
            query = "SELECT p FROM PropertyEntity p WHERE"
            + "(:lowerRent IS NULL OR p.rent >= :lowerRent)"
            + " AND (:upperRent IS NULL OR p.rent <= :upperRent)"
            + " AND (:type IS NULL OR p.type = :type)"
            + " AND (:hasAc IS NULL OR p.hasAc = :hasAc)"
            + " AND (:hasSecureParking IS NULL OR p.hasSecureParking = :hasSecureParking)"
            + " AND (:hasDishWasher IS NULL OR p.hasDishWasher = :hasDishWasher)"
            + " AND (:hasBalcony IS NULL OR p.hasBalcony = :hasBalcony)"
            + " AND (:hasWardrobe IS NULL OR p.hasWardrobe = :hasWardrobe)"
            + " AND (:noOfParking IS NULL OR p.noOfParking >= :noOfParking)"
            + " AND (:noOfBathroom IS NULL OR p.noOfBathroom >= :noOfBathroom)"
            + " AND (:noOfBedroom IS NULL OR p.noOfBedroom >= :noOfBedroom)"
            + " AND (:state IS NULL OR p.address.state = :state)"
            + " AND (:searchText IS NULL OR (p.address.suburb = :searchText OR p.address.postcode = :searchText))"
            
    ),
    @NamedQuery(
            name = "PropertyEntity.count",
            query = "SELECT COUNT(p) FROM PropertyEntity p WHERE"
            + "(:lowerRent IS NULL OR p.rent >= :lowerRent)"
            + " AND (:upperRent IS NULL OR p.rent <= :upperRent)"
            + " AND (:type IS NULL OR p.type = :type)"
            + " AND (:hasAc IS NULL OR p.hasAc = :hasAc)"
            + " AND (:hasSecureParking IS NULL OR p.hasSecureParking = :hasSecureParking)"
            + " AND (:hasDishWasher IS NULL OR p.hasDishWasher = :hasDishWasher)"
            + " AND (:hasBalcony IS NULL OR p.hasBalcony = :hasBalcony)"
            + " AND (:hasWardrobe IS NULL OR p.hasWardrobe = :hasWardrobe)"
            + " AND (:noOfParking IS NULL OR p.noOfParking >= :noOfParking)"
            + " AND (:noOfBathroom IS NULL OR p.noOfBathroom >= :noOfBathroom)"
            + " AND (:noOfBedroom IS NULL OR p.noOfBedroom >= :noOfBedroom)"
            + " AND (:state IS NULL OR p.address.state = :state)"
            + " AND (:searchText IS NULL OR (p.address.suburb = :searchText OR p.address.postcode = :searchText))"
            
    )
})
public class PropertyEntity implements Serializable {

    public static final String QUERY_SEARCH_QUERY = "PropertyEntity.search";
    public static final String QUERY_GET_PROPERTY_BY_AGENT = "PropertyEntity.getPropertiesByAgent";
    public static final String QUERY_GET_PROPERTY = "PropertyEntity.getProperty";
    public static final String QUERY_GET_PROPERTY_BY_DATE = "PropertyEntity.getPropertyByDate";
    public static final String QUERY_DELETE_PROPERTY_BY_ID = "PropertyEntity.deletePropertyById";
    public static final String QUERY_GET_ALL = "PropertyEntity.getAll";
    public static final String QUERY_COUNT = "PropertyEntity.count";
    public static final String QUERY_GET_PROPERTIES_FOR_GALLERY = "PropertyEntity.getPropertiesForGallery";
    public static final String QUERY_GET_FEATURED_PROPERTIES = "PropertyEntity.getFeaturedProperties";

    @Id
    @jakarta.persistence.GeneratedValue(strategy = IDENTITY)
    @Column(name = "pid", nullable = false)
    private Integer pid;

    @Column(name = "rent")
    private double rent;

    @Column(name = "propertyType")
    @Enumerated(EnumType.STRING)
    private PropertyType type;

    @Column(name = "inspection")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inspection;

    @Column(name = "listed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date listedDate;

    @Column(name = "hasAc")
    private Boolean hasAc = false;

    @Column(name = "mainImage")
    private String mainImage;

    @Column(name = "hasSecureParking")
    private Boolean hasSecureParking = false;

    @Column(name = "hasDishWasher")
    private Boolean hasDishWasher = false;

    @Column(name = "hasBalcony")
    private Boolean hasBalcony = false;

    @Column(name = "hasWardrobe")
    private Boolean hasWardrobe = false;

    @Column(name = "noOfParking")
    private int noOfParking = 0;

    @Column(name = "noOfBathroom")
    private int noOfBathroom = 0;

    @Column(name = "noOfBedroom")
    private int noOfBedroom = 0;

    @Lob
    @Column(name = "propertyDetails", length = 65535)
    private String propertyDetails = "";

    @Lob
    @Column(name = "map", length = 65535)
    private String map = "";

    @Column(name = "isFeatured")
    private Boolean isFeatured = false;
    
    @Column(name = "isInGallery")
    private Boolean isInGallery = false;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "addressId")
    private AddressEntity address;

    @OneToOne
    @JoinColumn(name = "agentId")
    private UserEntity agent;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PropertyImageEntity> images = new ArrayList<>();

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public Date getInspection() {
        return inspection;
    }

    public void setInspection(Date inspection) {
        this.inspection = inspection;
    }

    public Date getListedDate() {
        return listedDate;
    }

    public void setListedDate(Date listedDate) {
        this.listedDate = listedDate;
    }

    public Boolean getHasAc() {
        return hasAc;
    }

    public void setHasAc(Boolean hasAc) {
        this.hasAc = hasAc;
    }

    public Boolean getHasSecureParking() {
        return hasSecureParking;
    }

    public void setHasSecureParking(Boolean hasSecureParking) {
        this.hasSecureParking = hasSecureParking;
    }

    public Boolean getHasDishWasher() {
        return hasDishWasher;
    }

    public void setHasDishWasher(Boolean hasDishWasher) {
        this.hasDishWasher = hasDishWasher;
    }

    public Boolean getHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(Boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public Boolean getHasWardrobe() {
        return hasWardrobe;
    }

    public void setHasWardrobe(Boolean hasWardrobe) {
        this.hasWardrobe = hasWardrobe;
    }

    public int getNoOfParking() {
        return noOfParking;
    }

    public void setNoOfParking(int noOfParking) {
        this.noOfParking = noOfParking;
    }

    public int getNoOfBathroom() {
        return noOfBathroom;
    }

    public void setNoOfBathroom(int noOfBathroom) {
        this.noOfBathroom = noOfBathroom;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public UserEntity getAgent() {
        return agent;
    }

    public void setAgent(UserEntity agent) {
        this.agent = agent;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public List<PropertyImageEntity> getImages() {
        return images;
    }

    public void setImages(List<PropertyImageEntity> images) {
        this.images = images;
    }

    public int getNoOfBedroom() {
        return noOfBedroom;
    }

    public void setNoOfBedroom(int noOfBedroom) {
        this.noOfBedroom = noOfBedroom;
    }

    public void addImage(PropertyImageEntity image) {
        this.images.add(image);
    }

    public PropertyEntity() {
    }

    public String getPropertyDetails() {
        return propertyDetails;
    }

    public void setPropertyDetails(String propertyDetails) {
        this.propertyDetails = propertyDetails;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getIsInGallery() {
        return isInGallery;
    }

    public void setIsInGallery(Boolean isInGallery) {
        this.isInGallery = isInGallery;
    }

    @Override
    public String toString() {
        return "PropertyEntity{" + "pid=" + pid + ", rent=" + rent + ", type=" + type + ", inspection=" + inspection + ", listedDate=" + listedDate + ", hasAc=" + hasAc + ", mainImage=" + mainImage + ", hasSecureParking=" + hasSecureParking + ", hasDishWasher=" + hasDishWasher + ", hasBalcony=" + hasBalcony + ", hasWardrobe=" + hasWardrobe + ", noOfParking=" + noOfParking + ", noOfBathroom=" + noOfBathroom + ", noOfBedroom=" + noOfBedroom + ", address=" + address + ", agent=" + agent + ", images=" + images + '}';
    }

}
