package oz.property_image;

import jakarta.ejb.Stateless;
import oz.OzEJB;

@Stateless
public class PropertyImageEJB extends OzEJB {

    public Boolean addPropertyImage(PropertyImageEntity image) {
        try {
            entityManager.persist(image);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean removePropertyImage(PropertyImageEntity image) {
        try {
            entityManager.remove(image);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
