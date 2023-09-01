package oz.search;

import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import oz.OzEJB;
import oz.PropertyType;
import oz.property.PropertyEntity;

@Stateless
public class SearchEJB extends OzEJB {

    public List<PropertyEntity> getPropertyWithFilters(
            Integer lowerBound,
            Integer upperBound,
            PropertyType type,
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

            query.setParameter("lowerRent", lowerBound);
              query.setParameter("upperRent", upperBound);
            query.setParameter("type", type);
            if (hasAc) {
                query.setParameter("hasAc", hasAc);
            } else {
                query.setParameter("hasAc", null);
            }

            if (hasSecureParking) {
                query.setParameter("hasSecureParking", hasSecureParking);
            } else {
                query.setParameter("hasSecureParking", null);
            }

            if (hasDishWasher) {
                query.setParameter("hasDishWasher", hasDishWasher);
            } else {
                query.setParameter("hasDishWasher", null);
            }

            if (hasBalcony) {
                query.setParameter("hasBalcony", hasBalcony);
            } else {
                query.setParameter("hasBalcony", null);
            }

            if (hasBalcony) {
                query.setParameter("hasWardrobe", hasWardrobe);
            } else {
                query.setParameter("hasWardrobe", null);
            }

            if (noOfParking > 0) {
                query.setParameter("noOfParking", noOfParking);
            } else {
                query.setParameter("noOfParking", null);
            }

            if (noOfBathroom > 0) {
                query.setParameter("noOfBathroom", noOfBathroom);
            } else {
                query.setParameter("noOfBathroom", null);
            }

            if (noOfBathroom > 0) {
                query.setParameter("noOfBedroom", noOfBedroom);
            } else {
                query.setParameter("noOfBedroom", null);
            }

            return query.getResultList();
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
