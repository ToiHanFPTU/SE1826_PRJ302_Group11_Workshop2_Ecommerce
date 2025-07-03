<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.User"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin - Manage Users</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

        <style>
            body {
                background: linear-gradient(135deg, #ff6ec4, #7873f5);
                min-height: 100vh;
                padding-top: 30px;
            }
            .container {
                background: white;
                border-radius: 15px;
                padding: 30px;
                box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
            }
            .nav-buttons a {
                margin: 0 10px;
            }
            table th, table td {
                text-align: center;
                vertical-align: middle;
            }
            .btn-update {
                background-color: #6f42c1;
                color: white;
            }
            .btn-delete {
                background-color: #d63384;
                color: white;
            }
        </style>
    </head>
    <body>

        <%
            User user = (User) session.getAttribute("user");
            if (user == null || !user.getRoleID().equalsIgnoreCase("ad")) {
                response.sendRedirect("checkAuthorized.jsp");
                return;
            }
        %>

        <div class="container">
            <h2 class="text-center mb-4"><i class="fas fa-users-cog"></i> Admin - User Management</h2>

            <!-- Navigation Buttons -->
            <div class="d-flex justify-content-center nav-buttons mb-4">
                <button class="btn btn-light me-2" onclick="window.location.href = 'adminPage.jsp'">
                    <i class="fas fa-arrow-left me-1"></i>Back
                </button>
            </div>

            <!-- Welcome Message -->
            <div class="alert alert-info text-center">
                Welcome, <strong><%= user.getFullName() %></strong>!
            </div>

            <!-- Search Form -->
            <form action="SearchUserController" method="post" class="mb-4 d-flex">
                <input type="text" name="searchBox" class="form-control me-2" placeholder="Enter user name">
                <button type="submit" name="searchButton" class="btn btn-primary"><i class="fas fa-search"></i> Search</button>
            </form>

            <!-- Add User Link -->
            <div class="mb-3">
                <a href="addUserPage.jsp" class="btn btn-success"><i class="fas fa-user-plus"></i> Add New User</a>
            </div>

            <!-- User Table -->
            <table class="table table-bordered table-striped table-hover">
                <thead class="table-secondary">
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
                        <td><input type="text" name="No" value="${record.count}" readonly class="form-control text-center"></td>
                        <input type="hidden" name="userID" value="${user.userID}" />

                        <td><input type="text" value="${user.userID}" readonly class="form-control text-center" /></td>
                        <td><input type="text" name="fullName" value="${user.fullName}" required class="form-control" /></td>
                        <td><input type="text" name="roleID" value="${user.roleID}" required class="form-control text-center" /></td>
                        <td><input type="text" name="password" value="${user.password}" required class="form-control" /></td>
                        <td><input type="tel" name="phone" value="${user.phone}" required class="form-control" /></td>

                        <td>
                            <button type="submit" name="action" value="update" class="btn btn-sm btn-update"><i class="fas fa-edit"></i> Update</button>
                            <button type="submit" name="action" value="remove" class="btn btn-sm btn-delete"><i class="fas fa-trash"></i> Delete</button>
                        </td>
                    </form>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <!-- Alert Message -->
            <p class="alert alert-warning text-center" <c:if test="${empty msg}">style="display:none;"</c:if> >${msg}</p>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        const alertBox = document.querySelector(".alert");
                        if (alertBox && alertBox.textContent.trim() !== '') {
                            alertBox.style.display = 'block';
                        }
                    });
        </script>

    </body>
</html>
