package com.lifesystem.controllers;

import com.lifesystem.database.TenantDAO;
import com.lifesystem.models.Payment;
import com.lifesystem.models.Tenant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RecordPaymentController {

    @FXML private Label lblTenantName;
    @FXML private TextField txtAmount;
    @FXML private DatePicker dpPaymentDate;
    @FXML private ComboBox<String> cboMethod;
    @FXML private ComboBox<String> cboMonth;
    @FXML private ComboBox<Integer> cboYear;
    @FXML private TextArea txtNotes;

    private TenantDAO dao = new TenantDAO();
    private Tenant tenant;
    private TenantController tenantController;

    @FXML
    public void initialize() {
        cboMethod.getItems().addAll(
            "Cash", "Mobile Money",
            "Bank Transfer", "Cheque",
            "Airtel Money", "MTN MoMo");
        cboMethod.getSelectionModel().selectFirst();

        cboMonth.getItems().addAll(
            "January", "February", "March",
            "April", "May", "June", "July",
            "August", "September", "October",
            "November", "December");
        cboMonth.getSelectionModel().select(
            java.time.LocalDate.now()
                .getMonthValue() - 1);

        int currentYear =
            java.time.LocalDate.now().getYear();
        cboYear.getItems().addAll(
            currentYear - 1,
            currentYear,
            currentYear + 1);
        cboYear.setValue(currentYear);

        dpPaymentDate.setValue(
            java.time.LocalDate.now());
    }

    public void setTenant(Tenant t) {
        this.tenant = t;
        lblTenantName.setText(
            "Tenant:  " + t.getFullName() +
            "   |   Monthly Rent: UGX " +
            String.format("%,.0f",
                t.getMonthlyRent()));
    }

    public void setTenantController(
            TenantController tc) {
        this.tenantController = tc;
    }

    @FXML
    private void handleSave() {
        if (txtAmount.getText().trim().isEmpty()) {
            showAlert(
                "Please enter the amount paid.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(
                txtAmount.getText().trim());
        } catch (NumberFormatException e) {
            showAlert(
                "Please enter a valid amount.");
            return;
        }

        if (dpPaymentDate.getValue() == null) {
            showAlert(
                "Please select a payment date.");
            return;
        }

        Payment p = new Payment(
            tenant.getId(),
            amount,
            dpPaymentDate.getValue().toString(),
            cboMethod.getValue(),
            cboMonth.getSelectionModel()
                .getSelectedIndex() + 1,
            cboYear.getValue(),
            txtNotes.getText().trim()
        );

        String receiptNumber = dao.recordPayment(p);
        p.setReceiptNumber(receiptNumber);

        if (tenantController != null) {
            tenantController.loadTenants();
        }

        // Open receipt screen
try {
    System.out.println(
        "Opening receipt: " + receiptNumber);

    java.net.URL receiptUrl = getClass()
        .getResource("/fxml/receipt.fxml");

    if (receiptUrl == null) {
        System.out.println(
            "ERROR: receipt.fxml not found!");
        javafx.scene.control.Alert alert =
            new javafx.scene.control.Alert(
                javafx.scene.control
                    .Alert.AlertType.ERROR);
        alert.setTitle("Receipt Error");
        alert.setHeaderText(null);
        alert.setContentText(
            "receipt.fxml file not found. " +
            "Check resources/fxml folder.");
        alert.showAndWait();
        return;
    }

    System.out.println(
        "receipt.fxml found at: " +
        receiptUrl.toString());

    FXMLLoader loader = new FXMLLoader(receiptUrl);
    Parent root = loader.load();
    ReceiptController ctrl =
        loader.getController();
    ctrl.setReceiptData(
        tenant, p, receiptNumber);
    Stage stage = new Stage();
    stage.setTitle(
        "Receipt — " + receiptNumber);
    stage.setScene(
        new Scene(root, 580, 720));
    stage.initModality(
        Modality.APPLICATION_MODAL);
    stage.showAndWait();

} catch (Exception e) {
    System.out.println(
        "Receipt error: " + e.getMessage());
    e.printStackTrace();
    javafx.scene.control.Alert alert =
        new javafx.scene.control.Alert(
            javafx.scene.control
                .Alert.AlertType.ERROR);
    alert.setTitle("Receipt Error");
    alert.setHeaderText(null);
    alert.setContentText(
        "Error opening receipt: " +
        e.getMessage());
    alert.showAndWait();
}
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtAmount
            .getScene().getWindow();
        stage.close();
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