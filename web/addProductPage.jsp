<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ page import="model.User"%>
<%@ page import="model.Category"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Add Product Page</title>
    </head>
    <body>
        <form action="CreateProductController" method="post">
            <h2>Insert new product</h2>
            <table>
                <tr>
                    <td>Product Name</td>
                    <td><input type="text" name="name" required /></td>
                </tr>
                <tr>
                    <td>Category</td>
                    <td>
                        <select name="categoryID">
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.categoryID}">
                                    ${category.categoryName}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Price</td>
                    <td>
                        <input type="number" name="price" min="0" step="0.01" required />
                    </td>
                </tr>
                <tr>
                    <td>Quantity</td>
                    <td><input type="number" name="quantity" min="0" required /></td>
                </tr>
                <tr style="display: none">
                    <td>Seller ID</td>
                    <td><input type="text" name="sellerID" value="${user.userID}" /></td>
                </tr>
                <tr>
                    <td>Status</td>
                    <td>
                        <select name="status">
                            <option value="available">Available</option>
                            <option value="unavailable">Unavailable</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><button type="submit">Add</button></td>
                </tr>
            </table>
        </form>
    </body>
</html>
