<%-- 
    Document   : doAddNewRealFeel
    Created on : Jan 29, 2015, 3:43:02 PM
    Author     : URI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@page import="javax.naming.*, waggr.*" %>
        <%@ page errorPage="error.jsp"%>
        <%!
        RealFeelBeanRemote realFeelBeanRemote;
        %>
        <%
        InitialContext ic = new InitialContext();
        realFeelBeanRemote = (RealFeelBeanRemote)ic.lookup("waggr.RealFeelBeanRemote");
        System.out.println(String.valueOf(session.getAttribute("userLogin")));
        System.out.println(String.valueOf(session.getAttribute("cityName")));
        System.out.println(String.valueOf(session.getAttribute("countryName")));
        System.out.println(Integer.parseInt(request.getParameter("temperature")));
        System.out.println(Integer.parseInt(request.getParameter("pressure")));
        System.out.println(Integer.parseInt(request.getParameter("humidity")));
        System.out.println(Float.parseFloat(request.getParameter("windSpeed")));
        if (realFeelBeanRemote.newRealFeel(
                String.valueOf(session.getAttribute("userLogin")),
                String.valueOf(session.getAttribute("cityName")),
                String.valueOf(session.getAttribute("countryName")),
                Integer.parseInt(request.getParameter("temperature")),
                Integer.parseInt(request.getParameter("pressure")),
                Integer.parseInt(request.getParameter("humidity")),
                Float.parseFloat(request.getParameter("windSpeed"))
        )) {
         RequestDispatcher rd = request.getRequestDispatcher("mainPage.jsp");
         rd.forward(request, response);
        } else {
         session.setAttribute("message", "Wrong weather data");
         session.setAttribute("backpage", "newRealFeel.jsp");
         RequestDispatcher rd = request.getRequestDispatcher("userError.jsp");
         rd.forward(request, response);
        }
        
        %>
    </body>
</html>
