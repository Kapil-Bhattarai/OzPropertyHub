package oz.search;

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
import oz.PropertyType;
import oz.StateType;
import oz.UserType;
import oz.Util;

@ManagedBean(name = "ozglobalbean")
@SessionScoped
public class GlobalConfigController {

    private StateType state;
    private PropertyType propertyType;
    private String price;
    private String searchText;
    
    private boolean hasBalcony;
    private boolean hasDishwater;
    private boolean hasAc;
    private boolean hasSecureParking;
    private boolean hasWardrobe;

    private int noOfBedroom;
    private int noOfBathroom;
    private int noOfParking;
    
    
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

    public boolean isHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public boolean isHasDishwater() {
        return hasDishwater;
    }

    public void setHasDishwater(boolean hasDishwater) {
        this.hasDishwater = hasDishwater;
    }

    public boolean isHasAc() {
        return hasAc;
    }

    public void setHasAc(boolean hasAc) {
        this.hasAc = hasAc;
    }

    public boolean isHasSecureParking() {
        return hasSecureParking;
    }

    public void setHasSecureParking(boolean hasSecureParking) {
        this.hasSecureParking = hasSecureParking;
    }

    public boolean isHasWardrobe() {
        return hasWardrobe;
    }

    public void setHasWardrobe(boolean hasWardrobe) {
        this.hasWardrobe = hasWardrobe;
    }

    public int getNoOfBedroom() {
        return noOfBedroom;
    }

    public void setNoOfBedroom(int noOfBedroom) {
        this.noOfBedroom = noOfBedroom;
    }

    public int getNoOfBathroom() {
        return noOfBathroom;
    }

    public void setNoOfBathroom(int noOfBathroom) {
        this.noOfBathroom = noOfBathroom;
    }

    public int getNoOfParking() {
        return noOfParking;
    }

    public void setNoOfParking(int noOfParking) {
        this.noOfParking = noOfParking;
    }
    
     
    public boolean clearGlobalConfigAndReturn(Integer userId) {
        
        this.hasAc = false;
        this.hasBalcony = false;
        this.hasDishwater = false;
        this.hasSecureParking = false;
        this.hasWardrobe = false;
        
        this.noOfBathroom = 0;
        this.noOfParking = 0;
        this.noOfBedroom = 0;
        
        this.price = null;
        this.propertyType = null;
        this.searchText = null;
        this.state = null;
        
        return userId == null;
    }
}
