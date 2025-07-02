package controller.product;

import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;
import model.Category;
import model.Product;

@WebServlet(name = "SearchProductController", urlPatterns = {"/SearchProductController"})
public class SearchProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            CategoryDAO categoryDAO = new CategoryDAO();
            ProductDAO productDAO = new ProductDAO();

            List<Category> categoryList = categoryDAO.listAllCategories();

            // Lấy các tham số từ form
            String keyword = request.getParameter("searchBox");
            String minPriceParam = request.getParameter("minPrice");
            String maxPriceParam = request.getParameter("maxPrice");
            String orderByPrice = request.getParameter("orderByPrice");
            String categoryIDParam = request.getParameter("categoryID");

            int categoryID = 0; // Mặc định là không lọc
            if (categoryIDParam != null && !categoryIDParam.isEmpty()) {
                try {
                    categoryID = Integer.parseInt(categoryIDParam.trim());
                    System.out.println("Found categoryID: " + categoryID);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid categoryID: " + categoryIDParam);
                }
            }

            double minPrice = 0;
            double maxPrice = 0;
            if (minPriceParam != null && !minPriceParam.trim().isEmpty()) {
                minPrice = Double.parseDouble(minPriceParam.trim());
            }
            if (maxPriceParam != null && !maxPriceParam.trim().isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceParam.trim());
            }

            if (keyword == null) {
                keyword = "";
            }

            // Tìm sản phẩm theo các tiêu chí
            List<Product> products = productDAO.findProduct(keyword.trim(), categoryID, minPrice, maxPrice);

            // Sắp xếp nếu có yêu cầu
            if (orderByPrice != null) {
                if ("ascending".equalsIgnoreCase(orderByPrice)) {
                    products.sort(Comparator.comparing(Product::getPrice));
                } else if ("descending".equalsIgnoreCase(orderByPrice)) {
                    products.sort(Comparator.comparing(Product::getPrice).reversed());
                }
            }

            // Gửi dữ liệu về trang JSP
            request.setAttribute("keyword", keyword);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);
            request.setAttribute("orderByPrice", orderByPrice);
            request.setAttribute("productList", products);
            request.setAttribute("categoryList", categoryList);
            request.setAttribute("categoryIDChoose", categoryID); // để giữ option được chọn

            request.getRequestDispatcher("ProductList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
