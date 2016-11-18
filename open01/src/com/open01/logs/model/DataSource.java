package com.open01.logs.model;


public class DataSource {
private int ds_id;
private int project_id;
private int client_id;
private String name;
private String path;
private String uptime;
private String deadtime;
private int fs,filestatus;
public int getFilestatus() {
	return filestatus;
}
public void setFilestatus(int filestatus) {
	this.filestatus = filestatus;
}
public String getUptime() {
	return uptime;
}
public void setUptime(String uptime) {
	this.uptime = uptime;
}
public String getDeadtime() {
	return deadtime;
}
public void setDeadtime(String deadtime) {
	this.deadtime = deadtime;
}
public int getProject_id() {
	return project_id;
}
public void setProject_id(int project_id) {
	this.project_id = project_id;
}
public int getDs_id() {
	return ds_id;
}
public void setDs_id(int ds_id) {
	this.ds_id = ds_id;
}
public int getClient_id() {
	return client_id;
}
public void setClient_id(int client_id) {
	this.client_id = client_id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPath() {
	return path;
}
public void setPath(String path) {
	this.path = path;
}
public int getFs() {
	return fs;
}
public void setFs(int fs) {
	this.fs = fs;
}

}
