package oz.user;

import config.ConfigEJB;
import config.ConfigEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.primefaces.model.file.UploadedFile;
import oz.ApplicationStatus;
import oz.MessageType;
import oz.PropertyType;
import oz.UserType;
import static oz.UserType.ADMIN;
import static oz.UserType.AGENT;
import oz.Util;
import oz.newsletter_subscriber.NewsletterSubscriberEJB;
import oz.newsletter_subscriber.NewsletterSubscriberEntity;
import oz.property_application.PropertyApplicationEntity;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserController {

    @PersistenceContext
    private EntityManager em;

    private Integer id;
    private String firstName;
    private String password;
    private String confirmPassword;
    private String lastName;
    private String email;
    private String bio;
    private String phone;
    private String address;
    private Date since;
    private Boolean isLive = true;
    private UploadedFile mainImage;
    private String mainImageUrl;
    private String newEmail;
    private String source = "list";
    private UserType type;

    @EJB
    private OzUserEJB userEJB;

    @EJB
    private NewsletterSubscriberEJB newsletterEJB;

    @EJB
    private ConfigEJB configEJB;

    private UserEntity ozUser;

    private String formUserName;
    private String formEmail;
    private String formNumber;
    private String formMessage;

    private ApplicationStatus applicationStatus;

    private String config;

    @PostConstruct
    public void init() {
        ozUser = new UserEntity();
        String bio = "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.";
        registerUser("admin", "Deo", HashConvert("password"), "123456789", "200,Kent Street, NSW, Australia", UserType.ADMIN, "admin@gmail.com", true, bio);
        registerUser("Agent1", "Agent 1", HashConvert("password"), "123456789", "200,Kent Street, NSW, Australia", UserType.AGENT, "test1@gmail.com", true, bio);
        registerUser("Agent2", "User 2", HashConvert("password"), "123456789", "200,Kent Street, NSW, Australia", UserType.AGENT, "test2@gmail.com", true, bio);
        registerUser("Agent3", "User 3", HashConvert("password"), "123456789", "200,Kent Street, NSW, Australia", UserType.AGENT, "test3@gmail.com", false, bio);
        registerUser("User1", "User 1", HashConvert("password"), "123456789", "200,Kent Street, NSW, Australia", UserType.USER, "user1@gmail.com", true, bio);
        registerUser("User2", "User 2", HashConvert("password"), "123456789", "200,Kent Street, NSW, Australia", UserType.USER, "user2@gmail.com", true, bio);

        addConfig(MessageType.ERROR_USER_NOT_FOUND, "User details not found!. Please enter correct details.");
        addConfig(MessageType.MESSAGE_PENDING_REQUEST, "User details not found!. Your request is still pending.");
        addConfig(MessageType.ERROR_PROFILE_FETCH_ERROR, "Something went wrong getting your details.");
        addConfig(MessageType.MESSAGE_ACCOUNT_SUSPENSION, "Your account has been suspended. Please contact admin for further information.");
        addConfig(MessageType.MESSAGE_ACCOUNT_DELETION, "Your account has been deleted from Oz Property Hub. Please contact admin for further information.");
        addConfig(MessageType.MESSAGE_ACCOUNT_ACTIVATION, "Congratulations!. Your account has been activated. Now, you can add properties to OZ Property Hub.");
        addConfig(MessageType.ERROR_WHILE_SENDING_EMAIL, "There were some errors while sending emails.");

        addConfig(MessageType.MESSAGE_APPLICATION_APPROVED, "Congratulation! Your application has been approved.");
        addConfig(MessageType.MESSAGE_APPLICATION_REJECTED, "Your application has been rejected.");
        addConfig(MessageType.MESSAGE_APPLICATION_PENDING, "Your application has been moved to pending state.");

    }

    public String loginUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserEntity user = userEJB.getUserbyEmailAndPassword(email, HashConvert(password));
        if (user == null) {
            // not registered before.
            Util.showMessage(context, FacesMessage.SEVERITY_ERROR, "User details not found!. Please enter correct details.", null);
            return "";
        } else {
            System.out.println(user.getIsLive().toString());
            // registered user
            if (!user.getIsLive()) {
                // request not accepeted by admin. Show error message
                Util.showMessage(context, FacesMessage.SEVERITY_ERROR, "Your request is still pending.", null);
                return null;
            } else {
                UserType type = user.getType();
                if (type == null) {
                    return "";
                } else {

                    user = userEJB.getUserbyEmail(email);
                    setUserData(user);
                    System.out.println(type);

                    return switch (type) {
                        case ADMIN ->
                            "/dashboard/admin/admin_dashboard.faces?faces-redirect=true";
                        case AGENT ->
                            "/dashboard/agent/agent_dashboard.faces?faces-redirect=true";
                        default ->
                            "/dashboard/user/user_dashboard.faces?faces-redirect=true";
                    };
                }
            }
        }
    }

    public void updateProfile() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserEntity user = userEJB.getUserbyEmail(email);
        if (user == null) {
            // not registered before.
            Util.showMessage(context, FacesMessage.SEVERITY_ERROR, configEJB.getConfigByKey(MessageType.ERROR_PROFILE_FETCH_ERROR.name()).getValue(), null);
            //return "";
        } else {

            if (mainImage != null || mainImage.getSize() == 0) {
                try {
                    String fileName = mainImage.getFileName();
                    String fileLocation = System.getProperty("OZPROPERTYHUB_UPLOAD_LOCATION") + "/" + fileName;
                    try (InputStream inputStream = mainImage.getInputStream()) {
                        Files.copy(inputStream, Paths.get(fileLocation), StandardCopyOption.REPLACE_EXISTING);
                        user.setMainImage(fileName);
                    } catch (IOException e) {
                        System.out.println(e);
                        // Handle the exception
                    }
                    // Process the file content, save it, or do whatever you need.
                } catch (Exception e) {
                    // Handle the exception
                    System.out.println(e);
                }
            } else if (mainImageUrl != null) {
                user.setMainImage(mainImageUrl);
            }

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(newEmail);
            user.setBio(bio);
            user.setPhone(phone);
            user.setAddress(address);
            userEJB.updateUser(user);
            Util.showMessage(context, FacesMessage.SEVERITY_INFO, "Profile successfully updated.", null);
            this.setMainImageUrl(user.getMainImage());
            this.setEmail(newEmail);
        }
    }

    public boolean isLoggedIn() {
        return id != null;
    }

    public boolean isUser() {
        return type == UserType.USER;
    }

    public boolean showAgentDashboard() {
        return id != null && type == UserType.AGENT;
    }

    public boolean showAdminDashboard() {
        return id != null && type == UserType.ADMIN;
    }

    public boolean showUserDashboard() {
        return id != null && type == UserType.USER;
    }

    public void setSource(String s) {
        source = s;
    }

    public String getSource() {
        return source;
    }

    public List<UserEntity> getActiveUsersByType(Boolean isActive) {
        List<UserEntity> list = userEJB.getActiveUsersByType(UserType.AGENT, isActive);
        return list;
    }

    public String suspendAgent(UserEntity user) {
        Util.sendEmail(user.getEmail(), "admin@gmail.com",
                "Account Suspension",
                configEJB.getConfigByKey(MessageType.MESSAGE_ACCOUNT_SUSPENSION.name()).getValue());
        if (userEJB.suspendAgent(user) != null) {
            return "/dashboard/admin/admin_dashboard.faces?faces-redirect=true";
        } else {
            return null;
        }
    }

    public String activateAgent(UserEntity user) {
        Util.sendEmail(user.getEmail(), "admin@gmail.com",
                "Account activation",
                configEJB.getConfigByKey(MessageType.MESSAGE_ACCOUNT_ACTIVATION.name()).getValue());
        if (userEJB.activateAgent(user) != null) {
            return "/dashboard/admin/admin_pending_request_dashboard.faces?faces-redirect=true";
        } else {
            return null;
        }
    }

    public String deleteAgent(UserEntity user) {
        Util.sendEmail(user.getEmail(), "admin@gmail.com",
                "Account deletion",
                configEJB.getConfigByKey(MessageType.MESSAGE_ACCOUNT_DELETION.name()).getValue());
        if (userEJB.deleteAgent(user) != null) {
            return "/dashboard/admin/admin_dashboard.faces?faces-redirect=true";
        } else {
            return null;
        }

    }

    public String registerUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserEntity user = userEJB.getUserbyEmail(email);
        if (user == null) {
            if (!password.equals(confirmPassword)) {
                FacesMessage message = new FacesMessage("");
                context.addMessage(null, message);
                Util.showMessage(context, FacesMessage.SEVERITY_ERROR, "The specified passwords do not match,  please try again!", null);
                return null;
            }

            user = new UserEntity();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(HashConvert(password));
            user.setPhone(phone);
            user.setAddress(address);
            user.setType(getUserType(isLive));
            user.setSince(new Date());
            user.setIsLive(!isLive);
            user.setEmail(email);

            if (userEJB.addUser(user)) {
                id = user.getId();
                String dashboard = "/dashboard/agent/agent_pending_request.faces?faces-redirect=true";
                String body = firstName + " " + lastName + " has requested to join the site as property manager.";
                if (isLive) {
                    Util.sendEmail("admin@gmail.com", email, "Request for Agent Registration", body);
                } else {
                    dashboard = "/dashboard/user/user_dashboard.faces?faces-redirect=true";
                }
                return dashboard;
            } else {
                Util.showMessage(context, FacesMessage.SEVERITY_ERROR, "Error creating user!", "Unexpected error when creating your account.  Please contact the system Administrator");
                return null;
            }
        } else {
            Util.showMessage(context, FacesMessage.SEVERITY_ERROR, "User already exits", "Unexpected error when creating your account.  Please contact the system Administrator");
            return null;

        }

    }

    public void addConfig(MessageType key, String value) {
        ConfigEntity config = configEJB.getConfigByKey(key.name());
        if (config == null) {
            configEJB.createConfig(new ConfigEntity(key.name(), key.getDisplayName(), value));
        }
    }

    public String contactForm() {
        if (formEmail.length() > 0 && formUserName.length() > 0 && formMessage.length() > 0 && formNumber.length() > 0) {
            Util.sendEmail("admin@gmail.com", formEmail, "Feedback from " + formUserName, formMessage + "\n Contact Details: \n" + formNumber);
            return "feedback_success.faces?faces-redirect=true";
        } else {
            return null;
        }
    }

    public void registerUser(String firstName, String lastName, String password, String phone, String address, UserType type, String email, boolean isLive, String bio) {
        UserEntity user = userEJB.getUserbyEmail(email);
        if (user == null) {
            user = new UserEntity();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setPhone(phone);
            user.setAddress(address);
            user.setBio(bio);
            user.setType(type);
            user.setSince(new Date());
            user.setIsLive(isLive);
            user.setEmail(email);
            userEJB.addUser(user);
        }
    }

    private void setUserData(UserEntity user) {
        email = user.getEmail();
        newEmail = user.getEmail();
        password = user.getPassword();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        phone = user.getPhone();
        id = user.getId();
        since = user.getSince();
        type = user.getType();
        bio = user.getBio();
        isLive = user.getIsLive();
        address = user.getAddress();
        mainImageUrl = user.getMainImage();
    }

    public void resetUserData() {
        id = null;
        firstName = null;
        lastName = null;
        password = null;
        confirmPassword = null;
        email = null;
        isLive = false;
        phone = null;
        since = null;
        isLive = true;
        address = null;
        bio = null;
        type = UserType.USER;
    }

    private UserType getUserType(boolean isPropertyAgent) {

        if (isPropertyAgent) {
            return UserType.AGENT;
        } else {
            return UserType.USER;
        }
    }

    //Generate the hash code of a password
    public String HashConvert(String oripassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(oripassword.getBytes());
            byte byteData[] = md.digest();
            //convert the byte to hex format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public ApplicationStatus[] getStatus() {
        return ApplicationStatus.values();
    }

    public String updateApplicationStatus(PropertyApplicationEntity application) {
        application.setStatus(applicationStatus);
        if (userEJB.updateApplicationStatus(application) != null) {
            return "/dashboard/user/application_dashboard.faces?faces-redirect=true";
        } else {
            return null;
        }
    }

    public String viewApplicationDetails(PropertyApplicationEntity application) {
        return "apply_property.faces?faces-redirect=true";
    }

    public List<PropertyApplicationEntity> getPropertiesApplicationByUser(Integer userId) {
        return userEJB.getPropertiesApplicationByUser(userId);
    }

    public List<PropertyApplicationEntity> getPropertiesApplicationByAgent(Integer userId) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String propertyId = params.get("id");
        if (propertyId == null) {
            source = "list";
            return userEJB.getPropertiesApplicationByAgent(userId);
        } else {
            source = "singleP";
            return userEJB.getPropertiesApplicationByAgent(userId, Integer.parseInt(propertyId));
        }
    }

    public void subscribeToNewsletter() {
        if (email == null || email.isEmpty()) {
            return;
        }

        if (newsletterEJB.canAddToTheList(email)) {
            newsletterEJB.addEmail(new NewsletterSubscriberEntity(email));
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Successfully subscribed to the mailing list."));
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "You are already in the mailing list."));
        }

    }

    public UserEntity getOzUser() {
        return ozUser;
    }

    public void setOzUser(UserEntity ozUser) {
        this.ozUser = ozUser;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstName;
    }

    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        System.out.println("get email");
        System.out.println(email);
        return email;
    }

    public void setEmail(String email) {
        System.out.println("set email");
        System.out.println(email);
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

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public Boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(Boolean isLive) {
        this.isLive = isLive;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public OzUserEJB getUserEJB() {
        return userEJB;
    }

    public void setUserEJB(OzUserEJB userEJB) {
        this.userEJB = userEJB;
    }

    public String getFirstName() {
        if (firstName == null || firstName.length() == 0) {
            return firstName;
        }
        return firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
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

    public String getFormUserName() {
        return formUserName;
    }

    public void setFormUserName(String formUserName) {
        this.formUserName = formUserName;
    }

    public String getFormEmail() {
        return formEmail;
    }

    public void setFormEmail(String formEmail) {
        this.formEmail = formEmail;
    }

    public String getFormNumber() {
        return formNumber;
    }

    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
    }

    public String getFormMessage() {
        return formMessage;
    }

    public void setFormMessage(String formMessage) {
        this.formMessage = formMessage;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UploadedFile getMainImage() {
        return mainImage;
    }

    public void setMainImage(UploadedFile mainImage) {
        this.mainImage = mainImage;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Override
    public String toString() {
        return "UserController{" + "id=" + id + ", firstName=" + firstName + ", password=" + password + ", confirmPassword=" + confirmPassword + ", lastName=" + lastName + ", email=" + email + ", bio=" + bio + ", phone=" + phone + ", address=" + address + ", since=" + since + ", isLive=" + isLive + ", mainImage=" + mainImage + ", mainImageUrl=" + mainImageUrl + ", newEmail=" + newEmail + ", type=" + type + ", userEJB=" + userEJB + ", newsletterEJB=" + newsletterEJB + ", ozUser=" + ozUser + ", formUserName=" + formUserName + ", formEmail=" + formEmail + ", formNumber=" + formNumber + ", formMessage=" + formMessage + '}';
    }

}
