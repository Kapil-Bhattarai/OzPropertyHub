package oz.user;

import oz.OzEJB;
import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import oz.UserType;
import oz.property_application.PropertyApplicationEntity;

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

    public UserEntity updateUser(UserEntity user) {
        return entityManager.merge(user);
    }

    public List<UserEntity> getActiveUsersByType(UserType type, Boolean isActive) {
        try {
            return entityManager.createNamedQuery("UserEntity.findActiveUserByType", UserEntity.class)
                    .setParameter("type", type)
                    .setParameter("isLive", isActive).getResultList();
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

    public String activateAgent(UserEntity user) {
        try {
            user.setIsLive(Boolean.TRUE);
            entityManager.merge(user);
            return "success";
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }

    public String updateApplicationStatus(PropertyApplicationEntity application) {
        try {
            entityManager.merge(application);
            return "success";
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }

    public List<PropertyApplicationEntity> getPropertiesApplicationByUser(Integer userId) {
        try {
            return entityManager.createNamedQuery(PropertyApplicationEntity.QUERY_GET_ALL_APPLICATIONS, PropertyApplicationEntity.class)
                    .setParameter("userId", userId).getResultList();
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<PropertyApplicationEntity> getPropertiesApplicationByAgent(Integer agentId) {
        try {
            return entityManager.createNamedQuery(PropertyApplicationEntity.QUERY_GET_ALL_APPLICATIONS_TO_AGENT, PropertyApplicationEntity.class)
                    .setParameter("agentId", agentId).getResultList();
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<PropertyApplicationEntity> getPropertiesApplicationByAgent(Integer agentId, Integer propertyId) {
        try {
            return entityManager.createNamedQuery(PropertyApplicationEntity.QUERY_GET_ALL_APPLICATIONS_TO_AGENT_BY_PROPERTY, PropertyApplicationEntity.class)
                    .setParameter("agentId", agentId)
                    .setParameter("propertyId", propertyId).getResultList();
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return new ArrayList<>();
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
