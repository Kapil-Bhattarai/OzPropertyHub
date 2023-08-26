package chat;


import java.io.Serializable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "OZ_CHAT")
public class Chat implements Serializable {

    public Chat() {}
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "message", nullable = false)
    private String message;
    
    @Column(name = "attachment", nullable = true)
    private String attachment;

    Chat(String message, String attachment) {
        this.message = message;
        this.attachment = attachment;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Chat(String message) {
        this.message = message;
    }
    

}