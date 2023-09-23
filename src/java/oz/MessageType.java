package oz;


public enum MessageType {
    
    MESSAGE_PENDING_REQUEST("Message for Pending Request"),
    MESSAGE_ACCOUNT_SUSPENSION("Message for Account Suspension"),
    MESSAGE_ACCOUNT_DELETION("Message for Account deletion"),
    MESSAGE_ACCOUNT_ACTIVATION("Message for Account activation"),
    
    MESSAGE_APPLICATION_APPROVED("Message for application approval"),
    MESSAGE_APPLICATION_REJECTED("Message for application rejection"),
    MESSAGE_APPLICATION_PENDING("Message for pending application"),
    
    ERROR_USER_NOT_FOUND("Error Message for User Not Found"),
    ERROR_PROFILE_FETCH_ERROR("Error Message for Profile Fetch Error"),
    ERROR_WHILE_SENDING_EMAIL("Error Message while sending email");
    
    private  String displayName;

    private MessageType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
