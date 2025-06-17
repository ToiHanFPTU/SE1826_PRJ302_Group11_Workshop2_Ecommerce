<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Reset Password Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body class="bg-light">
        <div class="container d-flex justify-content-center align-items-center min-vh-100">
            <div class="card p-4 shadow rounded-4" style="width: 100%; max-width: 500px;">
                <h3 class="text-center mb-4">Reset Password</h3>

                <form action="ResetPasswordController" method="post">
                    <!-- Ẩn userID nếu cần -->
                    <input type="hidden" name="userID" value="${user.userID}" />

                    <div class="mb-3">
                        <label for="newPassword" class="form-label">New Password</label>
                        <input type="password" class="form-control" id="newPassword" name="newPassword" required minlength="6">
                    </div>

                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">Confirm Password</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required minlength="6">
                    </div>

                    <div class="d-grid mb-3">
                        <button type="submit" class="btn btn-success">Reset Password</button>
                    </div>

                    <div class="text-center">
                        <a href="login.jsp" class="small">Back to login</a>
                    </div>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <script>
            const form = document.querySelector("form");
            form.addEventListener("submit", function (e) {
                const newPass = document.getElementById("newPassword").value;
                const confirmPass = document.getElementById("confirmPassword").value;
                if (newPass !== confirmPass) {
                    e.preventDefault();
                    alert("Passwords do not match!");
                }
            });
        </script>
    </body>

</html>