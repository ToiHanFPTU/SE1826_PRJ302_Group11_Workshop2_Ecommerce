<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
        <style>
            body {
                margin: 0;
                padding: 0;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f8d7da;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                flex-direction: column;
                color: #721c24;
            }

            h1 {
                font-size: 2em;
                background-color: #f5c6cb;
                padding: 20px 40px;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }

            a {
                margin-top: 20px;
                text-decoration: none;
                color: white;
                background-color: #c82333;
                padding: 10px 20px;
                border-radius: 5px;
                font-weight: bold;
                transition: background-color 0.3s ease;
            }

            a:hover {
                background-color: #a71d2a;
            }
        </style>
    </head>
    <body>
        <h1>Something went wrong x_x</h1>
        <a href="login.jsp">Back to Login</a>
    </body>
</html>
