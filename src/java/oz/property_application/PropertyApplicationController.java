package oz.property_application;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Date;
import java.util.Map;
import org.primefaces.model.file.UploadedFile;
import oz.ApplicationStatus;
import oz.SalaryType;
import oz.property.PropertyEntity;
import oz.user.UserEntity;

@ManagedBean(name = "applyPropertyBean")
@ViewScoped
public class PropertyApplicationController {

    @PersistenceContext
    private EntityManager em;

    private Date moveInDate;
    private ApplicationStatus status = ApplicationStatus.PENDING;
    private double offeredRent;
    private String leaseTermInMonths = "6";
    private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private String phone;
    private String address;
    private boolean isEmployed = true;
    private Double salary;
    private SalaryType salaryType;
    private int noOfCats = 0;
    private int noOfDogs = 0;
    private int noOfOtherPets = 0;
    private UserEntity agent, user;
    private PropertyEntity property;

    private UploadedFile primaryDocument, secondaryDocument, incomeDocument, otherDocuement;

    private PropertyApplicationEntity propertyEntity;

    @EJB
    private PropertyApplicationEJB propertyApplicationEJB;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();

        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        
        String uid = params.get("uid");
        String pid = params.get("pid");
        String aid = params.get("aid");

        agent = em.find(UserEntity.class, Integer.parseInt(aid));
        user = em.find(UserEntity.class, Integer.parseInt(uid));
        property = em.find(PropertyEntity.class, Integer.parseInt(pid));

    }

   
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

    public SalaryType[] getSalaryTypes() {
        return SalaryType.values();
    }

    public String submit() {

        propertyEntity = new PropertyApplicationEntity();

        propertyEntity.setFirstName(firstName);
        propertyEntity.setLastName(lastName);
        propertyEntity.setAddress(address);
        propertyEntity.setMoveInDate(moveInDate);
        propertyEntity.setBio(bio);

        propertyEntity.setEmail(email);
        propertyEntity.setIsEmployed(isEmployed);
        propertyEntity.setPhone(phone);

        propertyEntity.setLeaseTermInMonths(leaseTermInMonths);

        propertyEntity.setIsEmployed(isEmployed);
        propertyEntity.setSalary(salary);
        propertyEntity.setSalaryType(salaryType);

        propertyEntity.setNoOfDogs(noOfDogs);
        propertyEntity.setNoOfCats(noOfCats);
        propertyEntity.setNoOfOtherPets(noOfOtherPets);

        propertyEntity.setStatus(ApplicationStatus.PENDING);
        propertyEntity.setAgent(agent);
        propertyEntity.setUser(user);
        propertyEntity.setProperty(property);

        if(propertyApplicationEJB.addApplyPropertyDetails(propertyEntity)) {
            return "/dashboard/user/user_dashboard.faces?faces-redirect=true";
        } else {
            return "/property-detail.faces?id="+property.getPid();
        }
        //System.out.println(this);
        
    }

    @Override
    public String toString() {
        return "PropertyApplicationController{" + "moveInDate=" + moveInDate + ", status=" + status + ", offeredRent=" + offeredRent + ", leaseTermInMonths=" + leaseTermInMonths + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", bio=" + bio + ", phone=" + phone + ", address=" + address + ", isEmployed=" + isEmployed + ", salary=" + salary + ", salaryType=" + salaryType + ", noOfCats=" + noOfCats + ", noOfDogs=" + noOfDogs + ", noOfOtherPets=" + noOfOtherPets + ", secondaryDocument=" + secondaryDocument + ", incomeDocument=" + incomeDocument + '}';
    }

}
