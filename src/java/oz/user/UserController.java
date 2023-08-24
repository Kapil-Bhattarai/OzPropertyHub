package oz.user;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import oz.UserType;
import oz.Util;

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
    private Date since;
    private Boolean isLive = true;

    private UserType type;

    @EJB
    private OzUserEJB userEJB;

    private UserEntity ozUser;

    @PostConstruct
    public void init() {
        ozUser = new UserEntity();
    }

    public String loginUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserEntity user = userEJB.getUserbyEmailAndPassword(email, HashConvert(password));
        if (user == null) {
            // not registered before.
            Util.showMessage(context, FacesMessage.SEVERITY_ERROR, "User details not found!. Please enter correct details.", null);
            return "";
        } else {
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
                    
                    return switch (type) {
                        case ADMIN ->
                            "admin_dashboard.faces?faces-redirect=true";
                        case AGENT ->
                            "agent_dashboard.faces?faces-redirect=true";
                        default ->
                            "user_dashboard.faces?faces-redirect=true";
                    };
                }
            }
        }
    }
    
    public boolean isLoggedIn() {
         return id != null;
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

    public String registerUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserEntity user = userEJB.getUserbyEmail(email);
        System.out.println("userValue " + user);
        if (user == null) {
            if (!password.equals(confirmPassword)) {
                FacesMessage message = new FacesMessage("");
                context.addMessage(null, message);
                Util.showMessage(context, FacesMessage.SEVERITY_ERROR, "The specified passwords do not match,  please try again!", null);
                return null;
            }

            user = new UserEntity();
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setPassword(HashConvert(password));
            user.setPhone(phone);
            user.setType(getUserType(isLive));
            user.setSince(new Date());
            user.setIsLive(!isLive);
            user.setEmail(email);

            if (userEJB.addUser(user)) {
                id = user.getId();
                String dashboard = "agent_pending_request.faces?faces-redirect=true";
                String body = firstName + " " + lastName + " has requested to join the site as property manager.";
                if (isLive) {
                    Util.sendEmail("admin@gmail.com", email, "Request for Agent Registration", body);
                } else {
                    dashboard = "user_dashboard.faces?faces-redirect=true";
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

    private void setUserData(UserEntity user) {
        email = user.getEmail();
        password = user.getPassword();
        firstName = user.getFirstname();
        lastName = user.getLastname();
        phone = user.getPhone();
        id = user.getId();
        since = user.getSince();
        type = user.getType();
        bio = user.getBio();
        isLive = user.getIsLive();
    }
    
    public String resetUserData() {
        System.out.println("Reset User Data");
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
        type = UserType.USER;
       return "index.faces?faces-redirect=true";
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

    @Override
    public String toString() {
        return "UserController{ id=" + id + ", firstName=" + firstName + ", password=" + password + ", confirmPassword=" + confirmPassword + ", lastName=" + lastName + ", email=" + email + ", bio=" + bio + ", phone=" + phone + ", since=" + since + ", isLive=" + isLive + ", type=" + type + ", userEJB=" + userEJB + ", ozUser=" + ozUser + '}';
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}