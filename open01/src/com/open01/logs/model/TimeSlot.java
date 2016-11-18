package com.open01.logs.model;

public class TimeSlot {

	private long time, pvNum, ipNum, dataNum,urlNum,statusNum,osNum,browserNum,requestNum,referrerNum,stNum,stayTime;
	private String url,osType,browserType,requestType,ip,referrerType,province;
	private int status;
	//private List<String> IPs;

	
	
	public long getTime() {
		return time;
	}

	

	public long getStNum() {
		return stNum;
	}



	public void setStNum(long stNum) {
		this.stNum = stNum;
	}



	public long getStayTime() {
		return stayTime;
	}



	public void setStayTime(long stayTime) {
		this.stayTime = stayTime;
	}



	public String getProvince() {
		return province;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public long getReferrerNum() {
		return referrerNum;
	}



	public void setReferrerNum(long referrerNum) {
		this.referrerNum = referrerNum;
	}




	public String getReferrerType() {
		return referrerType;
	}



	public void setReferrerType(String referrerType) {
		this.referrerType = referrerType;
	}



	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getRequestNum() {
		return requestNum;
	}

	public void setRequestNum(long requestNum) {
		this.requestNum = requestNum;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public long getBrowserNum() {
		return browserNum;
	}

	public void setBrowserNum(long browserNum) {
		this.browserNum = browserNum;
	}

	public String getBrowserType() {
		return browserType;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	public long getOsNum() {
		return osNum;
	}

	public void setOsNum(long osNum) {
		this.osNum = osNum;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getStatusNum() {
		return statusNum;
	}

	public void setStatusNum(long statusNum) {
		this.statusNum = statusNum;
	}

	public long getUrlNum() {
		return urlNum;
	}

	public void setUrlNum(long urlNum) {
		this.urlNum = urlNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getPvNum() {
		return pvNum;
	}

	public void setPvNum(long pvNum) {
		this.pvNum = pvNum;
	}

	public long getIpNum() {
		return ipNum;
	}

	public void setIpNum(long ipNum) {
		this.ipNum = ipNum;
	}

	public long getDataNum() {
		return dataNum;
	}

	public void setDataNum(long dataNum) {
		this.dataNum = dataNum;
	}

	
}