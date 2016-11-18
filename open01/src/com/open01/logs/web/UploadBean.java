package com.open01.logs.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.open01.logs.db.UploadDatabase;
import com.open01.logs.util.Consant;
import com.open01.logs.util.EsShellComandThread;
import com.open01.logs.util.HadoopShellComandThread;
import com.open01.logs.util.JsonUtils;
import com.open01.logs.util.MailUtil;
import com.open01.logs.util.RemoteShellTool;
import com.open01.logs.util.SftpUtil;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;
import io.goeasy.GoEasy;

public class UploadBean extends WholePartBean implements BooleanCondition {

	static GoEasy goEasy = new GoEasy("a9062eed-9a21-49ef-836f-81ace12dc3c3");
	private static String tabStr = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	private static String goEasyKeyPront = "open01_";
	
	
	/**
	 * Default constructor.
	 */
	public UploadBean() {
		super("cmd");
		addPart(null, new Part0());
		addPart("WEL:SUBMITFILECOMPIE", new Part1());
		// 得到页面填写打事实上传参数，调用后台，生成下载的配置文件
		addPart("WEL:GETONLINECONFIGFILE", new Part2());
	}

	@Override
	public boolean isTrue() {
		return false;
	}

	private final class Part0 extends DefaultPlainBean {
		Map<String, Object> map = new HashMap<String, Object>();

		@Override
		protected boolean doConditionIsTrue() {
			
			HttpSession sessions = request.getSession();
			int user_id = (Integer) sessions.getAttribute("user_id");
			String user_name =(String) sessions.getAttribute("user_name");
			
			// 项目id,可以为多个，多个用","分割     
			String project_ids= request.getParameter("projectids");  
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			//1：接受文件
			goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"1：服务器开始接受到文件...<br>"); 
			
			UploadDatabase uploaddatabases = new UploadDatabase();
			
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				String localFilePath = Consant.FILE_LOCAL_TEMP_PATH+user_name+"_"+user_id;
				File localFile = new File(localFilePath);
				if(!localFile.exists()){
					localFile.mkdirs();
				} 
				factory.setRepository(localFile);
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(1024000000);   
				List items = new ArrayList();
				try {
					items = upload.parseRequest(request);
				} catch (FileUploadException e1) {
					System.out.println("文件上传发生错误" + e1.getMessage());
				}
				//2：SMTP文件上传发生错误
				goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"2：开始SFTP文件上传...<br>");
				// 项目id,可以为多个，多个用","分割
				// String[] project_id =
				// request.getParameterValues("project_id[]");
				// System.out.println(project_id);
				Iterator it = items.iterator();
				SftpUtil sf = new SftpUtil();

				String host = Consant.FILE_SERVER_HOST;
				int port = Consant.FILE_SERVER_PORT;
				String username = Consant.FILE_SERVER_USER_NAME;
				String password = Consant.FILE_SERVER_USER_PASSWD;

				// 实际路径例如:/opt/sftp/testuser/kaishu_1/data/xxx.json
				// 组装文件路径
				String directory = Consant.FILE_DATA_PATH + user_name + "_" + user_id + "/data/";

				Object uid = session.getAttribute("user_id");
				Object uname = session.getAttribute("user_name");
				String file_id = "";
				C: while (it.hasNext()) {
					FileItem fileItem = (FileItem) it.next();
					if (fileItem.getName() != null && fileItem.getSize() != 0) {
						
						System.out.println("fileItem.getSize():"+fileItem.getSize());
	
						long size = Math.round(fileItem.getSize() / (1024*1024));
						String filename = fileItem.getName();

						// 本地目录filepath
						String filepath = Consant.FILE_LOCAL_TEMP_PATH + fileItem.getName();
						File newFile = new File(filepath);
						ChannelSftp sftp = sf.connect(host, port, username, password);
						try {
							fileItem.write(newFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (!newFile.exists() && !newFile.isDirectory()) {
							break C;
						} else {
							sf.upload(directory, newFile, sftp, filename);
						}
					
						//3：SMTP文件上传发生错误
						goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"3：完成SFTP文件上传...<font color='blue'>OK.</font><br>");
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
						String uploadtime = df.format(new Date());// new
																	// Date()为获取当前系统时间
						DateTime dt1 = new DateTime(new Date());
						String deadtime = dt1.plusMonths(1).toString("yyyy-MM-dd");

						// 文件目录
						String fileData = Consant.FILE_DATA_REEDBACK_DATA_NAME;

						Map<String, Object> map2 = uploaddatabases.fileinsert(uid, filename, filepath, size, filename,
								uid, host, port, username, password, uploadtime, deadtime, user_id, fileData, project_ids);
						File fpath = (File) map2.get("filepath");

						int fileId = (int) map2.get("file_id");  
						
						file_id = String.valueOf(fileId); 
						//"/opt/sftp/testuser/"
						sf.upload(Consant.FILE_FEDBACK_DATA_PATH + user_name + "_" + user_id + "/", fpath, sftp,
								Consant.FILE_FEDBACK_DATA_FILE_NAME);
						//4：开始文件内容解析
						goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"4：开始文件内容预解析...<br>");
						// 调用shell脚本,执行数据分析  
						String compireRes = execShellSricpts(user_name + "_" + user_id);
						
						goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+tabStr+"解析结果："+compireRes+"<br>"); 
						
						if("-1".equals(compireRes.trim())){  
							goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+tabStr+"可能是非标准日志内容，解析失败！<font color='red'>Error.</font><br>"); 
							return false; 
						}
						
						if(!"2".equals(compireRes.trim())){  
							goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+tabStr+"预解析失败！请联系客服！<font color='red'>Error.</font><br>"); 
							return false; 
						}
						
						//5：开始文件内容解析
						goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"5：完成文件内容预解析...<br>");
					
						//6：开始文件内容解析
						goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"6：开始获取预解析头部文件...<br>");
						// 获取表头信息 
						List<String[]> lsa = getHeader(request,file_id);
						if(lsa.size()<1){
							//7：开始文件内容解析
							goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"7：获取预解析头部文件失败...<font color='red'>Error.</font><br>");
							return false;
						}else{
							map.put("header", lsa);
							//7：开始文件内容解析
							goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"7：获取预解析头部文件完成...<font color='blue'>OK.</font><br>");
						}
						
						
						//8：开始文件内容解析
						goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"8：开始获取预解析数据样例文件...<br>");
						// 获取数据信息
						List<String[]> lsb = getBody(request,file_id);
						if(lsb.size()<1){
							//9：开始文件内容解析
							goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"9：获取预解析数据样例文件失败...<font color='red'>Error.</font><br>");
							return false;
						}else{
							map.put("body", lsb);
							goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"9：获取预解析数据样例文件完成...<font color='blue'>OK.</font><br>");
						}
						String file_size_covent = getFileSizeInDisk(file_id);
						if("".equals(file_size_covent)){
							//10：开始文件内容解析
							goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"10：获取文件实际大小失败...<font color='red'>Error.</font>");
							return false;
						}else{
							map.put("fileId", file_id); 
							map.put("projectIds", project_ids);
							//10：开始文件内容解析
							goEasy.publish(goEasyKeyPront+user_id,dfs.format(new Date())+"&nbsp;&nbsp;"+"10：预解析完成...<font color='blue'>OK.</font>数据实际大小为："+file_size_covent+" MB.即将进入预解析确认页面.<br>");
							//更新文件大小
							uploaddatabases.updateFileSizeByFileId(file_id,file_size_covent);
							try {   
								Thread.sleep(1000); 
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					
					} 
					
				}
			} 
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.write(JsonUtils.objectToJson(map, response));
		}
	}

	/**
	 * 获取文件大小
	 */
	//拿到文件实际大小 
	private String getFileSizeInDisk(String file_id){
		
		String file_size = getUploadFileSizeFromRemote(request, file_id);
		String file_size_covent="";
		if(!"".equals(file_size) && file_size.contains("M")){
			file_size_covent = file_size.substring(0,file_size.length()-1);
		}
		if(!"".equals(file_size) && file_size.contains("G")){
			file_size_covent = String.valueOf(Long.valueOf(file_size.substring(0,file_size.length()-1)) * 1024);
		}
		if(!"".equals(file_size) && file_size.contains("K")){
			Long st = Long.valueOf(file_size.substring(0,file_size.length()-1)) / 1024;
			double filesize = myRound(st,2);
			file_size_covent = String.valueOf(filesize);
		}
		if(!"".equals(file_size) && file_size.contains("B")){
			file_size_covent = String.valueOf(Math.round(Long.valueOf(file_size.substring(0,file_size.length()-1)) / (1024*1024)));
		}
		return file_size_covent;
	}
	
	
	public static String[] toStringArray(List<String[]> strList) {
		String[] array = new String[strList.size()];
		strList.toArray(array);
		return array;
	}

	/**
	 * 调用shell脚本
	 * 
	 * @return
	 */
	private static String execShellSricpts(String userIdName) {
		RemoteShellTool tool = new RemoteShellTool(Consant.HADOOP_SHELL_SCRIPT_HOST_ADDRESS,
				Consant.HADOOP_SHELL_SCRIPT_USER_NAME, Consant.HADOOP_SHELL_SCRIPT_USER_PASSWD, "utf-8");
		System.out.println("连接hadoop成功");
		
		String exec2 = tool.exec("sh uncompress.sh "+userIdName );
		
		System.out.println(exec2);
		return exec2;
		
	}

	private static List<String[]> getHeader(HttpServletRequest request, String file_id) {

		List<String[]> list = new ArrayList<String[]>();
		HttpSession sessions = request.getSession();
		int user_id = (Integer) sessions.getAttribute("user_id");
		String user_name = (String) sessions.getAttribute("user_name");

		// 组装文件路径
		String baseFilePath = Consant.RECOMPIRE_CONFIG_FILE_BASE_PATH + user_name + "_" + user_id;
		String headerFilePath = baseFilePath + "/header_content_" + file_id + ".txt";

		//File headerFile = getExistsFile(headerFilePath);
		File headerFile = getOuterFileFromRemote(baseFilePath+"/", "header_content_" + file_id + ".txt", String.valueOf(user_id));
 
		if (null != headerFile) {
			Scanner scanner = null;
			try { 
				scanner = new Scanner(headerFile, "utf-8");
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] coloumn = new String[line.split(",").length];
					for (int s = 0; s < line.split(",").length; s++) {
						coloumn[s] = line.split(",")[s].trim();
					}
					list.add(coloumn);
				}
			} catch (FileNotFoundException e) {

			} finally {
				if (scanner != null) {
					scanner.close();
				}
			}

		}
		return list;
	}

	
	private static File getOuterFileFromRemote(String filePath, String fileName,String user_id){
		SftpUtil sf = new SftpUtil();
		File fileOut = null;
		String host = Consant.FILE_SERVER_HOST;
		int port = Consant.FILE_SERVER_PORT;
		String username = Consant.FILE_SERVER_USER_NAME;
		String password = Consant.FILE_SERVER_USER_PASSWD;
		ChannelSftp sftp = sf.connect(host, port, username, password);
		
		String remoteFilePath = filePath+fileName;
		
		boolean flag = false;
		int count=1;
		String block=".";
		 
		//设定轮回执行次数，180次。一次10秒钟；全部等待时间为30分钟
		int maxCount = 100;
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String compireStr= dfs.format(new Date())+"&nbsp;&nbsp;"+tabStr+"预分析数据中.";
		
		goEasy.publish(goEasyKeyPront+user_id,""+compireStr);  
	 
		while (!flag && count < maxCount) { 
			System.out.println("count...."+count);
			try {   
				if( sftp.ls(remoteFilePath) != null){
					flag = true; 
					File file = new File(filePath);
					if(!file.exists()){
						file.mkdirs();
					}
					sf.download(filePath , fileName, filePath+fileName, sftp);
					fileOut = new File(filePath+fileName); 
				}else{
					flag = false;
				}
			} catch (SftpException e) { 
				flag = false;
				e.printStackTrace();
				try {   
					count++;
					Thread.sleep(5000); 
					goEasy.publish(goEasyKeyPront+user_id,""+block); 
					//换行
					if(count == 100){
						goEasy.publish(goEasyKeyPront+user_id,"<br>"); 
					}
					System.out.println("Thread wait....");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		goEasy.publish(goEasyKeyPront+user_id,"<br>"); 
		if(count==maxCount){
			goEasy.publish(goEasyKeyPront+user_id,tabStr+"数据清洗出现异常或者超时，请联系客服人员...<br>"); 
		}
	
		return fileOut;
	}
	/**
	 * 拿到哦解压缩后的文件大小
	 * @param request
	 * @param file_id
	 * @return
	 */
	private static String getUploadFileSizeFromRemote(HttpServletRequest request, String file_id){
		String fileSize="";
		HttpSession sessions = request.getSession();
		int user_id = (Integer) sessions.getAttribute("user_id");
		String user_name = (String) sessions.getAttribute("user_name"); 
		// 组装文件路径
		String baseFilePath = Consant.RECOMPIRE_CONFIG_FILE_BASE_PATH + user_name + "_" + user_id;
		File fileSizeFile = getOuterFileFromRemote(baseFilePath+"/", "capacity_" + file_id + ".txt", String.valueOf(user_id));
		
		FileInputStream f;
		try {
			f = new FileInputStream(fileSizeFile);
			BufferedReader dr = new BufferedReader(new InputStreamReader(f));
			fileSize = dr.readLine().trim();
			
			dr.close();
			f.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return fileSize;
	}
	
	private static List<String[]> getBody(HttpServletRequest request, String file_id) {

		List<String[]> list = new ArrayList<String[]>();

		// 实际路径例如:/opt/sftp/testuser/kaishu_1/body_content_$b.txt
		HttpSession sessions = request.getSession();
		int user_id = (Integer) sessions.getAttribute("user_id");
		String user_name = (String) sessions.getAttribute("user_name"); 
		// 组装文件路径
		String baseFilePath = Consant.RECOMPIRE_CONFIG_FILE_BASE_PATH + user_name + "_" + user_id;

		String bodyFilePath = baseFilePath + "/body_content_" + file_id + ".txt";  

		File bodyFileFile = getOuterFileFromRemote(baseFilePath+"/", "body_content_" + file_id + ".txt",String.valueOf(user_id));
		//File bodyFileFile = getExistsFile(bodyFilePath);

		if (null != bodyFileFile) {
 
			try {
				FileInputStream f = new FileInputStream(bodyFilePath);
				BufferedReader dr = new BufferedReader(new InputStreamReader(f));
				String ss = "";
				while ((ss = dr.readLine()) != null) {
					String[] coloumn = new String[ss.split(",").length];
					for (int s = 0; s < ss.split(",").length; s++) {
						coloumn[s] = ss.split(",")[s].trim();
					}
					list.add(coloumn);
					System.out.println(ss);
				}
				dr.close();
				f.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 将项目ID和文件ID插入项目文件关系表,提交确认后打txt文件给数据处理调用
	 * 
	 * @param ds_id
	 * @param project_id
	 */
	private final class Part1 extends DefaultPlainBean {
		@Override
		protected boolean doConditionIsTrue() {
			// 拿到表头信息，用","隔开
			String header_content = request.getParameter("confirmPrewData");
			String fileId = request.getParameter("fileId");
			
			SftpUtil sf = new SftpUtil();
			String host = Consant.FILE_SERVER_HOST;
			int port = Consant.FILE_SERVER_PORT;
			String username = Consant.FILE_SERVER_USER_NAME;
			String password = Consant.FILE_SERVER_USER_PASSWD;

			// 实际路径例如:/opt/sftp/testuser/kaishu_1/selected_header_$b.txt
			HttpSession sessions = request.getSession();
			int user_id = (Integer) sessions.getAttribute("user_id");
			String user_name = (String) sessions.getAttribute("user_name");
			String user_token = (String) sessions.getAttribute("user_token");
			// 组装文件路径
			String baseFilePath = Consant.FILE_LOCAL_TEMP_PATH + user_name + "_" + user_id+"/";
			String fileName= "selected_header_" + user_id + ".txt";

			String confirmCompieFilePath = baseFilePath +fileName;

			// 判断文件是否存在
			File return_header_content = new File(baseFilePath);
			if(!return_header_content.exists()){
				return_header_content.mkdirs();
			}
			try {
					File returnFile = new File(confirmCompieFilePath);
					if(!returnFile.exists()){
						returnFile.createNewFile();
					}
					// 写文件到本地服务器
					writeTxtFile(header_content, returnFile);
					// 本地目录filepath
					ChannelSftp sftp = sf.connect(host, port, username, password);
					try {
						String directory = Consant.RECOMPIRE_CONFIG_FILE_BASE_PATH+user_name + "_" + user_id+"/";
						sf.upload(directory, returnFile, sftp, fileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			MailUtil.sendMail("xianghong.dai@open01.com", "开始链接es", " "+user_id +" " +fileId +" "+user_name);
			//链接es，执行数据插入到ES
			EsShellComandThread thread2 = new EsShellComandThread("sh up.sh "+" "+user_id +" " +fileId +" "+user_name);
	        thread2.start(); 
	    	MailUtil.sendMail("xianghong.dai@open01.com", "完成链接es", " "+user_id +" " +fileId +" "+user_name);
		
	    	String projectIds = request.getParameter("projectIds");
	    	MailUtil.sendMail("xianghong.dai@open01.com", "开始链接hadoop", "projectIds="+projectIds);
	    	
	    	if(null==projectIds || "".equals(projectIds)){
				//链接hadoop，执行数据插入到数据库
				HadoopShellComandThread thread1 = new HadoopShellComandThread("sh up.sh " +user_name + "_" +user_id);
			    thread1.start();
	    	}
			if(null!=projectIds && projectIds.split(",").length>0){
				if(",".equals(projectIds.substring(projectIds.length()-1, projectIds.length()))){
					projectIds = projectIds.substring(0,projectIds.length()-1);
				}
				
				//链接hadoop，执行数据插入到数据库
				HadoopShellComandThread thread1 = new HadoopShellComandThread("sh up.sh " +user_name + "_" +user_id);
		        thread1.start();
		        
		        ChannelSftp sftp = sf.connect(host, port, username, password);
				//生成确认上传的文件到本地服务器 文件格式:第一行  client_id project_id file_name
				String fpath = Consant.FILE_DATA_REEDBACK_DATA_NAME2;
				writeHadoopFile(fpath,String.valueOf(user_id),projectIds,fileId);
				File fileFeedback = new File(fpath);
				//上传文件到hadoop应用服务器
				sf.upload(Consant.FILE_FEDBACK_DATA_PATH + user_name + "_" + user_id + "/", fileFeedback, sftp,Consant.FILE_FEDBACK_DATA_FILE_NAME2);
		        
				//判断文件是否上传到hdfs完毕
				File resFlag = getOuterFileFromRemote(Consant.FILE_FEDBACK_DATA_PATH + user_name + "_" + user_id + "/", "upok.txt", "");
				if(null != resFlag){
					//链接hadoop，执行数据插入到数据库
					HadoopShellComandThread thread3 = new HadoopShellComandThread("sh run.sh " +user_name + "_" +user_id+" "+Consant.FILE_FEDBACK_DATA_FILE_NAME2);
			        thread3.start();
				}
			}
			MailUtil.sendMail("xianghong.dai@open01.com", "完成链接hadoop", " "+user_id +" " +fileId +" "+user_name);

			//更新数据状态 
			UploadDatabase uploadDataBase = new UploadDatabase();
			uploadDataBase.confirmSubmitData(fileId);
			
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.println("PART1 Test test.");
		}
	}

	

	private static void writeHadoopFile(String fileData,String c_id, String p_id,String f_id){
		try {
        	String createtxt = fileData;
        	File createpath = new File(createtxt);
            /* 创建写入对象 */
            FileWriter fileWriter = new FileWriter(createpath);
            /* 创建缓冲区 */
            BufferedWriter writer = new BufferedWriter(fileWriter);
            /* 写入字符串 */
            writer.write(c_id+" "+p_id);
            writer.write("\n");
            writer.write(f_id);
            /* 关掉对象 */
            writer.close(); 
            fileWriter.close();
           } catch (IOException e) {
            e.printStackTrace();
           }
	}
	
	/**
	 * //得到页面填写打事实上传参数，调用后台，生成下载的配置文件
	 * 
	 * @param path
	 * @param logType
	 * @param tag
	 */
	private final class Part2 extends DefaultPlainBean {
		Map<String, Object> map = new HashMap<String, Object>();

		@Override
		protected boolean doConditionIsTrue() {
			String path_value = request.getParameter("path");
			String logtype_value = request.getParameter("logType");
			String tag_value = request.getParameter("tag");

			// 根据USER_ID控制文件数据权限
			HttpSession sessions = request.getSession();
			String user_token = (String) sessions.getAttribute("user_token");
			String config_file_ipadress = Consant.ONLINE_CONFIG_FILE_IP_ADRESS;

			String online_config_file_path = Consant.ONLINE_CONFIG_FILE_PATH;
			File online_config_file = new File(online_config_file_path);

			try {
				FileInputStream f = new FileInputStream(online_config_file);
				BufferedReader dr = new BufferedReader(new InputStreamReader(f));

				// -h log.ali.open01.com -t e7972e9f1df04993a40a26b4a232c9bc
				// --filepath /PATH/TO/YOUR/LOGFILE --appname TYPE_OF_YOUR_LOG
				// --tag CUSTOM_ATTRIBUTES_OF_YOUR_LOG
				StringBuffer strBuf = new StringBuffer();
				for (String tmp = null; (tmp = dr.readLine()) != null; tmp = null) {
					tmp = tmp.replaceAll("host_ip_adress", config_file_ipadress);
					tmp = tmp.replaceAll("user_token", user_token);
					tmp = tmp.replaceAll("local_file_path", path_value);
					tmp = tmp.replaceAll("log_file_type", logtype_value);
					tmp = tmp.replaceAll("local_tag", tag_value);
					strBuf.append(tmp);
					strBuf.append(System.getProperty("line.separator"));
				}
				dr.close();
				f.close();

				// 写入新打文件writeTxtFile
				String fileName = Consant.ONLINE_CONFIG_FILE_OUTPUT_PATH + "configure_linux_rsyslog_"
						+ sessions.getAttribute("user_id") + ".sh";
				File file = new File(fileName);
				writeTxtFile(strBuf.toString(), file);

				// 返回已经生成打下载文件路径
				map.put("configure_linux_rsyslog_path",
						"configure_linux_rsyslog_" + sessions.getAttribute("user_id") + ".sh");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 将文件id和项目id写入文件项目关系表中
			UploadDatabase uploaddatabases = new UploadDatabase();
			// 构建online类型打数据
			// 根据USER_ID控制文件数据权限
			int user_id = (Integer) sessions.getAttribute("user_id");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			String uploadtime = df.format(new Date());// new
														// Date()为获取当前系统时间
			DateTime dt1 = new DateTime(new Date());
			String deadtime = dt1.plusMonths(1).toString("yyyy-MM-dd");
			uploaddatabases.insertOnlineFileData(String.valueOf(user_id), Consant.ONLINE_DATA_NAME, uploadtime, deadtime, user_id);

			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			writer.write(JsonUtils.objectToJson(map, response));
		}
	}

	/**
	 * 写文件内容
	 * 
	 * @param content
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static boolean writeTxtFile(String content, File fileName) throws Exception {

		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("UTF-8"));
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();  
			}
		} 
		return flag; 
	}
	
	private static double myRound(double number,int index){
        double result = 0;
        double temp = Math.pow(10, index);
        result = Math.round(number*temp)/temp;
        return result;
    }
	
	public static void main(String[] args) throws IOException {
		String localFilePath = Consant.FILE_LOCAL_TEMP_PATH+"test002"+"_"+"125/dt.txt";
		File localFile = new File(localFilePath);
		if(!localFile.exists()){
			localFile.mkdirs();
		} 
		try {
			writeTxtFile("dxt",localFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		SftpUtil sf = new SftpUtil();
		String host = Consant.FILE_SERVER_HOST;
		int port = Consant.FILE_SERVER_PORT;
		String username = Consant.FILE_SERVER_USER_NAME;
		String password = Consant.FILE_SERVER_USER_PASSWD;
		ChannelSftp sftp = sf.connect(host, port, username, password);
		sf.upload(Consant.RECOMPIRE_CONFIG_FILE_BASE_PATH, localFile, sftp, "xx.txt");

	}

}