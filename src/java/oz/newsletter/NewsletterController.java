package oz.newsletter;

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
import java.text.SimpleDateFormat;
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
import oz.newsletter_subscriber.NewsletterSubscriberEJB;
import oz.property_image.PropertyImageEntity;
import oz.user.UserController;
import oz.user.UserEntity;

/**
 *
 * @author bishal
 */
@ManagedBean(name = "newsletterBean")
@ViewScoped
public class NewsletterController {

    @PersistenceContext
    private EntityManager em;

    private int id;
    private String subject;
    private String body;

    @EJB
    private NewsletterEJB newsletterEJB;

    @EJB
    private NewsletterSubscriberEJB newsletterSubscriberEJB;
    
    @ManagedProperty("#{userBean}")
    private UserController userBean; // Inject the UserController bean

    private NewsletterEntity newsletterEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String nid = params.get("id");
        if (nid != null) {
            NewsletterEntity ne = newsletterEJB.getNewsletter(Integer.parseInt(nid));

            this.id = ne.getId();
            this.subject = ne.getSubject();
            this.body = ne.getBody();
        }
    }

    public String submit() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            newsletterEntity = new NewsletterEntity();
            newsletterEntity.setSubject(subject);
            newsletterEntity.setBody(body);
            
            if (this.id != 0) {
                newsletterEntity.setId(id);
                newsletterEJB.updateNewsletter(newsletterEntity);
            } else {
                newsletterEJB.createNewsletter(newsletterEntity);
            }

            return "/dashboard/admin/newsletter_list.faces?faces-redirect=true";

        } catch (Exception e) {
            Util.showMessage(context, FacesMessage.SEVERITY_ERROR, "There were some errors while adding property.", null);
            return "";
        }

    }

    public String editNewsletter(NewsletterEntity n) {
        return "/dashboard/admin/newsletter_form.faces?faces-redirect=true&id=" + n.getId();
    }

    public String deleteProperty(NewsletterEntity n) {
        if (newsletterEJB.deleteProperty(n) != null) {
            return "/dashboard/admin/newsletter_list.faces?faces-redirect=true";
        } else {
            return null;
        }
    }
}
