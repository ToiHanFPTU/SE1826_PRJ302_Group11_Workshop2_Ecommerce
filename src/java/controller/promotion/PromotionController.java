package controller.promotions;

import dao.PromotionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Promotion;
import model.ProductPromotion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import model.Product;

@WebServlet(name = "PromotionController", urlPatterns = {"/PromotionController"})
public class PromotionController extends HttpServlet {

    private PromotionDAO promotionDAO = new PromotionDAO();

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "list":
                    listPromotions(request, response);
                    break;
                case "create":
                    createPromotion(request, response);
                    break;
                case "update":
                    updatePromotion(request, response);
                    break;
                case "delete":
                    deletePromotion(request, response);
                    break;
                case "addProduct":
                    addProductToPromotion(request, response);
                    break;
                case "removeProduct":
                    removeProductFromPromotion(request, response);
                    break;
                default:
                    listPromotions(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            listPromotions(request, response);
        }
    }

    // Hiển thị trang quản lý khuyến mãi
    private void listPromotions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy danh sách tất cả khuyến mãi
        List<Promotion> promotions = promotionDAO.getAllPromotions();
        request.setAttribute("promotions", promotions);

        // Lấy danh sách sản phẩm đang giảm giá
        List<ProductPromotion> productsOnSale = promotionDAO.getProductsOnSale();
        request.setAttribute("productsOnSale", productsOnSale);
        List<Product> allProducts = promotionDAO.getAllProducts();
        request.setAttribute("allProducts", allProducts);
        request.getRequestDispatcher("managepromotions.jsp").forward(request, response);
    }

    // Tạo chương trình khuyến mãi mới
    private void createPromotion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            String discountStr = request.getParameter("discountPercent");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String status = request.getParameter("status");

            // Validation
            if (name == null || name.trim().isEmpty()) {
                throw new Exception("Tên chương trình không được để trống");
            }

            if (discountStr == null || discountStr.trim().isEmpty()) {
                throw new Exception("Phần trăm giảm giá không được để trống");
            }

            float discountPercent = Float.parseFloat(discountStr);
            if (discountPercent <= 0 || discountPercent > 100) {
                throw new Exception("Phần trăm giảm giá phải từ 1-100%");
            }

            if (startDateStr == null || endDateStr == null) {
                throw new Exception("Ngày bắt đầu và kết thúc không được để trống");
            }

            Date startDate = Date.valueOf(startDateStr);
            Date endDate = Date.valueOf(endDateStr);

            if (startDate.after(endDate)) {
                throw new Exception("Ngày bắt đầu phải trước ngày kết thúc");
            }

            if (status == null || status.trim().isEmpty()) {
                throw new Exception("Trạng thái không được để trống");
            }

            // Tạo đối tượng Promotion
            Promotion promotion = new Promotion(name.trim(), discountPercent, startDate, endDate, status);

            // Lưu vào database
            if (promotionDAO.createPromotion(promotion)) {
                request.setAttribute("message", "Tạo chương trình khuyến mãi '" + name + "' thành công!");
            } else {
                request.setAttribute("error", "Tạo chương trình khuyến mãi thất bại!");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Phần trăm giảm giá phải là số!");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Định dạng ngày không hợp lệ!");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        listPromotions(request, response);
    }

    // Cập nhật chương trình khuyến mãi
    private void updatePromotion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String promoIDStr = request.getParameter("promoID");
            String name = request.getParameter("name");
            String discountStr = request.getParameter("discountPercent");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String status = request.getParameter("status");

            // Validation
            if (promoIDStr == null || promoIDStr.trim().isEmpty()) {
                throw new Exception("ID chương trình không hợp lệ");
            }

            int promoID = Integer.parseInt(promoIDStr);

            if (name == null || name.trim().isEmpty()) {
                throw new Exception("Tên chương trình không được để trống");
            }

            if (discountStr == null || discountStr.trim().isEmpty()) {
                throw new Exception("Phần trăm giảm giá không được để trống");
            }

            float discountPercent = Float.parseFloat(discountStr);
            if (discountPercent <= 0 || discountPercent > 100) {
                throw new Exception("Phần trăm giảm giá phải từ 1-100%");
            }

            if (startDateStr == null || endDateStr == null) {
                throw new Exception("Ngày bắt đầu và kết thúc không được để trống");
            }

            Date startDate = Date.valueOf(startDateStr);
            Date endDate = Date.valueOf(endDateStr);

            if (startDate.after(endDate)) {
                throw new Exception("Ngày bắt đầu phải trước ngày kết thúc");
            }

            if (status == null || status.trim().isEmpty()) {
                throw new Exception("Trạng thái không được để trống");
            }

            // Tạo đối tượng Promotion
            Promotion promotion = new Promotion(promoID, name.trim(), discountPercent, startDate, endDate, status);

            // Cập nhật database
            if (promotionDAO.updatePromotion(promotion)) {
                request.setAttribute("message", "Cập nhật chương trình khuyến mãi '" + name + "' thành công!");
            } else {
                request.setAttribute("error", "Cập nhật chương trình khuyến mãi thất bại!");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID hoặc phần trăm giảm giá không hợp lệ!");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Định dạng ngày không hợp lệ!");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        listPromotions(request, response);
    }

    // Xóa chương trình khuyến mãi
    private void deletePromotion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String promoIDStr = request.getParameter("promoID");
            String forceDelete = request.getParameter("forceDelete"); // Tham số xác nhận xóa cưỡng bức

            if (promoIDStr == null || promoIDStr.trim().isEmpty()) {
                throw new Exception("ID chương trình không hợp lệ");
            }

            int promoID = Integer.parseInt(promoIDStr);

            // Lấy thông tin chương trình trước khi xóa
            Promotion promotion = promotionDAO.getPromotionById(promoID);
            if (promotion == null) {
                throw new Exception("Không tìm thấy chương trình khuyến mãi");
            }

            // Kiểm tra xem chương trình có đang được sử dụng không
            boolean inUse = promotionDAO.isPromotionInUse(promoID);
            int productCount = promotionDAO.getProductCountByPromotion(promoID);

            if (inUse && !"true".equals(forceDelete)) {
                // Nếu đang được sử dụng và chưa xác nhận xóa cưỡng bức
                request.setAttribute("error",
                        "Chương trình khuyến mãi '" + promotion.getName() + "' đang được sử dụng bởi "
                        + productCount + " sản phẩm. "
                        + "<a href='PromotionController?action=delete&promoID=" + promoID + "&forceDelete=true' "
                        + "class='btn btn-sm btn-danger ms-2' "
                        + "onclick='return confirm(\"Bạn có chắc chắn muốn xóa chương trình này và tất cả sản phẩm liên quan?\")'>Xóa cưỡng bức</a>");
            } else {
                // Xóa chương trình (và tất cả sản phẩm liên quan nếu có)
                if (promotionDAO.deletePromotion(promoID)) {
                    if (inUse) {
                        request.setAttribute("message",
                                "Xóa chương trình khuyến mãi '" + promotion.getName() + "' và "
                                + productCount + " sản phẩm liên quan thành công!");
                    } else {
                        request.setAttribute("message",
                                "Xóa chương trình khuyến mãi '" + promotion.getName() + "' thành công!");
                    }
                } else {
                    request.setAttribute("error", "Xóa chương trình khuyến mãi thất bại!");
                }
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID chương trình không hợp lệ!");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        listPromotions(request, response);
    }

    // Gắn sản phẩm vào chương trình khuyến mãi
    private void addProductToPromotion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String promoIDStr = request.getParameter("promoID");
            String productIDStr = request.getParameter("productID");

            if (promoIDStr == null || promoIDStr.trim().isEmpty()) {
                throw new Exception("Vui lòng chọn chương trình khuyến mãi");
            }

            if (productIDStr == null || productIDStr.trim().isEmpty()) {
                throw new Exception("Vui lòng chọn sản phẩm");
            }

            int promoID = Integer.parseInt(promoIDStr);
            int productID = Integer.parseInt(productIDStr);

            // Kiểm tra chương trình khuyến mãi có tồn tại không
            Promotion promotion = promotionDAO.getPromotionById(promoID);
            if (promotion == null) {
                throw new Exception("Chương trình khuyến mãi không tồn tại");
            }

            // Gắn sản phẩm vào chương trình
            if (promotionDAO.addProductToPromotion(promoID, productID)) {
                request.setAttribute("message", "Gắn sản phẩm vào chương trình '" + promotion.getName() + "' thành công!");
            } else {
                request.setAttribute("error", "Gắn sản phẩm thất bại! Có thể sản phẩm đã có trong chương trình này.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID chương trình hoặc sản phẩm không hợp lệ!");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        listPromotions(request, response);
    }

    // Gỡ sản phẩm khỏi chương trình khuyến mãi
    private void removeProductFromPromotion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String promoIDStr = request.getParameter("promoID");
            String productIDStr = request.getParameter("productID");

            if (promoIDStr == null || promoIDStr.trim().isEmpty()) {
                throw new Exception("ID chương trình không hợp lệ");
            }

            if (productIDStr == null || productIDStr.trim().isEmpty()) {
                throw new Exception("ID sản phẩm không hợp lệ");
            }

            int promoID = Integer.parseInt(promoIDStr);
            int productID = Integer.parseInt(productIDStr);

            // Gỡ sản phẩm khỏi chương trình
            if (promotionDAO.removeProductFromPromotion(promoID, productID)) {
                request.setAttribute("message", "Gỡ sản phẩm khỏi chương trình khuyến mãi thành công!");
            } else {
                request.setAttribute("error", "Gỡ sản phẩm thất bại!");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID chương trình hoặc sản phẩm không hợp lệ!");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        listPromotions(request, response);
    }
}
