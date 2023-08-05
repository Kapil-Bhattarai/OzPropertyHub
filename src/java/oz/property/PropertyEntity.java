package oz.property;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import oz.UserType;

@Entity
@Table(name = "OZ_PROPERTY")
public class PropertyEntity implements Serializable {

   
    @Id
    @jakarta.persistence.GeneratedValue(strategy = IDENTITY)
    @Column(name = "pid", nullable = false)
    private Integer pid;

    @Column(name = "rent")
    private double rent;

    @Column(name = "propertyType")
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(name = "inspection")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inspection;

    @Column(name = "listed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date listedDate;

    @Column(name = "hasAc")
    private Boolean hasAc = false;

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

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
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
    

    public PropertyEntity() {}
}
