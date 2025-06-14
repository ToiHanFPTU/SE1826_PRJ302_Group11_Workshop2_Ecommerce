/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import DBUtils.utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author HP
 */
public class UserDAO extends utils{
    public User login(String userID, String password)
            throws ClassNotFoundException, SQLException {
        System.out.println("UserDAO.login(): userID=" + userID + ", password=" + password);

        String sql = "SELECT fullName, roleID FROM tblUsers WHERE userID = ? AND password = ?";
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userID);
            preparedStatement.setString(2, password);
            try ( ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    String phone = rs.getString("phone");
                    System.out.println("Login successful for " + userID + ", fullName=" + fullName + ", roleID=" + roleID);
                    return new User(userID, fullName, roleID, password, phone);
                } else {
                    System.out.println("Login failed: no matching record");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
        return null;
    }
}
