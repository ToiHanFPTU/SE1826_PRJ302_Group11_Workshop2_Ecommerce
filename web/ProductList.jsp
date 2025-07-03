<%-- 
    Document   : productList
    Created on : Jun 18, 2025, 10:17:41 AM
    Author     : Log
--%>
<%-- 
    Document   : productList
    Created on : Jun 18, 2025, 10:17:41 AM
    Author     : Log
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User"%>
<%@ page import="dao.CartDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    User user = (User) session.getAttribute("user");
    if (user == null || (!user.getRoleID().equalsIgnoreCase("bu") && !user.getRoleID().equalsIgnoreCase("ad"))) {
        response.sendRedirect("checkAuthorized.jsp");
        return;
    }
    CartDAO cDAO = new CartDAO();
    int cartCount = cDAO.countCartItems(user.getUserID());
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #ff6ec4, #7873f5);
            min-height: 100vh;
        }
        .card {
            background-color: white;
        }
        .cart-count {
            position: absolute;
            top: -10px;
            right: -10px;
            background: red;
            color: white;
            border-radius: 50%;
            padding: 2px 6px;
            font-size: 12px;
        }
        .btn-view-order {
            background-color: white !important;
            color: black !important;
            border: 1px solid #ccc;
        }
        .btn-view-order:hover {
            background-color: #f0f0f0 !important;
        }
    </style>
</head>
<body>    
<div class="container my-4">
    <!-- Welcome + Cart + View Orders -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="text-white">Welcome, <c:out value="${user.fullName}"/></h3>
         <% if (user.getRoleID().equalsIgnoreCase("bu")) { %>
        <div>
            <a class="btn btn-view-order" href="SearchInvoiceController">View Orders</a>
        </div>
        <a href="viewCart.jsp" class="btn btn-light position-relative">
            <i class="fas fa-shopping-cart"></i> Cart
            <span class="cart-count"><%= cartCount %></span>
        </a>
    <% } %>
    </div>

    <!-- Search Form -->
    <div class="card shadow-sm p-3 mb-4 rounded-4">
        <form action="SearchProductController" method="post" class="row g-2 align-items-end">
            <div class="col-md-4">
                <input type="text" name="searchBox" placeholder="Search by name..." class="form-control">
            </div>

            <div class="col-md-2">
                <input type="number" name="minPrice" value="<c:out value='${minPrice}'/>" placeholder="Min Price" class="form-control">
            </div>

            <div class="col-md-2">
                <input type="number" name="maxPrice" value="<c:out value='${maxPrice}'/>" placeholder="Max Price" class="form-control">
            </div>

            <div class="col-md-2">
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

    <!-- Product Table -->
    <div class="card shadow-sm rounded-4">
        <div class="card-body">
            <table class="table table-bordered table-hover align-middle">
                <thead class="table-secondary">
                    <tr>
                        <th>Product ID</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${productList}" var="product">
                        <tr>
                            <td>
                                <input type="number" name="productID" value="${product.productID}" class="form-control" readonly />
                            </td>
                            <td>
                                <input type="text" name="name" value="${product.name}" class="form-control" required />
                            </td>
                            <td>
                                <input type="number" name="price" value="<fmt:formatNumber value='${product.price}' pattern='#.##'/>" class="form-control" />
                            </td>
                             <% if (user.getRoleID().equalsIgnoreCase("bu")) { %>
                            <td>
                                <form action="AddCartController" method="post" class="d-flex">
                                    <input type="hidden" name="productID" value="${product.productID}" />
                                    <input type="number" name="quantity" value="1" min="1" class="form-control me-2" style="width:80px;" required>
                                    <button type="submit" class="btn btn-success btn-sm">Add</button>
                                </form>
                            </td>
            <% } %>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>




