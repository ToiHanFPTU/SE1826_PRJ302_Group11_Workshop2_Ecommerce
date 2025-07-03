package controller.product;

import dao.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DeletePeoductController", urlPatterns = {"/DeletePeoductController"})
public class DeletePeoductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productID = request.getParameter("productID").trim();
        try {
            if (productID == null || productID.isEmpty()) {
                System.out.println("Khong the lay product ID");
                request.setAttribute("msg", "Can't take product ID");
                request.getRequestDispatcher("ProductController?action=search").forward(request, response);
                return;
            } else {
                if (!new ProductDAO().deleteProduct(Integer.parseInt(productID))) {
                    System.out.println("Khong the xoa product");
                    request.setAttribute("msg", "Delete product failed");
                } else {
                    System.out.println("Xoa thanh cong");
                    request.setAttribute("msg", "Delete product successfully");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("SearchProducyByUserIDController").forward(request, response);
        return;
    }
}
