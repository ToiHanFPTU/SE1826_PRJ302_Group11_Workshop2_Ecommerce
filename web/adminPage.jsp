<%-- 
    Document   : adminPage
    Created on : Jun 27, 2025, 10:19:31 AM
    Author     : Log
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User"%>
<%
    User userLogin = (User) session.getAttribute("user");
    if (userLogin == null || !"AD".equalsIgnoreCase(userLogin.getRoleID())) {
        response.sendRedirect("checkAuthorized.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(135deg, #ff6ec4, #7873f5);
                min-height: 100vh;
            }
            .card {
                border-radius: 20px;
            }
            .menu-btn {
                margin-bottom: 15px;
                padding: 12px 20px;
                font-weight: bold;
                border-radius: 10px;
            }
            .menu-btn:hover {
                transform: scale(1.05);
                transition: 0.3s;
            }
        </style>
    </head>
    <body class="d-flex justify-content-center align-items-center">

        <div class="card text-center p-4 shadow-lg text-white" style="background: rgba(255, 255, 255, 0.1); max-width: 500px;">
            <h2 class="mb-3">Welcome, <%= userLogin.getFullName() %> 👑</h2>
            <h5 class="mb-4">Admin Dashboard</h5>

            <div class="d-grid gap-2">
                <button class="btn btn-outline-light menu-btn" onclick="window.location.href = 'SearchCategoryController?action=searchButton'">
                    📂 Manage Categories
                </button>

            <button class="btn btn-outline-light menu-btn" onclick="window.location.href='SearchUserController?action=searchButton'">
                👥 Manage Users
            </button>
        
            <button class="btn btn-outline-light menu-btn" onclick="window.location.href='InventoryController?action=search'">
                👜 Manage Inventory
            </button>
            
            <button class="btn btn-outline-light menu-btn" onclick="window.location.href='ProductController?action=search'">
                🛍 Manage Product
            </button>

                <button class="btn btn-outline-light menu-btn" onclick="window.location.href = 'LogoutController'">
                    🔓 Logout
                </button>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

