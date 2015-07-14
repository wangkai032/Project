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
		response.setCharacterEncoding("UTF-8"); //�]�wresponse�s�X�覡
		request.setCharacterEncoding("UTF-8");	//�]�wrequest�s�X�覡
		
		response.setContentType("text/html"); //�]�w������html����
		
		PrintWriter out = response.getWriter(); 
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println(" <HEAD><TITLE>�еn�J�˵�Notic�ɮ�</TITLE></HEAD>");
		out.println("<style>body, td, div {font-size:12px; }</style>");
		out.println(" <BODY>");
		out.println("<form action='" + request.getRequestURI() + "' method='post'>");
		out.println("�b��:<input type='text' name='username' style='width:200px; '><br/>");
		out.println("�K�X:<input type='password' name='password' style='width:200px; '> <br/><br/>");
		out.println("<input type='submit' value='�n�J'>");
		out.println("</form>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.println();
		out.println();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username"); //�ǰe��username�Ѽ�
		String password = request.getParameter("password"); //�ǰe��password�Ѽ�
		
		Enumeration<String> params = this.getInitParameterNames();
		
		while(params.hasMoreElements()){ //�p�G�ϥΪ̦W�ٱK�X���h���
			String usernameParam = (String)params.nextElement();//�ѼƦW�A�ΨϥΪ̦W��
			String passnameParam = getInitParameter(usernameParam);//�ѼƭȡA�αK�X
			
			if(usernameParam.equalsIgnoreCase(username) && passnameParam.equals(password)){
				request.getRequestDispatcher("/WebContent/identity.html").forward(request, response);
				return;
			}
		}
		this.doGet(request, response); //username, password �����A��ܵn�J����
	}

}
