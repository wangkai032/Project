package com.helloweenvsfei.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitParamServlet extends HttpServlet {

	private static final long serialVersionUID = 7298032096933866458L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>�еn�J�˵� Notice �ɮ�</TITLE></HEAD>");
		out.println("<style>body, td, div {font-size:12px; }</style>");
		out.println("  <BODY>");
		
		out.println("<form action='" + request.getRequestURI() + "' method='post'>");
		out.println("�b���G<input type='text' name='username' style='width:200px; '> <br/>");
		out.println("�K�X�G<input type='password' name='password' style='width:200px; '> <br/><br/>");
		out.println("<input type='submit' value='  �n�J  '>");
		out.println("</form>");
		
		if(true){
			out.println("<br/><br/><br/><br/><br/><br/><br/>�ϥΪ̦W�١B�K�X���G<br/>");
			Enumeration params = this.getInitParameterNames();
			while(params.hasMoreElements()){
				String usernameParam = (String)params.nextElement();
				String passnameParam = this.getInitParameter(usernameParam);
				out.println("[" + usernameParam + ", " + passnameParam + "], ");
			}
		}
		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �ǰe�� username �Ѽ�
		String username = request.getParameter("username");
		// �ǰe�� password �Ѽ�
		String password = request.getParameter("password");
		// ���Ҧ�����l�ưѼƦW��
		Enumeration params = this.getInitParameterNames();
		while(params.hasMoreElements()){
			String usernameParam = (String)params.nextElement();
			// ���Ѽƭ�
			String passnameParam = this.getInitParameter(usernameParam);
			// �p�G username ���B password ���. username �j�p�g���ӷP�Apassword�j�p�g�ӷP
			if(usernameParam.equalsIgnoreCase(username)
					&& passnameParam.equals(password)){
				// ����ɮסC/WEB-INF �U���ɮפ���z�L�s�����s����A�]���O�w����
				request.getRequestDispatcher("/WEB-INF/notice.html").forward(request, response);
				return;
			}
		}
		// username�Apassword �����A��ܵn�J����
		this.doGet(request, response);		
	}

}
