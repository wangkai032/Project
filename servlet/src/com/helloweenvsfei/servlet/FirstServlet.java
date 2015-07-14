package com.helloweenvsfei.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FirstServlet extends HttpServlet {
	
	private static final long serialVersionUID = 2386052823761867369L;
	
	/**
	 * �H GET �覡�s�������ɰ���Ө�ơC
	 * ���� doGet �e�|������ getLastModified�A�p�G�s�����o�{ getLastModified �Ǧ^���ƭ�
	 * �P�W���s���ɶǦ^���ƭȬۦP�A�h�{���Ӥ��S����s�A�s�����ĥΧ֨��O����Ӥ����� doGet�C
	 * �p�G getLastModified �Ǧ^ -1�A�h�{���O�ɨ��s���A�`�O����Ө�ơC
	 */ 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// �I�s HttpServlet ���a����x��ƿ�X��T�챱��x
		this.log("���� doGet ��k... ");

		// �B�z doGet
		this.execute(request, response);
	}

	/**
	 * �H POST �覡�s�������ɰ���Ө�ơC
	 */ 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.log("���� doPost ��k... ");
		
		// �B�z doPost
		this.execute(request, response);
	}
	
	/**
	 * �Ǧ^�� Servlet ���ͪ���󪺧�s�ɶ��C�� Get �覡�s�����ġC
	 * �Ǧ^���ɶ����۹�� 1970�~1��1��08:00:00 ���@��ơC
	 * �p�G�� -1 �h�{���O��ɧ�s�C�w�]�� -1�C
	 */
	@Override
	public long getLastModified(HttpServletRequest request) {
		
		this.log("���� getLastModified ��k... ");
		
		return 0;
	}

	private void execute(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		// �s���� Servlet �� URI
		String requestURI = request.getRequestURI();
		// �s���� Servlet ���覡�A�O GET �٬O POST
		String method = request.getMethod();
		// �Ȥ�ݶǰe���Ѽ� param ��
		String param = request.getParameter("param");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println("	�H " + method + " �覡�s���ӭ����C���쪺 param �ѼƬ��G" + param + "<br/>");
		
		out.println("	<form action='" + requestURI + "' method='get'><input type='text' name='param' value=''><input type='submit' value='�H GET �覡�s�� RequestServlet'></form>");
		out.println("	<form action='" + requestURI + "' method='post'><input type='text' name='param' value=''><input type='submit' value='�H POST �覡�s�� RequestServlet'></form>");
		
		// �ѫȤ���s����Ū���Ӥ�󪺧�s�ɶ�
		out.println("	<script>document.write('�������̫��s�ɶ��G' + document.lastModified + '<br />'); </script>");
		out.println("	<script>document.write('������URL�G' + location + '<br/>' ); </script>");
		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
		
	}

}
