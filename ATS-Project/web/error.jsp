<%-- 
    Document   : error
    Created on : Dec 6, 2020, 2:51:19 PM
    Author     : jber5
    Purpose: this Jsp page is for Showing exceptions
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% response.setStatus(200);%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title><%=exception.toString()%></title>
    </head>
    <body>
        <p><%=exception.getMessage()%></p>
        <a href="home.jsp">Back to Main</a>
    </body>
</html>
