package oz.property_application;

import config.ConfigEJB;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Map;
import org.primefaces.model.file.UploadedFile;
import oz.ApplicationStatus;
import oz.MessageType;
import oz.SalaryType;
import oz.Util;
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
    private String primaryImageUrl, secondaryImageUrl, incomeImageUrl, otherImageUrl;
    private PropertyApplicationEntity propertyEntity;

    @EJB
    private PropertyApplicationEJB propertyApplicationEJB;

    @EJB
    private ConfigEJB configEJB;
        
    private boolean shouldDisableUI = false;

    int uid, pid, aid;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();

        uid = Integer.parseInt(params.get("uid"));
        pid = Integer.parseInt(params.get("pid"));
        aid = Integer.parseInt(params.get("aid"));

        agent = em.find(UserEntity.class, aid);
        user = em.find(UserEntity.class, uid);
        property = em.find(PropertyEntity.class, pid);

        PropertyApplicationEntity propertyApplicationEntity = propertyApplicationEJB.checkApplicationForUpdate(uid, pid, aid);
        if (propertyApplicationEntity != null && "true".equals(params.get("reload"))) {
            shouldDisableUI = true;
            firstName = propertyApplicationEntity.getFirstName();
            lastName = propertyApplicationEntity.getLastName();
            moveInDate = propertyApplicationEntity.getMoveInDate();
            status = propertyApplicationEntity.getStatus();
            offeredRent = propertyApplicationEntity.getOfferedRent();
            leaseTermInMonths = propertyApplicationEntity.getLeaseTermInMonths();
            email = propertyApplicationEntity.getEmail();
            bio = propertyApplicationEntity.getBio();
            phone = propertyApplicationEntity.getPhone();
            address = propertyApplicationEntity.getAddress();
            isEmployed = propertyApplicationEntity.isIsEmployed();
            salary = propertyApplicationEntity.getSalary();
            salaryType = propertyApplicationEntity.getSalaryType();

            noOfCats = propertyApplicationEntity.getNoOfCats();
            noOfDogs = propertyApplicationEntity.getNoOfDogs();
            noOfOtherPets = propertyApplicationEntity.getNoOfOtherPets();

            property = propertyApplicationEntity.getProperty();

            primaryImageUrl = propertyApplicationEntity.getPrimaryImageUrl();
            secondaryImageUrl = propertyApplicationEntity.getSecondaryImageUrl();
            incomeImageUrl = propertyApplicationEntity.getIncomeImageUrl();
            otherImageUrl = propertyApplicationEntity.getOtherImageUrl();
        }

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

    public UploadedFile getOtherDocuement() {
        return otherDocuement;
    }

    public void setOtherDocuement(UploadedFile otherDocuement) {
        this.otherDocuement = otherDocuement;
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

    public SalaryType[] getSalaryTypes() {
        return SalaryType.values();
    }

    public boolean isShouldDisableUI() {
        return shouldDisableUI;
    }

    public void setShouldDisableUI(boolean shouldDisableUI) {
        this.shouldDisableUI = shouldDisableUI;
    }

    public ApplicationStatus[] getApplicationStatus() {
        return ApplicationStatus.values();
    }

    public String updateApplication() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();

        agent = em.find(UserEntity.class, aid);
        user = em.find(UserEntity.class, uid);
        property = em.find(PropertyEntity.class, pid);

        PropertyApplicationEntity propertyApplicationEntity = propertyApplicationEJB.checkApplicationForUpdate(uid, pid, aid);
        if (propertyApplicationEntity != null) {
            propertyApplicationEntity.setStatus(status);
            propertyApplicationEJB.updateApplicationStatus(propertyApplicationEntity);
            if (null != status) switch (status) {
            case ACCEPTED -> // send lease agreement
                Util.sendEmail(user.getEmail(), agent.getEmail(), configEJB.getConfigByKey(MessageType.MESSAGE_APPLICATION_APPROVED.name()).getValue(),
                        "Congratulation your application has been approved for "
                                +property.getAddress().getUnit()+" "+
                                property.getAddress().getStreet_name()+" "+
                                property.getAddress().getStreet_number()+" "+
                                property.getAddress().getSuburb()+" \n"+
                                        "Plase find your lease Agrement:\n"+
                                        "Duration: "+leaseTermInMonths+"\n"+
                                                "Rent: "+offeredRent+"\n"+
                                                        "MoveIn Date:"+moveInDate+"\n \n"+
                                "Regards\nOZPropertyHub"
                );
            case REJECTED -> // send rejection message
                Util.sendEmail(user.getEmail(), agent.getEmail(), configEJB.getConfigByKey(MessageType.MESSAGE_APPLICATION_REJECTED.name()).getValue(),
                        "Your application has been rejected for "
                                +property.getAddress().getUnit()+" "+
                                property.getAddress().getStreet_name()+" "+
                                property.getAddress().getStreet_number()+" "+
                                property.getAddress().getSuburb()+" \n"
                );
            case PENDING -> // send pending status to user
                Util.sendEmail(user.getEmail(), agent.getEmail(), configEJB.getConfigByKey(MessageType.MESSAGE_PENDING_REQUEST.name()).getValue(),
                        "Your application has been moved to pending state for "
                                +property.getAddress().getUnit()+" "+
                                property.getAddress().getStreet_name()+" "+
                                property.getAddress().getStreet_number()+" "+
                                property.getAddress().getSuburb());
            default -> {
            }
        }
            return "/dashboard/agent/application_dashboard.faces?faces-redirect=true";
        }
        return null;
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
        propertyEntity.setOfferedRent(offeredRent);

        propertyEntity.setApplicationDate(new Date());
        propertyEntity.setNoOfDogs(noOfDogs);
        propertyEntity.setNoOfCats(noOfCats);
        propertyEntity.setNoOfOtherPets(noOfOtherPets);

        if (primaryDocument != null || primaryDocument.getSize() == 0) {
            try {
                String fileName = primaryDocument.getFileName();
                String fileLocation = System.getProperty("OZPROPERTYHUB_UPLOAD_LOCATION") + "/" + fileName;
                try ( InputStream inputStream = primaryDocument.getInputStream()) {
                    Files.copy(inputStream, Paths.get(fileLocation), StandardCopyOption.REPLACE_EXISTING);
                    this.primaryImageUrl = fileName;
                    propertyEntity.setPrimaryImageUrl(primaryImageUrl);
                } catch (IOException e) {
                    System.out.println(e);
                    // Handle the exception
                }
                // Process the file content, save it, or do whatever you need.
            } catch (Exception e) {
                // Handle the exception
                System.out.println(e);
            }
        } else if (primaryImageUrl != null) {
            propertyEntity.setPrimaryImageUrl(primaryImageUrl);
        }

        if (secondaryDocument != null || secondaryDocument.getSize() == 0) {
            try {
                String fileName = secondaryDocument.getFileName();
                String fileLocation = System.getProperty("OZPROPERTYHUB_UPLOAD_LOCATION") + "/" + fileName;
                try ( InputStream inputStream = secondaryDocument.getInputStream()) {
                    Files.copy(inputStream, Paths.get(fileLocation), StandardCopyOption.REPLACE_EXISTING);
                    this.secondaryImageUrl = fileName;
                    propertyEntity.setSecondaryImageUrl(secondaryImageUrl);
                } catch (IOException e) {
                    System.out.println(e);
                    // Handle the exception
                }
                // Process the file content, save it, or do whatever you need.
            } catch (Exception e) {
                // Handle the exception
                System.out.println(e);
            }
        } else if (secondaryImageUrl != null) {
            propertyEntity.setSecondaryImageUrl(secondaryImageUrl);
        }

        if (incomeDocument != null || incomeDocument.getSize() == 0) {
            try {
                String fileName = incomeDocument.getFileName();
                String fileLocation = System.getProperty("OZPROPERTYHUB_UPLOAD_LOCATION") + "/" + fileName;
                try ( InputStream inputStream = incomeDocument.getInputStream()) {
                    Files.copy(inputStream, Paths.get(fileLocation), StandardCopyOption.REPLACE_EXISTING);
                    this.incomeImageUrl = fileName;
                    propertyEntity.setIncomeImageUrl(incomeImageUrl);
                } catch (IOException e) {
                    System.out.println(e);
                    // Handle the exception
                }
                // Process the file content, save it, or do whatever you need.
            } catch (Exception e) {
                // Handle the exception
                System.out.println(e);
            }
        } else if (incomeImageUrl != null) {
            propertyEntity.setIncomeImageUrl(incomeImageUrl);
        }

        if (otherDocuement != null || otherDocuement.getSize() == 0) {
            try {
                String fileName = otherDocuement.getFileName();
                String fileLocation = System.getProperty("OZPROPERTYHUB_UPLOAD_LOCATION") + "/" + fileName;
                try ( InputStream inputStream = otherDocuement.getInputStream()) {
                    Files.copy(inputStream, Paths.get(fileLocation), StandardCopyOption.REPLACE_EXISTING);
                    this.otherImageUrl = fileName;
                    propertyEntity.setOtherImageUrl(otherImageUrl);
                } catch (IOException e) {
                    System.out.println(e);
                    // Handle the exception
                }
                // Process the file content, save it, or do whatever you need.
            } catch (Exception e) {
                // Handle the exception
                System.out.println(e);
            }
        } else if (otherImageUrl != null) {
            propertyEntity.setIncomeImageUrl(otherImageUrl);
        }

        propertyEntity.setStatus(ApplicationStatus.PENDING);

        propertyEntity.setAgent(agent);
        propertyEntity.setUser(user);
        propertyEntity.setProperty(property);

        if (propertyApplicationEJB.addApplyPropertyDetails(propertyEntity)) {
            return "/dashboard/user/user_dashboard.faces?faces-redirect=true";
        } else {
            return "/property-detail.faces?id=" + property.getPid();
        }
        //System.out.println(this);

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

    private String getSubmittedFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");

        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";
    }

    @Override
    public String toString() {
        return "PropertyApplicationController{" + "moveInDate=" + moveInDate + ", status=" + status + ", offeredRent=" + offeredRent + ", leaseTermInMonths=" + leaseTermInMonths + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", bio=" + bio + ", phone=" + phone + ", address=" + address + ", isEmployed=" + isEmployed + ", salary=" + salary + ", salaryType=" + salaryType + ", noOfCats=" + noOfCats + ", noOfDogs=" + noOfDogs + ", noOfOtherPets=" + noOfOtherPets + ", secondaryDocument=" + secondaryDocument + ", incomeDocument=" + incomeDocument + '}';
    }

}
