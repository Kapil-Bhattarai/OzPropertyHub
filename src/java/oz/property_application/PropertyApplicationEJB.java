package oz.property_application;

import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import oz.OzEJB;

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
  
}
