<%-- 
    Document   : invoicePage
    Created on : Jun 20, 2025, 10:34:21 AM
    Author     : Log
--%>

<%@ page import="java.util.List" %>
<%@ page import="model.Cart" %>
<%@ page import="model.Product" %>
<%@ page import="dao.ProductDAO" %>
<%@ page import="model.Invoice" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Cart> selectedCarts = (List<Cart>) request.getAttribute("selectedCarts");
    Invoice invoice = (Invoice) request.getAttribute("invoice");
    ProductDAO productDAO = new ProductDAO();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Complete Bill</title>
</head>
<body>
    <h2>Confirm order information</h2>

    <form action="CreateInvoiceController" method="post">
        <table border="1" cellpadding="10">
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total Amount</th>
            </tr>
            <%
                for (Cart c : selectedCarts) {
                    Product p = productDAO.getProductByID(c.getProductID());
                    double amount = p.getPrice() * c.getQuantity();
            %>
            <tr>
                <td><%= p.getName() %></td>
                <td><%= p.getPrice() %></td>
                <td><%= c.getQuantity() %></td>
                <td><%= amount %></td>
            </tr>
            <!-- Gửi cartID về CreateInvoiceController -->
            <input type="hidden" name="selectedCartID" value="<%= c.getCartID() %>">
            <% } %>
            <tr>
                <td colspan="3" align="right"><strong>Total:</strong></td>
                <td><strong><%= invoice.getTotalAmount() %></strong></td>
            </tr>
        </table>

        <h3>Delivery Information</h3>
        <label>Address: </label>
        <input type="text" name="deliveryAddress" required><br><br>

        <label>Contact: </label>
        <input type="text" name="contact" required><br><br>

        <label>Paying method:</label>
        <select name="paymentMethod" required>
            <option value="COD">COD (Cash On Delivery)</option>
            <option value="Bank">Banking</option>
            <option value="Momo">Momo E-Wallet</option>
        </select><br><br>

        <input type="submit" value="Payment">
    </form>
</body>
</html>


