package com.lifesystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class HubController {

    @FXML private Label lblWelcome;

    @FXML
    public void initialize() {
        lblWelcome.setText("Welcome, SAMWITIWIKI");
    }

    // Open Tenant Management
    @FXML
    private void openTenants(ActionEvent event) {
        openModule("/fxml/tenants.fxml", 
                   "Tenant Management", event);
    }

    // Open Wealth Management
    @FXML
    private void openWealth(ActionEvent event) {
        openModule("/fxml/wealth.fxml",
                   "Wealth Management", event);
    }

    // Open Shop Management
    @FXML
    private void openShop(ActionEvent event) {
        openModule("/fxml/shop.fxml",
                   "Shop Management", event);
    }

    // Open Vehicle Management
    @FXML
    private void openVehicles(ActionEvent event) {
        openModule("/fxml/vehicles.fxml",
                   "Vehicle Management", event);
    }

    // Open Farm Management
    @FXML
    private void openFarm(ActionEvent event) {
        openModule("/fxml/farm.fxml",
                   "Farm Management", event);
    }

    // Open Student Management
    @FXML
    private void openStudents(ActionEvent event) {
        openModule("/fxml/students.fxml",
                   "Student Management", event);
    }

    // Open Health Management
    @FXML
    private void openHealth(ActionEvent event) {
        openModule("/fxml/health.fxml",
                   "Health Management", event);
    }

    // Logout back to login screen
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/login.fxml"));
            Stage stage = (Stage)((Node) event
                .getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            stage.setTitle(
                "Personal Life Management System");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reusable method to open any module
    private void openModule(String fxmlPath, 
                            String title,
                            ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 1000, 650));
            stage.show();
        } catch (Exception e) {
            System.out.println(title + 
                " module not built yet.");
        }
    }
}