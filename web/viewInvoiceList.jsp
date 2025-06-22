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
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Invoice List</title>
    <style>
        .nav-tabs {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }
        .nav-tabs button {
            padding: 10px 20px;
            border: none;
            background-color: #eee;
            cursor: pointer;
        }
        .nav-tabs button.active {
            background-color: #ccc;
            font-weight: bold;
        }
        .invoice-table {
            display: none;
        }
        .invoice-table.active {
            display: table;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #999;
        }
    </style>
    <script>
        function showTab(status) {
            let tabs = document.querySelectorAll(".invoice-table");
            tabs.forEach(tab => tab.classList.remove("active"));

            let target = document.getElementById(status);
            if (target) target.classList.add("active");

            let buttons = document.querySelectorAll(".nav-tabs button");
            buttons.forEach(btn => btn.classList.remove("active"));

            document.getElementById("btn-" + status).classList.add("active");
        }

        window.onload = function() {
            showTab('all');
        };
    </script>
</head>
<body>
    <h2>List of Invoice</h2>

    <div class="nav-tabs">
        <button id="btn-all" onclick="showTab('all')">All</button>
        <button id="btn-pending" onclick="showTab('pending')">Pending</button>
        <button id="btn-shipping" onclick="showTab('shipping')">Transport</button>
        <button id="btn-waiting" onclick="showTab('waiting')">Delivering</button>
        <button id="btn-cancelled" onclick="showTab('cancelled')">Canceled</button>
        <button id="btn-refund" onclick="showTab('refund')">Refund</button>
    </div>

    <%
        String[] statuses = {"all", "pending", "shipping", "waiting", "cancelled", "refund"};
        String[] statusLabels = {"All", "Pending", "Transport", "Delivering", "Canceled", "Refund"};

        for (int i = 0; i < statuses.length; i++) {
    %>
    <table id="<%= statuses[i] %>" class="invoice-table">
        <tr>
            <th>Invoice ID</th>
            <th>Customer</th>
            <th>Total Amount</th>
            <th>Status</th>
            <th>Created Date</th>
        </tr>
        <%
            for (Invoice inv : invoiceList) {
                if (statuses[i].equals("all") || inv.getStatus().equalsIgnoreCase(statuses[i])) {
        %>
        <tr>
            <td><%= inv.getInvoiceID() %></td>
            <td><%= inv.getUserID() %></td>
            <td><%= inv.getTotalAmount() %></td>
            <td><%= inv.getStatus() %></td>
            <td><%= inv.getCreateDate() %></td>
        </tr>
        <%
                }
            }
        %>
    </table>
    <% } %>
</body>
</html>

