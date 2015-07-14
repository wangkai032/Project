package com.helloweenvsfei.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/InitparamServlet")
public class InitparamServlet extends HttpServlet {

       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); //設定response編碼方式
		request.setCharacterEncoding("UTF-8");	//設定request編碼方式
		
		response.setContentType("text/html"); //設定類型為html類型
		
		PrintWriter out = response.getWriter(); 
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println(" <HEAD><TITLE>請登入檢視Notic檔案</TITLE></HEAD>");
		out.println("<style>body, td, div {font-size:12px; }</style>");
		out.println(" <BODY>");
		out.println("<form action='" + request.getRequestURI() + "' method='post'>");
		out.println("帳號:<input type='text' name='username' style='width:200px; '><br/>");
		out.println("密碼:<input type='password' name='password' style='width:200px; '> <br/><br/>");
		out.println("<input type='submit' value='登入'>");
		out.println("</form>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.println();
		out.println();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username"); //傳送的username參數
		String password = request.getParameter("password"); //傳送的password參數
		
		Enumeration<String> params = this.getInitParameterNames();
		
		while(params.hasMoreElements()){ //如果使用者名稱密碼比對則顯示
			String usernameParam = (String)params.nextElement();//參數名，及使用者名稱
			String passnameParam = getInitParameter(usernameParam);//參數值，及密碼
			
			if(usernameParam.equalsIgnoreCase(username) && passnameParam.equals(password)){
				request.getRequestDispatcher("/WebContent/identity.html").forward(request, response);
				return;
			}
		}
		this.doGet(request, response); //username, password 不比對，顯示登入頁面
	}

}
