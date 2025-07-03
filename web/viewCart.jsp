<%-- 
    Document   : viewCart
    Created on : Jun 18, 2025, 4:33:36 PM
    Author     : Log
--%>

<%@ page import="java.util.List" %>
<%@ page import="model.Cart" %>
<%@ page import="dao.CartDAO" %>
<%@ page import="model.Product" %>
<%@ page import="dao.ProductDAO" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null && !user.getRoleID().equalsIgnoreCase("bu") && !user.getRoleID().equalsIgnoreCase("se")) {
        response.sendRedirect("checkAuthorized.jsp");
        return;
    }

    CartDAO cartDAO = new CartDAO();
    ProductDAO productDAO = new ProductDAO();
    List<Cart> cartList = cartDAO.getCartByUserID(user.getUserID());
    double total = 0;
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Cart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #ff6ec4, #7873f5);
            min-height: 100vh;
        }
        .card {
            background-color: white;
        }
        .total-box {
            font-size: 1.2rem;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="container my-5">
    <div class="card shadow-sm p-4 rounded-4">
        <h3 class="mb-4 text-center">🛒 Your Cart - <%= user.getFullName() %></h3>

        <form action="PrepareInvoiceController" method="post">
            <table class="table table-bordered table-hover align-middle">
                <thead class="table-warning">
                    <tr>
                        <th>Select</th>
                        <th>Product</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Cart c : cartList) {
                            Product p = productDAO.getProductByID(c.getProductID());
                            double amount = p.getPrice() * c.getQuantity();
                            total += amount;
                    %>
                        <tr>
                            <td class="text-center">
                                <input type="checkbox" name="selectedCartID" value="<%= c.getCartID() %>">
                            </td>
                            <td><%= p.getName() %></td>
                            <td><%= String.format("%.2f", p.getPrice()) %></td>
                            <td><%= c.getQuantity() %></td>
                            <td><%= String.format("%.2f", amount) %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <!-- Tổng tiền -->
            <div class="d-flex justify-content-between total-box">
                <span>Total Amount:</span>
                <span><%= String.format("%.2f", total) %> đ</span>
            </div>

            <div class="mt-3 text-end">
                <button type="submit" class="btn btn-success">Buy Selected Items</button>
            </div>
        </form>

        <div class="mt-3">
            <a href="ProductController?action=search" class="btn btn-secondary">Back to Product List</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>



