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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !user.getRoleID().equalsIgnoreCase("bu")) {
        response.sendRedirect("checkAuthorized.jsp");
        return;
    }

    CartDAO cartDAO = new CartDAO();
    ProductDAO productDAO = new ProductDAO();

    List<Cart> cartList = cartDAO.getCartByUserID(userID);
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

    <table border="1" cellpadding="10">
        <tr>
            <th>Product</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Total Amount</th>
            <th>Action</th>
        </tr>
        <%
            for (Cart c : cartList) {
                Product p = productDAO.getProductByID(c.getProductID());
                double amount = p.getPrice() * c.getQuantity();
                total += amount;
        %>
        <tr>
            <td><%= p.getName() %></td>
            <td><%= p.getPrice() %></td>
            <td><%= c.getQuantity() %></td>
            <td><%= amount %></td>
            <td>
                <form action="removeFromCart" method="post">
                    <input type="hidden" name="cartID" value="<%= c.getCartID() %>">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
        <% } %>
        <tr>
            <td colspan="3" align="right"><strong>Total:</strong></td>
            <td colspan="2"><strong><%= total %></strong></td>
        </tr>
    </table>

    <form action="checkout" method="post">
        <input type="submit" value="Payment">
    </form>
</body>
</html>

