package controller.product;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {

    private static final String SEARCH_PRODUCT = "search";
    private static final String SEARCH_PRODUCT_CONTROLLER = "SearchProductController";

    private static final String INSERT_PRODUCT = "insert";
    private static final String INSERT_PRODUCT_CONTROLLER = "CreateProductController";

    private static final String DELETE_PRODUCT = "delete";
    private static final String DELETE_PRODUCT_CONTROLLER = "DeletePeoductController";

    private static final String UPDATE_PRODUCT = "update";
    private static final String UPDATE_PRODUCT_CONTROLLER = "UpdateProductController";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "error.jsp";
        try {
            String action = request.getParameter("action");
            System.out.println("Hanh dong: " + action);
            switch (action) {
                case SEARCH_PRODUCT:
                    url = SEARCH_PRODUCT_CONTROLLER;
                    break;
                case INSERT_PRODUCT:
                    url = INSERT_PRODUCT_CONTROLLER;
                    break;
                case DELETE_PRODUCT:
                    url = DELETE_PRODUCT_CONTROLLER;
                    break;
                case UPDATE_PRODUCT:
                    url = UPDATE_PRODUCT_CONTROLLER;
                    break;
                default:
                    url = "error.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
