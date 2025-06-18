package controller.category;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CategoryController", urlPatterns = {"/CategoryController"})
public class CategoryController extends HttpServlet {

    private static final String SEARCH_CATEGORY = "search";
    private static final String SEARCH_CATEGORY_CONTROLLER = "SearchCategoryController";

    private static final String DELETE_CATEGORY = "delete";
    private static final String DELETE_CATEGORY_CONTROLLER = "DeleteCategoryController";

    private static final String INSERT_CATEGORY = "insert";
    private static final String INSERT_CATEGORY_CONTROLLER = "CreateCategoryController";

    private static final String UPDATE_CATEGORY = "update";
    private static final String UPDATE_CATEGORY_CONTROLLER = "UpdateCategoryController";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "error.jsp";
        try {
            String action = request.getParameter("action");
            System.out.println("Hanh dong: " + action);
            switch (action) {
                case SEARCH_CATEGORY:
                    url = SEARCH_CATEGORY_CONTROLLER;
                    break;
                case DELETE_CATEGORY:
                    url = DELETE_CATEGORY_CONTROLLER;
                    break;
                case INSERT_CATEGORY:
                    url = INSERT_CATEGORY_CONTROLLER;
                    break;
                case UPDATE_CATEGORY:
                    url = UPDATE_CATEGORY_CONTROLLER;
                    break;
                default:
                    throw new AssertionError();
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
