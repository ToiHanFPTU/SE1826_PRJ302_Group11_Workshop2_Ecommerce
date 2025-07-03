package controller.Cart;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dao.CartDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import model.Cart;
import model.User;

/**
 *
 * @author Log
 */
@WebServlet(name = "AddCartController", urlPatterns = {"/AddCartController"})
public class AddCartController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String userID = user.getUserID();
        if(userID == null){
            response.sendRedirect("login.jsp");
            return;
        }

        String userID = user.getUserID();

        try {
            String rawProductID = request.getParameter("productID");
            String rawQuantity = request.getParameter("quantity");

            System.out.println("productID: " + rawProductID);
            System.out.println("quantity: " + rawQuantity);

            int productID = Integer.parseInt(rawProductID);
            int quantity = Integer.parseInt(rawQuantity);
            Date createdDate = new Date(System.currentTimeMillis());

            Cart c = new Cart(userID, createdDate, productID, quantity);
            System.out.println("Cart: " + c);

            CartDAO cdao = new CartDAO();
            cdao.addToCart(c);
        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }finally{
            request.getRequestDispatcher("ProductList.jsp").forward(request, response);
        }
    }

}
