<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Register - eCommerce</title>
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
    </style>
</head>

<body class="d-flex justify-content-center align-items-center">

    <div class="container d-flex justify-content-center align-items-center min-vh-100">
        <div class="card p-4 shadow" style="width: 100%; max-width: 500px;">
            <h3 class="text-center mb-3"><i class="fas fa-user-plus me-2"></i>Create an Account</h3>

            <form action="SignUpController" method="post">
                <div class="mb-3">
                    <label for="userID" class="form-label">User ID</label>
                    <input type="text" class="form-control" id="userID" name="userID" required placeholder="Enter User ID">
                </div>

                <div class="mb-3">
                    <label for="fullName" class="form-label">Full Name</label>
                    <input type="text" class="form-control" id="fullName" name="fullName" required placeholder="Your Full Name">
                </div>

                <div class="mb-3">
                    <label for="phone" class="form-label">Phone</label>
                    <input type="tel" class="form-control" id="phone" name="phone" pattern="[0-9]{10,15}" required placeholder="e.g., 0123456789">
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password" name="password" required placeholder="Enter Password">
                </div>

                <div class="d-grid mb-3">
                    <button type="submit" class="btn btn-custom"><i class="fas fa-user-check me-2"></i>Register</button>
                </div>

                <div class="text-center">
                    <span class="small">Already have an account? <a href="login.jsp" class="text-decoration-none">Login here</a></span>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
