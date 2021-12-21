package mnd.trackandtracegui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateReceiverController {
    private Sender sender = Container.getInstance().getSender();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField name, email, address, postalCode;

    @FXML
    private CheckBox saveReceiver;

    @FXML
    public void visNextForm(Sender sender, ActionEvent event, String screen, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(screen));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        name.setText(sender.getName());
        email.setText(sender.getName());
        address.setText(sender.getAddress());
        postalCode.setText(String.valueOf(sender.getPostalCode()));
    }
}
