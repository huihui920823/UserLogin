<%@page import="com.huihui.service.UserService"%>
<%@page import="com.huihui.domain.User"%>
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

<title>My JSP 'manager.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<script type="text/javascript" language="javascript">
	function confirmOper(){ return window.confirm("真的要删除该用户吗？")};
	function gotoPageNow(){
		var pageNow = document.getElementById("pagenow");
		window.alert("pageNow="+pageNow);
	};
</script>
<body>
	<h1>管理用户</h1>
	<a href="main.jsp">返回主菜单</a>
	<hr>

	<%!//定义分也需要的变量
	int pageNow = 1;//当前页
	int pageSize = 3;//每页显示的记录数
	int pageCount = 0;//总页数（通过计算得到）
	int rowCount = 0;//总的记录数（从数据库中查询得到）%>

	<%
		//当前页
		if (request.getParameter("pageNow") != null) {
			pageNow = Integer.parseInt(request.getParameter("pageNow"));
		}

		//总的记录数
		rowCount = new UserService().getRowCount();

		//总页数
		pageCount = rowCount % pageSize == 0 ? rowCount / pageSize
				: rowCount / pageSize + 1;
	%>

	<table border=1 bordercolor="green" cellspacing="0" width="700px">
		<tr>
			<th>用户ID</th>
			<th>用户名</th>
			<th>用户邮箱</th>
			<th>用户级别</th>
			<th>用户密码</th>
			<th>删除用户</th>
			<th>修改用户</th>
		</tr>

		<%
			UserService userService = new UserService();
			ArrayList<User> list = userService.getUserInfo(pageNow, pageSize);
			for (int i = 0; i < list.size(); i++) {

				User user = list.get(i);
		%>
		<tr>
			<td><%=user.getId()%></td>
			<td><%=user.getUsername()%></td>
			<td><%=user.getEmail()%></td>
			<td><%=user.getGrade()%></td>
			<td><%=user.getPasswd()%></td>
			<td><a onclick="confirmOper()" href="/UserLoginUp/UserClServlet?type=del&id=<%=user.getId()%>">删除用户</a>
			</td>
			<td><a href="/UserLoginUp/UserClServlet?type=gotoUpdateUser&id=<%=user.getId()%>">修改用户</a>
			</td>
		</tr>

		<%
			}
		%>

	</table>

	<%
		if (pageNow != 1) {
	%>
	<a href="manager.jsp?pageNow=<%=pageNow - 1%>">上一页</a>
	<%
		}
	%>

	<%
		for (int i = 1; i <= pageCount; i++) {
	%>
	<a href="manager.jsp?pageNow=<%=i%>"><%=i%></a>
	<%
		}
	%>

	<%
		if (pageNow != pageCount) {
	%>
	<a href="manager.jsp?pageNow=<%=pageNow + 1%>">下一页</a>
	<%
		}
	%>
	
	&nbsp;&nbsp;&nbsp;当前页<%=pageNow %>/总页数<%=pageCount %><br>
	跳转到<input type="text" id="pagenow" name="pageNow"> <input type="button" value="跳" onclick="gotoPageNow()">
</body>
</html>
