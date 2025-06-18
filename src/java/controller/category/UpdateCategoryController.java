package controller.category;

import dao.CategoryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;

@WebServlet(name = "UpdateCategoryController", urlPatterns = {"/UpdateCategoryController"})
public class UpdateCategoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int categoryID = Integer.parseInt(request.getParameter("categoryID").trim());
        try {
            if (String.valueOf(categoryID).trim().isEmpty() || String.valueOf(categoryID) == null) {
                System.out.println("khong the lay cateID");
                request.setAttribute("msg", "Can't take categoryID");
                request.getRequestDispatcher("CategoryController?action=search").forward(request, response);
                return;
            } else {
                Category category = new CategoryDAO().getCategoryByID(categoryID);
                if (category == null) {
                    System.out.println("khong the tim user");
                    request.setAttribute("msg", "Can't find Category with id " + categoryID);
                    request.getRequestDispatcher("CategoryController?action=search").forward(request, response);
                    return;
                } else {
                    String categoryName = request.getParameter("categoryName");
                    System.out.println("da lay " + categoryName);
                    String description = request.getParameter("categoryDescription");
                    System.out.println("da lay " + description);
                    category.setCategoryName(categoryName);
                    category.setDescription(description);
                    if (new CategoryDAO().updateCategory(category)) {
                        System.out.println("cap nhat thanh cong");
                        request.setAttribute("msg", "Update category successfull");
                        request.getRequestDispatcher("CategoryController?action=search").forward(request, response);
                        return;
                    } else {
                        request.setAttribute("msg", "Update category failed");
                        request.getRequestDispatcher("CategoryController?action=search").forward(request, response);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Đã xảy ra lỗi.");
        }
        request.getRequestDispatcher("CategoryController?action=search").forward(request, response);
    }
}
