package controller.product;

import dao.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Product;
import model.User;

@WebServlet(name = "SearchProducyByUserIDController", urlPatterns = {"/SearchProducyByUserIDController"})
public class SearchProducyByUserIDController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || (!user.getRoleID().equalsIgnoreCase("SE") && !user.getRoleID().equalsIgnoreCase("AD"))) {
            response.sendRedirect("checkAuthorized.jsp");
            return;
        }

        String sellerID = user.getUserID();

        String searchBox = request.getParameter("searchBox");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        String orderByPrice = request.getParameter("orderByPrice");

        double minPrice = 0;
        double maxPrice = Double.MAX_VALUE;

        try {
            if (minPriceStr != null && !minPriceStr.isEmpty()) {
                minPrice = Double.parseDouble(minPriceStr);
            }
            if (maxPriceStr != null && !maxPriceStr.isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceStr);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "Giá không hợp lệ.");
        }

        // Truy vấn dữ liệu
        ProductDAO dao = new ProductDAO();
        List<Product> productList = dao.searchProductBySellerID(sellerID, searchBox, minPrice, maxPrice, orderByPrice);

        // Truyền dữ liệu ra JSP
        request.setAttribute("productList", productList);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        request.setAttribute("orderByPrice", orderByPrice);

        request.getRequestDispatcher("sellerProductPage.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
