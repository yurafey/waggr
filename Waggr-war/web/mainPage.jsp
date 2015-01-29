<%-- 
    Document   : mainPage
    Created on : Jan 28, 2015, 4:15:36 AM
    Author     : URI
--%>

<%@page import="waggr.WeatherBeanRemote"%>
<%@page import="waggr.Weatheryan"%>
<%@page import="waggr.Weatherwua"%>
<%@page import="java.util.List"%>
<%@page import="waggr.WaggrSessionRemote"%>
<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" %>
<%@page pageEncoding="windows-1251" %>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>Waggr Main Page</title>

        <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 1px;   
        }
        ul {
        list-style: none;
        }
        li {
            display: inline-block;
            width: 49%;
            vertical-align: top;
        }
        </style>

    </head>
    <body>
        <%!
            WaggrSessionRemote waggrSessionRemote;
            WeatherBeanRemote weatherRemote;
            String userLogin;
            String userPassword;
        %>

       <%
        InitialContext ic = new InitialContext();
        waggrSessionRemote = (WaggrSessionRemote)ic.lookup("waggr.WaggrSessionRemote");
        String userLogin = session.getAttribute("userLogin")==null?null:(String)session.getAttribute("userLogin"); 
        String userPassword = session.getAttribute("userPassword")==null?null:(String)session.getAttribute("userPassword"); 
        if (userLogin==null||userPassword==null) {
            userLogin = request.getParameter("userLogin")==null?null:(String)request.getParameter("userLogin"); 
            userPassword = request.getParameter("userPassword")==null?null:(String)request.getParameter("userPassword"); 
            if(userLogin==null||userPassword==null) {
                session.setAttribute("message", "Log in system!");
                session.setAttribute("backpage", "index.jsp");
                RequestDispatcher rd = request.getRequestDispatcher("userError.jsp");
                rd.forward(request, response);
            }
        } 
        
        if (waggrSessionRemote.doAuthorization(userLogin, userPassword) == false) {
            session.setAttribute("message", "Wrong user name or password");
            session.setAttribute("backpage", "index.jsp");
            response.sendRedirect("userError.jsp"); 
        } else { 
            session.setAttribute("userLogin", userLogin);
            session.setAttribute("userPassword", userPassword);
            session.setAttribute("cityName", waggrSessionRemote.getUserCurrentCityName(userLogin));
            session.setAttribute("countryName", waggrSessionRemote.getUserCurrentCountryName(userLogin));
        }
        weatherRemote = (WeatherBeanRemote)ic.lookup("waggr.WeatherBeanRemote");
        List<List<String>> weatherCurrentTable = weatherRemote.getCurrentTableByLogin(userLogin);
        List<List<String>> weatherYanTable = weatherRemote.getYanTableByLogin(userLogin);
        List<List<String>> weatherWuaTable = weatherRemote.getWuaTableByLogin(userLogin);
        %>


        <h2>Пользователь:<%=userLogin%></h2>
        <form action="doLogout.jsp" method="POST">
              <p><input type="submit" value="Выйти"></p>
        </form>
        
        <h2>Избранный город:</h2>    
        <form action="doSaveUserCityCountry.jsp" method="POST">
        <p>Город: <input type="text" name="userCity" value="<%=waggrSessionRemote.getUserCurrentCityName(userLogin)%>" /></p>
        <p>Страна: <input type="text" name="userCountry" value="<%=waggrSessionRemote.getUserCurrentCountryName(userLogin)%>" /></p>
        <input type="submit" value="Сохранить / Найти прогноз" />
        </form>
        <br>
        <form action="newRealFeel.jsp" method="POST">
        <input type="submit" value="Добавить свое ощущение о погоде" />
        </form>
        
        <h2>Текущая погода в городе 
            <%=waggrSessionRemote.getUserCurrentCityName(userLogin)%>, <%=waggrSessionRemote.getUserCurrentCountryName(userLogin)%>:
        </h2>
         <ul>
            <li>
            <table> 
                <tr>
                    <th></th>
                    <th>Yandex</th>		
                    <th>WUA</th>
                    <th>RealFeel</th>
                </tr>
                    <%
                    System.out.println("FOR1");
                    for(int y = 0;y<weatherCurrentTable.size();y++){ 
                    %>
                    <tr>    
                    <%  List<String> row = weatherCurrentTable.get(y);
                        for(int x = 0; x < row.size(); x++) {
                            %>
                            <td>
                                <%= row.get(x).toString()%>
                            </td>
                            <%
                        } %>
                        </tr>
                        <%
                    }
                    %>
            </table>
            </li>
        </ul>
        <h2>Прогноз погоды в городе 
            <%=waggrSessionRemote.getUserCurrentCityName(userLogin)%>, <%=waggrSessionRemote.getUserCurrentCountryName(userLogin)%>:
        </h2>
        <ul>
            <li>
        <table>
            <caption style="font-size:120%">Yandex</caption>
            <tr>
                <th>Дата</th>
                <th>Температура</th>
                <th>Влажность</th>
                <th>Атмосферное давление</th>
                <th>Скорость ветра</th>
                <th>Направление ветра</th>
            </tr>
            <%for(int y=0;y<weatherYanTable.size();y++){ %> 
            <tr>
              <%List<String> row = weatherYanTable.get(y);
              for(int x=0;x<row.size();x++){ %>
                <td><%=row.get(x)%></td>
              <%}%>
            </tr>
            <%}%>
        </table>
            </li>
            <li>
        <table>
            <caption style="font-size:120%">WUA</caption>
            <tr>
                <th>Дата</th>
                <th>Температура</th>
                <th>Влажность</th>
                <th>Атмосферное давление</th>
                <th>Скорость ветра</th>
                <th>Направление ветра</th>
            </tr>
            <%for(int y=0;y<weatherWuaTable.size();y++){ %> 
            <tr>
              <%List<String> row = weatherWuaTable.get(y);
              for(int x=0;x<row.size();x++){ %>
                <td><%=row.get(x)%></td>
              <%}%>
            </tr>
            <%}%>
        </table>
        </li>
        </ul>
    </body>
</html>
