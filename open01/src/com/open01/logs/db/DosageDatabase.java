package com.open01.logs.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.open01.logs.auth.User;
import com.open01.logs.model.DataSource;
import com.open01.logs.model.Dosage;
import com.open01.logs.web.Project;

public class DosageDatabase extends Open01Database{

	public DosageDatabase() {
		super();
	}
	public List<Dosage> getdosage(int user_id) {

		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();

		executeQuery("SELECT ds_id,name, client_id, filesize, filestatus,path,uptime,projectname,projectdesc,"
				+ "projectid,deadtime FROM stat_data_source where filestatus in (-1,2,5,6,8) and user_id='"+user_id+"' and uptime < now() and deadtime > now() order by uptime ;",
				result_set_processor_1);
		for(Dosage p:result_set_processor_1.dosages){
			String projectname = "",projectdesc="",pds="",pda="",projectid="",pid="";
			List<Project>  ds = getDatasource(p.getDs_id());
			if(ds.size()>0){
				for(Project d:ds){
					projectname+=d.getName()+",";
					pds=projectname.substring(0,projectname.length()-1);
					projectdesc+=d.getDescription()+",";
					pda=projectdesc.substring(0,projectdesc.length()-1);
					projectid += d.getId()+",";
					pid=projectid.substring(0,projectid.length()-1);
				}
				p.setProjectname(pds);
				p.setProjectdesc(pda);
				p.setProjectid(pid);
			}
			
		}
		return result_set_processor_1.dosages;
	}
	private final class ResultSetProcessor_1 extends ResultSetProcessor {

		private List<Dosage> dosages = null;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {

			dosages = new ArrayList<Dosage>();

			while (resultSet.next()) {
				Dosage dosage=new Dosage();
				dosage.setDs_id(resultSet.getInt("ds_id"));
				dosage.setName(resultSet.getString("name"));
				dosage.setClient_id(resultSet.getInt("client_id"));
				dosage.setFilesize(resultSet.getLong("filesize"));
				dosage.setFilestatus(resultSet.getInt("filestatus"));
				dosage.setPath(resultSet.getString("path"));
				dosage.setUptime(resultSet.getString("uptime"));
				dosage.setDeadtime(resultSet.getString("deadtime"));
				dosages.add(dosage);
			}

		}
	}
	public List<Project> getDatasource(int fileid) {

		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		
		executeQuery("select sp.*,sds.name,sds.description,sds.id,sds.is_delete from stat_project_ds sp left join stat_project sds on sp.project_id = sds.id where ds_id ="+ fileid + " and sds.is_delete ='"+1+"';",result_set_processor_3);
	
		return result_set_processor_3.projects;
	}

	private final class ResultSetProcessor_3 extends ResultSetProcessor {

		private List<Project> projects = null;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {

			projects = new ArrayList<Project>();

			while (resultSet.next()) {

				Project project = new Project();
				project.setId(resultSet.getInt("id"));
				project.setName(resultSet.getString("name"));
				project.setDescription(resultSet.getString("description"));
				project.setIs_delete(resultSet.getInt("is_delete"));
				projects.add(project);
			}

		}
	}
	/*
	 * 删除
	 * 修改状态字段　为０则删除为１则展示
	 */
	public void updateid_delete(String fid) {
		String[] split = fid.split(",");
		for (String id : split) {
			executeUpdate("UPDATE stat_data_source SET filestatus = "+0+" WHERE ds_id = '"+id+"';");
			executeUpdate("delete from  stat_project_ds WHERE ds_id = '"+id+"';");
		}
	}
	
	
/**
 * 拿到最大限制使用流量
 * @param user_id
 * @return 
 */
public int getTotalLimitSize(int user_id) {

	ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
	executeQuery("SELECT limit_data_size from user where user_id = '"+user_id+"' ;",result_set_processor_5);
	int limit_data_size = 0;
	for(User p:result_set_processor_5.userList){
		limit_data_size = p.getLimit_data_size();
	}
	return limit_data_size;
}

/**
 * 拿到最大限制使用流量结果集
 * @param user_id
 * @return
 */
private final class ResultSetProcessor_5 extends ResultSetProcessor {
	private List<User> userList = null;
	@Override
	protected void process(ResultSet resultSet) throws SQLException {
		userList = new ArrayList<User>();
		while (resultSet.next()) {
			User user=new User();
			user.setLimit_data_size(resultSet.getInt("limit_data_size"));
			userList.add(user);
		}
	}
}
/**
 * 拿到用户使用流量总计
 * @param user_id
 * @return
 */ 
public int getUsedDataSize(int user_id) {
	ResultSetProcessor_6 result_set_processor_6 = new ResultSetProcessor_6();
	executeQuery("select  sum(filesize) used_size from stat_data_source where filestatus in (-1,2,5,6,8)  and user_id = '"+user_id+"' ;",result_set_processor_6);
	return result_set_processor_6.intUsedSize;
}
/**
 * 拿到用户使用流量总计结果集
 * @param user_id
 * @return
 */
private  final class ResultSetProcessor_6 extends ResultSetProcessor {
	private int intUsedSize = 0;
	@Override
	protected void process(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			intUsedSize = resultSet.getInt("used_size");
		}
	}
}


public Dosage getDosage(String fileid) {
	ResultSetProcessor_8 result_set_processor_8 = new ResultSetProcessor_8();
	String sql = "select ds_id, name,uptime,deadtime,file_type from  stat_data_source sp where sp.ds_id in ("+ fileid + ") and  sp.filestatus in (-1,2,5,6,8) order by uptime desc limit 1;";
	System.out.println(sql);
	executeQuery(sql,result_set_processor_8);

	return result_set_processor_8.dosage; 
}

private final class ResultSetProcessor_8 extends ResultSetProcessor {

	private Dosage dosage = new Dosage();

	@Override
	protected void process(ResultSet resultSet) throws SQLException {
		while (resultSet.next()){
		dosage.setDs_id(resultSet.getInt("ds_id"));
		dosage.setName(resultSet.getString("name"));
		dosage.setUptime(resultSet.getString("uptime"));
		dosage.setDeadtime(resultSet.getString("deadtime"));
		dosage.setFileType(resultSet.getString("file_type"));
		}
	}
}
}
