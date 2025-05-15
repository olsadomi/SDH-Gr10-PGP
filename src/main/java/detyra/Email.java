package detyra;

import java.io.Serializable;

public class  Email implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sender;
    private String recipient;
    private String message;


    public Email(String sender, String recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }


    @Override
    public String toString() {
        return "From: " + sender + "\nMessage: " + message;
    }
}
