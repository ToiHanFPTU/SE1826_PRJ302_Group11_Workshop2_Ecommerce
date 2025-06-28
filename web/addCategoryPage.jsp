<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Insert New Category</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #ff6ec4, #7873f5);
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
            <h3 class="text-center mb-4">Insert New Category</h3>

            <form action="CategoryController" method="post">
                <div class="mb-3">
                    <label for="categoryName" class="form-label">Category Name</label>
                    <input type="text" class="form-control" id="categoryName" name="categoryName" required>
                </div>

                <div class="mb-3">
                    <label for="categoryDescription" class="form-label">Category Description</label>
                    <input type="text" class="form-control" id="categoryDescription" name="categoryDescription" required>
                </div>

                <div class="d-grid">
                    <button type="submit" name="submitform" value="insert" class="btn btn-custom">Add Category</button>
                </div>
            </form>

            <div class="text-center mt-3">
                <a href="CategoryController?action=search" class="btn btn-outline-secondary">← Back to Category List</a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
