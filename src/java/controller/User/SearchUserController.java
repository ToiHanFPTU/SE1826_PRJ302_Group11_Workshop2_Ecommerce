package controller.User;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.User;

@WebServlet(name = "SearchUserController", urlPatterns = {"/SearchUserController"})
public class SearchUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UserDAO userDAO = new UserDAO();
            List<User> users;
            String keyword = request.getParameter("searchBox");
            if (keyword == null || keyword.trim().isEmpty()) {
                users = userDAO.listAllUser();
            } else {
                users = userDAO.searchUser(keyword.trim());
            }
            request.setAttribute("userList", users);
            request.setAttribute("SEARCH_KEYWORD", keyword);
            request.getRequestDispatcher("adminUserPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Something went wrong while searching users.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
