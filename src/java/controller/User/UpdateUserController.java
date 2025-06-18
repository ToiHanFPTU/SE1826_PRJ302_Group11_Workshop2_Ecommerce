package controller.User;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "UpdateUserController", urlPatterns = {"/UpdateUserController"})
public class UpdateUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            UserDAO userDAO = new UserDAO();
            String userID = request.getParameter("userID");
            User user = userDAO.getUserByIDOrPhone(userID);

            if (user == null) {
                request.setAttribute("msg", "Không thể tìm thấy người dùng.");
            } else {
                String fullName = request.getParameter("fullName");
                String roleID = request.getParameter("roleID");
                String password = request.getParameter("password");
                String phone = request.getParameter("phone");

                user.setFullName(fullName);
                user.setRoleID(roleID);
                user.setPassword(password);
                user.setPhone(phone);

                if (userDAO.isUpdated(user)) {
                    request.setAttribute("msg", "Cập nhật người dùng thành công");
                    response.sendRedirect("UserController?action=search");
                    return;
                } else {
                    request.setAttribute("msg", "Cập nhật người dùng thất bại");
                }
                request.setAttribute("user", user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Có lỗi xảy ra khi cập nhật");
        }
    }
}
