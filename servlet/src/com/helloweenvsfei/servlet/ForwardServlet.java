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
		
		// 跳躍到 /WEB-INF/web.xml。透過地址欄輸入網址是不能存取到該檔案的，但是 forward 可以
		if("file".equals(destination)){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/web.xml");
			dispatcher.forward(request, response);
		}
		// 跳躍到 /forward.jsp
		else if("jsp".equals(destination)){
			// 透過 setAttribute 方法傳遞一個 Date 對像給 JSP 頁面
			Date date = new Date();
			request.setAttribute("date", date);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/forward.jsp");
			dispatcher.forward(request, response);
		}
		// 跳躍到另一個 Servlet
		else if("servlet".equals(destination)){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/servlet/LifeCycleServlet");
			dispatcher.forward(request, response);
		}
		else{
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("缺少參數。用法：" + request.getRequestURL() + "?destination=jsp 或者 file 或者 servlet ");
		}
		
	}
	public void doPut(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		this.doGet(request, response);
	}

}
