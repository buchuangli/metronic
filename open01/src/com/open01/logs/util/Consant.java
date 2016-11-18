package com.open01.logs.util;

/**
 * 公共配置变量的定义路径地址
 * @author dai
 *
 */
public class Consant {
	//导出日志excel文件的文件生成地址目录
	public static final String EXPORT_FILE_PATH = "/home/uploadfile/exceldata/";
	//导出日志excel文件的文件名称到前缀
	public static final String EXPORT_FILE_NAME = "export_data";

	// /opt/sftp/testuser/kaishu_1/data/xxx.json
	// /opt/sftp/testuser/kaishu_1/kaishu.txt  
	// /opt/sftp/testuser/kaishu_1/selected_header_$b.txt
	
	// /opt/sftp/testuser/kaishu_1/body_content_$b.txt
	// /opt/sftp/testuser/kaishu_1/header_content_$b.txt
	// /opt/sftp/testuser/kaishu_1/capacity_$b.txt

	//导出数据上传时候预分析的用户选在分析到对应关系的文件的文件存放路径:/home/uploadfile/header_content.txt
	public static final String RECOMPIRE_CONFIG_FILE_BASE_PATH = "/opt/sftp/testuser/";
	
	//实时接入的日志时候，需要用户配置到syslog文件配置的模板文件存放地址
	public static final String ONLINE_CONFIG_FILE_PATH = "/home/uploadfile/configure_linux_rsyslog_template.sh";
	//实时接入的日志时候，需要用户配置到syslog文件配置的重新生成到文件存放目录
	public static final String ONLINE_CONFIG_FILE_OUTPUT_PATH = "/home/uploadfile/";
	//实时接入的日志时候，需要用户配置到syslog文件配置内容到ip地址
	public static final String ONLINE_CONFIG_FILE_IP_ADRESS = "log.ali.open01.com";

	// 上传数据文件到文件服务器IP
	public static final String FILE_SERVER_HOST = "60.205.152.23";
	// 上传数据文件到文件服务器端口
	public static final int FILE_SERVER_PORT = 22;
	// 上传数据文件到文件服务器的用户名
	public static final String FILE_SERVER_USER_NAME = "testuser";
	// 上传数据文件到文件服务器的密码
	public static final String FILE_SERVER_USER_PASSWD = "kaishu123";

	// 上传数据文件到文件存放路径:之前目录/upload/data/
	public static final String FILE_DATA_PATH = "/opt/sftp/testuser/";
	// 上传文件时本地临时文件目录
	public static final String FILE_LOCAL_TEMP_PATH = "/home/uploadfile/";
	// 上传完毕到数据，给hadoop调用时候反馈的数据和用户对应信息
	public static final String FILE_DATA_REEDBACK_DATA_NAME = "/home/uploadfile/kaishu.txt";
	// 建立完项目，确认开始清洗
	public static final String FILE_DATA_REEDBACK_DATA_NAME2 = "/home/uploadfile/kaishu2.txt";
	// 上传反馈信息到hadoop文件到的存放路径:/upload/
	public static final String FILE_FEDBACK_DATA_PATH = "/opt/sftp/testuser/";
	// 上传反馈信息到hadoop文件到的文件名称
	public static final String FILE_FEDBACK_DATA_FILE_NAME = "kaishu.txt";
	
	// 建立完项目，确认开始清洗,生成确认清洗的文件
	public static final String FILE_FEDBACK_DATA_FILE_NAME2 = "kaishu2.txt";

	// shell脚本的服务器IP地址
	public static final String SHELL_SCRIPT_HOST_ADDRESS = "60.205.152.23";
	// shell脚本的服务器用户名地址
	public static final String SHELL_SCRIPT_USER_NAME = "tongshuai";
	// shell脚本的服务器密码
	public static final String SHELL_SCRIPT_USER_PASSWD = "Kaishu123";
	// hadoop的shell脚本的服务器IP地址
	public static final String HADOOP_SHELL_SCRIPT_HOST_ADDRESS = "60.205.152.23";
	// hadoop的shell脚本的服务器用户名地址
	public static final String HADOOP_SHELL_SCRIPT_USER_NAME = "hadoop";
	// hadoop的shell脚本的服务器密码
	public static final String HADOOP_SHELL_SCRIPT_USER_PASSWD = "kaishuhadoop";
	
	// 设置邮件服务器
	public static final String MAIL_HOST = "smtp.open01.com";
	public static final String MAIL_SENDER = "open01@open01.com";
	public static final String MAIL_SEND_USERNAME= "open01@open01.com";  
	public static final String MAIL_SEND_PASSWORD = "Kaishu123";
	
	//重置密码的邮件
	public static final String FORGET_PASSWORD_MAIL_TITLE = "找回密码[Open01]";
	public static final String FORGET_PASSWORD_MAIL_CONTENT = "您的密码已经重置为111111 您现在可以登录系统，并请及时修改密码！";
	
	//访问来源
	public static final String FILE_REPORT_REFERRER = "直接访问";
	
	public static final String ONLINE_DATA_NAME = "实时数据...";

}
