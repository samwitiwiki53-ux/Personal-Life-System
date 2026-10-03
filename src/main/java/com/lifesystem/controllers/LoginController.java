package com.lifesystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText(
                "Please enter both username and password.");
            return;
        }

        if (username.equals("admin") &&
            password.equals("admin123")) {
            try {
                Parent root = FXMLLoader.load(
                    getClass().getResource(
                        "/fxml/hub.fxml"));
                Stage stage = (Stage)((Node) event
                    .getSource()).getScene().getWindow();
                stage.setScene(
                    new Scene(root, 1000, 650));
                stage.setTitle(
                    "Personal Life Management System");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                lblError.setText(
                    "Error loading hub screen.");
            }
        } else {
            lblError.setText(
                "Incorrect username or password.");
        }
    }
}