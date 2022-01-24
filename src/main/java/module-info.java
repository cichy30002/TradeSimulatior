module app.javaFX {
    requires javafx.controls;
    requires javafx.fxml;


    opens app.javaFX to javafx.fxml;
    exports app.javaFX;
}