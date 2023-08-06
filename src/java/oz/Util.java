package oz;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import jakarta.faces.context.FacesContext;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


public class Util {
    
    public static final String PERSISTANCE_NAME = "oz_property_hub";
    
    public static void showMessage(FacesContext context, Severity level, String title, String description) {
           FacesMessage message = new FacesMessage(level, title, description);
            context.addMessage(null, message);
    }
    
    public static boolean sendEmail(String emailTo, String emailFrom, String emailTitle, String emailBody ) {
        //The sending of email uses a fake SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", 2525);
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(emailFrom));
            InternetAddress[] address = {new InternetAddress(emailTo)};
            message.setRecipients(Message.RecipientType.TO, address);        
            message.setSubject(emailTitle);
            message.setSentDate(new Date());          
            message.setText(emailBody);          
            Transport.send(message);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return false;
        } 

    }
}
