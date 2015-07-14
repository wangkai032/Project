package com.helloweenvsfei.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostServlet extends HttpServlet {

	private static final long serialVersionUID = 2112378505872783022L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println("�Шϥ� POST �覡�ǰe��ơC");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// �q ��r�� text �����m�W
		String name = request.getParameter("name");
		// �q �K�X�� password �����K�X
		String password = request.getParameter("password");
		// �q ���� checkbox �����ʧO
		String sex = request.getParameter("sex");

		int age = 0;
		try {
			// �� �~��. �ݭn�� �r�� �ഫ�� int.
			// �p�G�榡����|�ߥX NumberFormattingException
			age = Integer.parseInt(request.getParameter("age"));
		} catch (Exception e) {
		}

		Date birthday = null;
		try {
			// �� �ͤ�. �ݭn�� �r�� ��Ƭ� Date.
			// �p�G�榡����|�ߥX ParseException
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			birthday = format.parse(request.getParameter("birthday"));
		} catch (Exception e) {
		}

		// �q �h��� checkbox �����h�ӭ�
		String[] interesting = request.getParameterValues("interesting");
		// �q �U�Ԯ� select ������
		String area = request.getParameter("area");
		// �q ��r�� textarea ������
		String description = request.getParameter("description");

		// �� �ǰe���s �����
		String btn = request.getParameter("btn");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("<HEAD><TITLE>�P�±z�ǰe��T</TITLE>");
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

		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>�z���m�W�G</div>");
		out.println("			<div align='left' class='rightDiv'>" + name + "</div>");
		out.println("		</div>");

		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>�z���K�X�G</div>");
		out.println("			<div align='left' class='rightDiv'>" + password
				+ "</div>");
		out.println("		</div>");

		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>�z���ʧO�G</div>");
		out.println("			<div align='left' class='rightDiv'>" + sex + "</div>");
		out.println("		</div>");

		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>�z���~�֡G</div>");
		out.println("			<div align='left' class='rightDiv'>" + age + "</div>");
		out.println("		</div>");

		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>�z���ͤ�G</div>");
		out.println("			<div align='left' class='rightDiv'>");
		out.println(new SimpleDateFormat("yyyy�~MM��dd��").format(birthday));
		out.println("			</div>");
		out.println("		</div>");

		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>�z������G</div>");
		out.println("			<div align='left' class='rightDiv'>");

		if (interesting != null)
			for (String str : interesting) {
				out.println(str + ", ");
			}

		out.println("			</div>");
		out.println("		</div>");

		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>�ۧڴy�z�G</div>");
		out.println("			<div align='left' class='rightDiv'>" + description
				+ "</div>");
		out.println("		</div>");

		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>���s��ȡG</div>");
		out.println("			<div align='left' class='rightDiv'>" + btn + "</div>");
		out.println("		</div>");

		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'></div>");
		out.println("			<div align='left' class='rightDiv'>");
		out
				.println("				<br/><input type='button' name='btn' value='�Ǧ^�W�@��' onclick='history.go(-1); ' class='button'><br/>");
		out.println("			</div>");
		out.println("		</div>");

		out.println("<BODY>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}
