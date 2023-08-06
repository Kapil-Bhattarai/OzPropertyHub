package oz;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import static oz.Util.PERSISTANCE_NAME;

@Stateless
public class OzEJB {
    
  @PersistenceContext(unitName = PERSISTANCE_NAME)
   public EntityManager entityManager;
  
}
