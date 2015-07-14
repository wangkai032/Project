package com.helloweenvsfei.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PostServlet
 */
//@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//GET�ɰ���Ӥ�k
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println("�Шϥ�post�覡�ǰe��ơC"); //��X���ܸ�T
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UFT-8");
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		
		int age = 0;
		try{
			age = Integer.parseInt(request.getParameter("age")); //��o�~�֡A����Ƭ�int
		}catch(Exception e){}
		
		Date birthday = null;
		try{
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //��o�ͤ�A��Ƭ�date
			
			birthday = format.parse(request.getParameter("birthday"));
		}catch(Exception e){}
		
		String[] interesting = request.getParameterValues("interesting"); //��o�h�ӭ�
		String area = request.getParameter("area"); //���U�Ԧ��M���� select������
		String description = request.getParameter("description"); //�q��r�� textarea������
		String btn = request.getParameter("btn"); //��o���U�������
		response.setContentType("text/html"); //�]�w��X����
		PrintWriter out = response.getWriter(); //��oout����
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("<HEAD><TITLE>�P�±z�ǰe��T</TITLE></HEAD>");
		out.println("<style>");
		out.println("body, div, td, input {font-size:12px; margin:0px; }");
		out.println(".line {margin:2px; }");
		out
				.println(".leftDiv {width:110px; float:left; height:22px; line-height:22px; font-weight:bold; }");
		out.println(".rightDiv {height:22px; line-height:22px; }");
		out.println(".button {");
		out.println("	color:#fff;");
		out.println("	font-weight:bold;");
		out.println("	font-size: 11px; ");
		out.println("	text-align:center;");
		out.println("	padding:.17em 0 .2em .17em;");
		out.println("	border-style:solid;");
		out.println("	border-width:1px;");
		out.println("	border-color:#9cf #159 #159 #9cf;");
		out
				.println("	background:#69c url(/servlet/images/bg-btn-blue.gif) repeat-x;");
		out.println("</style>");
		out.println("</HEAD>");
		
		out.println("<div align=\"center\"><br/>");
		out.println("<fieldset style='width:90%'><legend>��g�ϥΪ̸�T</legend><br/>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>�z���m�W:</div>");
		out.println("		<div align='left' class='rightDiv'>" + name + "</div>");
		out.println("   </div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>�z���K�X:</div>");
		out.println("		<div align='left' class='rightDiv'>" + password + "</div>");
		out.println("	</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>�z���ʧO:</div>");
		out.println("		<div align='left' class='rightDiv'>" + sex + "</div>");
		out.println("	</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>�z���~��:</div>");
		out.println("		<div align='left' class='rightDiv'>" + age + "</div>");
		out.println("	</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>�z���ͤ�:</div>");
		out.println("		<div align='left' class='rightDiv'>");
		out.println(new SimpleDateFormat("yyy�~MM��dd��").format(birthday));
		out.println("		</div>");
		out.println("	</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>�z������:</div>");
		out.println("		<div align='left' class='rightDiv'>");
		
		if(interesting != null)
			for(String str : interesting){
				 out.println(str + ", "); 
			}
		
		out.println(" 	</div>");
		out.println("</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>�ۧڴy�z:</div>");
		out.println("		<div align='left' class='leftDiv'>" + description + "</div>");
		out.println("	</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>���s���:</div>");
		out.println("		<div align='left' class='rightDiv'>" + btn + "</div>");
		out.println("	</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'></div>");
		out.println("		<div align='left' class='rightDiv'>");
		out.println("			<br/><input type='button' name='btn' value='�Ǧ^�W�@��' "
				+ "onclick='history.go(-1);' class='button'><br/>");
		out.println("	</div>");
		out.println("	</div>");
		out.println("<BODY>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
		
	}
}
