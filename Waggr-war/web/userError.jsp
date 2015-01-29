<%-- 
    Document   : usererror
    Created on : Dec 25, 2014, 12:58:14 AM
    Author     : URI
--%>

<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>Waggr Error</title>
    </head>
    <body>
        <h1>Error: <%=session.getAttribute("message")%></h1>
        <p><a href="<%=session.getAttribute("backpage")%>">Вернуться</a></p>
    </body>
</html>
