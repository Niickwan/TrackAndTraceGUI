module mnd.trackandtracegui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens mnd.trackandtracegui to javafx.fxml;
    exports mnd.trackandtracegui;
}