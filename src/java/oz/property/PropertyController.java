/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oz.property;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.bean.ViewScoped;
import jakarta.servlet.http.Part;
import java.util.ArrayList;
import java.util.Date;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import oz.PropertyType;
import oz.StateType;

/**
 *
 * @author bishal
 */
@ManagedBean(name = "propertyBean")
@ViewScoped
public class PropertyController {

    private int unitNumber;
    private String streetName;
    private String suburb;
    private StateType state;
    private int postCode;
    private Part mainImage;
    private ArrayList<Part> additionalImages = new ArrayList<>();
    private PropertyType propertyType;
    private int rent;
    private int noOfBedroom;
    private int noOfBathroom;
    private int noOfParking;
    private boolean hasBalcony;
    private boolean hasDishwater;
    private boolean hasAc;
    private boolean hasSecureParking;
    private boolean hasWardrobe;
    private Date listedDate;
    private Date inspectionDate;

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public StateType getState() {
        return state;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public Part getMainImage() {
        return mainImage;
    }

    public void setMainImage(Part mainImage) {
        this.mainImage = mainImage;
    }

    public ArrayList<Part> getAdditionalImages() {
        return additionalImages;
    }

    public void setAdditionalImages(ArrayList<Part> additionalImages) {
        this.additionalImages = additionalImages;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
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

    public Date getListedDate() {
        return listedDate;
    }

    public void setListedDate(Date listedDate) {
        this.listedDate = listedDate;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
    

    
    public StateType[] getStates() {
        return StateType.values();
    }
    
    public PropertyType[] getPropertyTypes() {
        return PropertyType.values();
    }
    
    public void submit(){
         
    }
}
