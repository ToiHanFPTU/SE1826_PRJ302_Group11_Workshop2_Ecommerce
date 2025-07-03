<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ page import="model.User"%>
<%@ page import="model.Category"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Add Product Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #ff6ec4, #7873f5);
            min-height: 100vh;
        }
        .form-card {
            background-color: white;
            padding: 30px;
            border-radius: 16px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
        }
        .form-card h2 {
            color: #343a40;
            font-weight: 600;
        }
        .form-label {
            font-weight: 500;
        }
        .btn-primary {
            background-color: #6f42c1;
            border: none;
        }
        .btn-primary:hover {
            background-color: #5a33a7;
        }
        .alert {
            color: red;
            font-weight: 600;
        }
    </style>
</head>
<body>

<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
    <div class="form-card col-md-8">
        <h2 class="mb-4 text-center">🛍️ Add New Product</h2>

        <form action="CreateProductController" method="post">
            <div class="mb-3">
                <label class="form-label">Product Name</label>
                <input type="text" name="name" class="form-control" required />
            </div>

            <div class="mb-3">
                <label class="form-label">Category</label>
                <select name="categoryID" class="form-select">
                    <option value="101">Khác</option>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.categoryID}">
                            ${category.categoryName}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Price (đ)</label>
                <input type="number" name="price" min="0" step="0.01" class="form-control" required />
            </div>

            <div class="mb-3">
                <label class="form-label">Quantity</label>
                <input type="number" name="quantity" min="0" class="form-control" required />
            </div>

            <input type="hidden" name="sellerID" value="${user.userID}" />

            <div class="mb-3">
                <label class="form-label">Status</label>
                <select name="status" class="form-select">
                    <option value="available">Available</option>
                    <option value="unavailable">Unavailable</option>
                </select>
            </div>

            <div class="d-flex justify-content-between align-items-center">
                <a href="ProductController?action=search" class="btn btn-secondary">⬅ Back to Product List</a>
                <button type="submit" class="btn btn-primary">Add Product</button>
            </div>
        </form>

        <p class="alert mt-3" hidden>${msg}</p>
    </div>
</div>

<script>
    window.addEventListener('DOMContentLoaded', function () {
        const alertBox = document.querySelector(".alert");
        const message = alertBox?.textContent?.trim();
        if (message) {
            alert(message);
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
