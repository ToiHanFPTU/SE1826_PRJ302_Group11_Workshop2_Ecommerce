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
import model.Invoice;

/**
 *
 * @author Log
 */
public class InvoiceDAO extends utils{
    public void createInvoice(Invoice invoice){
        String sql = "INSERT INTO tblInvoices(userID, totalAmount, status, createDate) VALUES(?, ?, ?, ?);"
                   + "INSERT INTO tblInvoiceDetails(productID, quantity, price) VALUES(?, ?, ?)";
        getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setObject(1, invoice.getUserID());
            ps.setObject(2, invoice.getTotalAmount());
            ps.setObject(3, invoice.getStatus());
            ps.setObject(4, invoice.getCreateDate());
            ps.setObject(5, invoice.getProductID());
            ps.setObject(6, invoice.getQuantity());
            ps.setObject(7, invoice.getPrice());
            ps.executeLargeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public List<Invoice> listInvoice(){
        List<Invoice> Ilist = new ArrayList<>();
        String sql = "SELECT i.[invoiceID]\n" +
                     "      ,i.[userID]\n" +
                     "      ,i.[totalAmount]\n" +
                     "      ,i.[status]\n" +
                     "      ,i.[createdDate]\n"
                   + "      ,id.[productID]\n"
                   + "      ,id.[quantity]\n"
                   + "      ,id.[price]\n" 
                   + "FROM [ECommerceDB].[dbo].[tblInvoices] i\n"
                   + "JOIN [ECommerceDB].[dbo].[tblInvoiceDetails] id\n"
                   + "ON id.[invoiceID] = i.[invoiceID]\n";
        getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs =ps.executeQuery()){
            while(rs.next()){
            int invoiceID = rs.getInt("invoiceID");
            String userID = rs.getString("userID");
            float totalAmount = rs.getFloat("totalAmount");
            String status = rs.getString("status");
            Date createdDate = rs.getDate("createdDate");
            int productID = rs.getInt("productID");
            int quantity = rs.getInt("quantity");
            float price = rs.getFloat("price");
            Ilist.add(new Invoice(invoiceID, userID, totalAmount, status, createdDate, productID, quantity, price));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return Ilist;
    }
    public void deleteInvoice(int invoiceID){
        String sql = "DELETE FROM [dbo].[tblInvoices]\n"
                   + "WHERE [invoiceID] = ?";
        getConnection();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, invoiceID);
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}