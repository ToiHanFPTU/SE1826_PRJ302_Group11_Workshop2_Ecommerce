package dao;

import DBUtils.utils;
import model.Promotion;
import model.ProductPromotion;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class PromotionDAO extends utils {

    // CRUD Operations for Promotions
    // Thêm method này vào PromotionDAO.java để lấy danh sách sản phẩm
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT productID, name FROM tblProducts WHERE status = 'Available'";

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductID(resultSet.getInt("productID"));
                product.setName(resultSet.getString("name"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return products;
    }

    // CREATE
    public boolean createPromotion(Promotion promotion) {
        String sql = "INSERT INTO tblPromotions (name, discountPercent, startDate, endDate, status) VALUES (?, ?, ?, ?, ?)";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, promotion.getName());
            preparedStatement.setFloat(2, promotion.getDiscountPercent());
            preparedStatement.setDate(3, promotion.getStartDate());
            preparedStatement.setDate(4, promotion.getEndDate());
            preparedStatement.setString(5, promotion.getStatus());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    // READ - Get all promotions
    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM tblPromotions ORDER BY promoID DESC";

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Promotion promotion = new Promotion(
                        resultSet.getInt("promoID"),
                        resultSet.getString("name"),
                        resultSet.getFloat("discountPercent"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getString("status")
                );
                promotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return promotions;
    }

    // READ - Get promotion by ID
    public Promotion getPromotionById(int promoID) {
        String sql = "SELECT * FROM tblPromotions WHERE promoID = ?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, promoID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Promotion(
                        resultSet.getInt("promoID"),
                        resultSet.getString("name"),
                        resultSet.getFloat("discountPercent"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    // UPDATE
    public boolean updatePromotion(Promotion promotion) {
        String sql = "UPDATE tblPromotions SET name = ?, discountPercent = ?, startDate = ?, endDate = ?, status = ? WHERE promoID = ?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, promotion.getName());
            preparedStatement.setFloat(2, promotion.getDiscountPercent());
            preparedStatement.setDate(3, promotion.getStartDate());
            preparedStatement.setDate(4, promotion.getEndDate());
            preparedStatement.setString(5, promotion.getStatus());
            preparedStatement.setInt(6, promotion.getPromoID());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    // DELETE
    // Cập nhật method deletePromotion trong PromotionDAO.java
    public boolean deletePromotion(int promoID) {
        String deleteProductPromotions = "DELETE FROM tblProductPromotions WHERE promoID = ?";
        String deletePromotion = "DELETE FROM tblPromotions WHERE promoID = ?";

        try {
            connection = getConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Bước 1: Xóa tất cả sản phẩm liên quan đến chương trình khuyến mãi
            preparedStatement = connection.prepareStatement(deleteProductPromotions);
            preparedStatement.setInt(1, promoID);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            // Bước 2: Xóa chương trình khuyến mãi
            preparedStatement = connection.prepareStatement(deletePromotion);
            preparedStatement.setInt(1, promoID);
            int rowsAffected = preparedStatement.executeUpdate();

            connection.commit(); // Commit transaction
            return rowsAffected > 0;

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback nếu có lỗi
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // Reset auto commit
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }

// Thêm method kiểm tra chương trình có đang được sử dụng không
    public boolean isPromotionInUse(int promoID) {
        String sql = "SELECT COUNT(*) FROM tblProductPromotions WHERE promoID = ?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, promoID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

// Thêm method lấy số lượng sản phẩm đang sử dụng chương trình
    public int getProductCountByPromotion(int promoID) {
        String sql = "SELECT COUNT(*) FROM tblProductPromotions WHERE promoID = ?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, promoID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return 0;
    }

    // Product-Promotion Operations
    // Gắn sản phẩm với chương trình khuyến mãi
    public boolean addProductToPromotion(int promoID, int productID) {
        String sql = "INSERT INTO tblProductPromotions (promoID, productID, createdDate) VALUES (?, ?, GETDATE())";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, promoID);
            preparedStatement.setInt(2, productID);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    // Xóa sản phẩm khỏi chương trình khuyến mãi
    public boolean removeProductFromPromotion(int promoID, int productID) {
        String sql = "DELETE FROM tblProductPromotions WHERE promoID = ? AND productID = ?";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, promoID);
            preparedStatement.setInt(2, productID);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    // Lấy danh sách sản phẩm trong chương trình khuyến mãi
    public List<ProductPromotion> getProductsByPromotion(int promoID) {
        List<ProductPromotion> products = new ArrayList<>();
        String sql = "SELECT pp.*, p.name as productName, pr.name as promoName, pr.discountPercent "
                + "FROM tblProductPromotions pp "
                + "JOIN tblProducts p ON pp.productID = p.productID "
                + "JOIN tblPromotions pr ON pp.promoID = pr.promoID "
                + "WHERE pp.promoID = ?";

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, promoID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ProductPromotion pp = new ProductPromotion();
                pp.setPromoID(resultSet.getInt("promoID"));
                pp.setProductID(resultSet.getInt("productID"));
                pp.setCreatedDate(resultSet.getDate("createdDate"));
                pp.setProductName(resultSet.getString("productName"));
                pp.setPromoName(resultSet.getString("promoName"));
                pp.setDiscountPercent(resultSet.getFloat("discountPercent"));
                products.add(pp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return products;
    }

    // Tự động hiển thị sản phẩm đang giảm giá
    public List<ProductPromotion> getProductsOnSale() {
        List<ProductPromotion> products = new ArrayList<>();
        String sql = "SELECT pp.*, p.name as productName, pr.name as promoName, pr.discountPercent, "
                + "p.price, p.price * (1 - pr.discountPercent/100) as discountedPrice "
                + "FROM tblProductPromotions pp "
                + "JOIN tblProducts p ON pp.productID = p.productID "
                + "JOIN tblPromotions pr ON pp.promoID = pr.promoID "
                + "WHERE pr.status = 'Active' AND GETDATE() BETWEEN pr.startDate AND pr.endDate";

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ProductPromotion pp = new ProductPromotion();
                pp.setPromoID(resultSet.getInt("promoID"));
                pp.setProductID(resultSet.getInt("productID"));
                pp.setCreatedDate(resultSet.getDate("createdDate"));
                pp.setProductName(resultSet.getString("productName"));
                pp.setPromoName(resultSet.getString("promoName"));
                pp.setDiscountPercent(resultSet.getFloat("discountPercent"));
                products.add(pp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return products;
    }
}
