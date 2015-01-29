<%-- 
    Document   : doLogout
    Created on : Jan 29, 2015, 2:06:29 PM
    Author     : URI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Waggr Application</title>
    </head>
    <body>
        <%
            session.setAttribute("userLogin",null);
            session.setAttribute("userPassword",null);
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        %>
     </body>
</html>
