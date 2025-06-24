package controller.Log;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");
        System.out.println(password);
        UserDAO userDAO = new UserDAO();
        try {
            User userLogin = userDAO.login(username, password);
            if (userLogin == null) {
                System.out.println("Login failed: user not found");
                request.setAttribute("error", "Incorrect username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                System.out.println("Found: " + userLogin.toString());
                HttpSession session = request.getSession();
                session.setAttribute("user", userLogin);
//                response.sendRedirect(request.getContextPath() + "/ProductController?action=search");
                request.getRequestDispatcher("view/customer/shoppingPage.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
