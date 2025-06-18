<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Category"%>
<%@ page import="dao.UserDAO"%>
<%@ page import="model.User"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Category Page</title>
    </head>

    <body>
        <%
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRoleID().equalsIgnoreCase("ad")) {
            response.sendRedirect("checkAuthorized.jsp");
            return;
        }
        %>
        <div class="welcome-context">
            <h1>Welcome ${user.getFullName()}</h1>
            <a href="${pageContext.request.contextPath}/LogoutController">Logout</a>
        </div>
        <div class="searchZone">
            <h2>Search Category</h2>
            <!-- form để search -->

            <form action="SearchCategoryController" method="post">
                <!-- chỗ nhập tên để search -->
                <input type="text" name="searchBox" placeholder="Enter category name">
                <!-- nút submit -->
                <input type="submit" name="searchButton" value="search">
            </form>
        </div>
        <h2>Categories List</h2>
        <table>
            <thead>
                <tr>
                    <th>Category ID</th>
                    <th>Category Name</th>
                    <th>Category Description</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${categoryList}" var="cate">
                    <tr>
                        <<form action="CategoryController">
                    <td>
                        <input type="text" name="categoryID" value="${cate.categoryID}" readonly required />
                    </td>
                    <td>
                        <input type="text" name="categoryName" value="${cate.categoryName}" required />
                    </td>
                    <td>
                        <textarea type="text" name="categoryDescription" value="${cate.description}" required></textarea>
                    </td>
                    <td>
                        <form action="CategoryController">
                            <input
                                type="hidden"
                                name="categoryID"
                                value="${category.categoryID}"
                                />
                            <button type="submit" name="action" value="update">Update</button>
                            <button type="submit" name="action" value="delete">Delete</button>
                        </form>
                    </td>
                </form>>
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

