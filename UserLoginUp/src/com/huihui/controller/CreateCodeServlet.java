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
		
		//7.��ֹ�����������������ͼƬ
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		
		//6.֪ͨ�ͻ�����ͼƬ�ķ�ʽ�򿪷��͹�ȥ������
		response.setHeader("Content-Type", "image/jpeg");
		
		//1.���ڴ��д���һ��ͼƬ
		BufferedImage image = new BufferedImage(50, 30, BufferedImage.TYPE_INT_RGB);
		
		//2.��ͼƬ��д����
		Graphics g = image.getGraphics();
		//���ñ���ɫ
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 50, 30);
		
		//3.����д�����ݵ���ɫ������
		g.setColor(Color.RED);
		g.setFont(new Font(null, Font.BOLD, 20));
		
		//4.��ͼƬ��д����
		String num = makenum();
		//��������ɵ���ֵ���浽session������
		request.getSession().setAttribute("checkCode", num);
		g.drawString(num, 0, 20);
		
		//5.��д�����ݵ�ͼƬ����������
		ImageIO.write(image, "jpg", response.getOutputStream());

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	public void init() throws ServletException {

	}
	
	//�ú����������4λ����
	public String makenum(){
		Random r = new Random();
		String num = r.nextInt(9999)+"";
		StringBuffer sb = new StringBuffer();
		//������������ֲ���4λ���;���ǰ�߲�0
		for(int i=0;i<4-num.length();i++){
			sb.append("0");
		}
		num = sb.toString()+num;
		return num;
	}

}
