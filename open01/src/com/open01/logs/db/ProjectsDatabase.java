package com.open01.logs.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.jcraft.jsch.ChannelSftp;
import com.open01.logs.model.DataSource;
import com.open01.logs.model.Dosage;
import com.open01.logs.util.Consant;
import com.open01.logs.util.EsShellComandThread;
import com.open01.logs.util.GetOuterFileUtil;
import com.open01.logs.util.HadoopShellComandThread;
import com.open01.logs.util.SftpUtil;
import com.open01.logs.web.Project;

public class ProjectsDatabase extends Open01Database {
	private Map<String,Object> map = new HashMap<String,Object>();
	public ProjectsDatabase() {

		super();

	}
	public List<Project> getProjects(int user_id) {

		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();

		executeQuery("SELECT id,name, description, datatype, datasize,time,project_data,fileid,is_delete,fileuptime,filedeadtime FROM stat_project where is_delete=1 and user_id='"+user_id+"' and fileuptime < now() and filedeadtime> now() order by time;",
				result_set_processor_1);
		for(Project p:result_set_processor_1.projects){
			String pd = "",fileid="",pds="",pda="",upfitime="",uploadfiletime="",dft="",deadfiletime="";
			List<DataSource>  ds = getDatasource(p.getId());
			if(ds.size()>0){
				for(DataSource d:ds){
					pd+=d.getName()+",";
					pds=pd.substring(0,pd.length()-1);
					fileid+=d.getDs_id()+",";
					pda=fileid.substring(0,fileid.length()-1);
					upfitime+=d.getUptime()+",";
					uploadfiletime=upfitime.substring(0,upfitime.length()-1);
					dft+=d.getDeadtime()+",";
					deadfiletime=dft.substring(0,dft.length()-1);
				}
				p.setProject_data(pds);
				p.setFileid(pda);
				p.setFileuptime(uploadfiletime);
				p.setFiledeadtime(deadfiletime);
				
			}
			
		}
		return result_set_processor_1.projects;
	}
	private final class ResultSetProcessor_1 extends ResultSetProcessor {

		private List<Project> projects = null;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {

			projects = new ArrayList<Project>();

			while (resultSet.next()) {
				Project project = new Project();
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setDescription(resultSet.getString("description"));
				project.setDatatype(resultSet.getString("datatype"));
				project.setDatasize(resultSet.getInt("datasize"));
				project.setTime(resultSet.getString("time"));
				project.setProject_data(resultSet.getString("project_data"));
				project.setFileid(resultSet.getString("fileid"));
				project.setIs_delete(resultSet.getInt("is_delete"));
				project.setFileuptime(resultSet.getString("fileuptime"));
				project.setFiledeadtime(resultSet.getString("filedeadtime"));
				projects.add(project);
			}

		}
	}
	public void updateproject(String ids[]) {

		executeUpdate("UPDATE stat_project SET name = '"+ids[1]+"',  description = '"+ids[2]+"' WHERE id = "+ids[0]+";");
		
	}
	public void updateid_delete(int id) {
		executeUpdate("UPDATE stat_project SET is_delete = "+0+" WHERE id = "+id+";");
	}
	public  Map<String,Object>  insertproject(String pn,String pd,String pt,String fid, int user_id, String start_date,String end_date) {
		
		try {
			
			String sql="insert into stat_project (name,description,time,client_id,is_delete, user_id, fileuptime, filedeadtime) values('"+pn+"','"+pd+"','"+pt+"','"+2+"',"+1+",'"+user_id+"','"+start_date+"','"+end_date+"')";
			executeUpdate(sql, new PreparedStatementProcessor() {
				@Override
				protected void process(PreparedStatement statement) throws SQLException {
					statement.getGeneratedKeys();
				}
			},new ResultSetProcessor() {
				
				@Override
				protected void process(ResultSet resultSet) throws SQLException {
					 if (resultSet.next()) {
	                     //知其仅有一列，故获取第一列
	                     int id = resultSet.getInt(1);
	                     if(fid!=null){
	                    	 String[] split = fid.split(",");
	                    	 for (String fival : split) {
								executeUpdate("insert into stat_project_ds(project_id,ds_id) values("+id+","+fival+")");
							}
	                     }
	                     
	                     map.put("project_id", id);
	                   
	             } 
				}
			});
			
			
		} catch (Exception e) {
			 e.printStackTrace(); 
		}
		return map;
	}
	public  Map<String,Object> projectinsertfile(String pid,String fid,String projectname,String projectdesc,String time, int user_id, String user_token, String user_name) {
		if(pid!=null&&pid!=""){
			int pi = Integer.parseInt(pid);
			executeUpdate("delete from stat_project_ds where project_id="+pi+";");
			if(fid!=null&&fid!=""){
				String[] fileid = fid.split(",");
				for (String faid : fileid) {
					int filed = Integer.parseInt(faid);
					executeUpdate("insert into stat_project_ds(project_id,ds_id) values("+pid+","+filed+");");
				}
				}
		}else{
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
				String uploadtime = df.format(new Date());// new
															// Date()为获取当前系统时间
				DateTime dt1 = new DateTime(new Date());
				String deadtime = dt1.plusMonths(1).toString("yyyy-MM-dd");
				
				String sql="insert into stat_project (name,description,time,client_id,is_delete, user_id,fileuptime,filedeadtime) values('"+projectname+"','"+projectdesc+"','"+time+"',2,1,'"+user_id+"','"+uploadtime+"','"+deadtime+"');";
				executeUpdate(sql, new PreparedStatementProcessor() {
					@Override
					protected void process(PreparedStatement statement) throws SQLException {
						statement.getGeneratedKeys();
					}
				},new ResultSetProcessor() {
					@Override
					protected void process(ResultSet resultSet) throws SQLException {
						 if (resultSet.next()) {
		                     //知其仅有一列，故获取第一列
		                     int id = resultSet.getInt(1);
		                     if(fid!=null){
		                    	 String[] split = fid.split(",");
		                    	 for (String fival : split) {
									executeUpdate("insert into stat_project_ds(project_id,ds_id) values('"+id+"','"+fival+"');");
								}
		                     }    
		                     
		                   //增加完项目，如果是流式数据，需要增加调用shell脚本
		         			DosageDatabase dosageDatabase = new DosageDatabase(); 
		         			String fileIds = fid;
		         			if(",".equals(fid.substring(fid.length()-1,fid.length()))){
		         				fileIds = fid.substring(0,fid.length()-1);
		         			}
		         			Dosage dosages = dosageDatabase.getDosage(fileIds);
		         			String fileType=dosages.getFileType();
		         			
		         			//判断流式数据
		         			if(null!=fileType && "2".equals(fileType)){
		         				//链接hadoop，执行数据插入到数据库
		         				String client_id = String.valueOf(user_id);
		         				HadoopShellComandThread thread1 = new HadoopShellComandThread("sh tomcat.sh"+" "+client_id+" "+ id+" "+user_token);
		         		        thread1.start();
		         				
		         				//链接es，执行数据插入到ES
		         				EsShellComandThread thread2 = new EsShellComandThread("sh ls.sh "+" "+user_id +" " +fileIds +" "+user_token+" "+user_name);
		         		        thread2.start();
		         		    //新建项目，选择数据，需要重新调用清洗数据
		         			}else if(null!=fileType && "1".equals(fileType)){
		         				
		         				String fileIdsx="";
		         				if(",".equals(fid.substring(fid.length()-1, fid.length()))){
		         					fileIdsx = fid.substring(0,fid.length()-1);
		        				}
		         				
		         				SftpUtil sf = new SftpUtil();
		         				String host = Consant.FILE_SERVER_HOST;
		         				int port = Consant.FILE_SERVER_PORT;
		         				String username = Consant.FILE_SERVER_USER_NAME;
		         				String password = Consant.FILE_SERVER_USER_PASSWD;
		        				ChannelSftp sftp = sf.connect(host, port, username, password);
		        				//生成确认上传的文件到本地服务器 文件格式:第一行  client_id project_id file_name
		        				// 文件目录
		        				String fpath = Consant.FILE_DATA_REEDBACK_DATA_NAME2; 
		        				writeHadoopFile(fpath,String.valueOf(user_id),String.valueOf(id), fileIdsx);
		        				File fileFeedback = new File(fpath);
		        				//上传文件到hadoop应用服务器
		        				sf.upload(Consant.FILE_FEDBACK_DATA_PATH + user_name + "_" + user_id + "/", fileFeedback, sftp,Consant.FILE_FEDBACK_DATA_FILE_NAME2);
		        				
		        				//判断文件是否上传到hdfs完毕
		        				String baseFilePath = Consant.FILE_LOCAL_TEMP_PATH + user_name + "_" + user_id;
		        				File resFlag = GetOuterFileUtil.getOuterFileFromRemote(baseFilePath+"/", "upok.txt", "");
		        				
		        				if(null != resFlag){
		        					//链接hadoop，执行数据插入到数据库
			         				String client_id = String.valueOf(user_id);
			         				HadoopShellComandThread thread1 = new HadoopShellComandThread("sh run.sh"+" "+user_name+" "+ user_id);
			         		        thread1.start();
		        				}
		         			}
		         			
		                    map.put("project_id", id);
		             } 
					}
				});
			} catch (Exception e) {
				 e.printStackTrace(); 
			}
		}
		return map;
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
	public List<DataSource> getDatasource(int project_id) {

		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		
		executeQuery("select sp.*,sds.client_id,sds.name,sds.path,sds.fs,sds.uptime,sds.deadtime from stat_project_ds sp left join stat_data_source sds on sp.ds_id = sds.ds_id where project_id ="+ project_id + ";",result_set_processor_3);
	
		return result_set_processor_3.dss;
	}

	private final class ResultSetProcessor_3 extends ResultSetProcessor {

		private List<DataSource> dss = null;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			dss = new ArrayList<DataSource>();

			while (resultSet.next()) {
				DataSource ds = new DataSource();
				ds.setClient_id(resultSet.getInt("client_id"));
				ds.setProject_id(resultSet.getInt("project_id"));
				ds.setPath(resultSet.getString("path"));
				ds.setFs(resultSet.getInt("fs"));
				ds.setName(resultSet.getString("name"));
				ds.setDs_id(resultSet.getInt("ds_id"));
				ds.setUptime(resultSet.getString("uptime"));
				ds.setDeadtime(resultSet.getString("deadtime"));
				dss.add(ds);

			}

		}
	}

	public List<DataSource> selectfile(int user_id)  {

		ResultSetProcessor_5 resultSetProcessor_5 = new ResultSetProcessor_5();
		executeQuery("select ds_id,path,name,fs,client_id, filestatus from stat_data_source where user_id = '"+user_id+"' and filestatus  in (-1,2,5,6,8) and uptime < now() and deadtime> now();", resultSetProcessor_5);
		return resultSetProcessor_5.dss;
	}

	private final class ResultSetProcessor_5 extends ResultSetProcessor {

		private List<DataSource> dss = null;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			dss = new ArrayList<DataSource>();
			while (resultSet.next()) {
				DataSource ds = new DataSource();
				ds.setClient_id(resultSet.getInt("client_id"));
				ds.setPath(resultSet.getString("path"));
				ds.setFs(resultSet.getInt("fs"));
				ds.setDs_id(resultSet.getInt("ds_id"));
				ds.setFilestatus(resultSet.getInt("filestatus"));
				dss.add(ds);
			}
		}
	}

	public void addProject(Project project,int user_id) {
		executeUpdate("insert into stat_project (id,name, description, datatype, datasize,time,project_data,user_id) "
				+ "values('" + project.getId() + "','" + project.getName() + "','" + project.getDescription() + "','"
				+ project.getDatatype() + "','" + project.getDatasize() + "','" + project.getTime() + "','"+user_id+"')");
	}
	
	
	/*
	 * 通过user_id查找项目
	 */
	
	public List<Project>  getProjectByUserId(int user_id) {
		
		ResultSetProcessor_6 result_set_processor_6 = new ResultSetProcessor_6();
		PreparedStatementProcessor_6 prepared_statement_processor_6 = new PreparedStatementProcessor_6();
		
		executeQuery(
				"SELECT id,name FROM stat_project WHERE user_id = ? and is_delete =1 and fileuptime < now() and filedeadtime> now() "
				+ ";",prepared_statement_processor_6.init(user_id),result_set_processor_6);
		
		return result_set_processor_6.pList;
	}
	
	private final class PreparedStatementProcessor_6 extends PreparedStatementProcessor {
		int user_id;

		private PreparedStatementProcessor init(int user_id) {
			this.user_id = user_id;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setInt(1, user_id);
		}
	}

	private final class ResultSetProcessor_6 extends ResultSetProcessor {
		List<Project> pList=new ArrayList<Project>();
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				Project p =  new Project();
				p.setId(resultSet.getInt(1));
				p.setName(resultSet.getString(2));
				pList.add(p);
			}
		}
	}
	
	/*
	 * 通过user_id 找最新Project_id
	 */
	
	public  int  getProjectIdByUserId(int user_id) {
		
		ResultSetProcessor_7 result_set_processor_7 = new ResultSetProcessor_7();
		PreparedStatementProcessor_7 prepared_statement_processor_7 = new PreparedStatementProcessor_7();
		
		executeQuery(
				"SELECT id FROM stat_project WHERE user_id = ? ORDER BY id DESC LIMIT 1; "+ ";",
				prepared_statement_processor_7.init(user_id),result_set_processor_7);
		
		return result_set_processor_7.projectID;
	}
	
	private final class PreparedStatementProcessor_7 extends PreparedStatementProcessor {
		int user_id;

		private PreparedStatementProcessor init(int user_id) {
			this.user_id = user_id;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setInt(1, user_id);
		}
	}
	private final class ResultSetProcessor_7 extends ResultSetProcessor {
		int projectID=0;
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				projectID= resultSet.getInt(1);
			}
		}
	}
	
	/*
	 * 通过user_id 找最新Project_id
	 */
	
	public  String  getProjectNameByUserId(int project_id) {
		
		ResultSetProcessor_8 result_set_processor_8 = new ResultSetProcessor_8();
		PreparedStatementProcessor_8 prepared_statement_processor_8 = new PreparedStatementProcessor_8();
		
		executeQuery(
				"SELECT name FROM stat_project WHERE id = ?;",
				prepared_statement_processor_8.init(project_id),result_set_processor_8);
		
		return result_set_processor_8.projectName;
	}
	
	private final class PreparedStatementProcessor_8 extends PreparedStatementProcessor {
		int project_id;

		private PreparedStatementProcessor init(int project_id) {
			this.project_id = project_id;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setInt(1, project_id);
		}
	}
	private final class ResultSetProcessor_8 extends ResultSetProcessor {
		String projectName="";
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				projectName= resultSet.getString(1);
			}
		}
	}
}
