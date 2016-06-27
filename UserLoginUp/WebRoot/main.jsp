<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="com.huihui.domain.User" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

<title>My JSP 'main.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<%
	String username = request.getParameter("username");
	User u = (User)request.getSession().getAttribute("user");
	if(u==null){
	//跳回到登陆界面
		request.getRequestDispatcher("index.jsp").forward(request, response);
		return;
	}
%>
<%
	String count = (String)this.getServletContext().getAttribute("count");
 %>
</head>

<body>
<center>
	<h1>
		欢迎<%=username%>的到来
	</h1>

	<%
		boolean b = false;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if ("lasttime".equals(name)) {
					b = true;
	%>
	你上次登陆的时间是：<%=cookie.getValue()%>
	<%
		SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String nowtime = sdf.format(new Date());
					cookie.setValue(nowtime);//把cooike的值设置为当前的时间
					cookie.setMaxAge(7 * 34 * 3600);//保存一周
					response.addCookie(cookie);//把cookie写会给浏览器
					break;
				}
			}
		}

		if (!b) {
	%>
	你是第一次登陆
	<%
		SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String nowtime = sdf.format(new Date());
			Cookie cookie = new Cookie("lasttime", nowtime);
			cookie.setMaxAge(7 * 24 * 3600);//保存一周
			response.addCookie(cookie);
		}
	%>

	&nbsp;&nbsp;&nbsp;<a href="index.jsp">返回到登陆界面</a>
	
	<hr>
	<h3>请选择你要进行的操作</h3>
	<a href="manager.jsp">管理用户</a>
	<br>
	<a href="UserClServlet?type=gotoAddUser">添加用户</a>
	<br>
	<a href="UserClServlet?type=gotoSelectUser">查找用户</a>
	<br>
	<a href="">退出系统</a>
	<br>
	<hr>
	该网页被访问了<%=count %>次
	</center>
</body>
</html>
