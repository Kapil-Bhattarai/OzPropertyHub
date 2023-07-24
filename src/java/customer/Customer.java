package customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import order.ProductOrder;

@Entity
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name"),
    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
    @NamedQuery(name = "Customer.countTotal", query = "SELECT COUNT(c) FROM Customer c"),
    @NamedQuery(name = "Customer.getCustomerName", query = "SELECT c.name FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name = "Customer.customerOrders", query = "SELECT po FROM Customer c JOIN c.productOrders po WHERE c.id = :id"),
    @NamedQuery(name = "Customer.countTotalOrders", query = "SELECT COUNT(po) FROM Customer c JOIN c.productOrders po WHERE c.id = :id")
})
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "email", nullable = false, length=128)
    private String email;
    @Column(name = "phone", nullable = false, length=128)
    private String phone;
    @Column(name = "since")
    @Temporal(TemporalType.TIMESTAMP)
    private Date since;
    
    @OneToMany(fetch = FetchType.EAGER,  cascade = CascadeType.PERSIST)
    private List<ProductOrder> productOrders;
    
    public Customer() {}

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

    public List<ProductOrder> getOrders() {
        return productOrders;
    }

    public void setOrders(List<ProductOrder> orders) {
        this.productOrders = orders;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", phone=" + phone + ", since=" + since + ", productOrders=" + productOrders +'}';
    }
    
}
