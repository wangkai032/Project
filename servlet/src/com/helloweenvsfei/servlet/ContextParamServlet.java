package com.helloweenvsfei.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContextParamServlet extends HttpServlet {

	private static final long serialVersionUID = 3194071196406358461L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>讀取文件參數</TITLE></HEAD>");
		out.println("  <link rel='stylesheet' type='text/css' href='../css/style.css'>");
		out.println("  <BODY>");
		out.println("<div align=center><br/>");
		out.println("<fieldset style='width:90%'><legend>所有的文件參數</legend><br/>");

		ServletContext servletContext = this.getServletConfig().getServletContext();
		
		String uploadFolder = servletContext.getInitParameter("upload folder");
		String allowedFileType = servletContext.getInitParameter("allowed file type");

		out.println("<div class='line'>");
		out.println("	<div align='left' class='leftDiv'>上傳檔案夾</div>");
		out.println("	<div align='left' class='rightDiv'>" + uploadFolder + "</div>");
		out.println("</div>");

		out.println("<div class='line'>");
		out.println("	<div align='left' class='leftDiv'>實際磁碟路徑</div>");
		out.println("	<div align='left' class='rightDiv'>" + servletContext.getRealPath(uploadFolder) + "</div>");
		out.println("</div>");
		
		out.println("<div class='line'>");
		out.println("	<div align='left' class='leftDiv'>允許上傳的類型</div>");
		out.println("	<div align='left' class='rightDiv'>" + allowedFileType + "</div>");
		out.println("</div>");

		out.println("</fieldset></div>");
		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}
