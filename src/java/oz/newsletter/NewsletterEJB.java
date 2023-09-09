package oz.newsletter;

import oz.OzEJB;
import jakarta.ejb.Stateless;

@Stateless
public class NewsletterEJB extends OzEJB {

    public Boolean createNewsletter(NewsletterEntity ne) {
        try {
            entityManager.persist(ne);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public NewsletterEntity updateNewsletter(NewsletterEntity ne) {
        return entityManager.merge(ne);
    }
    
    public NewsletterEntity getNewsletter(int id) {
        try {
            return entityManager.createNamedQuery(NewsletterEntity.QUERY_GET_NEWSLETTER, NewsletterEntity.class).setParameter("id", id).getResultList().get(0);
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }
    
    public String deleteProperty(NewsletterEntity ne) {

        try {
            NewsletterEntity newsletterToDelete = entityManager.find(NewsletterEntity.class, ne.getId());
            
            if (newsletterToDelete != null) {
                NewsletterEntity managedNewsletter = entityManager.merge(newsletterToDelete); // Re-attach the entity
                entityManager.remove(managedNewsletter); // Now remove the managed entity
            }
            return "success";
        } catch (Exception e) {
            System.out.println("exception value  " + e.getMessage());
            return null;
        }
    }
}
