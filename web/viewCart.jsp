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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !user.getRoleID().equalsIgnoreCase("bu")) {
        response.sendRedirect("checkAuthorized.jsp");
        return;
    }

    CartDAO cartDAO = new CartDAO();
    ProductDAO productDAO = new ProductDAO();

    List<Cart> cartList = cartDAO.getCartByUserID(user.getUserID());
    double total = 0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Cart</title>
</head>
<body>
    <h2>Your Cart - <%= user.getFullName()%></h2>

    <form action="PrepareInvoiceController" method="post">
        <table border="1" cellpadding="10">
            <tr>
                <th>Select</th>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total</th>
            </tr>
            <%
                for (Cart c : cartList) {
                    Product p = productDAO.getProductByID(c.getProductID());
                    double amount = p.getPrice() * c.getQuantity();
            %>
            <tr>
                <td>
                    <input type="checkbox" name="selectedCartID" value="<%= c.getCartID() %>">
                </td>
                <td class="name"><%= p.getName() %></td>
                <td class="price"><%= p.getPrice() %></td>
                <td class="quantity"><%= c.getQuantity() %></td>
                <td class="amount"><%= amount %></td>
            </tr>
            <% } %>
        </table>

        <br>
        <input type="submit" value="Buy">
    </form>
</body>
</html>


