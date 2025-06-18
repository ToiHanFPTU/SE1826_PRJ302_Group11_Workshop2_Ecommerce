/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import DBUtils.utils;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Cart;


/**
 *
 * @author Log
 */
public class CartDAO extends utils{
    public void addToCart(Cart cart){
        String sql = "INSERT INTO tblCarts(userID, createdDate) VALUES(?, ?);"
                   + "ISERT INTO tblCartDetails(productID, quantity) VALUES(?, ?);";
        getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setObject(1, cart.getUserID());
            ps.setObject(2, cart.getCreateDate());
            ps.setObject(3,cart.getProductID());
            ps.setObject(4, cart.getQuantity());
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        closeConnection();
    }
    public List<Cart> getCartByUserID(String userID){
        List<Cart> cart= new ArrayList<>();
        String sql = "SELECT c.cartID,"
                   + "       c.userID,"
                   + "       c.createdDate,"
                   + "       cd.productID,"
                   + "       cd.quantity"
                   + "FROM tblCarts c"
                   + "JOIN tblCartDetails cd"
                   + "ON c.cartID = cd.cartID"
                   + "WHERE userID LIKE ?";
        getConnection();
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, "%" + userID + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               int cartID = rs.getInt("cartID");
               String UserID = rs.getString("userID");
               Date createdDate = rs.getDate("createdDate");
               int productID = rs.getInt("productID");
               int quantity = rs.getInt("quantity");
               
               cart.add(new Cart(cartID, UserID, createdDate, productID, quantity));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        closeConnection();
        return cart;
    }
}