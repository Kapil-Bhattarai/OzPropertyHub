package oz.property;

import jakarta.ejb.Stateless;
import oz.OzEJB;



@Stateless
public class PropertyEJB  extends OzEJB {
    public Boolean addProperty(PropertyEntity property) {
        try {
            entityManager.persist(property);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
