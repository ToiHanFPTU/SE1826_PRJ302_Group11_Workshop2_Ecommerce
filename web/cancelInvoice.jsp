<%-- 
    Document   : cancelInvoice
    Created on : Jun 22, 2025, 10:50:03 AM
    Author     : Log
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Invoice" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null || !user.getRoleID().equalsIgnoreCase("bu")) {
        response.sendRedirect("checkAuthorized.jsp");
        return;
    }

    List<Invoice> invoiceList = (List<Invoice>) request.getAttribute("cancelableInvoices");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cancel Invoice Request</title>
</head>
<body>
    <h2>Cancel Invoice Request - <%= user.getFullName() %></h2>

    <%
        if (invoiceList == null || invoiceList.isEmpty()) {
    %>
        <p>None of product for canceling</p>
    <%
        } else {
    %>
    <form action="CancelInvoiceController" method="post">
    <table border="1">
        <tr>
            <th>Choose</th>
            <th>Invoice ID</th>
            <th>Created Date</th>
            <th>Total Amount</th>
            <th>Status</th>
        </tr>
        <c:forEach var="inv" items="${cancelableInvoices}">
            <tr>
                <td><input type="checkbox" name="invoiceID" value="${inv.invoiceID}"></td>
                <td>${inv.invoiceID}</td>
                <td>${inv.createDate}</td>
                <td>${inv.totalAmount}</td>
                <td>${inv.status}</td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <input type="submit" value="Send Request">
</form>

    <%
        }
    %>
</body>
</html>
