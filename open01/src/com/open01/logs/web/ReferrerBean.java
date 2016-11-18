package com.open01.logs.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.taglibs.standard.tag.common.sql.DataSourceUtil;
import org.jboss.netty.util.internal.StringUtil;

import com.open01.logs.db.RefererDatabase;
import com.open01.logs.model.TimeSlot;
import com.open01.logs.util.Consant;
import com.open01.logs.util.DateUtils;
import com.open01.logs.util.JsonUtils;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class ReferrerBean extends WholePartBean implements BooleanCondition {

	/**
	 * Default constructor.
	 */

	/**
	 * Default constructor.
	 */
	public ReferrerBean() {
		super("cmd");
		addPart(null, new Part0());// 默认获取一天的数据，从hour表
		addPart("WEL:SELECTYEAR", new Part1());// 获取年表信息
		addPart("WEL:SELECTMONTH", new Part2());// 获取月表信息
		addPart("WEL:SELECTDAILY", new Part3());// 获取天表信息
		addPart("WEL:SELECTHOUR", new Part4());// 获取小时表信息
		addPart("WEL:SELECTMINUTE", new Part5());// 获取分钟,小时,日,月,年表信息
		
		addPart("WEL:SELECTURLBYYEAR", new Part6());// 
		addPart("WEL:SELECTURLBYMONTH", new Part7());// 
		addPart("WEL:SELECTURLBYDAILY", new Part8());// 
		addPart("WEL:SELECTURLBYHOUR", new Part9());// 
		addPart("WEL:SELECTURLBYMINUTE", new Part10());// 
		
		addPart("WEL:GETCHARINFO", new Part11());
	}

	@Override
	public boolean isTrue() {
		// TODO Auto-generated method stub
		return true;
	}
	RefererDatabase rfd  = new RefererDatabase ();
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
			List list2 = new ArrayList();
			Map map2 = null;
			Map map3 = null;
			List<TimeSlot> tsLit = rfd.getDailyReferrer(startTime, endTime, client_id, project_id);
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("referrerType", tsLit.get(i).getReferrerType());
				map2.put("referrerNum", tsLit.get(i).getReferrerNum());
				list1.add(map2);
			}
			List<TimeSlot> indirectLit = rfd.getDailyIndirectReferrer(startTime, endTime, client_id, project_id);
			for (int i = indirectLit.size()-1; i >=0; i--) {
				map3 = new HashMap();
				map3.put("url", indirectLit.get(i).getReferrerType());
				map3.put("urlNum", indirectLit.get(i).getReferrerNum());
				list2.add(map3);
			}
			
			map1.put("directType", list1);
			map1.put("indirectType", list2);
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
			List list2 = new ArrayList();
			Map map2 = null;
			Map map3 = null;
			List<TimeSlot> tsLit = rfd.getYearReferrer(startTime, endTime, client_id, project_id);
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("referrerType", tsLit.get(i).getReferrerType());
				map2.put("referrerNum", tsLit.get(i).getReferrerNum());
				list1.add(map2);
			}
			List<TimeSlot> indirectLit = rfd.getYearIndirectReferrer(startTime, endTime, client_id, project_id);
			for (int i = indirectLit.size()-1; i >=0; i--) {
				map3 = new HashMap();
				map3.put("url", indirectLit.get(i).getReferrerType());
				map3.put("urlNum", indirectLit.get(i).getReferrerNum());
				list2.add(map3);
			}
			
			map1.put("directType", list1);
			map1.put("indirectType", list2);
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
			List list2 = new ArrayList();
			Map map2 = null;
			Map map3 = null;
			List<TimeSlot> tsLit = rfd.getMonthReferrer(startTime, endTime, client_id, project_id);
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("referrerType", tsLit.get(i).getReferrerType());
				map2.put("referrerNum", tsLit.get(i).getReferrerNum());
				list1.add(map2);
			}
			List<TimeSlot> indirectLit = rfd.getMonthIndirectReferrer(startTime, endTime, client_id, project_id);
			for (int i = indirectLit.size()-1; i >=0; i--) {
				map3 = new HashMap();
				map3.put("url", indirectLit.get(i).getReferrerType());
				map3.put("urlNum", indirectLit.get(i).getReferrerNum());
				list2.add(map3);
			}
			
			map1.put("directType", list1);
			map1.put("indirectType", list2);
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
			List list2 = new ArrayList();
			Map map2 = null;
			Map map3 = null;
			List<TimeSlot> tsLit = rfd.getDailyReferrer(startTime, endTime, client_id, project_id);
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("referrerType", tsLit.get(i).getReferrerType());
				map2.put("referrerNum", tsLit.get(i).getReferrerNum());
				list1.add(map2);
			}
			List<TimeSlot> indirectLit = rfd.getDailyIndirectReferrer(startTime, endTime, client_id, project_id);
			for (int i = indirectLit.size()-1; i >=0; i--) {
				map3 = new HashMap();
				map3.put("url", indirectLit.get(i).getReferrerType());
				map3.put("urlNum", indirectLit.get(i).getReferrerNum());
				list2.add(map3);
			}
			
			map1.put("directType", list1);
			map1.put("indirectType", list2);
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
	/*		startTime = 2016020100;
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
			List list2 = new ArrayList();
			Map map2 = null;
			Map map3 = null;
			List<TimeSlot> tsLit = rfd.getHourReferrer(startTime, endTime, client_id, project_id);
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("referrerType", tsLit.get(i).getReferrerType());
				map2.put("referrerNum", tsLit.get(i).getReferrerNum());
				list1.add(map2);
			}
			List<TimeSlot> indirectLit = rfd.getHourIndirectReferrer(startTime, endTime, client_id, project_id);
			for (int i = indirectLit.size()-1; i >=0; i--) {
				map3 = new HashMap();
				map3.put("url", indirectLit.get(i).getReferrerType());
				map3.put("urlNum", indirectLit.get(i).getReferrerNum());
				list2.add(map3);
			}
			
			map1.put("directType", list1);
			map1.put("indirectType", list2);
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
			/*startTime = 201602010000l;
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
			List list2 = new ArrayList();
			Map map2 = null;
			Map map3 = null;
			List<TimeSlot> tsLit = rfd.getMinuteReferrer(startTime, endTime, client_id, project_id);
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("referrerType", tsLit.get(i).getReferrerType());
				map2.put("referrerNum", tsLit.get(i).getReferrerNum());
				list1.add(map2);
			}
			List<TimeSlot> indirectLit = rfd.getMinuteIndirectReferrer(startTime, endTime, client_id, project_id);
			for (int i = indirectLit.size()-1; i >=0; i--) {
				map3 = new HashMap();
				map3.put("url", indirectLit.get(i).getReferrerType());
				map3.put("urlNum", indirectLit.get(i).getReferrerNum());
				list2.add(map3);
			}
			
			map1.put("directType", list1);
			map1.put("indirectType", list2);
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
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			String referrer = request.getParameter("referrer");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 0;
			long endTime = 0;
			if(StringUtils.isNotEmpty(st)){
				 startTime=Long.valueOf(st);
				 endTime=Long.valueOf(et);
			}
			List list1 = new ArrayList();
			Map map2 = null;
			List<TimeSlot>  tsLit;
			String flag="";
			/*int subTime=DateUtils.substractNum(startTime, endTime);//计算开始和截止时间是否大于一年
			if(subTime<=1){
				flag="4";
				startTime=startTime*100+1;
				endTime=endTime*100+1;
				if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
					tsLit = rfd.getMonthByUrlDirect(startTime, endTime, client_id, project_id);
				}else{
					tsLit = rfd.getMonthByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
				}
			}else{
				flag="5";
				if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
					tsLit = rfd.getYearByUrlDirect(startTime, endTime, client_id, project_id);
				}else{
					tsLit = rfd.getYearByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
				}
			}*/
			flag="5";
			if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
				tsLit = rfd.getYearByUrlDirect(startTime, endTime, client_id, project_id);
			}else{
				tsLit = rfd.getYearByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
			}
			
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long pvNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						pvNum=tsLit.get(j).getPvNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, flag));
					map2.put("pvNum", pvNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, flag));
					map2.put("pvNum", 0);
				}
				list1.add(map2);
			}
			map1.put("timeSlot", list1);
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
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			String referrer = request.getParameter("referrer");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 0;
			long endTime = 0;
			if(StringUtils.isNotEmpty(st)){
				startTime=Long.valueOf(st);
				endTime=Long.valueOf(et);
			}
			List list1 = new ArrayList();
			Map map2 = null;
			List<TimeSlot>  tsLit;
			String flag="";
			/*int subTime=DateUtils.substractNum(startTime, endTime);//计算开始和截止时间是否大于一天
			if(subTime<=1){
				flag="3";
				startTime=startTime*100+1;
				endTime=endTime*100+1;
				if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
					tsLit = rfd.getDailyByUrlDirect(startTime, endTime, client_id, project_id);
				}else{
					tsLit = rfd.getDailyByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
				}
			}else{
				flag="4";
				if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
					tsLit = rfd.getMonthByUrlDirect(startTime, endTime, client_id, project_id);
				}else{
					tsLit = rfd.getMonthByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
				}
			}*/
			
			flag="4";
			if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
				tsLit = rfd.getMonthByUrlDirect(startTime, endTime, client_id, project_id);
			}else{
				tsLit = rfd.getMonthByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
			}
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long pvNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						pvNum=tsLit.get(j).getPvNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, flag));
					map2.put("pvNum", pvNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, flag));
					map2.put("pvNum", 0);
				}
				list1.add(map2);
			}
			map1.put("timeSlot", list1);
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
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			String referrer = request.getParameter("referrer");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 0;
			long endTime = 0;
			if(StringUtils.isNotEmpty(st)){
				startTime=Long.valueOf(st);
				endTime=Long.valueOf(et);
			}
			List list1 = new ArrayList();
			Map map2 = null;
			List<TimeSlot>  tsLit;
			String flag="";
			/*int subTime=DateUtils.substractNum(startTime, endTime);//计算开始和截止时间是否大于一天
			if(subTime<=1){
				flag="2";
				startTime=startTime*100;
				endTime=endTime*100;
				if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
					tsLit = rfd.getHourByUrlDirect(startTime, endTime, client_id, project_id);
				}else{
					tsLit = rfd.getHourByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
				}
			}else{
				flag="3";
				if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
					tsLit = rfd.getDailyByUrlDirect(startTime, endTime, client_id, project_id);
				}else{
					tsLit = rfd.getDailyByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
				}
			}*/
			flag="3";
			if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
				tsLit = rfd.getDailyByUrlDirect(startTime, endTime, client_id, project_id);
			}else{
				tsLit = rfd.getDailyByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
			}
			
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long pvNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						pvNum=tsLit.get(j).getPvNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, flag));
					map2.put("pvNum", pvNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, flag));
					map2.put("pvNum", 0);
				}
				list1.add(map2);
			}
			map1.put("timeSlot", list1);
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
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			String referrer = request.getParameter("referrer");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 0;
			long endTime = 0;
			if(StringUtils.isNotEmpty(st)){
				startTime=Long.valueOf(st);
				endTime=Long.valueOf(et);
			}
			List list1 = new ArrayList();
			Map map2 = null;
			List<TimeSlot>  tsLit;
			String flag="";
			/*int subTime=DateUtils.substractNum(startTime, endTime);//计算开始和截止时间是否大于一天
			if(subTime<=1){
				flag="1";
				startTime=startTime*100;
				endTime=endTime*100;
				if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
					tsLit = rfd.getMinuteByUrlDirect(startTime, endTime, client_id, project_id);
				}else{
					tsLit = rfd.getMinuteByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
				}
			}else{
				flag="2";
				if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
					tsLit = rfd.getHourByUrlDirect(startTime, endTime, client_id, project_id);
				}else{
					tsLit = rfd.getHourByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
				}
			}*/
			flag="2";
			if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
				tsLit = rfd.getHourByUrlDirect(startTime, endTime, client_id, project_id);
			}else{
				tsLit = rfd.getHourByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
			}
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long pvNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						pvNum=tsLit.get(j).getPvNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, flag));
					map2.put("pvNum", pvNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, flag));
					map2.put("pvNum", 0);
				}
				list1.add(map2);
			}
			map1.put("timeSlot", list1);
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
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			String referrer = request.getParameter("referrer");
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 0;
			long endTime = 0;
			if(StringUtils.isNotEmpty(st)){
				startTime=Long.valueOf(st);
				endTime=Long.valueOf(et);
			}
			List list1 = new ArrayList();
			Map map2 = null;
			List<TimeSlot>  tsLit;
			if(referrer.equals(Consant.FILE_REPORT_REFERRER)){
				tsLit = rfd.getMinuteByUrlDirect(startTime, endTime, client_id, project_id);
			}else{
				tsLit = rfd.getMinuteByUrlIndirect(startTime, endTime, client_id, project_id, referrer);
			}
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long pvNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						pvNum=tsLit.get(j).getPvNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "1"));
					map2.put("pvNum", pvNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, "1"));
					map2.put("pvNum", 0);
				}
				list1.add(map2);
			}
			map1.put("timeSlot", list1);
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
					List list2 = new ArrayList();
					Map map2 = null;
					Map map3 = null;
					List<TimeSlot> tsLit = rfd.getHourReferrer(startTime, endTime, client_id, project_id);
					for (int i = 0; i < tsLit.size(); i++) {
						map2 = new HashMap();
						map2.put("referrerType", tsLit.get(i).getReferrerType());
						map2.put("referrerNum", tsLit.get(i).getReferrerNum());
						list1.add(map2);
					}
					List<TimeSlot> indirectLit = rfd.getHourIndirectReferrer(startTime, endTime, client_id, project_id);
					for (int i = 0; i < indirectLit.size(); i++) {
						map3 = new HashMap();
						map3.put("url", indirectLit.get(i).getReferrerType());
						map3.put("urlNum", indirectLit.get(i).getReferrerNum());
						list2.add(map3);
					}
					
					map1.put("directType", list1);
					map1.put("indirectType", list2);
				}
			} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
				if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List list1 = new ArrayList();
					List list2 = new ArrayList();
					Map map2 = null;
					Map map3 = null;
					List<TimeSlot> tsLit = rfd.getDailyReferrer(startTime, endTime, client_id, project_id);
					for (int i = 0; i < tsLit.size(); i++) {
						map2 = new HashMap();
						map2.put("referrerType", tsLit.get(i).getReferrerType());
						map2.put("referrerNum", tsLit.get(i).getReferrerNum());
						list1.add(map2);
					}
					List<TimeSlot> indirectLit = rfd.getDailyIndirectReferrer(startTime, endTime, client_id, project_id);
					for (int i = 0; i < indirectLit.size(); i++) {
						map3 = new HashMap();
						map3.put("url", indirectLit.get(i).getReferrerType());
						map3.put("urlNum", indirectLit.get(i).getReferrerNum());
						list2.add(map3);
					}
					
					map1.put("directType", list1);
					map1.put("indirectType", list2);
				}
			} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")|| dateType.equals("year")) {
				if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List list1 = new ArrayList();
					List list2 = new ArrayList();
					Map map2 = null;
					Map map3 = null;
					List<TimeSlot> tsLit = rfd.getMonthReferrer(startTime, endTime, client_id, project_id);
					for (int i = 0; i < tsLit.size(); i++) {
						map2 = new HashMap();
						map2.put("referrerType", tsLit.get(i).getReferrerType());
						map2.put("referrerNum", tsLit.get(i).getReferrerNum());
						list1.add(map2);
					}
					List<TimeSlot> indirectLit = rfd.getMonthIndirectReferrer(startTime, endTime, client_id, project_id);
					for (int i = 0; i < indirectLit.size(); i++) {
						map3 = new HashMap();
						map3.put("url", indirectLit.get(i).getReferrerType());
						map3.put("urlNum", indirectLit.get(i).getReferrerNum());
						list2.add(map3);
					}
					
					map1.put("directType", list1);
					map1.put("indirectType", list2);
				}
			} else if (dateType.equals("twoYear")) {
				if (chartType.equals("hbarplot") || chartType.equals("treemap")) {
					List list1 = new ArrayList();
					List list2 = new ArrayList();
					Map map2 = null;
					Map map3 = null;
					List<TimeSlot> tsLit = rfd.getYearReferrer(startTime, endTime, client_id, project_id);
					for (int i = 0; i < tsLit.size(); i++) {
						map2 = new HashMap();
						map2.put("referrerType", tsLit.get(i).getReferrerType());
						map2.put("referrerNum", tsLit.get(i).getReferrerNum());
						list1.add(map2);
					}
					List<TimeSlot> indirectLit = rfd.getYearIndirectReferrer(startTime, endTime, client_id, project_id);
					for (int i = 0; i < indirectLit.size(); i++) {
						map3 = new HashMap();
						map3.put("url", indirectLit.get(i).getReferrerType());
						map3.put("urlNum", indirectLit.get(i).getReferrerNum());
						list2.add(map3);
					}
					
					map1.put("directType", list1);
					map1.put("indirectType", list2);
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