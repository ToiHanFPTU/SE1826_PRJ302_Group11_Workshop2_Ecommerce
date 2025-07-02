/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.CustomerCare;
import DBUtils.utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Log
 */
public class CustomerCareDAO extends utils{
    public boolean insertFeedback(CustomerCare feedback) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tblCustomerCares (userID, subject, content, status, reply) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, feedback.getUserID());
            ps.setString(2, feedback.getSubject());
            ps.setString(3, feedback.getContent());
            ps.setString(4, feedback.getStatus());
            ps.setString(5, feedback.getReply());
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean replyToFeedback(int ticketID, String reply, String newStatus) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tblCustomerCares SET reply = ?, status = ? WHERE ticketID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reply);
            ps.setString(2, newStatus);
            ps.setInt(3, ticketID);
            return ps.executeUpdate() > 0;
        }
    }

    
    public CustomerCare getFeedbackById(int ticketID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tblCustomerCares WHERE ticketID = ?";
        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, ticketID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CustomerCare dto = new CustomerCare();
                    dto.setTicketID(rs.getInt("ticketID"));
                    dto.setUserID(rs.getString("userID"));
                    dto.setSubject(rs.getString("subject"));
                    dto.setContent(rs.getString("content"));
                    dto.setReply(rs.getString("reply"));
                    dto.setStatus(rs.getString("status"));
                    return dto;
                }
            }
        }
        return null;
    }

    
    
    public List<CustomerCare> getAllFeedback() throws SQLException, ClassNotFoundException {
        List<CustomerCare> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCustomerCares ORDER BY ticketID DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CustomerCare dto = new CustomerCare();
                dto.setTicketID(rs.getInt("ticketID"));
                dto.setUserID(rs.getString("userID"));
                dto.setSubject(rs.getString("subject"));
                dto.setContent(rs.getString("content"));
                dto.setReply(rs.getString("reply"));
                dto.setStatus(rs.getString("status"));
                list.add(dto);
            }
        }
        return list;
    }
}
