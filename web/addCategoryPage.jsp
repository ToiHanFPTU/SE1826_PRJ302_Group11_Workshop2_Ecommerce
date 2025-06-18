<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>
    </head>
    <body>
        <div>
            <h1>Insert New Category</h1>
            <a href="CategoryController?action=search">Back</a>
        </div>
        <form action="CategoryController?action=insert" method="post">
            <table>
                <tr>
                    <td>Category name</td>
                    <td>
                        <input type="text" name="categoryName" required />
                    </td>
                </tr>
                <tr>
                    <td>Category Description</td>
                    <td>
                        <input type="text" name="categoryDescription" required />
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <button type="submit" name="submitform">Add</button>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>

