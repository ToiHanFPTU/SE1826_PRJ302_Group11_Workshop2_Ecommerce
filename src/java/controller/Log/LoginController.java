package controller.Log;

import dao.CategoryDAO;
import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Category;
import model.User;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String ADMIN = "adminPage.jsp";
    private static final String PRODUCT_LIST = "ProductController?action=search";
    private static final String DELIVERY_CONTROLLER = "DeliveryController?action=list";
    String url = "LoginController";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            String username = request.getParameter("username");
            System.out.println(username);
            String password = request.getParameter("password");
            System.out.println(password);
            UserDAO userDAO = new UserDAO();
            User userLogin = userDAO.login(username, password);
            if (userLogin == null) {
                System.out.println("Login failed: user not found");
                request.setAttribute("error", "Incorrect username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                CategoryDAO cateDAO = new CategoryDAO();
                List<Category> listCategory = cateDAO.listAllCategories();
                System.out.println("Found: " + userLogin.toString());
                session.setAttribute("user", userLogin);
                session.setAttribute("category", listCategory);
                String action = userLogin.getRoleID();
                switch (action) {
                    case "AD":
                        url = ADMIN;
                        break;
                    case "BU" :
                        url = PRODUCT_LIST ;
                       break;
                    case "SE" :
                        url = PRODUCT_LIST ;
                        break;
                    case "DL": 
                        url = DELIVERY_CONTROLLER ;
                        break; 
                    default :
                        url = ERROR ;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
