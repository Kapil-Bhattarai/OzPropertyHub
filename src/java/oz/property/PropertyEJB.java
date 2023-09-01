package oz.property;

import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import oz.OzEJB;
import oz.PropertyType;
import oz.UserType;
import oz.address.AddressEntity;
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
            return entityManager.createNamedQuery(PropertyEntity.QUERY_GET_PROPERTY_BY_AGENT, PropertyEntity.class)
                    .setParameter("id", id).getResultList();
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

    public List<PropertyEntity> getPropertyWithFilters(
    Double rent,
    PropertyType type,
    Date inspection,
    Date listedDate,
    Boolean hasAc,
    Boolean hasSecureParking,
    Boolean hasDishWasher,
    Boolean hasBalcony,
    Boolean hasWardrobe,
    Integer noOfParking,
    Integer noOfBathroom,
    Integer noOfBedroom
    ) {
        try {
            TypedQuery<PropertyEntity> query = entityManager.createNamedQuery(PropertyEntity.QUERY_SEARCH_QUERY, PropertyEntity.class);

            query.setParameter("rent", rent);
            query.setParameter("type", type);
            query.setParameter("inspection", inspection);
            query.setParameter("listedDate", listedDate);
            query.setParameter("hasAc", hasAc);
            query.setParameter("hasSecureParking", hasSecureParking);
            query.setParameter("hasDishWasher", hasDishWasher);
            query.setParameter("hasBalcony", hasBalcony);
            query.setParameter("hasWardrobe", hasWardrobe);
            query.setParameter("noOfParking", noOfParking);
            query.setParameter("noOfBathroom", noOfBathroom);
            query.setParameter("noOfBedroom", noOfBedroom);

            return query.getResultList();
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return new ArrayList<>();
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
                AddressEntity managedAddress = entityManager.merge(addressToDelete); // Re-attach the entity
                entityManager.remove(managedAddress); // Now remove the managed entity
            }
            return "success";
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }

}
