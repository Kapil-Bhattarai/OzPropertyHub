package oz.search;

import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;
import oz.PropertyType;
import oz.StateType;
import oz.property.PropertyEntity;

@ManagedBean(name = "ozglobalbean")
@SessionScoped
public class SearchController {

    private StateType state;
    private PropertyType propertyType;
    private String rent;
    private Integer lowerBound;
    private Integer upperBound;
    private String searchText;

    private boolean hasBalcony;
    private boolean hasDishwater;
    private boolean hasAc;
    private boolean hasSecureParking;
    private boolean hasWardrobe;

    private String noOfBedroom = "1";
    private String noOfBathroom = "1";
    private String noOfParking = "0";

    private List<PropertyEntity> properties = new ArrayList<>();
  
     
    @EJB
    private SearchEJB searchEJB;

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
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

    public String getNoOfBedroom() {
        return noOfBedroom;
    }

    public void setNoOfBedroom(String noOfBedroom) {
        this.noOfBedroom = noOfBedroom;
    }

    public String getNoOfBathroom() {
        return noOfBathroom;
    }

    public void setNoOfBathroom(String noOfBathroom) {
        this.noOfBathroom = noOfBathroom;
    }

    public String getNoOfParking() {
        return noOfParking;
    }

    public void setNoOfParking(String noOfParking) {
        this.noOfParking = noOfParking;
    }

    public void getPropertyWithFilters() {
        List<PropertyEntity> properties = searchEJB.getPropertyWithFilters(lowerBound, upperBound,
                propertyType, hasAc, hasSecureParking,
                hasDishwater, hasBalcony, hasWardrobe, Integer.parseInt(noOfParking),
                Integer.parseInt(noOfBathroom), Integer.parseInt(noOfBedroom));
        this.properties.clear();
        this.properties.addAll(properties);
        System.out.println("search properties " + this.properties + " Size is " + this.properties.size());
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
        splitRange(rent);
    }

    public Integer getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Integer lowerBound) {
        this.lowerBound = lowerBound;
    }

    public Integer getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Integer upperBound) {
        this.upperBound = upperBound;
    }
    

    public boolean clearGlobalConfigAndReturn(Integer userId) {

//        this.hasAc = false;
//        this.hasBalcony = false;
//        this.hasDishwater = false;
//        this.hasSecureParking = false;
//        this.hasWardrobe = false;
//        
//        this.noOfBathroom = 0;
//        this.noOfParking = 0;
//        this.noOfBedroom = 0;
//        
//        this.price = null;
//        this.propertyType = null;
//        this.searchText = null;
//        this.state = null;
//        
        return userId == null;
    }

    @Override
    public String toString() {
        return "SearchController{" + "state=" + state + ", propertyType=" + propertyType + ", rent=" + rent + ", lowerBound=" + lowerBound + ", upperBound=" + upperBound + ", searchText=" + searchText + ", hasBalcony=" + hasBalcony + ", hasDishwater=" + hasDishwater + ", hasAc=" + hasAc + ", hasSecureParking=" + hasSecureParking + ", hasWardrobe=" + hasWardrobe + ", noOfBedroom=" + noOfBedroom + ", noOfBathroom=" + noOfBathroom + ", noOfParking=" + noOfParking + ", properties=" + properties + ", searchEJB=" + searchEJB + '}';
    }


    public List<PropertyEntity> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyEntity> properties) {
        this.properties = properties;
    }

    private void splitRange(String range) {

        String[] bounds = range.split("-");

        if (bounds.length == 2) {
            this.lowerBound = Integer.parseInt(bounds[0]);
            this.upperBound = Integer.parseInt(bounds[1]);
        } else {
            this.lowerBound =0;
            this.upperBound = 0;
        }
    }

}
