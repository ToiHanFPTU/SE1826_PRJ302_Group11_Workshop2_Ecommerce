<%-- 
    Document   : invoiceSuccess
    Created on : Jun 25, 2025, 9:49:31 AM
    Author     : Log
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Invoice Created Successfully</title>
    <style>
        body {
            text-align: center;
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
        }
        .success-icon {
            font-size: 100px;
            color: #28a745;
            margin-top: 50px;
        }
        h1 {
            color: #28a745;
        }
        .btn {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .btn:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>

    <div class="success-icon">
        ✅
    </div>

    <h1>Invoice Created Successfully!</h1>

    <p>Your order has been successfully placed.</p>

    <a class="btn" href="SearchInvoiceController">View My Orders</a>

</body>
</html>
