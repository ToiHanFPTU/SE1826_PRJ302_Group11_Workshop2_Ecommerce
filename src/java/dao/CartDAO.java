/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import DBUtils.utils;
import java.sql.PreparedStatement;
import model.Cart;


/**
 *
 * @author Log
 */
public class CartDAO extends utils{
    public void addToCart(Cart cart){
        String sql = "INSERT INTO tblCarts(userID, createDate) VALUES(?, ?);"
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
}