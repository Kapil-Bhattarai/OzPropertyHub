package oz.user;

import oz.OzEJB;
import jakarta.ejb.Stateless;

@Stateless
public class OzUserEJB extends OzEJB {

    public Boolean addUser(UserEntity user) {
        try {
            entityManager.persist(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
