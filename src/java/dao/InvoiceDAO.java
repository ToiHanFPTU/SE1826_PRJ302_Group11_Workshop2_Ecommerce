package dao;

import DBUtils.utils;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Invoice;

public class InvoiceDAO extends utils{
    public int createInvoice(String userID, double totalAmount, Date createDate) {
    String sql = "INSERT INTO tblInvoices(userID, totalAmount, status, createdDate) VALUES (?, ?, 'Pending', ?)";
    int invoiceID = -1;
    getConnection();
    try {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, userID);
        ps.setDouble(2, totalAmount);
        ps.setDate(3, createDate);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            invoiceID = rs.getInt(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    closeConnection();
    return invoiceID;
}

    public void addInvoiceDetail(int invoiceID, int productID, int quantity, double price) {
    String sql = "INSERT INTO tblInvoiceDetails(invoiceID, productID, quantity, price) VALUES (?, ?, ?, ?)";
    getConnection();
    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, invoiceID);
        ps.setInt(2, productID);
        ps.setInt(3, quantity);
        ps.setDouble(4, price);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
    closeConnection();
  }
   public List<Invoice> getInvoicesByUser(String userID) {
    List<Invoice> Ilist = new ArrayList<>();
    String sql = "SELECT i.invoiceID, i.userID, i.totalAmount, i.status, i.createdDate, " +
                 "id.productID, id.quantity, id.price " +
                 "FROM tblInvoices i " +
                 "JOIN tblInvoiceDetails id ON i.invoiceID = id.invoiceID " +
                 "WHERE i.userID = ?";
    getConnection();
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, userID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int invoiceID = rs.getInt("invoiceID");
            float totalAmount = rs.getFloat("totalAmount");
            String status = rs.getString("status");
            Date createdDate = rs.getDate("createdDate");
            int productID = rs.getInt("productID");
            int quantity = rs.getInt("quantity");
            float price = rs.getFloat("price");

            Ilist.add(new Invoice(invoiceID, userID, totalAmount, status, createdDate, productID, quantity, price));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    closeConnection();
    return Ilist;
  }
   public List<Invoice> getInvoicesByUserAndStatus(String userID, String status) {
    List<Invoice> Ilist = new ArrayList<>();
    String sql = "SELECT i.invoiceID, i.userID, i.totalAmount, i.status, i.createdDate, " +
                 "id.productID, id.quantity, id.price " +
                 "FROM tblInvoices i " +
                 "JOIN tblInvoiceDetails id ON i.invoiceID = id.invoiceID " +
                 "WHERE i.userID = ? AND i.status = ?";
    getConnection();
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, userID);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int invoiceID = rs.getInt("invoiceID");
            float totalAmount = rs.getFloat("totalAmount");
            Date createdDate = rs.getDate("createdDate");
            int productID = rs.getInt("productID");
            int quantity = rs.getInt("quantity");
            float price = rs.getFloat("price");

            Ilist.add(new Invoice(invoiceID, userID, totalAmount, status, createdDate, productID, quantity, price));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    closeConnection();
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
    public void updateInvoiceStatus(int invoiceID, String status) {
    String sql = "UPDATE tblInvoices SET status = ? WHERE invoiceID = ?";
    getConnection();
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, status);
        ps.setInt(2, invoiceID);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
  } 
    public List<Invoice> getInvoicesByStatus(String status) {
    List<Invoice> Ilist = new ArrayList<>();
    String sql = "SELECT i.invoiceID, i.userID, i.totalAmount, i.status, i.createdDate, " +
                 "id.productID, id.quantity, id.price " +
                 "FROM tblInvoices i " +
                 "JOIN tblInvoiceDetails id ON i.invoiceID = id.invoiceID " +
                 "WHERE i.status = ?";
    getConnection();
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int invoiceID = rs.getInt("invoiceID");
            String userID = rs.getString("userID");
            float totalAmount = rs.getFloat("totalAmount");
            Date createdDate = rs.getDate("createdDate");
            int productID = rs.getInt("productID");
            int quantity = rs.getInt("quantity");
            float price = rs.getFloat("price");

            Invoice invoice = new Invoice(invoiceID, userID, totalAmount, status, createdDate, productID, quantity, price);
            Ilist.add(invoice);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        closeConnection(); // đảm bảo bạn có closeConnection() trong DAO
    }
    return Ilist;
}

}