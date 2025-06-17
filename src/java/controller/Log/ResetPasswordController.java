package controller.Log;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "ResetPasswordController", urlPatterns = {"/ResetPasswordController"})
public class ResetPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //lấy user để check user có quyền vô trang này không
        User user = (User) request.getSession().getAttribute("user");
        //nếu chưa đăng nhập và vô trang này thì sẽ bị đá ra trang login.jsp
        if (user == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        System.out.println("Đã lấy được user");
        System.out.println(user.toString());
        //lấy password mới
        String newPassword = request.getParameter("newPassword");
        System.out.println("đã láy mật khẩu");
        //lấy xác nhận mật khẩu
        String confirmPassword = request.getParameter("confirmPassword");
        System.out.println("đã lấy nhật khẩu xác nhận");
        //lấy userID
        //cxheck khớp
        String userID = request.getParameter("userID");
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("msg", "Passwords do not match");
            request.setAttribute("userID", userID);
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }
        System.out.println("đã lấy userID");
        //check xem mật khẩu có bị trùng hay không
        if (new UserDAO().isDuplicatePassword(newPassword)) {
            System.out.println("nhật khẩu bị trùng");
            request.setAttribute("msg", "Password exist");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }
        if (newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("msg", "Password fields cannot be empty");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        //thực hiện việc update
        if (new UserDAO().updatePasswordByID(newPassword, userID)) {
            System.out.println("Update thành công");
            request.setAttribute("msg", "Reset password successfully");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            //nếu update không được thì trả lại lỗi và trả về trang resetPassword.jsp
        } else {
            System.out.println("Update thất bại");
            request.setAttribute("msg", "Reset failed.");
            request.setAttribute("userID", userID);
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        }
    }

}
