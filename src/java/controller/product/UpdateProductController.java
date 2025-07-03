package controller.product;

import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

@WebServlet(name = "UpdateProductController", urlPatterns = {"/UpdateProductController"})
public class UpdateProductController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productID = request.getParameter("productID");
        if (productID == null || productID.trim().isEmpty()) {
            request.setAttribute("msg", "Không thể lấy product ID");
            request.getRequestDispatcher("ProductController?action=search").forward(request, response);
            return;
        }

        Product product = new ProductDAO().getProductByID(Integer.parseInt(productID));
        if (product == null) {
            request.setAttribute("msg", "Không thể tìm sản phẩm");
            request.getRequestDispatcher("ProductController?action=search").forward(request, response);
            return;
        }

        // Parse các tham số từ form
        String name = request.getParameter("name");
        String categoryParam = request.getParameter("categoryID");
        String priceParam = request.getParameter("price");
        String quantityParam = request.getParameter("quantity");

        if (categoryParam == null || priceParam == null || quantityParam == null) {
            request.setAttribute("msg", "Vui lòng điền đầy đủ thông tin sản phẩm");
            request.setAttribute("product", product);
            request.setAttribute("categories", new CategoryDAO().listAllCategories());
            request.getRequestDispatcher("updateProductPage.jsp").forward(request, response);
            return;
        }

        int categoryID = Integer.parseInt(categoryParam);
        double price = Double.parseDouble(priceParam);
        int quantity = Integer.parseInt(quantityParam);

        String sellerID = request.getParameter("sellerID");
        String status = request.getParameter("status");

        product.setName(name);
        product.setCategoryID(categoryID);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setSellerID(sellerID);
        product.setStatus(status);

        ProductDAO productDAO = new ProductDAO();
        if (productDAO.updateProduct(product)) {
            response.sendRedirect("SearchProducyByUserIDController");
        } else {
            request.setAttribute("msg", "Cập nhật sản phẩm thất bại");
            request.setAttribute("product", product);
            request.setAttribute("categories", new CategoryDAO().listAllCategories());
            request.getRequestDispatcher("updateProductPage.jsp").forward(request, response);
        }
    }

}
