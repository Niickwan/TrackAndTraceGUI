package mnd.trackandtracegui;

public class Sender extends User {
    private int senderID;
    private String email;
    private String password;

    public Sender() {
    }

    public Sender(String name, String address, int postalCode, int senderID, String email, String password) {
        super(name, address, postalCode);
        this.senderID = senderID;
        this.email = email;
        this.password = password;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
