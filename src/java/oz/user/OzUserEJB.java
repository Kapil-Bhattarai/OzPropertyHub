package oz.user;

import oz.OzEJB;
import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import oz.UserType;

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

    public List<UserEntity> getActiveUsersByType(UserType type) {
        try {
            return entityManager.createNamedQuery("UserEntity.findActiveUserByType", UserEntity.class)
                .setParameter("type", type).getResultList();
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return new ArrayList<>();
        }
    }
      
    public String suspendAgent(UserEntity user) {
        try {
            user.setIsLive(Boolean.FALSE);
            entityManager.merge(user);
            return "success";
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }
    
    public String deleteAgent(UserEntity user) {
        
        try {
            //entityManager.remove(user);
             UserEntity userToDelete = entityManager.find(UserEntity.class, user.getId());
        
        if (userToDelete != null) {
            UserEntity managedUser = entityManager.merge(userToDelete); // Re-attach the entity
            entityManager.remove(managedUser); // Now remove the managed entity
        }
            return "success";
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }
     
    //Retrieve a user by email address
    public UserEntity getUserbyEmail(String email) {
        try {
            return entityManager.createNamedQuery("UserEntity.findByEmail", UserEntity.class).
                    setParameter("email", email).getResultList().get(0);
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }

    public UserEntity getUserbyEmailAndPassword(String email, String password) {
        try {
            return entityManager.createNamedQuery("UserEntity.findByEmailAndPassword", UserEntity.class).
                    setParameter("email", email)
                    .setParameter("password", password)
                    .getResultList().get(0);

        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }
}
