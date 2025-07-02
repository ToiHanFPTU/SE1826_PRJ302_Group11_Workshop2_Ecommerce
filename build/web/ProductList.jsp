<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="dao.ProductDAO" %>
<%@ page import="dao.CartDAO" %>
<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    User user = (User) session.getAttribute("user");
    
    if(user == null || !user.getRoleID().equalsIgnoreCase("bu")){
     response.sendRedirect("checkAuthorized.jsp");
     return;
    }
    ProductDAO dao = new ProductDAO();
    List<Product> list = dao.listAllProduct();
   
    CartDAO cDAO = new CartDAO();
    int cartCount = cDAO.countCartItems(user.getUserID());
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Product List</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            .title {
                display: flex;
                justify-content: center;
            }
            .left-side {
                margin-right: 50px;
            }
            .quantity-control {
                display: inline-flex;
                align-items: center;
                gap: 4px;
            }

            .quantity-control input[type="number"] {
                width: 50px;
                text-align: center;
            }

            .quantity-control button {
                width: 30px;
                height: 30px;
                font-weight: bold;
                background-color: #f5f5f5;
                border: 1px solid #ccc;
                cursor: pointer;
            }

            .right-side {
                width: max-content;
                display: flex
            }
        </style>
    </head>
    <body>
        <div class="header">
            <div class="greating">
                <h2>Welcome, <%= user.getFullName()%>!</h2>
            </div>
            <div class="title-table">
                <h3 class="title">Product</h3>
            </div>
            <div class="cart">
                <a href="viewCart.jsp" style="float: right; font-size: 20px; text-decoration: none; position: relative;">
                    <i class="fas fa-shopping-cart"></i>
                    <span style="position: absolute; top: -10px; right: -10px; background: red; color: white; border-radius: 50%; padding: 2px 6px; font-size: 12px;">
                        <%= cartCount %>
                    </span>
                </a>
            </div>

        </div>
        <div class="main-content" style="display: flex">
            <div class="left-side">
                <h2>Search Category</h2>
                <!-- form để search -->

                <form action="SearchProductController" method="post">
                    <!-- chỗ nhập tên để search -->
                    <div class="searchByName">
                        <input type="text" name="searchBox" placeholder="Search..." value="${keyword eq null ? '' : keyword}"/>
                    </div>
                    <div class="searchByPrice">
                        <h4>
                            Price
                        </h4>
                        <div>
                            <table>
                                <tr>
                                    <td>From: </td>
                                    <td>
                                        <input type="number" name="minPrice" style="width: 120px; height: 15.333px;"value="<fmt:formatNumber value='${minPrice}' pattern='#.##'/>" placeholder="đ Từ">
                                    </td>
                                </tr>
                                <tr>
                                    <td>To: </td>
                                    <td>
                                        <input type="number" name="maxPrice" style="width: 120px; height: 15.333px;" value="<fmt:formatNumber value='${maxPrice}' pattern='#.##'/>" placeholder="đ Đến">
                                    </td>
                                </tr>
                            </table>
                            <br>

                        </div>
                    </div>
                    <div class="sortByPrice">
                        <h4>Sort By Price</h4>
                        <div>
                            <select name="orderByPrice" style="height: 22.333px;">
                                <option value="ascending" ${orderByPrice == 'ascending' ? 'selected' : ''}>Ascending</option>
                                <option value="descending" ${orderByPrice == 'descending' ? 'selected' : ''}>Descending</option>
                            </select>
                        </div>
                    </div>
                    <div class="category">
                        <h4>
                            Category
                        </h4>
                        <div>
                            <select name="categoryID">
                                <option value="0">--All--</option>
                                <c:forEach items="${categoryList}" var="category">
                                    <option value="${category.categoryID}" 
                                            ${category.categoryID == categoryIDChoose ? 'selected' : ''}>
                                        ${category.categoryName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <br>
                    <!-- nút submit -->
                    <input type="submit" name="searchButton" value="search" />
                </form>
            </div>

            <div class="right-side">

                <table border="1" cellpadding="10">
                    <tr>
                        <th>Product ID</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Add to Cart</th>
                    </tr>
                    <c:forEach items="${productList}" var="product">
                        <tr>
                            <td>${product.productID}</td>
                            <td>${product.name}</td>
                            <td><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" /></td>
                        <td>
                            <form action="AddCartController" method="post">
                                <input type="hidden" name="productID" value="${product.getProductID()}">
                                <div class="quantity-control">
                                    <button type="button" class="decrease">-</button>
                                    <input type="number" name="quantity" value="1" min="1" />
                                    <button type="button" class="increase">+</button>
                                </div>
                                <input type="submit" value="Add to Cart">
                            </form> 
                        </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const controls = document.querySelectorAll('.quantity-control');

                controls.forEach(control => {
                    const input = control.querySelector('input[name="quantity"]');
                    const increaseBtn = control.querySelector('.increase');
                    const decreaseBtn = control.querySelector('.decrease');

                    increaseBtn.addEventListener('click', () => {
                        input.value = parseInt(input.value) + 1;
                    });

                    decreaseBtn.addEventListener('click', () => {
                        if (parseInt(input.value) > 1) {
                            input.value = parseInt(input.value) - 1;
                        }
                    });
                });
            });
        </script>
    </body>
</html>