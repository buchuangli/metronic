package com.open01.logs.model;

public class Dosage {
private int ds_id;
private int client_id;
private String name;
private String path;
private int fs;
private long filesize;
private String uptime;
private int filestatus;
private String projectname;
private String projectdesc;
private String projectid;
private String deadtime;
private String fileType;

public String getFileType() {
	return fileType;
}
public void setFileType(String fileType) {
	this.fileType = fileType;
}
public String getDeadtime() {
	return deadtime;
}
public void setDeadtime(String deadtime) {
	this.deadtime = deadtime;
}
public String getProjectid() {
	return projectid;
}
public void setProjectid(String projectid) {
	this.projectid = projectid;
}
public String getProjectname() {
	return projectname;
}
public void setProjectname(String projectname) {
	this.projectname = projectname;
}
public String getProjectdesc() {
	return projectdesc;
}
public void setProjectdesc(String projectdesc) {
	this.projectdesc = projectdesc;
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
public long getFilesize() {
	return filesize;
}
public void setFilesize(long filesize) {
	this.filesize = filesize;
}

public String getUptime() {
	return uptime;
}
public void setUptime(String uptime) {
	this.uptime = uptime;
}
public int getFilestatus() {
	return filestatus;
}
public void setFilestatus(int filestatus) {
	this.filestatus = filestatus;
}
@Override
public String toString() {
	return "Dosage [ds_id=" + ds_id + ", client_id=" + client_id + ", name=" + name + ", path=" + path + ", fs=" + fs
			+ ", filesize=" + filesize + ", uptime=" + uptime + ", filestatus=" + filestatus + ", projectname="
			+ projectname + ", projectdesc=" + projectdesc + ", projectid=" + projectid + "]";
}

}
