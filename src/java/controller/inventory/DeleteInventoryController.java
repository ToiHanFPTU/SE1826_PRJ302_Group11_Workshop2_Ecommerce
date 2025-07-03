/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.inventory;

import dao.InventoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteInventoryController", urlPatterns = {"/DeleteInventoryController"})
public class DeleteInventoryController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean isDelete = false;
        String productID_raw = request.getParameter("productID");
        if (productID_raw == null || productID_raw.trim().isEmpty()) {
            System.out.println("khong the lay product ID");
            response.sendRedirect("InventoryController?action=search");
        } else {
            System.out.println("da lay poroduct voi ID: " + productID_raw);
            int productID = 0;
            try {
                productID = Integer.parseInt(productID_raw);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            new InventoryDAO().deleteInventoryByProductID(productID);
            isDelete = true;
        }
        if (isDelete) {
            System.out.println("xoa thanh cong");
            request.setAttribute("msg", "Delete item in Inventory successfully");

        } else {
            System.out.println("xoa that bai");
            request.setAttribute("msg", "Delete item in Inventory failed");
        }
        request.getRequestDispatcher("InventoryController?action=search").forward(request, response);

    }
}
