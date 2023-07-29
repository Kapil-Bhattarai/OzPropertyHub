package order;

import customer.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import products.Product;

@Stateless
public class ProductOrderEJB {

    @PersistenceContext(unitName = "oz_property_hub")
    private EntityManager entityManager;

    /**
     *
     * @param orderId: Gets the ProductOrder based on orderId
     * @return: Returns list of ProductOrder based on searchQuery
     */
    public List<ProductOrder> getOrderList(int orderId) {
        if (orderId == 0) {
            return entityManager.createNamedQuery("ProductOrder.findAll", ProductOrder.class).getResultList();
        } else {
            return entityManager.createNamedQuery("ProductOrder.findByItemId", ProductOrder.class)
                    .setParameter("id", orderId)
                    .getResultList();
        }
    }

    /**
     *
     * @param customer: Customer for which order count need to be found
     * @return: total order count for the customer
     */
    public Long getCustomerOrderCount(Customer customer) {

        return entityManager.createNamedQuery("ProductOrder.customerOrdersCount", Long.class)
                .setParameter("customer", customer)
                .getSingleResult();
    }

    /**
     *
     * @param customer: customer for which orders to be fetched
     * @return: list of customers to be returned
     */
    public List<Customer> getCustomerDetailsWithOrders(Customer customer) {
        try {
            return entityManager.createNamedQuery("ProductOrder.findByCustomerId", Customer.class)
                    .setParameter("customer", customer).getResultList();

        } catch (Exception e) {

            return new ArrayList<>();
        }
    }

    /**
     *
     * @param searchQuery: searchQuery to search order
     * @return: list of orders based on searchQuery
     */
    public List<ProductOrder> searchOrder(String searchQuery) {
        return entityManager.createNamedQuery("ProductOrder.findByItemId", ProductOrder.class)
                .setParameter("id", Integer.parseInt(searchQuery))
                .getResultList();
    }

    /**
     *
     * @param productId: id to search the Product
     * @return: Product with given productId
     */
    public Product getProductById(int productId) {
        return entityManager.createNamedQuery("Product.findById", Product.class)
                .setParameter("id", productId)
                .getSingleResult();
    }

    /**
     *
     * @param customerId: customerId to search for customer
     * @return: returns the customer based on customerId
     */
    public Customer getCustomerById(int customerId) {
        return entityManager.createNamedQuery("Customer.findById", Customer.class)
                .setParameter("id", customerId)
                .getSingleResult();
    }

    /**
     *
     * @param productOrder: productOrder to save
     * @param itemId: selected productId from drop down
     * @param quantity: quantity to save
     * @param selectedCustomer: selected customerId from drop down
     * @return
     */
    public Object addOrder(ProductOrder productOrder, int itemId, int quantity, int selectedCustomer) {

        int stockLeft = findStockById(itemId);
        int remainingStock = stockLeft - quantity;

        if (remainingStock >= 0 && quantity != 0) {
            //  Customer customer = getCustomerById(selectedCustomer);
            Customer customer = entityManager.find(Customer.class, selectedCustomer);
            productOrder.setCustomer(customer);
            customer.setProductOrders(new ArrayList<ProductOrder>() {
                {
                    addAll(customer.getProductOrders());
                    add(productOrder);
                }
            });
            try {

                entityManager.merge(customer);

                Query query = entityManager.createNamedQuery("Product.updateStockById");
                query.setParameter("stock", remainingStock);
                query.setParameter("productId", itemId);
                query.executeUpdate();

            } catch (Exception e) {

                return e.getMessage();
            }

            Customer updatedCustomer = entityManager.find(Customer.class, selectedCustomer);
            return updatedCustomer.getProductOrders().get(updatedCustomer.getProductOrders().size() - 1).getId();
        } else {
            var errorMessage = "Only " + stockLeft + " items left in the stock";
            if (quantity == 0) {
                errorMessage = "Quantity must be > 0";
            }

            return errorMessage;
        }

    }

    /**
     * 
     * @param customerId: current customerId 
     * @param productId: productId to delete
     * @param quantity: quantity to update after deletion
     * @param orderId: orderId to delete
     * @return 
     */
    public boolean deleteOrder(int customerId, int productId, int quantity, int orderId) {

        // Customer customer = getCustomerById(customerId);
        Customer customer = entityManager.find(Customer.class, customerId);
        //productOrder.setCustomer(customer);
        int stock = findStockById(productId);
        customer.getProductOrders().clear();

        try {
            ProductOrder order = entityManager.find(ProductOrder.class, orderId);
            if (order == null) {
                throw new Exception("Order not found");
            }

            order = entityManager.merge(order);
            customer.getProductOrders().remove(order);
            order.setCustomer(null); // Set the customer to null

            // Update the stock of the product
            Query updateStockQuery = entityManager.createNamedQuery("Product.updateStockById");
            updateStockQuery.setParameter("stock", stock + quantity);
            updateStockQuery.setParameter("productId", productId);
            updateStockQuery.executeUpdate();
            entityManager.merge(customer);

            // Delete the ProductOrder entity
            entityManager.remove(order);
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 
     * @return: returns all the customer
     */
    public List<Customer> getCustomerList() {
        return entityManager.createNamedQuery("Customer.findAll", Customer.class).getResultList();
    }

    /**
     * 
     * @return: returns the total products
     */
    public List<Product> getProductsList() {
        return entityManager.createNamedQuery("Product.findAll", Product.class).getResultList();
    }

    /**
     * 
     * @param productId: productId to search
     * @return 
     */
    public int findStockById(int productId) {
        return entityManager.createNamedQuery("Product.findStockById", Integer.class)
                .setParameter("id", productId).getSingleResult();
    }
    
    /**
     * 
     * @return: gets the total number of customers
     */
    public Long countCustomers() {
        return entityManager.createNamedQuery("ProductOrder.countTotal", Long.class).getSingleResult();
    }

    /**
     * 
     * @param customerId: customerId to the get the total number of order
     * @return: order count
     */
    public Long getCustomerOrderCount(int customerId) {
        var orderCount = entityManager.createNamedQuery("Customer.countTotalOrders", Long.class)
                .setParameter("id", customerId)
                .getSingleResult();
        return orderCount;
    }

    /**
     * 
     * @return: total number of orders count
     */
    public Long getAllOrdersCount() {
        var orderCount = entityManager.createNamedQuery("ProductOrder.countAllOrders", Long.class)
                .getSingleResult();
        return orderCount;
    }

}
