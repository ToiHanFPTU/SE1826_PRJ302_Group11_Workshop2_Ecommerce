package controller.product;

import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.Product;

@WebServlet(name = "CreateProductController", urlPatterns = {"/CreateProductController"})
public class CreateProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Category> categories = new CategoryDAO().listAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("addProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProductDAO productDAO = new ProductDAO();

            String name = request.getParameter("name");
            int categoryID = Integer.parseInt(request.getParameter("categoryID"));
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String sellerID = request.getParameter("sellerID");
            String status = request.getParameter("status");

            Product newProduct = new Product(0, name, categoryID, price, quantity, sellerID, status);
            boolean success = productDAO.insertProduct(newProduct);

            if (success) {
                request.setAttribute("msg", "Create new product successfully");
            } else {
                request.setAttribute("msg", "Create new product failed");
            }

            // Redirect về danh sách sản phẩm (hoặc có thể về lại form)
            response.sendRedirect("ProductController?action=search");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Lỗi khi thêm sản phẩm: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}