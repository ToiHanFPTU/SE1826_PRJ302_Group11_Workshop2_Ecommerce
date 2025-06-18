package controller.category;

import dao.CategoryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;

@WebServlet(name = "CreateCategoryController", urlPatterns = {"/CreateCategoryController"})
public class CreateCategoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.getRequestDispatcher("addCategoryPage.jsp").forward(request, response);
            String categoryName = request.getParameter("categoryName");
            System.out.println("lay " + categoryName + " thanh cong");
            String categoryDescription = request.getParameter("categoryDescription");
            System.out.println("lay " + categoryDescription + " thanh cong");
            Category category = new Category(categoryName, categoryDescription);
            if (!new CategoryDAO().insertCategory(category)) {
                System.out.println("them category that bai");
                request.setAttribute("msg", "Insert new category failed");
                request.getRequestDispatcher("addCategoryPage.jsp").forward(request, response);
                return;

            } else {
                System.out.println("them category thanh cong");
                request.setAttribute("msg", "Insert new category successfully");
            }
            request.getRequestDispatcher("CategoryController?action=search").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
