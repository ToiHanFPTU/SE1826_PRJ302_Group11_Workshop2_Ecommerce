<%-- 
    Document   : returnForm
    Created on : Jul 3, 2025, 10:10:37 AM
    Author     : Log
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Send Refund Request </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <h2>📦 Request Refund</h2>

    <form action="CreateReturnRequestController" method="post">
        <label for="invoiceID">Invoice ID:</label><br>
        <input type="number" name="invoiceID" id="invoiceID" required><br><br>

        <label for="reason">Reason:</label><br>
        <textarea name="reason" id="reason" rows="4" cols="50" required></textarea><br><br>

        <input type="submit" value="Send Request">
    </form>

    <a class="btn-back" href="SearchInvoiceController">Back</a>
    <hr>
    <p style="color:green">${MESSAGE}</p>
    <p style="color:red">${ERROR}</p>
</body>
</html>
