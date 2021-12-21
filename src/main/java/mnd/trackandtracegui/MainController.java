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

public class MainController {
    private CreateNewPackageController createNewPackageController = new CreateNewPackageController();
    private Sender sender;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private MySQL db = new MySQL();

    @FXML
    private Label loggedIn;
    @FXML
    private Label nName, statusText;
    @FXML
    private TextField name, email, address, postalCode, tntField;
    @FXML
    private PasswordField password;
    @FXML
    private TitledPane titledPane;
    @FXML
    private ListView listView, listViewUser1, listViewUser2, listViewUser3;

    @FXML
    public void login(ActionEvent event) throws IOException  {
        sender = db.logIn(email.getText(), password.getText());
        if(sender != null) {
            loggedIn.setText("LoggedIn");
            //System.out.println(email.getText());
            visNextForm(sender, event, "user-start-screen.fxml", "Welcome: " + sender.getName());
        } else {
            loggedIn.setText("NONONO");
        }
    }

    @FXML
    public void visNextForm(Sender sender, ActionEvent event, String screen, String title) throws IOException  {
        Parent root = FXMLLoader.load(getClass().getResource(screen));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("file:C:/Users/Nickwan/IdeaProjects/TrackAndTraceGUI/src/main/java/mnd/trackandtracegui/css/login.css");
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void createUser(ActionEvent event) throws IOException {
        boolean inputOk = true;
        if(postalCode.getText().length() != 4 || !postalCode.getText().matches("[0-9]+")) {
            inputOk = false;
            System.out.println("Ugyldigt postnummer");
        }
        if(!email.getText().contains("@") || !email.getText().contains(".")) {
            inputOk = false;
            System.out.println("Ugyldig email");
        }
        if(
            email.getText().length() == 0 ||
            password.getText().length() == 0 ||
            name.getText().length() == 0 ||
            address.getText().length() == 0 ||
            postalCode.getText().length() == 0
        ) { inputOk = false; }

        if(inputOk) {
            sender = db.createNewSender(email.getText(), password.getText(), name.getText(), address.getText(), Integer.parseInt(postalCode.getText()));
            Parent root = FXMLLoader.load(getClass().getResource("user-start-screen.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add("file:C:/Users/Nickwan/IdeaProjects/TrackAndTraceGUI/src/main/java/mnd/trackandtracegui/css/login.css");
            stage.setTitle("Welcome " + name.getText());
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void backToLogin(ActionEvent event) throws IOException {
        sender = db.getLoggedInUser();
        visNextForm(sender, event, "login.fxml", "Track And Trace - Login");
    }

    @FXML
    public void goToCreateUserScreen(ActionEvent event) throws IOException {
        sender = db.getLoggedInUser();
        visNextForm(sender, event, "opret-bruger.fxml", "Opret ny bruger");
    }

    @FXML
    public void goToCreateNewPackage(ActionEvent event) throws IOException {
        sender = db.getLoggedInUser();
        createNewPackageController.showNextForm(sender, event, "create-new-package.fxml", "Opret ny forsendelse med track and trace");
    }

    @FXML
    public void initialize() {
        db.getTntAllSearchInfo(2);
    }

    public void searchTnt(ActionEvent event) {
        db.getTntSearchInfo(tntField.getText(), listView, titledPane, statusText);

        System.out.println(tntField.getText());
        statusText.setVisible(true);
        titledPane.setVisible(true);
        titledPane.setText("Track and Trace: " + tntField.getText());
    }

    public void goToLogin(ActionEvent event) throws IOException {
        visNextForm(sender, event, "login.fxml", "Track And Trace - Login");
        sender = db.getLoggedInUser();
    }


}