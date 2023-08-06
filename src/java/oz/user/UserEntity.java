package oz.user;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import oz.UserType;

@Entity
@Table(name = "OZ_USER")
@NamedQueries( {
    @NamedQuery(name = "UserEntity.findByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email")
})
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "firstName", nullable = false)
    private String firstname;
    
    @Column(name = "password", nullable = false)
    private String password;
     
    @Column(name = "lastName", nullable = false)
    private String lastname;
       
    @Column(name = "email", nullable = false, length = 128)
    private String email;
    
    @Column(name = "bio", nullable = true)
    private String bio;
      
    @Column(name = "phone", nullable = false, length = 12)
    private String phone;
    
    @Column(name = "since")
    @Temporal(TemporalType.TIMESTAMP)
    private Date since;

    @Column(name = "isLive")
    private Boolean isLive = true;
      
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserType type;
    
     public UserEntity() {}
     
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public UserEntity(Integer id, String firstname, String password, String lastname, String email, String bio, String phone, Date since, UserType type) {
        this.id = id;
        this.firstname = firstname;
        this.password = password;
        this.lastname = lastname;
        this.email = email;
        this.bio = bio;
        this.phone = phone;
        this.since = since;
        this.type = type;
    }
     
}
