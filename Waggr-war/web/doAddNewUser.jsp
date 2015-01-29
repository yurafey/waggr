<%-- 
    Document   : doAddNewUser
    Created on : Jan 28, 2015, 3:08:04 AM
    Author     : URI
--%>

<%@page import="waggr.WaggrSessionRemote"%>
<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>JSP Page</title>
    </head>
    <body>
        <%@page import="javax.naming.*, waggr.*" %>
        <%@ page errorPage="error.jsp"%>
        <%!
        WaggrSessionRemote waggrSessionRemote;
        %>
        <%
        InitialContext ic = new InitialContext();
        waggrSessionRemote = (WaggrSessionRemote)ic.lookup("waggr.WaggrSessionRemote");
        String userName= new String(request.getParameter("userName").getBytes("ISO-8859-1"),"Cp1251");
        String userSurname = new String(request.getParameter("userSurname").getBytes("ISO-8859-1"),"Cp1251");
        String userCity = new String(request.getParameter("userCity").getBytes("ISO-8859-1"),"Cp1251");
        String userCountry = new String(request.getParameter("userCountry").getBytes("ISO-8859-1"),"Cp1251");
        if (userName.equals("")||
            userSurname.equals("")||
            request.getParameter("userLogin").toString().equals("")||
            request.getParameter("userPassword").toString().equals("")||
            userCity.equals("")||
            userCountry.toString().equals("")) {
            session.setAttribute("message","Fill all fields!");
            session.setAttribute("backpage","newUser.jsp");
            RequestDispatcher rd = request.getRequestDispatcher("userError.jsp");
            rd.forward(request, response);
        }
        if (waggrSessionRemote.checkUserExists(request.getParameter("userLogin"))){
            session.setAttribute("message","Login already exists!");
            session.setAttribute("backpage","newUser.jsp");
            RequestDispatcher rd = request.getRequestDispatcher("userError.jsp");
            rd.forward(request, response);
        } else {
            if (!waggrSessionRemote.checkCityAndCountryExists(userCity, userCountry)) {
                session.setAttribute("message","No such city or country!");
                session.setAttribute("backpage","newUser.jsp");
                RequestDispatcher rd = request.getRequestDispatcher("userError.jsp");
                rd.forward(request, response);
            } else {
                waggrSessionRemote.addNewUser(
                userName,
                userSurname,
                request.getParameter("userLogin"),
                request.getParameter("userPassword"),
                userCity,
                userCountry);
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            }
        }
        %>
    </body>
</html>
