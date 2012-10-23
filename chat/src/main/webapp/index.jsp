<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat Senac</title>
    </head>
    <body>
        <h1>Chat Senac</h1>
        
        <p>
            <form action=LoginServlet>
                <input name="login" size=30 ><input type=submit value=Login>
            </form></p>
            <%@ page import="java.util.*" %>
            <h5>Hora Servidor: <%= new Date() %></h5>
    </body>
</html>
