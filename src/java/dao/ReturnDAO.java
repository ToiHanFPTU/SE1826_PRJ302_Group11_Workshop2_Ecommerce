/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import DBUtils.utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Return;

/**
 *
 * @author Log
 */
public class ReturnDAO extends utils{
    public Boolean createReturnRequest(int invoiceID, String reason) {
        String sql = "INSERT INTO tblReturns (invoiceID, reason, status) VALUES (?, ?, 'Pending')";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, invoiceID);
            ps.setString(2, reason);
            //ps.executeUpdate() → Trả về số dòng bị ảnh hưởng (rows affected) bởi câu SQL.
            //Nếu > 0 → có nghĩa là INSERT hoặc UPDATE thành công, thì trả về true. 
            //nó check giống if nếu đúng thì return true ko thì false 
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    preparestatement laf gi
    resultset la gi
    tai sao dung rs.next
     */
    //2. Admin xem danh sách các yêu cầu theo status (VD: "Pending")
    
    
    public List<Return> getAllReturnByStatus(String status) {
        List<Return> list = new ArrayList<>();
        String sql = "SELECT * FROM tblReturns WHERE status = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Return dto = new Return(
                        rs.getInt("returnID"),
                        rs.getInt("invoiceID"),
                        rs.getString("reason"),
                        rs.getString("status")
                );
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Admin cập nhật trạng thái yêu cầu (Approved / Rejected)
    public boolean updateReturnStatus(int returnID, String newStatus) {
        String sql = "UPDATE tblReturns SET status = ? WHERE returnID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, returnID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //lay ra danh sach yeu cầu đc AD duyệt
    public List<Return> getReturnsByUser(String userID) {
        List<Return> list = new ArrayList<>();
        String sql = "SELECT R.returnID, R.invoiceID, R.reason, R.status "
                + "FROM tblReturns R JOIN tblInvoices I ON R.invoiceID = I.invoiceID "
                + "WHERE I.customerID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Return(
                        rs.getInt("returnID"),
                        rs.getInt("invoiceID"),
                        rs.getString("reason"),
                        rs.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
