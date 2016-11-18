package com.open01.logs.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

import io.goeasy.GoEasy;

public class GetOuterFileUtil {

	static GoEasy goEasy = new GoEasy("a9062eed-9a21-49ef-836f-81ace12dc3c3");
	private static String tabStr = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	private static String goEasyKeyPront = "open01_";
	
	public static File getOuterFileFromRemote(String filePath, String fileName,String user_id){
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
}
