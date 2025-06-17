package controller.Log;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "SignUpController", urlPatterns = {"/SignUpController"})
public class SignUpController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String userID = request.getParameter("userID");
        if (userID == null || userID.isEmpty()) {
            System.out.println("khong the lay user ID");
        }
        String fullName = request.getParameter("fullName");
        //check roleID co bi trung hay khong
        if (userDAO.isDuplicateUserID(userID)) {
            System.out.println("user ID bi trung khong the insert");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        User user = new User(userID, fullName, "bu", password, phone);
        if (userDAO.insertUser(user)) {
            System.out.println("dang ky user thanh cong");
            request.setAttribute("msg", "Sign up successfully");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            System.out.println("dang ky user that bai");
            request.setAttribute("msg", "Sign up failed");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
