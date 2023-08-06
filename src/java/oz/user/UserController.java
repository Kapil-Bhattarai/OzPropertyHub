package oz.user;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.faces.bean.SessionScoped;
import java.util.Date;
import oz.UserType;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserController {
    
    @PersistenceContext
    private EntityManager entityManager;

    
    private Integer id;
    private String firstname;
    private String password;
    private String lastname;  
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

    public UserEntity getOzUser() {
        return ozUser;
    }

    public void setOzUser(UserEntity ozUser) {
        this.ozUser = ozUser;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
    
    
}



