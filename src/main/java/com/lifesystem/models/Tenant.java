package com.lifesystem.models;

import org.bson.types.ObjectId;

public class Tenant {

    private ObjectId id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String unitNumber;
    private String propertyType;
    private double monthlyRent;
    private String leaseStartDate;
    private String leaseEndDate;
    private String nationalID;
    private String status; // Active or Inactive

    // Constructor for new tenant
    public Tenant(String firstName, String lastName,
                  String phoneNumber, String email,
                  String unitNumber, String propertyType,
                  double monthlyRent, String leaseStartDate,
                  String leaseEndDate, String nationalID) {
        this.firstName     = firstName;
        this.lastName      = lastName;
        this.phoneNumber   = phoneNumber;
        this.email         = email;
        this.unitNumber    = unitNumber;
        this.propertyType  = propertyType;
        this.monthlyRent   = monthlyRent;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate  = leaseEndDate;
        this.nationalID    = nationalID;
        this.status        = "Active";
    }

    // Empty constructor
    public Tenant() {}

    // Getters and Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) {
        this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) {
        this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email; }

    public String getUnitNumber() { return unitNumber; }
    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber; }

    public String getPropertyType() { return propertyType; }
    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType; }

    public double getMonthlyRent() { return monthlyRent; }
    public void setMonthlyRent(double monthlyRent) {
        this.monthlyRent = monthlyRent; }

    public String getLeaseStartDate() { return leaseStartDate; }
    public void setLeaseStartDate(String leaseStartDate) {
        this.leaseStartDate = leaseStartDate; }

    public String getLeaseEndDate() { return leaseEndDate; }
    public void setLeaseEndDate(String leaseEndDate) {
        this.leaseEndDate = leaseEndDate; }

    public String getNationalID() { return nationalID; }
    public void setNationalID(String nationalID) {
        this.nationalID = nationalID; }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status; }

    public String getFullName() {
        return firstName + " " + lastName; }
}