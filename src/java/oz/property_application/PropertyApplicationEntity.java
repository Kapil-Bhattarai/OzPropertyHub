package oz.property_application;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import oz.SalaryType;
import oz.ApplicationStatus;
import oz.property.PropertyEntity;
import oz.user.UserEntity;

@Entity
@Table(name = "OZ_PROPERTY_APPLICATION")
@NamedQueries({
    @NamedQuery(
            name = "PropertyApplicationEntity.findPropertyTypeByUserIdAndPropertyId",
            query = "SELECT pa FROM PropertyApplicationEntity pa WHERE pa.id.userId = :userId AND pa.id.propertyId = :propertyId"
    ),
    @NamedQuery(
            name = "PropertyApplicationEntity.findAllAppliedProperties",
            query = "SELECT pa FROM PropertyApplicationEntity pa WHERE pa.id.userId = :userId"
    ),
    @NamedQuery(
        name = "PropertyApplicationEntity.findAllApplicationByAgentByProperty",
        query = "SELECT pa FROM PropertyApplicationEntity pa WHERE pa.id.agentId = :agentId AND pa.id.propertyId = :propertyId"
    ),
    @NamedQuery(
        name = "PropertyApplicationEntity.findAllApplicationByAgent",
        query = "SELECT pa FROM PropertyApplicationEntity pa WHERE pa.id.agentId = :agentId"
    ),
    @NamedQuery(
        name = "PropertyApplicationEntity.getAcceptedApplication",
        query = "SELECT pa FROM PropertyApplicationEntity pa WHERE pa.id.propertyId = :propertyId AND pa.status = :applicationStatus"
    ),
     @NamedQuery(
            name = "PropertyApplicationEntity.findPropertyTypeForUpdate",
            query = "SELECT pa FROM PropertyApplicationEntity pa WHERE pa.id.userId = :userId AND pa.id.propertyId = :propertyId AND pa.id.agentId = :agentId"
    ),
})
public class PropertyApplicationEntity implements Serializable {

    public static final String QUERY_GET_APPLICATION_STATUS = "PropertyApplicationEntity.findPropertyTypeByUserIdAndPropertyId";
    public static final String QUERY_GET_ALL_APPLICATIONS = "PropertyApplicationEntity.findAllAppliedProperties";
     public static final String QUERY_GET_ALL_APPLICATIONS_TO_AGENT_BY_PROPERTY = "PropertyApplicationEntity.findAllApplicationByAgentByProperty";
     public static final String QUERY_GET_ALL_APPLICATIONS_TO_AGENT = "PropertyApplicationEntity.findAllApplicationByAgent";
     public static final String QUERY_GET_APPLICATION_TO_UPDATE = "PropertyApplicationEntity.findPropertyTypeForUpdate";
     public static final String QUERY_ACCEPTED_APPLICATION = "PropertyApplicationEntity.getAcceptedApplication";

    @EmbeddedId
    private PropertyApplicationId id;

    @Column(name = "moveInDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date moveInDate;

    @Column(name = "applicationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applicationDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "offeredRent")
    private double offeredRent;

    @Column(name = "leaseTermInMonths")
    private String leaseTermInMonths = "6";

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, length = 128)
    private String email;

    @Lob
    @Column(name = "bio", nullable = true, length = 65535)
    private String bio;

    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "isEmployed")
    private boolean isEmployed = true;

    @Column(name = "salary")
    private Double salary;

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

    @ManyToOne
    @MapsId("agentId")
    @JoinColumn(name = "agentId")
    private UserEntity agent;

    @Column(name = "primaryImageUrl", nullable = true)
    private String primaryImageUrl;

    @Column(name = "secondaryImageUrl", nullable = true)
    private String secondaryImageUrl;

    @Column(name = "incomeImageUrl", nullable = true)
    private String incomeImageUrl;

    @Column(name = "otherImageUrl", nullable = true)
    private String otherImageUrl;

    public PropertyApplicationId getId() {
        return id;
    }

    public void setId(PropertyApplicationId id) {
        this.id = id;
    }

    public ApplicationStatus getUserStatus() {
        return status;
    }

    public void setUserStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Date getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(Date moveInDate) {
        this.moveInDate = moveInDate;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public double getOfferedRent() {
        return offeredRent;
    }

    public void setOfferedRent(double offeredRent) {
        this.offeredRent = offeredRent;
    }

    public String getLeaseTermInMonths() {
        return leaseTermInMonths;
    }

    public void setLeaseTermInMonths(String leaseTermInMonths) {
        this.leaseTermInMonths = leaseTermInMonths;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIsEmployed() {
        return isEmployed;
    }

    public void setIsEmployed(boolean isEmployed) {
        this.isEmployed = isEmployed;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
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

    public UserEntity getAgent() {
        return agent;
    }

    public void setAgent(UserEntity agent) {
        this.agent = agent;
    }

    public PropertyApplicationEntity() {
    }

    @Override
    public String toString() {
        return "PropertyApplicationEntity{" + "id=" + id + ", moveInDate=" + moveInDate + ", applicationDate=" + applicationDate + ", status=" + status + ", offeredRent=" + offeredRent + ", leaseTermInMonths=" + leaseTermInMonths + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", bio=" + bio + ", phone=" + phone + ", address=" + address + ", isEmployed=" + isEmployed + ", salary=" + salary + ", salaryType=" + salaryType + ", noOfCats=" + noOfCats + ", noOfDogs=" + noOfDogs + ", noOfOtherPets=" + noOfOtherPets + ", property=" + property + ", user=" + user + ", agent=" + agent +'}';
    }

    public String getPrimaryImageUrl() {
        return primaryImageUrl;
    }

    public void setPrimaryImageUrl(String primaryImageUrl) {
        this.primaryImageUrl = primaryImageUrl;
    }

    public String getSecondaryImageUrl() {
        return secondaryImageUrl;
    }

    public void setSecondaryImageUrl(String secondaryImageUrl) {
        this.secondaryImageUrl = secondaryImageUrl;
    }

    public String getIncomeImageUrl() {
        return incomeImageUrl;
    }

    public void setIncomeImageUrl(String incomeImageUrl) {
        this.incomeImageUrl = incomeImageUrl;
    }

    public String getOtherImageUrl() {
        return otherImageUrl;
    }

    public void setOtherImageUrl(String otherImageUrl) {
        this.otherImageUrl = otherImageUrl;
    }

}
