package mnd.trackandtracegui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UserStartController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Container container = Container.getInstance();
    private MySQL db = new MySQL();

    @FXML
    private Accordion accordion;

    @FXML
    private TextField tntNo;

    @FXML
    public void initialize() {
        db.getTntAllSearchInfo(container.getSender().getSenderID(), accordion, "0");
    }

    public void userStartScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user-start-screen.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add("file:C:/Users/Nickwan/IdeaProjects/TrackAndTraceGUI/src/main/java/mnd/trackandtracegui/css/login.css");
            stage.setTitle("Welcome: " + container.getSender().getName());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
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

    public void searchTntNo(ActionEvent event) {
        db.getTntAllSearchInfo(container.getSender().getSenderID(), accordion, tntNo.getText());
    }
}
