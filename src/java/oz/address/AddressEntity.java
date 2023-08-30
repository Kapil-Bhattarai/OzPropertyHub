package oz.address;


import java.io.Serializable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import oz.StateType;


@Entity
@Table(name = "OZ_ADDRESS")
public class AddressEntity implements Serializable {

    public AddressEntity() {}
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "unit", nullable = false)
    private String unit;
    
    @Column(name = "street_number", nullable = false)
    private String street_number;
    
    @Column(name = "street_name", nullable = false)
    private String street_name;
    
    @Column(name = "suburb", nullable = false)
    private String suburb;
    
    @Column(name = "postcode", nullable = false)
    private String postcode;
    
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private StateType state;

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public StateType getState() {
        return state;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    
    public AddressEntity(Integer id, String unit, String street_name, String street_number, String suburb, String postcode, StateType state) {
        this.id = id;
        this.unit = unit;
        this.street_name = street_name;
        this.suburb = suburb;
        this.postcode = postcode;
        this.state = state;
        this.street_number = street_number;
    }

    @Override
    public String toString() {
        return "AddressEntity{" + "id=" + id + ", unit=" + unit + ", street_number=" + street_number + ", street_name=" + street_name + ", suburb=" + suburb + ", postcode=" + postcode + ", state=" + state + '}';
    }
    
}