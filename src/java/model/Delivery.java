package model;

import java.sql.Date;

public class Delivery {
    private int deliveryID;
    private int invoiceID;
    private String address;
    private Date deliveryDate;
    private String status;
    
    // Thông tin bổ sung từ join với các bảng khác
    private String userID;
    private String customerName;
    private String customerPhone;
    private float totalAmount;
    private String invoiceStatus;
    private Date invoiceCreatedDate;

    public Delivery() {
    }

    public Delivery(int deliveryID, int invoiceID, String address, Date deliveryDate, String status) {
        this.deliveryID = deliveryID;
        this.invoiceID = invoiceID;
        this.address = address;
        this.deliveryDate = deliveryDate;
        this.status = status;
    }

    // Getters and Setters
    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public Date getInvoiceCreatedDate() {
        return invoiceCreatedDate;
    }

    public void setInvoiceCreatedDate(Date invoiceCreatedDate) {
        this.invoiceCreatedDate = invoiceCreatedDate;
    }
}
