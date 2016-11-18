package com.open01.logs.web;


public class Project {

	private int id, datasize,is_delete;
	private String name, description, datatype,project_data,fileid,time,fileuptime,filedeadtime;

	public String getFileuptime() {
		return fileuptime;
	}

	public void setFileuptime(String fileuptime) {
		this.fileuptime = fileuptime;
	}

	public String getFiledeadtime() {
		return filedeadtime;
	}

	public void setFiledeadtime(String filedeadtime) {
		this.filedeadtime = filedeadtime;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getProject_data() {
		return project_data;
	}

	public void setProject_data(String project_data) {
		this.project_data = project_data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public int getDatasize() {
		return datasize;
	}

	public void setDatasize(int datasize) {
		this.datasize = datasize;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	
	
}
