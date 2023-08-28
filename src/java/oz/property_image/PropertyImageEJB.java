package oz.property_image;

import jakarta.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import oz.OzEJB;
import oz.UserType;
import oz.user.UserEntity;



@Stateless
public class PropertyImageEJB  extends OzEJB {
    public Boolean addPropertyImage(PropertyImageEntity image) {
        try {
            entityManager.persist(image);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
