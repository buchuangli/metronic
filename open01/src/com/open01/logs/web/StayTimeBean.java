package com.open01.logs.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.open01.logs.db.StayTimeDatabase;
import com.open01.logs.model.TimeSlot;
import com.open01.logs.util.DateUtils;
import com.open01.logs.util.JsonUtils;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class StayTimeBean extends WholePartBean implements BooleanCondition {

	/**
	 * Default constructor.
	 */

	/**
	 * Default constructor.
	 */
	public StayTimeBean() {
		super("cmd");
		addPart(null, new Part0());// 默认获取一天的数据，从hour表
		addPart("WEL:SELECTYEAR", new Part1());// 获取年表信息
		addPart("WEL:SELECTMONTH", new Part2());// 获取月表信息
		addPart("WEL:SELECTDAILY", new Part3());// 获取天表信息
		addPart("WEL:SELECTHOUR", new Part4());// 获取小时表信息
		addPart("WEL:SELECTMINUTE", new Part5());// 获取分钟,小时,日,月,年表信息
		
		addPart("WEL:GETCHARINFO", new Part6());
	}
	@Override
	public boolean isTrue() {
		// TODO Auto-generated method stub
		return true;
	}
	StayTimeDatabase std  = new StayTimeDatabase ();
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
		/*	long startTime=0;
			long endTime=0;
			startTime = 20160201;
			endTime = 20160202;*/
			long startTime=(long) DateUtils.getInitDate("day").get(0);
			long endTime=(long) DateUtils.getInitDate("day").get(1);
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = std.getDailyStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = tsLit.size()-1; i >=0; i--) {
				map2 = new HashMap();
				map2.put("stayTimeType", tsLit.get(i).getStayTime());
				map2.put("url", tsLit.get(i).getUrl());
			//	map2.put("stNum", tsLit.get(i).getStNum());
				list1.add(map2);
			}
			long allNum = std.getAllNumDaily(startTime, endTime, client_id, project_id);
			map1.put("allNum", allNum);
			map1.put("timeSlot", list1);
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
			TreeMap<Long, List<TimeSlot>> dailyCount = null;
			List<TimeSlot> urlList = null;
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
		/*	startTime = 2016;
			endTime = 2017;
			*/
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"5");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}	
		
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = std.getYearStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = tsLit.size()-1; i >=0; i--) {
				map2 = new HashMap();
				map2.put("stayTimeType", tsLit.get(i).getStayTime());
				map2.put("url", tsLit.get(i).getUrl());
			//	map2.put("stNum", tsLit.get(i).getStNum());
				list1.add(map2);
			}
			long allNum = std.getAllNumYear(startTime, endTime, client_id, project_id);
			map1.put("allNum", allNum);
			map1.put("timeSlot", list1);
			
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
			List<TimeSlot> urlList = null;
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
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = std.getMonthStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = tsLit.size()-1; i >=0; i--) {
				map2 = new HashMap();
				map2.put("stayTimeType", tsLit.get(i).getStayTime());
				map2.put("url", tsLit.get(i).getUrl());
			//	map2.put("stNum", tsLit.get(i).getStNum());
				list1.add(map2);
			}
			long allNum = std.getAllNumMonth(startTime, endTime, client_id, project_id);
			map1.put("allNum", allNum);
			map1.put("timeSlot", list1);
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
			List<TimeSlot> urlList = null;
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
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = std.getDailyStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = tsLit.size()-1; i >=0; i--) {
				map2 = new HashMap();
				map2.put("stayTimeType", tsLit.get(i).getStayTime());
				map2.put("url", tsLit.get(i).getUrl());
				//map2.put("stNum", tsLit.get(i).getStNum());
				list1.add(map2);
			}
			long allNum = std.getAllNumDaily(startTime, endTime, client_id, project_id);
			map1.put("allNum", allNum);
			map1.put("timeSlot", list1);
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
			/*startTime = 2016030406;
			endTime = 2016030409;*/
			
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}	
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = std.getHourStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = tsLit.size()-1; i >=0; i--) {
				map2 = new HashMap();
				map2.put("stayTimeType", tsLit.get(i).getStayTime());
				map2.put("url", tsLit.get(i).getUrl());
				//map2.put("stNum", tsLit.get(i).getStNum());
				list1.add(map2);
			}
			long allNum = std.getAllNumHour(startTime, endTime, client_id, project_id);
			map1.put("allNum", allNum);
			map1.put("timeSlot", list1);
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
	/*		startTime = 201602010000l;
			endTime = 201602010100l;*/
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}	
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = std.getMinuteStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = tsLit.size()-1; i >=0; i--) {
				map2 = new HashMap();
				map2.put("stayTimeType", tsLit.get(i).getStayTime());
				map2.put("url", tsLit.get(i).getUrl());
			//	map2.put("stNum", tsLit.get(i).getStNum());
				list1.add(map2);
			}
			long allNum = std.getAllNumMinute(startTime, endTime, client_id, project_id);
			map1.put("allNum", allNum);
			map1.put("timeSlot", list1);
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
			String date = request.getParameter("date");
			String dateType = request.getParameter("dateType");
			String chartType = request.getParameter("chartType");
			Integer project_id = Integer.valueOf(request.getParameter("project_id"));
			if(StringUtils.isEmpty(date)){
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
				if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List list1 = new ArrayList();
					List<TimeSlot> tsLit = std.getHourStatus(startTime, endTime, client_id, project_id);
					Map map2 = null;
					for (int i = tsLit.size()-1; i >=0; i--) {
						map2 = new HashMap();
						map2.put("stayTimeType", tsLit.get(i).getStayTime());
						map2.put("url", tsLit.get(i).getUrl());
						list1.add(map2);
					}
					long allNum = std.getAllNumHour(startTime, endTime, client_id, project_id);
					map1.put("allNum", allNum);
					map1.put("timeSlot", list1);
				}
			} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
				if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List list1 = new ArrayList();
					List<TimeSlot> tsLit = std.getDailyStatus(startTime, endTime, client_id, project_id);
					Map map2 = null;
					for (int i = tsLit.size()-1; i >=0; i--) {
						map2 = new HashMap();
						map2.put("stayTimeType", tsLit.get(i).getStayTime());
						map2.put("url", tsLit.get(i).getUrl());
						list1.add(map2);
					}
					long allNum = std.getAllNumDaily(startTime, endTime, client_id, project_id);
					map1.put("allNum", allNum);
					map1.put("timeSlot", list1);
				}
			} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")|| dateType.equals("year")) {
				if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List list1 = new ArrayList();
					List<TimeSlot> tsLit = std.getMonthStatus(startTime, endTime, client_id, project_id);
					Map map2 = null;
					for (int i = tsLit.size()-1; i >=0; i--) {
						map2 = new HashMap();
						map2.put("stayTimeType", tsLit.get(i).getStayTime());
						map2.put("url", tsLit.get(i).getUrl());
						list1.add(map2);
					}
					long allNum = std.getAllNumMonth(startTime, endTime, client_id, project_id);
					map1.put("allNum", allNum);
					map1.put("timeSlot", list1);
				}
			} else if (dateType.equals("twoYear")) {
				if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List list1 = new ArrayList();
					List<TimeSlot> tsLit = std.getYearStatus(startTime, endTime, client_id, project_id);
					Map map2 = null;
					for (int i = tsLit.size()-1; i >=0; i--) {
						map2 = new HashMap();
						map2.put("stayTimeType", tsLit.get(i).getStayTime());
						map2.put("url", tsLit.get(i).getUrl());
						list1.add(map2);
					}
					long allNum = std.getAllNumYear(startTime, endTime, client_id, project_id);
					map1.put("allNum", allNum);
					map1.put("timeSlot", list1);
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