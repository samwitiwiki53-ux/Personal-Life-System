package com.lifesystem.controllers;

import com.lifesystem.models.Payment;
import com.lifesystem.models.Tenant;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReceiptController {

    @FXML private Label lblReceiptNumber;
    @FXML private Label lblIssuedDate;
    @FXML private Label lblTenantName;
    @FXML private Label lblUnitNumber;
    @FXML private Label lblPhone;
    @FXML private Label lblPropertyType;
    @FXML private Label lblPeriod;
    @FXML private Label lblPaymentDate;
    @FXML private Label lblPaymentMethod;
    @FXML private Label lblMonthlyRent;
    @FXML private Label lblAmountPaid;
    @FXML private Label lblBalance;

    private static final String[] MONTHS = {
        "", "January", "February", "March",
        "April", "May", "June", "July",
        "August", "September", "October",
        "November", "December"
    };

    public void setReceiptData(Tenant t,
            Payment p, String receiptNumber) {
        lblReceiptNumber.setText(receiptNumber);
        lblIssuedDate.setText(
            java.time.LocalDate.now().toString());
        lblTenantName.setText(t.getFullName());
        lblUnitNumber.setText(t.getUnitNumber());
        lblPhone.setText(t.getPhoneNumber());
        lblPropertyType.setText(
            t.getPropertyType());
        lblPeriod.setText(
            MONTHS[p.getPeriodMonth()] +
            " " + p.getPeriodYear());
        lblPaymentDate.setText(
            p.getPaymentDate());
        lblPaymentMethod.setText(
            p.getPaymentMethod());
        lblMonthlyRent.setText(
            String.format("UGX %,.0f",
                t.getMonthlyRent()));
        lblAmountPaid.setText(
            String.format("UGX %,.0f",
                p.getAmountPaid()));
        double balance =
            t.getMonthlyRent() - p.getAmountPaid();
        if (balance > 0) {
            lblBalance.setText(
                "Balance Due: UGX " +
                String.format("%,.0f", balance));
            lblBalance.setStyle(
                "-fx-text-fill: #C0392B;" +
                "-fx-font-weight: bold;");
        } else {
            lblBalance.setText("✔  Fully Paid");
            lblBalance.setStyle(
                "-fx-text-fill: #27AE60;" +
                "-fx-font-weight: bold;");
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) lblReceiptNumber
            .getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handlePrint() {
        // Print functionality
        javafx.print.PrinterJob job =
            javafx.print.PrinterJob
                .createPrinterJob();
        if (job != null) {
            boolean proceed =
                job.showPrintDialog(
                    lblReceiptNumber
                        .getScene().getWindow());
            if (proceed) {
                job.endJob();
            }
        }
    }
}