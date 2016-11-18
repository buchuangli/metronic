package com.open01.logs.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.open01.logs.db.DataCountDatabase;
import com.open01.logs.model.TimeSlot;
import com.open01.logs.util.DateUtils;
import com.open01.logs.util.JsonUtils;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class DataCountBean extends WholePartBean implements BooleanCondition {


	/**
	 * Default constructor.
	 */
	public DataCountBean() {
		super("cmd");
		addPart(null, new Part0());// 默认获取一天的数据，从hour表
		addPart("WEL:SELECTYEAR", new Part1());// 获取年表信息
		addPart("WEL:SELECTMONTH", new Part2());// 获取月表信息
		addPart("WEL:SELECTDAILY", new Part3());// 获取天表信息
		addPart("WEL:SELECTHOUR", new Part4());// 获取小时表信息
		addPart("WEL:SELECTMINUTE", new Part5());// 获取分钟,小时,日,月,年表信息
		
		addPart("WEL:GETBYTESBYURLYEAR", new Part6());// 根据url查询Bytes
		addPart("WEL:GETBYTESBYURLMONTH", new Part7());// 根据url查询Bytes
		addPart("WEL:GETBYTESBYURLDAILY", new Part8());// 根据url查询Bytes
		addPart("WEL:GETBYTESBYURLHOUR", new Part9());// 根据url查询Bytes
		addPart("WEL:GETBYTESBYURLMINUTE", new Part10());// 根据url查询Bytes
		
		addPart("WEL:GETCHARINFO", new Part11());//
	}

	@Override
	public boolean isTrue() {
		// TODO Auto-generated method stub
		return true;
	}
	DataCountDatabase dc  = new DataCountDatabase ();
	int client_id=0;
	int project_id = 0;
	private final class Part0 extends DefaultPlainBean {
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime=(long) DateUtils.getInitDate("hour").get(0);
			long endTime=(long) DateUtils.getInitDate("hour").get(1);
			
			List list1 = new ArrayList();
			List<TimeSlot> tsList = dc.getHourData(startTime, endTime, client_id, project_id);
			Long thisAllBytes = dc.getHourAllData(startTime, endTime, client_id, project_id);
		//	Long lastAllBytes = dc.getHourAllData(startTime, endTime, client_id, project_id);
			Map map2 = null;
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long bytes=0l;
				for(int j = 0; j <tsList.size(); j++){
					if(t==tsList.get(j).getTime()){
						m=t;
						bytes=tsList.get(j).getDataNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "2"));
					map2.put("bytes", bytes);
				}else{
					map2.put("time", DateUtils.timeToString(t, "2"));
					map2.put("bytes", 0);
				}	
					
				list1.add(map2);
			}
			
			Map map3 = null;
			List list2 = new ArrayList();
			List<TimeSlot> ipList = dc.getTopIpHour(startTime, endTime, client_id, project_id);
			for (int i = 0; i < ipList.size(); i++) {
				map3 = new HashMap();
				map3.put("ip",ipList.get(i).getIp());
				map3.put("bytes", ipList.get(i).getIpNum());
				list2.add(map3);
			}
			Map map4 = null;
			List list3 = new ArrayList();
			List<TimeSlot> urlList = dc.getTopUrlHour(startTime, endTime, client_id, project_id);
			for (int i = urlList.size()-1; i >=0; i--) {
				map4 = new HashMap();
				map4.put("url",urlList.get(i).getUrl());
				map4.put("bytes", urlList.get(i).getDataNum());
				list3.add(map4);
			}
			
			//查询地图中每个省份的页面访问总量
			List<TimeSlot> geographyPVList = dc.getGeographyPVHour(startTime, endTime, client_id, project_id,"中国");
			List list6 = new ArrayList();
			Map map7 = null;
			for (int i = 0; i <geographyPVList.size(); i++) {
				map7 = new HashMap();
				map7.put("province", geographyPVList.get(i).getProvince());
				map7.put("pvNum", geographyPVList.get(i).getPvNum());
				list6.add(map7);
			}
			//查询地图中每个省份的独立Ip数
			List<TimeSlot> geographyIpList = dc.getGeographyIpHour(startTime, endTime, client_id, project_id,"中国");
			List list4 = new ArrayList();
			Map map5 = null;
			for (int i = 0; i <geographyIpList.size(); i++) {
				map5 = new HashMap();
				map5.put("province", geographyIpList.get(i).getProvince());
				map5.put("pvNum", geographyIpList.get(i).getIpNum());
				list4.add(map5);
			}
			//查询地图中每个省份的字节数
			List<TimeSlot> geographyByteList = dc.getGeographyDataHour(startTime, endTime, client_id, project_id,"中国");
			List list5 = new ArrayList();
			Map map6 = null;
			for (int i = 0; i <geographyByteList.size(); i++) {
				map6 = new HashMap();
				map6.put("province", geographyByteList.get(i).getProvince());
				map6.put("pvNum", geographyByteList.get(i).getDataNum());
				list5.add(map6);
			}
			map1.put("geographyPVCount", list6);
			map1.put("geographyByteList", list5);
			map1.put("geographyIpCount", list4);
			map1.put("urlCount",list3);
			map1.put("ipCount",list2);
			map1.put("timeSlot", list1);
			map1.put("thisAllBytes", thisAllBytes);
			//map1.put("lastAllBytes", lastAllBytes);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}

	private final class Part1 extends DefaultPlainBean {

		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"5");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
		/*	try {
				lastStartTime = DateUtils.getLastDate(startTime, endTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			List list1 = new ArrayList();
			List<TimeSlot> tsList = dc.getYearData(startTime, endTime, client_id, project_id);
			Long thisAllBytes = dc.getYearAllData(startTime, endTime, client_id, project_id);
		//	Long lastAllBytes = dc.getYearAllData(lastStartTime, startTime, client_id, project_id);
			Map map2 = null;
			
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long bytes=0l;
				for(int j = 0; j <tsList.size(); j++){
					if(t==tsList.get(j).getTime()){
						m=t;
						bytes=tsList.get(j).getDataNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "5"));
					map2.put("bytes", bytes);
				}else{
					map2.put("time", DateUtils.timeToString(t, "5"));
					map2.put("bytes", 0);
				}	
					
				list1.add(map2);
			}
			
			
			Map map3 = null;
			List list2 = new ArrayList();
			List<TimeSlot> ipList = dc.getTopIpYear(startTime, endTime, client_id, project_id);
			for (int i = 0; i < ipList.size(); i++) {
				map3 = new HashMap();
				map3.put("ip",ipList.get(i).getIp());
				map3.put("bytes", ipList.get(i).getIpNum());
				list2.add(map3);
			}
			Map map4 = null;
			List list3 = new ArrayList();
			List<TimeSlot> urlList = dc.getTopUrlYear(startTime, endTime, client_id, project_id);
			for (int i = urlList.size()-1; i >=0; i--) {
				map4 = new HashMap();
				map4.put("url",urlList.get(i).getUrl());
				map4.put("bytes", urlList.get(i).getDataNum());
				list3.add(map4);
			}
			//查询地图中每个省份的页面访问总量
			List<TimeSlot> geographyPVList = dc.getGeographyPVYear(startTime, endTime, client_id, project_id,"中国");
			List list6 = new ArrayList();
			Map map7 = null;
			for (int i = 0; i <geographyPVList.size(); i++) {
				map7 = new HashMap();
				map7.put("province", geographyPVList.get(i).getProvince());
				map7.put("pvNum", geographyPVList.get(i).getPvNum());
				list6.add(map7);
			}
			//查询地图中每个省份的独立Ip数
			List<TimeSlot> geographyIpList = dc.getGeographyIpYear(startTime, endTime, client_id, project_id,"中国");
			List list4 = new ArrayList();
			Map map5 = null;
			for (int i = 0; i <geographyIpList.size(); i++) {
				map5 = new HashMap();
				map5.put("province", geographyIpList.get(i).getProvince());
				map5.put("pvNum", geographyIpList.get(i).getIpNum());
				list4.add(map5);
			}
			//查询地图中每个省份的字节数
			List<TimeSlot> geographyByteList = dc.getGeographyDataYear(startTime, endTime, client_id, project_id,"中国");
			List list5 = new ArrayList();
			Map map6 = null;
			for (int i = 0; i <geographyByteList.size(); i++) {
				map6 = new HashMap();
				map6.put("province", geographyByteList.get(i).getProvince());
				map6.put("pvNum", geographyByteList.get(i).getDataNum());
				list5.add(map6);
			}
			map1.put("geographyPVCount", list6);
			map1.put("geographyByteList", list5);
			map1.put("geographyIpCount", list4);
			map1.put("urlCount",list3);
			map1.put("ipCount",list2);
			map1.put("timeSlot", list1);
			map1.put("thisAllBytes", thisAllBytes);
		//	map1.put("lastAllBytes", lastAllBytes);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part2 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime=0;
			long endTime=0;
		//	long lastStartTime=0;
			/*
			startTime = 201602;
			endTime = 201606;*/
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"4");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
		/*	try {
				lastStartTime = DateUtils.getLastDate(startTime, endTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			List list1 = new ArrayList();
			List<TimeSlot> tsList = dc.getMonthData(startTime, endTime, client_id, project_id);
			Long thisAllBytes = dc.getMonthAllData(startTime, endTime, client_id, project_id);
	//		Long lastAllBytes = dc.getMonthAllData(lastStartTime, startTime, client_id, project_id);
			Map map2 = null;
			
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long bytes=0l;
				for(int j = 0; j <tsList.size(); j++){
					if(t==tsList.get(j).getTime()){
						m=t;
						bytes=tsList.get(j).getDataNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "4"));
					map2.put("bytes", bytes);
				}else{
					map2.put("time", DateUtils.timeToString(t, "4"));
					map2.put("bytes", 0);
				}	
					
				list1.add(map2);
			}
			
			
			Map map3 = null;
			List list2 = new ArrayList();
			List<TimeSlot> ipList = dc.getTopIpMonth(startTime, endTime, client_id, project_id);
			for (int i = 0; i < ipList.size(); i++) {
				map3 = new HashMap();
				map3.put("ip",ipList.get(i).getIp());
				map3.put("bytes", ipList.get(i).getIpNum());
				list2.add(map3);
			}
			Map map4 = null;
			List list3 = new ArrayList();
			List<TimeSlot> urlList = dc.getTopUrlMonth(startTime, endTime, client_id, project_id);
			for (int i = urlList.size()-1; i >=0; i--) {
				map4 = new HashMap();
				map4.put("url",urlList.get(i).getUrl());
				map4.put("bytes", urlList.get(i).getDataNum());
				list3.add(map4);
			}
			//查询地图中每个省份的页面访问总量
			List<TimeSlot> geographyPVList = dc.getGeographyPVMonth(startTime, endTime, client_id, project_id,"中国");
			List list6 = new ArrayList();
			Map map7 = null;
			for (int i = 0; i <geographyPVList.size(); i++) {
				map7 = new HashMap();
				map7.put("province", geographyPVList.get(i).getProvince());
				map7.put("pvNum", geographyPVList.get(i).getPvNum());
				list6.add(map7);
			}
			//查询地图中每个省份的独立Ip数
			List<TimeSlot> geographyIpList = dc.getGeographyIpMonth(startTime, endTime, client_id, project_id,"中国");
			List list4 = new ArrayList();
			Map map5 = null;
			for (int i = 0; i <geographyIpList.size(); i++) {
				map5 = new HashMap();
				map5.put("province", geographyIpList.get(i).getProvince());
				map5.put("pvNum", geographyIpList.get(i).getIpNum());
				list4.add(map5);
			}
			//查询地图中每个省份的字节数
			List<TimeSlot> geographyByteList = dc.getGeographyDataMonth(startTime, endTime, client_id, project_id,"中国");
			List list5 = new ArrayList();
			Map map6 = null;
			for (int i = 0; i <geographyByteList.size(); i++) {
				map6 = new HashMap();
				map6.put("province", geographyByteList.get(i).getProvince());
				map6.put("pvNum", geographyByteList.get(i).getDataNum());
				list5.add(map6);
			}
			map1.put("geographyPVCount", list6);
			map1.put("geographyByteList", list5);
			map1.put("geographyIpCount", list4);
			map1.put("urlCount",list3);
			map1.put("ipCount",list2);
			map1.put("timeSlot", list1);
			map1.put("thisAllBytes", thisAllBytes);
	//		map1.put("lastAllBytes", lastAllBytes);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part3 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime=0;
			long endTime=0;
		/*	startTime = 20160200;
			endTime = 20160300;*/
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
	//		long lastStartTime=0;
			/*try {
				lastStartTime = DateUtils.getLastDate(startTime, endTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			List list1 = new ArrayList();
			List<TimeSlot> tsList = dc.getDailyData(startTime, endTime, client_id, project_id);
			Long thisAllBytes = dc.getDailyAllData(startTime, endTime, client_id, project_id);
	//		Long lastAllBytes = dc.getDailyAllData(lastStartTime, startTime, client_id, project_id);
			Map map2 = null;
		
			List timeList = DateUtils.getBetweenDate(startTime, endTime,"daily");
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long bytes=0l;
				for(int j = 0; j <tsList.size(); j++){
					if(t==tsList.get(j).getTime()){
						m=t;
						bytes=tsList.get(j).getDataNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "3"));
					map2.put("bytes", bytes);
				}else{
					map2.put("time", DateUtils.timeToString(t, "3"));
					map2.put("bytes", 0);
				}	
					
				list1.add(map2);
			}
			
			Map map3 = null;
			List list2 = new ArrayList();
			List<TimeSlot> ipList = dc.getTopIpDaily(startTime, endTime, client_id, project_id);
			for (int i = 0; i < ipList.size(); i++) {
				map3 = new HashMap();
				map3.put("ip",ipList.get(i).getIp());
				map3.put("bytes", ipList.get(i).getIpNum());
				list2.add(map3);
			}
			Map map4 = null;
			List list3 = new ArrayList();
			List<TimeSlot> urlList = dc.getTopUrlDaily(startTime, endTime, client_id, project_id);
			for (int i = urlList.size()-1; i >=0; i--) {
				map4 = new HashMap();
				map4.put("url",urlList.get(i).getUrl());
				map4.put("bytes", urlList.get(i).getDataNum());
				list3.add(map4);
			}
			//查询地图中每个省份的页面访问总量
			List<TimeSlot> geographyPVList = dc.getGeographyPVDaily(startTime, endTime, client_id, project_id,"中国");
			List list6 = new ArrayList();
			Map map7 = null;
			for (int i = 0; i <geographyPVList.size(); i++) {
				map7 = new HashMap();
				map7.put("province", geographyPVList.get(i).getProvince());
				map7.put("pvNum", geographyPVList.get(i).getPvNum());
				list6.add(map7);
			}
			//查询地图中每个省份的独立Ip数
			List<TimeSlot> geographyIpList = dc.getGeographyIpDaily(startTime, endTime, client_id, project_id,"中国");
			List list4 = new ArrayList();
			Map map5 = null;
			for (int i = 0; i <geographyIpList.size(); i++) {
				map5 = new HashMap();
				map5.put("province", geographyIpList.get(i).getProvince());
				map5.put("pvNum", geographyIpList.get(i).getIpNum());
				list4.add(map5);
			}
			//查询地图中每个省份的字节数
			List<TimeSlot> geographyByteList = dc.getGeographyDataDaily(startTime, endTime, client_id, project_id,"中国");
			List list5 = new ArrayList();
			Map map6 = null;
			for (int i = 0; i <geographyByteList.size(); i++) {
				map6 = new HashMap();
				map6.put("province", geographyByteList.get(i).getProvince());
				map6.put("pvNum", geographyByteList.get(i).getDataNum());
				list5.add(map6);
			}
			map1.put("geographyPVCount", list6);
			map1.put("geographyByteList", list5);
			map1.put("geographyIpCount", list4);
			map1.put("urlCount",list3);
			map1.put("ipCount",list2);
			map1.put("timeSlot", list1);
			map1.put("thisAllBytes", thisAllBytes);
		//	map1.put("lastAllBytes", lastAllBytes);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part4 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime=0;
			long endTime=0;
			/*startTime = 2016020100;
			endTime = 2016020200;*/
			
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
		/*	long lastStartTime=0;
			try {
				lastStartTime = DateUtils.getLastDate(startTime, endTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			List list1 = new ArrayList();
			List<TimeSlot> tsList = dc.getHourData(startTime, endTime, client_id, project_id);
			Long thisAllBytes = dc.getHourAllData(startTime, endTime, client_id, project_id);
//			Long lastAllBytes = dc.getHourAllData(lastStartTime, startTime, client_id, project_id);
			Map map2 = null;
			/*for (int i = 0; i < tsList.size(); i++) {
				map2 = new HashMap();
				map2.put("bytes", tsList.get(i).getDataNum());
				map2.put("time", DateUtils.timeToString(tsList.get(i).getTime(), "2"));
				list1.add(map2);
			}*/
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long bytes=0l;
				for(int j = 0; j <tsList.size(); j++){
					if(t==tsList.get(j).getTime()){
						m=t;
						bytes=tsList.get(j).getDataNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "2"));
					map2.put("bytes", bytes);
				}else{
					map2.put("time", DateUtils.timeToString(t, "2"));
					map2.put("bytes", 0);
				}	
					
				list1.add(map2);
			}
			
			Map map3 = null;
			List list2 = new ArrayList();
			List<TimeSlot> ipList = dc.getTopIpDaily(startTime, endTime, client_id, project_id);
			for (int i = 0; i < ipList.size(); i++) {
				map3 = new HashMap();
				map3.put("ip",ipList.get(i).getIp());
				map3.put("bytes", ipList.get(i).getIpNum());
				list2.add(map3);
			}
			Map map4 = null;
			List list3 = new ArrayList();
			List<TimeSlot> urlList = dc.getTopUrlHour(startTime, endTime, client_id, project_id);
			for (int i = urlList.size()-1; i >=0; i--) {
				map4 = new HashMap();
				map4.put("url",urlList.get(i).getUrl());
				map4.put("bytes", urlList.get(i).getDataNum());
				list3.add(map4);
			}
			
			//查询地图中每个省份的页面访问总量
			List<TimeSlot> geographyPVList = dc.getGeographyPVHour(startTime, endTime, client_id, project_id,"中国");
			List list6 = new ArrayList();
			Map map7 = null;
			for (int i = 0; i <geographyPVList.size(); i++) {
				map7 = new HashMap();
				map7.put("province", geographyPVList.get(i).getProvince());
				map7.put("pvNum", geographyPVList.get(i).getPvNum());
				list6.add(map7);
			}
			//查询地图中每个省份的独立Ip数
			List<TimeSlot> geographyIpList = dc.getGeographyIpHour(startTime, endTime, client_id, project_id,"中国");
			List list4 = new ArrayList();
			Map map5 = null;
			for (int i = 0; i <geographyIpList.size(); i++) {
				map5 = new HashMap();
				map5.put("province", geographyIpList.get(i).getProvince());
				map5.put("pvNum", geographyIpList.get(i).getIpNum());
				list4.add(map5);
			}
			//查询地图中每个省份的字节数
			List<TimeSlot> geographyByteList = dc.getGeographyDataHour(startTime, endTime, client_id, project_id,"中国");
			List list5 = new ArrayList();
			Map map6 = null;
			for (int i = 0; i <geographyByteList.size(); i++) {
				map6 = new HashMap();
				map6.put("province", geographyByteList.get(i).getProvince());
				map6.put("pvNum", geographyByteList.get(i).getDataNum());
				list5.add(map6);
			}
			map1.put("geographyPVCount", list6);
			map1.put("geographyByteList", list5);
			map1.put("geographyIpCount", list4);
			map1.put("urlCount",list3);
			map1.put("ipCount",list2);
			map1.put("timeSlot", list1);
			map1.put("thisAllBytes", thisAllBytes);
	//		map1.put("lastAllBytes", lastAllBytes);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part5 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
		/*	long lastStartTime=0;
			try {
				lastStartTime = DateUtils.getLastDate(startTime, endTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
			}*/
			List list1 = new ArrayList();
			List<TimeSlot> tsList = dc.getMinuteData(startTime, endTime, client_id, project_id);
			Long thisAllBytes = dc.getMinuteAllData(startTime, endTime, client_id, project_id);
	//		Long lastAllBytes = dc.getMinuteAllData(lastStartTime, startTime, client_id, project_id);
			Map map2 = null;
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long bytes=0l;
				for(int j = 0; j <tsList.size(); j++){
					if(t==tsList.get(j).getTime()){
						m=t;
						bytes=tsList.get(j).getDataNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "1"));
					map2.put("bytes", bytes);
				}else{
					map2.put("time", DateUtils.timeToString(t, "1"));
					map2.put("bytes", 0);
				}	
					
				list1.add(map2);
			}
			Map map3 = null;
			List list2 = new ArrayList();
			List<TimeSlot> ipList = dc.getTopIpMinute(startTime, endTime, client_id, project_id);
			for (int i = 0; i < ipList.size(); i++) {
				map3 = new HashMap();
				map3.put("ip",ipList.get(i).getIp());
				map3.put("bytes", ipList.get(i).getIpNum());
				list2.add(map3);
			}
			Map map4 = null;
			List list3 = new ArrayList();
			List<TimeSlot> urlList = dc.getTopUrlMinute(startTime, endTime, client_id, project_id);
			for (int i = urlList.size()-1; i >=0; i--) {
				map4 = new HashMap();
				map4.put("url",urlList.get(i).getUrl());
				map4.put("bytes", urlList.get(i).getDataNum());
				list3.add(map4);
			}
			//查询地图中每个省份的页面访问总量
			List<TimeSlot> geographyPVList = dc.getGeographyPVMinute(startTime, endTime, client_id, project_id,"中国");
			List list6 = new ArrayList();
			Map map7 = null;
			for (int i = 0; i <geographyPVList.size(); i++) {
				map7 = new HashMap();
				map7.put("province", geographyPVList.get(i).getProvince());
				map7.put("pvNum", geographyPVList.get(i).getPvNum());
				list6.add(map7);
			}
			//查询地图中每个省份的独立Ip数
			List<TimeSlot> geographyIpList = dc.getGeographyIpMinute(startTime, endTime, client_id, project_id,"中国");
			List list4 = new ArrayList();
			Map map5 = null;
			for (int i = 0; i <geographyIpList.size(); i++) {
				map5 = new HashMap();
				map5.put("province", geographyIpList.get(i).getProvince());
				map5.put("pvNum", geographyIpList.get(i).getIpNum());
				list4.add(map5);
			}
			//查询地图中每个省份的字节数
			List<TimeSlot> geographyByteList = dc.getGeographyDataMinute(startTime, endTime, client_id, project_id,"中国");
			List list5 = new ArrayList();
			Map map6 = null;
			for (int i = 0; i <geographyByteList.size(); i++) {
				map6 = new HashMap();
				map6.put("province", geographyByteList.get(i).getProvince());
				map6.put("pvNum", geographyByteList.get(i).getDataNum());
				list5.add(map6);
			}
			map1.put("geographyPVCount", list6);
			map1.put("geographyByteList", list5);
			map1.put("geographyIpCount", list4);
			map1.put("urlCount",list3);
			map1.put("ipCount",list2);
			map1.put("timeSlot", list1);
			map1.put("thisAllBytes", thisAllBytes);
	//		map1.put("lastAllBytes", lastAllBytes);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part6 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String url = request.getParameter("url");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = Long.valueOf(st);
			long endTime = Long.valueOf(et);
			/*		startTime = 201602010000l;
			endTime = 201602010100l;*/
			List list1 = new ArrayList();
			List<TimeSlot> urlList = dc.getBytesByUrlYear(startTime, endTime, client_id, project_id, url);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long bytes=0l;
				for(int j = 0; j < urlList.size(); j++){
					if(t==urlList.get(j).getTime()){
						m=t;
						bytes=urlList.get(j).getDataNum();
					}
				}
				if(m!=0){
					map2.put("bytes", bytes);
					map2.put("time", DateUtils.timeToString(m, "5"));
				}else{
					map2.put("bytes", 0);
					map2.put("time", DateUtils.timeToString(t, "5"));
				}	
					
				list1.add(map2);
			}
			map1.put("everyUrlCount",list1);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part7 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String url = request.getParameter("url");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = Long.valueOf(st);
			long endTime = Long.valueOf(et);
			/*		startTime = 201602010000l;
			endTime = 201602010100l;*/
			List list1 = new ArrayList();
			List<TimeSlot> urlList = dc.getBytesByUrlMonth(startTime, endTime, client_id, project_id, url);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long bytes=0l;
				for(int j = 0; j < urlList.size(); j++){
					if(t==urlList.get(j).getTime()){
						m=t;
						bytes=urlList.get(j).getDataNum();
					}
				}
				if(m!=0){
					map2.put("bytes", bytes);
					map2.put("time", DateUtils.timeToString(m, "4"));
				}else{
					map2.put("bytes", 0);
					map2.put("time", DateUtils.timeToString(t, "4"));
				}	
					
				list1.add(map2);
			}
			map1.put("everyUrlCount",list1);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part8 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String url = request.getParameter("url");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = Long.valueOf(st);
			long endTime = Long.valueOf(et);
			/*		startTime = 201602010000l;
			endTime = 201602010100l;*/
			
			List list1 = new ArrayList();
			List<TimeSlot> urlList = dc.getBytesByUrlDaily(startTime, endTime, client_id, project_id, url);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long bytes=0l;
				for(int j = 0; j < urlList.size(); j++){
					if(t==urlList.get(j).getTime()){
						m=t;
						bytes=urlList.get(j).getDataNum();
					}
				}
				if(m!=0){
					map2.put("bytes", bytes);
					map2.put("time", DateUtils.timeToString(m, "3"));
				}else{
					map2.put("bytes", 0);
					map2.put("time", DateUtils.timeToString(t, "3"));
				}	
					
				list1.add(map2);
			}
			map1.put("everyUrlCount",list1);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part9 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String url = request.getParameter("url");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = Long.valueOf(st);
			long endTime = Long.valueOf(et);
			/*		startTime = 201602010000l;
			endTime = 201602010100l;*/
			
			
			List list1 = new ArrayList();
			List<TimeSlot> urlList = dc.getBytesByUrlHour(startTime, endTime, client_id, project_id, url);
		
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long bytes=0l;
				for(int j = 0; j < urlList.size(); j++){
					if(t==urlList.get(j).getTime()){
						m=t;
						bytes=urlList.get(j).getDataNum();
					}
				}
				if(m!=0){
					map2.put("bytes", bytes);
					map2.put("time", DateUtils.timeToString(m, "2"));
				}else{
					map2.put("bytes", 0);
					map2.put("time", DateUtils.timeToString(t, "2"));
				}	
					
				list1.add(map2);
			}
			
			map1.put("everyUrlCount",list1);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part10 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String url = request.getParameter("url");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = Long.valueOf(st);
			long endTime = Long.valueOf(et);
			/*		startTime = 201602010000l;
			endTime = 201602010100l;*/
			
			List list1 = new ArrayList();
			List<TimeSlot> urlList = dc.getBytesByUrlMinute(startTime, endTime, client_id, project_id, url);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long bytes=0l;
				for(int j = 0; j < urlList.size(); j++){
					if(t==urlList.get(j).getTime()){
						m=t;
						bytes=urlList.get(j).getDataNum();
					}
				}
				if(m!=0){
					map2.put("bytes", bytes);
					map2.put("time", DateUtils.timeToString(m, "1"));
				}else{
					map2.put("bytes", 0);
					map2.put("time", DateUtils.timeToString(t, "1"));
				}	
					
				list1.add(map2);
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	
	private final class Part11 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String date = request.getParameter("date");
			String dateType = request.getParameter("dateType");
			String chartType = request.getParameter("chartType");
			Integer project_id = Integer.valueOf(request.getParameter("project_id"));
			if(StringUtils.isEmpty(date)){
				//date = "20160301";
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				date=sdf.format(d);
			}
			Integer client_id = (Integer) session.getAttribute("client_id");
			long lastStartTime=0;
			/*
			 * 计算开始时间和截止时间
			 */

			HashMap dateMap = new HashMap();
			if (dateType.equals("day")) {
				dateMap = DateUtils.getNextTime(dateType, "2", date);
			} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
				dateMap = DateUtils.getNextTime(dateType, "3", date);
			} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
					|| dateType.equals("year")) {
				dateMap = DateUtils.getNextTime(dateType, "4", date);
			} else if (dateType.equals("twoYear")) {
				dateMap = DateUtils.getNextTime(dateType, "5", date);
			}
			long startTime = (long) dateMap.get("firstT");
			long endTime = (long) dateMap.get("nextT");
			
			
			if (dateType.equals("day")) {
				if (chartType.equals("areaplot")) {
					List list1 = new ArrayList();
					List<TimeSlot> tsList = dc.getHourData(startTime, endTime, client_id, project_id);
					Long thisAllBytes = dc.getHourAllData(startTime, endTime, client_id, project_id);
					Long lastAllBytes = dc.getHourAllData(lastStartTime, startTime, client_id, project_id);
					Map map2 = null;
					List timeList = DateUtils.getBetweenDate(startTime, endTime);
					for (int i = 0; i < timeList.size(); i++) {
						map2 = new HashMap();
						long t = (long) timeList.get(i);
						long m= 0;
						long bytes=0l;
						for(int j = 0; j <tsList.size(); j++){
							if(t==tsList.get(j).getTime()){
								m=t;
								bytes=tsList.get(j).getDataNum();
							}
						}
						if(m!=0){
							map2.put("time", DateUtils.timeToString(m, "2"));
							map2.put("bytes", bytes);
						}else{
							map2.put("time", DateUtils.timeToString(t, "2"));
							map2.put("bytes", 0);
						}	
							
						list1.add(map2);
					}
					map1.put("timeSlot", list1);
					map1.put("thisAllBytes", thisAllBytes);
					map1.put("lastAllBytes", lastAllBytes);
				} else if (chartType.equals("mapplot")) {
					//查询地图中每个省份的页面访问总量
					List<TimeSlot> geographyPVList = dc.getGeographyPVHour(startTime, endTime, client_id, project_id,"中国");
					List list6 = new ArrayList();
					Map map7 = null;
					for (int i = 0; i <geographyPVList.size(); i++) {
						map7 = new HashMap();
						map7.put("province", geographyPVList.get(i).getProvince());
						map7.put("pvNum", geographyPVList.get(i).getPvNum());
						list6.add(map7);
					}
					//查询地图中每个省份的独立Ip数
					List<TimeSlot> geographyIpList = dc.getGeographyIpHour(startTime, endTime, client_id, project_id,"中国");
					List list4 = new ArrayList();
					Map map5 = null;
					for (int i = 0; i <geographyIpList.size(); i++) {
						map5 = new HashMap();
						map5.put("province", geographyIpList.get(i).getProvince());
						map5.put("pvNum", geographyIpList.get(i).getIpNum());
						list4.add(map5);
					}
					//查询地图中每个省份的字节数
					List<TimeSlot> geographyByteList = dc.getGeographyDataHour(startTime, endTime, client_id, project_id,"中国");
					List list5 = new ArrayList();
					Map map6 = null;
					for (int i = 0; i <geographyByteList.size(); i++) {
						map6 = new HashMap();
						map6.put("province", geographyByteList.get(i).getProvince());
						map6.put("pvNum", geographyByteList.get(i).getDataNum());
						list5.add(map6);
					}
					map1.put("geographyByteList", list6);
					map1.put("geographyIpCount", list5);
					map1.put("geographyPVCount", list4);
				} else if (chartType.equals("hbarplot")) {
					Map map4 = null;
					List list3 = new ArrayList();
					List<TimeSlot> urlList = dc.getTopUrlHour(startTime, endTime, client_id, project_id);
					for (int i = urlList.size()-1; i >=0; i--) {
						map4 = new HashMap();
						map4.put("url",urlList.get(i).getUrl());
						map4.put("bytes", urlList.get(i).getDataNum());
						list3.add(map4);
					}
					map1.put("startTime", startTime);
					map1.put("endTime", endTime);
					map1.put("urlCount",list3);
				} else if (chartType.equals("treemap")) {
					Map map3 = null;
					List list2 = new ArrayList();
					List<TimeSlot> ipList = dc.getTopIpHour(startTime, endTime, client_id, project_id);
					for (int i = 0; i < ipList.size(); i++) {
						map3 = new HashMap();
						map3.put("ip",ipList.get(i).getIp());
						map3.put("bytes", ipList.get(i).getIpNum());
						list2.add(map3);
					}
					map1.put("ipCount",list2);
				}
			} else if (dateType.equals("week") || dateType.equals("oneMonth")) {

				if (chartType.equals("areaplot")) {
					List list1 = new ArrayList();
					List<TimeSlot> tsList = dc.getDailyData(startTime, endTime, client_id, project_id);
					Long thisAllBytes = dc.getDailyAllData(startTime, endTime, client_id, project_id);
					Long lastAllBytes = dc.getDailyAllData(lastStartTime, startTime, client_id, project_id);
					Map map2 = null;
					List timeList = DateUtils.getBetweenDate(startTime, endTime);
					for (int i = 0; i < timeList.size(); i++) {
						map2 = new HashMap();
						long t = (long) timeList.get(i);
						long m= 0;
						long bytes=0l;
						for(int j = 0; j <tsList.size(); j++){
							if(t==tsList.get(j).getTime()){
								m=t;
								bytes=tsList.get(j).getDataNum();
							}
						}
						if(m!=0){
							map2.put("time", DateUtils.timeToString(m, "3"));
							map2.put("bytes", bytes);
						}else{
							map2.put("time", DateUtils.timeToString(t, "3"));
							map2.put("bytes", 0);
						}	
							
						list1.add(map2);
					}
					map1.put("timeSlot", list1);
					map1.put("thisAllBytes", thisAllBytes);
					map1.put("lastAllBytes", lastAllBytes);
				} else if (chartType.equals("mapplot")) {
					//查询地图中每个省份的页面访问总量
					List<TimeSlot> geographyPVList = dc.getGeographyPVDaily(startTime, endTime, client_id, project_id,"中国");
					List list6 = new ArrayList();
					Map map7 = null;
					for (int i = 0; i <geographyPVList.size(); i++) {
						map7 = new HashMap();
						map7.put("province", geographyPVList.get(i).getProvince());
						map7.put("pvNum", geographyPVList.get(i).getPvNum());
						list6.add(map7);
					}
					//查询地图中每个省份的独立Ip数
					List<TimeSlot> geographyIpList = dc.getGeographyIpDaily(startTime, endTime, client_id, project_id,"中国");
					List list4 = new ArrayList();
					Map map5 = null;
					for (int i = 0; i <geographyIpList.size(); i++) {
						map5 = new HashMap();
						map5.put("province", geographyIpList.get(i).getProvince());
						map5.put("pvNum", geographyIpList.get(i).getIpNum());
						list4.add(map5);
					}
					//查询地图中每个省份的字节数
					List<TimeSlot> geographyByteList = dc.getGeographyDataDaily(startTime, endTime, client_id, project_id,"中国");
					List list5 = new ArrayList();
					Map map6 = null;
					for (int i = 0; i <geographyByteList.size(); i++) {
						map6 = new HashMap();
						map6.put("province", geographyByteList.get(i).getProvince());
						map6.put("pvNum", geographyByteList.get(i).getDataNum());
						list5.add(map6);
					}
					map1.put("geographyByteList", list6);
					map1.put("geographyIpCount", list5);
					map1.put("geographyPVCount", list4);
				} else if (chartType.equals("hbarplot")) {
					Map map4 = null;
					List list3 = new ArrayList();
					List<TimeSlot> urlList = dc.getTopUrlDaily(startTime, endTime, client_id, project_id);
					for (int i = urlList.size()-1; i >=0; i--) {
						map4 = new HashMap();
						map4.put("url",urlList.get(i).getUrl());
						map4.put("bytes", urlList.get(i).getDataNum());
						list3.add(map4);
					}
					map1.put("startTime", startTime);
					map1.put("endTime", endTime);
					map1.put("urlCount",list3);
				} else if (chartType.equals("treemap")) {
					Map map3 = null;
					List list2 = new ArrayList();
					List<TimeSlot> ipList = dc.getTopIpDaily(startTime, endTime, client_id, project_id);
					for (int i = 0; i < ipList.size(); i++) {
						map3 = new HashMap();
						map3.put("ip",ipList.get(i).getIp());
						map3.put("bytes", ipList.get(i).getIpNum());
						list2.add(map3);
					}
					map1.put("ipCount",list2);
				}
			
			} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")|| dateType.equals("year")) {

				if (chartType.equals("areaplot")) {
					List list1 = new ArrayList();
					List<TimeSlot> tsList = dc.getMonthData(startTime, endTime, client_id, project_id);
					Long thisAllBytes = dc.getMonthAllData(startTime, endTime, client_id, project_id);
					Long lastAllBytes = dc.getMonthAllData(lastStartTime, startTime, client_id, project_id);
					Map map2 = null;
					List timeList = DateUtils.getBetweenDate(startTime, endTime);
					for (int i = 0; i < timeList.size(); i++) {
						map2 = new HashMap();
						long t = (long) timeList.get(i);
						long m= 0;
						long bytes=0l;
						for(int j = 0; j <tsList.size(); j++){
							if(t==tsList.get(j).getTime()){
								m=t;
								bytes=tsList.get(j).getDataNum();
							}
						}
						if(m!=0){
							map2.put("time", DateUtils.timeToString(m, "4"));
							map2.put("bytes", bytes);
						}else{
							map2.put("time", DateUtils.timeToString(t, "4"));
							map2.put("bytes", 0);
						}	
							
						list1.add(map2);
					}
					map1.put("timeSlot", list1);
					map1.put("thisAllBytes", thisAllBytes);
					map1.put("lastAllBytes", lastAllBytes);
				} else if (chartType.equals("mapplot")) {
					//查询地图中每个省份的页面访问总量
					List<TimeSlot> geographyPVList = dc.getGeographyPVMonth(startTime, endTime, client_id, project_id,"中国");
					List list6 = new ArrayList();
					Map map7 = null;
					for (int i = 0; i <geographyPVList.size(); i++) {
						map7 = new HashMap();
						map7.put("province", geographyPVList.get(i).getProvince());
						map7.put("pvNum", geographyPVList.get(i).getPvNum());
						list6.add(map7);
					}
					//查询地图中每个省份的独立Ip数
					List<TimeSlot> geographyIpList = dc.getGeographyIpMonth(startTime, endTime, client_id, project_id,"中国");
					List list4 = new ArrayList();
					Map map5 = null;
					for (int i = 0; i <geographyIpList.size(); i++) {
						map5 = new HashMap();
						map5.put("province", geographyIpList.get(i).getProvince());
						map5.put("pvNum", geographyIpList.get(i).getIpNum());
						list4.add(map5);
					}
					//查询地图中每个省份的字节数
					List<TimeSlot> geographyByteList = dc.getGeographyDataMonth(startTime, endTime, client_id, project_id,"中国");
					List list5 = new ArrayList();
					Map map6 = null;
					for (int i = 0; i <geographyByteList.size(); i++) {
						map6 = new HashMap();
						map6.put("province", geographyByteList.get(i).getProvince());
						map6.put("pvNum", geographyByteList.get(i).getDataNum());
						list5.add(map6);
					}
					map1.put("geographyByteList", list6);
					map1.put("geographyIpCount", list5);
					map1.put("geographyPVCount", list4);
				} else if (chartType.equals("hbarplot")) {
					Map map4 = null;
					List list3 = new ArrayList();
					List<TimeSlot> urlList = dc.getTopUrlMonth(startTime, endTime, client_id, project_id);
					for (int i = urlList.size()-1; i >=0; i--) {
						map4 = new HashMap();
						map4.put("url",urlList.get(i).getUrl());
						map4.put("bytes", urlList.get(i).getDataNum());
						list3.add(map4);
					}
					map1.put("startTime", startTime);
					map1.put("endTime", endTime);
					map1.put("urlCount",list3);
				} else if (chartType.equals("treemap")) {
					Map map3 = null;
					List list2 = new ArrayList();
					List<TimeSlot> ipList = dc.getTopIpMonth(startTime, endTime, client_id, project_id);
					for (int i = 0; i < ipList.size(); i++) {
						map3 = new HashMap();
						map3.put("ip",ipList.get(i).getIp());
						map3.put("bytes", ipList.get(i).getIpNum());
						list2.add(map3);
					}
					map1.put("ipCount",list2);
				}
			
			} else if (dateType.equals("twoYear")) {

				if (chartType.equals("areaplot")) {
					List list1 = new ArrayList();
					List<TimeSlot> tsList = dc.getYearData(startTime, endTime, client_id, project_id);
					Long thisAllBytes = dc.getYearAllData(startTime, endTime, client_id, project_id);
					Long lastAllBytes = dc.getYearAllData(lastStartTime, startTime, client_id, project_id);
					Map map2 = null;
					List timeList = DateUtils.getBetweenDate(startTime, endTime);
					for (int i = 0; i < timeList.size(); i++) {
						map2 = new HashMap();
						long t = (long) timeList.get(i);
						long m= 0;
						long bytes=0l;
						for(int j = 0; j <tsList.size(); j++){
							if(t==tsList.get(j).getTime()){
								m=t;
								bytes=tsList.get(j).getDataNum();
							}
						}
						if(m!=0){
							map2.put("time", DateUtils.timeToString(m, "5"));
							map2.put("bytes", bytes);
						}else{
							map2.put("time", DateUtils.timeToString(t, "5"));
							map2.put("bytes", 0);
						}	
							
						list1.add(map2);
					}
					map1.put("timeSlot", list1);
					map1.put("thisAllBytes", thisAllBytes);
					map1.put("lastAllBytes", lastAllBytes);
				} else if (chartType.equals("mapplot")) {
					//查询地图中每个省份的页面访问总量
					List<TimeSlot> geographyPVList = dc.getGeographyPVYear(startTime, endTime, client_id, project_id,"中国");
					List list6 = new ArrayList();
					Map map7 = null;
					for (int i = 0; i <geographyPVList.size(); i++) {
						map7 = new HashMap();
						map7.put("province", geographyPVList.get(i).getProvince());
						map7.put("pvNum", geographyPVList.get(i).getPvNum());
						list6.add(map7);
					}
					//查询地图中每个省份的独立Ip数
					List<TimeSlot> geographyIpList = dc.getGeographyIpYear(startTime, endTime, client_id, project_id,"中国");
					List list4 = new ArrayList();
					Map map5 = null;
					for (int i = 0; i <geographyIpList.size(); i++) {
						map5 = new HashMap();
						map5.put("province", geographyIpList.get(i).getProvince());
						map5.put("pvNum", geographyIpList.get(i).getIpNum());
						list4.add(map5);
					}
					//查询地图中每个省份的字节数
					List<TimeSlot> geographyByteList = dc.getGeographyDataYear(startTime, endTime, client_id, project_id,"中国");
					List list5 = new ArrayList();
					Map map6 = null;
					for (int i = 0; i <geographyByteList.size(); i++) {
						map6 = new HashMap();
						map6.put("province", geographyByteList.get(i).getProvince());
						map6.put("pvNum", geographyByteList.get(i).getDataNum());
						list5.add(map6);
					}
					map1.put("geographyByteList", list6);
					map1.put("geographyIpCount", list5);
					map1.put("geographyPVCount", list4);
				} else if (chartType.equals("hbarplot")) {
					Map map4 = null;
					List list3 = new ArrayList();
					List<TimeSlot> urlList = dc.getTopUrlYear(startTime, endTime, client_id, project_id);
					for (int i = urlList.size()-1; i >=0; i--) {
						map4 = new HashMap();
						map4.put("url",urlList.get(i).getUrl());
						map4.put("bytes", urlList.get(i).getDataNum());
						list3.add(map4);
					}
					map1.put("startTime", startTime);
					map1.put("endTime", endTime);
					map1.put("urlCount",list3);
				} else if (chartType.equals("treemap")) {
					Map map3 = null;
					List list2 = new ArrayList();
					List<TimeSlot> ipList = dc.getTopIpYear(startTime, endTime, client_id, project_id);
					for (int i = 0; i < ipList.size(); i++) {
						map3 = new HashMap();
						map3.put("ip",ipList.get(i).getIp());
						map3.put("bytes", ipList.get(i).getIpNum());
						list2.add(map3);
					}
					map1.put("ipCount",list2);
				}
			}
			
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
}
