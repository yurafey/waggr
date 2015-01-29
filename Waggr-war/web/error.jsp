<%-- 
    Document   : error
    Created on : Jan 28, 2015, 3:15:44 AM
    Author     : URI
--%>

<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
 "http://www.w3.org/TR/html4/loose.dtd">
<%@ page isErrorPage="true" %>
<html>
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
 <title>Error</title>
 </head>
 <body>
 <h1>There was an error</h1>
 <p style="color: red">${pageContext.errorData.throwable.message}</p>
 <p><a href="index.jsp">Return</a></p>
 </body>
</html>