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
		//GET時執行該方法
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println("請使用post方式傳送資料。"); //輸出提示資訊
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UFT-8");
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		
		int age = 0;
		try{
			age = Integer.parseInt(request.getParameter("age")); //獲得年齡，並轉化為int
		}catch(Exception e){}
		
		Date birthday = null;
		try{
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //獲得生日，轉化為date
			
			birthday = format.parse(request.getParameter("birthday"));
		}catch(Exception e){}
		
		String[] interesting = request.getParameterValues("interesting"); //獲得多個值
		String area = request.getParameter("area"); //取下拉式清單方塊 select中取值
		String description = request.getParameter("description"); //從文字域 textarea中取值
		String btn = request.getParameter("btn"); //獲得按下的按鍵值
		response.setContentType("text/html"); //設定輸出類型
		PrintWriter out = response.getWriter(); //獲得out物件
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("<HEAD><TITLE>感謝您傳送資訊</TITLE></HEAD>");
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
		out.println("<fieldset style='width:90%'><legend>填寫使用者資訊</legend><br/>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>您的姓名:</div>");
		out.println("		<div align='left' class='rightDiv'>" + name + "</div>");
		out.println("   </div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>您的密碼:</div>");
		out.println("		<div align='left' class='rightDiv'>" + password + "</div>");
		out.println("	</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>您的性別:</div>");
		out.println("		<div align='left' class='rightDiv'>" + sex + "</div>");
		out.println("	</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>您的年齡:</div>");
		out.println("		<div align='left' class='rightDiv'>" + age + "</div>");
		out.println("	</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>您的生日:</div>");
		out.println("		<div align='left' class='rightDiv'>");
		out.println(new SimpleDateFormat("yyy年MM月dd日").format(birthday));
		out.println("		</div>");
		out.println("	</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>您的興趣:</div>");
		out.println("		<div align='left' class='rightDiv'>");
		
		if(interesting != null)
			for(String str : interesting){
				 out.println(str + ", "); 
			}
		
		out.println(" 	</div>");
		out.println("</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>自我描述:</div>");
		out.println("		<div align='left' class='leftDiv'>" + description + "</div>");
		out.println("	</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'>按鈕鍵值:</div>");
		out.println("		<div align='left' class='rightDiv'>" + btn + "</div>");
		out.println("	</div>");
		out.println("	<div class='line'>");
		out.println("		<div align='left' class='leftDiv'></div>");
		out.println("		<div align='left' class='rightDiv'>");
		out.println("			<br/><input type='button' name='btn' value='傳回上一頁' "
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
