/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Invoice;

import dao.CartDAO;
import dao.ProductDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Cart;
import model.Product;
import model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import model.Invoice;

/**
 *
 * @author Log
 */
@WebServlet(name="PrepareInvoiceController", urlPatterns={"/PrepareInvoiceController"})
public class PrepareInvoiceController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PrepareInvoiceController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PrepareInvoiceController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"BU".equalsIgnoreCase(user.getRoleID())) {
            response.sendRedirect("checkAuthorized.jsp");
            return;
        }

        String[] selectedCartIDs = request.getParameterValues("selectedCartID");
        if (selectedCartIDs == null || selectedCartIDs.length == 0) {
            request.setAttribute("error", "Please choose product for payment.");
            request.getRequestDispatcher("viewCart.jsp").forward(request, response);
            return;
        }

        CartDAO cartDAO = new CartDAO();
        ProductDAO productDAO = new ProductDAO();

        List<Cart> selectedCarts = new ArrayList<>();
        float totalAmount = 0;

        for (String idStr : selectedCartIDs) {
            int cartID = Integer.parseInt(idStr);
            Cart cart = cartDAO.getCartByID(cartID);
            if (cart != null) {
                Product product = productDAO.getProductByID(cart.getProductID());
                totalAmount += product.getPrice() * cart.getQuantity();
                selectedCarts.add(cart);
            }
        }

        // Gửi danh sách sản phẩm và tổng tiền đến JSP
        Invoice invoice = new Invoice();
        invoice.setTotalAmount(totalAmount);
        request.setAttribute("selectedCarts", selectedCarts);
        request.setAttribute("invoice", invoice);

        // Forward tới invoicePage.jsp
        request.getRequestDispatcher("invoicePage.jsp").forward(request, response);
        
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
