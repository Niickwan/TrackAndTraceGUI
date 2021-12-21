package mnd.trackandtracegui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class CreateNewPackageController {
    private Sender sender = Container.getInstance().getSender();
    private CreateReceiverController createReceiverController = new CreateReceiverController();
    private Container container = Container.getInstance();
    private PaymentController paymentController = new PaymentController();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private MySQL db = new MySQL();

    @FXML
    private Button pay;

    @FXML
    private Label priceKg;

    @FXML
    private TextField name, email, address, postalCode, rName, rAddress, rPostalCode, weight, height, length, width;

    @FXML
    private SplitMenuButton savedReceiverList;

    @FXML
    private CheckBox saveReceiver, fragile;

    @FXML
    private RadioButton PostNord, GLS, Express, UPS;

    @FXML
    public void showNextForm(Sender sender, ActionEvent event, String screen, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(screen));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("file:C:/Users/Nickwan/IdeaProjects/TrackAndTraceGUI/src/main/java/mnd/trackandtracegui/css/login.css");
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToPayment(ActionEvent event) throws IOException {
        if (postalCode.getText().length() == 4 && rPostalCode.getText().length() == 4) {
            db.createNewReceiver(rName.getText(), rAddress.getText(), rPostalCode.getText(), saveReceiver.isSelected());
            Package aPackage = new Package();

            //TODO Ret container variable til lokale variable hvis tid:
            if (GLS.isSelected()) {
                db.getCarrierInformation(container.getGlsID());
                aPackage.setFk_CarrierID(container.getGlsID());
                aPackage.setCarrierPrice(container.getGLS_price());
                container.setDeliveryDays(container.getDeliveryDaysGLS());
            }
            if (PostNord.isSelected()) {
                db.getCarrierInformation(container.getPostNordID());
                aPackage.setFk_CarrierID(container.getPostNordID());
                aPackage.setCarrierPrice(container.getPostNord_price());
                container.setDeliveryDays(container.getDeliveryDaysPostNord());
            }
            if (UPS.isSelected()) {
                db.getCarrierInformation(container.getUpsID());
                aPackage.setFk_CarrierID(container.getUpsID());
                aPackage.setCarrierPrice(container.getUPS_price());
                container.setDeliveryDays(container.getDeliveryDaysUPS());
            }
            if (Express.isSelected()) {
                db.getCarrierInformation(container.getExpressID());
                aPackage.setFk_CarrierID(container.getExpressID());
                aPackage.setCarrierPrice(container.getExpress_price());
                container.setDeliveryDays(container.getDeliveryDaysExpress());
            }

            aPackage.setPackageHeight(Double.parseDouble(height.getText()));
            aPackage.setPackageWidth(Double.parseDouble(width.getText()));
            aPackage.setPackageLength(Double.parseDouble(length.getText()));
            aPackage.setWeight(Double.parseDouble(weight.getText()));
            // TODO make this look good plz
            if (fragile.isSelected()) {
                aPackage.setFragileFreight(1);
                aPackage.setFragilePrice(29.0);
                aPackage.setTotalPrice(container.getCarrierPrice() + container.getWeightPrice() + 29.0);
            } else {
                aPackage.setFragileFreight(0);
                aPackage.setTotalPrice(container.getCarrierPrice() + container.getWeightPrice());
            }
            aPackage.setTrackingCode(UUID.randomUUID().toString().substring(0, 7));
            aPackage.setWeightPrice(container.getWeightPrice());
            aPackage.setFk_SenderID(container.getSender().getSenderID());
            aPackage.setFk_ReceiverID(container.getReceiver().getReceiverID());
            container.setaPackage(aPackage);
            paymentController.showNextForm(sender, event, "payment.fxml", "Vælg betalings metode");
        }
    }

    @FXML
    public void initialize() {
        name.setText(sender.getName());
        email.setText(sender.getName());
        address.setText(sender.getAddress());
        postalCode.setText(String.valueOf(sender.getPostalCode()));
        db.getReceiverList(sender.getSenderID(), savedReceiverList, rName, rAddress, rPostalCode);

        rPostalCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if(postalCode.getText().length() == 4 && rPostalCode.getText().length() == 4)
                    db.getCarrierPriceAndDeliveryDays(Integer.parseInt(postalCode.getText()), Integer.parseInt(rPostalCode.getText()), PostNord, GLS, Express, UPS);
                }
        });

//        rPostalCode.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                if(rPostalCode.getText().length() >= 4) {
//                    if(rPostalCode.getText().length() >= 5) {
//                        rPostalCode.setText(rPostalCode.getText().substring(0, 4));
//                    }
//                    System.out.println(rPostalCode.getText());
//                    db.getCarrierPriceAndDeliveryDays(Integer.parseInt(postalCode.getText()), Integer.parseInt(rPostalCode.getText()), PostNord, GLS, Express, UPS);
//                }
//            }
//        });

        weight.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                priceKg.setText(db.calcWeightPrice(weight.getText()));
            }
        });
    }

    public void goToSearchPackage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user-start-screen.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add("file:C:/Users/Nickwan/IdeaProjects/TrackAndTraceGUI/src/main/java/mnd/trackandtracegui/css/login.css");
            stage.setTitle("Søg pakke");
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    public void goToCreateNewPackage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("create-new-package.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add("file:C:/Users/Nickwan/IdeaProjects/TrackAndTraceGUI/src/main/java/mnd/trackandtracegui/css/login.css");
            stage.setTitle("Opret ny forsendelse med track and trace");
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    public void goToLogoutAndReturnToFrontPage(ActionEvent event) {
        container.setSender(null);
        container.setReceiver(null);
        container.setCarrier(null);
        container.setaPackage(null);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("start-screen.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add("file:C:/Users/Nickwan/IdeaProjects/TrackAndTraceGUI/src/main/java/mnd/trackandtracegui/css/login.css");
            stage.setTitle("Track And Trace");
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
