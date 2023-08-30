package oz.address;

import jakarta.ejb.Stateless;
import oz.OzEJB;

@Stateless
public class AddressEJB  extends OzEJB {
    public Boolean addAddress(AddressEntity address) {
        try {
            entityManager.persist(address);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public AddressEntity updateAddress(AddressEntity address) {
        return entityManager.merge(address);
    }
}
