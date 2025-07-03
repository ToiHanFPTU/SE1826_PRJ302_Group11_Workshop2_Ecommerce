<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User"%>
<%@page import="model.Promotion"%>
<%@page import="model.ProductPromotion"%>
<%@page import="java.util.List"%>
<%
    User userLogin = (User) session.getAttribute("user");
    if (userLogin == null || !"AD".equalsIgnoreCase(userLogin.getRoleID())) {
        response.sendRedirect("checkAuthorized.jsp");
        return;
    }
    
    // Lấy data từ request attributes (được set bởi controller)
    List<Promotion> promotions = (List<Promotion>) request.getAttribute("promotions");
    List<ProductPromotion> productsOnSale = (List<ProductPromotion>) request.getAttribute("productsOnSale");
    
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
    
    // Nếu chưa có data, redirect đến controller để load data
    if (promotions == null) {
        response.sendRedirect("PromotionController?action=list");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manage Promotions</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                min-height: 100vh;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }
            .main-container {
                background: rgba(255, 255, 255, 0.95);
                border-radius: 20px;
                box-shadow: 0 20px 40px rgba(0,0,0,0.1);
                padding: 30px;
                margin: 20px auto;
                max-width: 1200px;
            }
            .nav-tabs .nav-link {
                border-radius: 15px 15px 0 0;
                font-weight: 600;
                color: #667eea;
            }
            .nav-tabs .nav-link.active {
                background: linear-gradient(135deg, #667eea, #764ba2);
                color: white;
                border: none;
            }
            .btn-gradient {
                background: linear-gradient(135deg, #667eea, #764ba2);
                border: none;
                border-radius: 25px;
                padding: 10px 25px;
                color: white;
                font-weight: 600;
                transition: all 0.3s ease;
            }
            .btn-gradient:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 20px rgba(0,0,0,0.2);
                color: white;
            }
            .table th {
                background: linear-gradient(135deg, #667eea, #764ba2);
                color: white;
                border: none;
                font-weight: 600;
            }
            .card {
                border: none;
                border-radius: 15px;
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            }
            .form-control, .form-select {
                border-radius: 10px;
                border: 2px solid #e9ecef;
                transition: all 0.3s ease;
            }
            .form-control:focus, .form-select:focus {
                border-color: #667eea;
                box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
            }
            .status-badge {
                padding: 5px 15px;
                border-radius: 20px;
                font-size: 0.8em;
                font-weight: 600;
            }
            .status-active {
                background: #d4edda;
                color: #155724;
            }
            .status-inactive {
                background: #f8d7da;
                color: #721c24;
            }
            .status-upcoming {
                background: #fff3cd;
                color: #856404;
            }
            .alert {
                border-radius: 15px;
                border: none;
            }
        </style>
    </head>
    <body>

        <div class="container-fluid">
            <div class="main-container">
                <!-- Header -->
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2><i class="fas fa-tags me-2"></i>Quản Lý Khuyến Mãi</h2>
                    <a href="adminPage.jsp" class="btn btn-outline-secondary">
                        <i class="fas fa-arrow-left me-2"></i>Quay lại
                    </a>
                </div>

                <!-- Messages -->
                <% if (message != null) { %>
                <div class="alert alert-success alert-dismissible fade show">
                    <i class="fas fa-check-circle me-2"></i><%= message %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <% } %>
                <% if (error != null) { %>
                <div class="alert alert-danger alert-dismissible fade show">
                    <i class="fas fa-exclamation-circle me-2"></i><%= error %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <% } %>

                <!-- Navigation Tabs -->
                <ul class="nav nav-tabs mb-4" id="promotionTabs" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="list-tab" data-bs-toggle="tab" data-bs-target="#list-pane" type="button">
                            <i class="fas fa-list me-2"></i>Danh Sách Khuyến Mãi
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="create-tab" data-bs-toggle="tab" data-bs-target="#create-pane" type="button">
                            <i class="fas fa-plus me-2"></i>Tạo Khuyến Mãi
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="products-tab" data-bs-toggle="tab" data-bs-target="#products-pane" type="button">
                            <i class="fas fa-shopping-cart me-2"></i>Sản Phẩm Giảm Giá
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="assign-tab" data-bs-toggle="tab" data-bs-target="#assign-pane" type="button">
                            <i class="fas fa-link me-2"></i>Gắn Sản Phẩm
                        </button>
                    </li>
                </ul>

                <!-- Tab Content -->
                <div class="tab-content" id="promotionTabsContent">

                    <!-- DANH SÁCH KHUYẾN MÃI -->
                    <div class="tab-pane fade show active" id="list-pane">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title mb-3">
                                    <i class="fas fa-list me-2"></i>Danh Sách Chương Trình Khuyến Mãi
                                </h5>

                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Tên Chương Trình</th>
                                                <th>Giảm Giá (%)</th>
                                                <th>Ngày Bắt Đầu</th>
                                                <th>Ngày Kết Thúc</th>
                                                <th>Trạng Thái</th>
                                                <th>Thao Tác</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <% if (promotions != null && !promotions.isEmpty()) { %>
                                            <% for (Promotion promo : promotions) { 
                                                // Kiểm tra xem chương trình có đang được sử dụng không
                                                int productCount = new dao.PromotionDAO().getProductCountByPromotion(promo.getPromoID());
                                            %>
                                            <tr>
                                                <td><%= promo.getPromoID() %></td>
                                                <td>
                                                    <strong><%= promo.getName() %></strong>
                                                    <% if (productCount > 0) { %>
                                                    <br><small class="text-muted">
                                                        <i class="fas fa-link me-1"></i><%= productCount %> sản phẩm đang sử dụng
                                                    </small>
                                                    <% } %>
                                                </td>
                                                <td><span class="badge bg-success"><%= promo.getDiscountPercent() %>%</span></td>
                                                <td><%= promo.getStartDate() %></td>
                                                <td><%= promo.getEndDate() %></td>
                                                <td>
                                                    <span class="status-badge status-<%= promo.getStatus().toLowerCase() %>">
                                                        <%= promo.getStatus() %>
                                                    </span>
                                                </td>
                                                <td>
                                                    <button class="btn btn-sm btn-outline-primary me-1" 
                                                            onclick="editPromotion(<%= promo.getPromoID() %>, '<%= promo.getName().replace("'", "\\'") %>', <%= promo.getDiscountPercent() %>, '<%= promo.getStartDate() %>', '<%= promo.getEndDate() %>', '<%= promo.getStatus() %>')">
                                                        <i class="fas fa-edit"></i>
                                                    </button>
                                                    <% if (productCount > 0) { %>
                                                    <button class="btn btn-sm btn-outline-warning me-1" 
                                                            onclick="showDeleteWarning(<%= promo.getPromoID() %>, '<%= promo.getName().replace("'", "\\'") %>', <%= productCount %>)"
                                                            title="Chương trình đang được sử dụng">
                                                        <i class="fas fa-exclamation-triangle"></i>
                                                    </button>
                                                    <% } else { %>
                                                    <button class="btn btn-sm btn-outline-danger" 
                                                            onclick="deletePromotion(<%= promo.getPromoID() %>, '<%= promo.getName().replace("'", "\\'") %>')">
                                                        <i class="fas fa-trash"></i>
                                                    </button>
                                                    <% } %>
                                                </td>
                                            </tr>
                                            <% } %>
                                            <% } else { %>
                                            <tr>
                                                <td colspan="7" class="text-center">Chưa có chương trình khuyến mãi nào</td>
                                            </tr>
                                            <% } %>
                                        </tbody>

                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- TẠO KHUYẾN MÃI -->
                    <div class="tab-pane fade" id="create-pane">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title mb-3">
                                    <i class="fas fa-plus me-2"></i>Tạo Chương Trình Khuyến Mãi Mới
                                </h5>

                                <form action="PromotionController" method="post" id="createForm">
                                    <input type="hidden" name="action" value="create">

                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Tên Chương Trình *</label>
                                            <input type="text" class="form-control" name="name" required maxlength="100">
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Phần Trăm Giảm Giá (%) *</label>
                                            <input type="number" class="form-control" name="discountPercent" min="1" max="100" step="0.1" required>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Ngày Bắt Đầu *</label>
                                            <input type="date" class="form-control" name="startDate" required>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Ngày Kết Thúc *</label>
                                            <input type="date" class="form-control" name="endDate" required>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Trạng Thái *</label>
                                        <select class="form-select" name="status" required>
                                            <option value="">Chọn trạng thái</option>
                                            <option value="Active">Đang hoạt động</option>
                                            <option value="Inactive">Tạm dừng</option>
                                            <option value="Upcoming">Sắp diễn ra</option>
                                        </select>
                                    </div>

                                    <button type="submit" class="btn btn-gradient">
                                        <i class="fas fa-save me-2"></i>Tạo Chương Trình
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- SẢN PHẨM GIẢM GIÁ -->
                    <div class="tab-pane fade" id="products-pane">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title mb-3">
                                    <i class="fas fa-shopping-cart me-2"></i>Sản Phẩm Đang Giảm Giá
                                </h5>

                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>Sản Phẩm</th>
                                                <th>Chương Trình</th>
                                                <th>Giảm Giá</th>
                                                <th>Ngày Gắn</th>
                                                <th>Thao Tác</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% if (productsOnSale != null && !productsOnSale.isEmpty()) { %>
                                            <% for (ProductPromotion pp : productsOnSale) { %>
                                            <tr>
                                                <td><strong><%= pp.getProductName() %></strong></td>
                                                <td><%= pp.getPromoName() %></td>
                                                <td><span class="badge bg-danger"><%= pp.getDiscountPercent() %>%</span></td>
                                                <td><%= pp.getCreatedDate() %></td>
                                                <td>
                                                    <button class="btn btn-sm btn-outline-danger" 
                                                            onclick="removeProductFromPromotion(<%= pp.getPromoID() %>, <%= pp.getProductID() %>, '<%= pp.getProductName().replace("'", "\\'") %>')">
                                                        <i class="fas fa-unlink me-1"></i>Gỡ bỏ
                                                    </button>
                                                </td>
                                            </tr>
                                            <% } %>
                                            <% } else { %>
                                            <tr>
                                                <td colspan="5" class="text-center">Chưa có sản phẩm nào đang giảm giá</td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- GẮN SẢN PHẨM -->
                    <div class="tab-pane fade" id="assign-pane">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title mb-3">
                                    <i class="fas fa-link me-2"></i>Gắn Sản Phẩm Với Chương Trình Khuyến Mãi
                                </h5>

                                <form action="PromotionController" method="post" id="assignForm">
                                    <input type="hidden" name="action" value="addProduct">

                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Chương Trình Khuyến Mãi *</label>
                                            <select class="form-select" name="promoID" required>
                                                <option value="">Chọn chương trình</option>
                                                <% if (promotions != null) { %>
                                                <% for (Promotion promo : promotions) { %>
                                                <option value="<%= promo.getPromoID() %>">
                                                    <%= promo.getName() %> (<%= promo.getDiscountPercent() %>% - <%= promo.getStatus() %>)
                                                </option>
                                                <% } %>
                                                <% } %>
                                            </select>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Sản Phẩm *</label>
                                            <select class="form-select" name="productID" required>
                                                <option value="">Chọn sản phẩm</option>
                                                <!-- Danh sách sản phẩm mẫu - trong thực tế sẽ load từ database -->
                                                <option value="1">iPhone 15 Pro Max</option>
                                                <option value="2">MacBook Pro M3</option>
                                                <option value="3">Nồi chiên không dầu Philips</option>
                                                <option value="4">Máy giặt Samsung</option>
                                                <option value="5">Tủ lạnh LG</option>
                                                <option value="6">Smart TV Sony</option>
                                                <option value="7">Laptop Dell XPS</option>
                                                <option value="8">Máy lọc nước Kangaroo</option>
                                                <option value="9">Điều hòa Daikin</option>
                                                <option value="10">Bàn ủi hơi nước Tefal</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="alert alert-info">
                                        <i class="fas fa-info-circle me-2"></i>
                                        <strong>Lưu ý:</strong> Chỉ có thể gắn sản phẩm vào chương trình khuyến mãi đang "Active" hoặc "Upcoming".
                                    </div>

                                    <button type="submit" class="btn btn-gradient">
                                        <i class="fas fa-link me-2"></i>Gắn Sản Phẩm
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Edit Modal -->
        <div class="modal fade" id="editModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">
                            <i class="fas fa-edit me-2"></i>Chỉnh Sửa Chương Trình Khuyến Mãi
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <form action="PromotionController" method="post" id="editForm">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="promoID" id="editPromoID">

                        <div class="modal-body">
                            <div class="mb-3">
                                <label class="form-label">Tên Chương Trình *</label>
                                <input type="text" class="form-control" name="name" id="editName" required maxlength="100">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Phần Trăm Giảm Giá (%) *</label>
                                <input type="number" class="form-control" name="discountPercent" id="editDiscount" min="1" max="100" step="0.1" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Ngày Bắt Đầu *</label>
                                <input type="date" class="form-control" name="startDate" id="editStartDate" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Ngày Kết Thúc *</label>
                                <input type="date" class="form-control" name="endDate" id="editEndDate" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Trạng Thái *</label>
                                <select class="form-select" name="status" id="editStatus" required>
                                    <option value="Active">Đang hoạt động</option>
                                    <option value="Inactive">Tạm dừng</option>
                                    <option value="Upcoming">Sắp diễn ra</option>
                                </select>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                <i class="fas fa-times me-2"></i>Hủy
                            </button>
                            <button type="submit" class="btn btn-gradient">
                                <i class="fas fa-save me-2"></i>Cập Nhật
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Delete Confirmation Modal -->
        <div class="modal fade" id="deleteModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title text-danger">
                            <i class="fas fa-exclamation-triangle me-2"></i>Xác Nhận Xóa
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <p>Bạn có chắc chắn muốn xóa chương trình khuyến mãi <strong id="deletePromoName"></strong>?</p>
                        <div class="alert alert-warning">
                            <i class="fas fa-warning me-2"></i>
                            <strong>Cảnh báo:</strong> Hành động này không thể hoàn tác!
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times me-2"></i>Hủy
                        </button>
                        <a href="#" class="btn btn-danger" id="confirmDeleteBtn">
                            <i class="fas fa-trash me-2"></i>Xóa
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                                                // Edit Promotion Function
                                                                function editPromotion(id, name, discount, startDate, endDate, status) {
                                                                    document.getElementById('editPromoID').value = id;
                                                                    document.getElementById('editName').value = name;
                                                                    document.getElementById('editDiscount').value = discount;
                                                                    document.getElementById('editStartDate').value = startDate;
                                                                    document.getElementById('editEndDate').value = endDate;
                                                                    document.getElementById('editStatus').value = status;

                                                                    var editModal = new bootstrap.Modal(document.getElementById('editModal'));
                                                                    editModal.show();
                                                                }

                                                                // Delete Promotion Function
                                                                function deletePromotion(id, name) {
                                                                    document.getElementById('deletePromoName').textContent = name;
                                                                    document.getElementById('confirmDeleteBtn').href = 'PromotionController?action=delete&promoID=' + id;

                                                                    var deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
                                                                    deleteModal.show();
                                                                }

                                                                // Remove Product from Promotion Function
                                                                function removeProductFromPromotion(promoID, productID, productName) {
                                                                    if (confirm('Bạn có chắc chắn muốn gỡ sản phẩm "' + productName + '" khỏi chương trình khuyến mãi?')) {
                                                                        window.location.href = 'PromotionController?action=removeProduct&promoID=' + promoID + '&productID=' + productID;
                                                                    }
                                                                }

                                                                // Form Validation
                                                                document.getElementById('createForm').addEventListener('submit', function (e) {
                                                                    var startDate = new Date(document.querySelector('#createForm input[name="startDate"]').value);
                                                                    var endDate = new Date(document.querySelector('#createForm input[name="endDate"]').value);
                                                                    var today = new Date();
                                                                    today.setHours(0, 0, 0, 0);

                                                                    if (startDate >= endDate) {
                                                                        e.preventDefault();
                                                                        alert('Ngày kết thúc phải sau ngày bắt đầu!');
                                                                        return false;
                                                                    }

                                                                    if (startDate < today) {
                                                                        if (!confirm('Ngày bắt đầu đã qua. Bạn có chắc chắn muốn tạo chương trình này?')) {
                                                                            e.preventDefault();
                                                                            return false;
                                                                        }
                                                                    }
                                                                });

                                                                // Edit Form Validation
                                                                document.getElementById('editForm').addEventListener('submit', function (e) {
                                                                    var startDate = new Date(document.getElementById('editStartDate').value);
                                                                    var endDate = new Date(document.getElementById('editEndDate').value);

                                                                    if (startDate >= endDate) {
                                                                        e.preventDefault();
                                                                        alert('Ngày kết thúc phải sau ngày bắt đầu!');
                                                                        return false;
                                                                    }
                                                                });

                                                                // Assign Form Validation
                                                                document.getElementById('assignForm').addEventListener('submit', function (e) {
                                                                    var promoID = document.querySelector('#assignForm select[name="promoID"]').value;
                                                                    var productID = document.querySelector('#assignForm select[name="productID"]').value;

                                                                    if (!promoID || !productID) {
                                                                        e.preventDefault();
                                                                        alert('Vui lòng chọn đầy đủ chương trình khuyến mãi và sản phẩm!');
                                                                        return false;
                                                                    }
                                                                });

                                                                // Auto-dismiss alerts after 5 seconds
                                                                setTimeout(function () {
                                                                    var alerts = document.querySelectorAll('.alert');
                                                                    alerts.forEach(function (alert) {
                                                                        var bsAlert = new bootstrap.Alert(alert);
                                                                        bsAlert.close();
                                                                    });
                                                                }, 5000);

                                                                // Set minimum date to today for date inputs
                                                                document.addEventListener('DOMContentLoaded', function () {
                                                                    var today = new Date().toISOString().split('T')[0];
                                                                    var dateInputs = document.querySelectorAll('input[type="date"]');
                                                                    dateInputs.forEach(function (input) {
                                                                        if (input.name === 'startDate' && input.closest('#createForm')) {
                                                                            input.min = today;
                                                                        }
                                                                    });
                                                                });

                                                                // Dynamic end date validation
                                                                document.querySelector('#createForm input[name="startDate"]').addEventListener('change', function () {
                                                                    var startDate = this.value;
                                                                    var endDateInput = document.querySelector('#createForm input[name="endDate"]');
                                                                    endDateInput.min = startDate;

                                                                    if (endDateInput.value && endDateInput.value <= startDate) {
                                                                        endDateInput.value = '';
                                                                    }
                                                                });

                                                                document.getElementById('editStartDate').addEventListener('change', function () {
                                                                    var startDate = this.value;
                                                                    var endDateInput = document.getElementById('editEndDate');
                                                                    endDateInput.min = startDate;

                                                                    if (endDateInput.value && endDateInput.value <= startDate) {
                                                                        endDateInput.value = '';
                                                                    }
                                                                });
        </script>

    </body>
</html>                        