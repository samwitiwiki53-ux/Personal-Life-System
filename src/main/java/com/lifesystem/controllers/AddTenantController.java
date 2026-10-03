package com.lifesystem.controllers;

import com.lifesystem.database.TenantDAO;
import com.lifesystem.models.Tenant;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddTenantController {

    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtUnit;
    @FXML private ComboBox<String> cboProperty;
    @FXML private TextField txtRent;
    @FXML private TextField txtNationalID;
    @FXML private DatePicker dpLeaseStart;
    @FXML private DatePicker dpLeaseEnd;
    @FXML private ComboBox<String> cboStatus;
    @FXML private Label lblTitle;

    private TenantDAO dao = new TenantDAO();
    private TenantController tenantController;
    private Tenant editingTenant = null;

    @FXML
    public void initialize() {
        cboProperty.getItems().addAll(
            "Single Room",
            "Double Room",
            "Bedsitter",
            "Single Bedroom Apartment",
            "Two Bedroom Apartment",
            "Three Bedroom Apartment",
            "Boys Quarters",
            "Shop/Stall",
            "Office Space",
            "Mansion"
        );
        cboProperty.getSelectionModel()
            .selectFirst();

        cboStatus.getItems().addAll(
            "Active", "Inactive");
        cboStatus.getSelectionModel()
            .selectFirst();
    }

    public void setTenantController(
            TenantController tc) {
        this.tenantController = tc;
    }

    public void loadTenantForEdit(Tenant t) {
        this.editingTenant = t;
        lblTitle.setText("✏️  Edit Tenant");
        txtFirstName.setText(t.getFirstName());
        txtLastName.setText(t.getLastName());
        txtPhone.setText(t.getPhoneNumber());
        txtEmail.setText(t.getEmail());
        txtUnit.setText(t.getUnitNumber());
        cboProperty.setValue(t.getPropertyType());
        txtRent.setText(
            String.valueOf(t.getMonthlyRent()));
        txtNationalID.setText(t.getNationalID());
        cboStatus.setValue(t.getStatus());
        if (t.getLeaseStartDate() != null &&
                !t.getLeaseStartDate().isEmpty()) {
            dpLeaseStart.setValue(
                java.time.LocalDate.parse(
                    t.getLeaseStartDate()));
        }
        if (t.getLeaseEndDate() != null &&
                !t.getLeaseEndDate().isEmpty()) {
            dpLeaseEnd.setValue(
                java.time.LocalDate.parse(
                    t.getLeaseEndDate()));
        }
    }

    @FXML
    private void handleSave() {
        if (txtFirstName.getText().trim().isEmpty() ||
            txtLastName.getText().trim().isEmpty() ||
            txtUnit.getText().trim().isEmpty() ||
            txtRent.getText().trim().isEmpty()) {
            showAlert("Please fill in all " +
                "required fields marked with *");
            return;
        }

        double rent;
        try {
            rent = Double.parseDouble(
                txtRent.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid " +
                "monthly rent amount.");
            return;
        }

        String leaseStart = dpLeaseStart.getValue()
            != null ? dpLeaseStart.getValue()
            .toString() : "";
        String leaseEnd = dpLeaseEnd.getValue()
            != null ? dpLeaseEnd.getValue()
            .toString() : "";

        if (editingTenant == null) {
            Tenant t = new Tenant(
                txtFirstName.getText().trim(),
                txtLastName.getText().trim(),
                txtPhone.getText().trim(),
                txtEmail.getText().trim(),
                txtUnit.getText().trim(),
                cboProperty.getValue(),
                rent,
                leaseStart,
                leaseEnd,
                txtNationalID.getText().trim()
            );
            dao.saveTenant(t);
            showInfo("Tenant saved successfully!");
        } else {
            editingTenant.setFirstName(
                txtFirstName.getText().trim());
            editingTenant.setLastName(
                txtLastName.getText().trim());
            editingTenant.setPhoneNumber(
                txtPhone.getText().trim());
            editingTenant.setEmail(
                txtEmail.getText().trim());
            editingTenant.setUnitNumber(
                txtUnit.getText().trim());
            editingTenant.setPropertyType(
                cboProperty.getValue());
            editingTenant.setMonthlyRent(rent);
            editingTenant.setLeaseStartDate(
                leaseStart);
            editingTenant.setLeaseEndDate(leaseEnd);
            editingTenant.setNationalID(
                txtNationalID.getText().trim());
            editingTenant.setStatus(
                cboStatus.getValue());
            dao.updateTenant(
                editingTenant.getId(), editingTenant);
            showInfo("Tenant updated successfully!");
        }

        if (tenantController != null) {
            tenantController.loadTenants();
        }
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtFirstName
            .getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(
            Alert.AlertType.WARNING);
        alert.setTitle("Required Fields");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(
            Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}