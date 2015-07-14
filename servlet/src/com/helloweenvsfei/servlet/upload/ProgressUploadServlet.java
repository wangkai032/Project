package com.helloweenvsfei.servlet.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ProgressUploadServlet extends HttpServlet {

	private static final long serialVersionUID = -4935921396709035718L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// �W�Ǫ��A
		UploadStatus status = new UploadStatus();

		// ��ť��
		UploadListener listener = new UploadListener(status);

		// �� UploadStatus ��� session ��
		request.getSession(true).setAttribute("uploadStatus", status);

		// Apache �W�Ǥu��
		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

		// �]�w listener
		upload.setProgressListener(listener);

		try {
			List itemList = upload.parseRequest(request);

			for (Iterator it = itemList.iterator(); it.hasNext();) {
				FileItem item = (FileItem) it.next();
				if (item.isFormField()) {
					System.out.println("FormField: " + item.getFieldName()
							+ " = " + item.getString());
				} else {
					System.out.println("File: " + item.getName());

					// �Τ@ Linux �P windows �����|���j��
					String fileName = item.getName().replace("/", "\\");
					fileName = fileName.substring(fileName.lastIndexOf("\\"));

					File saved = new File("C:\\upload_test", fileName);
					saved.getParentFile().mkdirs();

					InputStream ins = item.getInputStream();
					OutputStream ous = new FileOutputStream(saved);

					byte[] tmp = new byte[1024];
					int len = -1;

					while ((len = ins.read(tmp)) != -1) {
						ous.write(tmp, 0, len);
					}

					ous.close();
					ins.close();

					response.getWriter().println("�w�x�s�ɮסG" + saved);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("�W�ǵo�Ϳ��~�G" + e.getMessage());
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragrma", "no-cache");
		response.setDateHeader("Expires", 0);

		UploadStatus status = (UploadStatus) request.getSession(true)
				.getAttribute("uploadStatus");

		if (status == null) {
			response.getWriter().println("�S���W�Ǹ�T");
			return;
		}

		long startTime = status.getStartTime();
		long currentTime = System.currentTimeMillis();

		// �w�ǿ骺�ɶ� ���Gs
		long time = (currentTime - startTime) / 1000 + 1;

		// �ǿ�t�� ���Gbyte/s
		double velocity = ((double) status.getBytesRead()) / (double) time;

		// ���p�`�ɶ� ���Gs
		double totalTime = status.getContentLength() / velocity;

		// ���p�Ѿl�ɶ� ���Gs
		double timeLeft = totalTime - time;

		// �w�������ʤ���
		int percent = (int) (100 * (double) status.getBytesRead() / (double) status
				.getContentLength());

		// �w������ ���GM
		double length = ((double) status.getBytesRead()) / 1024 / 1024;

		// �`���� ���GM
		double totalLength = ((double) status.getContentLength()) / 1024 / 1024;

		// �榡�G�ʤ���||�w������(M)||�ɮ��`����(M)||�ǿ�t�v(K)||�w�ήɶ�(s)||���p�`�ɶ�(s)||���p�Ѿl�ɶ�(s)||���b�W�ǲĴX���ɮ�
		String value = percent + "||" + length + "||" + totalLength + "||"
				+ velocity + "||" + time + "||" + totalTime + "||" + timeLeft
				+ "||" + status.getItems();

		response.getWriter().println(value);
	}

}
