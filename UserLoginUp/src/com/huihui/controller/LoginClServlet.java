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
		System.out.println("LoginClServlet�Ѿ���ʼ�����ڴ�..........");
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
		System.out.println("LoginClServlet������..........");
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

		// �����û����û�����cookie������cookie���浽��½��������Ļ�����
		if (value != null && "keep".equals(value)) {
			Cookie cookie = new Cookie("username", username);
			cookie.setMaxAge(2 * 24 * 3600);// ��������
			response.addCookie(cookie);// ��д�������
		}

		//�����֤���Ƿ�������ȷ
		if (checkCode != null && checkCode.equals(num)) {
			User user = new User();
			user.setUsername(username);
			user.setPasswd(passwd);

			UserService userservice = new UserService();
			boolean flag = userservice.checkUser(user);

			//�����û��������Ƿ���ȷ
			if (flag) {
				// ���û���Ϣ�洢����������session������
				hs.setAttribute("user", user);

				//��վ������
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
