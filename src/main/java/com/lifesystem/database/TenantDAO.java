package com.lifesystem.database;

import com.lifesystem.models.Tenant;
import com.lifesystem.models.Payment;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class TenantDAO {

    private static MongoCollection<Document> tenantsCol;
    private static MongoCollection<Document> paymentsCol;
    private static MongoCollection<Document> receiptsCol;

    public TenantDAO() {
        MongoDatabase db = MongoDBHelper.getDatabase();
        tenantsCol  = db.getCollection("tenants");
        paymentsCol = db.getCollection("payments");
        receiptsCol = db.getCollection("receipts");
    }

    // ── Save a new tenant ────────────────────────────
    public void saveTenant(Tenant t) {
        Document doc = new Document()
            .append("firstName",     t.getFirstName())
            .append("lastName",      t.getLastName())
            .append("phoneNumber",   t.getPhoneNumber())
            .append("email",         t.getEmail())
            .append("unitNumber",    t.getUnitNumber())
            .append("propertyType",  t.getPropertyType())
            .append("monthlyRent",   t.getMonthlyRent())
            .append("leaseStartDate",t.getLeaseStartDate())
            .append("leaseEndDate",  t.getLeaseEndDate())
            .append("nationalID",    t.getNationalID())
            .append("status",        t.getStatus());
        tenantsCol.insertOne(doc);
    }

    // ── Update an existing tenant ────────────────────
    public void updateTenant(ObjectId id, Tenant t) {
        Document update = new Document("$set",
            new Document()
                .append("firstName",     t.getFirstName())
                .append("lastName",      t.getLastName())
                .append("phoneNumber",   t.getPhoneNumber())
                .append("email",         t.getEmail())
                .append("unitNumber",    t.getUnitNumber())
                .append("propertyType",  t.getPropertyType())
                .append("monthlyRent",   t.getMonthlyRent())
                .append("leaseStartDate",t.getLeaseStartDate())
                .append("leaseEndDate",  t.getLeaseEndDate())
                .append("nationalID",    t.getNationalID())
                .append("status",        t.getStatus()));
        tenantsCol.updateOne(
            Filters.eq("_id", id), update);
    }

    // ── Get all tenants ──────────────────────────────
    public List<Tenant> getAllTenants() {
        List<Tenant> list = new ArrayList<>();
        for (Document doc : tenantsCol.find()) {
            list.add(documentToTenant(doc));
        }
        return list;
    }

    // ── Get active tenants only ──────────────────────
    public List<Tenant> getActiveTenants() {
        List<Tenant> list = new ArrayList<>();
        for (Document doc : tenantsCol.find(
                Filters.eq("status", "Active"))) {
            list.add(documentToTenant(doc));
        }
        return list;
    }

    // ── Deactivate a tenant ──────────────────────────
    public void deactivateTenant(ObjectId id) {
        tenantsCol.updateOne(
            Filters.eq("_id", id),
            new Document("$set",
                new Document("status", "Inactive")));
    }

    // ── Record a payment ─────────────────────────────
    public String recordPayment(Payment p) {
        // Generate receipt number
        long count = receiptsCol.countDocuments() + 1;
        String receiptNum = "RCP-" +
            p.getPeriodYear() + "-" +
            String.format("%05d", count);

        // Save payment
        Document payDoc = new Document()
            .append("tenantId",     p.getTenantId())
            .append("amountPaid",   p.getAmountPaid())
            .append("paymentDate",  p.getPaymentDate())
            .append("paymentMethod",p.getPaymentMethod())
            .append("periodMonth",  p.getPeriodMonth())
            .append("periodYear",   p.getPeriodYear())
            .append("notes",        p.getNotes())
            .append("receiptNumber",receiptNum);
        paymentsCol.insertOne(payDoc);

        // Save receipt record
        Document recDoc = new Document()
            .append("receiptNumber", receiptNum)
            .append("tenantId",      p.getTenantId())
            .append("amountPaid",    p.getAmountPaid())
            .append("paymentDate",   p.getPaymentDate())
            .append("paymentMethod", p.getPaymentMethod())
            .append("periodMonth",   p.getPeriodMonth())
            .append("periodYear",    p.getPeriodYear())
            .append("issuedDate",
                java.time.LocalDate.now().toString());
        receiptsCol.insertOne(recDoc);

        return receiptNum;
    }

    // ── Get payments for a tenant ────────────────────
    public List<Document> getTenantPayments(
            ObjectId tenantId) {
        List<Document> list = new ArrayList<>();
        for (Document doc : paymentsCol.find(
                Filters.eq("tenantId", tenantId))) {
            list.add(doc);
        }
        return list;
    }

    // ── Get total collected ──────────────────────────
    public double getTotalCollected() {
        double total = 0;
        for (Document doc : paymentsCol.find()) {
            total += doc.getDouble("amountPaid");
        }
        return total;
    }

    // ── Convert Document to Tenant object ────────────
    private Tenant documentToTenant(Document doc) {
        Tenant t = new Tenant();
        t.setId(doc.getObjectId("_id"));
        t.setFirstName(doc.getString("firstName"));
        t.setLastName(doc.getString("lastName"));
        t.setPhoneNumber(doc.getString("phoneNumber"));
        t.setEmail(doc.getString("email"));
        t.setUnitNumber(doc.getString("unitNumber"));
        t.setPropertyType(doc.getString("propertyType"));
        t.setMonthlyRent(doc.getDouble("monthlyRent"));
        t.setLeaseStartDate(
            doc.getString("leaseStartDate"));
        t.setLeaseEndDate(doc.getString("leaseEndDate"));
        t.setNationalID(doc.getString("nationalID"));
        t.setStatus(doc.getString("status"));
        return t;
    }
}