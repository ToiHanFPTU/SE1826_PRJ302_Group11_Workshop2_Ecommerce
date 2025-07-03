package model;

import java.sql.Date;

public class Promotion {
    private int promoID;
    private String name;
    private float discountPercent;
    private Date startDate;
    private Date endDate;
    private String status;
    
    // Constructors
    public Promotion() {}
    
    public Promotion(String name, float discountPercent, Date startDate, Date endDate, String status) {
        this.name = name;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
    
    public Promotion(int promoID, String name, float discountPercent, Date startDate, Date endDate, String status) {
        this.promoID = promoID;
        this.name = name;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters and Setters
    public int getPromoID() { return promoID; }
    public void setPromoID(int promoID) { this.promoID = promoID; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public float getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(float discountPercent) { this.discountPercent = discountPercent; }
    
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
