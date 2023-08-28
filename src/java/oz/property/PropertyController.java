/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oz.property;

import static com.sun.faces.facelets.util.Path.context;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ManagedProperty;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import oz.PropertyType;
import oz.StateType;
import oz.Util;
import oz.address.AddressEJB;
import oz.address.AddressEntity;
import oz.user.UserController;
import oz.user.UserEntity;

/**
 *
 * @author bishal
 */
@ManagedBean(name = "propertyBean")
@ViewScoped
public class PropertyController {

    @PersistenceContext
    private EntityManager em;

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

    @EJB
    private PropertyEJB propertyEJB;

    @EJB
    private AddressEJB addressEJB;

    @ManagedProperty("#{userBean}")
    private UserController userBean; // Inject the UserController bean

    private PropertyEntity propertyEntity;

    private AddressEntity addressEntity;

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

    public UserController getUserBean() {
        return userBean;
    }

    public void setUserBean(UserController userBean) {
        this.userBean = userBean;
    }

    public String submit() {
        FacesContext context = FacesContext.getCurrentInstance();

        System.out.println("submit");
        try {
            // Begin a new transaction
            //em.getTransaction().begin();
            System.out.println("suru");
            propertyEntity = new PropertyEntity();
            if (mainImage != null) {
                try (InputStream input = mainImage.getInputStream()) {
                    String fileName = getSubmittedFileName(mainImage);
                    System.out.println(System.getenv("OZPROPERTYHUB_UPLOAD_LOCATION"));
                    System.out.println("hello");
                    String fileLocation = System.getenv("OZPROPERTYHUB_UPLOAD_LOCATION") + "/" + fileName;
                    File outputFile = new File(fileLocation);

                    try (FileOutputStream output = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = input.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }
                        System.out.println("hello");
                        propertyEntity.setMainImage(fileName);
                    }

                    // Additional logic after saving the file
                } catch (IOException e) {
                    // Handle exception
                }

                System.out.println(propertyEntity.getMainImage());
                addressEntity = new AddressEntity();
                addressEntity.setUnit(String.valueOf(unitNumber));
                addressEntity.setStreet_name(streetName);
                addressEntity.setSuburb(suburb);
                addressEntity.setPostcode(String.valueOf(postCode));
                addressEntity.setState(state); // Convert enum to string

                // Save the AddressEntity in the database
                addressEJB.addAddress(addressEntity);
                System.out.println("mid");
                // Create a new PropertyEntity and set its attributes
                propertyEntity.setRent(rent);
                propertyEntity.setType(propertyType);
                propertyEntity.setInspection(inspectionDate);
                propertyEntity.setListedDate(listedDate);
                propertyEntity.setHasAc(hasAc);
                propertyEntity.setHasSecureParking(hasSecureParking);
                propertyEntity.setHasDishWasher(hasDishwater);
                propertyEntity.setHasBalcony(hasBalcony);
                propertyEntity.setHasWardrobe(hasWardrobe);
                propertyEntity.setNoOfParking(noOfParking);
                propertyEntity.setNoOfBathroom(noOfBathroom);

                // Associate the created AddressEntity with the PropertyEntity
                propertyEntity.setAddress(addressEntity);
                UserEntity existingAgent = em.find(UserEntity.class, userBean.getId()); // Use the correct agent ID here

                propertyEntity.setAgent(existingAgent);

                // Save the PropertyEntity in the database
                propertyEJB.addProperty(propertyEntity);
                System.out.println("end");
                return "/dashboard/agent/agent_dashboard.faces?faces-redirect=true";
            }
            return "";
        } catch (Exception e) {
            // Rollback the transaction if an exception occurs
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
            // Handle the exception or rethrow it
            // For example, you can log the error or show an error message to the user
            Util.showMessage(context, FacesMessage.SEVERITY_ERROR, "There were some errors while adding property.", null);
            return "";
        }

    }
    
    public List<PropertyEntity> getPropertiesByAgent(Boolean isActive) {
        System.out.println("get all values");
        List<PropertyEntity> list = propertyEJB.getPropertiesByAgent(userBean.getId());
        System.out.println("values of agent " + list.toString());
        return list;
    }

    private String getSubmittedFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");

        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";
    }
}
