package oz.search;

import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import oz.OzEJB;
import oz.PropertyType;
import oz.StateType;
import oz.property.PropertyEntity;

@Stateless
public class SearchEJB extends OzEJB {

//      public List<PropertyEntity> getPropertyWithoutFilters() {
//        try {
//            TypedQuery<PropertyEntity> query = entityManager.createNamedQuery(PropertyEntity.QUERY_GET_ALL, PropertyEntity.class);
//            return query.getResultList();
//        } catch (Exception e) {
//            System.out.println("exception value  " + e.getMessage());
//            return new ArrayList<>();
//        }
//    }
    public LazyDataModel<PropertyEntity> getPropertyLazyModel(
            String searchText,
            StateType state,
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
        return new LazyDataModel<PropertyEntity>() {

            @Override
            public List<PropertyEntity> load(int offset, int skip, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
                try {

                    System.out.println("Offset is: " + offset);
                    TypedQuery<PropertyEntity> query = entityManager.createNamedQuery(PropertyEntity.QUERY_SEARCH_QUERY, PropertyEntity.class);

                    if (searchText == null || searchText.length() == 0) {
                        query.setParameter("searchText", null);
                    } else {
                        query.setParameter("searchText", searchText);
                    }

                    query.setParameter("state", state);

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

                    if (noOfBedroom > 0) {
                        query.setParameter("noOfBedroom", noOfBedroom);
                    } else {
                        query.setParameter("noOfBedroom", null);
                    }

                    // Set the first result (offset)
                    query.setFirstResult(offset);

                    // Set the max results (limit)
                    query.setMaxResults(skip);

                    // get the total result count
                    if (offset == 0) {
                        TypedQuery<Long> countQuery = entityManager.createNamedQuery(PropertyEntity.QUERY_COUNT, Long.class);
                        if (searchText == null || searchText.length() == 0) {
                            countQuery.setParameter("searchText", null);
                        } else {
                            countQuery.setParameter("searchText", searchText);
                        }

                        countQuery.setParameter("state", state);

                        countQuery.setParameter("lowerRent", lowerBound);
                        countQuery.setParameter("upperRent", upperBound);
                        countQuery.setParameter("type", type);

                        if (hasAc) {
                            countQuery.setParameter("hasAc", hasAc);
                        } else {
                            countQuery.setParameter("hasAc", null);
                        }

                        if (hasSecureParking) {
                            countQuery.setParameter("hasSecureParking", hasSecureParking);
                        } else {
                            countQuery.setParameter("hasSecureParking", null);
                        }

                        if (hasDishWasher) {
                            countQuery.setParameter("hasDishWasher", hasDishWasher);
                        } else {
                            countQuery.setParameter("hasDishWasher", null);
                        }

                        if (hasBalcony) {
                            countQuery.setParameter("hasBalcony", hasBalcony);
                        } else {
                            countQuery.setParameter("hasBalcony", null);
                        }

                        if (hasBalcony) {
                            countQuery.setParameter("hasWardrobe", hasWardrobe);
                        } else {
                            countQuery.setParameter("hasWardrobe", null);
                        }

                        if (noOfParking > 0) {
                            countQuery.setParameter("noOfParking", noOfParking);
                        } else {
                            countQuery.setParameter("noOfParking", null);
                        }

                        if (noOfBathroom > 0) {
                            countQuery.setParameter("noOfBathroom", noOfBathroom);
                        } else {
                            countQuery.setParameter("noOfBathroom", null);
                        }

                        if (noOfBedroom > 0) {
                            countQuery.setParameter("noOfBedroom", noOfBedroom);
                        } else {
                            countQuery.setParameter("noOfBedroom", null);
                        }
                        this.setRowCount(countQuery.getSingleResult().intValue());
                    }

                    return query.getResultList();
                } catch (Exception e) {
                    System.out.println("exception value  " + e.getMessage());
                    return new ArrayList<>();
                }
            }

        };
    }
}
