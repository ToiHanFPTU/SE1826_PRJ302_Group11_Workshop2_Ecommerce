<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Forgot Password</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body class="bg-light">
        <div class="container d-flex justify-content-center align-items-center min-vh-100">
            <div class="card p-4 shadow rounded-4" style="width: 100%; max-width: 500px;">
                <h3 class="text-center mb-4">Forgot Password</h3>
                <p class="text-center text-muted mb-3">Enter your User ID or Phone Number</p>

                <form action="ForgotPasswordController" method="post">
                    <div class="mb-3">
                        <label for="identifier" class="form-label">User ID or Phone Number</label>
                        <input type="text" class="form-control" id="identifier" name="identifier" required>
                    </div>

                    <div class="d-grid mb-3">
                        <button type="submit" class="btn btn-primary">Send Reset Link</button>
                    </div>

                    <div class="text-center">
                        <a href="login.jsp" class="small">Back to login</a>
                    </div>
                </form>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>

</html>