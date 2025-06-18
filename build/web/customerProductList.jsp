<%-- 
    Document   : productList
    Created on : Jun 18, 2025, 10:17:41 AM
    Author     : Log
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="dao.ProductDAO" %>
<%
    // Lấy user từ session
    User user = (User) session.getAttribute("user");
    
    if(user == null || !user.getRoleID().equalsIgnoreCase("bu")){
     response.sendRedirect("checkAuthorized.jsp");
     return;
    }
    // Lấy danh sách sản phẩm
    ProductDAO dao = new ProductDAO();
    List<Product> list = dao.listAllProducts();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
</head>
<body>
    <h2>Welcome, <%= user.getFullName()%>!</h2>
    <h3>Product List</h3>
    <table border="1" cellpadding="10">
        <tr>
            <th>Product ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Add to Cart</th>
        </tr>
        <%
            for(Product p : list){
        %>
        <tr>
            <td><%= p.getProductID() %></td>
            <td><%= p.getName() %></td>
            <td><%= p.getPrice() %></td>
            <td>
                <form action="addToCart" method="post">
                    <input type="hidden" name="productID" value="<%= p.getProductID() %>">
                    Quantity: <input type="number" name="quantity" value="1" min="1" required>
                    <input type="submit" value="Add to Cart">
                </form> 
            </td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>
