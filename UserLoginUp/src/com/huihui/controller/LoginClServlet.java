package com.huihui.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.huihui.domain.User;
import com.huihui.service.UserService;

public class LoginClServlet extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("LoginClServlet已经初始化到内存..........");
		String path = this.getServletContext().getRealPath("count.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String count = br.readLine();
			this.getServletContext().setAttribute("count", count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			br = null;
		}
		
	}

	public LoginClServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
		System.out.println("LoginClServlet被销毁..........");
		String count = (String)this.getServletContext().getAttribute("count");
		String path = this.getServletContext().getRealPath("count.txt");
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(path);
			pw.write(count);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				pw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			pw = null;
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		System.out.println(username);
		String passwd = request.getParameter("passwd");
		String value = request.getParameter("iskeepinfo");

		String checkCode = request.getParameter("checkCode");
		HttpSession hs = request.getSession();
		String num = (String) hs.getAttribute("checkCode");

		// 保存用户的用户名到cookie，并将cookie保存到登陆的浏览器的缓存中
		if (value != null && "keep".equals(value)) {
			Cookie cookie = new Cookie("username", username);
			cookie.setMaxAge(2 * 24 * 3600);// 保存两周
			response.addCookie(cookie);// 回写给浏览器
		}

		//检查验证码是否输入正确
		if (checkCode != null && checkCode.equals(num)) {
			User user = new User();
			user.setUsername(username);
			user.setPasswd(passwd);

			UserService userservice = new UserService();
			boolean flag = userservice.checkUser(user);

			//检验用户名密码是否正确
			if (flag) {
				// 将用户信息存储到服务器的session对象中
				hs.setAttribute("user", user);

				//网站计数器
				String count = (String)this.getServletContext().getAttribute("count");
				if(count==null){
					this.getServletContext().setAttribute("count", "1");
				}else{
					this.getServletContext().setAttribute("count", String.valueOf((Integer.parseInt(count)+1)));
				}
				
				response.sendRedirect("main.jsp?username=" + username + "");
			} else {
				response.sendRedirect("index.jsp");
			}
		} else {
			
			response.sendRedirect("index.jsp");
			
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	public void init() throws ServletException {

	}

}
