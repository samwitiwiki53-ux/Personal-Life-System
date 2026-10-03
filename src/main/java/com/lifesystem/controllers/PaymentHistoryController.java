package com.lifesystem.controllers;

import com.lifesystem.database.TenantDAO;
import com.lifesystem.models.Tenant;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import java.util.List;

public class PaymentHistoryController {

    @FXML private Label lblTenantName;
    @FXML private Label lblTotalPaid;
    @FXML private Label lblPaymentCount;
    @FXML private TableView<Document> tblHistory;
    @FXML private TableColumn<Document,String>
        colReceipt;
    @FXML private TableColumn<Document,String>
        colDate;
    @FXML private TableColumn<Document,String>
        colPeriod;
    @FXML private TableColumn<Document,String>
        colAmount;
    @FXML private TableColumn<Document,String>
        colMethod;
    @FXML private TableColumn<Document,String>
        colNotes;

    private TenantDAO dao = new TenantDAO();
    private Tenant tenant;

    private static final String[] MONTHS = {
        "", "January", "February", "March",
        "April", "May", "June", "July",
        "August", "September", "October",
        "November", "December"
    };

    public void setTenant(Tenant t) {
        this.tenant = t;
        lblTenantName.setText(
            "Payment History — " + t.getFullName());
        setupColumns();
        loadHistory();
    }

    private void setupColumns() {
        colReceipt.setCellValueFactory(c ->
            new javafx.beans.property
                .SimpleStringProperty(
                c.getValue().getString(
                    "receiptNumber")));
        colDate.setCellValueFactory(c ->
            new javafx.beans.property
                .SimpleStringProperty(
                c.getValue().getString(
                    "paymentDate")));
        colPeriod.setCellValueFactory(c -> {
            int month = c.getValue()
                .getInteger("periodMonth");
            int year = c.getValue()
                .getInteger("periodYear");
            return new javafx.beans.property
                .SimpleStringProperty(
                MONTHS[month] + " " + year);
        });
        colAmount.setCellValueFactory(c ->
            new javafx.beans.property
                .SimpleStringProperty(
                String.format("UGX %,.0f",
                    c.getValue()
                        .getDouble("amountPaid"))));
        colMethod.setCellValueFactory(c ->
            new javafx.beans.property
                .SimpleStringProperty(
                c.getValue().getString(
                    "paymentMethod")));
        colNotes.setCellValueFactory(c ->
            new javafx.beans.property
                .SimpleStringProperty(
                c.getValue().getString("notes")));
    }

    private void loadHistory() {
        List<Document> payments =
            dao.getTenantPayments(tenant.getId());
        ObservableList<Document> data =
            FXCollections.observableArrayList(
                payments);
        tblHistory.setItems(data);

        double total = payments.stream()
            .mapToDouble(d ->
                d.getDouble("amountPaid"))
            .sum();
        lblTotalPaid.setText(
            "Total Paid: UGX " +
            String.format("%,.0f", total));
        lblPaymentCount.setText(
            "Payments: " + payments.size());
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) tblHistory
            .getScene().getWindow();
        stage.close();
    }
}