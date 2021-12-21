package mnd.trackandtracegui;

public class Carrier {
    int carrierID;
    String Name;
    double Price;
    int RegionDistance;

    public Carrier(int carrierID, String name, double price, int regionDistance) {
        this.carrierID = carrierID;
        this.Name = name;
        this.Price = price;
        this.RegionDistance = regionDistance;
    }

    public int getCarrierID() {
        return carrierID;
    }

    public void setCarrierID(int carrierID) {
        this.carrierID = carrierID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getRegionDistance() {
        return RegionDistance;
    }

    public void setRegionDistance(int regionDistance) {
        RegionDistance = regionDistance;
    }
}
