<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎页面</title>
</head>
<body>
<h1>欢迎你</h1>

<a href = "spring">点此请求DispatcherServlet并跳转</a><br>

<a href ="add" style="color:red; font-size:3em"> HA you good! hah! </a>  <br>

<a href ="twitter" style="color:blue; font-size:3em"> Twitter</a>  <br>

<form action="login" method = "post">
username:<input type = "text" name = "username">
<br>
password:<input type = "password" name = "password">
<br>
<input type = "submit" value = "提交">
</form>
</body>
</html>