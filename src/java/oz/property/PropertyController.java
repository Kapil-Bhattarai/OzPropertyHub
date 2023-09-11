package oz.property;

import oz.property_image.PropertyImageEJB;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ManagedProperty;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.primefaces.model.file.UploadedFiles;
import oz.PropertyType;
import oz.StateType;
import oz.Util;
import oz.address.AddressEJB;
import oz.address.AddressEntity;
import oz.property_image.PropertyImageEntity;
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

    private int pid;
    private int aid;
    private String unitNumber;
    private String streetName;
    private String streetNumber;
    private String suburb;
    private StateType state;
    private String propertyDetails;
    private String map;
    private String postCode;
    private Part mainImage;
    private String mainImageUrl;
    private PropertyType propertyType;
    private double rent;
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

    private UploadedFiles additionalImages;
    private List<PropertyImageEntity> additionalImagesE = new ArrayList<>();
    private List<PropertyImageEntity> removedImagesE = new ArrayList<>();

    @EJB
    private PropertyEJB propertyEJB;

    @EJB
    private AddressEJB addressEJB;

    @EJB
    private PropertyImageEJB propertyImageEJB;

    @ManagedProperty("#{userBean}")
    private UserController userBean; // Inject the UserController bean

    private PropertyEntity propertyEntity;

    private AddressEntity addressEntity;

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

    public Part getMainImage() {
        return mainImage;
    }

    public void setMainImage(Part mainImage) {
        this.mainImage = mainImage;
    }

    public UploadedFiles getAdditionalImages() {
        return additionalImages;
    }

    public void setAdditionalImages(UploadedFiles additionalImages) {
        this.additionalImages = additionalImages;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPropertyDetails() {
        return propertyDetails;
    }

    public void setPropertyDetails(String propertyDetails) {
        this.propertyDetails = propertyDetails;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public List<PropertyImageEntity> getAdditionalImagesE() {
        return additionalImagesE;
    }

    public void setAdditionalImagesE(List<PropertyImageEntity> additionalImagesE) {
        this.additionalImagesE = additionalImagesE;
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String id = params.get("id");
        if (id != null) {
            PropertyEntity propertyEntity = propertyEJB.getProperty(Integer.parseInt(id));

            this.aid = propertyEntity.getAddress().getId();
            this.pid = propertyEntity.getPid();
            this.unitNumber = propertyEntity.getAddress().getUnit();
            this.streetName = propertyEntity.getAddress().getStreet_name();
            this.streetNumber = propertyEntity.getAddress().getStreet_number();
            this.suburb = propertyEntity.getAddress().getSuburb();
            this.state = propertyEntity.getAddress().getState();
            this.postCode = propertyEntity.getAddress().getPostcode();
            this.mainImageUrl = propertyEntity.getMainImage();
            this.propertyType = propertyEntity.getType();
            this.rent = propertyEntity.getRent();
            this.noOfBedroom = propertyEntity.getNoOfBedroom();
            this.noOfBathroom = propertyEntity.getNoOfBathroom();
            this.noOfParking = propertyEntity.getNoOfParking();
            this.hasBalcony = propertyEntity.getHasBalcony();
            this.hasDishwater = propertyEntity.getHasDishWasher();
            this.hasSecureParking = propertyEntity.getHasSecureParking();
            this.hasAc = propertyEntity.getHasAc();
            this.hasWardrobe = propertyEntity.getHasWardrobe();
            this.listedDate = propertyEntity.getListedDate();
            this.inspectionDate = propertyEntity.getInspection();
            this.additionalImagesE = propertyEntity.getImages();
            this.map = propertyEntity.getMap();
            this.propertyDetails = propertyEntity.getPropertyDetails();
        }
    }

    public String submit() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            propertyEntity = new PropertyEntity();
            if (mainImage != null) {
                try (InputStream input = mainImage.getInputStream()) {
                    String fileName = getSubmittedFileName(mainImage);
                    String fileLocation = System.getProperty("OZPROPERTYHUB_UPLOAD_LOCATION") + "/" + fileName;
                    File outputFile = new File(fileLocation);

                    try (FileOutputStream output = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = input.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }

                        propertyEntity.setMainImage(fileName);
                    }

                    // Additional logic after saving the file
                } catch (IOException e) {
                    // Handle exception
                    System.out.println(e);
                }
            } else if (mainImageUrl != null) {
                propertyEntity.setMainImage(mainImageUrl);
            }

            addressEntity = new AddressEntity();
            addressEntity.setUnit(unitNumber);
            addressEntity.setStreet_number(streetNumber);
            addressEntity.setStreet_name(streetName);
            addressEntity.setSuburb(suburb);
            addressEntity.setPostcode(postCode);
            addressEntity.setState(state); // Convert enum to string

            if (this.aid != 0) {
                addressEntity.setId(aid);
                addressEJB.updateAddress(addressEntity);
            } else {
                // Save the AddressEntity in the database
                addressEJB.addAddress(addressEntity);
            }

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
            propertyEntity.setNoOfBedroom(noOfBedroom);
            propertyEntity.setMap(map);
            propertyEntity.setPropertyDetails(propertyDetails);

            // Associate the created AddressEntity with the PropertyEntity
            propertyEntity.setAddress(addressEntity);

            UserEntity existingAgent = em.find(UserEntity.class, userBean.getId()); // Use the correct agent ID here

            propertyEntity.setAgent(existingAgent);

            propertyEntity.setImages(additionalImagesE);

            if (this.pid != 0) {
                propertyEntity.setPid(pid);
                propertyEJB.updateProperty(propertyEntity);
            } else {
                // Save the PropertyEntity in the database

                propertyEJB.addProperty(propertyEntity);
            }

            if (additionalImages.getSize() > 0) {
                for (org.primefaces.model.file.UploadedFile uploadedFile : additionalImages.getFiles()) {
                    try {
                        String fileName = uploadedFile.getFileName();
                        String fileLocation = System.getProperty("OZPROPERTYHUB_UPLOAD_LOCATION") + "/" + fileName;
                        try (InputStream inputStream = uploadedFile.getInputStream()) {
                            Files.copy(inputStream, Paths.get(fileLocation), StandardCopyOption.REPLACE_EXISTING);
                            PropertyImageEntity pie = new PropertyImageEntity();
                            pie.setProperty(propertyEntity);
                            pie.setImage(fileName);
                            propertyImageEJB.addPropertyImage(pie);
                            propertyEntity.addImage(pie);
                        } catch (IOException e) {
                            System.out.println(e);
                            // Handle the exception
                        }
                        // Process the file content, save it, or do whatever you need.
                    } catch (Exception e) {
                        // Handle the exception
                        System.out.println(e);
                    }
                }
            }

            for (PropertyImageEntity removedImage : removedImagesE) {
                propertyImageEJB.removePropertyImage(removedImage);
            }
            propertyEJB.updateProperty(propertyEntity);

            return "/dashboard/agent/agent_dashboard.faces?faces-redirect=true";

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

    public void executeDemoListing(Integer agentId) {
        //         registerDemoProperties(Integer propertyId, String mainImageUrl, Double rent, PropertyType propertyType, 
//          String map, 
//         String propertyDetails, 
//            Boolean hasAc, Boolean hasSecureParking, Boolean hasDishwater, Boolean hasWardrobe, Boolean hasBalcony,
//            Integer noOfParking, Integer noOfBathroom, Integer noOfBedroom,
//            String unitNumber, String streetNumber, String streetName,String suburb, String postCode, StateType state,
//            Integer agentId, List<PropertyImageEntity> additionalImagesE )

            Random random = new Random();
            String propertyDetails = "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.";
            String map = "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3312.7081619008386!2d151.20256887642336!3d-33.87141127322549!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6b12ae38ac7b59c3%3A0xfe1a2414acd05bdb!2sCQUniversity%20Sydney!5e0!3m2!1sen!2sau!4v1691202775933!5m2!1sen!2sau";
            
              DecimalFormat decimalFormat = new DecimalFormat("#.##");
        
            registerDemoProperties("1.jpg", Double.parseDouble(decimalFormat.format(random.nextDouble(50.0, 750.0))), PropertyType.values()[random.nextInt(PropertyType.values().length)],
            map,
            propertyDetails,
             random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), 
             random.nextInt(1,4),  random.nextInt(1,4),  random.nextInt(1,4),
             random.nextInt(1, 20)+"", "9-11","hawkesbury road", "Westmead", "2145", StateType.values()[random.nextInt(StateType.values().length)],
             agentId, new ArrayList()
            );
 
             registerDemoProperties("1.jpg", Double.parseDouble(decimalFormat.format(random.nextDouble(50.0, 750.0))), PropertyType.values()[random.nextInt(PropertyType.values().length)],
            map,
            propertyDetails,
             random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), 
             random.nextInt(1,4),  random.nextInt(1,4),  random.nextInt(1,4),
             random.nextInt(1, 20)+"", "74","hawkesbury road", "Strathfield", "2145", StateType.values()[random.nextInt(StateType.values().length)],
             agentId, new ArrayList()
            );
             
              registerDemoProperties("1.jpg", Double.parseDouble(decimalFormat.format(random.nextDouble(50.0, 750.0))), PropertyType.values()[random.nextInt(PropertyType.values().length)],
            map,
            propertyDetails,
             random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), 
             random.nextInt(1,4),  random.nextInt(1,4),  random.nextInt(1,4),
             random.nextInt(1, 20)+"", "First Street","hawkesbury road", "Auburn", "2145", StateType.values()[random.nextInt(StateType.values().length)],
             agentId, new ArrayList()
            );
    }
    
    public void registerDemoProperties( String mainImageUrl, Double rent, PropertyType propertyType, String map, String propertyDetails, 
            Boolean hasAc, Boolean hasSecureParking, Boolean hasDishwater, Boolean hasWardrobe, Boolean hasBalcony,
            Integer noOfParking, Integer noOfBathroom, Integer noOfBedroom,
            String unitNumber, String streetNumber, String streetName,String suburb, String postCode, StateType state,
            Integer agentId, List<PropertyImageEntity> additionalImagesE ) {
        
        try {
//             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//             Date date = dateFormat.parse("2023-09-04 14:00:55");
//            PropertyEntity propertyEntity = propertyEJB.getProperty(propertyId);
//            if (propertyEntity == null ) {
//                
            propertyEntity = new PropertyEntity();
            
            propertyEntity.setMainImage(mainImageUrl);


            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setUnit(unitNumber);
            addressEntity.setStreet_number(streetNumber);
            addressEntity.setStreet_name(streetName);
            addressEntity.setSuburb(suburb);
            addressEntity.setPostcode(postCode);
            addressEntity.setState(state); 
    
            
            // Create a new PropertyEntity and set its attributes
            propertyEntity.setRent(rent);
            propertyEntity.setType(propertyType);
            propertyEntity.setInspection(new Date());
            propertyEntity.setListedDate(new Date());
            propertyEntity.setHasAc(hasAc);
            propertyEntity.setHasSecureParking(hasSecureParking);
            propertyEntity.setHasDishWasher(hasDishwater);
            propertyEntity.setHasBalcony(hasBalcony);
            propertyEntity.setHasWardrobe(hasWardrobe);
            propertyEntity.setNoOfParking(noOfParking);
            propertyEntity.setNoOfBathroom(noOfBathroom);
            propertyEntity.setNoOfBedroom(noOfBedroom);
            propertyEntity.setMap(map);
            propertyEntity.setPropertyDetails(propertyDetails);

            // Associate the created AddressEntity with the PropertyEntity
            propertyEntity.setAddress(addressEntity);

            UserEntity existingAgent = em.find(UserEntity.class, agentId); // Use the correct agent ID here

            propertyEntity.setAgent(existingAgent);

            propertyEntity.setImages(additionalImagesE);

            propertyEJB.addProperty(propertyEntity);
            
            for (PropertyImageEntity removedImage : removedImagesE) {
                propertyImageEJB.removePropertyImage(removedImage);
            }
            propertyEJB.updateProperty(propertyEntity);

//        }
        } catch (Exception e) {
           
        }
    }
    
    public List<PropertyEntity> getPropertiesByAgent(Boolean isActive) {
        List<PropertyEntity> list = propertyEJB.getPropertiesByAgent(userBean.getId());
        return list;
    }

     public PropertyEntity getPropertiesByPid(Integer pid) {
      return propertyEJB.getProperty(pid);
    }
     
    public String editProperty(PropertyEntity property) {
        return "/dashboard/agent/property_form.faces?faces-redirect=true&id=" + property.getPid();
    }

    public String deleteProperty(PropertyEntity property) {
        if (propertyEJB.deleteProperty(property) != null) {
            return "/dashboard/agent/agent_dashboard.faces?faces-redirect=true";
        } else {
            return null;
        }
    }

    public void removeImage(PropertyImageEntity image) {
        additionalImagesE.remove(image);
        removedImagesE.add(image);
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

    public String getApartmentTypeValue() {
        return propertyType.name().toLowerCase();
    }
    
    @Override
    public String toString() {
        return "PropertyController{" + "em=" + em + ", pid=" + pid + ", aid=" + aid + ", unitNumber=" + unitNumber + ", streetName=" + streetName + ", streetNumber=" + streetNumber + ", suburb=" + suburb + ", state=" + state + ", propertyDetails=" + propertyDetails + ", map=" + map + ", postCode=" + postCode + ", mainImage=" + mainImage + ", mainImageUrl=" + mainImageUrl + ", propertyType=" + propertyType + ", rent=" + rent + ", noOfBedroom=" + noOfBedroom + ", noOfBathroom=" + noOfBathroom + ", noOfParking=" + noOfParking + ", hasBalcony=" + hasBalcony + ", hasDishwater=" + hasDishwater + ", hasAc=" + hasAc + ", hasSecureParking=" + hasSecureParking + ", hasWardrobe=" + hasWardrobe + ", listedDate=" + listedDate + ", inspectionDate=" + inspectionDate + ", additionalImages=" + additionalImages + ", additionalImagesE=" + additionalImagesE + ", removedImagesE=" + removedImagesE + ", propertyEJB=" + propertyEJB + ", addressEJB=" + addressEJB + ", propertyImageEJB=" + propertyImageEJB + ", userBean=" + userBean + ", propertyEntity=" + propertyEntity + ", addressEntity=" + addressEntity + '}';
    }
    
    
}
