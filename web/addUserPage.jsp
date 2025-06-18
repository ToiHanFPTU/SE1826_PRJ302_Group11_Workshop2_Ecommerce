<%-- 
    Document   : addUserPage
    Created on : Jun 18, 2025, 1:39:37 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>

    <body>
        <!--form để insert user-->
        <form action="UserController?action=insert" id="form-insert" class="form-container" method="post">
            <h2 class="insertHeader">Insert new user</h2>
            <table>
                <tr>
                    <td>User ID</td>
                    <td>
                        <input type="text" name="userIDInsert" required>
                    </td>
                </tr>
                <tr>
                    <td>Full Name</td>
                    <td>
                        <input type="text" name="fullNameInsert" required>
                    </td>
                </tr>
                <tr>
                    <td>Role ID</td>
                    <td>
                        <input type="text" name="roleIDInsert" required>
                    </td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="passwordInsert" required></td>
                </tr>
                <tr>
                    <td>Phone</td>
                    <td><input type="tel" name="phoneInsert" required></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <button type="submit" name="submitForm">Add User</button>
                    </td>
                </tr>
            </table>
        </form>
    </body>

</html>
