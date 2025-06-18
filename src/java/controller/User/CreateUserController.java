package controller.User;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "CreateUserController", urlPatterns = {"/CreateUserController"})
public class CreateUserController extends HttpServlet {

    public static final String PHONE_FORMAT = "^\\d{10,12}$";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String userID = request.getParameter("userIDInsert");
        String fullName = request.getParameter("fullNameInsert");
        String roleID = request.getParameter("roleIDInsert");
        String password = request.getParameter("passwordInsert");
        String phone = request.getParameter("phoneInsert");

        try {
            if (userDAO.isDuplicateUserID(userID)) {
                request.setAttribute("msg", "User ID was duplicated");
                request.getRequestDispatcher("addUserPage.jsp").forward(request, response);
                return;
            }

            if (userDAO.isDuplicatePassword(password)) {
                request.setAttribute("msg", "Password was duplicated");
                request.getRequestDispatcher("addUserPage.jsp").forward(request, response);
                return;
            }

            if (!phone.matches(PHONE_FORMAT)) {
                request.setAttribute("msg", "Please enter a valid phone number (10 to 12 digits)");
                request.getRequestDispatcher("addUserPage.jsp").forward(request, response);
                return;
            }

            if (userDAO.insertUser(new User(userID, fullName, roleID, password, phone))) {
                request.setAttribute("msg", "Create new user successfully");
            } else {
                request.setAttribute("msg", "Create new user failed");
            }

            response.sendRedirect("UserController?action=search");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "An error occurred");
            request.getRequestDispatcher("addUserPage.jsp").forward(request, response);
        }
    }

}
