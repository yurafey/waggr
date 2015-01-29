<%-- 
    Document   : doSaveUserCityCountry
    Created on : Jan 29, 2015, 3:25:37 AM
    Author     : URI
--%>

<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>Waggr Application</title>
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
        String userCity = new String(request.getParameter("userCity").getBytes("ISO-8859-1"),"Cp1251");
        String userCountry = new String(request.getParameter("userCountry").getBytes("ISO-8859-1"),"Cp1251");
        if (userCity.isEmpty()||userCountry.isEmpty()) {
            session.setAttribute("message", "Empty fields!");
            session.setAttribute("backpage", "mainPage.jsp");
            RequestDispatcher rd = request.getRequestDispatcher("userError.jsp");
            rd.forward(request, response);
        } else {
            if (!waggrSessionRemote.checkCityAndCountryExists(userCity, userCountry)) {
                session.setAttribute("message", "No such city!");
                session.setAttribute("backpage", "mainPage.jsp");
                RequestDispatcher rd = request.getRequestDispatcher("userError.jsp");
                rd.forward(request, response);
            } else {
                waggrSessionRemote.changeUserCityAndCountry((String)session.getAttribute("userLogin"),userCity,userCountry);
                RequestDispatcher rd = request.getRequestDispatcher("mainPage.jsp");
                rd.forward(request, response);
            }
        }
        %>
    </body>
</html>
