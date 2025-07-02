<%-- 
    Document   : productList
    Created on : Jun 18, 2025, 10:17:41 AM
    Author     : Log
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="dao.ProductDAO" %>
<%@ page import="dao.CartDAO" %>
<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    // Lấy user từ session
    User user = (User) session.getAttribute("user");
    
    if(user == null || !user.getRoleID().equalsIgnoreCase("bu")){
     response.sendRedirect("checkAuthorized.jsp");
     return;
    }
    CartDAO cDAO = new CartDAO();
    int cartCount = cDAO.countCartItems(user.getUserID());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <h2>Welcome, <%= user.getFullName()%>!</h2>
    <a href="viewCart.jsp" style="float: right; font-size: 20px; text-decoration: none; position: relative;">
    <i class="fas fa-shopping-cart"></i>
    <span style="position: absolute; top: -10px; right: -10px; background: red; color: white; border-radius: 50%; padding: 2px 6px; font-size: 12px;">
        <%= cartCount %>
    </span>
</a>
    </a> <br>
    <h3>Product List</h3>
    
    
                
    <table border="1" cellpadding="10">
        <tr>
            <th>Product ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Action</th>
        </tr>
        <tbody>
            <c:forEach items="${productList}" var="product">
                <tr>   
            <td>
                <input type="number" name="productID" value="${product.productID}" readonly />
            </td>     
            <td>
                <input type="text" name="name" value="${product.name}" required />
            </td>
            <td>
                <input type="number" name="price" value="<fmt:formatNumber value='${product.price}' pattern='#.##'/>" />
            </td>
            <td
                <form action="AddCartController" method="post">
                    <input type="hidden" name="productID" value="${product.productID}">
                    Quantity: <input type="number" name="quantity" value="1" min="1" required>
                    <input type="submit" value="Add to Cart">
                </form> 
            </td>
                </tr> 
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
