package com.helloweenvsfei.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardServlet extends HttpServlet {

	private static final long serialVersionUID = -291840563095094360L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String destination = request.getParameter("destination");
		
		// ���D�� /WEB-INF/web.xml�C�z�L�a�}���J���}�O����s������ɮת��A���O forward �i�H
		if("file".equals(destination)){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/web.xml");
			dispatcher.forward(request, response);
		}
		// ���D�� /forward.jsp
		else if("jsp".equals(destination)){
			// �z�L setAttribute ��k�ǻ��@�� Date �ﹳ�� JSP ����
			Date date = new Date();
			request.setAttribute("date", date);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/forward.jsp");
			dispatcher.forward(request, response);
		}
		// ���D��t�@�� Servlet
		else if("servlet".equals(destination)){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/servlet/LifeCycleServlet");
			dispatcher.forward(request, response);
		}
		else{
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("�ʤְѼơC�Ϊk�G" + request.getRequestURL() + "?destination=jsp �Ϊ� file �Ϊ� servlet ");
		}
		
	}
	public void doPut(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		this.doGet(request, response);
	}

}
