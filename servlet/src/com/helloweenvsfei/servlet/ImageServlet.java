package com.helloweenvsfei.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServlet extends HttpServlet {
	
	private static final long serialVersionUID = -5446593186536558309L;
	
	public ImageServlet() {
		System.out.println("���b���J " + this.getClass().getName() + " ... ");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String referer = request.getHeader("referer");

		// �p�G������J�����}�A�Ϊ̬O�q�O�������}�Ҫ��A��ܿ��~��T�C
		if(referer == null 
				|| !referer.toLowerCase().startsWith("http://" + request.getServerName().toLowerCase())){
			// �}�ҹϤ� error.gif
			request.getRequestDispatcher("/error.gif").forward(request, response);
			return;
		}

		String requestURI = request.getRequestURI();
		String fileName = requestURI.substring(requestURI.lastIndexOf("/") + 1);
		
		// �ШD���ɮצ�m
		File file = new File(this.getServletContext().getRealPath("upload"), fileName);
		this.log("�ШD�ɮ� " + file.getAbsolutePath());

		// �p�G�ɮפ��s�b�A��ܿ��~��T
		if(!file.exists()){
			response.getWriter().println("File " + requestURI + " doesn't exist. ");
			return;
		}
		
		// �]�w�}�Ҥ覡�� inline�A�s�������}��
		response.setHeader("Content-Disposition", "inline;filename=" + file.getName());
		response.setHeader("Connection", "close");
		
		if(fileName.toLowerCase().endsWith(".jpg"))
			// .jpg �Ϥ��榡
			response.setHeader("Content-Type", "image/jpeg");
		else if(fileName.toLowerCase().endsWith(".gif"))
			// .gif �Ϥ��榡
			response.setHeader("Content-Type", "image/gif");
		else if(fileName.toLowerCase().endsWith(".doc"))
			// word �榡
			response.setHeader("Content-Type", "application/msword");
		else
			// ��L�榡
			response.setHeader("Content-Type", "application/octet-stream");  
		
		// �z�L ins Ū���ɮ�
		InputStream ins = new FileInputStream(file);
		// �z�L ous �o�e���Ȥ��
		OutputStream ous = response.getOutputStream();
		
		try{
			// �֨��O����
			byte[] buffer = new byte[1024];
			int len = 0;
			
			// Ū���ɮפ��e�ñN���o�e���Ȥ���s����
			while((len=ins.read(buffer)) > -1){
				ous.write(buffer, 0, len);
			}
		}finally{
			if(ous != null)	ous.close();
			if(ins != null)	ins.close();
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doGet(request, response);
	}
	
}
