package controller.category;

import dao.CategoryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteCategoryController", urlPatterns = {"/DeleteCategoryController"})
public class DeleteCategoryController extends HttpServlet {

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
                request.setAttribute("msg", "Can't take categoryID");
                request.getRequestDispatcher("CategoryController?action=search");
                return;
            } else {
                if (!new CategoryDAO().deleteCategory(categoryID)) {
                    System.out.println("delete that bai");
                    request.setAttribute("msg", "Delete category failed");
                    
                }
                else {
                    System.out.println("delete thanh cong");
                    request.setAttribute("msg", "Delete category successfully");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Đã xảy ra lỗi.");
        }
        response.sendRedirect("CategoryController?action=search");
    }
}
