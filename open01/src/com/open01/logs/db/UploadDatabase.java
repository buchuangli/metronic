package com.open01.logs.db;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UploadDatabase extends Open01Database{
	private Map<String,Object> map=new HashMap<String,Object>();
	public Map<String,Object> fileinsert(Object client_id,String name,String path ,long filesize,String filename,Object uid,String host,int port,
			String username,String password,String uploadtime,String deadtime, int user_id, String fileData, String project_ids) {
			try {
				String sql="insert into stat_data_source (client_id,name,path,fs,filesize,uptime,filestatus,deadtime, user_id) "
						+ "values("+client_id+",'"+name+"','"+path+"',2,"+filesize+",'"+uploadtime+"',-1,'"+deadtime+"','"+user_id+"');";
				executeUpdate(sql, new PreparedStatementProcessor() {
					@Override
					protected void process(PreparedStatement statement) throws SQLException {
						statement.getGeneratedKeys();
					}
					
				},new ResultSetProcessor() {
					@Override
					protected void process(ResultSet resultSet) throws SQLException,Exception {
						 if (resultSet.next()) {
		                     //知其仅有一列，故获取第一列
		                int  id = resultSet.getInt(1);
		                try {
		                	   // 将文件id和项目id写入文件项目关系表中 
		                	if(!"".equals(project_ids) && null != project_ids){ 
		                	for (int i=0; i<project_ids.split(",").length; i++) {
			    				String sql="insert into stat_project_ds(project_id,ds_id) values("+project_ids.split(",")[i]+","+id+")";
			        			executeUpdate(sql);  
							}
		                	}
		        		} catch (Exception e) {
		        			 e.printStackTrace(); 
		        		}
		                C:
		                try {
		                	String createtxt = fileData;
		                	File createpath = new File(createtxt);
		                    /* 创建写入对象 */
		                    FileWriter fileWriter = new FileWriter(createtxt);
		                    /* 创建缓冲区 */
		                    BufferedWriter writer = new BufferedWriter(fileWriter);
		                    /* 写入字符串 */
		                    writer.write(uid+" "+id+" "+filename+" "+username);
		                    /* 关掉对象 */
		                    writer.close(); 
		                    fileWriter.close();
		                    if  (!createpath .exists()  && !createpath .isDirectory()) {       
								break C;
							} else  {  
								 map.put("filepath", createpath);
								 map.put("file_id", id); 
							}
		                   } catch (IOException e) {
		                    e.printStackTrace();
		                   }
		        		
		             }
					}
				});
			} catch (Exception e) {
				 e.printStackTrace(); 
			}
			return map;
		}
	/**
	 * 将项目ID和文件ID插入项目文件关系表
	 * @param ds_id
	 * @param project_id
	 */
	public void insertOnlineFileData(String client_id,String name, String uptime , String deadtime, int user_id) { 
		try { 
			long filesize = 1L;
			String sql ="INSERT INTO stat_data_source(client_id,name,path,fs,filesize,filestatus,projectname,projectdesc,projectid,uptime,deadtime,user_id,file_type) values ('"+client_id+"','"+name+"','',2,'"+filesize+"','8','','','','"+uptime+"','"+deadtime+"','"+user_id+"','2')";
			
			System.out.println(sql);
			executeUpdate(sql);
			
		} catch (Exception e) {
			 e.printStackTrace(); 
		}
	}

	/**
	 * 将项目ID和文件ID插入项目文件关系表
	 * @param ds_id
	 * @param project_id
	 */
	public void updateFileSizeByFileId(String file_id,String file_size) { 
		try { 
			String sql ="update stat_data_source set filesize = '"+file_size+"' where ds_id='"+file_id+"';";
			executeUpdate(sql);
			
		} catch (Exception e) {
			 e.printStackTrace(); 
		}
	}
	
	/**
	 * 将项目ID和文件ID插入项目文件关系表
	 * @param ds_id
	 * @param project_id
	 */
	public void confirmSubmitData(String file_id) { 
		try { 
			String sql2 ="update stat_data_source set filestatus = '2' where ds_id='"+file_id+"';";
			executeUpdate(sql2);
		} catch (Exception e) {
			 e.printStackTrace(); 
		}
	}
}