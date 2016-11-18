package com.open01.logs.model;

public class SearchOuter {
	private int order;
	private String datatime;
	private String message;
	public SearchOuter(){
		
	}
	
	public SearchOuter(int order, String datetime, String message){
		this.order = order;
		this.datatime = datetime;
		this.message = message;
	}
	public String getDatatime() {
		return datatime;
	}
	public void setDatatime(String datatime) {
		this.datatime = datatime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}

}
