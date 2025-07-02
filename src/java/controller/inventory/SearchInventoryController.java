/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.inventory;

import dao.InventoryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Inventory;

/**
 *
 * @author HP
 */
@WebServlet(name = "SearchInventoryController", urlPatterns = {"/SearchInventoryController"})
public class SearchInventoryController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            InventoryDAO inventoryDAO = new InventoryDAO();
            List<Inventory> inventorys = inventoryDAO.listAllInventory();
            System.out.println("Tìm thấy " + inventorys.size() + " hàng tồn kho.");
            for (Inventory inv : inventorys) {
                System.out.println(inv);
            }

            request.setAttribute("inventoryList", inventorys);
            System.out.println("Tìm thấy " + inventorys.size() + " hàng tồn kho.");
            request.getRequestDispatcher("inventoryList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Không thể tải danh sách tồn kho.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "SearchInventoryController hiển thị danh sách hàng tồn kho.";
    }
}
