package com.lifesystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText(
                "Please enter both username and password.");
            return;
        }

        if (username.equals("admin") &&
            password.equals("admin123")) {
            lblError.setText("");
            System.out.println("Login successful!");
        } else {
            lblError.setText(
                "Incorrect username or password.");
        }
    }
}