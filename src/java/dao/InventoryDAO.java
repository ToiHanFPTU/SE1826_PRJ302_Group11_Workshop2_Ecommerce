package dao;

import DBUtils.utils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Inventory;

public class InventoryDAO extends utils {

    public void addInventory(int productID, int stockQuantity, int reorderThreshold) {
        String sql = "INSERT INTO tblInventory (productID, stockQuantity, reorderThreshold) VALUES (?, ?, ?)";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, productID);
            preparedStatement.setInt(2, stockQuantity);
            preparedStatement.setInt(3, reorderThreshold);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public void updateInventory(int productID, int newQuantity, int newThreshold) {
        String sql = "UPDATE tblInventory SET stockQuantity = ?, reorderThreshold = ? WHERE productID = ?";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, newThreshold);
            preparedStatement.setInt(3, productID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public void deleteInventoryByProductID(int productID) {
        String sql = "DELETE FROM tblInventory WHERE productID = ?";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, productID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public List<Inventory> listAllInventory() {
        List<Inventory> inventories = new ArrayList<>();
        String sql = "SELECT i.warehouseID, i.productID, p.name, i.stockQuantity, i.reorderThreshold "
                + "FROM tblInventory i "
                + "JOIN tblProducts p ON i.productID = p.productID ";
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Inventory inv = new Inventory();
                inv.setWarehouseID(resultSet.getInt("warehouseID"));
                inv.setProductID(resultSet.getInt("productID"));
                inv.setProductName(resultSet.getString("name"));
                inv.setStockQuantity(resultSet.getInt("stockQuantity"));
                inv.setReorderThreshold(resultSet.getInt("reorderThreshold"));
                inventories.add(inv);
            }

            closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventories;
    }

    public List<Inventory> getLowStockInventories() {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT i.warehouseID, i.productID, p.name, i.stockQuantity, i.reorderThreshold "
                + "FROM tblInventory i "
                + "JOIN tblProducts p ON i.productID = p.productID "
                + "WHERE i.stockQuantity < i.reorderThreshold";

        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Inventory inv = new Inventory();
                inv.setWarehouseID(resultSet.getInt("warehouseID"));
                inv.setProductID(resultSet.getInt("productID"));
                inv.setProductName(resultSet.getString("name"));
                inv.setStockQuantity(resultSet.getInt("stockQuantity"));
                inv.setReorderThreshold(resultSet.getInt("reorderThreshold"));
                list.add(inv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return list;
    }

    public List<Inventory> searchInventoty(String inventoryName) {
        String sql = "SELECT TOP (1000) [warehouseID]\n"
                + "				  ,i.[productID]\n"
                + "				  ,p.[name]\n"
                + "				  ,[stockQuantity]\n"
                + "				  ,[reorderThreshold]\n"
                + "  FROM [ECommerceDB].[dbo].[tblInventory] i\n"
                + "  JOIN [tblProducts] p on p.[productID] = i.[productID]\n"
                + "  WHERE p.[name] LIKE ?";
        List<Inventory> inventorys = new ArrayList<>();

        try {
            getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + inventoryName + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Inventory inv = new Inventory();
                inv.setWarehouseID(resultSet.getInt("warehouseID"));
                inv.setProductID(resultSet.getInt("productID"));
                inv.setProductName(resultSet.getString("name"));
                inv.setStockQuantity(resultSet.getInt("stockQuantity"));
                inv.setReorderThreshold(resultSet.getInt("reorderThreshold"));
                inventorys.add(inv);
            }
            closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventorys;
    }
}
