<%-- 
    Document   : inventoryList
    Created on : Jun 29, 2025, 2:42:39 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="dao.ProductDAO" %>
<%@ page import="dao.CartDAO" %>
<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8" />
        <title>Inventory List</title>
        <style>
            table {
                border-collapse: collapse;
                width: 90%;
                margin: 20px auto;
            }

            th,
            td {
                border: 1px solid #ccc;
                padding: 8px;
            }

            th {
                background-color: #f2f2f2;
            }

            h2 {
                text-align: center;
            }

            .low-stock {
                color: red;
                font-weight: bold;
            }
            .searchZone {
                display: flex;
                justify-content: center;
            }
        </style>
    </head>

    <body>
        <h2>📦 Full Inventory</h2>
        <div class="top">
            <a href="${pageContext.request.contextPath}/LogoutController">Logout</a>
            <a href="adminPage.jsp">Back</a>
        </div>
        
        <div class="searchZone">
            <form action="InventoryController?action=search" method="post">
                <input type="text" name="searchBox" placeholder="Search..." value="${keyWord eq null ? '' : keyWord}"/>
                <input type="submit" value="search">
            </form>
        </div>
        <table>
            <thead>
                <tr>
                    <th>Warehouse ID</th>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Stock Quantity</th>
                    <th>Reorder Threshold</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${inventoryList}" var="inv">
                    <c:choose>
                        <c:when test="${inv.stockQuantity < inv.reorderThreshold}">
                            <tr class="low-stock">
                        <form action="InventoryController" method="post">
                            <input type="hidden" name="productID" value="${inv.productID}" />
                            <td>${inv.warehouseID}</td>
                            <td>${inv.productID}</td>
                            <td>${inv.productName}</td>
                            <td>
                                <input type="number" name="stockQuantity" value="${inv.stockQuantity}" />
                            </td>
                            <td>
                                <input type="number" name="reorderThreshold" value="${inv.reorderThreshold}" />
                            </td>
                            <td>
                                <button type="submit" name="action" value="update" class="btn btn-sm btn-update"><i class="fas fa-edit"></i> Update</button>
                                <button type="submit" name="action" value="remove" class="btn btn-sm btn-delete"><i class="fas fa-trash"></i> Delete</button>
                            </td>
                        </form>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                    <form action="InventoryController" method="post">
                        <input type="hidden" name="productID" value="${inv.productID}" />
                        <td>${inv.warehouseID}</td>
                        <td>${inv.productID}</td>
                        <td>${inv.productName}</td>
                        <td>
                            <input type="number" name="stockQuantity" value="${inv.stockQuantity}" />
                        </td>
                        <td>
                            <input type="number" name="reorderThreshold" value="${inv.reorderThreshold}" />
                        </td>
                        <td>
                            <button type="submit" name="action" value="update" class="btn btn-sm btn-update"><i class="fas fa-edit"></i> Update</button>
                            <button type="submit" name="action" value="remove" class="btn btn-sm btn-delete"><i class="fas fa-trash"></i> Delete</button>
                        </td>
                    </form>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</tbody>
</table>
</body>

</html>
