<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #c471ed, #f64f59);
            min-height: 100vh;
        }

        .card {
            border-radius: 20px;
        }

        .btn-custom {
            background-color: #f64f59;
            color: white;
        }

        .btn-custom:hover {
            background-color: #c471ed;
        }

        .text-muted {
            font-size: 0.9rem;
        }
    </style>
</head>

<body class="d-flex justify-content-center align-items-center">

    <div class="container d-flex justify-content-center align-items-center min-vh-100">
        <div class="card p-4 shadow" style="width: 100%; max-width: 450px;">
            <h3 class="text-center mb-3"><i class="fas fa-unlock-alt me-2"></i>Forgot Password</h3>
            <p class="text-center text-muted mb-4">Enter your <strong>User ID</strong> or <strong>Phone Number</strong> below</p>

            <form action="ForgotPasswordController" method="post">
                <div class="mb-3">
                    <label for="identifier" class="form-label">User ID or Phone Number</label>
                    <input type="text" class="form-control" id="identifier" name="identifier" required placeholder="e.g., U001 or 0123456789">
                </div>

                <div class="d-grid mb-3">
                    <button type="submit" class="btn btn-custom"><i class="fas fa-paper-plane me-2"></i>Send Reset Link</button>
                </div>

                <div class="text-center">
                    <a href="login.jsp" class="small text-decoration-none"><i class="fas fa-arrow-left me-1"></i>Back to Login</a>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
