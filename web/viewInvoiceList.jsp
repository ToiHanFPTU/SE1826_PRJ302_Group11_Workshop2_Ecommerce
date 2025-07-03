<%-- 
    Document   : viewInvoiceList
    Created on : Jun 22, 2025, 9:49:13 AM
    Author     : Log
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Invoice" %>
<%@ page import="model.User" %>

<%
    List<Invoice> invoiceList = (List<Invoice>) request.getAttribute("invoiceList");
    User user = (User) session.getAttribute("user");
    String currentStatus = request.getParameter("status");
    if (currentStatus == null) currentStatus = "all";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Invoice List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #ff6ec4, #7873f5);
            min-height: 100vh;
        }
        .nav-tabs .btn {
            margin-right: 5px;
        }
        .nav-tabs .selected {
            background-color: #0d6efd;
            color: white;
        }
    </style>
</head>
<body>

    <h2>List of Invoice</h2>
    <a href="ProductController?action=search">Back</a>
    <div class="nav-tabs">
        <form action="SearchInvoiceController" method="get">
            <input type="submit" name="status" value="all" class="<%= currentStatus.equals("all") ? "selected" : "" %>">
            <input type="submit" name="status" value="pending" class="<%= currentStatus.equals("pending") ? "selected" : "" %>">
            <input type="submit" name="status" value="shipping" class="<%= currentStatus.equals("shipping") ? "selected" : "" %>">
            <input type="submit" name="status" value="waiting" class="<%= currentStatus.equals("waiting") ? "selected" : "" %>">
            <input type="submit" name="status" value="cancelled" class="<%= currentStatus.equals("cancelled") ? "selected" : "" %>">
            <input type="submit" name="status" value="refund" class="<%= currentStatus.equals("refund") ? "selected" : "" %>">
        </form>

        <table class="table table-bordered table-hover align-middle">
            <thead class="table-success">
                <tr>
                    <th>Invoice ID</th>
                    <th>Customer ID</th>
                    <th>Total Amount (đ)</th>
                    <th>Status</th>
                    <th>Created Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (invoiceList != null && !invoiceList.isEmpty()) {
                        for (Invoice inv : invoiceList) {
                %>
                <tr>
                    <td><%= inv.getInvoiceID() %></td>
                    <td><%= inv.getUserID() %></td>
                    <td><%= String.format("%.2f", inv.getTotalAmount()) %></td>
                    <td><span class="badge bg-secondary"><%= inv.getStatus() %></span></td>
                    <td><%= inv.getCreateDate() %></td>
                    <td>
                        <% if (user.getRoleID().equalsIgnoreCase("bu")) {
                              if(inv.getStatus().equalsIgnoreCase("pending")) {%>
                            <form action="CancelInvoiceController" method="post" style="display:inline-block;">
                                <input type="hidden" name="invoiceID" value="<%= inv.getInvoiceID() %>">
                                <button type="submit" class="btn btn-danger btn-sm">Cancel</button>
                            </form>
                        <% } else if(inv.getStatus().equalsIgnoreCase("delivered")) { %>
                            <a href="returnForm.jsp?invoiceID=<%= inv.getInvoiceID() %>" 
                            class="btn btn-warning btn-sm">
                            Request Refund
                            </a>
                        <% } else { %>
                            <span class="text-muted">No Action</span>
                        <% } 
                        }%>

                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" class="text-center">No Invoice Found.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


