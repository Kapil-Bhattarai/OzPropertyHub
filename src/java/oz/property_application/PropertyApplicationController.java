package oz.property_application;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Date;
import org.primefaces.model.file.UploadedFile;
import oz.ApplicationStatus;
import oz.SalaryType;

@ManagedBean(name = "applyPropertyBean")
@ViewScoped
public class PropertyApplicationController {

    @PersistenceContext
    private EntityManager em;

    private Date moveInDate;
    private ApplicationStatus status = ApplicationStatus.PENDING;
    private double offeredRent;
    private int leaseTermInMonths = 6;
    private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private String phone;
    private String address;
    private boolean isEmployed = true;
    private Float salary;
    private SalaryType salaryType;
    private int noOfCats = 0;
    private int noOfDogs = 0;
    private int noOfOtherPets = 0;

    private UploadedFile primaryDocument, secondaryDocument, incomeDocument, otherDocuement;

    public Date getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(Date moveInDate) {
        this.moveInDate = moveInDate;
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

    public int getLeaseTermInMonths() {
        return leaseTermInMonths;
    }

    public void setLeaseTermInMonths(int leaseTermInMonths) {
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

    public UploadedFile getPrimaryDocument() {
        return primaryDocument;
    }

    public void setPrimaryDocument(UploadedFile primaryDocument) {
        this.primaryDocument = primaryDocument;
    }

    public UploadedFile getSecondaryDocument() {
        return secondaryDocument;
    }

    public void setSecondaryDocument(UploadedFile secondaryDocument) {
        this.secondaryDocument = secondaryDocument;
    }

    public UploadedFile getIncomeDocument() {
        return incomeDocument;
    }

    public void setIncomeDocument(UploadedFile incomeDocument) {
        this.incomeDocument = incomeDocument;
    }

    public UploadedFile getOtherDocuement() {
        return otherDocuement;
    }

    public void setOtherDocuement(UploadedFile otherDocuement) {
        this.otherDocuement = otherDocuement;
    }

}