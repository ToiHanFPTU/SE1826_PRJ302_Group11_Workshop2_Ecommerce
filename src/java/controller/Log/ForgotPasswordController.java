package controller.Log;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "ForgotPasswordController", urlPatterns = {"/ForgotPasswordController"})
public class ForgotPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String IDOrPhone = request.getParameter("identifier");
        User userFound = new UserDAO().getUserByIDOrPhone(IDOrPhone);
        if (userFound == null) {
            System.out.println("Không tìm thấy user");
            request.setAttribute("error", "No user found with that information.");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
        }
        else {
            System.out.println("đã tìm thấy user");
            System.out.println(userFound.toString());
            request.getSession().setAttribute("user", userFound);
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        }
    }
}
