package controller.inventory;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "InventoryController", urlPatterns = {"/InventoryController"})
public class InventoryController extends HttpServlet {

    private static final String SEARCH_INVENTORY = "search";
    private static final String SEARCH_INVENTORY_CONTROLLER = "SearchInventoryController";

    private static final String UPDATE_INVENTORY = "update";
    private static final String UPDATE_INVENTORY_CONTROLLER = "UpdateInventoryController";

    private static final String CREATE_INVENTORY = "insert";
    private static final String CREATE_INVENTORY_CONTROLLER = "CreateInventoryController";

    private static final String DELETE_INVENTORY = "remove";
    private static final String DELETE_INVENTORY_CONTROLLER = "DeleteInventoryController";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "error.jsp";
        try {
            String action = request.getParameter("action");
            System.out.println("action = " + action);
            switch (action) {
                case SEARCH_INVENTORY:
                    url = SEARCH_INVENTORY_CONTROLLER;
                    break;
                case UPDATE_INVENTORY:
                    url = UPDATE_INVENTORY_CONTROLLER;
                    break;
                case CREATE_INVENTORY:
                    url = CREATE_INVENTORY_CONTROLLER;
                    break;
                case DELETE_INVENTORY:
                    url = DELETE_INVENTORY_CONTROLLER;
                    break;
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
