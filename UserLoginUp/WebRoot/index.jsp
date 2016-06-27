<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
  </head>
  
  <%!String username=null; %>
  
  <%
  	Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("username".equals(cookie.getName())) {
					username = cookie.getValue();
				}
			}
		}
   %>
  
  <body>
  <center>
    <h1>用户登录</h1>
    <hr>
    <form action="/UserLoginUp/LoginClServlet" method="post">
    <table border="0">
    <tr>
    <td>
    	用户名：
    	</td><td><input type="text" name="username" value=<%=username %> ></input></td>
    	</tr>
    	<tr>
    	<td>
    	密码：
    	</td><td><input type="password" name="passwd"/></td>
    	</tr>
    	
    	<tr>
    	<td>
    	验证码：
    	</td>
    	<td><input type="text" name="checkCode"/></td>
    	<td><img src="/UserLoginUp/CreateCodeServlet"/></td>
    	</tr>
    	<tr>
    	<td>
    	<input type="submit" value="登陆"/>
    	</td>
    	<td>
    	<input type="reset" value="重置"/>
    	</td>
    	</tr>
    	</table>
    	
    	<input type="checkbox" name="iskeepinfo" value="keep">在此电脑上保留用户名
    </form>
    </center>
  </body>
</html>
