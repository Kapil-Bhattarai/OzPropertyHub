package oz.property;

import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import oz.OzEJB;
import oz.address.AddressEntity;

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
            return entityManager.createNamedQuery(PropertyEntity.QUERY_GET_PROPERTY_BY_AGENT, PropertyEntity.class)
                    .setParameter("id", id).getResultList();
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<PropertyEntity> getPropertiesForGallery() {
        try {
            return entityManager.createNamedQuery(PropertyEntity.QUERY_GET_PROPERTIES_FOR_GALLERY, PropertyEntity.class)
                    .setParameter("isInGallery", true).getResultList();
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<PropertyEntity> getFeaturedProperties() {
        try {
            return entityManager.createNamedQuery(PropertyEntity.QUERY_GET_FEATURED_PROPERTIES, PropertyEntity.class)
                    .setParameter("isFeatured", true).getResultList();
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public PropertyEntity getProperty(int id) {
        try {
            return entityManager.createNamedQuery(PropertyEntity.QUERY_GET_PROPERTY, PropertyEntity.class).setParameter("id", id).getResultList().get(0);
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }

    public PropertyEntity getPropertyByDate(Date date) {
        try {
            return entityManager.createNamedQuery(PropertyEntity.QUERY_GET_PROPERTY_BY_DATE, PropertyEntity.class).setParameter("dateValue", date).getResultList().get(0);
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
            AddressEntity addressToDelete = entityManager.find(AddressEntity.class, property.getAddress().getId());
            if (propertyToDelete != null) {
                PropertyEntity managedProperty = entityManager.merge(propertyToDelete); // Re-attach the entity
                entityManager.remove(managedProperty); // Now remove the managed entity
//                AddressEntity managedAddress = entityManager.merge(addressToDelete); // Re-attach the entity
//                entityManager.remove(managedAddress); // Now remove the managed entity
                property.setAddress(null);

            }
            return "success";
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }

}
