<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User"%>
<%@ page import="dao.UserDAO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
    </head>

    <body>
        <!--phân quyền của user nếu user có quyền admin thì mới được xem trang này-->
        <%
    User user = (User) session.getAttribute("user");
    if (user == null || !user.getRoleID().equalsIgnoreCase("ad")) {
        response.sendRedirect("checkAuthorized.jsp");
        return;
    }
        %>


        <!-- Navigation Bar -->
        <nav class="navbar">
            <div class="nav-container">
                <div class="nav-logo">Stock Manager</div>
                <ul class="nav-links">
                    <li><a href="StockController?action=SearchStock" active>Stock</a></li>
                    <li><a href="UserController">User</a></li>
                    <li><a href="AlertController?action=SearchAlerts">Alert</a></li>
                    <li><a href="#">Transaction</a></li>
                </ul>
            </div>
        </nav>
        <div class="content">
            <div class="search-welcome">
                <!-- chỗ xin chào người dùng -->
                <div class="welcome-context">
                    <h1>Welcome ${user.getFullName()}</h1>
                    <a href="${pageContext.request.contextPath}/LogoutController">Logout</a>
                </div>
                <!-- CHỗ để search user -->
                <div class="searchZone">
                    <h2>Search User</h2>
                    <!-- form để search -->

                    <form action="SearchUserController" method="post">
                        <!-- chỗ nhập tên để search -->
                        <input type="text" name="searchBox" placeholder="Enter user name">
                        <!-- nút submit -->
                        <input type="submit" name="searchButton" value="search">
                    </form>
                </div>
            </div>
            <a href="addUserPage.jsp">Add new User</a>
            <!-- List user -->
            <table>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>User ID</th>
                        <th>Full Name</th>
                        <th>Role ID</th>
                        <th>Password</th>
                        <th>Phone</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${userList}" var="user" varStatus="record">
                        <tr>
                    <form action="UserController" method="POST">
                        <!-- No: dùng để đến số dùng -->
                        <td>
                            <input type="text" name="No" value="${record.count}" readonly>
                        </td>
                        <%--userID này để delete hoặc update--%>
                        <input type="hidden" name="userID" value="${user.userID}" />
                        <%--Hiển thị userID--%>
                        <td>
                            <input type="text" value="${user.userID}" readonly/>
                        </td>
                        <%--Hiển thị full name--%>
                        <td>
                            <input type="text"  name="fullName"   value="${user.fullName}" required/>
                        </td>
                        <%--Hiển thị role--%>
                        <td>
                            <input type="text"  name="roleID" value="${user.roleID}" required/>
                        </td>
                        <%--hiển thị password--%>
                        <td>
                            <input type="text"  name="password"  value="${user.password}" required/>
                        </td>
                        <td>
                            <input type="tel"  name="phone"  value="${user.phone}" required/>
                        </td>
                        <%--Hành độngc ảu người dùng--%>
                        <td class="actions">
                            <form action="UserController" method="post">
                                <input type="hidden" name="userID" value="${user.userID}">
                                <button type="submit" name="action" value="update">Update</button>
                                <button type="submit" name="action" value="remove">Delete</button>
                            </form>
                        </td>
                    </form>
                    </tr>
                </c:forEach>
                </tbody>
        </div>
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
