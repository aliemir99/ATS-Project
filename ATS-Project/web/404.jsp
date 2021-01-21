<%-- 
    Document   : 404.jsp
    Created on : Dec 6, 2020, 2:51:04 PM
    Author     : jber5
    Purpose: this Jsp page is for any incorrect URL path
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>404 Error</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"/>
        <style>body{padding:20px;}
            #countDown{font-weight: bold;}
        </style>
    </head>
    <body>
        <div class="container" style="text-align:center">
            <h1>Unknown Page</h1>
            <button class="btn btn-warning">
                <a href="home.jsp">Redirect to Home</a>
            </button>
        </div>
    </body>
</html>
