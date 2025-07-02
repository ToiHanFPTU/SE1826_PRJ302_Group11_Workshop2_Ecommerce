package controller.product;

import dao.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;
import model.Product;
import model.User;

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
        HttpSession session = request.getSession();
        try {
            User userLogin =(User) session.getAttribute("user");
            ProductDAO productDAO = new ProductDAO();
            List<Product> products;

            String keyword = request.getParameter("searchBox");
            String minPriceParam = request.getParameter("minPrice");
            String maxPriceParam = request.getParameter("maxPrice");
            String orderByPrice = request.getParameter("orderByPrice");

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

            products = productDAO.findProduct(keyword.trim(), minPrice, maxPrice);

            if (orderByPrice != null) {
                if ("ascending".equalsIgnoreCase(orderByPrice)) {
                    products.sort(Comparator.comparing(Product::getPrice));
                } else if ("descending".equalsIgnoreCase(orderByPrice)) {
                    products.sort(Comparator.comparing(Product::getPrice).reversed());
                }
            }
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);
            request.setAttribute("orderByPrice", orderByPrice);
            request.setAttribute("productList", products);
            if(userLogin.getRoleID().equalsIgnoreCase("se")|| userLogin.getRoleID().equalsIgnoreCase("ad")){
            request.getRequestDispatcher("productPage.jsp").forward(request, response);
            }else if(userLogin.getRoleID().equalsIgnoreCase("bu")){
                request.getRequestDispatcher("ProductList.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}
