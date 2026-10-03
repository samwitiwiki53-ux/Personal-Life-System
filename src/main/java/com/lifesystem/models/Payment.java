package com.lifesystem.models;

import org.bson.types.ObjectId;

public class Payment {

    private ObjectId id;
    private ObjectId tenantId;
    private double amountPaid;
    private String paymentDate;
    private String paymentMethod;
    private int periodMonth;
    private int periodYear;
    private String notes;
    private String receiptNumber;

    public Payment() {}

    public Payment(ObjectId tenantId, double amountPaid,
                   String paymentDate, String paymentMethod,
                   int periodMonth, int periodYear,
                   String notes) {
        this.tenantId      = tenantId;
        this.amountPaid    = amountPaid;
        this.paymentDate   = paymentDate;
        this.paymentMethod = paymentMethod;
        this.periodMonth   = periodMonth;
        this.periodYear    = periodYear;
        this.notes         = notes;
    }

    // Getters and Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public ObjectId getTenantId() { return tenantId; }
    public void setTenantId(ObjectId tenantId) {
        this.tenantId = tenantId; }

    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod; }

    public int getPeriodMonth() { return periodMonth; }
    public void setPeriodMonth(int periodMonth) {
        this.periodMonth = periodMonth; }

    public int getPeriodYear() { return periodYear; }
    public void setPeriodYear(int periodYear) {
        this.periodYear = periodYear; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) {
        this.notes = notes; }

    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber; }
}