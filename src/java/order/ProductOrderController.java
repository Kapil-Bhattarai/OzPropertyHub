package order;

import customer.Customer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.transaction.UserTransaction;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import products.Product;

@ManagedBean(name = "orderBean")
@SessionScoped
public class ProductOrderController implements Serializable {

    private Integer id;
    private Integer quantity;
    private Integer itemId;

    private ProductOrder productOrder;

    private String searchQuery;

    private int selectedCustomer;
    private ProductOrder selectedProduct;

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private UserTransaction utx;

    @EJB
    private ProductOrderEJB productOrderEJB;

    //state 
    private int orderId = 0;
    private boolean search = false;

    @PostConstruct
    public void init() {
        productOrder = new ProductOrder();
    }

    /**
     *  method to add Order on db
     * @return: returns the final url of the page
     */
    public String addOrder() {

        productOrder.setQuantity(quantity);
        productOrder.setCreationDate(new Date());

        Product product = entityManager.find(Product.class, itemId);
        productOrder.setItemId(product);

        Object result = productOrderEJB.addOrder(productOrder, itemId, quantity, selectedCustomer);
        FacesContext context = FacesContext.getCurrentInstance();

        if (result instanceof String) {

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, result.toString(), "Please adjust your quantity");
            context.addMessage(null, message);
            Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to place new order");
            return null;

        } else {
            this.orderId = Integer.parseInt(result.toString());
            return "detail_order.faces?faces-redirect=true";
        }
    }

    /**
     * method to search order
     * @return: returns the url of the final url or error essage
     */
    public String searchOrder() {
        var orders = productOrderEJB.searchOrder(searchQuery);
        searchQuery = "";
        if (!orders.isEmpty()) {
            this.orderId = orders.get(0).getId();
            this.search = true;
            return "search_result_order.faces?faces-redirect=true";
        } else {
             FacesContext context = FacesContext.getCurrentInstance();
             FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Order not found! Please enter another id.","");
            context.addMessage(null, message);
            return null;
        }
    }

    /**
     * 
     * @param orderId: id of the order
     * @return: List of ProductOrder
     */
    public List<ProductOrder> getOrderList(int orderId) {
        return productOrderEJB.getOrderList(orderId);
    }

    /**
     * 
     * @param productId: id of the product
     * @return: product based on Id
     */
    public Product getProductById(int productId) {
        return productOrderEJB.getProductById(productId);
    }

    /**
     * 
     * @param customerId: id of the customer
     * @return 
     */
    public Customer getCustomerById(int customerId) {
        return productOrderEJB.getCustomerById(customerId);
    }

   /**
     * 
     * @param customerId: current customerId 
     * @param productId: productId to delete
     * @param quantity: quantity to update after deletion
     * @param orderId: orderId to delete
     * @return 
     */
    public String deleteOrder(int customerId, int productId, int quantity, int orderId) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (productOrderEJB.deleteOrder(customerId, productId, quantity, orderId)) {
            Logger.getAnonymousLogger().log(Level.INFO, "Data deleted successfully");
            return "list_order.faces";
        } else {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error deleting order!",
                    "Unexpected error when creating your account.  Please contact the system Administrator");
            context.addMessage(null, message);
            Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to delete order", "");
            return null;
        }

    }

    public List<Customer> getCustomerList() {
        return productOrderEJB.getCustomerList();
    }

    public List<Product> getProductsList() {
        return productOrderEJB.getProductsList();

    }

    public int findStockById(int productId) {
        return productOrderEJB.findStockById(productId);
    }

    public Long countCustomers() {
        return productOrderEJB.countCustomers();
    }

    public Long getCustomerOrderCount(int customerId) {
        return productOrderEJB.getCustomerOrderCount(customerId);
    }

    public Long getAllOrdersCount() {
        return productOrderEJB.getAllOrdersCount();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public ProductOrder getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductOrder selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public int getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(int selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

}
