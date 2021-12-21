package mnd.trackandtracegui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class PaymentController {

    //private PaymentController paymentController = new PaymentController();
    //private CreateNewPackageController createNewPackageController = new CreateNewPackageController();
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Container container = Container.getInstance();
    private MySQL db = new MySQL();

    @FXML
    public void showNextForm(Sender sender, ActionEvent event, String screen, String title) throws IOException {
        root = FXMLLoader.load(getClass().getResource(screen));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("file:C:/Users/Nickwan/IdeaProjects/TrackAndTraceGUI/src/main/java/mnd/trackandtracegui/css/login.css");
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void completePayment() {
        if(checkPayment()) {
            LocalDate dateNow = LocalDate.now();
            int shipmentID = db.getCurrentShipmentID() + 1;
            System.out.println("--->" + container.getDeliveryDays());
            for (int i = 1; i <= container.getDeliveryDays() + 1; i++) {
                db.savePackageDestinations(dateNow, i, shipmentID);
            }
        } else {
            System.out.println("fejl i betalingen");
        }

    }

    @FXML
    public void initialize() {

    }

    private boolean checkPayment() {
        return true;
    }

    public void goToLogoutAndReturnToFrontPage(ActionEvent event) {
        container.setSender(null);
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
}
