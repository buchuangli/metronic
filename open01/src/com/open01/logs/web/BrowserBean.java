package com.open01.logs.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.open01.logs.db.BrowserDatabase;
import com.open01.logs.db.DataCountDatabase;
import com.open01.logs.model.TimeSlot;
import com.open01.logs.util.DateUtils;
import com.open01.logs.util.JsonUtils;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class BrowserBean extends WholePartBean implements BooleanCondition {

	/**
	 * Default constructor.
	 */

	/**
	 * Default constructor.
	 */
	public BrowserBean() {
		super("cmd");
		addPart(null, new Part0());// 默认获取一天的数据，从hour表
		addPart("WEL:SELECTYEAR", new Part1());// 获取年表信息
		addPart("WEL:SELECTMONTH", new Part2());// 获取月表信息
		addPart("WEL:SELECTDAILY", new Part3());// 获取天表信息
		addPart("WEL:SELECTHOUR", new Part4());// 获取小时表信息
		addPart("WEL:SELECTMINUTE", new Part5());// 获取分钟,小时,日,月,年表信息
		
		addPart("WEL:SELECTPROVINCEYEAR", new Part6());// 获取某省份年表信息
		addPart("WEL:SELECTPROVINCEMONTH", new Part7());// 获取某省份月表信息
		addPart("WEL:SELECTPROVINCEDAILY", new Part8());// 获取某省份天表信息
		addPart("WEL:SELECTPROVINCEHOUR", new Part9());// 获取某省份小时表信息
		addPart("WEL:SELECTPROVINCEMINUTE", new Part10());// 获取某省份分钟信息
		
		addPart("WEL:GETCHARINFO", new Part11());
		
		
	}

	@Override
	public boolean isTrue() {
		// TODO Auto-generated method stub
		return true;
	}
	BrowserDatabase bro  = new BrowserDatabase ();
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
			/*long startTime=0;
			long endTime=0;
			startTime = 20160201;
			endTime = 20160202;*/
			long startTime=(long) DateUtils.getInitDate("day").get(0);
			long endTime=(long) DateUtils.getInitDate("day").get(1);
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = bro.getDailyStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("browserType", tsLit.get(i).getBrowserType());
				map2.put("browserNum", tsLit.get(i).getBrowserNum());
				list1.add(map2);
			}
			
			List<TimeSlot> countryList = bro.getDailyCountryStatus(startTime, endTime, client_id, project_id,"中国");
			Map map3 = null;
			List list2 = new ArrayList();
			for (int i = 0; i < countryList.size(); i++) {
				map3 = new HashMap();
				map3.put("browserType", countryList.get(i).getBrowserType());
				map3.put("browserNum", countryList.get(i).getBrowserNum());
				list2.add(map3);
			}
			map1.put("browserType", list2);
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
	/*		startTime = 2016;
			endTime = 2017;*/
			
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"5");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}	
		
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = bro.getYearStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("browserType", tsLit.get(i).getBrowserType());
				map2.put("browserNum", tsLit.get(i).getBrowserNum());
				list1.add(map2);
			}
			List<TimeSlot> countryList = bro.getYearCountryStatus(startTime, endTime, client_id, project_id,"中国");
			Map map3 = null;
			List list2 = new ArrayList();
			for (int i = 0; i < countryList.size(); i++) {
				map3 = new HashMap();
				map3.put("browserType", countryList.get(i).getBrowserType());
				map3.put("browserNum", countryList.get(i).getBrowserNum());
				list2.add(map3);
			}
			map1.put("browserType", list2);
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
			
			/*startTime = 201602;
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
			List<TimeSlot> tsLit = bro.getMonthStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("browserType", tsLit.get(i).getBrowserType());
				map2.put("browserNum", tsLit.get(i).getBrowserNum());
				list1.add(map2);
			}
			List<TimeSlot> countryList = bro.getMonthCountryStatus(startTime, endTime, client_id, project_id,"中国");
			Map map3 = null;
			List list2 = new ArrayList();
			for (int i = 0; i < countryList.size(); i++) {
				map3 = new HashMap();
				map3.put("browserType", countryList.get(i).getBrowserType());
				map3.put("browserNum", countryList.get(i).getBrowserNum());
				list2.add(map3);
			}
			map1.put("browserType", list2);
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
	/*		startTime = 20160200;
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
			List<TimeSlot> tsLit = bro.getDailyStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("browserType", tsLit.get(i).getBrowserType());
				map2.put("browserNum", tsLit.get(i).getBrowserNum());
				list1.add(map2);
			}
			List<TimeSlot> countryList = bro.getDailyCountryStatus(startTime, endTime, client_id, project_id,"中国");
			Map map3 = null;
			List list2 = new ArrayList();
			for (int i = 0; i < countryList.size(); i++) {
				map3 = new HashMap();
				map3.put("browserType", countryList.get(i).getBrowserType());
				map3.put("browserNum", countryList.get(i).getBrowserNum());
				list2.add(map3);
			}
			map1.put("browserType", list2);
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
/*			startTime = 2016020100;
			endTime = 2016020200;*/
			
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}	
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = bro.getHourStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("browserType", tsLit.get(i).getBrowserType());
				map2.put("browserNum", tsLit.get(i).getBrowserNum());
				list1.add(map2);
			}
			List<TimeSlot> countryList = bro.getHourCountryStatus(startTime, endTime, client_id, project_id,"中国");
			Map map3 = null;
			List list2 = new ArrayList();
			for (int i = 0; i < countryList.size(); i++) {
				map3 = new HashMap();
				map3.put("browserType", countryList.get(i).getBrowserType());
				map3.put("browserNum", countryList.get(i).getBrowserNum());
				list2.add(map3);
			}
			map1.put("browserType", list2);
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
/*			startTime = 201602010000l;
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
			List<TimeSlot> tsLit = bro.getMinuteStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("browserType", tsLit.get(i).getBrowserType());
				map2.put("browserNum", tsLit.get(i).getBrowserNum());
				list1.add(map2);
			}
			List<TimeSlot> countryList = bro.getMinuteCountryStatus(startTime, endTime, client_id, project_id,"中国");
			Map map3 = null;
			List list2 = new ArrayList();
			for (int i = 0; i < countryList.size(); i++) {
				map3 = new HashMap();
				map3.put("browserType", countryList.get(i).getBrowserType());
				map3.put("browserNum", countryList.get(i).getBrowserNum());
				list2.add(map3);
			}
			map1.put("browserType", list2);
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
			startTime = 2016;
			endTime = 2017;
			/*if(StringUtils.isNotEmpty(st)){
				startTime = Long.valueOf(st);
				endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"5");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}	*/
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = bro.getYearProvinceStatus(startTime, endTime, client_id, project_id, "中国", "beijing");
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("browserType", tsLit.get(i).getBrowserType());
				map2.put("browserNum", tsLit.get(i).getBrowserNum());
				list1.add(map2);
			}
			map1.put("pCount", list1);
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
			startTime = 201602;
			endTime = 201603;
			/*if(StringUtils.isNotEmpty(st)){
				startTime = Long.valueOf(st);
				endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"4");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}*/	
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = bro.getMonthProvinceStatus(startTime, endTime, client_id, project_id, "中国", "beijing");
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("browserType", tsLit.get(i).getBrowserType());
				map2.put("browserNum", tsLit.get(i).getBrowserNum());
				list1.add(map2);
			}
			map1.put("pCount", list1);
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
			startTime = 20160201;
			endTime = 20160202l;
			/*if(StringUtils.isNotEmpty(st)){
				startTime = Long.valueOf(st);
				endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}	*/
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = bro.getDailyProvinceStatus(startTime, endTime, client_id, project_id, "中国", "beijing");
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("browserType", tsLit.get(i).getBrowserType());
				map2.put("browserNum", tsLit.get(i).getBrowserNum());
				list1.add(map2);
			}
			map1.put("pCount", list1);
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
			startTime = 2016020100;
			endTime = 2016020102;
		/*	if(StringUtils.isNotEmpty(st)){
				startTime = Long.valueOf(st);
				endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}*/	
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = bro.getHourProvinceStatus(startTime, endTime, client_id, project_id, "中国", "beijing");
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("browserType", tsLit.get(i).getBrowserType());
				map2.put("browserNum", tsLit.get(i).getBrowserNum());
				list1.add(map2);
			}
			map1.put("pCount", list1);
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
			startTime = 201602010000l;
			endTime = 201602010100l;
			/*if(StringUtils.isNotEmpty(st)){
				startTime = Long.valueOf(st);
				endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}*/	
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = bro.getMinuteProvinceStatus(startTime, endTime, client_id, project_id, "中国", "beijing");
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("browserType", tsLit.get(i).getBrowserType());
				map2.put("browserNum", tsLit.get(i).getBrowserNum());
				list1.add(map2);
			}
			map1.put("pCount", list1);
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
				if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List list1 = new ArrayList();
					List<TimeSlot> tsLit = bro.getHourStatus(startTime, endTime, client_id, project_id);
					Map map2 = null;
					for (int i = 0; i < tsLit.size(); i++) {
						map2 = new HashMap();
						map2.put("browserType", tsLit.get(i).getBrowserType());
						map2.put("browserNum", tsLit.get(i).getBrowserNum());
						list1.add(map2);
					}
					map1.put("timeSlot", list1);
				}
			} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
				if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List list1 = new ArrayList();
					List<TimeSlot> tsLit = bro.getDailyStatus(startTime, endTime, client_id, project_id);
					Map map2 = null;
					for (int i = 0; i < tsLit.size(); i++) {
						map2 = new HashMap();
						map2.put("browserType", tsLit.get(i).getBrowserType());
						map2.put("browserNum", tsLit.get(i).getBrowserNum());
						list1.add(map2);
					}
					map1.put("timeSlot", list1);
				}
			} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
					|| dateType.equals("year")) {
				List list1 = new ArrayList();
				List<TimeSlot> tsLit = bro.getMonthStatus(startTime, endTime, client_id, project_id);
				Map map2 = null;
				for (int i = 0; i < tsLit.size(); i++) {
					map2 = new HashMap();
					map2.put("browserType", tsLit.get(i).getBrowserType());
					map2.put("browserNum", tsLit.get(i).getBrowserNum());
					list1.add(map2);
				}
				map1.put("timeSlot", list1);
			} else if (dateType.equals("twoYear")) {
				if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List list1 = new ArrayList();
					List<TimeSlot> tsLit = bro.getYearStatus(startTime, endTime, client_id, project_id);
					Map map2 = null;
					for (int i = 0; i < tsLit.size(); i++) {
						map2 = new HashMap();
						map2.put("browserType", tsLit.get(i).getBrowserType());
						map2.put("browserNum", tsLit.get(i).getBrowserNum());
						list1.add(map2);
					}
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