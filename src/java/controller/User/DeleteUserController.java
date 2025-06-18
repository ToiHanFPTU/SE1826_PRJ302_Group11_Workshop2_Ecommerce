package controller.User;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet(name = "DeleteUserController", urlPatterns = {"/DeleteUserController"})
public class DeleteUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String userID = request.getParameter("userID");

            if (userID == null || userID.trim().isEmpty()) {
                request.setAttribute("msg", "Không thể lấy user ID.");
                response.sendRedirect("UserController?action=search");
                return;
            }

            UserDAO dao = new UserDAO();
            User user = dao.getUserByIDOrPhone(userID);
            if (user == null) {
                request.setAttribute("msg", "Không tìm thấy user.");
                response.sendRedirect("UserController?action=search");
                return;
            }

            if (dao.deleteUser(userID)) {
                request.setAttribute("msg", "Xóa người dùng thành công.");
            } else {
                request.setAttribute("msg", "Xóa người dùng thất bại.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Đã xảy ra lỗi.");
        }

        response.sendRedirect("UserController?action=search");
    }
}