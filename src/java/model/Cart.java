/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author HP
 */
public class Cart{
    private int cartID ;
    private String userID;
    private Date createDate;
    private int productID;
    private int quantity;
    
    public Cart() {
    }

    public Cart(String userID, Date createDate, int productID, int quantity) {
        this.userID = userID;
        this.createDate = createDate;
        this.productID = productID;
        this.quantity = quantity;
    }

    public Cart(int cartID, String userID, Date createDate, int productID, int quantity) {
        this.cartID = cartID;
        this.userID = userID;
        this.createDate = createDate;
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
    return "Cart{" + "cartID=" + cartID + ", userID=" + userID + ", createDate=" + createDate
           + ", productID=" + productID + ", quantity=" + quantity + '}';
    }
}