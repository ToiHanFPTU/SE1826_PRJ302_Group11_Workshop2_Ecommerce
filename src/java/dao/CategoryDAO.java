package dao;

import DBUtils.utils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Category;

public class CategoryDAO extends utils {

    public List<Category> listAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT [categoryID]\n"
                + "      ,[categoryName]\n"
                + "      ,[description]\n"
                + "  FROM [dbo].[tblCategories]";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int categoryID = resultSet.getInt("categoryID");
                String categoryName = resultSet.getString("categoryName");
                String description = resultSet.getString("description");
                categories.add(new Category(categoryID, categoryName, description));
            }
        } catch (SQLException e) {
            System.out.println("Error to connect database");
        }
        closeConnection();
        return categories;
    }

    public List<Category> findCategoriesByName(String name) {
        List<Category> categorys = new ArrayList<>();
        String sql = "SELECT [categoryID]\n"
                + "      ,[categoryName]\n"
                + "      ,[description]\n"
                + "  FROM [dbo].[tblCategories]\n"
                + "  Where [categoryName] LIKE ?";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, "%" + name + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int categoryID = resultSet.getInt("categoryID");
                String categoryName = resultSet.getString("categoryName");
                String description = resultSet.getString("description");
                categorys.add(new Category(categoryID, categoryName, description));
            }
        } catch (SQLException e) {
            System.out.println("Error to connect database");
        }
        closeConnection();
        return categorys;
    }

    public boolean insertCategory(Category category) {
        boolean isInserted = false;
        String sql = "INSERT INTO [dbo].[tblCategories]\n"
                + "           ([categoryName]\n"
                + "           ,[description])\n"
                + "     VALUES (?,?)";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, category.getCategoryName());
            preparedStatement.setObject(2, category.getDescription());
            isInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error to connect database");
        }
        closeConnection();
        return isInserted;
    }

    public boolean updateCategory(Category category) {
        boolean isUpdated = false;
        String sql = "UPDATE [dbo].[tblCategories]\n"
                + "   SET [categoryName] = ?\n"
                + "      ,[description] = ?\n"
                + " WHERE [categoryID] = ?";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, category.getCategoryName());
            preparedStatement.setObject(2, category.getDescription());
            preparedStatement.setObject(3, category.getCategoryID());
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error to connect database");
        }
        closeConnection();
        return isUpdated;
    }

    public boolean deleteCategory(int categotyID) {
        boolean isDeleted = false;
        String sql = "DELETE FROM [dbo].[tblCategories]\n"
                + "      WHERE [categoryID] = ?";
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, categotyID);
            isDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error to connect database");
        }
        closeConnection();
        return isDeleted;
    }
}
