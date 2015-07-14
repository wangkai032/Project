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
		System.out.println("正在載入 " + this.getClass().getName() + " ... ");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String referer = request.getHeader("referer");

		// 如果直接輸入的網址，或者是從別的網站開啟的，顯示錯誤資訊。
		if(referer == null 
				|| !referer.toLowerCase().startsWith("http://" + request.getServerName().toLowerCase())){
			// 開啟圖片 error.gif
			request.getRequestDispatcher("/error.gif").forward(request, response);
			return;
		}

		String requestURI = request.getRequestURI();
		String fileName = requestURI.substring(requestURI.lastIndexOf("/") + 1);
		
		// 請求的檔案位置
		File file = new File(this.getServletContext().getRealPath("upload"), fileName);
		this.log("請求檔案 " + file.getAbsolutePath());

		// 如果檔案不存在，顯示錯誤資訊
		if(!file.exists()){
			response.getWriter().println("File " + requestURI + " doesn't exist. ");
			return;
		}
		
		// 設定開啟方式為 inline，瀏覽器中開啟
		response.setHeader("Content-Disposition", "inline;filename=" + file.getName());
		response.setHeader("Connection", "close");
		
		if(fileName.toLowerCase().endsWith(".jpg"))
			// .jpg 圖片格式
			response.setHeader("Content-Type", "image/jpeg");
		else if(fileName.toLowerCase().endsWith(".gif"))
			// .gif 圖片格式
			response.setHeader("Content-Type", "image/gif");
		else if(fileName.toLowerCase().endsWith(".doc"))
			// word 格式
			response.setHeader("Content-Type", "application/msword");
		else
			// 其他格式
			response.setHeader("Content-Type", "application/octet-stream");  
		
		// 透過 ins 讀取檔案
		InputStream ins = new FileInputStream(file);
		// 透過 ous 發送給客戶端
		OutputStream ous = response.getOutputStream();
		
		try{
			// 快取記憶體
			byte[] buffer = new byte[1024];
			int len = 0;
			
			// 讀取檔案內容並將它發送給客戶端瀏覽器
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
