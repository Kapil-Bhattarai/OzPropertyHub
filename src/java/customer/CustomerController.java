package customer;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.transaction.Transactional;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ejb.EJB;
import order.ProductOrder;
import order.ProductOrderEJB;

@ManagedBean(name = "customerBean")
@SessionScoped
public class CustomerController implements Serializable {

    @EJB
    private CustomerEJB customerEJB;

    @EJB
    private ProductOrderEJB productOrderEJB;

    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private Date since;
    private List<ProductOrder> productOrders;

    private Customer customer;

    private String searchQuery;

    //states
    private int customerId = 0;
    private boolean search = false;
       
    @PostConstruct
    public void init() {
        customer = new Customer();
    }
   

 /**
  * 
  * @return: returns the url to display inserted customer data if the operation is successful otherwise null
  */
    @Transactional
    public String addCustomer() {

        customer.setEmail(email);
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhone(phone);
        customer.setSince(new Date());
        
         FacesContext context = FacesContext.getCurrentInstance();
        if (customerEJB.addCustomer(customer)) {
            Logger.getAnonymousLogger().log(Level.INFO, "Data added successfully");
             customerId = customer.getId();
             return "page_new_customer.faces?faces-redirect=true";
          
        } else {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error creating user!",
                    "Unexpected error when creating your account.  Please contact the system Administrator");
            context.addMessage(null, message);
            return null;
        }

    }

     public List<Customer> getCustomerList(int customerId) {
          
        if (customerId == 0) {
            return customerEJB.findAllCustomer();
        } else {
            return customerEJB.findCustomerById(customerId);
        }
    }

     /**
      * 
      * @return: returns the url of the page to display the found customer detail otherwise null
      */
    public String searchCustomer() {
        var customers = customerEJB.findCustomerByEmail(searchQuery);
        if (customers.isEmpty()) {
            customers = customerEJB.findCustomerByName(searchQuery);
        }
        searchQuery = "";
        if (!customers.isEmpty()) {
            search = true;
            customerId = customers.get(0).getId();
            return "page_new_customer.faces?faces-redirect=true";
        } else {
            search = false;
             FacesContext context = FacesContext.getCurrentInstance();
             FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Customer not found! Please enter another name.","");
            context.addMessage(null, message);
            return null;
        }
    }

    /**
     * 
     * @param customerId: id of the customer
     * @return: returns the url of the page to show the customer details
     */
    public String showCustomerDetailsById(int customerId) {
        this.customerId = customerId;
        return "detail_customer.faces?faces-redirect=true";
    }
    
   /**
    * 
    * @param customerId: id of the customer
    * @return: returns the customer name based on the customerId
    */
    public String getCustomerName(int customerId) {
        try {
            return customerEJB.getCustomerName(customerId);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 
     * @param id: id of the customer
     * @return: returns the list of customer with orders
     */
    public List<Customer> getCustomerOrders(int id) {
        try {
            Customer customer = customerEJB.findCustomerById(id).get(0);
            return productOrderEJB.getCustomerDetailsWithOrders(customer);

        } catch (Exception e) {

            return new ArrayList<>();
        }
    }

    /**
     * 
     * @param orderId: order id
     * @return: list of productOrder based on orderId
     */
    public List<ProductOrder> getOrderList(int orderId) {
        return productOrderEJB.getOrderList(orderId);
    }

    /**
     * 
     * @return: total number of customers
     */
    public Long countCustomers() {
        return customerEJB.countCustomers();
    }

    /**
     * 
     * @param customerId: id of the customer
     * @return: total number of orders for the customerId
     */
    public Long getCustomerOrderCount(int customerId) {
        Customer customer = customerEJB.findCustomerById(customerId).get(0);
        return productOrderEJB.getCustomerOrderCount(customer);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

}
