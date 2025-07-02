<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User"%>
<%@ page import="dao.UserDAO"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Product Page</title>
    </head>
    <body>
        <h2>Products List</h2>
        <%
            User user = (User) session.getAttribute("user"); 
            if (user == null || !user.getRoleID().equalsIgnoreCase("SE") && !user.getRoleID().equalsIgnoreCase("ad")) {
                response.sendRedirect("checkAuthorized.jsp"); return; 
            } 
        %>
        <div class="welcome-context">
            <h1>Welcome ${user.getFullName()}</h1>
            <a href="${pageContext.request.contextPath}/LogoutController">Logout</a>
        </div>
        <div class="searchZone">
            <h2>Search Category</h2>
            <!-- form để search -->

            <form action="SearchProductController" method="post">
                <!-- chỗ nhập tên để search -->
                <input type="text" name="searchBox" placeholder="Search ..." />
                Price: <input type="number" name="minPrice" style="width: 120px; height: 15.333px;"value="<fmt:formatNumber value='${minPrice}' pattern='#.##'/>" placeholder="đ Từ">
                <input type="number" name="maxPrice" style="width: 120px; height: 15.333px;" value="<fmt:formatNumber value='${maxPrice}' pattern='#.##'/>" placeholder="đ Đến">
                <select name="orderByPrice" style="height: 22.333px;">
                    <option value="ascending" ${orderByPrice == 'ascending' ? 'selected' : ''}>Ascending</option>
                    <option value="descending" ${orderByPrice == 'descending' ? 'selected' : ''}>Descending</option>
                </select>
                <!-- nút submit -->
                <input type="submit" name="searchButton" value="search" />
            </form>
        </div>
        <a href="CreateProductController">Add New Product</a>
        <table>
            <thead>
                <tr>
                    <th>Product ID</th>
                    <th>Name</th>
                    <th>Category ID</th>
                    <th>price</th>
                    <th>Quantity</th>
                    <th>Seller ID</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${productList}" var="product">
                    <tr>

                        <td>
                            <!-- productID ẩn -->
                            <input type="hidden" name="productID" value="${product.productID}" />
                            <input type="number" name="productID" value="${product.productID}" readonly />
                        </td>
                        <td>
                            <input type="text" name="name" value="${product.name}" required />
                        </td>
                        <td>
                            <input type="number" name="categoryID" value="${product.categoryID}" readonly />
                        </td>
                        <td>
                            <input type="number" name="price" value="<fmt:formatNumber value='${product.price}' pattern='#.##'/>" />
                        </td>
                        <td>
                            <input type="number" name="quantity" value="${product.quantity}" required />
                        </td>
                        <td>
                            <input type="text" name="sellerID" value="${product.sellerID}" readonly />
                        </td>
                        <td>
                            <select name="status">
                                <option value="available" ${product.status == 'available' ? 'selected' : ''}>Available</option>
                                <option value="unavailable" ${product.status == 'unavailable' ? 'selected' : ''}>Unavailable</option>
                            </select>
                        </td>
                        <td>
                            <form action="UpdateProductController" method="post">
                                <input type="hidden" name="productID" value="${product.productID}"/>
                                <button type="submit" name="action" value="update">Update</button>
                            </form>
                            <form action="DeletePeoductController" style="display: flex" method="post">
                                <input type="hidden" name="productID" value="${product.productID}"/>
                                <button type="submit" name="action" value="delete">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <p class="alert" hidden>${msg}</p>
        <script>
            window.addEventListener('DOMContentLoaded', function () {
                const alertBox = document.querySelector(".alert");
                const message = alertBox?.textContent?.trim();
                        if (message) {
                    alert(message);
                }
            });
        </script>
    </body>
</html>

