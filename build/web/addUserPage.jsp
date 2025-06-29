<%-- 
    Document   : addUserPage
    Created on : Jun 18, 2025, 1:39:37 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #c471ed, #f64f59);
            min-height: 100vh;
        }
        .form-container {
            max-width: 500px;
            margin: auto;
            margin-top: 80px;
            padding: 30px;
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
        }
        .btn-custom {
            background-color: #f64f59;
            color: white;
        }
        .btn-custom:hover {
            background-color: #c471ed;
        }
    </style>
</head>

<body>

    <div class="container">
        <div class="form-container">
            <h3 class="text-center mb-4">Insert New User</h3>

            <form action="UserController?action=insert" method="post">
                <div class="mb-3">
                    <label for="userID" class="form-label">User ID</label>
                    <input type="text" class="form-control" id="userID" name="userIDInsert" required>
                </div>

                <div class="mb-3">
                    <label for="fullName" class="form-label">Full Name</label>
                    <input type="text" class="form-control" id="fullName" name="fullNameInsert" required>
                </div>

                <div class="mb-3">
                    <label for="roleID" class="form-label">Role ID</label>
                    <input type="text" class="form-control" id="roleID" name="roleIDInsert" required>
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password" name="passwordInsert" required>
                </div>

                <div class="mb-3">
                    <label for="phone" class="form-label">Phone</label>
                    <input type="tel" class="form-control" id="phone" name="phoneInsert" required>
                </div>

                <div class="d-grid mb-3">
                    <button type="submit" name="submitForm" class="btn btn-custom">Add User</button>
                </div>

                <div class="text-center">
                    <a href="UserController?action=search" class="btn btn-outline-secondary">← Back to User List</a>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>

