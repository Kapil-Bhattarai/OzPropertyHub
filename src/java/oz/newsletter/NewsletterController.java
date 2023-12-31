package oz.newsletter;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ManagedProperty;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import oz.Util;
import oz.newsletter_subscriber.NewsletterSubscriberEJB;
import oz.newsletter_subscriber.NewsletterSubscriberEntity;
import oz.user.UserController;

/**
 *
 * @author bishal
 */
@ManagedBean(name = "newsletterBean")
@SessionScoped
public class NewsletterController {

    @PersistenceContext
    private EntityManager em;

    private int id;
    private String subject;
    private String body;
    private boolean isSent;

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

    public UserController getUserBean() {
        return userBean;
    }

    public void setUserBean(UserController userBean) {
        this.userBean = userBean;
    }

    public boolean isIsSent() {
        return isSent;
    }

    public void setIsSent(boolean isSent) {
        this.isSent = isSent;
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

    public List<NewsletterEntity> getNewsletters() {
        List<NewsletterEntity> list = newsletterEJB.getNewsletters();
        return list;
    }

    public String editNewsletter(NewsletterEntity n) {
        return "/dashboard/admin/newsletter_form.faces?faces-redirect=true&id=" + n.getId();
    }

    public String deleteNewsletter(NewsletterEntity n) {
        if (newsletterEJB.deleteProperty(n) != null) {
            return "/dashboard/admin/newsletter_list.faces?faces-redirect=true";
        } else {
            return null;
        }
    }

    public void sendToAll(NewsletterEntity ne) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            List<NewsletterSubscriberEntity> nse = newsletterSubscriberEJB.getAllSubscribers();
            for (NewsletterSubscriberEntity subscriber : nse) {
                   Util.sendEmail(subscriber.getEmail(), "admin@gmail.com", ne.getSubject(), ne.getBody()); 
            }
            ne.setIsSent(true);
            Util.showMessage(context, FacesMessage.SEVERITY_INFO, "Emails sent successfully.", null);
            
            newsletterEJB.updateNewsletter(ne);
        } catch (Exception e) {
            System.out.println("Send to all error ======>");
            System.out.println(e);
            Util.showMessage(context, FacesMessage.SEVERITY_ERROR, "There were some errors while sending emails.", null);
        }
    }
}
