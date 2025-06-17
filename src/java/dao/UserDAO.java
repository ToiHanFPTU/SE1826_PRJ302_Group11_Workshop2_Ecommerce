package dao;

import DBUtils.utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO extends utils {

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
        } catch (SQLException e) {
            System.out.println("Error");
        }
        closeConnection();
        return null;
    }

    public List<User> listAllUser() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT [userID]\n"
                + "      ,[fullName]\n"
                + "      ,[roleID]\n"
                + "      ,[password]\n"
                + "      ,[phone]\n"
                + "  FROM [dbo].[tblUsers]";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            String userID = resultSet.getString("userID");
            String fullName = resultSet.getString("fullName");
            String roleID = resultSet.getString("roleID");
            String password = resultSet.getString("password");
            String phone = resultSet.getString("phone");
            users.add(new User(userID, fullName, roleID, password, phone));
        } catch (SQLException e) {
            System.out.println("Error");
        }
        closeConnection();
        return users;
    }

    public boolean insertUser(User user) {
        String sql = "INSERT INTO [dbo].[tblUsers]\n"
                + "           ([userID]\n"
                + "           ,[fullName]\n"
                + "           ,[roleID]\n"
                + "           ,[password]\n"
                + "           ,[phone])\n"
                + "     VALUES (?,?,?,?,?)";
        boolean isInserted = false;
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, user.getUserID());
            preparedStatement.setObject(2, user.getFullName());
            preparedStatement.setObject(3, user.getRoleID());
            preparedStatement.setObject(4, user.getPassword());
            preparedStatement.setObject(5, user.getPhone());
            isInserted = preparedStatement.executeLargeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error");
        }
        closeConnection();
        return isInserted;
    }

    public boolean deleteUser(String userID) {
        String sqlStatement = "DELETE FROM [dbo].[tblUsers]\n"
                + "      WHERE [userID] = ?";
        boolean isdeleted = false;
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setObject(1, userID);
            isdeleted = preparedStatement.executeUpdate() > 0;
            System.out.println("Delete user sucessfully");
        } catch (SQLException e) {
            System.out.println("Error");
        }
        closeConnection();
        return isdeleted;
    }

    public boolean isUpdated(User user) {
        boolean isUpdated = false;
        String sql = "UPDATE [dbo].[tblUsers]\n"
                + "   SET [fullName] = ?\n"
                + "      ,[roleID] = ?\n"
                + "      ,[password] = ?\n"
                + "      ,[phone] = ?\n"
                + " WHERE [userID] = ?";

        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, user.getFullName());
            preparedStatement.setObject(2, user.getRoleID());
            preparedStatement.setObject(3, user.getPassword());
            preparedStatement.setObject(4, user.getPhone());
            preparedStatement.setObject(5, user.getUserID());
            isUpdated = preparedStatement.executeLargeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error");
        }
        closeConnection();
        return isUpdated;
    }

    public List<User> searchUser(String keyword) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT [userID]\n"
                + "      ,[fullName]\n"
                + "      ,[roleID]\n"
                + "      ,[password]\n"
                + "      ,[phone]\n"
                + "  FROM [dbo].[tblUsers]\n"
                + "  WHERE [userID]  = ? OR [roleID] = ? OR [fullName] LIKE ?";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, "%" + keyword + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userID = resultSet.getString("userID");
                String fullName = resultSet.getString("fullName");
                String roleID = resultSet.getString("roleID");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                users.add(new User(userID, fullName, roleID, password, phone));
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }
        closeConnection();
        return users;
    }

    public boolean isDuplicatePassword(String newPassword) {
        List<User> users = listAllUser();
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(newPassword)) {
                return true;
            }
        }
        return false;
    }

    public boolean updatePasswordByID(String newPassword, String userID) {
        String sql = "UPDATE [dbo].[tblUsers]\n"
                + "   SET [password] = ?\n"
                + " WHERE [userID] = ?";
        boolean isUpdated = false;
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, newPassword);
            preparedStatement.setObject(2, userID);
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        }
        closeConnection();
        return isUpdated;
    }

    public User getUserByIDOrPhone(String keyword) {
        User user = null;
        String sql = "SELECT [userID], [fullName], [roleID], [password], [phone] "
                + "FROM [dbo].[tblUsers] WHERE [userID] = ? OR [phone] = ?";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, keyword);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString("userID"),
                        resultSet.getString("fullName"),
                        resultSet.getString("roleID"),
                        resultSet.getString("password"),
                        resultSet.getString("phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return user;
    }
    public boolean isDuplicateUserID(String userID){
        List<User> users = listAllUser();
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID)) {
                return true;
            }
        }
        return false;
    }
}
