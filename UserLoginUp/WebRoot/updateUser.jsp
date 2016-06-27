<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.huihui.domain.User" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'updateUser.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <h1>修改用户信息</h1>
  <hr>
  <%
  	User user = (User)request.getAttribute("user");
   %>
  <form action="/UserLoginUp/UserClServlet?type=update" method="post">
   <table border=1 bordercolor="green" cellspacing="0" width="250px">
   	<tr>
   		<td>用户ID</td>
   		<td><input type="text" name="id" readonly value=<%=user.getId() %>></td>
   	</tr>
   		<tr>
   		<td>用户名</td>
   		<td><input type="text" name="username"  value=<%=user.getUsername() %>></td>
   	</tr>
   		<tr>
   		<td>Email</td>
   		<td><input type="text" name="email" value=<%=user.getEmail() %> ></td>
   	</tr>
   		<tr>
   		<td>用户等级</td>
   		<td><input type="text" name="grade" value=<%=user.getGrade() %> ></td>
   	</tr>
   		<tr>
   		<td>密码</td>
   		<td><input type="text" name="passwd" value=<%=user.getPasswd() %> ></td>
   	</tr>
   	<tr>
   		<td><input type="submit" value="修改用户"></td>
   		<td><input type="reset" value="重置"></td>
   	</tr>
   </table>
   </form>
  </body>
</html>
