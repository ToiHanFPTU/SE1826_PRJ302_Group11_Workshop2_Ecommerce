package dao;

import DBUtils.utils;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Cart;

/**
 *
 * @author Log
 */
public class CartDAO extends utils {
    public void addToCart(Cart cart) {
        String insertCartSQL = "INSERT INTO tblCarts(userID, createdDate) VALUES (?, ?)";
        String insertCartDetailSQL = "INSERT INTO tblCartDetails(cartID, productID, quantity) VALUES (?, ?, ?)";
        getConnection();
        try {
            // 1. Insert into tblCarts và lấy cartID sinh ra
            PreparedStatement psCart = connection.prepareStatement(insertCartSQL, Statement.RETURN_GENERATED_KEYS);
            psCart.setString(1, cart.getUserID());
            psCart.setDate(2, cart.getCreateDate());
            psCart.executeUpdate();

            ResultSet rs = psCart.getGeneratedKeys();
            int cartID = -1;
            if (rs.next()) {
                cartID = rs.getInt(1);
            }

            // 2. Insert into tblCartDetails với cartID vừa lấy được
            PreparedStatement psDetail = connection.prepareStatement(insertCartDetailSQL);
            psDetail.setInt(1, cartID);
            psDetail.setInt(2, cart.getProductID());
            psDetail.setInt(3, cart.getQuantity());
            psDetail.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public List<Cart> getCartByUserID(String userID) {
        List<Cart> cart = new ArrayList<>();
        String sql = "SELECT c.cartID, "
                + "c.userID, "
                + "c.createdDate, "
                + "cd.productID, "
                + "cd.quantity "
                + "FROM tblCarts c "
                + "JOIN tblCartDetails cd ON c.cartID = cd.cartID "
                + "WHERE c.userID = ?";
        getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userID); // Không dùng LIKE
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int cartID = rs.getInt("cartID");
                String UserID = rs.getString("userID");
                Date createdDate = rs.getDate("createdDate");
                int productID = rs.getInt("productID");
                int quantity = rs.getInt("quantity");

                cart.add(new Cart(cartID, UserID, createdDate, productID, quantity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return cart;
    }

    public Cart getCartByID(int cartID) {
        String sql = "SELECT c.cartID, c.userID, c.createdDate, cd.productID, cd.quantity "
                + "FROM tblCarts c "
                + "JOIN tblCartDetails cd ON c.cartID = cd.cartID "
                + "WHERE c.cartID = ?";
        getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cartID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cart(
                        rs.getInt("cartID"),
                        rs.getString("userID"),
                        rs.getDate("createdDate"),
                        rs.getInt("productID"),
                        rs.getInt("quantity")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
        return null;
    }

    public void deleteCart(int cartID) {
        String deleteDetails = "DELETE FROM tblCartDetails WHERE cartID = ?";
        String deleteCart = "DELETE FROM tblCarts WHERE cartID = ?";
        getConnection();
        try {
            PreparedStatement ps1 = connection.prepareStatement(deleteDetails);
            ps1.setInt(1, cartID);
            ps1.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement(deleteCart);
            ps2.setInt(1, cartID);
            ps2.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
    }
    public int countCartItems(String userID) {
    int count = 0;
    String sql = "SELECT SUM(cd.quantity) " +
                 "FROM tblCarts c " +
                 "JOIN tblCartDetails cd ON c.cartID = cd.cartID " +
                 "WHERE c.userID = ?";
    getConnection();
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, userID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (Exception e) {
        e.printStackTrace();   
    } finally {
        closeConnection();
    }
    return count;
}


}
