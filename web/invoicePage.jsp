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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Confirm Your Order</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #ff6ec4, #7873f5);
            min-height: 100vh;
        }
        .card {
            background-color: white;
        }
        .total-row td {
            font-weight: bold;
            font-size: 1.1rem;
        }
    </style>
</head>
<body>

<div class="container my-5">
    <div class="card shadow-sm p-4 rounded-4">
        <h3 class="text-center mb-4">🧾 Confirm Your Order</h3>

        <form action="CreateInvoiceController" method="post">
            <table class="table table-bordered align-middle">
                <thead class="table-success">
                    <tr>
                        <th>Product</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Cart c : selectedCarts) {
                            Product p = productDAO.getProductByID(c.getProductID());
                            double amount = p.getPrice() * c.getQuantity();
                    %>
                    <tr>
                        <td><%= p.getName() %></td>
                        <td><%= String.format("%.2f", p.getPrice()) %> đ</td>
                        <td><%= c.getQuantity() %></td>
                        <td><%= String.format("%.2f", amount) %> đ</td>
                    </tr>
                    <!-- Gửi cartID dưới dạng hidden input -->
                    <input type="hidden" name="selectedCartID" value="<%= c.getCartID() %>">
                    <% } %>
                    <tr class="total-row table-warning">
                        <td colspan="3" class="text-end">Total Amount:</td>
                        <td><%= String.format("%.2f", invoice.getTotalAmount()) %> đ</td>
                    </tr>
                </tbody>
            </table>

            <h4 class="mt-4">Delivery Information</h4>
            <div class="mb-3">
                <label class="form-label">Address:</label>
                <input type="text" class="form-control" name="deliveryAddress" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Contact:</label>
                <input type="text" class="form-control" name="contact" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Payment Method:</label>
                <select name="paymentMethod" class="form-select" required>
                    <option value="COD">COD (Cash on Delivery)</option>
                    <option value="Bank">Bank Transfer</option>
                    <option value="Momo">Momo E-Wallet</option>
                </select>
            </div>

            <div class="text-end">
                <button type="submit" class="btn btn-success">Confirm & Pay</button>
            </div>
        </form>

        <div class="mt-3">
            <a href="viewCart.jsp" class="btn btn-secondary">Back to Cart</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
