package com.huihui.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huihui.domain.User;
import com.huihui.service.UserService;
import com.huihui.util.SqlHelper;

public class UserClServlet extends HttpServlet {

	public UserClServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		request.setCharacterEncoding("utf-8");
		String type = request.getParameter("type");
		UserService userService = new UserService();
		if ("del".equals(type)) {
			String id = request.getParameter("id");
			if (userService.delUser(id)) {
				request.getRequestDispatcher("ok.html").forward(request,
						response);
			} else {
				request.getRequestDispatcher("error.html").forward(request,
						response);
			}
		} else if ("gotoUpdateUser".equals(type)) {
			String id = request.getParameter("id");
			User user = userService.selectUser(id);

			request.setAttribute("user", user);
			request.getRequestDispatcher("updateUser.jsp").forward(request,
					response);

		} else if ("update".equals(type)) {
			// 这里省略了对用户提交的数据进行验证
			String id = request.getParameter("id");
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String grade = request.getParameter("grade");
			String passwd = request.getParameter("passwd");
			System.out.println("id:" + id + ",username:" + username + ",email:"
					+ email + ",grade:" + grade + ",passwd:" + passwd);
			User user = new User(Integer.parseInt(id), username, email,
					Integer.parseInt(grade), passwd);

			if (userService.updateUser(user)) {
				request.getRequestDispatcher("ok.html").forward(request,
						response);
			} else {
				request.getRequestDispatcher("error.html").forward(request,
						response);
			}
		} else if ("gotoAddUser".equals(type)) {
			request.getRequestDispatcher("addUser.jsp").forward(request,
					response);
		} else if ("add".equals(type)) {
			// 这里省略了对用户提交的数据进行验证
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String grade = request.getParameter("grade");
			String passwd = request.getParameter("passwd");

			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setGrade(Integer.parseInt(grade));
			user.setPasswd(passwd);

			if (userService.addUser(user)) {
				request.getRequestDispatcher("ok.html").forward(request,
						response);
			} else {
				request.getRequestDispatcher("error.html").forward(request,
						response);
			}
		} else if ("gotoSelectUser".equals(type)) {
			request.getRequestDispatcher("selectUser.jsp").forward(request,
					response);
		} else if ("select".equals(type)) {
			// 这里省略了对用户提交的数据进行验证
			ArrayList<User> list = null;
			String username = request.getParameter("username");
			String model = request.getParameter("model");
			int rowCount = 0;
			if (model != null && username != null) {
				if ("like".equals(model)) {
					list = userService.selectUserByName2(username);
					rowCount = userService.selectUserByNameCount(username);
				} else if ("enter".equals(model)) {
					list = userService.selectUserByName1(username);
					rowCount = 1;
				}
				request.setAttribute("list", list);
				request.setAttribute("rowCount", rowCount);
				request.getRequestDispatcher("selectUser.jsp").forward(request,
						response);
			} else {
				request.getRequestDispatcher("error.html").forward(request,
						response);
			}
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	public void init() throws ServletException {

	}

}
