package com.lifesystem.controllers;

import com.lifesystem.database.TenantDAO;
import com.lifesystem.models.Tenant;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.util.List;
import java.util.stream.Collectors;

public class TenantController {

    @FXML private TableView<Tenant> tblTenants;
    @FXML private TableColumn<Tenant,String> colName;
    @FXML private TableColumn<Tenant,String> colUnit;
    @FXML private TableColumn<Tenant,String> colProperty;
    @FXML private TableColumn<Tenant,String> colPhone;
    @FXML private TableColumn<Tenant,String> colRent;
    @FXML private TableColumn<Tenant,String> colLease;
    @FXML private TableColumn<Tenant,String> colStatus;
    @FXML private Label lblTotalTenants;
    @FXML private Label lblTotalCollected;
    @FXML private TextField txtSearch;

    private TenantDAO dao = new TenantDAO();
    private ObservableList<Tenant> tenantList =
        FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadTenants();
    }

    private void setupTableColumns() {
        colName.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getFullName()));
        colUnit.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getUnitNumber()));
        colProperty.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getPropertyType()));
        colPhone.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getPhoneNumber()));
        colRent.setCellValueFactory(c ->
            new SimpleStringProperty(
                String.format("UGX %,.0f",
                    c.getValue().getMonthlyRent())));
        colLease.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getLeaseStartDate()));
        colStatus.setCellValueFactory(c ->
            new SimpleStringProperty(
                c.getValue().getStatus()));
    }

    public void loadTenants() {
        tenantList.clear();
        tenantList.addAll(dao.getAllTenants());
        tblTenants.setItems(tenantList);
        lblTotalTenants.setText(
            String.valueOf(tenantList.size()));
        double total = dao.getTotalCollected();
        lblTotalCollected.setText(
            String.format("UGX %,.0f", total));
    }

    @FXML
    private void searchTenants() {
        String term = txtSearch.getText()
            .toLowerCase().trim();
        if (term.isEmpty()) {
            tblTenants.setItems(tenantList);
            return;
        }
        List<Tenant> filtered = tenantList.stream()
            .filter(t ->
                t.getFullName().toLowerCase()
                    .contains(term) ||
                t.getUnitNumber().toLowerCase()
                    .contains(term) ||
                t.getPropertyType().toLowerCase()
                    .contains(term))
            .collect(Collectors.toList());
        tblTenants.setItems(
            FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void openAddTenant() {
        openTenantForm(null);
    }

    @FXML
    private void openEditTenant() {
        Tenant selected =
            tblTenants.getSelectionModel()
                .getSelectedItem();
        if (selected == null) {
            showAlert("Please select a tenant to edit.");
            return;
        }
        openTenantForm(selected);
    }

    private void openTenantForm(Tenant tenant) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                    "/fxml/addTenant.fxml"));
            Parent root = loader.load();
            AddTenantController ctrl =
                loader.getController();
            ctrl.setTenantController(this);
            if (tenant != null) {
                ctrl.loadTenantForEdit(tenant);
            }
            Stage stage = new Stage();
            stage.setTitle(tenant == null ?
                "Add New Tenant" : "Edit Tenant");
            stage.setScene(new Scene(root, 520, 620));
            stage.initModality(
                Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openRecordPayment() {
        Tenant selected =
            tblTenants.getSelectionModel()
                .getSelectedItem();
        if (selected == null) {
            showAlert(
                "Please select a tenant first.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                    "/fxml/recordPayment.fxml"));
            Parent root = loader.load();
            RecordPaymentController ctrl =
                loader.getController();
            ctrl.setTenant(selected);
            ctrl.setTenantController(this);
            Stage stage = new Stage();
            stage.setTitle("Record Payment — " +
                selected.getFullName());
            stage.setScene(new Scene(root, 480, 500));
            stage.initModality(
                Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openPaymentHistory() {
        Tenant selected =
            tblTenants.getSelectionModel()
                .getSelectedItem();
        if (selected == null) {
            showAlert(
                "Please select a tenant first.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                    "/fxml/paymentHistory.fxml"));
            Parent root = loader.load();
            PaymentHistoryController ctrl =
                loader.getController();
            ctrl.setTenant(selected);
            Stage stage = new Stage();
            stage.setTitle("Payment History — " +
                selected.getFullName());
            stage.setScene(new Scene(root, 750, 480));
            stage.initModality(
                Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deactivateTenant() {
        Tenant selected =
            tblTenants.getSelectionModel()
                .getSelectedItem();
        if (selected == null) {
            showAlert(
                "Please select a tenant first.");
            return;
        }
        Alert confirm = new Alert(
            Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Deactivate Tenant");
        confirm.setHeaderText(
            "Deactivate " + selected.getFullName());
        confirm.setContentText(
            "Are you sure you want to deactivate " +
            "this tenant? They will be marked " +
            "as Inactive.");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                dao.deactivateTenant(selected.getId());
                loadTenants();
            }
        });
    }

    @FXML
    private void goBackToHub(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                getClass().getResource(
                    "/fxml/hub.fxml"));
            Stage stage = (Stage)((Node) event
                .getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 650));
            stage.setTitle(
                "Personal Life Management System");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(
            Alert.AlertType.WARNING);
        alert.setTitle("Notice");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}