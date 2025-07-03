<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Product Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #ff6ec4, #7873f5);
            min-height: 100vh;
        }
        .card {
            background-color: white;
        }
        .table th, .table td {
            vertical-align: middle;
        }
    </style>
</head>
<body>
    <div class="container my-4">
        <!-- Welcome Header -->
        <%
            User user = (User) session.getAttribute("user"); 
            if (user == null || !user.getRoleID().equalsIgnoreCase("se")) {
                response.sendRedirect("checkAuthorized.jsp");
                return; 
            } 
        %>
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="text-white">Welcome, <c:out value="${user.fullName}"/></h2>
            <a href="${pageContext.request.contextPath}/LogoutController" class="btn btn-outline-light">Logout</a>
        </div>

        <!-- Search Zone -->
        <div class="card shadow-sm p-3 mb-4 rounded-4">
            <form action="SearchProductController" method="post" class="row g-2 align-items-end">
                <div class="col-md-4">
                    <label class="form-label">Product Name</label>
                    <input type="text" name="searchBox" placeholder="Search..." class="form-control" />
                </div>

                <div class="col-md-2">
                    <label class="form-label">Price From</label>
                    <input type="number" name="minPrice" value="<c:out value='${minPrice}'/>" class="form-control" placeholder="Min Price">
                </div>

                <div class="col-md-2">
                    <label class="form-label">Price To</label>
                    <input type="number" name="maxPrice" value="<c:out value='${maxPrice}'/>" class="form-control" placeholder="Max Price">
                </div>

                <div class="col-md-2">
                    <label class="form-label">Order By</label>
                    <select name="orderByPrice" class="form-select">
                        <option value="ascending" ${orderByPrice == 'ascending' ? 'selected' : ''}>Ascending</option>
                        <option value="descending" ${orderByPrice == 'descending' ? 'selected' : ''}>Descending</option>
                    </select>
                </div>

                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100">Search</button>
                </div>
            </form>
        </div>

        <!-- Add Product Button -->
        <div class="mb-3 text-end">
            <a href="CreateProductController" class="btn btn-success">Add New Product</a>
        </div>

        <!-- Product Table -->
        <div class="card shadow-sm rounded-4">
            <div class="card-body">
                <table class="table table-bordered table-hover align-middle">
                    <thead class="table-secondary">
                        <tr>
                            <th>Product ID</th>
                            <th>Name</th>
                            <th>Category ID</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Seller ID</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${productList}" var="product">
                            <tr>
                                <form action="UpdateProductController" method="post">
                                    <td>
                                        <input type="number" name="productID" value="${product.productID}" class="form-control" readonly>
                                    </td>
                                    <td>
                                        <input type="text" name="name" value="${product.name}" class="form-control" required>
                                    </td>
                                    <td>
                                        <input type="number" name="categoryID" value="${product.categoryID}" class="form-control" readonly>
                                    </td>
                                    <td>
                                        <input type="number" step="0.01" name="price" value="${product.price}" class="form-control">
                                    </td>
                                    <td>
                                        <input type="number" name="quantity" value="${product.quantity}" class="form-control" required>
                                    </td>
                                    <td>
                                        <input type="text" name="sellerID" value="${product.sellerID}" class="form-control" readonly>
                                    </td>
                                    <td>
                                        <select name="status" class="form-select">
                                            <option value="available" ${product.status == 'available' ? 'selected' : ''}>Available</option>
                                            <option value="unavailable" ${product.status == 'unavailable' ? 'selected' : ''}>Unavailable</option>
                                        </select>
                                    </td>
                                    <td class="d-flex gap-1">
                                        <button type="submit" name="action" value="update" class="btn btn-warning btn-sm">Update</button>
                                </form>
                                <form action="DeleteProductController" method="post" onsubmit="return confirm('Are you sure you want to delete this product?');">
                                    <input type="hidden" name="productID" value="${product.productID}">
                                    <button type="submit" name="action" value="delete" class="btn btn-danger btn-sm">Delete</button>
                                </form>
                                    </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Success/Error Message -->
        <c:if test="${not empty msg}">
            <div class="alert alert-info mt-3">${msg}</div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        window.addEventListener('DOMContentLoaded', function () {
            const msg = "${msg}";
            if (msg) {
                alert(msg);
            }
        });
    </script>
</body>
</html>
