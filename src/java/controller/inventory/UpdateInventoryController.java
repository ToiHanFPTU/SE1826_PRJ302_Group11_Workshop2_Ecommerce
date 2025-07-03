package controller.inventory;

import dao.InventoryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateInventoryController", urlPatterns = {"/UpdateInventoryController"})
public class UpdateInventoryController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean isUpdated = false;
        String productID_raw = request.getParameter("productID").trim();
        String stockQuantity_raw = request.getParameter("stockQuantity").trim();
        String reorderThreshold_raw = request.getParameter("reorderThreshold").trim();
        if (productID_raw == null || productID_raw.isEmpty() || stockQuantity_raw == null || stockQuantity_raw.isEmpty() || reorderThreshold_raw == null || reorderThreshold_raw.isEmpty()) {
            System.out.println("Khong the lay stockQuantity, reorderThreshold hoac productID");
            response.sendRedirect("InventoryController?action=search");
        }
        else {
            try {
                int productID = Integer.parseInt(productID_raw);
                int stockQuantity = Integer.parseInt(stockQuantity_raw);
                int reorderThreshold = Integer.parseInt(stockQuantity_raw);
                System.out.println("da lay: " + productID + ", " + stockQuantity + ", " + reorderThreshold);
                new InventoryDAO().updateInventory(productID, stockQuantity, reorderThreshold);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (isUpdated) {
            System.out.println("update thanh cong");
            request.setAttribute("msg", "Update information successfully");
        } else {
            System.out.println("update that bai");
            request.setAttribute("msg", "Update information failed");
        }
        request.getRequestDispatcher("InventoryController?action=search").forward(request, response);
    }
}
