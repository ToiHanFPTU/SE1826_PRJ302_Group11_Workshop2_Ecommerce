package model;

import java.sql.Date;

public class ProductPromotion {
    private int promoID;
    private int productID;
    private Date createdDate;
    private String productName;
    private String promoName;
    private float discountPercent;
    
    // Constructors
    public ProductPromotion() {}
    
    public ProductPromotion(int promoID, int productID) {
        this.promoID = promoID;
        this.productID = productID;
    }
    
    public ProductPromotion(int promoID, int productID, Date createdDate) {
        this.promoID = promoID;
        this.productID = productID;
        this.createdDate = createdDate;
    }

    // Getters and Setters
    public int getPromoID() { return promoID; }
    public void setPromoID(int promoID) { this.promoID = promoID; }
    
    public int getProductID() { return productID; }
    public void setProductID(int productID) { this.productID = productID; }
    
    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public String getPromoName() { return promoName; }
    public void setPromoName(String promoName) { this.promoName = promoName; }
    
    public float getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(float discountPercent) { this.discountPercent = discountPercent; }
}
