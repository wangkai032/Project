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
		this.log("���� doGet ��k....");
		this.execute(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.log("���� doPost��k....");
		this.execute(request, response);
	}
	//�Ǧ^�̫��s�ɶ�	
	public long getLastModified(HttpServletRequest request){
		this.log("���� getLastModified ��k...");
		return -1;
	}

	private void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); //�]�wresponse�s�X�覡
		request.setCharacterEncoding("UTF-8"); //�]�wrequest�s�X�覡
		
		String requestURI = request.getRequestURI(); //�s���� Servlet��URI
		String method = request.getMethod(); //�s��Servlet���覡 get�Ϊ�post
		String param = request.getParameter("param");//�Τ�ݶǰe���Ѽ� param��
		
		response.setContentType("text/html"); //�]�w���������html����
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.print("<HTML>");
		out.print(" <HEAD><TITLE> A Servlet </TITLE></HEAD>");
		out.print(" <BODY>");
		out.print(" �H " + method + "�覡�s���ӭ����C���쪺 param�ѼƬ�:" + param+ "<br/>" );
		
		out.print("<form action='"+requestURI+"' method='get'>"
				+ "<input type='text' name='parm' value='param string'>"
				+ "<input type='submit' value='�H get �覡�d�߭��� "+requestURI+"'></form>");
					
		out.print("<form action='"+requestURI+"' method='post'>"
				+ "<input type='text' name='parm' value='param string'>"
				+ "<input type='submit' value='�H post �覡�ǰe�쭶�� "+requestURI+"'></form>");
	   
						
		//�ѥΤ���s����Ū���Ӥ�󪺧�s�ɶ�
		out.print(" <script>document.write('���~���̫��s�ɶ�:' + document.lastModified); </script>");
		
		out.println(" </BODY>");
		out.print("</HTML>");
		out.flush();
		out.close();
	}

	
}
