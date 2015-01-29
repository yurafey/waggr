<%-- 
    Document   : authorizationPage
    Created on : Jan 28, 2015, 3:59:11 AM
    Author     : URI
--%>

<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>Waggr Authorization</title>
    </head>
    <body>
        <h1>Авторизация пользователя:</h1>
        <form action="mainPage.jsp" method="POST">
        <p>Login: <input type="text" name="userLogin" value="" /></p>
        <p>Password: <input type="text" name="userPassword" value="" /></p>
        <input type="submit" value="Войти" />
        <p><a href="newUser.jsp">Зарегистрировать нового пользователя</a></p>
    </body>
</html>
