<%-- 
    Document   : newuser
    Created on : Jan 28, 2015, 3:07:41 AM
    Author     : URI
--%>

<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
 "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
 <title>Waggr application</title>
 </head>
 <body>
 <h1>Register a new user</h1>
 <form action="doAddNewUser.jsp" method="POST">
 <p>Имя: <input type="text" name="userName" value="" /></p>
 <p>Фамилия: <input type="text" name="userSurname" value="" /></p>
 <p>Логин: <input type="text" name="userLogin" value="" /></p>
 <p>Пароль: <input type="text" name="userPassword" value="" /></p>
 <p>Город: <input type="text" name="userCity" value="" /></p>
 <p>Страна: <input type="text" name="userCountry" value="" /></p>
 <input type="submit" value="Зарегистрировать пользователя" />
 </form>
 </body>
</html>