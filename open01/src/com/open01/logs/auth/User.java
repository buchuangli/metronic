package com.open01.logs.auth;

import java.io.Serializable;

public class User implements Serializable {

	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int user_id;
	private int client_id;
	private String name;
	private String password;
	private String company;
	private String  phone; 
	private String sitename;
	private String email;
	private String image;
	private String sectionName; 
	private String statu;
	private String user_token;
	private int limit_data_size;
	
	   
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUser_token() {
		return user_token;
	}
	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public int getLimit_data_size() {
		return limit_data_size;
	}
	public void setLimit_data_size(int limit_data_size) {
		this.limit_data_size = limit_data_size;
	}
	@Override
	public String toString() {
		return "User [client_id=" + client_id + ", name=" + name + ", password=" + password + ", phone=" + phone
				+ ", sitename=" + sitename + ", email=" + email + ", image=" + image + "]";
	}
	
	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}
}
