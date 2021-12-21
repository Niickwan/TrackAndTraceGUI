package mnd.trackandtracegui;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySQL {
    private Container container;
    private MySQL mySQL;

    public MySQL() {
        this.container = Container.getInstance();
    }

    public static Connection connectionToMySql() {
        try {
            String url = "jdbc:mysql://mysql85.unoeuro.com:3306/danielguldberg_dk_db";  // danielguldberg_dk_db_bank2
            return DriverManager.getConnection(url, "danielguldberg_dk", "280781");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check if Email and Passowrd matches with database
     *
     * @param email
     * @param password
     * @return Sender(User who is logged in) object
     */
    public Sender logIn(String email, String password) {
        Sender sender = null;
        try {
            Connection conn = connectionToMySql();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("Select * FROM sender WHERE Email = '" + email + "' AND Password = '" + password + "'");
            if (result.next()) {
                sender = new Sender(
                        result.getString("Name"),
                        result.getString("Address"),
                        result.getInt("PostalCode"),
                        result.getInt("SenderID"),
                        result.getString("Email"),
                        result.getString("Password"));
                container.setSender(sender);
                stmt.close();
                conn.close();
            } else {
                System.out.println("nothing");
                System.out.println(result.next());
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sender;
    }

    /**
     * Create new Sender in database and return Sender object
     *
     * @param email
     * @param password
     * @param name
     * @param address
     * @param postalCode
     * @return Sender(User who is logged in) object
     */
    public Sender createNewSender(String email, String password, String name, String address, int postalCode) {
        Sender sender = null;
        try {
            Connection conn = connectionToMySql();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO sender (email, password, name, address, postalcode) " +
                    "VALUES ('" + email + "', '" + password + "', '" + name + "', '" + address + "', '" + postalCode + "');");
            sender = logIn(email, password);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sender;
    }

    public Receiver createNewReceiver(String name, String address, String postalcode, boolean savedReceiver) {
        container.setReceiver(new Receiver(name, address, Integer.parseInt(postalcode)));
        int saved = 0;
        // If user want to save receiver, saved = SenderID
        if (savedReceiver == true) {
            saved = container.getSender().getSenderID();
        }
        if (receiverNotExists(address, saved, name)) {
            Receiver receiver;
            try {
                Connection conn = connectionToMySql();
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("INSERT INTO receiver (fk_SenderID, Name, Address, Postalcode, SavedReceiver) " +
                        "VALUES (" + Container.getInstance().getSender().getSenderID() + ", '" + name + "', '" + address
                        + "', " + Integer.parseInt(postalcode) + ", " + saved + ");");
                    receiver = new Receiver(
                            name,
                            address,
                            Integer.parseInt(postalcode),
                            receiverGetID()
                    );
                    container.setReceiver(receiver);
                    stmt.close();
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return container.getReceiver();
    }

    public boolean receiverNotExists(String address, int saved, String name) {
        Receiver receiver;
        try {
            Connection conn = connectionToMySql();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM receiver WHERE Address = '" + address + "' " +
                    "AND savedReceiver = " + saved + " AND Name = '" + name + "'");
            if (result.next()) {
                receiver = new Receiver(
                        result.getString("Name"),
                        result.getString("Address"),
                        result.getInt("PostalCode"),
                        result.getInt("ReceiverID")
                );
                container.setReceiver(receiver);
                return false;
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Sender getLoggedInUser() {
        return container.getSender();
    }

    /**
     * Create new ArrayList with Receivers saved by Senders,
     * and set drop down button with Receivers and their info
     *
     * @param senderID
     * @param splitMenuButton
     * @param rNavn
     * @param rAddress
     * @param rPostaLCode
     * @return
     */
    public ArrayList<MenuItem> getReceiverList(int senderID, SplitMenuButton splitMenuButton, TextField rNavn, TextField rAddress, TextField rPostaLCode) {
        ArrayList<MenuItem> receiverList = new ArrayList<>();
        try {
            Connection conn = connectionToMySql();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("Select * FROM receiver WHERE fk_SenderID =" + senderID + " AND SavedReceiver = " + senderID);
            ArrayList<MenuItem> menuItemList = new ArrayList<>();
            while (result.next()) {
                Receiver receiver = new Receiver(
                        result.getString("Name"),
                        result.getString("Address"),
                        result.getInt("PostalCode"),
                        result.getInt("ReceiverID"));
                MenuItem choice = new MenuItem(result.getString("Name"));
                choice.setOnAction(e -> {
                    rNavn.setText(receiver.getName());
                    rAddress.setText(receiver.getAddress());
                    rPostaLCode.setText(String.valueOf(receiver.getPostalCode()));
                    container.getReceiver().setReceiverID(receiver.getReceiverID());
                    container.setReceiver(receiver);
                    //TODO TEST MÅSKE VEND TILBAGE HVIS TID:
/*                    if(rPostalCode.getText().length() >= 4) {
                        if(rPostalCode.getText().length() >= 5) {
                            rPostalCode.setText(rPostalCode.getText().substring(0, 4));
                        }
                        System.out.println(rPostalCode.getText());
                        db.getCarrierPriceAndDeliveryDays(Integer.parseInt(postalCode.getText()), Integer.parseInt(rPostalCode.getText()), PostNord, GLS, Express, UPS);
                    }*/
                });
                receiverList.add(choice);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < receiverList.size(); i++) {
            splitMenuButton.getItems().add(receiverList.get(i));
        }
        return receiverList;
    }

    public int regionsAfstand(int fraPostnummer, int tilPostnummer) {
        int fraRegion = 0;
        int tilRegion = 0;
        if (fraPostnummer >= 1000 && fraPostnummer <= 4999) {
            fraRegion = 1;
        }
        if (fraPostnummer > 5000 && fraPostnummer <= 5999) {
            fraRegion = 2;
        }
        if (fraPostnummer > 6000) {
            fraRegion = 3;
        }

        if (tilPostnummer >= 1000 && tilPostnummer <= 4999) {
            tilRegion = 1;
        }
        if (tilPostnummer > 5000 && tilPostnummer <= 5999) {
            tilRegion = 2;
        }
        if (tilPostnummer > 6000) {
            tilRegion = 3;
        }
//        System.out.println(fraRegion);
//        System.out.println(tilRegion);
        System.out.println(Math.abs(fraRegion - tilRegion));
        return Math.abs(fraRegion - tilRegion);
    }

    public void getCarrierPriceAndDeliveryDays(int fraPostnummer, int tilPostnummer, RadioButton postNord, RadioButton gls, RadioButton express, RadioButton ups) {
        int regionsAfstand = regionsAfstand(fraPostnummer, tilPostnummer);
        try {
            Connection conn = connectionToMySql();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("Select * FROM carrier WHERE RegionDistance = " + regionsAfstand);

            //TODO Ret carrier properties til carrier-objekt:
            while (result.next()) {
                if (result.getString("Name").equalsIgnoreCase("postnord")) {
                    postNord.setText("PostNord (" + result.getString("Days") + " dage) +" + result.getString("Price").substring(0, 2) + " kr");
                    container.setPostNordID(Integer.parseInt(result.getString("CarrierID")));
                    container.setPostNord_price(Double.parseDouble(result.getString("Price")));
                    container.setDeliveryDaysPostNord(Integer.parseInt(result.getString("Days")));
                }
                if (result.getString("Name").equalsIgnoreCase("gls")) {
                    gls.setText("GLS (" + result.getString("Days") + " dage) +" + result.getString("Price").substring(0, 2) + " kr");
                    container.setGlsID(Integer.parseInt(result.getString("CarrierID")));
                    container.setGLS_price(Double.parseDouble(result.getString("Price")));
                    container.setDeliveryDaysGLS(Integer.parseInt(result.getString("Days")));
                }
                if (result.getString("Name").equalsIgnoreCase("Express")) {
                    express.setText("Express (" + result.getString("Days") + " dage) +" + result.getString("Price").substring(0, 2) + " kr");
                    container.setExpressID(Integer.parseInt(result.getString("CarrierID")));
                    container.setExpress_price(Double.parseDouble(result.getString("Price")));
                    container.setDeliveryDaysExpress(Integer.parseInt(result.getString("Days")));
                    System.out.println("+++" + result.getString("Days"));
                }
                if (result.getString("Name").equalsIgnoreCase("ups")) {
                    ups.setText("UPS (" + result.getString("Days") + " dage) +" + result.getString("Price").substring(0, 2) + " kr");
                    container.setUpsID(Integer.parseInt(result.getString("CarrierID")));
                    container.setUPS_price(Double.parseDouble(result.getString("Price")));
                    container.setDeliveryDaysUPS(Integer.parseInt(result.getString("Days")));

                }
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String calcWeightPrice(String weight) {
        final double rate = 6.5;
        if (weight != "") {
            container.setWeightPrice(rate * Double.parseDouble(weight));
            return String.valueOf("+" + rate * Integer.parseInt(weight) + " kr");
        }
        return "0 kr";
    }

    public void savePackageDestinations(LocalDate localdate, int i, int shipmentID) {
        String location;
        LocalDate dateNow = localdate;
        if (i - 1 == 0) {
            location = container.getSender().getAddress();
        } else if (i == container.getDeliveryDays() + 1) {
            location = container.getReceiver().getAddress();
            dateNow = dateNow.plusDays(i-1);
        } else {
            location = "Pakkecentral Odense";
            dateNow = dateNow.plusDays(i-1);
        }
        try {
            System.out.println(dateNow);
            Connection conn = connectionToMySql();
            Statement stmt = conn.createStatement();
            String SQL = "INSERT INTO package (ShipmentID, fk_CarrierID, fk_SenderID, fk_ReceiverID, " +
                    "SizeWidth, SizeHeight, SizeLength, Weight, LocationDateStamp, CurrentLocation, TrackingCode, FragileFreigt, TotalPrice) " +
                    "VALUES (" + shipmentID + ", " + container.getCarrier().carrierID + ", " + container.getSender().getSenderID() + ", " +
                    container.getReceiver().getReceiverID() + ", " + container.getaPackage().getPackageWidth() + ", " +
                    container.getaPackage().getPackageHeight() + ", " + container.getaPackage().getPackageLength() + ", " +
                    container.getaPackage().getWeight() + ", DATE '" + dateNow + "', '" +
                    location  + "', '" + container.getaPackage().getTrackingCode()  + "', " +
                    container.getaPackage().getFragileFreight() + ", " + container.getaPackage().getTotalPrice() + ")";
            System.out.println(SQL);
            stmt.executeUpdate(SQL);
            stmt.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getCurrentShipmentID() {
        Connection conn = connectionToMySql();
        try {
            conn = connectionToMySql();
            Statement statement = conn.createStatement();
            int counter = 0;
            ResultSet resultsetIDs = statement.executeQuery("select * from package ORDER BY ShipmentID DESC LIMIT 1;");
            while (resultsetIDs.next()) {
                counter = counter + resultsetIDs.getInt("ShipmentID");

            }
            statement.close();
            conn.close();
            return counter;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void getCarrierInformation(int carrierID) {
        Carrier carrier;
        try {
            Connection conn = connectionToMySql();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("Select * FROM carrier WHERE CarrierID = " + carrierID);

            while(result.next()) {
                carrier = new Carrier(
                        result.getInt("CarrierID"),
                        result.getString("Name"),
                        result.getDouble("Price"),
                        result.getInt("RegionDistance")
                );
                container.setCarrier(carrier);
            }
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int receiverGetID() {
        try {
            Connection conn = connectionToMySql();
            Statement statement = conn.createStatement();
            int counter = 0;
            ResultSet resultsetIDs = statement.executeQuery("select ReceiverID from receiver ORDER BY ReceiverID DESC LIMIT 1;");
            while (resultsetIDs.next()) {
                counter = counter + resultsetIDs.getInt("ReceiverID");

            }
            statement.close();
            conn.close();
            return counter;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getTntSearchInfo(String traceCode, ListView listView, TitledPane titledPane, Label statusText) {
        String result = "";
        boolean resultatFundet = false;
        try {
            Connection conn = connectionToMySql();
            Statement statement = conn.createStatement();

            String sql = "SELECT package.PackageEventID, package.ShipmentID, carrier.Name carrierName, " +
                    "sender.SenderID, sender.Name senderName, sender.Address senderAddress, postalcodes.City, " +
                    "receiver.Name receiverName, receiver.Address receiverAddress, postalcodes_1.City, carrier.Days " +
                    "carrierDays, carrier.RegionDistance, package.LocationDateStamp locationDateStamp, " +
                    "package.CurrentLocation currentLocation, package.TrackingCode FROM postalcodes " +
                    "INNER JOIN ((((package INNER JOIN carrier ON package.fk_CarrierID = carrier.CarrierID) " +
                    "INNER JOIN receiver ON package.fk_ReceiverID = receiver.ReceiverID) " +
                    "INNER JOIN sender ON receiver.fk_SenderID = sender.SenderID) " +
                    "INNER JOIN postalcodes AS postalcodes_1 ON receiver.PostalCode = postalcodes_1.PostalCode) " +
                    "ON postalcodes.PostalCode = sender.PostalCode WHERE (((package.LocationDateStamp)<=CURRENT_DATE()) AND ((package.TrackingCode)='" + traceCode + "')) " +
                    "ORDER BY package.TrackingCode, package.LocationDateStamp DESC;";
            ResultSet resultset = statement.executeQuery(sql);

            int numberOfResults = 0;
            String locationDateStamp = "";
            String currentLocation = "";
            ArrayList<String> arr = new ArrayList<>();
            arr.add(String.format("%-53s %-50s", "DATO", "LOKATION"));
            while (resultset.next()) {
                // build string of info here:
                // senderName, senderAddress, receiverName, receiverAddress, currentLocation, locationDateStamp,

                result = resultset.getString("carrierName");
                resultatFundet = true;
                locationDateStamp = resultset.getString(13);
                currentLocation = resultset.getString(14);
                arr.add(String.format("%-50s %-50s", locationDateStamp, currentLocation));
            }
            ObservableList<String> list = FXCollections.observableArrayList(arr);

            if(resultatFundet) {
                titledPane.setVisible(true);
                statusText.setVisible(true);
                //listView.setText("Track and Trace: " + traceCode);
                listView.setItems(list);
            }
            statement.close();
            conn.close();
        } catch (Exception e) {

        }
        return result;
    }


    public String getTntAllSearchInfo(int senderID, Accordion accordion, String tntNo) {
        int getLastFive = 0;
        boolean stopFetch = false;

        String result = "";
        ArrayList<Integer> searchShipmentID = new ArrayList<>();
        ArrayList<Integer> ShipmentID = new ArrayList<>();

        // Get all shipmentID's linked to SenderID
        try {
            Connection conn = connectionToMySql();
            Statement statement = conn.createStatement();
            String sql = "";
            if(!tntNo.equalsIgnoreCase("0")) {
                accordion.getPanes().clear();
                sql = "SELECT ShipmentID FROM package WHERE fk_SenderID = " + senderID + " && TrackingCode = '" + tntNo + "' ORDER BY PackageEventID DESC";
            } else {
                sql = "SELECT ShipmentID FROM package WHERE fk_SenderID = " + senderID + " ORDER BY PackageEventID DESC";
            }

            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                ShipmentID.add(resultset.getInt("ShipmentID"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < ShipmentID.size()-1; i++) {
            if(ShipmentID.get(i) != ShipmentID.get(i+1) && !stopFetch){
                getLastFive++;
                searchShipmentID.add(ShipmentID.get(i));
                if(getLastFive == 5) {
                    stopFetch = true;
                }
            }
            if(i == ShipmentID.size()-2) {
                searchShipmentID.add(ShipmentID.get(i));
            }
        }

//        for (int i = 0; i < searchShipmentID.size(); i++) {
//            System.out.println(searchShipmentID.get(i));
//
//        }

        // Create Accordion(Table) with last 5 shipments
        for (int i = 0; i < searchShipmentID.size() && i < 5; i++) {
            accordion.getPanes().add(setAccordion(searchShipmentID.get(i)));
        }

        return result;
    }

    private TitledPane setAccordion(int shipmentID) {
        TitledPane titledPane = new TitledPane();
        VBox content = new VBox();
        content.getChildren().add(new Label(String.format("%-53s %-50s", "DATO", "LOKATION")));
        titledPane.setContent(content);
        try {
            Connection conn = connectionToMySql();
            Statement statement = conn.createStatement();
            String sql = "SELECT LocationDateStamp, CurrentLocation, TrackingCode FROM package WHERE ShipmentID = " + shipmentID + " ORDER BY PackageEventID DESC";

            ResultSet resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                titledPane.setText("Track and Trace: " + resultset.getString("TrackingCode"));
                content.getChildren().add(new Label(String.format("%-50s %-50s", resultset.getString("LocationDateStamp"), resultset.getString("CurrentLocation"))));
                titledPane.setContent(content);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return titledPane;
    }
}

