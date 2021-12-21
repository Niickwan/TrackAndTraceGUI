package mnd.trackandtracegui;

public class Receiver extends User {
    private int receiverID;

    public Receiver() {
        
    }

    public Receiver(String name, String address, int postalCode) {
        super(name, address, postalCode);
    }

    public Receiver(String name, String address, int postalcode, int receiverID) {
        super(name, address, postalcode);
        this.receiverID = receiverID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }
}
