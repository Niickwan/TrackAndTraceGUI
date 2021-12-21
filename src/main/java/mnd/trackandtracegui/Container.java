package mnd.trackandtracegui;

public class Container {
    private Sender sender;
    private Receiver receiver;
    private Package aPackage;
    private Carrier carrier;
    private int postNordID;
    private int glsID;
    private int expressID;
    private int upsID;
    private double GLS_price;
    private double UPS_price;
    private double Express_price;
    private double PostNord_price;
    private double carrierPrice;
    private double weightPrice;
    private int deliveryDaysGLS;
    private int deliveryDaysUPS;
    private int deliveryDaysExpress;
    private int deliveryDaysPostNord;
    private int deliveryDays;
    private static Container instance;

    private Container() {
        this.sender = new Sender();
        this.receiver = new Receiver();
        this.postNordID = 0;
        this.glsID = 0;
        this.expressID = 0;
        this.upsID = 0;
        this.carrierPrice = 0;
        this.GLS_price = 0;
        this.UPS_price = 0;
        this.Express_price = 0;
        this.PostNord_price = 0;
        this.deliveryDays = 0;

    }

    /***
     * @return Instance of Container (singleton object)
     */
    public static Container getInstance() {
        if(instance == null) {
            instance = new Container();
        }
        return instance;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public int getPostNordID() {
        return postNordID;
    }

    public void setPostNordID(int postNordID) {
        this.postNordID = postNordID;
    }

    public int getGlsID() {
        return glsID;
    }

    public void setGlsID(int glsID) {
        this.glsID = glsID;
    }

    public int getExpressID() {
        return expressID;
    }

    public void setExpressID(int expressID) {
        this.expressID = expressID;
    }

    public int getUpsID() {
        return upsID;
    }

    public void setUpsID(int upsID) {
        this.upsID = upsID;
    }

    public double getCarrierPrice() {
        return carrierPrice;
    }

    public void setCarrierPrice(double carrierPrice) {
        this.carrierPrice = carrierPrice;
    }

    public double getGLS_price() {
        return GLS_price;
    }

    public void setGLS_price(double GLS_price) {
        this.GLS_price = GLS_price;
    }

    public double getUPS_price() {
        return UPS_price;
    }

    public void setUPS_price(double UPS_price) {
        this.UPS_price = UPS_price;
    }

    public double getExpress_price() {
        return Express_price;
    }

    public void setExpress_price(double express_price) {
        Express_price = express_price;
    }

    public double getPostNord_price() {
        return PostNord_price;
    }

    public void setPostNord_price(double postNord_price) {
        PostNord_price = postNord_price;
    }

    public double getWeightPrice() {
        return weightPrice;
    }

    public void setWeightPrice(double weightPrice) {
        this.weightPrice = weightPrice;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public int getDeliveryDaysGLS() {
        return deliveryDaysGLS;
    }

    public void setDeliveryDaysGLS(int deliveryDaysGLS) {
        this.deliveryDaysGLS = deliveryDaysGLS;
    }

    public int getDeliveryDaysUPS() {
        return deliveryDaysUPS;
    }

    public void setDeliveryDaysUPS(int deliveryDaysUPS) {
        this.deliveryDaysUPS = deliveryDaysUPS;
    }

    public int getDeliveryDaysExpress() {
        return deliveryDaysExpress;
    }

    public void setDeliveryDaysExpress(int deliveryDaysExpress) {
        this.deliveryDaysExpress = deliveryDaysExpress;
    }

    public int getDeliveryDaysPostNord() {
        return deliveryDaysPostNord;
    }

    public void setDeliveryDaysPostNord(int deliveryDaysPostNord) {
        this.deliveryDaysPostNord = deliveryDaysPostNord;
    }

    public int getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(int deliveryDays) {
        this.deliveryDays = deliveryDays;
    }
}
