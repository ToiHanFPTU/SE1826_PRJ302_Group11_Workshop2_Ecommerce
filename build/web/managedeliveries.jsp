<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Delivery"%>
<%@page import="model.Invoice"%>
<%@page import="model.User"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
    User userLogin = (User) session.getAttribute("user");
    if (userLogin == null || !"DL".equalsIgnoreCase(userLogin.getRoleID())) {
        response.sendRedirect("checkAuthorized.jsp");
        return;
    }

    List<Delivery> deliveries = (List<Delivery>) request.getAttribute("deliveries");
    List<Invoice> invoicesWithoutDelivery = (List<Invoice>) request.getAttribute("invoicesWithoutDelivery");
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
    String searchType = (String) request.getAttribute("searchType");
    String searchValue = (String) request.getAttribute("searchValue");
    
    DecimalFormat formatter = new DecimalFormat("#,###");
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý giao hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        .container-fluid {
            padding: 20px;
        }
        
        .card {
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            border: none;
            margin-bottom: 20px;
        }
        
        .card-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px 15px 0 0 !important;
            padding: 20px;
        }
        
        .btn-gradient {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            color: white;
            border-radius: 25px;
            padding: 10px 20px;
            transition: all 0.3s ease;
        }
        
        .btn-gradient:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
            color: white;
        }
        
        .status-badge {
            padding: 8px 15px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
            text-transform: uppercase;
        }
        
        .status-pending {
            background-color: #fff3cd;
            color: #856404;
            border: 1px solid #ffeaa7;
        }
        
        .status-delivering {
            background-color: #cce5ff;
            color: #004085;
            border: 1px solid #74b9ff;
        }
        
        .status-delivered {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #00b894;
        }
        
        .status-shipping {
            background-color: #e2e3e5;
            color: #495057;
            border: 1px solid #636c72;
        }
        
        .table {
            border-radius: 10px;
            overflow: hidden;
        }
        
        .table thead th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 12px;
            padding: 15px 10px;
        }
        
        .table tbody tr {
            transition: all 0.3s ease;
        }
        
        .table tbody tr:hover {
            background-color: #f8f9ff;
            transform: scale(1.01);
        }
        
        .table tbody td {
            padding: 15px 10px;
            vertical-align: middle;
            border-color: #e9ecef;
        }
        
        .search-form {
            background: white;
            padding: 20px;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        
        .modal-content {
            border-radius: 15px;
            border: none;
        }
        
        .modal-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px 15px 0 0;
        }
        
        .form-control, .form-select {
            border-radius: 10px;
            border: 2px solid #e9ecef;
            padding: 12px 15px;
            transition: all 0.3s ease;
        }
        
        .form-control:focus, .form-select:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        
        .alert {
            border-radius: 10px;
            border: none;
            padding: 15px 20px;
        }
        
        .delivery-info {
            background: #f8f9fa;
            padding: 10px;
            border-radius: 8px;
            margin: 5px 0;
        }
        
        .customer-info {
            font-size: 12px;
            color: #6c757d;
        }
        
        .amount-display {
            font-weight: bold;
            color: #28a745;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <!-- HEADER -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2 class="text-white mb-0">
                    <i class="fas fa-truck me-2"></i>Quản lý giao hàng
                </h2>
                <p class="text-white-50 mb-0">Theo dõi và quản lý tình trạng giao hàng</p>
            </div>
            <div>
                <button class="btn btn-light me-2" onclick="window.location.href='adminPage.jsp'">
                    <i class="fas fa-arrow-left me-1"></i>Quay lại
                </button>
                <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#createDeliveryModal">
                    <i class="fas fa-plus me-1"></i>Tạo đơn giao hàng
                </button>
            </div>
        </div>

        <!-- THÔNG BÁO -->
        <% if (message != null) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fas fa-check-circle me-2"></i><%= message %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

        <% if (error != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fas fa-exclamation-circle me-2"></i><%= error %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

        <!-- TÌM KIẾM -->
        <div class="search-form">
            <form method="post" action="DeliveryController">
                <input type="hidden" name="action" value="search">
                <div class="row align-items-end">
                    <div class="col-md-3">
                        <label class="form-label fw-bold">Tìm kiếm theo:</label>
                        <select name="searchType" class="form-select">
                            <option value="invoiceID" <%= "invoiceID".equals(searchType) ? "selected" : "" %>>
                                Mã hóa đơn
                            </option>
                            <option value="status" <%= "status".equals(searchType) ? "selected" : "" %>>
                                Trạng thái giao hàng
                            </option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Từ khóa:</label>
                        <input type="text" name="searchValue" class="form-control" 
                               placeholder="Nhập từ khóa tìm kiếm..." 
                               value="<%= searchValue != null ? searchValue : "" %>">
                    </div>
                    <div class="col-md-3">
                        <button type="submit" class="btn btn-gradient w-100">
                            <i class="fas fa-search me-1"></i>Tìm kiếm
                        </button>
                    </div>
                </div>
            </form>
        </div>

        <!-- DANH SÁCH GIAO HÀNG -->
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">
                    <i class="fas fa-list me-2"></i>Danh sách đơn giao hàng
                    <span class="badge bg-light text-dark ms-2">
                        <%= deliveries != null ? deliveries.size() : 0 %> đơn
                    </span>
                </h5>
            </div>
            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-hover mb-0">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Hóa đơn</th>
                                <th>Khách hàng</th>
                                <th>Địa chỉ giao hàng</th>
                                <th>Ngày giao</th>
                                <th>Trạng thái</th>
                                <th>Số tiền</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (deliveries != null && !deliveries.isEmpty()) { %>
                                <% for (Delivery delivery : deliveries) { %>
                                <tr>
                                    <td>
                                        <strong class="text-primary">#<%= delivery.getDeliveryID() %></strong>
                                    </td>
                                    <td>
                                        <div class="delivery-info">
                                            <strong>HD#<%= delivery.getInvoiceID() %></strong>
                                            <div class="customer-info">
                                                <i class="fas fa-calendar me-1"></i>
                                                <%= delivery.getInvoiceCreatedDate() != null ? 
                                                    dateFormatter.format(delivery.getInvoiceCreatedDate()) : "N/A" %>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div>
                                            <strong><%= delivery.getCustomerName() %></strong>
                                            <div class="customer-info">
                                                <i class="fas fa-phone me-1"></i><%= delivery.getCustomerPhone() %>
                                            </div>
                                            <div class="customer-info">
                                                <i class="fas fa-user me-1"></i><%= delivery.getUserID() %>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="text-truncate" style="max-width: 200px;" 
                                             title="<%= delivery.getAddress() %>">
                                            <i class="fas fa-map-marker-alt me-1 text-danger"></i>
                                            <%= delivery.getAddress() %>
                                        </div>
                                    </td>
                                    <td>
                                        <i class="fas fa-truck me-1"></i>
                                        <%= delivery.getDeliveryDate() != null ? 
                                            dateFormatter.format(delivery.getDeliveryDate()) : "Chưa xác định" %>
                                    </td>
                                    <td>
                                        <span class="status-badge status-<%= delivery.getStatus().toLowerCase() %>">
                                            <%= delivery.getStatus() %>
                                        </span>
                                    </td>
                                    <td>
                                        <span class="amount-display">
                                            <%= formatter.format(delivery.getTotalAmount()) %>đ
                                        </span>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <button class="btn btn-sm btn-outline-primary" 
                                                    onclick="editDeliveryStatus(<%= delivery.getDeliveryID() %>, '<%= delivery.getStatus() %>')"
                                                    title="Cập nhật trạng thái">
                                                <i class="fas fa-edit"></i>
                                            </button>
                                            <button class="btn btn-sm btn-outline-info" 
                                                    onclick="editDeliveryAddress(<%= delivery.getDeliveryID() %>, '<%= delivery.getAddress().replace("'", "\\'") %>')"
                                                    title="Cập nhật địa chỉ">
                                                <i class="fas fa-map-marker-alt"></i>
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                                <% } %>
                            <% } else { %>
                                <tr>
                                    <td colspan="8" class="text-center py-4">
                                        <div class="text-muted">
                                            <i class="fas fa-inbox fa-3x mb-3"></i>
                                            <h5>Chưa có đơn giao hàng nào</h5>
                                            <p>Hãy tạo đơn giao hàng mới để bắt đầu quản lý</p>
                                        </div>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- MODAL TẠO ĐỚN GIAO HÀNG -->
    <div class="modal fade" id="createDeliveryModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <i class="fas fa-plus me-2"></i>Tạo đơn giao hàng mới
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <form method="post" action="DeliveryController">
                    <input type="hidden" name="action" value="create">
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Chọn hóa đơn:</label>
                                <select name="invoiceID" class="form-select" required>
                                    <option value="">-- Chọn hóa đơn --</option>
                                    <% if (invoicesWithoutDelivery != null && !invoicesWithoutDelivery.isEmpty()) { %>
                                        <% for (Invoice invoice : invoicesWithoutDelivery) { %>
                                        <option value="<%= invoice.getInvoiceID() %>">
                                            HD#<%= invoice.getInvoiceID() %> - <%= invoice.getUserID() %> 
                                            (<%= formatter.format(invoice.getTotalAmount()) %>đ)
                                        </option>
                                        <% } %>
                                    <% } else { %>
                                        <option value="" disabled>Không có hóa đơn nào cần tạo đơn giao hàng</option>
                                    <% } %>
                                </select>
                                <small class="text-muted">Chỉ hiển thị hóa đơn đã thanh toán chưa có đơn giao hàng</small>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Trạng thái:</label>
                                <select name="status" class="form-select" required>
                                    <option value="">-- Chọn trạng thái --</option>
                                    <option value="Pending">Chờ xử lý</option>
                                    <option value="Delivering">Đang giao</option>
                                    <option value="Delivered">Đã giao</option>
                                    <option value="Shipping">Đang vận chuyển</option>
                                </select>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Ngày giao hàng:</label>
                                <input type="date" name="deliveryDate" class="form-control" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Địa chỉ giao hàng:</label>
                                <textarea name="address" class="form-control" rows="3" 
                                          placeholder="Nhập địa chỉ giao hàng đầy đủ..." required></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times me-1"></i>Hủy
                        </button>
                        <button type="submit" class="btn btn-gradient">
                            <i class="fas fa-save me-1"></i>Tạo đơn giao hàng
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- MODAL CẬP NHẬT TRẠNG THÁI -->
    <div class="modal fade" id="statusModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <i class="fas fa-edit me-2"></i>Cập nhật trạng thái giao hàng
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <form method="post" action="DeliveryController">
                    <input type="hidden" name="action" value="updateStatus">
                    <input type="hidden" name="deliveryID" id="statusDeliveryID">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label fw-bold">Trạng thái mới:</label>
                            <select name="status" id="statusSelect" class="form-select" required>
                                <option value="Pending">Chờ xử lý</option>
                                <option value="Delivering">Đang giao</option>
                                <option value="Delivered">Đã giao</option>
                                <option value="Shipping">Đang vận chuyển</option>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times me-1"></i>Hủy
                        </button>
                        <button type="submit" class="btn btn-gradient">
                            <i class="fas fa-save me-1"></i>Cập nhật
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- MODAL CẬP NHẬT ĐỊA CHỈ -->
    <div class="modal fade" id="addressModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <i class="fas fa-map-marker-alt me-2"></i>Cập nhật địa chỉ giao hàng
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <form method="post" action="DeliveryController">
                    <input type="hidden" name="action" value="updateAddress">
                    <input type="hidden" name="deliveryID" id="addressDeliveryID">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label fw-bold">Địa chỉ giao hàng:</label>
                            <textarea name="address" id="addressTextarea" class="form-control" rows="4" 
                                      placeholder="Nhập địa chỉ giao hàng đầy đủ..." required></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times me-1"></i>Hủy
                        </button>
                        <button type="submit" class="btn btn-gradient">
                            <i class="fas fa-save me-1"></i>Cập nhật
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Hàm mở modal cập nhật trạng thái
        function editDeliveryStatus(deliveryID, currentStatus) {
            document.getElementById('statusDeliveryID').value = deliveryID;
            document.getElementById('statusSelect').value = currentStatus;
            new bootstrap.Modal(document.getElementById('statusModal')).show();
        }

        // Hàm mở modal cập nhật địa chỉ
        function editDeliveryAddress(deliveryID, currentAddress) {
            document.getElementById('addressDeliveryID').value = deliveryID;
            document.getElementById('addressTextarea').value = currentAddress;
            new bootstrap.Modal(document.getElementById('addressModal')).show();
        }

        // Tự động ẩn thông báo sau 5 giây
        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);

        // Thiết lập ngày tối thiểu cho input date
        document.addEventListener('DOMContentLoaded', function() {
            const today = new Date().toISOString().split('T')[0];
            const dateInput = document.querySelector('input[name="deliveryDate"]');
            if (dateInput) {
                dateInput.min = today;
                dateInput.value = today;
            }
        });
    </script>
</body>
</html>
