package com.lifesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.lifesystem.database.MongoDBHelper;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL fxmlUrl = getClass()
            .getResource("/fxml/login.fxml");

        if (fxmlUrl == null) {
            System.out.println(
                "ERROR: login.fxml not found!");
            return;
        }

        System.out.println(
            "Found login.fxml at: " + fxmlUrl.toString());

        Parent root = FXMLLoader.load(fxmlUrl);
        primaryStage.setTitle(
            "Personal Life Management System");
        primaryStage.setScene(
            new Scene(root, 900, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {
        MongoDBHelper.closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}