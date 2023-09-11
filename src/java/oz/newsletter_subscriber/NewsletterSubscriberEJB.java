package oz.newsletter_subscriber;

import oz.OzEJB;
import jakarta.ejb.Stateless;
import java.util.List;

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
            int size = entityManager.createNamedQuery("NewsletterSubscriberEntity.findByEmail", NewsletterSubscriberEntity.class).
                    setParameter("email", email).getResultList().size();
            return size == 0;
        } catch (Exception e) {
            System.out.println("ne exception  " + e.getMessage());
            return false;
        }
    }
    
    public List<NewsletterSubscriberEntity> getAllSubscribers() {
        try {
            return entityManager.createNamedQuery("NewsletterSubscriberEntity.getAll", NewsletterSubscriberEntity.class).
                    getResultList();
        } catch (Exception e) {
            System.out.println("ne exception  " + e.getMessage());
            return null;
        }
    }
}
