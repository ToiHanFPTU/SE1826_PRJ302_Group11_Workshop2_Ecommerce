/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Invoice;

import dao.CartDAO;
import dao.InvoiceDAO;
import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Cart;
import model.Invoice;
import model.Product;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Log
 */
@WebServlet(name="CreateInvoiceController", urlPatterns={"/CreateInvoiceController"})
public class CreateInvoiceController extends HttpServlet {
   
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
            out.println("<title>Servlet CreateInvoiceController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateInvoiceController at " + request.getContextPath () + "</h1>");
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
        // Lấy thông tin người dùng từ session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"BU".equalsIgnoreCase(user.getRoleID())) {
            response.sendRedirect("checkAuthorized.jsp");
            return;
        }

        // Lấy danh sách cartID được gửi từ invoicePage.jsp
        String[] selectedCartIDs = request.getParameterValues("selectedCartID");
        if (selectedCartIDs == null || selectedCartIDs.length == 0) {
            request.setAttribute("error", "No Product for payment");
            request.getRequestDispatcher("viewCart.jsp").forward(request, response);
            return;
        }

        // Lấy thông tin khác từ form
        String paymentMethod = request.getParameter("paymentMethod");
        String deliveryAddress = request.getParameter("deliveryAddress");
        String contact = request.getParameter("contact");

        CartDAO cartDAO = new CartDAO();
        ProductDAO productDAO = new ProductDAO();
        InvoiceDAO invoiceDAO = new InvoiceDAO();

        List<Cart> selectedCarts = new ArrayList<>();
        double totalAmount = 0;

        for (String cartIDStr : selectedCartIDs) {
            int cartID = Integer.parseInt(cartIDStr);
            Cart cart = cartDAO.getCartByID(cartID);
            if (cart != null) {
                Product product = productDAO.getProductByID(cart.getProductID());
                totalAmount += product.getPrice() * cart.getQuantity();
                selectedCarts.add(cart);
            }
        }

        // Tạo hóa đơn
        Date today = new Date(System.currentTimeMillis());
        int invoiceID = invoiceDAO.createInvoice(user.getUserID(), totalAmount, today);

        // Thêm chi tiết hóa đơn và xóa cart
        for (Cart cart : selectedCarts) {
            Product product = productDAO.getProductByID(cart.getProductID());

            invoiceDAO.addInvoiceDetail(invoiceID, cart.getProductID(), cart.getQuantity(), product.getPrice());

            // Xóa cart sau khi đã thanh toán
            cartDAO.deleteCart(cart.getCartID());
        }

        // Chuyển hướng đến trang xác nhận
        request.setAttribute("invoiceID", invoiceID);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("paymentMethod", paymentMethod);
        request.setAttribute("deliveryAddress", deliveryAddress);
        request.setAttribute("contact", contact);

        request.getRequestDispatcher("invoiceSuccess.jsp").forward(request, response);
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
