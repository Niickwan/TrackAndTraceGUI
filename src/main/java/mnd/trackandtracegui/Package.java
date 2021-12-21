package mnd.trackandtracegui;

import java.text.SimpleDateFormat;

public class Package {
    private int packageEventID;
    private int fk_CarrierID;
    private int fk_SenderID;
    private int fk_ReceiverID;
    private double packageHeight;
    private double packageWidth;
    private double packageLength;
    private double weight;
    private SimpleDateFormat packageDateStamp;
    private String CurrentLocation;
    private String TrackingCode;
    int FragileFreight;
    int ShipmentID;
    double TotalPrice;
    double carrierPrice;
    double fragilePrice;
    double weightPrice;


    public Package() {
    }

    public Package(double packageHeight, double packageWidth, double packageLength, double weight) {
        this.packageHeight = packageHeight;
        this.packageWidth = packageWidth;
        this.packageLength = packageLength;
        this.weight = weight;
        String dateTime = "dd-MM-yyyy";
        SimpleDateFormat myFormattedDateTime = new SimpleDateFormat(dateTime);
        this.packageDateStamp = myFormattedDateTime;
    }

    public int getPackageEventID() {
        return packageEventID;
    }

    public void setPackageEventID(int packageEventID) {
        this.packageEventID = packageEventID;
    }

    public int getFk_CarrierID() {
        return fk_CarrierID;
    }

    public void setFk_CarrierID(int fk_CarrierID) {
        this.fk_CarrierID = fk_CarrierID;
    }

    public double getPackageHeight() {
        return packageHeight;
    }

    public void setPackageHeight(double packageHeight) {
        this.packageHeight = packageHeight;
    }

    public double getPackageWidth() {
        return packageWidth;
    }

    public void setPackageWidth(double packageWidth) {
        this.packageWidth = packageWidth;
    }

    public double getPackageLength() {
        return packageLength;
    }

    public void setPackageLength(double packageLength) {
        this.packageLength = packageLength;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public SimpleDateFormat getPackageDateStamp() {
        return packageDateStamp;
    }

    public void setPackageDateStamp(SimpleDateFormat packageDateStamp) {
        this.packageDateStamp = packageDateStamp;
    }

    public String getCurrentLocation() {
        return CurrentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        CurrentLocation = currentLocation;
    }

    public String getTrackingCode() {
        return TrackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        TrackingCode = trackingCode;
    }

    public int getFragileFreight() {
        return FragileFreight;
    }

    public void setFragileFreight(int fragileFreight) {
        FragileFreight = fragileFreight;
    }

    public int getShipmentID() {
        return ShipmentID;
    }

    public void setShipmentID(int shipmentID) {
        ShipmentID = shipmentID;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public double getCarrierPrice() {
        return carrierPrice;
    }

    public void setCarrierPrice(double carrierPrice) {
        this.carrierPrice = carrierPrice;
    }

    public double getFragilePrice() {
        return fragilePrice;
    }

    public void setFragilePrice(double fragilePrice) {
        this.fragilePrice = fragilePrice;
    }

    public double getWeightPrice() {
        return weightPrice;
    }

    public void setWeightPrice(double weightPrice) {
        this.weightPrice = weightPrice;
    }

    public int getFk_SenderID() {
        return fk_SenderID;
    }

    public void setFk_SenderID(int fk_SenderID) {
        this.fk_SenderID = fk_SenderID;
    }

    public int getFk_ReceiverID() {
        return fk_ReceiverID;
    }

    public void setFk_ReceiverID(int fk_ReceiverID) {
        this.fk_ReceiverID = fk_ReceiverID;
    }

    @Override
    public String toString() {
        return "Package{" +
                "packageEventID=" + packageEventID +
                ", fk_CarrierID=" + fk_CarrierID +
                ", fk_SenderID=" + fk_SenderID +
                ", fk_ReceiverID=" + fk_ReceiverID +
                ", packageHeight=" + packageHeight +
                ", packageWidth=" + packageWidth +
                ", packageLength=" + packageLength +
                ", weight=" + weight +
                ", packageDateStamp=" + packageDateStamp +
                ", CurrentLocation='" + CurrentLocation + '\'' +
                ", TrackingCode='" + TrackingCode + '\'' +
                ", FragileFreight=" + FragileFreight +
                ", ShipmentID=" + ShipmentID +
                ", TotalPrice=" + TotalPrice +
                ", carrierPrice=" + carrierPrice +
                ", fragilePrice=" + fragilePrice +
                ", weightPrice=" + weightPrice +
                '}';
    }
}
