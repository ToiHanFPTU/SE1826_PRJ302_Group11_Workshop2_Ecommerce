<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- FontAwesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #ff6ec4, #7873f5);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .login-card {
            background-color: white;
            border-radius: 20px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
            padding: 30px;
            max-width: 420px;
            width: 100%;
        }
        .login-card .form-control {
            border-radius: 10px;
        }
        .login-card .btn-login {
            background: linear-gradient(135deg, #ff6ec4, #7873f5);
            border: none;
            border-radius: 10px;
            font-weight: bold;
            color: white;
        }
        .login-card .btn-login:hover {
            transform: scale(1.05);
            transition: 0.3s;
        }
        .login-icon {
            font-size: 50px;
            color: #ff7eb3;
        }
    </style>
</head>
<body>

<div class="login-card text-center">
    <i class="fas fa-user-circle login-icon mb-3"></i>
    <h3 class="mb-4">Admin Login</h3>

    <% String errorMsg = (String) request.getAttribute("error"); %>
    <% if (errorMsg != null) { %>
    <div class="alert alert-danger text-center rounded-3 shadow-sm d-flex align-items-center justify-content-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill me-2"></i>
        <%= errorMsg %>
    </div>
    <% } %>
    <form action="LoginController" method="post">
        <div class="mb-3 text-start">
            <label for="username" class="form-label"><i class="fas fa-user"></i> Username</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>

        <div class="mb-3 text-start">
            <label for="password" class="form-label"><i class="fas fa-lock"></i> Password</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>

        <button type="submit" class="btn btn-login w-100 mb-3">Login</button>

        <div>
            <a href="forgotPassword.jsp" class="small text-decoration-none">Forgot Password?</a>
        </div>
    </form>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
