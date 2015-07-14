package com.helloweenvsfei.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;




@WebServlet("/IdentityServlet")
public class IdentityServlet extends HttpServlet {
	
	public static final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
	
	public static Random random = new Random(); //亂數
	
	public static String getRandomString(){			//獲得六位亂數
		StringBuffer buffer = new StringBuffer();	//字串快取記憶體
		for(int i = 0; i < 6; i++){					//迴圈六次
			buffer.append(CHARS[random.nextInt(CHARS.length)]);	//每次取一個隨機字元
		}
		return buffer.toString();		
	}
	
	public static Color getRandomColor(){
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}
	
	public static Color getReverseColor(Color c){
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}
    
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg"); //設定輸出類型，必須的
		
		String randomString = getRandomString(); //隨機字串
		request.getSession(true).setAttribute("randomString", randomString); //放到session中
		
		int width = 100;
		int height = 30;
		
		Color color = getRandomColor();
		Color reverse = getReverseColor(color);
		
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //建立一個彩色圖片
		Graphics2D g = bi.createGraphics(); //獲得繪圖物件
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16)); //設定字體
		g.setColor(color);//設定顏色
		g.fillRect(0, 0, width, height);//繪製背景
		g.setColor(reverse);//設定顏色
		g.drawString(randomString, 18, 20);//繪製隨機字元
		for(int i = 0, n = random.nextInt(100); i < n; i++){//畫最多100個噪音點
			g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);//隨機噪音點
		}
		
		ServletOutputStream out = response.getOutputStream(); //轉乘JPEG格式
		
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);//編碼器
		encoder.encode(bi); //對圖片進行編碼
		out.flush();//輸出到用戶端
		
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
