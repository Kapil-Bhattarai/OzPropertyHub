package customer;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CustomerEJB {

    @PersistenceContext(unitName = "oz_property_hub")
    private EntityManager entityManager;

    /**
     * 
     * @param customer: customer to save into db
     * @return: returns true if the insertion is successful otherwise false
     */
    public Boolean addCustomer(Customer customer) {
        try {
            entityManager.persist(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 
     * @param searchQuery: searchQuery to find the customer by email
     * @return 
     */
    public List<Customer> findCustomerByEmail(String searchQuery) {
        return entityManager.createNamedQuery("Customer.findByEmail", Customer.class)
                .setParameter("email", searchQuery)
                .getResultList();
    }

    /**
     * 
     * @param searchQuery: searchQuery to find the customer by name
     * @return 
     */
    public List<Customer> findCustomerByName(String searchQuery) {
        return entityManager.createNamedQuery("Customer.findByName", Customer.class)
                .setParameter("name", searchQuery)
                .getResultList();
    }

    /**
     * 
     * @return: returns the list of customer
     */
    public List<Customer> findAllCustomer() {
        return entityManager.createNamedQuery("Customer.findAll", Customer.class).getResultList();
    }

    /**
     * 
     * @param customerId: customerId to find the customer
     * @return: returns the list of Customer
     */
    public List<Customer> findCustomerById(int customerId) {
        return entityManager.createNamedQuery("Customer.findById", Customer.class)
                .setParameter("id", customerId)
                .getResultList();
    }
    

    /**
     * 
     * @param customerId: customerId to search for
     * @return: returns the name of the customer
     */
    public String getCustomerName(int customerId) {
        return entityManager.createNamedQuery("Customer.getCustomerName", String.class)
                .setParameter("id", customerId).getSingleResult();
    }
    
    /**
     * 
     * @return: returns the total number of customers
     */
     public Long countCustomers() {
          return entityManager.createNamedQuery("Customer.countTotal", Long.class).getSingleResult();
     }
}
