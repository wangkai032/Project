package com.helloweenvsfei.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/FirstServlet")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.log("執行 doGet 方法....");
		this.execute(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.log("執行 doPost方法....");
		this.execute(request, response);
	}
	//傳回最後更新時間	
	public long getLastModified(HttpServletRequest request){
		this.log("執行 getLastModified 方法...");
		return -1;
	}

	private void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); //設定response編碼方式
		request.setCharacterEncoding("UTF-8"); //設定request編碼方式
		
		String requestURI = request.getRequestURI(); //存取該 Servlet的URI
		String method = request.getMethod(); //存取Servlet的方式 get或者post
		String param = request.getParameter("param");//用戶端傳送的參數 param值
		
		response.setContentType("text/html"); //設定文件類型為html類型
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.print("<HTML>");
		out.print(" <HEAD><TITLE> A Servlet </TITLE></HEAD>");
		out.print(" <BODY>");
		out.print(" 以 " + method + "方式存取該頁面。取到的 param參數為:" + param+ "<br/>" );
		
		out.print("<form action='"+requestURI+"' method='get'>"
				+ "<input type='text' name='parm' value='param string'>"
				+ "<input type='submit' value='以 get 方式查詢頁面 "+requestURI+"'></form>");
					
		out.print("<form action='"+requestURI+"' method='post'>"
				+ "<input type='text' name='parm' value='param string'>"
				+ "<input type='submit' value='以 post 方式傳送到頁面 "+requestURI+"'></form>");
	   
						
		//由用戶端瀏覽器讀取該文件的更新時間
		out.print(" <script>document.write('本業面最後更新時間:' + document.lastModified); </script>");
		
		out.println(" </BODY>");
		out.print("</HTML>");
		out.flush();
		out.close();
	}

	
}
