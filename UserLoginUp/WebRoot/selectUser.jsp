<%@page import="com.huihui.service.UserService"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.huihui.domain.User"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'selectUser.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>
<%!int pageNow = 1;
	int pageSize = 3;
	int pageCount = 0;
	int rowCount = 0;%>


<body>
	<h1>查找用户信息</h1>
	<a href="main.jsp">返回到主界面</a>
	<hr>
	<center>
		<form action="/UserLoginUp/UserClServlet?type=select" method="post">
			请输入用户名：<input type="text" name="username"><input
				type="submit" value="查找"><br> <input type="radio"
				name="model" value="like" checked>模糊查询 <input type="radio"
				name="model" value="enter">精确查询
		</form>


		<%
			if (request.getAttribute("list") != null) {
				if (request.getParameter("pageNow") != null) {
					pageNow = Integer.parseInt(request.getParameter("pageNow"));
				}
				rowCount = (Integer)request.getAttribute("rowCount");
						
				pageCount = rowCount%pageSize==0?rowCount/pageSize:rowCount/pageSize+1;

				ArrayList<User> list = (ArrayList<User>) request
						.getAttribute("list");
		%>
		<table border="1" bordercolor="green" cellspacing="0" width="700px">
			<tr>
				<td>用户ID</td>
				<td>用户名</td>
				<td>Email</td>
				<td>用户等级</td>
				<td>密码</td>
			</tr>
			<%
				for (int i = 0; i < list.size(); i++) {
						User user = list.get(i);
			%>
			<tr>
				<td><%=user.getId()%></td>
				<td><%=user.getUsername()%></td>
				<td><%=user.getEmail()%></td>
				<td><%=user.getGrade()%></td>
				<td><%=user.getPasswd()%></td>
			</tr>
			<%
				}
				%>
				
			

		</table>
		<%
			if (pageNow != 1) {
		%>
		<a href="selectUser.jsp?pageNow=<%=pageNow - 1%>">上一页</a>
		<%
			}
		%>

		<%
			for (int i = 1; i <= pageCount; i++) {
		%>
		<a href="selectUser.jsp?pageNow=<%=i%>"><%=i%></a>
		<%
			}
		%>

		<%
			if (pageNow != pageCount) {
		%>
		<a href="selectUser.jsp?pageNow=<%=pageNow + 1%>">下一页</a>
		<%
			}
		%>
		<%
		}
		 %>
	</center>
</body>
</html>
