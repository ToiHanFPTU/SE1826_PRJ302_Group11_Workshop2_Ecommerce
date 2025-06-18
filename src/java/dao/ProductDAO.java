package dao;

import DBUtils.utils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class ProductDAO extends utils {

    public List<Product> listAllProduct() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT [productID]\n"
                + "      ,[name]\n"
                + "      ,[categoryID]\n"
                + "      ,[price]\n"
                + "      ,[quantity]\n"
                + "      ,[sellerID]\n"
                + "      ,[status]\n"
                + "  FROM [dbo].[tblProducts]";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                String name = resultSet.getString("name");
                int categoryID = resultSet.getInt("categoryID");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String sellerID = resultSet.getString("sellerID");
                String status = resultSet.getString("status");
                products.add(new Product(productID, name, categoryID, price, quantity, sellerID, status));
            }
        } catch (SQLException e) {
            System.out.println("Error to connect database");
        }

        closeConnection();
        return products;
    }

    public List<Product> findProduct(String keyword, double minPrice, double maxPrice) {
        String sql = "SELECT [productID]\n"
                + "      ,[name]\n"
                + "      ,pro.[categoryID]\n"
                + "      ,[price]\n"
                + "      ,[quantity]\n"
                + "      ,[sellerID]\n"
                + "      ,[status]\n"
                + "  FROM [dbo].[tblProducts] pro\n"
                + "  JOIN [dbo].[tblCategories] cat\n"
                + "  ON pro.[categoryID] = cat.[categoryID]\n"
                + "  WHERE pro.[name] LIKE ?\n"
                + "  OR cat.[categoryName] LIKE ?\n"
                + "  OR pro.[status] = ?\n"
                + "  OR pro.[price] BETWEEN ? AND ?";
        List<Product> products = new ArrayList<>();
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, "%" + keyword + "%");
            preparedStatement.setObject(2, "%" + keyword + "%");
            preparedStatement.setObject(3, "%" + keyword + "%");
            preparedStatement.setObject(4, minPrice);
            preparedStatement.setObject(5, maxPrice);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                String name = resultSet.getString("name");
                int categoryID = resultSet.getInt("categoryID");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String sellerID = resultSet.getString("sellerID");
                String status = resultSet.getString("status");
                products.add(new Product(productID, name, categoryID, price, quantity, sellerID, status));
            }
        } catch (SQLException e) {
            System.out.println("Error to connect database");
        }
        closeConnection();
        return products;
    }

    public boolean insertProduct(Product product) {
        boolean isInserted = false;
        String sql = "INSERT INTO [dbo].[tblProducts]\n"
                + "           ([name]\n"
                + "           ,[categoryID]\n"
                + "           ,[price]\n"
                + "           ,[quantity]\n"
                + "           ,[sellerID]\n"
                + "           ,[status])\n"
                + "     VALUES (?,?,?,?,?,?)";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, product.getName());
            preparedStatement.setObject(2, product.getCategoryID());
            preparedStatement.setObject(3, product.getPrice());
            preparedStatement.setObject(4, product.getQuantity());
            preparedStatement.setObject(5, product.getSellerID());
            preparedStatement.setObject(6, product.getStatus());
            isInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error to connect database");
        }
        closeConnection();
        return isInserted;
    }

    public boolean deleteProduct(int productID) {
        boolean isDeleteed = false;
        String sql = "DELETE FROM [dbo].[tblProducts]\n"
                + "      WHERE [productID] = ?";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, productID);
            isDeleteed = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error to connect database");
        }
        closeConnection();
        return isDeleteed;
    }

    public boolean updateProduct(Product product) {
        boolean isUpdated = false;
        String sql = "UPDATE [dbo].[tblProducts]\n"
                + "   SET [name] = ?\n"
                + "      ,[categoryID] = ?\n"
                + "      ,[price] = ?\n"
                + "      ,[quantity] = ?\n"
                + "      ,[status] = ?\n"
                + " WHERE [productID] = ?";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, product.getName());
            preparedStatement.setObject(2, product.getCategoryID());
            preparedStatement.setObject(3, product.getPrice());
            preparedStatement.setObject(4, product.getQuantity());
            preparedStatement.setObject(5, product.getStatus());
            preparedStatement.setObject(6, product.getProductID());
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error to connect database");
        }
        closeConnection();
        return isUpdated;
    }
    public Product getProductByID(int id) {
    String sql = "SELECT [productID], [name], [categoryID], [price], [quantity], [sellerID], [status] "
               + "FROM [dbo].[tblProducts] WHERE [productID] = ?";
    Product product = null;
    getConnection();
    try {
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int productID = resultSet.getInt("productID");
            String name = resultSet.getString("name");
            int categoryID = resultSet.getInt("categoryID");
            double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");
            String sellerID = resultSet.getString("sellerID");
            String status = resultSet.getString("status");
            product = new Product(productID, name, categoryID, price, quantity, sellerID, status);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    closeConnection();
    return product;
}

}
