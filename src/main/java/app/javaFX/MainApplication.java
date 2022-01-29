package app.javaFX;

import app.controls.ControlPanel;
import app.controls.Simulation;
import app.exceptions.TransactionException;
import app.world.Investor;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-application.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        initApp(scene);
        stage.setTitle("Trade Simulator");
        stage.setScene(scene);
        stage.show();
        ControlPanel.getInstance().getSimulation().startSimulation();
    }

    private void initApp(Scene scene) {
        ApplicationController.Init(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}