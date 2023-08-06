package oz;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import static oz.Util.PERSISTANCE_NAME;


public class OzEJB {
    
  @PersistenceContext(unitName = PERSISTANCE_NAME)
   public EntityManager entityManager;
  
}
