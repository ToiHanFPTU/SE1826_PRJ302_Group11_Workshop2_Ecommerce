<%-- 
    Document   : cancelInvoice
    Created on : Jun 22, 2025, 10:50:03 AM
    Author     : Log
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cancel Invoice Success</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin-top: 100px;
        }
        .success-icon {
            font-size: 100px;
            color: #28a745; /* Màu xanh thành công */
        }
        .message {
            font-size: 24px;
            margin-top: 20px;
        }
        .btn-back {
            margin-top: 30px;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .btn-back:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <div class="success-icon">
        &#10004; 
    </div>

    <div class="message">
        Your Invoice has been Cancelled Successfully!
    </div>

    <a href="SearchInvoiceController" class="btn-back">Back to Invoice List</a>

</body>
</html>