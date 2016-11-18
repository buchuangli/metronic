package com.open01.logs.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.jcraft.jsch.ChannelSftp;
import com.open01.logs.db.DosageDatabase;
import com.open01.logs.db.ProjectsDatabase;
import com.open01.logs.model.DataSource;
import com.open01.logs.model.Dosage;
import com.open01.logs.util.Consant;
import com.open01.logs.util.JsonUtils;
import com.open01.logs.util.RemoteShellTool;
import com.open01.logs.util.SftpUtil;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class ProjectsBean extends WholePartBean implements BooleanCondition {

	/**
	 * Default constructor.
	 */
	public ProjectsBean() {
		super("cmd");

		addPart(null, new Part0());
		addPart("WEL:DELETE", new Part1());
		addPart("WEL:ADD", new Part2());
		addPart("WEL:update", new Part3());
		addPart("WEL:copyinsert", new Part4());
		addPart("WEL:queryfile", new Part5());
		addPart("WEL:insertfile", new Part6());
		
		addPart("WEL:getProjectByClient", new Part7());
		addPart("WEL:GETPROJECTLISTUSER", new Part10());
	}

	@Override
	public boolean isTrue() {
		// TODO Auto-generated method stub
		return true;
	}

	private final class Part0 extends DefaultPlainBean {

		private List<Project> projects = null;

		@Override
		protected boolean doConditionIsTrue() {

			ProjectsDatabase pd = new ProjectsDatabase();
			
			//根据USER_ID控制项目数据权限
			HttpSession sessions=request.getSession();
			int user_id =(Integer) sessions.getAttribute("user_id");
			projects = pd.getProjects(user_id);
			
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			
			writer.write(JsonUtils.objectToJson(projects,response));
		}
	}
	
	private final class Part10 extends DefaultPlainBean {
		Map<String, Object> map = new HashMap<String, Object>();
		private List<Project> projects = null;

		@Override
		protected boolean doConditionIsTrue() {

			ProjectsDatabase pd = new ProjectsDatabase();
			
			//根据USER_ID控制项目数据权限
			HttpSession sessions=request.getSession();
			int user_id =(Integer) sessions.getAttribute("user_id");
			projects = pd.getProjects(user_id);
			map.put("projects", projects);
			map.put("userId",user_id);
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			
			writer.write(JsonUtils.objectToJson(map,response));
		}
	}
	
	private final class Part1 extends DefaultPartBean {
		@Override
		protected boolean doConditionIsTrue() {
			int id = Integer.parseInt(request.getParameter("id"));
			ProjectsDatabase pd = new ProjectsDatabase();
			pd.updateid_delete(id);
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {

			writer.println("PART1 Test test.");
		}
	}
	private final class Part2 extends DefaultPartBean {

		@Override
		protected boolean doConditionIsTrue() {
			ProjectsDatabase pd = new ProjectsDatabase();
			Project project = new Project();
			//根据USER_ID控制文件数据权限
			HttpSession sessions=request.getSession();
			int user_id =(Integer) sessions.getAttribute("user_id");
			pd.addProject(project, user_id);
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {

			writer.println("PART1 Test test.");
		}
	}
	private final class Part3 extends DefaultPlainBean {
		@Override
		protected boolean doConditionIsTrue() {
			String[] values = request.getParameterValues("updata[]");
			ProjectsDatabase pd = new ProjectsDatabase();
			pd.updateproject(values);

			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			
			//writer.write(JsonUtils.objectToJson(projects,response));
		}
	}
	private final class Part4 extends DefaultPartBean {

		@Override
		protected boolean doConditionIsTrue() {
			String pn = request.getParameter("projectname");
			String pd = request.getParameter("projectdesc");
			String pt = request.getParameter("projecttime");
			ProjectsDatabase pds = new ProjectsDatabase();
			//根据USER_ID控制文件数据权限
			HttpSession sessions=request.getSession();
			int user_id =(Integer) sessions.getAttribute("user_id");
			String user_name =(String) sessions.getAttribute("user_name");
			String fid = request.getParameter("fileid");
			
			DosageDatabase ds = new DosageDatabase();
			Dosage dosage = ds.getDosage(fid);
			String start_date = dosage.getUptime();
			String end_date = dosage.getDeadtime();
			
			Map<String, Object> map2 = pds.insertproject(pn, pd, pt,fid,user_id,start_date, end_date);
			
			//增加完项目，如果是流式数据，需要增加调用shell脚本
			DosageDatabase dosageDatabase = new DosageDatabase();
			Dosage dosages = dosageDatabase.getDosage(fid);
			String fileType=dosages.getFileType();
			//判断流式数据
			if(null!=fileType && "2".equals(fileType)){
				RemoteShellTool tool = new RemoteShellTool(Consant.HADOOP_SHELL_SCRIPT_HOST_ADDRESS,
						Consant.HADOOP_SHELL_SCRIPT_USER_NAME, Consant.HADOOP_SHELL_SCRIPT_USER_PASSWD, "utf-8");

				String client_id = String.valueOf(user_id);
				String project_id = (String)map2.get("project_id");
				String user_token =(String) sessions.getAttribute("user_token");
				String cmd = "sh tomcat.sh"+" "+client_id+" "+ project_id+" "+user_token;
				System.out.println(cmd);

				String exec2 = tool.exec(cmd);
				System.out.println("流式数据调用Shell脚本开始数据分析...res:"+exec2);
			}
		
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {

			writer.println("PART1 Test test.");
		}
	}
	
	
	/**
	 * 调用shell脚本
	 * 
	 * @return
	 */
	private static String execConfirmShellSricpts() {
		RemoteShellTool tool = new RemoteShellTool(Consant.HADOOP_SHELL_SCRIPT_HOST_ADDRESS,
				Consant.HADOOP_SHELL_SCRIPT_USER_NAME, Consant.HADOOP_SHELL_SCRIPT_USER_PASSWD, "utf-8");
		System.out.println("连接hadoop成功");
		
		String exec2 = tool.exec("sh uncompress.sh");
		
		System.out.println(exec2);
		return exec2;
		
	}

	private static void writeFile(String fileData,String c_id, String p_id, String f_ids){
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
            writer.write(f_ids);
            
            /* 关掉对象 */
            writer.close(); 
            fileWriter.close();
           } catch (IOException e) {
            e.printStackTrace();
           }
	}
	
	
	private final class Part5 extends DefaultPlainBean {
		private List<DataSource> dataSource = null;
		@Override
		protected boolean doConditionIsTrue() {
			ProjectsDatabase pds = new ProjectsDatabase();
			
			//根据USER_ID控制项目数据权限
			HttpSession sessions=request.getSession();
			int user_id =(Integer) sessions.getAttribute("user_id");
			dataSource = pds.selectfile(user_id);
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {

			writer.write(JsonUtils.objectToJson(dataSource,response));
		}
	}
	private final class Part6 extends DefaultPlainBean {
		@Override
		protected boolean doConditionIsTrue() {
			String pid = request.getParameter("pid");
			String fileid = request.getParameter("fileid");
			String projectname = request.getParameter("pn");
			String projectdesc= request.getParameter("pd");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String time = df.format(new Date());// new Date()为获取当前系统时间
			ProjectsDatabase pds = new ProjectsDatabase();
			//根据USER_ID控制文件数据权限
			HttpSession sessions=request.getSession();
			int user_id =(Integer) sessions.getAttribute("user_id");
			String user_name =(String) sessions.getAttribute("user_name");
			String user_token =(String) sessions.getAttribute("user_token");
			Map<String, Object> map = pds.projectinsertfile(pid,fileid,projectname,projectdesc,time, user_id, user_token, user_name);
		
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.write(0);
		}
	}
	private class DefaultPartBean extends DefaultBean {
		protected DefaultPartBean() {
			super();
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			writeConditionIsTruePartOutput();
		}

		protected void writeConditionIsTruePartOutput() {
		}
	}

	
private final class Part7 extends DefaultPlainBean {
		List list = new ArrayList();
		@Override
		protected boolean doConditionIsTrue() {
			ProjectsDatabase pds = new ProjectsDatabase();
			int user_id=0;
			if(session.getAttribute("user_id") != null){
				user_id =(int) session.getAttribute("user_id");
			}
			List<Project> pList = pds.getProjectByUserId(user_id);
			for(Project p: pList){
				Map map = new HashMap();
				map.put("pId",p.getId());
				map.put("pName",p.getName());
				list.add(map);
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(list, response));
			list.clear();
		}
	}

 public static  void main(String[] args){
	 RemoteShellTool tool = new RemoteShellTool(Consant.HADOOP_SHELL_SCRIPT_HOST_ADDRESS,
				Consant.HADOOP_SHELL_SCRIPT_USER_NAME, Consant.HADOOP_SHELL_SCRIPT_USER_PASSWD, "utf-8");

	    //sh /webapp/myshell/myTest.sh j
		String cmd1 = "sh test.sh";
		System.out.println(cmd1);
		
		
		String exec2 = tool.exec(cmd1);
		System.out.println("流式数据调用Shell脚本开始数据分析...res:"+exec2);
		
	 
 }
}
