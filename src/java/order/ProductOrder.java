package order;

import customer.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import products.Product;

@Entity
@Table(name = "PRODUCT_ORDER")
@NamedQueries({
    @NamedQuery(name = "ProductOrder.findAll", query = "SELECT po FROM ProductOrder po"),
    @NamedQuery(name = "ProductOrder.findByItemId", query = "SELECT po FROM ProductOrder po WHERE po.id = :id"),
    @NamedQuery(name = "ProductOrder.getProductDetailsByOrderId", query = "SELECT o FROM ProductOrder o WHERE o.id = :orderId"),
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "ProductOrder.findByCustomerId", query = "SELECT po FROM ProductOrder po WHERE po.customer = :customer"),
    @NamedQuery(name = "ProductOrder.customerOrdersCount", query = "SELECT COUNT(po) FROM ProductOrder po WHERE po.customer = :customer"),
    @NamedQuery(name = "ProductOrder.countAllOrders", query = "SELECT COUNT(po) FROM ProductOrder po"),
    @NamedQuery(name = "ProductOrder.findAllWithCustomerAndProduct", query = "SELECT o FROM ProductOrder o JOIN FETCH o.customer")
})
public class ProductOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @OneToOne
    @JoinColumn(name = "itemId", nullable = false)
    private Product itemId;
         
    @Column(name = "creationDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
        
    public ProductOrder() {}

    public Integer getOrderId() {
        return id;
    }

    public void setOrderId(Integer orderId) {
        this.id = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getItemId() {
        return itemId;
    }

    public void setItemId(Product itemId) {
        this.itemId = itemId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    } 

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "ProductOrder{" + "id=" + id + ", quantity=" + quantity + ", itemId=" + itemId + ", creationDate=" + creationDate + '}';
    }

    public Integer getId() {
        return id;
    }

 
       
}
