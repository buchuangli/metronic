package com.open01.logs.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.open01.logs.db.AnalysisDatabase;
import com.open01.logs.db.IpCountDatabase;
import com.open01.logs.model.TimeSlot;
import com.open01.logs.util.DateUtils;
import com.open01.logs.util.JsonUtils;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class IpCountBean extends WholePartBean implements BooleanCondition {

	/**
	 * Default constructor.
	 */

	/**
	 * Default constructor.
	 */
	public IpCountBean() {
		super("cmd");
		addPart(null, new Part0());// 默认获取一天的数据，从hour表
		addPart("WEL:SELECTYEAR", new Part1());// 获取年表信息
		addPart("WEL:SELECTMONTH", new Part2());// 获取月表信息
		addPart("WEL:SELECTDAILY", new Part3());// 获取天表信息
		addPart("WEL:SELECTHOUR", new Part4());// 获取小时表信息
		addPart("WEL:SELECTMINUTE", new Part5());// 获取分钟,小时,日,月,年表信息
		
		addPart("WEL:GETPAGEVIEWBYIPMINUTE", new Part6());// 查找每个ip在各个时间点的页面访问次数 getPageviewByIplHour
		addPart("WEL:GETPAGEVIEWBYIPHOUR", new Part7());//
		addPart("WEL:GETPAGEVIEWBYIPDAILY", new Part8());//
		addPart("WEL:GETPAGEVIEWBYIPMONTH", new Part9());//
		addPart("WEL:GETPAGEVIEWBYIPYEAR", new Part10());//
		
		addPart("WEL:GETIPAGEVIEWBYGEYEAR", new Part11());//getIpPageViewByGeYear
		addPart("WEL:GETIPAGEVIEWBYGEMONTH", new Part12());//getIpPageViewByGeMonth
		addPart("WEL:GETIPAGEVIEWBYGEDAILY", new Part13());//getIpPageViewByGeDaily
		addPart("WEL:GETIPAGEVIEWBYGEHOUR", new Part14());//getIpPageViewByGeHour
		addPart("WEL:GETIPAGEVIEWBYGEMINUTE", new Part15());//getIpPageViewByGeMinute
		
		addPart("WEL:GETCHARINFO", new Part16());//

	}

	@Override
	public boolean isTrue() {
		// TODO Auto-generated method stub
		return true;
	}

	int client_id=0;
	int project_id = 0;
	private final class Part0 extends DefaultPlainBean {
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			List<TimeSlot> urlList = null;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime=(long) DateUtils.getInitDate("hour").get(0);
			long endTime=(long) DateUtils.getInitDate("hour").get(1);
			long lastStartTime=0;
			try {
				lastStartTime = DateUtils.getLastDate(startTime, endTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//小时内页面访问总量
			TreeMap<Long, List<TimeSlot>> numCount = AnalysisDatabase.instance.getHourPageviewCount(startTime, endTime, client_id, project_id);
		//	long thisPvNum =  AnalysisDatabase.instance.getLastPvNumHour(startTime, endTime, client_id, project_id);
		//	long lastPvNum =  AnalysisDatabase.instance.getLastPvNumHour(lastStartTime, startTime, client_id, project_id);
			
			long newIpCount  = AnalysisDatabase.instance.getHourNewIp(client_id, project_id,startTime,endTime);
			long lastNewIpCount  = AnalysisDatabase.instance.getHourNewIp(client_id, project_id,lastStartTime, startTime);
			long allIpCount = AnalysisDatabase.instance.getAllHourIpCount(client_id, project_id, startTime,endTime);
			long lastAllIpCount = AnalysisDatabase.instance.getAllHourIpCount(client_id, project_id, lastStartTime, startTime);
			
			urlList = AnalysisDatabase.instance.getIpHourPageviewCount(startTime, endTime, client_id, project_id);
			List list1 = new ArrayList();
			List<TimeSlot> pvList = numCount.get(1l);
			
			Map map2 = null;
			int n = pvList.size();
			/*
			 * 显示小时数据
			 */
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long pvNum=0l;
				long ipNum=0l;
				for(int j = 0; j < n/2; j++){
					if(t==pvList.get(j).getTime()){
						m=t;
						pvNum=pvList.get(j).getPvNum();
						ipNum=pvList.get(j + n / 2).getIpNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "2"));
					map2.put("pvNum", pvNum);
					map2.put("ipNum", ipNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, "2"));
					map2.put("pvNum", 0);
					map2.put("ipNum", 0);
				}	
					
				list1.add(map2);
			}
			
			List list2 = new ArrayList();
			Map map3 = null;
			for (int i = urlList.size()-1; i >=0; i--) {
				map3 = new HashMap();
				map3.put("ip", urlList.get(i).getIp());
				map3.put("ipNum", urlList.get(i).getIpNum());
				//map3.put("time", DateUtils.timeToString(urlList.get(i).getTime(), "2"));
				list2.add(map3);
			}
			//查询地图中每个省份的页面访问总量
			List<TimeSlot> geographyPVList = AnalysisDatabase.instance.getGeographyPVHour(startTime, endTime, client_id, project_id,"中国");
			List list3 = new ArrayList();
			Map map4 = null;
			for (int i = 0; i <geographyPVList.size(); i++) {
				map4 = new HashMap();
				map4.put("province", geographyPVList.get(i).getProvince());
				map4.put("pvNum", geographyPVList.get(i).getPvNum());
				list3.add(map4);
			}
			//查询地图中每个省份的独立Ip数
			List<TimeSlot> geographyIpList = AnalysisDatabase.instance.getGeographyIpHour(startTime, endTime, client_id, project_id,"中国");
			List list4 = new ArrayList();
			Map map5 = null;
			for (int i = 0; i <geographyIpList.size(); i++) {
				map5 = new HashMap();
				map5.put("province", geographyIpList.get(i).getProvince());
				map5.put("pvNum", geographyIpList.get(i).getIpNum());
				list4.add(map5);
			}
			//查询地图中每个省份的字节数
			List<TimeSlot> geographyByteList = AnalysisDatabase.instance.getGeographyDataHour(startTime, endTime, client_id, project_id,"中国");
			List list5 = new ArrayList();
			Map map6 = null;
			for (int i = 0; i <geographyByteList.size(); i++) {
				map6 = new HashMap();
				map6.put("province", geographyByteList.get(i).getProvince());
				map6.put("pvNum", geographyByteList.get(i).getDataNum());
				list5.add(map6);
			}
			
			map1.put("geographyByteList", list5);
			map1.put("geographyIpCount", list4);
			map1.put("geographyPVCount", list3);
			map1.put("timeSlot", list1);
			map1.put("ipCount", list2);
			map1.put("newIpCount", newIpCount);
			map1.put("lastNewIpCount", lastNewIpCount);
			map1.put("allIpCount", allIpCount);
			map1.put("lastAllIpCount", lastAllIpCount);
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

	// http://localhost:8080/open01/analysis.open?cmd=WEL:GETDATE&date=month
	private final class Part1 extends DefaultPlainBean {

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
			long lastStartTime=0;
			if(StringUtils.isNotEmpty(st)){
				startTime = Long.valueOf(st);
				endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"5");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			try {
				lastStartTime = DateUtils.getLastDate(startTime, endTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long thisPvNum =  AnalysisDatabase.instance.getLastPvNumYear(startTime, endTime, client_id, project_id);
			long lastPvNum =  AnalysisDatabase.instance.getLastPvNumYear(lastStartTime, startTime, client_id, project_id);
			TreeMap<Long, List<TimeSlot>> numCount = AnalysisDatabase.instance.getYearPageviewCount(startTime, endTime, client_id, project_id);
			long newIpCount  = AnalysisDatabase.instance.getYearNewIp(client_id, project_id, startTime,endTime);
			long lastNewIpCount  = AnalysisDatabase.instance.getYearNewIp(client_id, project_id,lastStartTime, startTime);
			long allIpCount = AnalysisDatabase.instance.getAllYearIpCount(client_id, project_id, startTime,endTime);
			long lastAllIpCount = AnalysisDatabase.instance.getAllYearIpCount(client_id, project_id, lastStartTime, startTime);
			urlList = AnalysisDatabase.instance.getIpYearPageviewCount(startTime, endTime, client_id, project_id);
			List list1 = new ArrayList();
			List<TimeSlot> pvList = numCount.get(1l);
			
			Map map2 = null;
			int n = pvList.size();
			/*
			 * 显示年数据
			 */
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long pvNum=0l;
				long ipNum=0l;
				for(int j = 0; j < n/2; j++){
					if(t==pvList.get(j).getTime()){
						m=t;
						pvNum=pvList.get(j).getPvNum();
						ipNum=pvList.get(j + n / 2).getIpNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "5"));
					map2.put("pvNum", pvNum);
					map2.put("ipNum", ipNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, "5"));
					map2.put("pvNum", 0);
					map2.put("ipNum", 0);
				}	
					
				list1.add(map2);
			}
			
			List list2 = new ArrayList();
			Map map3 = null;
			for (int i = urlList.size()-1; i >=0; i--) {
				map3 = new HashMap();
				map3.put("ip", urlList.get(i).getIp());
				map3.put("ipNum", urlList.get(i).getIpNum());
			//	map3.put("time", DateUtils.timeToString(urlList.get(i).getTime(), "4"));
				list2.add(map3);
			}

			//查询地图中每个省份的页面访问总量
			List<TimeSlot> geographyPVList = AnalysisDatabase.instance.getGeographyPVYear(startTime, endTime, client_id, project_id,"中国");
			List list3 = new ArrayList();
			Map map4 = null;
			for (int i = 0; i <geographyPVList.size(); i++) {
				map4 = new HashMap();
				map4.put("province", geographyPVList.get(i).getProvince());
				map4.put("pvNum", geographyPVList.get(i).getPvNum());
				list3.add(map4);
			}
			//查询地图中每个省份的独立Ip数
			List<TimeSlot> geographyIpList = AnalysisDatabase.instance.getGeographyIpYear(startTime, endTime, client_id, project_id,"中国");
			List list4 = new ArrayList();
			Map map5 = null;
			for (int i = 0; i <geographyIpList.size(); i++) {
				map5 = new HashMap();
				map5.put("province", geographyIpList.get(i).getProvince());
				map5.put("pvNum", geographyIpList.get(i).getIpNum());
				list4.add(map5);
			}
			//查询地图中每个省份的字节数
			List<TimeSlot> geographyByteList = AnalysisDatabase.instance.getGeographyDataYear(startTime, endTime, client_id, project_id,"中国");
			List list5 = new ArrayList();
			Map map6 = null;
			for (int i = 0; i <geographyByteList.size(); i++) {
				map6 = new HashMap();
				map6.put("province", geographyByteList.get(i).getProvince());
				map6.put("pvNum", geographyByteList.get(i).getDataNum());
				list5.add(map6);
			}
			
			map1.put("geographyByteList", list5);
			map1.put("geographyIpCount", list4);
			map1.put("geographyPVCount", list3);
			map1.put("timeSlot", list1);
			map1.put("ipCount", list2);
			map1.put("newIpCount", newIpCount);
			map1.put("lastNewIpCount", lastNewIpCount);
			map1.put("allIpCount", allIpCount);
			map1.put("lastAllIpCount", lastAllIpCount);
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
			long lastStartTime=0;
			if(StringUtils.isNotEmpty(st)){
				startTime = Long.valueOf(st);
				endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"4");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			try {
				lastStartTime = DateUtils.getLastDate(startTime, endTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long thisPvNum =  AnalysisDatabase.instance.getLastPvNumMonth(startTime, endTime, client_id, project_id);
			long lastPvNum =  AnalysisDatabase.instance.getLastPvNumMonth(lastStartTime, startTime, client_id, project_id);
			TreeMap<Long, List<TimeSlot>> numCount = AnalysisDatabase.instance.getMonthPageviewCount(startTime, endTime, client_id, project_id);
			long newIpCount  = AnalysisDatabase.instance.getMonthNewIp(client_id, project_id, startTime,endTime);
			long lastNewIpCount  = AnalysisDatabase.instance.getMonthNewIp(client_id, project_id,lastStartTime, startTime);
			long allIpCount = AnalysisDatabase.instance.getAllMonthIpCount(client_id, project_id, startTime,endTime);
			long lastAllIpCount = AnalysisDatabase.instance.getAllMonthIpCount(client_id, project_id, lastStartTime, startTime);
			
			urlList = AnalysisDatabase.instance.getIpMonthPageviewCount(startTime, endTime, client_id, project_id);
			List list1 = new ArrayList();
			List<TimeSlot> pvList = numCount.get(1l);
			
			Map map2 = null;
			int n = pvList.size();
			/*
			 * 显示月数据
			 */
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long pvNum=0l;
				long ipNum=0l;
				for(int j = 0; j < n/2; j++){
					if(t==pvList.get(j).getTime()){
						m=t;
						pvNum=pvList.get(j).getPvNum();
						ipNum=pvList.get(j + n / 2).getIpNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "4"));
					map2.put("pvNum", pvNum);
					map2.put("ipNum", ipNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, "4"));
					map2.put("pvNum", 0);
					map2.put("ipNum", 0);
				}	
					
				list1.add(map2);
			}
			
			List list2 = new ArrayList();
			Map map3 = null;
			for (int i = urlList.size()-1; i >=0; i--) {
				map3 = new HashMap();
				map3.put("ip", urlList.get(i).getIp());
				map3.put("ipNum", urlList.get(i).getIpNum());
			//	map3.put("time", DateUtils.timeToString(urlList.get(i).getTime(), "4"));
				list2.add(map3);
			}

			//查询地图中每个省份的页面访问总量
			List<TimeSlot> geographyPVList = AnalysisDatabase.instance.getGeographyPVMonth(startTime, endTime, client_id, project_id,"中国");
			List list3 = new ArrayList();
			Map map4 = null;
			for (int i = 0; i <geographyPVList.size(); i++) {
				map4 = new HashMap();
				map4.put("province", geographyPVList.get(i).getProvince());
				map4.put("pvNum", geographyPVList.get(i).getPvNum());
				list3.add(map4);
			}
			//查询地图中每个省份的独立Ip数
			List<TimeSlot> geographyIpList = AnalysisDatabase.instance.getGeographyIpMonth(startTime, endTime, client_id, project_id,"中国");
			List list4 = new ArrayList();
			Map map5 = null;
			for (int i = 0; i <geographyIpList.size(); i++) {
				map5 = new HashMap();
				map5.put("province", geographyIpList.get(i).getProvince());
				map5.put("pvNum", geographyIpList.get(i).getIpNum());
				list4.add(map5);
			}
			//查询地图中每个省份的字节数
			List<TimeSlot> geographyByteList = AnalysisDatabase.instance.getGeographyDataMonth(startTime, endTime, client_id, project_id,"中国");
			List list5 = new ArrayList();
			Map map6 = null;
			for (int i = 0; i <geographyByteList.size(); i++) {
				map6 = new HashMap();
				map6.put("province", geographyByteList.get(i).getProvince());
				map6.put("pvNum", geographyByteList.get(i).getDataNum());
				list5.add(map6);
			}
			
			map1.put("geographyByteList", list5);
			map1.put("geographyIpCount", list4);
			map1.put("geographyPVCount", list3);
			map1.put("timeSlot", list1);
			map1.put("ipCount", list2);
			map1.put("newIpCount", newIpCount);
			map1.put("lastNewIpCount", lastNewIpCount);
			map1.put("allIpCount", allIpCount);
			map1.put("lastAllIpCount", lastAllIpCount);
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
			long lastStartTime=0;
			if(StringUtils.isNotEmpty(st)){
				startTime = Long.valueOf(st);
				endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			try {
				lastStartTime = DateUtils.getLastDate(startTime, endTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long thisPvNum =  AnalysisDatabase.instance.getLastPvNumDaily(startTime, endTime, client_id, project_id);
			long lastPvNum =  AnalysisDatabase.instance.getLastPvNumDaily(lastStartTime, startTime, client_id, project_id);
			TreeMap<Long, List<TimeSlot>> numCount = AnalysisDatabase.instance.getDailyPageviewCount(startTime, endTime, client_id, project_id);
			long newIpCount  = AnalysisDatabase.instance.getDailyNewIp(client_id, project_id, startTime,endTime);
			long lastNewIpCount  = AnalysisDatabase.instance.getDailyNewIp(client_id, project_id,lastStartTime, startTime);
			long allIpCount = AnalysisDatabase.instance.getAllDailyIpCount(client_id, project_id, startTime,endTime);
			long lastAllIpCount = AnalysisDatabase.instance.getAllDailyIpCount(client_id, project_id, lastStartTime, startTime);
			
			urlList = AnalysisDatabase.instance.getIpDailyPageviewCount(startTime, endTime, client_id, project_id);
			List list1 = new ArrayList();
			List<TimeSlot> pvList = numCount.get(1l);
			
			Map map2 = null;
			int n = pvList.size();
			/*
			 * 显示日数据
			 */
			List timeList = DateUtils.getBetweenDate(startTime, endTime,"daily");
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long pvNum=0l;
				long ipNum=0l;
				for(int j = 0; j < n/2; j++){
					if(t==pvList.get(j).getTime()){
						m=t;
						pvNum=pvList.get(j).getPvNum();
						ipNum=pvList.get(j + n / 2).getIpNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "3"));
					map2.put("pvNum", pvNum);
					map2.put("ipNum", ipNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, "3"));
					map2.put("pvNum", 0);
					map2.put("ipNum", 0);
				}	
					
				list1.add(map2);
			}
			List list2 = new ArrayList();
			Map map3 = null;
			for (int i = urlList.size()-1; i >=0; i--) {
				map3 = new HashMap();
				map3.put("ip", urlList.get(i).getIp());
				map3.put("ipNum", urlList.get(i).getIpNum());
				//map3.put("time", DateUtils.timeToString(urlList.get(i).getTime(), "3"));
				list2.add(map3);
			}
			//查询地图中每个省份的页面访问总量
			List<TimeSlot> geographyPVList = AnalysisDatabase.instance.getGeographyPVDaily(startTime, endTime, client_id, project_id,"中国");
			List list3 = new ArrayList();
			Map map4 = null;
			for (int i = 0; i <geographyPVList.size(); i++) {
				map4 = new HashMap();
				map4.put("province", geographyPVList.get(i).getProvince());
				map4.put("pvNum", geographyPVList.get(i).getPvNum());
				list3.add(map4);
			}
			//查询地图中每个省份的独立Ip数
			List<TimeSlot> geographyIpList = AnalysisDatabase.instance.getGeographyIpDaily(startTime, endTime, client_id, project_id,"中国");
			List list4 = new ArrayList();
			Map map5 = null;
			for (int i = 0; i <geographyIpList.size(); i++) {
				map5 = new HashMap();
				map5.put("province", geographyIpList.get(i).getProvince());
				map5.put("pvNum", geographyIpList.get(i).getIpNum());
				list4.add(map5);
			}
			//查询地图中每个省份的字节数
			List<TimeSlot> geographyByteList = AnalysisDatabase.instance.getGeographyDataDaily(startTime, endTime, client_id, project_id,"中国");
			List list5 = new ArrayList();
			Map map6 = null;
			for (int i = 0; i <geographyByteList.size(); i++) {
				map6 = new HashMap();
				map6.put("province", geographyByteList.get(i).getProvince());
				map6.put("pvNum", geographyByteList.get(i).getDataNum());
				list5.add(map6);
			}
			//lastAllIpCount
			map1.put("geographyByteList", list5);
			map1.put("geographyIpCount", list4);
			map1.put("geographyPVCount", list3);
			map1.put("timeSlot", list1);
			map1.put("ipCount", list2);
			map1.put("newIpCount", newIpCount);
			map1.put("lastNewIpCount", lastNewIpCount);
			map1.put("allIpCount", allIpCount);
			map1.put("lastAllIpCount", lastAllIpCount);
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
			long lastStartTime=0;
			if(StringUtils.isNotEmpty(st)){
				startTime = Long.valueOf(st);
				endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			try {
				lastStartTime = DateUtils.getLastDate(startTime, endTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long thisPvNum =  AnalysisDatabase.instance.getLastPvNumHour(startTime, endTime, client_id, project_id);
			long lastPvNum =  AnalysisDatabase.instance.getLastPvNumHour(lastStartTime, startTime, client_id, project_id);
			TreeMap<Long, List<TimeSlot>> numCount = AnalysisDatabase.instance.getHourPageviewCount(startTime, endTime, client_id, project_id);
			
			long newIpCount  = AnalysisDatabase.instance.getHourNewIp(client_id, project_id, startTime,endTime);
			long lastNewIpCount  = AnalysisDatabase.instance.getHourNewIp(client_id, project_id,lastStartTime, startTime);
			long allIpCount = AnalysisDatabase.instance.getAllHourIpCount(client_id, project_id, startTime,endTime);
			long lastAllIpCount = AnalysisDatabase.instance.getAllHourIpCount(client_id, project_id, lastStartTime, startTime);
			
			urlList = AnalysisDatabase.instance.getIpHourPageviewCount(startTime, endTime, client_id, project_id);
			List list1 = new ArrayList();
			List<TimeSlot> pvList = numCount.get(1l);
			
			Map map2 = null;
			int n = pvList.size();
			/*
			 * 显示小时数据
			 */
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long pvNum=0l;
				long ipNum=0l;
				for(int j = 0; j < n/2; j++){
					if(t==pvList.get(j).getTime()){
						m=t;
						pvNum=pvList.get(j).getPvNum();
						ipNum=pvList.get(j + n / 2).getIpNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "2"));
					map2.put("pvNum", pvNum);
					map2.put("ipNum", ipNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, "2"));
					map2.put("pvNum", 0);
					map2.put("ipNum", 0);
				}	
					
				list1.add(map2);
			}
			List list2 = new ArrayList();
			Map map3 = null;
			for (int i = urlList.size()-1; i >=0; i--) {
				map3 = new HashMap();
				map3.put("ip", urlList.get(i).getIp());
				map3.put("ipNum", urlList.get(i).getIpNum());
			//	map3.put("time", DateUtils.timeToString(urlList.get(i).getTime(), "2"));
				list2.add(map3);
			}
			//查询地图中每个省份的页面访问总量
			List<TimeSlot> geographyPVList = AnalysisDatabase.instance.getGeographyPVHour(startTime, endTime, client_id, project_id,"中国");
			List list3 = new ArrayList();
			Map map4 = null;
			for (int i = 0; i <geographyPVList.size(); i++) {
				map4 = new HashMap();
				map4.put("province", geographyPVList.get(i).getProvince());
				map4.put("pvNum", geographyPVList.get(i).getPvNum());
				list3.add(map4);
			}
			//查询地图中每个省份的独立Ip数
			List<TimeSlot> geographyIpList = AnalysisDatabase.instance.getGeographyIpHour(startTime, endTime, client_id, project_id,"中国");
			List list4 = new ArrayList();
			Map map5 = null;
			for (int i = 0; i <geographyIpList.size(); i++) {
				map5 = new HashMap();
				map5.put("province", geographyIpList.get(i).getProvince());
				map5.put("pvNum", geographyIpList.get(i).getIpNum());
				list4.add(map5);
			}
			//查询地图中每个省份的字节数
			List<TimeSlot> geographyByteList = AnalysisDatabase.instance.getGeographyDataHour(startTime, endTime, client_id, project_id,"中国");
			List list5 = new ArrayList();
			Map map6 = null;
			for (int i = 0; i <geographyByteList.size(); i++) {
				map6 = new HashMap();
				map6.put("province", geographyByteList.get(i).getProvince());
				map6.put("pvNum", geographyByteList.get(i).getDataNum());
				list5.add(map6);
			}
			
			map1.put("geographyByteList", list5);
			map1.put("geographyIpCount", list4);
			map1.put("geographyPVCount", list3);
			map1.put("timeSlot", list1);
			map1.put("ipCount", list2);
			map1.put("newIpCount", newIpCount);
			map1.put("lastNewIpCount", lastNewIpCount);
			map1.put("allIpCount", allIpCount);
			map1.put("lastAllIpCount", lastAllIpCount);
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
			long lastStartTime=0;
			if(StringUtils.isNotEmpty(st)){
				startTime = Long.valueOf(st);
				endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			try {
				lastStartTime = DateUtils.getLastDate(startTime, endTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long thisPvNum =  AnalysisDatabase.instance.getLastPvNumMinute(startTime, endTime, client_id, project_id);
			long lastPvNum =  AnalysisDatabase.instance.getLastPvNumMinute(lastStartTime, startTime, client_id, project_id);
			TreeMap<Long, List<TimeSlot>> numCount = AnalysisDatabase.instance.getMinutePageviewCount(startTime, endTime, client_id, project_id);
			
			long newIpCount  = AnalysisDatabase.instance.getMinuteNewIp(client_id, project_id, startTime,endTime);
			long lastNewIpCount  = AnalysisDatabase.instance.getMinuteNewIp(client_id, project_id,lastStartTime, startTime);
			long allIpCount = AnalysisDatabase.instance.getAllMinuteIpCount(client_id, project_id, startTime,endTime);
			long lastAllIpCount = AnalysisDatabase.instance.getAllMinuteIpCount(client_id, project_id, lastStartTime, startTime);
			
			urlList = AnalysisDatabase.instance.getIpMinutePageviewCount(startTime, endTime, client_id, project_id);
			List list1 = new ArrayList();
			List<TimeSlot> pvList = numCount.get(1l);
			
			Map map2 = null;
			int n = pvList.size();
			/*
			 * 显示分钟数据
			 */
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long pvNum=0l;
				long ipNum=0l;
				for(int j = 0; j < n/2; j++){
					if(t==pvList.get(j).getTime()){
						m=t;
						pvNum=pvList.get(j).getPvNum();
						ipNum=pvList.get(j + n / 2).getIpNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "1"));
					map2.put("pvNum", pvNum);
					map2.put("ipNum", ipNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, "1"));
					map2.put("pvNum", 0);
					map2.put("ipNum", 0);
				}	
					
				list1.add(map2);
			}
			List list2 = new ArrayList();
			Map map3 = null;
			for (int i = urlList.size()-1; i >=0; i--) {
				map3 = new HashMap();
				map3.put("ip", urlList.get(i).getIp());
				map3.put("ipNum", urlList.get(i).getIpNum());
		//		map3.put("time", DateUtils.timeToString(urlList.get(i).getTime(), "1"));
				list2.add(map3);
			}
			//查询地图中每个省份的页面访问总量
			List<TimeSlot> geographyPVList = AnalysisDatabase.instance.getGeographyPVMinute(startTime, endTime, client_id, project_id,"中国");
			List list3 = new ArrayList();
			Map map4 = null;
			for (int i = 0; i <geographyPVList.size(); i++) {
				map4 = new HashMap();
				map4.put("province", geographyPVList.get(i).getProvince());
				map4.put("pvNum", geographyPVList.get(i).getPvNum());
				list3.add(map4);
			}
			//查询地图中每个省份的独立Ip数
			List<TimeSlot> geographyIpList = AnalysisDatabase.instance.getGeographyIpMinute(startTime, endTime, client_id, project_id,"中国");
			List list4 = new ArrayList();
			Map map5 = null;
			for (int i = 0; i <geographyIpList.size(); i++) {
				map5 = new HashMap();
				map5.put("province", geographyIpList.get(i).getProvince());
				map5.put("pvNum", geographyIpList.get(i).getIpNum());
				list4.add(map5);
			}
			//查询地图中每个省份的字节数
			List<TimeSlot> geographyByteList = AnalysisDatabase.instance.getGeographyDataMinute(startTime, endTime, client_id, project_id,"中国");
			List list5 = new ArrayList();
			Map map6 = null;
			for (int i = 0; i <geographyByteList.size(); i++) {
				map6 = new HashMap();
				map6.put("province", geographyByteList.get(i).getProvince());
				map6.put("pvNum", geographyByteList.get(i).getDataNum());
				list5.add(map6);
			}
			
			map1.put("geographyByteList", list5);
			map1.put("geographyIpCount", list4);
			map1.put("geographyPVCount", list3);
			map1.put("timeSlot", list1);
			map1.put("ipCount", list2);
			map1.put("newIpCount", newIpCount);
			map1.put("lastNewIpCount", lastNewIpCount);
			map1.put("allIpCount", allIpCount);
			map1.put("lastAllIpCount", lastAllIpCount);
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
			String ip = request.getParameter("ip");
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
			List list1 = new ArrayList();
			List<TimeSlot> tsLit =  AnalysisDatabase.instance.getPageviewByIpMinute(startTime, endTime, client_id, project_id, ip);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long IpNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						IpNum=tsLit.get(j).getIpNum();
					}
				}
				if(m!=0){
					map2.put("IpNum", IpNum);
					map2.put("time", DateUtils.timeToString(m, "1"));
				}else{
					map2.put("IpNum", 0);
					map2.put("time", DateUtils.timeToString(t, "1"));
				}	
					
				list1.add(map2);
			}
			
			map1.put("everyIpCount", list1);
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
			String ip = request.getParameter("ip");
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
			List list1 = new ArrayList();
			List<TimeSlot> tsLit =  AnalysisDatabase.instance.getPageviewByIpHour(startTime, endTime, client_id, project_id, ip);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long IpNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						IpNum=tsLit.get(j).getIpNum();
					}
				}
				if(m!=0){
					map2.put("IpNum", IpNum);
					map2.put("time", DateUtils.timeToString(m, "2"));
				}else{
					map2.put("IpNum", 0);
					map2.put("time", DateUtils.timeToString(t, "2"));
				}	
					
				list1.add(map2);
			}
			map1.put("everyIpCount", list1);
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
			String ip = request.getParameter("ip");
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
			List list1 = new ArrayList();
			List<TimeSlot> tsLit =  AnalysisDatabase.instance.getPageviewByIpDaily(startTime, endTime, client_id, project_id, ip);
			List timeList = DateUtils.getBetweenDate(startTime, endTime,"daily");
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long IpNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						IpNum=tsLit.get(j).getIpNum();
					}
				}
				if(m!=0){
					map2.put("IpNum", IpNum);
					map2.put("time", DateUtils.timeToString(m, "3"));
				}else{
					map2.put("IpNum", 0);
					map2.put("time", DateUtils.timeToString(t, "3"));
				}	
					
				list1.add(map2);
			}
			map1.put("everyIpCount", list1);
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
			String ip = request.getParameter("ip");
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
			List list1 = new ArrayList();
			List<TimeSlot> tsLit =  AnalysisDatabase.instance.getPageviewByIpMonth(startTime, endTime, client_id, project_id, ip);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long IpNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						IpNum=tsLit.get(j).getIpNum();
					}
				}
				if(m!=0){
					map2.put("IpNum", IpNum);
					map2.put("time", DateUtils.timeToString(m, "4"));
				}else{
					map2.put("IpNum", 0);
					map2.put("time", DateUtils.timeToString(t, "4"));
				}	
					
				list1.add(map2);
			}
			map1.put("everyIpCount", list1);
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
			String ip = request.getParameter("ip");
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
			List list1 = new ArrayList();
			List<TimeSlot> tsLit =  AnalysisDatabase.instance.getPageviewByIpYear(startTime, endTime, client_id, project_id, ip);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long IpNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						IpNum=tsLit.get(j).getIpNum();
					}
				}
				if(m!=0){
					map2.put("IpNum", IpNum);
					map2.put("time", DateUtils.timeToString(m, "5"));
				}else{
					map2.put("IpNum", 0);
					map2.put("time", DateUtils.timeToString(t, "5"));
				}	
					
				list1.add(map2);
			}
			map1.put("everyIpCount", list1);
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
			IpCountDatabase ipd = new IpCountDatabase();
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 2016;
			long endTime = 2017;
			String country="中国";
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = ipd.getIpPageViewByGeYear(startTime, endTime, client_id, project_id, country);
			Map map2 = null;
			for (int i = tsLit.size()-1;i>=0; i--) {
				map2 = new HashMap();
				map2.put("province", tsLit.get(i).getProvince());
				map2.put("ip", tsLit.get(i).getIp());
				map2.put("ipNum", tsLit.get(i).getIpNum());
				list1.add(map2);
			}
			map1.put("top10IpPageView", list1);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part12 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		
		@Override
		protected boolean doConditionIsTrue() {
			IpCountDatabase ipd = new IpCountDatabase();
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 201602;
			long endTime = 201603;
			String country="中国";
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = ipd.getIpPageViewByGeMonth(startTime, endTime, client_id, project_id, country);
			Map map2 = null;
			for (int i = tsLit.size()-1;i>=0; i--) {
				map2 = new HashMap();
				map2.put("province", tsLit.get(i).getProvince());
				map2.put("ip", tsLit.get(i).getIp());
				map2.put("ipNum", tsLit.get(i).getIpNum());
				list1.add(map2);
			}
			map1.put("top10IpPageView", list1);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part13 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		
		@Override
		protected boolean doConditionIsTrue() {
			IpCountDatabase ipd = new IpCountDatabase();
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 20160201;
			long endTime = 20160202;
			String country="中国";
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = ipd.getIpPageViewByGeDaily(startTime, endTime, client_id, project_id, country);
			Map map2 = null;
			for (int i = tsLit.size()-1;i>=0; i--) {
				map2 = new HashMap();
				map2.put("province", tsLit.get(i).getProvince());
				map2.put("ip", tsLit.get(i).getIp());
				map2.put("ipNum", tsLit.get(i).getIpNum());
				list1.add(map2);
			}
			map1.put("top10IpPageView", list1);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part14 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		
		@Override
		protected boolean doConditionIsTrue() {
			IpCountDatabase ipd = new IpCountDatabase();
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 2016020101;
			long endTime = 2016020102;
			String country="中国";
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = ipd.getIpPageViewByGeHour(startTime, endTime, client_id, project_id, country);
			Map map2 = null;
			for (int i = tsLit.size()-1;i>=0; i--) {
				map2 = new HashMap();
				map2.put("province", tsLit.get(i).getProvince());
				map2.put("ip", tsLit.get(i).getIp());
				map2.put("ipNum", tsLit.get(i).getIpNum());
				list1.add(map2);
			}
			map1.put("top10IpPageView", list1);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part15 extends DefaultPlainBean {
		
		Map map1 = new HashMap();
		
		@Override
		protected boolean doConditionIsTrue() {
			IpCountDatabase ipd = new IpCountDatabase();
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 201602010100l;
			long endTime = 201602010200l;
			String country="中国";
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = ipd.getIpPageViewByGeMinute(startTime, endTime, client_id, project_id, country);
			Map map2 = null;
			for (int i = tsLit.size()-1;i>=0; i--) {
				map2 = new HashMap();
				map2.put("province", tsLit.get(i).getProvince());
				map2.put("ip", tsLit.get(i).getIp());
				map2.put("ipNum", tsLit.get(i).getIpNum());
				list1.add(map2);
			}
			map1.put("top10IpPageView", list1);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	
	private final class Part16 extends DefaultPlainBean {

		
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
					
					try {
						lastStartTime = DateUtils.getLastDate(startTime, endTime);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					long thisPvNum =  AnalysisDatabase.instance.getLastPvNumHour(startTime, endTime, client_id, project_id);
					long lastPvNum =  AnalysisDatabase.instance.getLastPvNumHour(lastStartTime, startTime, client_id, project_id);
					TreeMap<Long, List<TimeSlot>> numCount = AnalysisDatabase.instance.getHourPageviewCount(startTime, endTime, client_id, project_id);
					
					long newIpCount  = AnalysisDatabase.instance.getHourNewIp(client_id, project_id, startTime,endTime);
					long lastNewIpCount  = AnalysisDatabase.instance.getHourNewIp(client_id, project_id,lastStartTime, startTime);
					long allIpCount = AnalysisDatabase.instance.getAllHourIpCount(client_id, project_id, startTime,endTime);
					long lastAllIpCount = AnalysisDatabase.instance.getAllHourIpCount(client_id, project_id, lastStartTime, startTime);
					List list1 = new ArrayList();
					List<TimeSlot> pvList = numCount.get(1l);
					Map map2 = null;
					int n = pvList.size();
					/*
					 * 显示小时数据
					 */
					List timeList = DateUtils.getBetweenDate(startTime, endTime);
					for (int i = 0; i < timeList.size(); i++) {
						map2 = new HashMap();
						long t = (long) timeList.get(i);
						long m= 0;
						long pvNum=0l;
						long ipNum=0l;
						for(int j = 0; j < n/2; j++){
							if(t==pvList.get(j).getTime()){
								m=t;
								pvNum=pvList.get(j).getPvNum();
								ipNum=pvList.get(j + n / 2).getIpNum();
							}
						}
						if(m!=0){
							map2.put("time", DateUtils.timeToString(m, "2"));
							map2.put("pvNum", pvNum);
							map2.put("ipNum", ipNum);
						}else{
							map2.put("time", DateUtils.timeToString(t, "2"));
							map2.put("pvNum", 0);
							map2.put("ipNum", 0);
						}	
							
						list1.add(map2);
					}
					
					map1.put("timeSlot", list1);
					map1.put("newIpCount", newIpCount);
					map1.put("allIpCount", allIpCount);
					map1.put("thisPvNum", thisPvNum);
					map1.put("lastNewIpCount", lastNewIpCount);
					map1.put("lastAllIpCount", lastAllIpCount);
					map1.put("startTime", startTime);
					map1.put("endTime", endTime);
					
				} else if (chartType.equals("mapplot")) {
					//查询地图中每个省份的页面访问总量
					List<TimeSlot> geographyPVList = AnalysisDatabase.instance.getGeographyPVHour(startTime, endTime, client_id, project_id,"中国");
					List list3 = new ArrayList();
					Map map4 = null;
					for (int i = 0; i <geographyPVList.size(); i++) {
						map4 = new HashMap();
						map4.put("province", geographyPVList.get(i).getProvince());
						map4.put("pvNum", geographyPVList.get(i).getPvNum());
						list3.add(map4);
					}
					//查询地图中每个省份的独立Ip数
					List<TimeSlot> geographyIpList = AnalysisDatabase.instance.getGeographyIpHour(startTime, endTime, client_id, project_id,"中国");
					List list4 = new ArrayList();
					Map map5 = null;
					for (int i = 0; i <geographyIpList.size(); i++) {
						map5 = new HashMap();
						map5.put("province", geographyIpList.get(i).getProvince());
						map5.put("pvNum", geographyIpList.get(i).getIpNum());
						list4.add(map5);
					}
					//查询地图中每个省份的字节数
					List<TimeSlot> geographyByteList = AnalysisDatabase.instance.getGeographyDataHour(startTime, endTime, client_id, project_id,"中国");
					List list5 = new ArrayList();
					Map map6 = null;
					for (int i = 0; i <geographyByteList.size(); i++) {
						map6 = new HashMap();
						map6.put("province", geographyByteList.get(i).getProvince());
						map6.put("pvNum", geographyByteList.get(i).getDataNum());
						list5.add(map6);
					}
					map1.put("geographyByteList", list5);
					map1.put("geographyIpCount", list4);
					map1.put("geographyPVCount", list3);
				} else if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List<TimeSlot> urlList = AnalysisDatabase.instance.getIpHourPageviewCount(startTime, endTime, client_id, project_id);
					List list2 = new ArrayList();
					Map map3 = null;
					for (int i = urlList.size()-1; i >=0; i--) {
						map3 = new HashMap();
						map3.put("ip", urlList.get(i).getIp());
						map3.put("ipNum", urlList.get(i).getIpNum());
						list2.add(map3);
					}
					map1.put("ipCount", list2);
					map1.put("startTime", startTime);
					map1.put("endTime", endTime);
				}
			} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
				if (chartType.equals("areaplot")) {
					
					try {
						lastStartTime = DateUtils.getLastDate(startTime, endTime);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					long thisPvNum =  AnalysisDatabase.instance.getLastPvNumDaily(startTime, endTime, client_id, project_id);
					long lastPvNum =  AnalysisDatabase.instance.getLastPvNumDaily(lastStartTime, startTime, client_id, project_id);
					TreeMap<Long, List<TimeSlot>> numCount = AnalysisDatabase.instance.getDailyPageviewCount(startTime, endTime, client_id, project_id);
					
					long newIpCount  = AnalysisDatabase.instance.getDailyNewIp(client_id, project_id, startTime,endTime);
					long lastNewIpCount  = AnalysisDatabase.instance.getDailyNewIp(client_id, project_id,lastStartTime, startTime);
					long allIpCount = AnalysisDatabase.instance.getAllDailyIpCount(client_id, project_id, startTime,endTime);
					long lastAllIpCount = AnalysisDatabase.instance.getAllDailyIpCount(client_id, project_id, lastStartTime, startTime);
					List list1 = new ArrayList();
					List<TimeSlot> pvList = numCount.get(1l);
					Map map2 = null;
					int n = pvList.size();
					/*
					 * 显示天数据
					 */
					List timeList = DateUtils.getBetweenDate(startTime, endTime,"daily");
					for (int i = 0; i < timeList.size(); i++) {
						map2 = new HashMap();
						long t = (long) timeList.get(i);
						long m= 0;
						long pvNum=0l;
						long ipNum=0l;
						for(int j = 0; j < n/2; j++){
							if(t==pvList.get(j).getTime()){
								m=t;
								pvNum=pvList.get(j).getPvNum();
								ipNum=pvList.get(j + n / 2).getIpNum();
							}
						}
						if(m!=0){
							map2.put("time", DateUtils.timeToString(m, "3"));
							map2.put("pvNum", pvNum);
							map2.put("ipNum", ipNum);
						}else{
							map2.put("time", DateUtils.timeToString(t, "3"));
							map2.put("pvNum", 0);
							map2.put("ipNum", 0);
						}	
							
						list1.add(map2);
					}
					
					map1.put("timeSlot", list1);
					map1.put("newIpCount", newIpCount);
					map1.put("allIpCount", allIpCount);
					map1.put("thisPvNum", thisPvNum);
					map1.put("lastNewIpCount", lastNewIpCount);
					map1.put("lastAllIpCount", lastAllIpCount);
					map1.put("startTime", startTime);
					map1.put("endTime", endTime);
				} else if (chartType.equals("mapplot")) {
					//查询地图中每个省份的页面访问总量
					List<TimeSlot> geographyPVList = AnalysisDatabase.instance.getGeographyPVDaily(startTime, endTime, client_id, project_id,"中国");
					List list3 = new ArrayList();
					Map map4 = null;
					for (int i = 0; i <geographyPVList.size(); i++) {
						map4 = new HashMap();
						map4.put("province", geographyPVList.get(i).getProvince());
						map4.put("pvNum", geographyPVList.get(i).getPvNum());
						list3.add(map4);
					}
					//查询地图中每个省份的独立Ip数
					List<TimeSlot> geographyIpList = AnalysisDatabase.instance.getGeographyIpDaily(startTime, endTime, client_id, project_id,"中国");
					List list4 = new ArrayList();
					Map map5 = null;
					for (int i = 0; i <geographyIpList.size(); i++) {
						map5 = new HashMap();
						map5.put("province", geographyIpList.get(i).getProvince());
						map5.put("pvNum", geographyIpList.get(i).getIpNum());
						list4.add(map5);
					}
					//查询地图中每个省份的字节数
					List<TimeSlot> geographyByteList = AnalysisDatabase.instance.getGeographyDataDaily(startTime, endTime, client_id, project_id,"中国");
					List list5 = new ArrayList();
					Map map6 = null;
					for (int i = 0; i <geographyByteList.size(); i++) {
						map6 = new HashMap();
						map6.put("province", geographyByteList.get(i).getProvince());
						map6.put("pvNum", geographyByteList.get(i).getDataNum());
						list5.add(map6);
					}
					map1.put("geographyByteList", list5);
					map1.put("geographyIpCount", list4);
					map1.put("geographyPVCount", list3);

				} else if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List<TimeSlot> urlList = AnalysisDatabase.instance.getIpDailyPageviewCount(startTime, endTime, client_id, project_id);
					List list2 = new ArrayList();
					Map map3 = null;
					for (int i = urlList.size()-1; i >=0; i--) {
						map3 = new HashMap();
						map3.put("ip", urlList.get(i).getIp());
						map3.put("ipNum", urlList.get(i).getIpNum());
						list2.add(map3);
					}
					map1.put("ipCount", list2);
					map1.put("startTime", startTime);
					map1.put("endTime", endTime);
				}
			} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
					|| dateType.equals("year")) {
				if (chartType.equals("areaplot")) {
					try {
						lastStartTime = DateUtils.getLastDate(startTime, endTime);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					long thisPvNum =  AnalysisDatabase.instance.getLastPvNumMonth(startTime, endTime, client_id, project_id);
					long lastPvNum =  AnalysisDatabase.instance.getLastPvNumMonth(lastStartTime, startTime, client_id, project_id);
					TreeMap<Long, List<TimeSlot>> numCount = AnalysisDatabase.instance.getMonthPageviewCount(startTime, endTime, client_id, project_id);
					
					long newIpCount  = AnalysisDatabase.instance.getMonthNewIp(client_id, project_id, startTime,endTime);
					long lastNewIpCount  = AnalysisDatabase.instance.getMonthNewIp(client_id, project_id,lastStartTime, startTime);
					long allIpCount = AnalysisDatabase.instance.getAllMonthIpCount(client_id, project_id, startTime,endTime);
					long lastAllIpCount = AnalysisDatabase.instance.getAllMonthIpCount(client_id, project_id, lastStartTime, startTime);
					List list1 = new ArrayList();
					List<TimeSlot> pvList = numCount.get(1l);
					Map map2 = null;
					int n = pvList.size();
					/*
					 * 显示月数据
					 */
					List timeList = DateUtils.getBetweenDate(startTime, endTime);
					for (int i = 0; i < timeList.size(); i++) {
						map2 = new HashMap();
						long t = (long) timeList.get(i);
						long m= 0;
						long pvNum=0l;
						long ipNum=0l;
						for(int j = 0; j < n/2; j++){
							if(t==pvList.get(j).getTime()){
								m=t;
								pvNum=pvList.get(j).getPvNum();
								ipNum=pvList.get(j + n / 2).getIpNum();
							}
						}
						if(m!=0){
							map2.put("time", DateUtils.timeToString(m, "4"));
							map2.put("pvNum", pvNum);
							map2.put("ipNum", ipNum);
						}else{
							map2.put("time", DateUtils.timeToString(t, "4"));
							map2.put("pvNum", 0);
							map2.put("ipNum", 0);
						}	
							
						list1.add(map2);
					}
					
					map1.put("timeSlot", list1);
					map1.put("newIpCount", newIpCount);
					map1.put("allIpCount", allIpCount);
					map1.put("thisPvNum", thisPvNum);
					map1.put("lastNewIpCount", lastNewIpCount);
					map1.put("lastAllIpCount", lastAllIpCount);
					map1.put("startTime", startTime);
					map1.put("endTime", endTime);
				} else if (chartType.equals("mapplot")) {
					//查询地图中每个省份的页面访问总量
					List<TimeSlot> geographyPVList = AnalysisDatabase.instance.getGeographyPVMonth(startTime, endTime, client_id, project_id,"中国");
					List list3 = new ArrayList();
					Map map4 = null;
					for (int i = 0; i <geographyPVList.size(); i++) {
						map4 = new HashMap();
						map4.put("province", geographyPVList.get(i).getProvince());
						map4.put("pvNum", geographyPVList.get(i).getPvNum());
						list3.add(map4);
					}
					//查询地图中每个省份的独立Ip数
					List<TimeSlot> geographyIpList = AnalysisDatabase.instance.getGeographyIpMonth(startTime, endTime, client_id, project_id,"中国");
					List list4 = new ArrayList();
					Map map5 = null;
					for (int i = 0; i <geographyIpList.size(); i++) {
						map5 = new HashMap();
						map5.put("province", geographyIpList.get(i).getProvince());
						map5.put("pvNum", geographyIpList.get(i).getIpNum());
						list4.add(map5);
					}
					//查询地图中每个省份的字节数
					List<TimeSlot> geographyByteList = AnalysisDatabase.instance.getGeographyDataMonth(startTime, endTime, client_id, project_id,"中国");
					List list5 = new ArrayList();
					Map map6 = null;
					for (int i = 0; i <geographyByteList.size(); i++) {
						map6 = new HashMap();
						map6.put("province", geographyByteList.get(i).getProvince());
						map6.put("pvNum", geographyByteList.get(i).getDataNum());
						list5.add(map6);
					}
					map1.put("geographyByteList", list5);
					map1.put("geographyIpCount", list4);
					map1.put("geographyPVCount", list3);

				} else if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List<TimeSlot> urlList = AnalysisDatabase.instance.getIpMonthPageviewCount(startTime, endTime, client_id, project_id);
					List list2 = new ArrayList();
					Map map3 = null;
					for (int i = urlList.size()-1; i >=0; i--) {
						map3 = new HashMap();
						map3.put("ip", urlList.get(i).getIp());
						map3.put("ipNum", urlList.get(i).getIpNum());
						list2.add(map3);
					}
					map1.put("ipCount", list2);
					map1.put("startTime", startTime);
					map1.put("endTime", endTime);
				}
			} else if (dateType.equals("twoYear")) {
				if (chartType.equals("areaplot")) {
					try {
						lastStartTime = DateUtils.getLastDate(startTime, endTime);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					long thisPvNum =  AnalysisDatabase.instance.getLastPvNumYear(startTime, endTime, client_id, project_id);
					long lastPvNum =  AnalysisDatabase.instance.getLastPvNumYear(lastStartTime, startTime, client_id, project_id);
					TreeMap<Long, List<TimeSlot>> numCount = AnalysisDatabase.instance.getYearPageviewCount(startTime, endTime, client_id, project_id);
					
					long newIpCount  = AnalysisDatabase.instance.getYearNewIp(client_id, project_id, startTime,endTime);
					long lastNewIpCount  = AnalysisDatabase.instance.getYearNewIp(client_id, project_id,lastStartTime, startTime);
					long allIpCount = AnalysisDatabase.instance.getAllYearIpCount(client_id, project_id, startTime,endTime);
					long lastAllIpCount = AnalysisDatabase.instance.getAllYearIpCount(client_id, project_id, lastStartTime, startTime);
					
					List list1 = new ArrayList();
					List<TimeSlot> pvList = numCount.get(1l);
					Map map2 = null;
					int n = pvList.size();
					/*
					 * 显示年数据
					 */
					List timeList = DateUtils.getBetweenDate(startTime, endTime);
					for (int i = 0; i < timeList.size(); i++) {
						map2 = new HashMap();
						long t = (long) timeList.get(i);
						long m= 0;
						long pvNum=0l;
						long ipNum=0l;
						for(int j = 0; j < n/2; j++){
							if(t==pvList.get(j).getTime()){
								m=t;
								pvNum=pvList.get(j).getPvNum();
								ipNum=pvList.get(j + n / 2).getIpNum();
							}
						}
						if(m!=0){
							map2.put("time", DateUtils.timeToString(m, "5"));
							map2.put("pvNum", pvNum);
							map2.put("ipNum", ipNum);
						}else{
							map2.put("time", DateUtils.timeToString(t, "5"));
							map2.put("pvNum", 0);
							map2.put("ipNum", 0);
						}	
							
						list1.add(map2);
					}
					
					map1.put("timeSlot", list1);
					map1.put("newIpCount", newIpCount);
					map1.put("allIpCount", allIpCount);
					map1.put("thisPvNum", thisPvNum);
					map1.put("lastNewIpCount", lastNewIpCount);
					map1.put("lastAllIpCount", lastAllIpCount);
					map1.put("startTime", startTime);
					map1.put("endTime", endTime);
				} else if (chartType.equals("mapplot")) {
					//查询地图中每个省份的页面访问总量
					List<TimeSlot> geographyPVList = AnalysisDatabase.instance.getGeographyPVYear(startTime, endTime, client_id, project_id,"中国");
					List list3 = new ArrayList();
					Map map4 = null;
					for (int i = 0; i <geographyPVList.size(); i++) {
						map4 = new HashMap();
						map4.put("province", geographyPVList.get(i).getProvince());
						map4.put("pvNum", geographyPVList.get(i).getPvNum());
						list3.add(map4);
					}
					//查询地图中每个省份的独立Ip数
					List<TimeSlot> geographyIpList = AnalysisDatabase.instance.getGeographyIpYear(startTime, endTime, client_id, project_id,"中国");
					List list4 = new ArrayList();
					Map map5 = null;
					for (int i = 0; i <geographyIpList.size(); i++) {
						map5 = new HashMap();
						map5.put("province", geographyIpList.get(i).getProvince());
						map5.put("pvNum", geographyIpList.get(i).getIpNum());
						list4.add(map5);
					}
					//查询地图中每个省份的字节数
					List<TimeSlot> geographyByteList = AnalysisDatabase.instance.getGeographyDataYear(startTime, endTime, client_id, project_id,"中国");
					List list5 = new ArrayList();
					Map map6 = null;
					for (int i = 0; i <geographyByteList.size(); i++) {
						map6 = new HashMap();
						map6.put("province", geographyByteList.get(i).getProvince());
						map6.put("pvNum", geographyByteList.get(i).getDataNum());
						list5.add(map6);
					}
					map1.put("geographyByteList", list5);
					map1.put("geographyIpCount", list4);
					map1.put("geographyPVCount", list3);
				} else if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List<TimeSlot> urlList = AnalysisDatabase.instance.getIpYearPageviewCount(startTime, endTime, client_id, project_id);
					List list2 = new ArrayList();
					Map map3 = null;
					for (int i = urlList.size()-1; i >=0; i--) {
						map3 = new HashMap();
						map3.put("ip", urlList.get(i).getIp());
						map3.put("ipNum", urlList.get(i).getIpNum());
						list2.add(map3);
					}
					map1.put("ipCount", list2);
					map1.put("startTime", startTime);
					map1.put("endTime", endTime);
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

	
	
	
