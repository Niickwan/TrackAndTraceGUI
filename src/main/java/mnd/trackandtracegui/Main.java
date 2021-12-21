

// TODO Ret afsenders postnummer til 4 chars hvis for lang (ligesom det er for modtager)
// TODO Opret Ret bruger funktionalitet
// TODO Implementer vægt-beregning
// TODO
// TODO

package mnd.trackandtracegui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(MathSolver.class.getResource("loginScene.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("start-screen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("file:C:/Users/Nickwan/IdeaProjects/TrackAndTraceGUI/src/main/java/mnd/trackandtracegui/css/login.css");
        stage.setTitle("Track And Trace");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}