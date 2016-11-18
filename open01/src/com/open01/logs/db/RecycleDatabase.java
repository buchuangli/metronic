package com.open01.logs.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.open01.logs.model.DataSource;
import com.open01.logs.web.Project;

public class RecycleDatabase extends Open01Database {

	public RecycleDatabase() {

		super();

	}

	public List<Project> getProjects( int user_id) {

		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();

		executeQuery("SELECT id,name, description, datatype, datasize,time,project_data,is_delete FROM stat_project where is_delete="+0+" and user_id = '"+user_id+"';", result_set_processor_1);
			
		for(Project p:result_set_processor_1.projects){
			String pd = "",fileid="",pds="",pda="";
			List<DataSource>  ds = getDatasource(p.getId());
			if(ds.size()>0){
				for(DataSource d:ds){
					pd+=d.getName()+",";
					pds=pd.substring(0,pd.length()-1);
					fileid+=d.getDs_id()+",";
					pda=fileid.substring(0,fileid.length()-1);
				}
				p.setProject_data(pds);
				p.setFileid(pda);
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
				project.setIs_delete(resultSet.getInt("is_delete"));
				projects.add(project);
			}
		}
	}
	public List<DataSource> getDatasource(int project_id) {

		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		
		executeQuery("select sp.*,sds.client_id,sds.name,sds.path,sds.fs from stat_project_ds sp left join stat_data_source sds on sp.ds_id = sds.ds_id where project_id ="+ project_id + ";",result_set_processor_3);
	
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
				dss.add(ds);
			}
		}
	}
	public void updateid_delete(int id) {
		executeUpdate("UPDATE stat_project SET is_delete = "+1+" WHERE id = "+id+";");
	}
	public void delete(int id) {
		executeUpdate("UPDATE stat_project SET is_delete = "+3+" WHERE id = "+id+";");
		executeUpdate("delete from stat_project_ds where project_id="+id+";");
	}
	public void deletes(String[] id) {
		for (String ids : id) {
			executeUpdate("UPDATE stat_project SET is_delete = "+3+" WHERE id = "+ids+";");
			executeUpdate("delete from stat_project_ds where project_id="+ids+";");
		}
	}
	public void recover(String[] id) {
		for (String ids : id) {
			executeUpdate("UPDATE stat_project SET is_delete = "+1+" WHERE id = "+ids+";");
		}
	}

}
