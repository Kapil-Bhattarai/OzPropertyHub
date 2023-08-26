package chat;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import products.Product;

@Stateless
public class ChatEJB {

    @PersistenceContext(unitName = "oz_property_hub")
    private EntityManager entityManager;

    public Boolean addChat(Chat chat) {
        try {
            entityManager.persist(chat);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
