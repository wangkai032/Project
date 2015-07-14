package com.helloweenvsfei.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.helloweenvsfei.util.IpUtil;

public class RequestServlet extends HttpServlet {

	private static final long serialVersionUID = -7936817351382556277L;

	/**
	 * @param accept
	 * @return �Ȥ���s�����������ɮ�����
	 */
	private String getAccept(String accept){
		StringBuffer buffer = new StringBuffer();
		if(accept.contains("image/gif"))	buffer.append("GIF �ɮ�, ");
		if(accept.contains("image/x-xbitmap"))	buffer.append("BMP �ɮ�, ");
		if(accept.contains("image/jpeg"))	buffer.append("JPG �ɮ�, ");
		if(accept.contains("application/vnd.ms-excel"))	buffer.append("Excel �ɮ�, ");
		if(accept.contains("application/vnd.ms-powerpoint"))	buffer.append("PPT �ɮ�, ");
		if(accept.contains("application/msword"))	buffer.append("Word �ɮ�, ");
		return buffer.toString().replaceAll(", $", "");
	}
	/**
	 * @param locale
	 * @return �y�����ҦW��
	 */
	private String getLocale(Locale locale) {
		if(Locale.SIMPLIFIED_CHINESE.equals(locale))	return "²�餤��";
		if(Locale.TRADITIONAL_CHINESE.equals(locale))	return "�c�餤��";
		if(Locale.ENGLISH.equals(locale))				return "�^��";
		if(Locale.JAPANESE.equals(locale))				return "���";
		return "�����y������";
	}
	/**
	 * @param ip IP�a�}
	 * @return IP�a�}�����������m
	 */
	private String getAddress(String ip){
		return IpUtil.getIpAddress(ip);
	}
	
	/**
	 * @param userAgent
	 * @return �Ȥ���s������T
	 */
	private String getNavigator(String userAgent) {
		if(userAgent.indexOf("TencentTraveler") > 0)	return "�˰T�s����";
		if(userAgent.indexOf("Maxthon") > 0)	return "Maxthon�s����";
		if(userAgent.indexOf("MyIE2") > 0)	return "MyIE2�s����";
		if(userAgent.indexOf("Firefox") > 0)	return "Firefox�s����";
		if(userAgent.indexOf("MSIE") > 0)	return "IE �s����";
		return "�����s����";
	}

	/**
	 * @param userAgent
	 * @return �Ȥ�ݾާ@�t��
	 */
	private String getOS(String userAgent) {
		if(userAgent.indexOf("Windows NT 5.1") > 0)	return "Windows XP";
		if(userAgent.indexOf("Windows 98") > 0)	return "Windows 98";
		if(userAgent.indexOf("Windows NT 5.0") > 0)	return "Windows 2000";
		if(userAgent.indexOf("Linux") > 0)	return "Linux";
		if(userAgent.indexOf("Unix") > 0)	return "Unix";
		return "����";
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		response.setContentType("text/html");
		
		String authType = request.getAuthType();
		String localAddr = request.getLocalAddr();
		Locale locale = request.getLocale();
		String localName = request.getLocalName();
		String contextPath = request.getContextPath();
		int localPort = request.getLocalPort();
		String method = request.getMethod();
		String pathInfo = request.getPathInfo();
		String pathTranslated = request.getPathTranslated();
		String protocol = request.getProtocol();
		String queryString = request.getQueryString();
		String remoteAddr = request.getRemoteAddr();
		int port = request.getRemotePort();
		String remoteUser = request.getRemoteUser();
		String requestedSessionId = request.getRequestedSessionId();
		String requestURI = request.getRequestURI();
		StringBuffer requestURL = request.getRequestURL();
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String servletPath = request.getServletPath();
		Principal userPrincipal = request.getUserPrincipal();
		
		String accept = request.getHeader("accept");
		String referer = request.getHeader("referer");
		String userAgent = request.getHeader("user-agent");
		
		String serverInfo = this.getServletContext().getServerInfo();
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		
		// �o��<title></title>��������T�b�s��������ܬ����D
		out.println("  <HEAD><TITLE>Request Servlet</TITLE></HEAD>");
		out.println("  <style>body, font, td, div {font-size:12px; line-height:18px; }</style>");
		out.println("  <BODY>");
		
		out.println("<b>�z��IP��</b> " + remoteAddr + "<b>�A���</b> " + getAddress(remoteAddr) + "<b>�F�z�ϥ�</b> " + getOS(userAgent) + " <b>�ާ@�t��</b>�A" + getNavigator(userAgent) + " <b>�C�z�ϥ�</b> " + getLocale(locale) + "�C<br/>");
		out.println("<b>�A�Ⱦ�IP��</b> " + localAddr + "<b>�A���</b> " + getAddress(localAddr) + "<b>�F�A�Ⱦ��ϥ�</b> " + serverPort + " <b>�q�T��A�z���s�����ϥΤF</b> " + port + " <b>�q�T��s���������C</b><br/>");
		out.println("<b>�A�Ⱦ��n��</b>�G" + serverInfo + "�C<b>�A�Ⱦ��W�٬�</b> " + localName + "�C<br/>");
		out.println("<b>�z���s��������</b> " + getAccept(accept) + "�C<br/>");
		out.println("<b>�z�q</b> " + referer + " <b>�s����ӭ����C</b><br/>");
		out.println("<b>�ϥΪ���w��</b> " + protocol + "�C<b>URL��w�Y</b> " + scheme + "�A<b>�A�Ⱦ��W��</b> " + serverName + "�A<b>�z�s����URI��</b> " + requestURI + "�C<br/>" );
		out.println("<b>�� Servlet ���|��</b> " + servletPath + "�A<b>�� Servlet ���W��</b> " + this.getClass().getName() + "�C<br/>");
		out.println("<b>�����ε{���b�w�Ъ��ڥؿ���</b> " + this.getServletContext().getRealPath("") + "�A<b>�����۹���|��</b> " + contextPath + "�C <br/>");
		
		out.println("<br/>");
				
		out.println("<br/><br/><a href=" + requestURI + "> �I����s������ </a>");
		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
