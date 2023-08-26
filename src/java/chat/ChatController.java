package chat;

//import java.io.Serializable;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ApplicationScoped;
import jakarta.faces.bean.ManagedBean;
//import jakarta.inject.Named;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.bean.ViewScoped;
import jakarta.inject.Named;
//import jakarta.faces.bean.SessionScoped;
//import jakarta.enterprise.context.RequestScoped;
//import jakarta.enterprise.context.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
////@Named
//@ApplicationScoped
////@Named("chatBean")
@ViewScoped

//@SessionScoped
public class ChatController {

    // Attributes             
    String message;
    public String roomId = "my-default";
    private boolean socketOpen = false;

    private void sendPushMessage(String message, String roomId) {
        System.out.println("message");
        System.out.println(roomId);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoomId() {
        return message;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void sendMessage() {
        // log.log(Level.INFO, "send push message from input box::" + this.message);
        this.sendPushMessage(this.message, this.roomId);
    }
    
    public void setRoom() {
    }

    public boolean isSocketOpen() {
        return socketOpen;
    }

    public void setSocketOpen(boolean socketOpen) {
        this.socketOpen = socketOpen;
    }
    
}
