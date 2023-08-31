package oz.property;

import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import oz.OzEJB;
import oz.UserType;
import oz.user.UserEntity;

@Stateless
public class PropertyEJB extends OzEJB {

    public Boolean addProperty(PropertyEntity property) {
        try {
            entityManager.persist(property);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<PropertyEntity> getPropertiesByAgent(int id) {
        try {
            return entityManager.createNamedQuery("PropertyEntity.getPropertiesByAgent", PropertyEntity.class)
                    .setParameter("id", id).getResultList();
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public PropertyEntity getProperty(int id) {
        try {
            return entityManager.createNamedQuery("PropertyEntity.getProperty", PropertyEntity.class).setParameter("id", id).getResultList().get(0);
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }
    
    public PropertyEntity updateProperty(PropertyEntity property) {
        return entityManager.merge(property);
    }
    
    public String deleteProperty(PropertyEntity property) {
        
        try {
             PropertyEntity propertyToDelete = entityManager.find(PropertyEntity.class, property.getPid());
        
        if (propertyToDelete != null) {
            PropertyEntity managedProperty = entityManager.merge(propertyToDelete); // Re-attach the entity
            entityManager.remove(managedProperty); // Now remove the managed entity
        }
            return "success";
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }
     
}
