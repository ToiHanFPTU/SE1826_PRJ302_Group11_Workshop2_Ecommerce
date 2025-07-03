package controller.delivery;

import dao.DeliveryDAO;
import model.Delivery;
import model.Invoice;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import dao.InvoiceDAO;
@WebServlet(name = "DeliveryController", urlPatterns = {"/DeliveryController"})
public class DeliveryController extends HttpServlet {
    
    private DeliveryDAO deliveryDAO;
    private InvoiceDAO invoiceDAO;
    
    @Override
    public void init() throws ServletException {
        deliveryDAO = new DeliveryDAO();
        invoiceDAO = new InvoiceDAO();
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
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
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
                    listDeliveries(request, response);
                    break;
                case "search":
                    searchDeliveries(request, response);
                    break;
                case "updateStatus":
                    updateDeliveryStatus(request, response);
                    break;
                case "updateAddress":
                    updateDeliveryAddress(request, response);
                    break;
                case "create":
                    createDelivery(request, response);
                    break;
                default:
                    listDeliveries(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
            listDeliveries(request, response);
        }
    }
    
    private void listDeliveries(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Delivery> deliveries = deliveryDAO.getAllDeliveries();
        List<Invoice> invoicesWithoutDelivery = deliveryDAO.getInvoicesWithoutDelivery();
        
        request.setAttribute("deliveries", deliveries);
        request.setAttribute("invoicesWithoutDelivery", invoicesWithoutDelivery);
        request.getRequestDispatcher("managedeliveries.jsp").forward(request, response);
    }
    
    private void searchDeliveries(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String searchType = request.getParameter("searchType");
        String searchValue = request.getParameter("searchValue");
        List<Delivery> deliveries = null;
        
        if (searchValue == null || searchValue.trim().isEmpty()) {
            deliveries = deliveryDAO.getAllDeliveries();
            request.setAttribute("message", "Vui lòng nhập từ khóa tìm kiếm!");
        } else {
            if ("invoiceID".equals(searchType)) {
                try {
                    int invoiceID = Integer.parseInt(searchValue.trim());
                    deliveries = deliveryDAO.searchByInvoiceID(invoiceID);
                    if (deliveries.isEmpty()) {
                        request.setAttribute("message", "Không tìm thấy đơn giao hàng cho hóa đơn #" + invoiceID);
                    }
                } catch (NumberFormatException e) {
                    deliveries = deliveryDAO.getAllDeliveries();
                    request.setAttribute("error", "ID hóa đơn phải là số!");
                }
            } else if ("status".equals(searchType)) {
                deliveries = deliveryDAO.searchByStatus(searchValue.trim());
                if (deliveries.isEmpty()) {
                    request.setAttribute("message", "Không tìm thấy đơn giao hàng với trạng thái: " + searchValue);
                }
            }
        }
        
        List<Invoice> invoicesWithoutDelivery = deliveryDAO.getInvoicesWithoutDelivery();
        
        request.setAttribute("deliveries", deliveries);
        request.setAttribute("invoicesWithoutDelivery", invoicesWithoutDelivery);
        request.setAttribute("searchType", searchType);
        request.setAttribute("searchValue", searchValue);
        request.getRequestDispatcher("managedeliveries.jsp").forward(request, response);
    }
    
    private void updateDeliveryStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String deliveryIDStr = request.getParameter("deliveryID");
            String status = request.getParameter("status");
            int invoiceID = Integer.parseInt(request.getParameter("invoiceID"));
            
            if (deliveryIDStr == null || deliveryIDStr.trim().isEmpty()) {
                throw new Exception("ID đơn giao hàng không hợp lệ");
            }
            
            if (status == null || status.trim().isEmpty()) {
                throw new Exception("Trạng thái không được để trống");
            }
            
            int deliveryID = Integer.parseInt(deliveryIDStr);
            
            if (deliveryDAO.updateDeliveryStatus(deliveryID, status)) {
                invoiceDAO.updateInvoiceStatus(invoiceID, status);
                request.setAttribute("message", "Cập nhật trạng thái giao hàng thành công!");
            } else {
                request.setAttribute("error", "Cập nhật trạng thái giao hàng thất bại!");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID đơn giao hàng không hợp lệ!");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        
        listDeliveries(request, response);
    }
    
    private void updateDeliveryAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String deliveryIDStr = request.getParameter("deliveryID");
            String address = request.getParameter("address");
            
            if (deliveryIDStr == null || deliveryIDStr.trim().isEmpty()) {
                throw new Exception("ID đơn giao hàng không hợp lệ");
            }
            
            if (address == null || address.trim().isEmpty()) {
                throw new Exception("Địa chỉ không được để trống");
            }
            
            int deliveryID = Integer.parseInt(deliveryIDStr);
            
            if (deliveryDAO.updateDeliveryAddress(deliveryID, address.trim())) {
                request.setAttribute("message", "Cập nhật địa chỉ giao hàng thành công!");
            } else {
                request.setAttribute("error", "Cập nhật địa chỉ giao hàng thất bại!");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID đơn giao hàng không hợp lệ!");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        
        listDeliveries(request, response);
    }
    
    private void createDelivery(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String invoiceIDStr = request.getParameter("invoiceID");
            String address = request.getParameter("address");
            String deliveryDateStr = request.getParameter("deliveryDate");
            String status = request.getParameter("status");
            
            if (invoiceIDStr == null || invoiceIDStr.trim().isEmpty()) {
                throw new Exception("Vui lòng chọn hóa đơn");
            }
            
            if (address == null || address.trim().isEmpty()) {
                throw new Exception("Địa chỉ giao hàng không được để trống");
            }
            
            if (deliveryDateStr == null || deliveryDateStr.trim().isEmpty()) {
                throw new Exception("Ngày giao hàng không được để trống");
            }
            
            if (status == null || status.trim().isEmpty()) {
                throw new Exception("Trạng thái không được để trống");
            }
            
            int invoiceID = Integer.parseInt(invoiceIDStr);
            Date deliveryDate = Date.valueOf(deliveryDateStr);
            
            if (deliveryDAO.createDelivery(invoiceID, address.trim(), deliveryDate, status)) {
                invoiceDAO.updateInvoiceStatus(invoiceID, status);
                request.setAttribute("message", "Tạo đơn giao hàng thành công!");
            } else {
                request.setAttribute("error", "Tạo đơn giao hàng thất bại!");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu nhập vào không hợp lệ!");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Định dạng ngày không hợp lệ!");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        
        listDeliveries(request, response);
    }
}
