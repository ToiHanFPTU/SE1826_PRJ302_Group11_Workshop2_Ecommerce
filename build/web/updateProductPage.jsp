<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.User"%>
<%@ page import="dao.UserDAO"%>
<%@ page import="dao.CategoryDAO"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Update Product</title>
    </head>
    <body>
        <form action="UpdateProductController" method="post">
            <h2>Update product</h2>
            <a href="ProductController?action=search">Back to Product list</a>

            <input type="hidden" name="productID" value="${product.productID}" />

            <table>
                <tr>
                    <td>Product Name</td>
                    <td><input type="text" name="name" value="${product.name}" required /></td>
                </tr>
                <tr>
                    <td>Category</td>
                    <td>
                        <select name="categoryID">
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.categoryID}"
                                        ${category.categoryID eq product.categoryID ? 'selected="selected"' : ''}>
                                    ${category.categoryName}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Price</td>
                    <td>
                        <input type="number" name="price" value="<fmt:formatNumber value='${product.price}' pattern='#.##'/>" min="0" step="0.01" required />
                    </td>
                </tr>
                <tr>
                    <td>Quantity</td>
                    <td><input type="number" name="quantity" value="${product.quantity}" min="0" required /></td>
                </tr>
                <tr style="display: none">
                    <td>Seller ID</td>
                    <td><input type="text" name="sellerID" value="${user.userID}" /></td>
                </tr>
                <tr>
                    <td>Status</td>
                    <td>
                        <select name="status">
                            <option value="available" ${product.status == 'available' ? 'selected' : ''}>Available</option>
                            <option value="unavailable" ${product.status == 'unavailable' ? 'selected' : ''}>Unavailable</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><button type="submit">Update</button></td>
                </tr>
            </table>
        </form>
    </body>
</html>
