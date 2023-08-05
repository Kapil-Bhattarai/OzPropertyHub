package oz.user;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.faces.bean.SessionScoped;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserController {
    
    @PersistenceContext
    private EntityManager entityManager;

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
    
}



