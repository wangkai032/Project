package com.helloweenvsfei.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



public class UploadServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println("�ХH POST �覡�W���ɮ�");
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		File file1 = null, file2 = null;
		String description1 = null, description2 = null;

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <link rel='stylesheet' type='text/css' href='../css/style.css'>");
		out.println("  <BODY>");
		
		out.println("<div align=center><br/>");
		out.println("<fieldset style='width:90%'><legend>�W���ɮ�</legend><br/>");
		
		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>�W�Ǥ�x�G</div>");
		out.println("			<div align='left' class='rightDiv'>");
		
		// �ϥ� DiskFileUpload �ﹳ�ѪR request
		FileItemFactory factory = new DiskFileItemFactory(); 
		ServletFileUpload upload = new ServletFileUpload(factory); 
//		DiskFileUpload diskFileUpload = new DiskFileUpload();
		try {
			// �N �ѪR�����G ��m�b List ��
			
			List<FileItem> list = upload.parseRequest(request);
			out.println("�ˬd�Ҧ��� FileItem ... <br/>");
			// �ˬd list ���Ҧ��� FileItem
			for(FileItem fileItem : list){
				if(fileItem.isFormField()){
					// �p�G�O ��r��
					if("description1".equals(fileItem.getFieldName())){
						// �p�G�� FileItem �W�٬� description1
						out.println("�ˬd�� description1 ... <br/>");
						description1 = new String(fileItem.getString().getBytes(), "UTF-8");
					}
					if("description2".equals(fileItem.getFieldName())){
						// �p�G�� FileItem �W�٬� description2
						out.println("�ˬd�� description2 ... <br/>");
						description2 = new String(fileItem.getString().getBytes(), "UTF-8");
					}
				}
				else{
					// �_�h�A���ɮװ�
					if("file1".equals(fileItem.getFieldName())){
						// �Ȥ���ɮ׸��|�غc�� File �ﹳ
						File remoteFile = new File(new String(fileItem.getName().getBytes(), "UTF-8"));
						out.println("�ˬd�� file1 ... <br/>");
						out.println("�Ȥ���ɮצ�m: " + remoteFile.getAbsolutePath() + "<br/>");
						// �A�Ⱦ����ɮסA��b upload �ɮק��U
						file1 = new File(this.getServletContext().getRealPath("attachment"), remoteFile.getName());
						file1.getParentFile().mkdirs();
						file1.createNewFile();
						
						// �g�ɮסA�N FileItem ���ɮפ��e�g���ɮפ�
						InputStream ins = fileItem.getInputStream();
						OutputStream ous = new FileOutputStream(file1);
						
						try{
							byte[] buffer = new byte[1024]; 
							int len = 0;
							while((len=ins.read(buffer)) > -1)
								ous.write(buffer, 0, len);
							out.println("�w�x�s�ɮ�" + file1.getAbsolutePath() + "<br/>");
						}finally{
							ous.close();
							ins.close();
						}
					}
					if("file2".equals(fileItem.getFieldName())){
						// �Ȥ���ɮ׸��|�غc�� File �ﹳ
						File remoteFile = new File(new String(fileItem.getName().getBytes(), "UTF-8"));
						out.println("�ˬd�� file2 ... <br/>");
						out.println("�Ȥ���ɮצ�m: " + remoteFile.getAbsolutePath() + "<br/>");
						// �A�Ⱦ����ɮסA��b upload �ɮק��U
						file2 = new File(this.getServletContext().getRealPath("attachment"), remoteFile.getName());
						file2.getParentFile().mkdirs();
						file2.createNewFile();
						
						// �g�ɮסA�N FileItem ���ɮפ��e�g���ɮפ�
						InputStream ins = fileItem.getInputStream();
						OutputStream ous = new FileOutputStream(file2);
						
						try{
							byte[] buffer = new byte[1024]; 
							int len = 0;
							while((len=ins.read(buffer)) > -1)
								ous.write(buffer, 0, len);
							out.println("�w�x�s�ɮ�" + file2.getAbsolutePath() + "<br/>");
						}finally{
							ous.close();
							ins.close();
						}
					}
				}
			}
			out.println("Request �ѪR����");
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.println("			</div>");
		out.println("		</div>");
		
		if(file1 != null){
		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>file1�G</div>");
		out.println("			<div align='left' class='rightDiv'>");
		out.println("				<a href='" + request.getContextPath() + "/attachment/" + file1.getName() + "' target=_blank>" + file1.getName() +  "</a>"	);
		out.println("			</div>");
		out.println("		</div>");
		}

		if(file2 != null){
		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>file2�G</div>");
		out.println("			<div align='left' class='rightDiv'>");
		out.println("				<a href='" + request.getContextPath() + "/attachment/" + URLEncoder.encode(file2.getName(), "UTF-8") + "' target=_blank>" + file2.getName() +  "</a>"	);
		out.println("			</div>");
		out.println("		</div>");
		}
		

		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>description1�G</div>");
		out.println("			<div align='left' class='rightDiv'>");
		out.println(description1);
		out.println("			</div>");
		out.println("		</div>");

		out.println("		<div class='line'>");
		out.println("			<div align='left' class='leftDiv'>description2�G</div>");
		out.println("			<div align='left' class='rightDiv'>");
		out.println(description2);
		out.println("			</div>");
		out.println("		</div>");
		
		out.println("</fieldset></div>");
		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}