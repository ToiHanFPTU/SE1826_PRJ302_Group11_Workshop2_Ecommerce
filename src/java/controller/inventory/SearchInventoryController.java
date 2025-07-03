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

@WebServlet(name = "SearchInventoryController", urlPatterns = {"/SearchInventoryController"})
public class SearchInventoryController extends HttpServlet {

    InventoryDAO inventoryDAO = new InventoryDAO();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Inventory> inventorys;
        try {
            String keyword = request.getParameter("searchBox");
            if (keyword == null || keyword.trim().isEmpty()) {
                System.out.println("khong tim thay ten");
                inventorys = inventoryDAO.listAllInventory();
            }
            else {
                inventorys = inventoryDAO.searchInventoty(keyword);
            }
            request.setAttribute("inventoryList", inventorys);
            request.setAttribute("keyWord", keyword);
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
        doPost(request, response);
    }
}
