package controller.user;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private static final String SEARCH_USER_CONTROLLER = "SearchUserController";
    private static final String SEARCH_USER = "search";
    private static final String UPDATE_USER_CONTROLLER = "UpdateUserController";
    private static final String UPDATE_USER = "update";
    private static final String DELETE_USER_CONTROLLER = "DeleteUserController";
    private static final String DELETE_USER = "remove";
    private static final String INSERT_USER_CONTROLLER = "InsertUserController";
    private static final String INSERT_USER = "insert";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "error.jsp";
    }
}
