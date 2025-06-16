package controller.user;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.User;

@WebServlet(name = "SearchUserController", urlPatterns = {"/SearchUserController"})
public class SearchUserController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UserDAO userDAO = new UserDAO();
        List<User> users = new ArrayList<>();
        String action = request.getParameter("searchBox");
        if (action == null || action.trim().isEmpty()) {
            System.out.println("Keyword nor found");
            users = userDAO.listAllUser();
        } else {
            System.out.println("Keyword found");
            users = userDAO.searchUser(action);
        }
        request.setAttribute("userList", users);
        request.getRequestDispatcher("userPage.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
