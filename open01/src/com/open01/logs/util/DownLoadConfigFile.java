package com.open01.logs.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownLoadExcel
 */
@WebServlet("/DownLoadConfigFile")
public class DownLoadConfigFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownLoadConfigFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stu bconfig_file_out_path 
		
		String fileName = (String) request.getParameter("config_file_out_path"); 
		  
		//String fileName = "configure_linux_rsyslog_2.SH";
		
		 String path = Consant.ONLINE_CONFIG_FILE_OUTPUT_PATH; 
	     
	     response.setHeader("Expires", "0");  
         response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");  
         response.setHeader("Pragma", "public");
         response.setCharacterEncoding("UTF-8");
		 response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		 response.setContentType("application/x-sh");   
		    

			InputStream in = null;  
			try {
				in = new FileInputStream(path+"/" + URLEncoder.encode(fileName, "UTF-8")); 
				int len = 0;
				byte[] buffer = new byte[1024];
				response.setCharacterEncoding("UTF-8");
				OutputStream out = response.getOutputStream();
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len); 
				}
				if (out != null) {
					try {
						out.close();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println(e);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
			 
			  
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response); 
	}

}
