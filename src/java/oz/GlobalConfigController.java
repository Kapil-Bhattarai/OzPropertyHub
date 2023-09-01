package oz;

import oz.user.*;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import oz.UserType;
import oz.Util;

@ManagedBean(name = "ozglobalbean")
@SessionScoped
public class GlobalConfigController {

    private StateType state;
    private PropertyType propertyType;
    private String price;
    private String searchText;

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
    
    

    public StateType getState() {
        return state;
    }

    public void setState(StateType state) {
        this.state = state;
    }
     
    public PropertyType[] getPropertyTypes() {
        return PropertyType.values();
    }
     
     public StateType[] getStates() {
        return StateType.values();
    }
    
}
