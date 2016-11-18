package com.open01.logs.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.open01.logs.db.StatusDatabase;
import com.open01.logs.model.TimeSlot;
import com.open01.logs.util.DateUtils;
import com.open01.logs.util.JsonUtils;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class StatusBean extends WholePartBean implements BooleanCondition {

	/**
	 * Default constructor.
	 */

	/**
	 * Default constructor.
	 */
	public StatusBean() {
		super("cmd");
		addPart(null, new Part0());// 默认获取一天的数据，从hour表
		addPart("WEL:SELECTYEAR", new Part1());// 获取年表信息
		addPart("WEL:SELECTMONTH", new Part2());// 获取月表信息
		addPart("WEL:SELECTDAILY", new Part3());// 获取天表信息
		addPart("WEL:SELECTHOUR", new Part4());// 获取小时表信息
		addPart("WEL:SELECTMINUTE", new Part5());// 获取分钟,小时,日,月,年表信息

		addPart("WEL:GETINFOBYSTATUSYEAR", new Part6());// 根据后台发送状态信息获取年表信息
		addPart("WEL:GETINFOBYSTATUSMONTH", new Part7());// 根据后台发送状态信息获取月表信息
		addPart("WEL:GETINFOBYSTATUSDAILY", new Part8());// 根据后台发送状态信息获取日表信息
		addPart("WEL:GETINFOBYSTATUSHOUR", new Part9());// 根据后台发送状态信息获取小时表信息
		addPart("WEL:GETINFOBYSTATUSMINUTE", new Part10());// 根据后台发送状态信息获取分钟表信息

		addPart("WEL:GETIPBYSTATUSYEAR", new Part11());// 根据后台发送状态获取所有ip的状态分布
		addPart("WEL:GETIPBYSTATUSMONTH", new Part12());
		addPart("WEL:GETIPBYSTATUSDAILY", new Part13());
		addPart("WEL:GETIPBYSTATUSHOUR", new Part14());
		addPart("WEL:GETIPBYSTATUSMINUTE", new Part15());

		addPart("WEL:GETTOPIPGEYEAR", new Part16());// 根据后台发送状态获取所有地理位置中ip及页面访问量
		addPart("WEL:GETTOPIPGEMONTH", new Part17());
		addPart("WEL:GETTOPIPGEDAILY", new Part18());
		addPart("WEL:GETTOPIPGEHOUR", new Part19());
		addPart("WEL:GETTOPIPGEMINUTE", new Part20());
		
		addPart("WEL:GETCHARINFO", new Part21());

	}

	@Override
	public boolean isTrue() {
		// TODO Auto-generated method stub
		return true;
	}

	StatusDatabase sd = new StatusDatabase();
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
		/*	long startTime = 0;
			long endTime = 0;
			startTime = 20160201;
			endTime = 20160202;*/
			long startTime=(long) DateUtils.getInitDate("day").get(0);
			long endTime=(long) DateUtils.getInitDate("day").get(1);
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = sd.getDailyStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("status", tsLit.get(i).getStatus());
				map2.put("statusNum", tsLit.get(i).getStatusNum());
				list1.add(map2);
			}
			
			/*
			 * 查询所有状态（２00,400...）下url和statusNum
			 */
			List<TimeSlot> urlList = sd.getUrlByStatusDaily(startTime, endTime, client_id, project_id);
			Map map3 = new HashMap();
			for(int i = tsLit.size()-1; i>=0; i--){
				List list2 = new ArrayList();
				Map map4 = null;
				for(int j = 0; j < urlList.size(); j++){
					if(tsLit.get(i).getStatus()==urlList.get(j).getStatus()){
						map4 = new HashMap();
						map4.put("statusNum", urlList.get(j).getUrlNum());
						map4.put("url", urlList.get(j).getUrl());
						list2.add(map4);
					}
				}
				List list3 = new ArrayList();
				int n=0;
				if(list2.size()>50){
					n=50;
				}else{
					n=list2.size();
				}
				
				for(int j=n-1;j>=0;j--){
					list3.add(list2.get(j));
				}
				map3.put(tsLit.get(i).getStatus(), list3);
			}
			

			/*
			 * 查询所有状态（２00,400...）下ip和statusNum
			 */
			List<TimeSlot> ipList = sd.getIpByStatusDaily(startTime, endTime, client_id, project_id);
			Map map5 = new HashMap();
			for(int i = tsLit.size()-1; i>=0; i--){
				List list2 = new ArrayList();
				Map map4 = null;
				for(int j = 0; j < ipList.size(); j++){
					if(tsLit.get(i).getStatus()==ipList.get(j).getStatus()){
						map4 = new HashMap();
						map4.put("statusNum", ipList.get(j).getUrlNum());
						map4.put("ip", ipList.get(j).getUrl());
						list2.add(map4);
					}
				}
				List list3 = new ArrayList();
				int n=0;
				if(list2.size()>50){
					n=50;
				}else{
					n=list2.size();
				}
				
				for(int j=n-1;j>=0;j--){
					list3.add(list2.get(j));
				}
				map5.put(tsLit.get(i).getStatus(), list3);
			}
			/*
			 * 查询所有状态（２00,400...）下status和statusNum,
			 */
			List<TimeSlot> statusList = sd.getByStatusHour(startTime*100, endTime*100, client_id, project_id);
			List timeList = DateUtils.getBetweenDate(startTime*100, endTime*100);
			Map map6 = new HashMap();
			if(tsLit.size()!=0){
				for(int i = tsLit.size()-1; i>=0; i--){
					
					List list2 = new ArrayList();
					Map map4 = null;
					for (int n = 0; n < timeList.size(); n++) {
						map4 = new HashMap();
						long t = (long) timeList.get(n);
						long m= 0;
						long pvNum=0l;
						for(int j = 0; j < statusList.size(); j++){
							if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
								m=t;
								pvNum=statusList.get(j).getPvNum();
							}
						}
						if(m!=0){
							map4.put("time", DateUtils.timeToString(m, "2"));
							map4.put("pvNum", pvNum);
						}else{
							map4.put("time", DateUtils.timeToString(t, "2"));
							map4.put("pvNum", "0");
						}
						list2.add(map4);
					}
					map6.put(tsLit.get(i).getStatus(), list2);
				}
			}else{
				List list2 = new ArrayList();
				for (int n = 0; n < timeList.size(); n++) {
					Map map4 =  new HashMap();
					map4.put("time", DateUtils.timeToString((long) timeList.get(n), "2"));
					map4.put("pvNum", 0);
					list2.add(map4);
				}
				map6.put("", list2);
			}
			map1.put("allStatusType", map6);
			map1.put("IpStatusType", map5);
			map1.put("urlStatusType", map3);
			map1.put("timeSlot", list1);
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
			long startTime = 0;
			long endTime = 0;

		/*	startTime = 201602;
			endTime = 201606;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "5");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = sd.getYearStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("status", tsLit.get(i).getStatus());
				map2.put("statusNum", tsLit.get(i).getStatusNum());
				list1.add(map2);
			}
			/*
			 * 查询所有状态（２00,400...）下url和statusNum
			 */
			List<TimeSlot> urlList = sd.getUrlByStatusYear(startTime, endTime, client_id, project_id);
			Map map3 = new HashMap();
			for(int i = tsLit.size()-1; i>=0; i--){
				List list2 = new ArrayList();
				Map map4 = null;
				for(int j = 0; j < urlList.size(); j++){
					if(tsLit.get(i).getStatus()==urlList.get(j).getStatus()){
						map4 = new HashMap();
						map4.put("statusNum", urlList.get(j).getUrlNum());
						map4.put("url", urlList.get(j).getUrl());
						list2.add(map4);
					}
				}
				List list3 = new ArrayList();
				Map map5 = null;
				int n=0;
				if(list2.size()>50){
					n=50;
				}else{
					n=list2.size();
				}
				
				for(int j=n-1;j>=0;j--){
					list3.add(list2.get(j));
				}
				map3.put(tsLit.get(i).getStatus(), list3);
			}
			/*
			 * 查询所有状态（２00,400...）下ip和statusNum
			 */
			List<TimeSlot> ipList = sd.getIpByStatusYear(startTime, endTime, client_id, project_id);
			Map map5 = new HashMap();
			for(int i = tsLit.size()-1; i>=0; i--){
				List list2 = new ArrayList();
				Map map4 = null;
				for(int j = 0; j < ipList.size(); j++){
					if(tsLit.get(i).getStatus()==ipList.get(j).getStatus()){
						map4 = new HashMap();
						map4.put("statusNum", ipList.get(j).getUrlNum());
						map4.put("ip", ipList.get(j).getUrl());
						list2.add(map4);
					}
				}
				List list3 = new ArrayList();
				int n=0;
				if(list2.size()>50){
					n=50;
				}else{
					n=list2.size();
				}
				
				for(int j=n-1;j>=0;j--){
					list3.add(list2.get(j));
				}
				map5.put(tsLit.get(i).getStatus(), list3);
			}
			/*
			 * 查询所有状态（２00,400...）下status和statusNum
			 */
			List<TimeSlot> statusList = sd.getByStatusYear(startTime, endTime, client_id, project_id);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map6 = new HashMap();
			if(tsLit.size()!=0){
				for(int i = tsLit.size()-1; i>=0; i--){
					
					List list2 = new ArrayList();
					Map map4 = null;
					for (int n = 0; n < timeList.size(); n++) {
						map4 = new HashMap();
						long t = (long) timeList.get(n);
						long m= 0;
						long pvNum=0l;
						for(int j = 0; j < statusList.size(); j++){
							if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
								m=t;
								pvNum=statusList.get(j).getPvNum();
							}
						}
						if(m!=0){
							map4.put("time", DateUtils.timeToString(m, "5"));
							map4.put("pvNum", pvNum);
						}else{
							map4.put("time", DateUtils.timeToString(t, "5"));
							map4.put("pvNum", 0);
						}
						list2.add(map4);
					}
					
					map6.put(tsLit.get(i).getStatus(), list2);
				}
			}else{
					List list2 = new ArrayList();
					for (int n = 0; n < timeList.size(); n++) {
						Map map4 =  new HashMap();
						map4.put("time", DateUtils.timeToString((long) timeList.get(n), "5"));
						map4.put("pvNum", 0);
						list2.add(map4);
					}
					map6.put("", list2);
			}
			
			map1.put("allStatusType", map6);
			map1.put("IpStatusType", map5);
			map1.put("urlStatusType", map3);
			map1.put("timeSlot", list1);
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
			long startTime = 0;
			long endTime = 0;

		/*	startTime = 201602;
			endTime = 201606;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "4");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = sd.getMonthStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("status", tsLit.get(i).getStatus());
				map2.put("statusNum", tsLit.get(i).getStatusNum());
				list1.add(map2);
			}
			/*
			 * 查询所有状态（２00,400...）下url和statusNum
			 */
			List<TimeSlot> urlList = sd.getUrlByStatusMonth(startTime, endTime, client_id, project_id);
			Map map3 = new HashMap();
			for(int i = tsLit.size()-1; i>=0; i--){
				List list2 = new ArrayList();
				Map map4 = null;
				for(int j = 0; j < urlList.size(); j++){
					if(tsLit.get(i).getStatus()==urlList.get(j).getStatus()){
						map4 = new HashMap();
						map4.put("statusNum", urlList.get(j).getUrlNum());
						map4.put("url", urlList.get(j).getUrl());
						list2.add(map4);
					}
				}
				List list3 = new ArrayList();
				Map map5 = null;
				int n=0;
				if(list2.size()>50){
					n=50;
				}else{
					n=list2.size();
				}
				
				for(int j=n-1;j>=0;j--){
					list3.add(list2.get(j));
				}
				map3.put(tsLit.get(i).getStatus(), list3);
			}
			/*
			 * 查询所有状态（２00,400...）下ip和statusNum
			 */
			List<TimeSlot> ipList = sd.getIpByStatusMonth(startTime, endTime, client_id, project_id);
			Map map5 = new HashMap();
			for(int i = tsLit.size()-1; i>=0; i--){
				List list2 = new ArrayList();
				Map map4 = null;
				for(int j = 0; j < ipList.size(); j++){
					if(tsLit.get(i).getStatus()==ipList.get(j).getStatus()){
						map4 = new HashMap();
						map4.put("statusNum", ipList.get(j).getUrlNum());
						map4.put("ip", ipList.get(j).getUrl());
						list2.add(map4);
					}
				}
				List list3 = new ArrayList();
				int n=0;
				if(list2.size()>50){
					n=50;
				}else{
					n=list2.size();
				}
				
				for(int j=n-1;j>=0;j--){
					list3.add(list2.get(j));
				}
				map5.put(tsLit.get(i).getStatus(), list3);
			}
			/*
			 * 查询所有状态（２00,400...）下status和statusNum
			 */
			List<TimeSlot> statusList = sd.getByStatusMonth(startTime, endTime, client_id, project_id);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map6 = new HashMap();
			if(tsLit.size()!=0){
				for(int i = tsLit.size()-1; i>=0; i--){
					
					List list2 = new ArrayList();
					Map map4 = null;
					for (int n = 0; n < timeList.size(); n++) {
						map4 = new HashMap();
						long t = (long) timeList.get(n);
						long m= 0;
						long pvNum=0l;
						for(int j = 0; j < statusList.size(); j++){
							if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
								m=t;
								pvNum=statusList.get(j).getPvNum();
							}
						}
						if(m!=0){
							map4.put("time", DateUtils.timeToString(m, "4"));
							map4.put("pvNum", pvNum);
						}else{
							map4.put("time", DateUtils.timeToString(t, "4"));
							map4.put("pvNum", "0");
						}
						list2.add(map4);
					}
					
					map6.put(tsLit.get(i).getStatus(), list2);
				}
			}else{
					List list2 = new ArrayList();
					for (int n = 0; n < timeList.size(); n++) {
						Map map4 =  new HashMap();
						map4.put("time", DateUtils.timeToString((long) timeList.get(n), "4"));
						map4.put("pvNum", 0);
						list2.add(map4);
					}
					map6.put("", list2);
			}
			
			map1.put("allStatusType", map6);
			map1.put("IpStatusType", map5);
			map1.put("urlStatusType", map3);
			map1.put("timeSlot", list1);
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
			long startTime = 0;
			long endTime = 0;
	/*		startTime = 20160200;
			endTime = 20160300;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = sd.getDailyStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("status", tsLit.get(i).getStatus());
				map2.put("statusNum", tsLit.get(i).getStatusNum());
				list1.add(map2);
			}
			
			/*
			 * 查询所有状态（２00,400...）下url和statusNum
			 */
			List<TimeSlot> urlList = sd.getUrlByStatusDaily(startTime, endTime, client_id, project_id);
			Map map3 = new HashMap();
			for(int i = tsLit.size()-1; i>=0; i--){
				List list2 = new ArrayList();
				Map map4 = null;
				for(int j = 0; j < urlList.size(); j++){
					if(tsLit.get(i).getStatus()==urlList.get(j).getStatus()){
						map4 = new HashMap();
						map4.put("statusNum", urlList.get(j).getUrlNum());
						map4.put("url", urlList.get(j).getUrl());
						list2.add(map4);
					}
				}
				List list3 = new ArrayList();
				Map map5 = null;
				int n=0;
				if(list2.size()>50){
					n=50;
				}else{
					n=list2.size();
				}
				
				for(int j=n-1;j>=0;j--){
					list3.add(list2.get(j));
				}
				map3.put(tsLit.get(i).getStatus(), list3);
			}
			/*
			 * 查询所有状态（２00,400...）下ip和statusNum
			 */
			List<TimeSlot> ipList = sd.getIpByStatusDaily(startTime, endTime, client_id, project_id);
			Map map5 = new HashMap();
			for(int i = tsLit.size()-1; i>=0; i--){
				List list2 = new ArrayList();
				Map map4 = null;
				for(int j = 0; j < ipList.size(); j++){
					if(tsLit.get(i).getStatus()==ipList.get(j).getStatus()){
						map4 = new HashMap();
						map4.put("statusNum", ipList.get(j).getUrlNum());
						map4.put("ip", ipList.get(j).getUrl());
						list2.add(map4);
					}
				}
				List list3 = new ArrayList();
				int n=0;
				if(list2.size()>50){
					n=50;
				}else{
					n=list2.size();
				}
				
				for(int j=n-1;j>=0;j--){
					list3.add(list2.get(j));
				}
				map5.put(tsLit.get(i).getStatus(), list3);
			}
			/*
			 * 查询所有状态（２00,400...）下status和statusNum
			 */
			List<TimeSlot> statusList = sd.getByStatusDaily(startTime, endTime, client_id, project_id);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map6 = new HashMap();
			if(tsLit.size()!=0){
				for(int i = tsLit.size()-1; i>=0; i--){
					
					List list2 = new ArrayList();
					Map map4 = null;
					for (int n = 0; n < timeList.size(); n++) {
						map4 = new HashMap();
						long t = (long) timeList.get(n);
						long m= 0;
						long pvNum=0l;
						for(int j = 0; j < statusList.size(); j++){
							if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
								m=t;
								pvNum=statusList.get(j).getPvNum();
							}
						}
						if(m!=0){
							map4.put("time", DateUtils.timeToString(m, "3"));
							map4.put("pvNum", pvNum);
						}else{
							map4.put("time", DateUtils.timeToString(t, "3"));
							map4.put("pvNum", "0");
						}
						list2.add(map4);
					}
					
					map6.put(tsLit.get(i).getStatus(), list2);
				}
			}else{
					List list2 = new ArrayList();
					for (int n = 0; n < timeList.size(); n++) {
						Map map4 =  new HashMap();
						map4.put("time", DateUtils.timeToString((long) timeList.get(n), "3"));
						map4.put("pvNum", 0);
						list2.add(map4);
					}
					map6.put("", list2);
			}
			
			map1.put("allStatusType", map6);
			map1.put("IpStatusType", map5);
			map1.put("urlStatusType", map3);
			map1.put("timeSlot", list1);
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
			long startTime = 0;
			long endTime = 0;
		/*	startTime = 2016020100;
			endTime = 2016020200;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = sd.getHourStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("status", tsLit.get(i).getStatus());
				map2.put("statusNum", tsLit.get(i).getStatusNum());
				list1.add(map2);
			}
			
			/*
			 * 查询所有状态（２00,400...）下url和statusNum
			 */
			List<TimeSlot> urlList = sd.getUrlByStatusHour(startTime, endTime, client_id, project_id);
			Map map3 = new HashMap();
			for(int i = tsLit.size()-1; i>=0; i--){
				List list2 = new ArrayList();
				Map map4 = null;
				for(int j = 0; j < urlList.size(); j++){
					if(tsLit.get(i).getStatus()==urlList.get(j).getStatus()){
						map4 = new HashMap();
						map4.put("statusNum", urlList.get(j).getUrlNum());
						map4.put("url", urlList.get(j).getUrl());
						list2.add(map4);
					}
				}
				List list3 = new ArrayList();
				Map map5 = null;
				int n=0;
				if(list2.size()>50){
					n=50;
				}else{
					n=list2.size();
				}
				
				for(int j=n-1;j>=0;j--){
					list3.add(list2.get(j));
				}
				map3.put(tsLit.get(i).getStatus(), list3);
			}
			/*
			 * 查询所有状态（２00,400...）下ip和statusNum
			 */
			List<TimeSlot> ipList = sd.getIpByStatusHour(startTime, endTime, client_id, project_id);
			Map map5 = new HashMap();
			for(int i = tsLit.size()-1; i>=0; i--){
				List list2 = new ArrayList();
				Map map4 = null;
				for(int j = 0; j < ipList.size(); j++){
					if(tsLit.get(i).getStatus()==ipList.get(j).getStatus()){
						map4 = new HashMap();
						map4.put("statusNum", ipList.get(j).getUrlNum());
						map4.put("ip", ipList.get(j).getUrl());
						list2.add(map4);
					}
				}
				List list3 = new ArrayList();
				int n=0;
				if(list2.size()>50){
					n=50;
				}else{
					n=list2.size();
				}
				
				for(int j=n-1;j>=0;j--){
					list3.add(list2.get(j));
				}
				map5.put(tsLit.get(i).getStatus(), list3);
			}
			/*
			 * 查询所有状态（２00,400...）下status和statusNum
			 */
			List<TimeSlot> statusList = sd.getByStatusHour(startTime, endTime, client_id, project_id);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map6 = new HashMap();
			if(tsLit.size()!=0){
				for(int i = tsLit.size()-1; i>=0; i--){
					
					List list2 = new ArrayList();
					Map map4 = null;
					for (int n = 0; n < timeList.size(); n++) {
						map4 = new HashMap();
						long t = (long) timeList.get(n);
						long m= 0;
						long pvNum=0l;
						for(int j = 0; j < statusList.size(); j++){
							if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
								m=t;
								pvNum=statusList.get(j).getPvNum();
							}
						}
						if(m!=0){
							map4.put("time", DateUtils.timeToString(m, "2"));
							map4.put("pvNum", pvNum);
						}else{
							map4.put("time", DateUtils.timeToString(t, "2"));
							map4.put("pvNum", "0");
						}
						list2.add(map4);
					}
					
					map6.put(tsLit.get(i).getStatus(), list2);
				}
			}else{
					List list2 = new ArrayList();
					for (int n = 0; n < timeList.size(); n++) {
						Map map4 =  new HashMap();
						map4.put("time", DateUtils.timeToString((long) timeList.get(n), "2"));
						map4.put("pvNum", 0);
						list2.add(map4);
					}
					map6.put("", list2);
			}
			
			map1.put("allStatusType", map6);
			map1.put("IpStatusType", map5);
			map1.put("urlStatusType", map3);
			map1.put("timeSlot", list1);
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
			long startTime = 0;
			long endTime = 0;
		/*	startTime = 201602010000l;
			endTime = 201602010100l;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = sd.getMinuteStatus(startTime, endTime, client_id, project_id);
			Map map2 = null;
			for (int i = 0; i < tsLit.size(); i++) {
				map2 = new HashMap();
				map2.put("status", tsLit.get(i).getStatus());
				map2.put("statusNum", tsLit.get(i).getStatusNum());
				list1.add(map2);
			}	
			
			/*
			 * 查询所有状态（２00,400...）下url和statusNum
			 */
			List<TimeSlot> urlList = sd.getUrlByStatusMinute(startTime, endTime, client_id, project_id);
			Map map3 = new HashMap();
			for(int i = tsLit.size()-1; i>=0; i--){
				List list2 = new ArrayList();
				Map map4 = null;
				for(int j = 0; j < urlList.size(); j++){
					if(tsLit.get(i).getStatus()==urlList.get(j).getStatus()){
						map4 = new HashMap();
						map4.put("statusNum", urlList.get(j).getUrlNum());
						map4.put("url", urlList.get(j).getUrl());
						list2.add(map4);
					}
				}
				List list3 = new ArrayList();
				Map map5 = null;
				int n=0;
				if(list2.size()>50){
					n=50;
				}else{
					n=list2.size();
				}
				
				for(int j=n-1;j>=0;j--){
					list3.add(list2.get(j));
				}
				map3.put(tsLit.get(i).getStatus(), list3);
			}
			/*
			 * 查询所有状态（２00,400...）下ip和statusNum
			 */
			List<TimeSlot> ipList = sd.getIpByStatusMinute(startTime, endTime, client_id, project_id);
			Map map5 = new HashMap();
			for(int i = tsLit.size()-1; i>=0; i--){
				List list2 = new ArrayList();
				Map map4 = null;
				for(int j = 0; j < ipList.size(); j++){
					if(tsLit.get(i).getStatus()==ipList.get(j).getStatus()){
						map4 = new HashMap();
						map4.put("statusNum", ipList.get(j).getUrlNum());
						map4.put("ip", ipList.get(j).getUrl());
						list2.add(map4);
					}
				}
				List list3 = new ArrayList();
				int n=0;
				if(list2.size()>50){
					n=50;
				}else{
					n=list2.size();
				}
				
				for(int j=n-1;j>=0;j--){
					list3.add(list2.get(j));
				}
				map5.put(tsLit.get(i).getStatus(), list3);
			}
			/*
			 * 查询所有状态（２00,400...）下status和statusNum
			 */
			List<TimeSlot> statusList = sd.getByStatusMinute(startTime, endTime, client_id, project_id);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map6 = new HashMap();
			if(tsLit.size()!=0){
				for(int i = tsLit.size()-1; i>=0; i--){
					
					List list2 = new ArrayList();
					Map map4 = null;
					for (int n = 0; n < timeList.size(); n++) {
						map4 = new HashMap();
						long t = (long) timeList.get(n);
						long m= 0;
						long pvNum=0l;
						for(int j = 0; j < statusList.size(); j++){
							if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
								m=t;
								pvNum=statusList.get(j).getPvNum();
							}
						}
						if(m!=0){
							map4.put("time", DateUtils.timeToString(m, "1"));
							map4.put("pvNum", pvNum);
						}else{
							map4.put("time", DateUtils.timeToString(t, "1"));
							map4.put("pvNum", "0");
						}
						list2.add(map4);
					}
					
					map6.put(tsLit.get(i).getStatus(), list2);
				}
			}else{
					List list2 = new ArrayList();
					for (int n = 0; n < timeList.size(); n++) {
						Map map4 =  new HashMap();
						map4.put("time", DateUtils.timeToString((long) timeList.get(n), "1"));
						map4.put("pvNum", 0);
						list2.add(map4);
					}
					map6.put("", list2);
			}
			map1.put("allStatusType", map6);
			map1.put("IpStatusType", map5);
			map1.put("urlStatusType", map3);
			map1.put("timeSlot", list1);
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
			Integer status = Integer.valueOf(request.getParameter("status"));
			String url = request.getParameter("url");
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
			long startTime = 0;
			long endTime = 0;
			/*startTime = 2016;
			endTime = 2017;*/

			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "5");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			 
			List list1 = new ArrayList();
			Map map2 = null;
			List<TimeSlot> tsLit;
			String flag="";
			int subTime=DateUtils.substractNum(startTime, endTime);//计算开始和截止时间是否大于一年
			if(subTime<=1){
				flag="4";
				startTime=startTime*100+1;
				endTime=endTime*100+1;
				tsLit = sd.getInfoByStatusMonth(startTime, endTime, client_id, project_id, status,url);
			}else{
				flag="5";
				tsLit = sd.getInfoByStatusYear(startTime, endTime, client_id, project_id, status,url);
			}
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long statusNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						statusNum=tsLit.get(j).getStNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, flag));
					map2.put("statusNum", statusNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, flag));
					map2.put("statusNum", 0);
				}	
					
				list1.add(map2);
			}
			map1.put("everyUrlStatus", list1);
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
			Integer status = Integer.valueOf(request.getParameter("status"));
			String url = request.getParameter("url");
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 11;
			}
			long startTime = 0;
			long endTime = 0;
/*			startTime = 201602;
			endTime = 201603;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "4");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			Map map2 = null;
			List<TimeSlot> tsLit;
			String flag="";
			int subTime=DateUtils.substractNum(startTime, endTime);//计算开始和截止时间是否大于一月
			if(subTime<=1){
				flag="3";
				startTime=startTime*100+1;
				endTime=endTime*100+1;
				tsLit = sd.getInfoByStatusDaily(startTime, endTime, client_id, project_id, status,url);
			}else{
				flag="4";
				tsLit = sd.getInfoByStatusMonth(startTime, endTime, client_id, project_id, status,url);
			}
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long statusNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						statusNum=tsLit.get(j).getStatusNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, flag));
					map2.put("statusNum", statusNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, flag));
					map2.put("statusNum", 0);
				}	
					
				list1.add(map2);
			}
			map1.put("everyUrlStatus", list1);
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
			Integer status = Integer.valueOf(request.getParameter("status"));
			String url = request.getParameter("url");
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
			long startTime = 0;
			long endTime = 0;
	/*		startTime = 20160201;
			endTime = 20160301;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			Map map2 = null;
			List<TimeSlot> tsLit;
			String flag="";
			int subTime=DateUtils.substractNum(startTime, endTime);//计算开始和截止时间是否大于一天
			if(subTime<=1){
				flag="2";
				startTime=startTime*100+1;
				endTime=endTime*100+1;
				tsLit = sd.getInfoByStatusHour(startTime, endTime, client_id, project_id, status,url);
			}else{
				flag="3";
				tsLit = sd.getInfoByStatusDaily(startTime, endTime, client_id, project_id, status,url);
			}
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long statusNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						statusNum=tsLit.get(j).getStatusNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, flag));
					map2.put("statusNum", statusNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, flag));
					map2.put("statusNum", 0);
				}	
					
				list1.add(map2);
			}
			map1.put("everyUrlStatus", list1);
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
			Integer status = Integer.valueOf(request.getParameter("status"));
			String url = request.getParameter("url");
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
			long startTime = 0;
			long endTime = 0;
			
		/*	startTime = 2016020100;
			endTime = 2016020200;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			Map map2 = null;
			List<TimeSlot> tsLit;
			String flag="";
			int subTime=DateUtils.substractNum(startTime, endTime);//计算开始和截止时间是否大于一小时
			if(subTime<=1){
				flag="1";
				startTime=startTime*100;
				endTime=endTime*100;
				tsLit = sd.getInfoByStatusMinute(startTime, endTime, client_id, project_id, status,url);
			}else{
				flag="2";
				tsLit = sd.getInfoByStatusHour(startTime, endTime, client_id, project_id, status,url);
			}
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long statusNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						statusNum=tsLit.get(j).getStatusNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, flag));
					map2.put("statusNum", statusNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, flag));
					map2.put("statusNum", 0);
				}	
					
				list1.add(map2);
			}
			map1.put("everyUrlStatus", list1);
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
			Integer status = Integer.valueOf(request.getParameter("status"));
			String url = request.getParameter("url");
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
			long startTime = 0;
			long endTime = 0;
		/*	startTime = 201602010000l;
			endTime = 201602010100l;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = sd.getInfoByStatusMinute(startTime, endTime, client_id, project_id, status,url);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long statusNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						statusNum=tsLit.get(j).getStatusNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "1"));
					map2.put("statusNum", statusNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, "1"));
					map2.put("statusNum", 0);
				}
				list1.add(map2);
			}
			map1.put("everyUrlStatus", list1);
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
			 Integer statusType = Integer.valueOf(request.getParameter("status"));
			
			//Integer statusType = 200;
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			String ip = request.getParameter("ip");
			
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 0;
			long endTime = 0;
	/*		startTime = 2016;
			endTime = 2017;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "5");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			List<TimeSlot> tsLit;
			String flag="";
			int subTime=DateUtils.substractNum(startTime, endTime);//计算开始和截止时间是否大于一年
			if(subTime<=1){
				flag="4";
				startTime=startTime*100+1;
				endTime=endTime*100+1;
				tsLit = sd.getIpByStatusMonth(startTime, endTime, client_id, project_id, statusType,ip);
			}else{
				flag="5";
				tsLit = sd.getIpByStatusYear(startTime, endTime, client_id, project_id, statusType,ip);
			}
		
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long statusNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						statusNum=tsLit.get(j).getStNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, flag));
					map2.put("statusNum", statusNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, flag));
					map2.put("statusNum", 0);
				}	
					
				list1.add(map2);
			}
			map1.put("everyIpStatus", list1);
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
			Integer statusType = Integer.valueOf(request.getParameter("status"));
			//Integer statusType = 200;
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			String ip = request.getParameter("ip");
			
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 0;
			long endTime = 0;
			/*startTime = 201602;
			endTime = 201603;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "4");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			List<TimeSlot> tsLit;
			String flag="";
			int subTime=DateUtils.substractNum(startTime, endTime);//计算开始和截止时间是否大于一月
			if(subTime<=1){
				flag="3";
				startTime=startTime*100+1;
				endTime=endTime*100+1;
				tsLit = sd.getIpByStatusDaily(startTime, endTime, client_id, project_id, statusType,ip);
			}else{
				flag="4";
				tsLit = sd.getIpByStatusMonth(startTime, endTime, client_id, project_id, statusType,ip);
			}
		
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long statusNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						statusNum=tsLit.get(j).getStNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, flag));
					map2.put("statusNum", statusNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, flag));
					map2.put("statusNum", 0);
				}	
					
				list1.add(map2);
			}
			map1.put("everyIpStatus", list1);
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
			Integer statusType = Integer.valueOf(request.getParameter("status"));
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			String ip = request.getParameter("ip");
			
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 0;
			long endTime = 0;
/*			startTime = 20160201;
			endTime = 20160207;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			List<TimeSlot> tsLit;
			String flag="";
			int subTime=DateUtils.substractNum(startTime, endTime);//计算开始和截止时间是否大于一月
			if(subTime<=1){
				flag="4";
				startTime=startTime*100;
				endTime=endTime*100;
				tsLit = sd.getIpByStatusHour(startTime, endTime, client_id, project_id, statusType,ip);
			}else{
				flag="3";
				tsLit = sd.getIpByStatusDaily(startTime, endTime, client_id, project_id, statusType,ip);
			}
		
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long statusNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						statusNum=tsLit.get(j).getStNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, flag));
					map2.put("statusNum", statusNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, flag));
					map2.put("statusNum", 0);
				}	
					
				list1.add(map2);
			}
			map1.put("everyIpStatus", list1);
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
			Integer statusType = Integer.valueOf(request.getParameter("status"));
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			String ip = request.getParameter("ip");
			
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}
			long startTime = 0;
			long endTime = 0;
			/*startTime = 2016020100;
			endTime = 2016020200;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			List list1 = new ArrayList();
			List<TimeSlot> tsLit;
			String flag="";
			int subTime=DateUtils.substractNum(startTime, endTime);//计算开始和截止时间是否大于一小时
			if(subTime<=1){
				flag="1";
				startTime=startTime*100;
				endTime=endTime*100;
				tsLit = sd.getIpByStatusMinute(startTime, endTime, client_id, project_id, statusType,ip);
			}else{
				flag="2";
				tsLit = sd.getIpByStatusHour(startTime, endTime, client_id, project_id, statusType,ip);
			}
			
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long statusNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						statusNum=tsLit.get(j).getStNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, flag));
					map2.put("statusNum", statusNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, flag));
					map2.put("statusNum", 0);
				}	
					
				list1.add(map2);
			}
			map1.put("everyIpStatus", list1);
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
			Integer statusType = Integer.valueOf(request.getParameter("status"));
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			String ip = request.getParameter("ip");
			
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 11;
			}
			long startTime = 0;
			long endTime = 0;
		/*	startTime = 201602010000l;
			endTime = 201602010100l;*/
			
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			 
			List list1 = new ArrayList();
			List<TimeSlot> tsLit = sd.getIpByStatusMinute(startTime, endTime, client_id, project_id, statusType,ip);
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			Map map2 = null;
			for (int i = 0; i < timeList.size(); i++) {
				map2 = new HashMap();
				long t = (long) timeList.get(i);
				long m= 0;
				long statusNum=0l;
				for(int j = 0; j < tsLit.size(); j++){
					if(t==tsLit.get(j).getTime()){
						m=t;
						statusNum=tsLit.get(j).getStNum();
					}
				}
				if(m!=0){
					map2.put("time", DateUtils.timeToString(m, "1"));
					map2.put("statusNum", statusNum);
				}else{
					map2.put("time", DateUtils.timeToString(t, "1"));
					map2.put("statusNum", 0);
				}	
					
				list1.add(map2);
			}
			map1.put("everyIpStatus", list1);
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
			long startTime = 0;
			long endTime = 0;
/*			startTime = 2016;
			endTime = 2017;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			
			/*
			 * 查询所有状态（２00,400...）下url和statusNum
			 */
			List<TimeSlot> tsLit = sd.getYearStatus(startTime, endTime, client_id, project_id);
			List<TimeSlot> tsLit1 = sd.getTopIpYear(startTime, endTime, client_id, project_id);
			
			for(int i = 0; i<tsLit.size(); i++){
				List list1 = new ArrayList();
				Map map2 = null;
				for(int j = 0; j < tsLit1.size(); j++){
					if(tsLit.get(i).getStatus()==tsLit1.get(j).getStatus()){
						map2 = new HashMap();
						map2.put("province", tsLit1.get(j).getProvince());
						map2.put("ip", tsLit1.get(j).getIp());
						map2.put("ipNum", tsLit1.get(j).getIpNum());
						list1.add(map2);
					}
				}
				List list2 = new ArrayList();
				int n=0;
				if(list1.size()>10){
					n=10;
				}else{
					n=list1.size();
				}
				
				for(int j =n-1;j>=0;j--){
					list2.add(list1.get(j));
				}
				map1.put(tsLit.get(i).getStatus(), list2);
			}
			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part17 extends DefaultPlainBean {
		
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
			long startTime = 0;
			long endTime = 0;
		/*	startTime = 201602;
			endTime = 201603;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			
			/*
			 * 查询所有状态（２00,400...）下url和statusNum
			 */
			List<TimeSlot> tsLit = sd.getMonthStatus(startTime, endTime, client_id, project_id);
			List<TimeSlot> tsLit1 = sd.getTopIpMonth(startTime, endTime, client_id, project_id);
			
			for(int i = 0; i<tsLit.size(); i++){
				List list1 = new ArrayList();
				Map map2 = null;
				for(int j = 0; j < tsLit1.size(); j++){
					if(tsLit.get(i).getStatus()==tsLit1.get(j).getStatus()){
						map2 = new HashMap();
						map2.put("province", tsLit1.get(j).getProvince());
						map2.put("ip", tsLit1.get(j).getIp());
						map2.put("ipNum", tsLit1.get(j).getIpNum());
						list1.add(map2);
					}
				}
				List list2 = new ArrayList();
				int n=0;
				if(list1.size()>50){
					n=50;
				}else{
					n=list1.size();
				}
				
				for(int j =n-1;j>=0;j--){
					list2.add(list1.get(j));
				}
				map1.put(tsLit.get(i).getStatus(), list2);
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part18 extends DefaultPlainBean {
		
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
			long startTime = 0;
			long endTime = 0;
	/*		startTime = 20160301;
			endTime = 20160302;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			
			/*
			 * 查询所有状态（２00,400...）下url和statusNum
			 */
			List<TimeSlot> tsLit = sd.getDailyStatus(startTime, endTime, client_id, project_id);
			List<TimeSlot> tsLit1 = sd.getTopIpDaily(startTime, endTime, client_id, project_id);
			
			for(int i = 0; i<tsLit.size(); i++){
				List list1 = new ArrayList();
				Map map2 = null;
				for(int j = 0; j < tsLit1.size(); j++){
					if(tsLit.get(i).getStatus()==tsLit1.get(j).getStatus()){
						map2 = new HashMap();
						map2.put("province", tsLit1.get(j).getProvince());
						map2.put("ip", tsLit1.get(j).getIp());
						map2.put("ipNum", tsLit1.get(j).getIpNum());
						list1.add(map2);
					}
				}
				List list2 = new ArrayList();
				int n=0;
				if(list1.size()>50){
					n=50;
				}else{
					n=list1.size();
				}
				
				for(int j =n-1;j>=0;j--){
					list2.add(list1.get(j));
				}
				map1.put(tsLit.get(i).getStatus(), list2);
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part19 extends DefaultPlainBean {
		
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
			long startTime = 0;
			long endTime = 0;
/*			startTime = 2016030100;
			endTime = 2016030200;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			
			/*
			 * 查询所有状态（２00,400...）下url和statusNum
			 */
			List<TimeSlot> tsLit = sd.getHourStatus(startTime, endTime, client_id, project_id);
			List<TimeSlot> tsLit1 = sd.getTopIpHour(startTime, endTime, client_id, project_id);
			
			for(int i = 0; i<tsLit.size(); i++){
				List list1 = new ArrayList();
				Map map2 = null;
				for(int j = 0; j < tsLit1.size(); j++){
					if(tsLit.get(i).getStatus()==tsLit1.get(j).getStatus()){
						map2 = new HashMap();
						map2.put("province", tsLit1.get(j).getProvince());
						map2.put("ip", tsLit1.get(j).getIp());
						map2.put("ipNum", tsLit1.get(j).getIpNum());
						list1.add(map2);
					}
				}
				List list2 = new ArrayList();
				int n=0;
				if(list1.size()>50){
					n=50;
				}else{
					n=list1.size();
				}
				
				for(int j =n-1;j>=0;j--){
					list2.add(list1.get(j));
				}
				map1.put(tsLit.get(i).getStatus(), list2);
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	private final class Part20 extends DefaultPlainBean {
		
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
			long startTime = 0;
			long endTime = 0;
		/*	startTime = 2016030100;
			endTime = 2016030159;*/
			if (StringUtils.isNotEmpty(st)) {
				startTime = Long.valueOf(st);
				endTime = Long.valueOf(et);
			} else {
				HashMap<String, Long> dayMap = DateUtils.getDay(dt, "1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			
			/*
			 * 查询所有状态（２00,400...）下url和statusNum
			 */
			List<TimeSlot> tsLit = sd.getMinuteStatus(startTime, endTime, client_id, project_id);
			List<TimeSlot> tsLit1 = sd.getTopIpMinute(startTime, endTime, client_id, project_id);
			
			for(int i = 0; i<tsLit.size(); i++){
				List list1 = new ArrayList();
				Map map2 = null;
				for(int j = 0; j < tsLit1.size(); j++){
					if(tsLit.get(i).getStatus()==tsLit1.get(j).getStatus()){
						map2 = new HashMap();
						map2.put("province", tsLit1.get(j).getProvince());
						map2.put("ip", tsLit1.get(j).getIp());
						map2.put("ipNum", tsLit1.get(j).getIpNum());
						list1.add(map2);
					}
				}
				List list2 = new ArrayList();
				int n=0;
				if(list1.size()>50){
					n=50;
				}else{
					n=list1.size();
				}
				
				for(int j =n-1;j>=0;j--){
					list2.add(list1.get(j));
				}
				map1.put(tsLit.get(i).getStatus(), list2);
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}


private final class Part21 extends DefaultPlainBean {
	
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
			if (chartType.equals("vbarplot")) {
				List list1 = new ArrayList();
				startTime=startTime/100;
				endTime=endTime/100;
				List<TimeSlot> tsLit = sd.getDailyStatus(startTime, endTime, client_id, project_id);
				Map map2 = null;
				for (int i = 0; i < tsLit.size(); i++) {
					map2 = new HashMap();
					map2.put("status", tsLit.get(i).getStatus());
					map2.put("statusNum", tsLit.get(i).getStatusNum());
					list1.add(map2);
				}
				/*
				 * 查询所有状态（２00,400...）下url和statusNum
				 */
				List<TimeSlot> urlList = sd.getUrlByStatusDaily(startTime, endTime, client_id, project_id);
				Map map3 = new HashMap();
				for(int i = tsLit.size()-1; i>=0; i--){
					List list2 = new ArrayList();
					Map map4 = null;
					for(int j = 0; j < urlList.size(); j++){
						if(tsLit.get(i).getStatus()==urlList.get(j).getStatus()){
							map4 = new HashMap();
							map4.put("statusNum", urlList.get(j).getUrlNum());
							map4.put("url", urlList.get(j).getUrl());
							list2.add(map4);
						}
					}
					List list3 = new ArrayList();
					int n=0;
					if(list2.size()>50){
						n=50;
					}else{
						n=list2.size();
					}
					
					for(int j=n-1;j>=0;j--){
						list3.add(list2.get(j));
					}
					map3.put(tsLit.get(i).getStatus(), list3);
				}
				

				/*
				 * 查询所有状态（２00,400...）下ip和statusNum
				 */
				List<TimeSlot> ipList = sd.getIpByStatusDaily(startTime, endTime, client_id, project_id);
				Map map5 = new HashMap();
				for(int i = tsLit.size()-1; i>=0; i--){
					List list2 = new ArrayList();
					Map map4 = null;
					for(int j = 0; j < ipList.size(); j++){
						if(tsLit.get(i).getStatus()==ipList.get(j).getStatus()){
							map4 = new HashMap();
							map4.put("statusNum", ipList.get(j).getUrlNum());
							map4.put("ip", ipList.get(j).getUrl());
							list2.add(map4);
						}
					}
					List list3 = new ArrayList();
					int n=0;
					if(list2.size()>50){
						n=50;
					}else{
						n=list2.size();
					}
					
					for(int j=n-1;j>=0;j--){
						list3.add(list2.get(j));
					}
					map5.put(tsLit.get(i).getStatus(), list3);
				}
				/*
				 * 查询所有状态（２00,400...）下status和statusNum,
				 */
				List<TimeSlot> statusList = sd.getByStatusHour(startTime*100, endTime*100, client_id, project_id);
				List timeList = DateUtils.getBetweenDate(startTime*100, endTime*100);
				Map map6 = new HashMap();
				if(tsLit.size()!=0){
					for(int i = tsLit.size()-1; i>=0; i--){
						
						List list2 = new ArrayList();
						Map map4 = null;
						for (int n = 0; n < timeList.size(); n++) {
							map4 = new HashMap();
							long t = (long) timeList.get(n);
							long m= 0;
							long pvNum=0l;
							for(int j = 0; j < statusList.size(); j++){
								if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
									m=t;
									pvNum=statusList.get(j).getPvNum();
								}
							}
							if(m!=0){
								map4.put("time", DateUtils.timeToString(m, "2"));
								map4.put("pvNum", pvNum);
							}else{
								map4.put("time", DateUtils.timeToString(t, "2"));
								map4.put("pvNum", "0");
							}
							list2.add(map4);
						}
						map6.put(tsLit.get(i).getStatus(), list2);
					}
				}else{
					List list2 = new ArrayList();
					for (int n = 0; n < timeList.size(); n++) {
						Map map4 =  new HashMap();
						map4.put("time", DateUtils.timeToString((long) timeList.get(n), "2"));
						map4.put("pvNum", 0);
						list2.add(map4);
					}
					map6.put("", list2);
				}
				map1.put("allStatusType", map6);
				map1.put("IpStatusType", map5);
				map1.put("urlStatusType", map3);
				map1.put("timeSlot", list1);
				map1.put("startTime", startTime);
				map1.put("endTime", endTime);
			}
		} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
			if (chartType.equals("vbarplot")) {
				List list1 = new ArrayList();
				List<TimeSlot> tsLit = sd.getDailyStatus(startTime, endTime, client_id, project_id);
				Map map2 = null;
				for (int i = 0; i < tsLit.size(); i++) {
					map2 = new HashMap();
					map2.put("status", tsLit.get(i).getStatus());
					map2.put("statusNum", tsLit.get(i).getStatusNum());
					list1.add(map2);
				}
				
				/*
				 * 查询所有状态（２00,400...）下url和statusNum
				 */
				List<TimeSlot> urlList = sd.getUrlByStatusDaily(startTime, endTime, client_id, project_id);
				Map map3 = new HashMap();
				for(int i = tsLit.size()-1; i>=0; i--){
					List list2 = new ArrayList();
					Map map4 = null;
					for(int j = 0; j < urlList.size(); j++){
						if(tsLit.get(i).getStatus()==urlList.get(j).getStatus()){
							map4 = new HashMap();
							map4.put("statusNum", urlList.get(j).getUrlNum());
							map4.put("url", urlList.get(j).getUrl());
							list2.add(map4);
						}
					}
					List list3 = new ArrayList();
					Map map5 = null;
					int n=0;
					if(list2.size()>50){
						n=50;
					}else{
						n=list2.size();
					}
					
					for(int j=n-1;j>=0;j--){
						list3.add(list2.get(j));
					}
					map3.put(tsLit.get(i).getStatus(), list3);
				}
				/*
				 * 查询所有状态（２00,400...）下ip和statusNum
				 */
				List<TimeSlot> ipList = sd.getIpByStatusDaily(startTime, endTime, client_id, project_id);
				Map map5 = new HashMap();
				for(int i = tsLit.size()-1; i>=0; i--){
					List list2 = new ArrayList();
					Map map4 = null;
					for(int j = 0; j < ipList.size(); j++){
						if(tsLit.get(i).getStatus()==ipList.get(j).getStatus()){
							map4 = new HashMap();
							map4.put("statusNum", ipList.get(j).getUrlNum());
							map4.put("ip", ipList.get(j).getUrl());
							list2.add(map4);
						}
					}
					List list3 = new ArrayList();
					int n=0;
					if(list2.size()>50){
						n=50;
					}else{
						n=list2.size();
					}
					
					for(int j=n-1;j>=0;j--){
						list3.add(list2.get(j));
					}
					map5.put(tsLit.get(i).getStatus(), list3);
				}
				/*
				 * 查询所有状态（２00,400...）下status和statusNum
				 */
				List<TimeSlot> statusList = sd.getByStatusDaily(startTime, endTime, client_id, project_id);
				List timeList = DateUtils.getBetweenDate(startTime, endTime);
				Map map6 = new HashMap();
				if(tsLit.size()!=0){
					for(int i = tsLit.size()-1; i>=0; i--){
						
						List list2 = new ArrayList();
						Map map4 = null;
						for (int n = 0; n < timeList.size(); n++) {
							map4 = new HashMap();
							long t = (long) timeList.get(n);
							long m= 0;
							long pvNum=0l;
							for(int j = 0; j < statusList.size(); j++){
								if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
									m=t;
									pvNum=statusList.get(j).getPvNum();
								}
							}
							if(m!=0){
								map4.put("time", DateUtils.timeToString(m, "3"));
								map4.put("pvNum", pvNum);
							}else{
								map4.put("time", DateUtils.timeToString(t, "3"));
								map4.put("pvNum", "0");
							}
							list2.add(map4);
						}
						
						map6.put(tsLit.get(i).getStatus(), list2);
					}
				}else{
						List list2 = new ArrayList();
						for (int n = 0; n < timeList.size(); n++) {
							Map map4 =  new HashMap();
							map4.put("time", DateUtils.timeToString((long) timeList.get(n), "3"));
							map4.put("pvNum", 0);
							list2.add(map4);
						}
						map6.put("", list2);
				}
				
				map1.put("allStatusType", map6);
				map1.put("IpStatusType", map5);
				map1.put("urlStatusType", map3);
				map1.put("timeSlot", list1);
				map1.put("startTime", startTime);
				map1.put("endTime", endTime);
			}
		} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")|| dateType.equals("year")) {
			if (chartType.equals("vbarplot")) {
				List list1 = new ArrayList();
				List<TimeSlot> tsLit = sd.getMonthStatus(startTime, endTime, client_id, project_id);
				Map map2 = null;
				for (int i = 0; i < tsLit.size(); i++) {
					map2 = new HashMap();
					map2.put("status", tsLit.get(i).getStatus());
					map2.put("statusNum", tsLit.get(i).getStatusNum());
					list1.add(map2);
				}
				/*
				 * 查询所有状态（２00,400...）下url和statusNum
				 */
				List<TimeSlot> urlList = sd.getUrlByStatusMonth(startTime, endTime, client_id, project_id);
				Map map3 = new HashMap();
				for(int i = tsLit.size()-1; i>=0; i--){
					List list2 = new ArrayList();
					Map map4 = null;
					for(int j = 0; j < urlList.size(); j++){
						if(tsLit.get(i).getStatus()==urlList.get(j).getStatus()){
							map4 = new HashMap();
							map4.put("statusNum", urlList.get(j).getUrlNum());
							map4.put("url", urlList.get(j).getUrl());
							list2.add(map4);
						}
					}
					List list3 = new ArrayList();
					Map map5 = null;
					int n=0;
					if(list2.size()>50){
						n=50;
					}else{
						n=list2.size();
					}
					
					for(int j=n-1;j>=0;j--){
						list3.add(list2.get(j));
					}
					map3.put(tsLit.get(i).getStatus(), list3);
				}
				/*
				 * 查询所有状态（２00,400...）下ip和statusNum
				 */
				List<TimeSlot> ipList = sd.getIpByStatusMonth(startTime, endTime, client_id, project_id);
				Map map5 = new HashMap();
				for(int i = tsLit.size()-1; i>=0; i--){
					List list2 = new ArrayList();
					Map map4 = null;
					for(int j = 0; j < ipList.size(); j++){
						if(tsLit.get(i).getStatus()==ipList.get(j).getStatus()){
							map4 = new HashMap();
							map4.put("statusNum", ipList.get(j).getUrlNum());
							map4.put("ip", ipList.get(j).getUrl());
							list2.add(map4);
						}
					}
					List list3 = new ArrayList();
					int n=0;
					if(list2.size()>50){
						n=50;
					}else{
						n=list2.size();
					}
					
					for(int j=n-1;j>=0;j--){
						list3.add(list2.get(j));
					}
					map5.put(tsLit.get(i).getStatus(), list3);
				}
				/*
				 * 查询所有状态（２00,400...）下status和statusNum
				 */
				List<TimeSlot> statusList = sd.getByStatusMonth(startTime, endTime, client_id, project_id);
				List timeList = DateUtils.getBetweenDate(startTime, endTime);
				Map map6 = new HashMap();
				if(tsLit.size()!=0){
					for(int i = tsLit.size()-1; i>=0; i--){
						
						List list2 = new ArrayList();
						Map map4 = null;
						for (int n = 0; n < timeList.size(); n++) {
							map4 = new HashMap();
							long t = (long) timeList.get(n);
							long m= 0;
							long pvNum=0l;
							for(int j = 0; j < statusList.size(); j++){
								if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
									m=t;
									pvNum=statusList.get(j).getPvNum();
								}
							}
							if(m!=0){
								map4.put("time", DateUtils.timeToString(m, "4"));
								map4.put("pvNum", pvNum);
							}else{
								map4.put("time", DateUtils.timeToString(t, "4"));
								map4.put("pvNum", "0");
							}
							list2.add(map4);
						}
						
						map6.put(tsLit.get(i).getStatus(), list2);
					}
				}else{
						List list2 = new ArrayList();
						for (int n = 0; n < timeList.size(); n++) {
							Map map4 =  new HashMap();
							map4.put("time", DateUtils.timeToString((long) timeList.get(n), "4"));
							map4.put("pvNum", 0);
							list2.add(map4);
						}
						map6.put("", list2);
				}
				
				map1.put("allStatusType", map6);
				map1.put("IpStatusType", map5);
				map1.put("urlStatusType", map3);
				map1.put("timeSlot", list1);
				map1.put("startTime", startTime);
				map1.put("endTime", endTime);
			}
		} else if (dateType.equals("twoYear")) {
			if (chartType.equals("vbarplot")) {
				List list1 = new ArrayList();
				List<TimeSlot> tsLit = sd.getYearStatus(startTime, endTime, client_id, project_id);
				Map map2 = null;
				for (int i = 0; i < tsLit.size(); i++) {
					map2 = new HashMap();
					map2.put("status", tsLit.get(i).getStatus());
					map2.put("statusNum", tsLit.get(i).getStatusNum());
					list1.add(map2);
				}
				/*
				 * 查询所有状态（２00,400...）下url和statusNum
				 */
				List<TimeSlot> urlList = sd.getUrlByStatusYear(startTime, endTime, client_id, project_id);
				Map map3 = new HashMap();
				for(int i = tsLit.size()-1; i>=0; i--){
					List list2 = new ArrayList();
					Map map4 = null;
					for(int j = 0; j < urlList.size(); j++){
						if(tsLit.get(i).getStatus()==urlList.get(j).getStatus()){
							map4 = new HashMap();
							map4.put("statusNum", urlList.get(j).getUrlNum());
							map4.put("url", urlList.get(j).getUrl());
							list2.add(map4);
						}
					}
					List list3 = new ArrayList();
					Map map5 = null;
					int n=0;
					if(list2.size()>50){
						n=50;
					}else{
						n=list2.size();
					}
					
					for(int j=n-1;j>=0;j--){
						list3.add(list2.get(j));
					}
					map3.put(tsLit.get(i).getStatus(), list3);
				}
				/*
				 * 查询所有状态（２00,400...）下ip和statusNum
				 */
				List<TimeSlot> ipList = sd.getIpByStatusYear(startTime, endTime, client_id, project_id);
				Map map5 = new HashMap();
				for(int i = tsLit.size()-1; i>=0; i--){
					List list2 = new ArrayList();
					Map map4 = null;
					for(int j = 0; j < ipList.size(); j++){
						if(tsLit.get(i).getStatus()==ipList.get(j).getStatus()){
							map4 = new HashMap();
							map4.put("statusNum", ipList.get(j).getUrlNum());
							map4.put("ip", ipList.get(j).getUrl());
							list2.add(map4);
						}
					}
					List list3 = new ArrayList();
					int n=0;
					if(list2.size()>50){
						n=50;
					}else{
						n=list2.size();
					}
					
					for(int j=n-1;j>=0;j--){
						list3.add(list2.get(j));
					}
					map5.put(tsLit.get(i).getStatus(), list3);
				}
				/*
				 * 查询所有状态（２00,400...）下status和statusNum
				 */
				List<TimeSlot> statusList = sd.getByStatusYear(startTime, endTime, client_id, project_id);
				List timeList = DateUtils.getBetweenDate(startTime, endTime);
				Map map6 = new HashMap();
				if(tsLit.size()!=0){
					for(int i = tsLit.size()-1; i>=0; i--){
						
						List list2 = new ArrayList();
						Map map4 = null;
						for (int n = 0; n < timeList.size(); n++) {
							map4 = new HashMap();
							long t = (long) timeList.get(n);
							long m= 0;
							long pvNum=0l;
							for(int j = 0; j < statusList.size(); j++){
								if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
									m=t;
									pvNum=statusList.get(j).getPvNum();
								}
							}
							if(m!=0){
								map4.put("time", DateUtils.timeToString(m, "5"));
								map4.put("pvNum", pvNum);
							}else{
								map4.put("time", DateUtils.timeToString(t, "5"));
								map4.put("pvNum", 0);
							}
							list2.add(map4);
						}
						
						map6.put(tsLit.get(i).getStatus(), list2);
					}
				}else{
						List list2 = new ArrayList();
						for (int n = 0; n < timeList.size(); n++) {
							Map map4 =  new HashMap();
							map4.put("time", DateUtils.timeToString((long) timeList.get(n), "5"));
							map4.put("pvNum", 0);
							list2.add(map4);
						}
						map6.put("", list2);
				}
				
				map1.put("allStatusType", map6);
				map1.put("IpStatusType", map5);
				map1.put("urlStatusType", map3);
				map1.put("timeSlot", list1);
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