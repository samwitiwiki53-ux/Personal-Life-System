package com.lifesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.lifesystem.database.MongoDBHelper;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(
            getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle(
            "Personal Life Management System");
        primaryStage.setScene(new Scene(root, 900, 600));
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