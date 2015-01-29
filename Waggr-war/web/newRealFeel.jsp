<%-- 
    Document   : newRealFeel
    Created on : Jan 29, 2015, 3:42:37 PM
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
        <h2>Введите ощущения о погоде в городе <%=(String)session.getAttribute("cityName")%>, <%=(String)session.getAttribute("countryName")%>:</h2>
        <form action="doAddNewRealFeel.jsp" method="POST">
        <p>Температура: <input type="text" name="temperature" value="" /></p>
        <p>Влажность: <input type="text" name="humidity" value="" /></p>
        <p>Атмосферное давление: <input type="text" name="pressure" value="" /></p>
        <p>Скорость ветра: <input type="text" name="windSpeed" value="" /></p>
        <input type="submit" value="OK" />
    </body>
</html>
