package dao;

import DBUtils.utils;
import model.Delivery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Invoice;

public class DeliveryDAO extends utils {
    
    // Lấy tất cả đơn giao hàng
    public List<Delivery> getAllDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT d.deliveryID, d.invoiceID, d.address, d.deliveryDate, d.status, " +
                    "i.userID, i.totalAmount, i.status as invoiceStatus, i.createdDate, " +
                    "u.fullName, u.phone " +
                    "FROM tblDeliveries d " +
                    "JOIN tblInvoices i ON d.invoiceID = i.invoiceID " +
                    "JOIN tblUsers u ON i.userID = u.userID " +
                    "ORDER BY d.deliveryDate DESC, d.deliveryID DESC";
        
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Delivery delivery = new Delivery();
                delivery.setDeliveryID(resultSet.getInt("deliveryID"));
                delivery.setInvoiceID(resultSet.getInt("invoiceID"));
                delivery.setAddress(resultSet.getString("address"));
                delivery.setDeliveryDate(resultSet.getDate("deliveryDate"));
                delivery.setStatus(resultSet.getString("status"));
                delivery.setUserID(resultSet.getString("userID"));
                delivery.setTotalAmount(resultSet.getFloat("totalAmount"));
                delivery.setInvoiceStatus(resultSet.getString("invoiceStatus"));
                delivery.setInvoiceCreatedDate(resultSet.getDate("createdDate"));
                delivery.setCustomerName(resultSet.getString("fullName"));
                delivery.setCustomerPhone(resultSet.getString("phone"));
                
                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return deliveries;
    }
    
    // Tìm kiếm theo invoiceID
    public List<Delivery> searchByInvoiceID(int invoiceID) {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT d.deliveryID, d.invoiceID, d.address, d.deliveryDate, d.status, " +
                    "i.userID, i.totalAmount, i.status as invoiceStatus, i.createdDate, " +
                    "u.fullName, u.phone " +
                    "FROM tblDeliveries d " +
                    "JOIN tblInvoices i ON d.invoiceID = i.invoiceID " +
                    "JOIN tblUsers u ON i.userID = u.userID " +
                    "WHERE d.invoiceID = ? " +
                    "ORDER BY d.deliveryDate DESC";
        
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, invoiceID);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Delivery delivery = new Delivery();
                delivery.setDeliveryID(resultSet.getInt("deliveryID"));
                delivery.setInvoiceID(resultSet.getInt("invoiceID"));
                delivery.setAddress(resultSet.getString("address"));
                delivery.setDeliveryDate(resultSet.getDate("deliveryDate"));
                delivery.setStatus(resultSet.getString("status"));
                delivery.setUserID(resultSet.getString("userID"));
                delivery.setTotalAmount(resultSet.getFloat("totalAmount"));
                delivery.setInvoiceStatus(resultSet.getString("invoiceStatus"));
                delivery.setInvoiceCreatedDate(resultSet.getDate("createdDate"));
                delivery.setCustomerName(resultSet.getString("fullName"));
                delivery.setCustomerPhone(resultSet.getString("phone"));
                
                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return deliveries;
    }
    
    // Tìm kiếm theo trạng thái
    public List<Delivery> searchByStatus(String status) {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT d.deliveryID, d.invoiceID, d.address, d.deliveryDate, d.status, " +
                    "i.userID, i.totalAmount, i.status as invoiceStatus, i.createdDate, " +
                    "u.fullName, u.phone " +
                    "FROM tblDeliveries d " +
                    "JOIN tblInvoices i ON d.invoiceID = i.invoiceID " +
                    "JOIN tblUsers u ON i.userID = u.userID " +
                    "WHERE d.status = ? " +
                    "ORDER BY d.deliveryDate DESC, d.deliveryID DESC";
        
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Delivery delivery = new Delivery();
                delivery.setDeliveryID(resultSet.getInt("deliveryID"));
                delivery.setInvoiceID(resultSet.getInt("invoiceID"));
                delivery.setAddress(resultSet.getString("address"));
                delivery.setDeliveryDate(resultSet.getDate("deliveryDate"));
                delivery.setStatus(resultSet.getString("status"));
                delivery.setUserID(resultSet.getString("userID"));
                delivery.setTotalAmount(resultSet.getFloat("totalAmount"));
                delivery.setInvoiceStatus(resultSet.getString("invoiceStatus"));
                delivery.setInvoiceCreatedDate(resultSet.getDate("createdDate"));
                delivery.setCustomerName(resultSet.getString("fullName"));
                delivery.setCustomerPhone(resultSet.getString("phone"));
                
                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return deliveries;
    }
    
    // Lấy delivery theo ID
    public Delivery getDeliveryById(int deliveryID) {
        String sql = "SELECT d.deliveryID, d.invoiceID, d.address, d.deliveryDate, d.status, " +
                    "i.userID, i.totalAmount, i.status as invoiceStatus, i.createdDate, " +
                    "u.fullName, u.phone " +
                    "FROM tblDeliveries d " +
                    "JOIN tblInvoices i ON d.invoiceID = i.invoiceID " +
                    "JOIN tblUsers u ON i.userID = u.userID " +
                    "WHERE d.deliveryID = ?";
        
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, deliveryID);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                Delivery delivery = new Delivery();
                delivery.setDeliveryID(resultSet.getInt("deliveryID"));
                delivery.setInvoiceID(resultSet.getInt("invoiceID"));
                delivery.setAddress(resultSet.getString("address"));
                delivery.setDeliveryDate(resultSet.getDate("deliveryDate"));
                delivery.setStatus(resultSet.getString("status"));
                delivery.setUserID(resultSet.getString("userID"));
                delivery.setTotalAmount(resultSet.getFloat("totalAmount"));
                delivery.setInvoiceStatus(resultSet.getString("invoiceStatus"));
                delivery.setInvoiceCreatedDate(resultSet.getDate("createdDate"));
                delivery.setCustomerName(resultSet.getString("fullName"));
                delivery.setCustomerPhone(resultSet.getString("phone"));
                
                return delivery;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }
    
    // Cập nhật trạng thái giao hàng
    public boolean updateDeliveryStatus(int deliveryID, String status) {
        String sql = "UPDATE tblDeliveries SET status = ? WHERE deliveryID = ?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, deliveryID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }
    
    // Cập nhật địa chỉ giao hàng
    public boolean updateDeliveryAddress(int deliveryID, String address) {
        String sql = "UPDATE tblDeliveries SET address = ? WHERE deliveryID = ?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, address);
            preparedStatement.setInt(2, deliveryID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }
    
    // Tạo đơn giao hàng mới
    public boolean createDelivery(int invoiceID, String address, java.sql.Date deliveryDate, String status) {
        String sql = "INSERT INTO tblDeliveries (invoiceID, address, deliveryDate, status) VALUES (?, ?, ?, ?)";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, invoiceID);
            preparedStatement.setString(2, address);
            preparedStatement.setDate(3, deliveryDate);
            preparedStatement.setString(4, status);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }
    
    // Lấy danh sách hóa đơn chưa có đơn giao hàng
    public List<Invoice> getInvoicesWithoutDelivery() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT i.invoiceID, i.userID, i.totalAmount, i.status, i.createdDate, u.fullName " +
                    "FROM tblInvoices i " +
                    "JOIN tblUsers u ON i.userID = u.userID " +
                    "WHERE i.invoiceID NOT IN (SELECT invoiceID FROM tblDeliveries) " +
                    "AND i.status = 'pending' " +
                    "ORDER BY i.createdDate DESC";  
        
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceID(resultSet.getInt("invoiceID"));
                invoice.setUserID(resultSet.getString("userID"));
                invoice.setTotalAmount(resultSet.getFloat("totalAmount"));
                invoice.setStatus(resultSet.getString("status"));
                invoice.setCreateDate(resultSet.getDate("createdDate"));
                // Có thể thêm tên khách hàng nếu cần
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return invoices ;
    }
}
