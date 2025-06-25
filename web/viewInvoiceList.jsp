<%-- 
    Document   : viewInvoiceList
    Created on : Jun 22, 2025, 9:49:13 AM
    Author     : Log
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Invoice" %>

<%
    List<Invoice> invoiceList = (List<Invoice>) request.getAttribute("invoiceList");
    String currentStatus = request.getParameter("status");
    if(currentStatus == null) currentStatus = "all";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Invoice List</title>
    <style>
        .nav-tabs input[type="submit"] {
            margin: 5px;
            padding: 10px 15px;
            border: none;
            background-color: #f1f1f1;
            cursor: pointer;
        }
        .nav-tabs input[type="submit"].selected {
            background-color: #007BFF;
            color: white;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 8px;
        }
    </style>
</head>
<body>

    <h2>List of Invoice</h2>

    <div class="nav-tabs">
        <form action="SearchInvoiceController" method="get">
            <input type="submit" name="status" value="all" class="<%= currentStatus.equals("all") ? "selected" : "" %>">
            <input type="submit" name="status" value="pending" class="<%= currentStatus.equals("pending") ? "selected" : "" %>">
            <input type="submit" name="status" value="shipping" class="<%= currentStatus.equals("shipping") ? "selected" : "" %>">
            <input type="submit" name="status" value="waiting" class="<%= currentStatus.equals("waiting") ? "selected" : "" %>">
            <input type="submit" name="status" value="cancelled" class="<%= currentStatus.equals("cancelled") ? "selected" : "" %>">
            <input type="submit" name="status" value="refund" class="<%= currentStatus.equals("refund") ? "selected" : "" %>">
        </form>
    </div>

    <table>
        <tr>
            <th>Invoice ID</th>
            <th>Customer</th>
            <th>Total Amount</th>
            <th>Status</th>
            <th>Created Date</th>
            <th>Action</th>
        </tr>

        <%
            if (invoiceList != null && !invoiceList.isEmpty()) {
                for (Invoice inv : invoiceList) {
        %>
        <tr>
            <td><%= inv.getInvoiceID() %></td>
            <td><%= inv.getUserID() %></td>
            <td><%= inv.getTotalAmount() %></td>
            <td><%= inv.getStatus() %></td>
            <td><%= inv.getCreateDate() %></td>
            <td>
                <% if (inv.getStatus().equalsIgnoreCase("pending")) { %>
                    <form action="CancelInvoiceController" method="post" style="display:inline;">
                        <input type="hidden" name="invoiceID" value="<%= inv.getInvoiceID() %>">
                        <input type="submit" value="Cancel Invoice">
                    </form>
                <% } %>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="6" style="text-align:center;">No Invoice Found</td>
        </tr>
        <%
            }
        %>
    </table>

</body>
</html>

