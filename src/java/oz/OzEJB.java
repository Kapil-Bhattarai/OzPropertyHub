package oz;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.UserTransaction;
import static oz.Util.PERSISTANCE_NAME;


public class OzEJB {
    
  @PersistenceContext(unitName = PERSISTANCE_NAME)
   public EntityManager entityManager;
  
   @Resource
    private UserTransaction utx;
}
