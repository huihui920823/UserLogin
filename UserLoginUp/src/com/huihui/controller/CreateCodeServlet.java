package com.huihui.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCodeServlet extends HttpServlet {

	public CreateCodeServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		
		//7.禁止浏览器缓存产生的随机图片
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		
		//6.通知客户端以图片的方式打开发送过去的数据
		response.setHeader("Content-Type", "image/jpeg");
		
		//1.在内存中创建一张图片
		BufferedImage image = new BufferedImage(50, 30, BufferedImage.TYPE_INT_RGB);
		
		//2.向图片上写数据
		Graphics g = image.getGraphics();
		//设置背景色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 50, 30);
		
		//3.设置写入数据的颜色和字体
		g.setColor(Color.RED);
		g.setFont(new Font(null, Font.BOLD, 20));
		
		//4.向图片上写数据
		String num = makenum();
		//把随机生成的数值保存到session对象中
		request.getSession().setAttribute("checkCode", num);
		g.drawString(num, 0, 20);
		
		//5.把写好数据的图片输出给浏览器
		ImageIO.write(image, "jpg", response.getOutputStream());

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	public void init() throws ServletException {

	}
	
	//该函数随机生成4位数字
	public String makenum(){
		Random r = new Random();
		String num = r.nextInt(9999)+"";
		StringBuffer sb = new StringBuffer();
		//如果产生的数字不够4位，就就在前边补0
		for(int i=0;i<4-num.length();i++){
			sb.append("0");
		}
		num = sb.toString()+num;
		return num;
	}

}
