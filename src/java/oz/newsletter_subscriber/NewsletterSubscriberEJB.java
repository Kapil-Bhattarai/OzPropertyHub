package oz.newsletter_subscriber;

import oz.OzEJB;
import jakarta.ejb.Stateless;

@Stateless
public class NewsletterSubscriberEJB extends OzEJB {

    public Boolean addEmail(NewsletterSubscriberEntity ne) {
        try {
            entityManager.persist(ne);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public Boolean canAddToTheList(String email) {
        try {
            int size = entityManager.createNamedQuery("NewsletterEntity.findByEmail", NewsletterSubscriberEntity.class).
                    setParameter("email", email).getResultList().size();
            return size == 0;
        } catch (Exception e) {
            System.out.println("ne exception  " + e.getMessage());
            return false;
        }
    }
}
