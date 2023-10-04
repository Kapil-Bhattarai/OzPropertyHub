package oz.property_application;

import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import oz.ApplicationStatus;
import oz.OzEJB;
import oz.user.UserEntity;

@Stateless
public class PropertyApplicationEJB extends OzEJB {

    public Boolean addApplyPropertyDetails(PropertyApplicationEntity propertyApplication) {
        try {
            entityManager.persist(propertyApplication);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public PropertyApplicationEntity checkApplication(Integer userId, Integer propertyId) {
        try {
            return entityManager.createNamedQuery(PropertyApplicationEntity.QUERY_GET_APPLICATION_STATUS, PropertyApplicationEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("propertyId", propertyId)
                    .getResultList().get(0);
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }

    public PropertyApplicationEntity updateApplicationStatus(PropertyApplicationEntity propertyApplication) {
        return entityManager.merge(propertyApplication);
    }

    public PropertyApplicationEntity checkApplicationForUpdate(Integer userId, Integer propertyId, Integer agentId) {
        try {
            return entityManager.createNamedQuery(PropertyApplicationEntity.QUERY_GET_APPLICATION_TO_UPDATE, PropertyApplicationEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("propertyId", propertyId)
                    .setParameter("agentId", agentId)
                    .getResultList().get(0);
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }

    public boolean canAcceptApplication(Integer propertyId) {
        try {
            int size = entityManager.createNamedQuery(PropertyApplicationEntity.QUERY_ACCEPTED_APPLICATION, PropertyApplicationEntity.class)
                    .setParameter("propertyId", propertyId)
                    .setParameter("applicationStatus", ApplicationStatus.ACCEPTED)
                    .getResultList().size();
            
            return size == 0;
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return false;
        }

    }
}
