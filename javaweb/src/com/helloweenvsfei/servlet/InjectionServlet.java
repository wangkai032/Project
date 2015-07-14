package com.helloweenvsfei.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InjectionServlet
 */
@WebServlet("/InjectionServlet")
public class InjectionServlet extends HttpServlet {

    private @Resource (name="hello") String hello;//从﹃ ︽糶猭
    private @Resource (name="i") int i;	//从俱计
    
    @Resource (name="persons") //ㄢ︽糶猭
    private String persons; //爹睦籔祘Α絏だ秨
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println(" <HEAD><TITLE>戈方从</TITLE></HEAD>");
		out.println("<style>body {font-size:12px; }</style>");
		out.println("<b>从﹃</br>:<br/>&nbsp;&nbsp;-&nbsp;" + hello + "<br/>");
		out.println("<b>从俱计</br>:<br/>&nbsp;&nbsp;-&nbsp;" + i + "<br/>");
		out.println("<b>从﹃皚</b>:<br/>");
		
		for(String person : persons.split(",")){ //盢﹃"," だ澄Θ皚浪琩块
			out.println("&nbsp;&nbsp;-&nbsp;" + person + "<br/>");
		}
		
		out.println(" <BODY>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
