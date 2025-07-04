/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Invoice;

import dao.InvoiceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Invoice;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author Log
 */
@WebServlet(name="SearchInvoiceController", urlPatterns={"/SearchInvoiceController"})
public class SearchInvoiceController extends HttpServlet {
   
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
            out.println("<title>Servlet SearchInvoiceController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchInvoiceController at " + request.getContextPath () + "</h1>");
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
          HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"BU".equalsIgnoreCase(user.getRoleID())) {
            response.sendRedirect("checkAuthorized.jsp");
            return;
        }

        String status = request.getParameter("status"); // ví dụ: "pending", "shipping", v.v.
        InvoiceDAO invoiceDAO = new InvoiceDAO();

        List<Invoice> invoiceList;
        if (status == null || status.equalsIgnoreCase("all")) {
            invoiceList = invoiceDAO.getInvoicesByUser(user.getUserID()); // tất cả đơn
        }else {
            invoiceList = invoiceDAO.getInvoicesByUserAndStatus(user.getUserID(), status); // lọc theo trạng thái
        }
         
        request.setAttribute("invoiceList", invoiceList);
        request.setAttribute("status", status); // để highlight tab nếu cần
        request.getRequestDispatcher("viewInvoiceList.jsp").forward(request, response);
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
        processRequest(request, response);
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
