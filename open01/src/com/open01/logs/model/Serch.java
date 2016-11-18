package com.open01.logs.model;

public class Serch {
	private String message,datatime;
	private int order;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDatatime() {
		return datatime;
	}

	public void setDatatime(String datatime) {
		this.datatime = datatime;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Serch [message=" + message + ", datatime=" + datatime + ", order=" + order + "]";
	}
	
}
