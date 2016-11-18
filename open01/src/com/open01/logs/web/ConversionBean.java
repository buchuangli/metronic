package com.open01.logs.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.open01.logs.db.ConversionDatabase;
import com.open01.logs.model.Conversion;
import com.open01.logs.model.TimeSlot;
import com.open01.logs.util.DateUtils;
import com.open01.logs.util.JsonUtils;
import com.open01.logs.util.UrlUtil;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class ConversionBean extends WholePartBean implements BooleanCondition {

	public ConversionBean() {
		super("cmd");
		addPart(null, new Part0());//
		addPart("WEL:GETCVRINFO", new Part26());//获取cur 初始页面信息
		addPart("WEL:INSERTCVRINFO", new Part27());//
		addPart("WEL:UPDATECVRINFO", new Part28());//
		
		
		addPart("WEL:GETPVYEAR", new Part1());//获取转化率年时间轨迹
		addPart("WEL:GETPVMONTH", new Part2());//
		addPart("WEL:GETPVDAILY", new Part3());//
		addPart("WEL:GETPVHOUR", new Part4());//
		addPart("WEL:GETPVMINUTE", new Part5());//
		
		addPart("WEL:GETREFERRERYEAR", new Part６());//获取转化率年访问来源
		addPart("WEL:GETREFERRMONTH", new Part7());//
		addPart("WEL:GETREFERRDAILY", new Part8());//
		addPart("WEL:GETREFERRHOUR", new Part9());//
		addPart("WEL:GETREFERRMINUTE", new Part10());//
		
		addPart("WEL:GETBROWSERYEAR", new Part11());//获取浏览器年访问来源
		addPart("WEL:GETBROWSERMONTH", new Part12());//
		addPart("WEL:GETBROWSERDAILY", new Part13());//
		addPart("WEL:GETBROWSERHOUR", new Part14());//
		addPart("WEL:GETBROWSERMINUTE", new Part15());//
		
		addPart("WEL:GETOSYEAR", new Part16());//获取操作系统年访问来源
		addPart("WEL:GETOSMONTH", new Part17());//
		addPart("WEL:GETOSDAILY", new Part18());//
		addPart("WEL:GETOSHOUR", new Part19());//
		addPart("WEL:GETOSMINUTE", new Part20());//
		
		addPart("WEL:GETGEYEAR", new Part21());//获取访问来源地域分析
		addPart("WEL:GETGEMONTH", new Part22());//
		addPart("WEL:GETGEDAILY", new Part23());//
		addPart("WEL:GETGEHOUR", new Part24());//
		addPart("WEL:GETGEMINUTE", new Part25());//
		
		
		addPart("WEL:SELECTYEAR", new Part31());// 获取年表信息
		addPart("WEL:SELECTMONTH", new Part32());// 获取月表信息
		addPart("WEL:SELECTDAILY", new Part33());// 获取天表信息
		addPart("WEL:SELECTHOUR", new Part34());// 获取小时表信息
		addPart("WEL:SELECTMINUTE", new Part35());// 获取分钟,小时,日,月,年表信息
		  
	}

	@Override
	public boolean isTrue() {
		return true;
	}
	ConversionDatabase cd  = new ConversionDatabase ();
	private final class Part0 extends DefaultPlainBean {
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
		//	String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			/*long startTime=0;
			long endTime=0;*/
			String urls[]= {"/Apply/Index","/"};
			long startTime=(long) DateUtils.getInitDate("hour").get(0);
			long endTime=(long) DateUtils.getInitDate("hour").get(1);
			/*if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}*/
			
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for(int i =0;i<urls.length;i++){
				//urlList = cd.getDailyStatus(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				List<TimeSlot> urlList = cd.getHourStatus(startTime, endTime, client_id, project_id,urls[i]);
				List list1 = new ArrayList();
				List list2 = new ArrayList();
				Map map2 = null;
				
				for (int m = 0; m < timeList.size(); m++) {
					map2 = new HashMap();
					long newtime = (long) timeList.get(m);
					long oldtime= 0;
					long pvNum=0l;
					for(int j = 0; j <urlList.size(); j++){
						if(newtime==urlList.get(j).getTime()){
							oldtime=newtime;
							pvNum=urlList.get(j).getPvNum();
							continue;
						}
					}
					if(oldtime!=0){
						//map2.put("time", DateUtils.timeToString(o, "3"));
						//map2.put("pvNum", pvNum);
						list1.add(pvNum);
					}else{
						//map2.put("time", DateUtils.timeToString(t, "3"));
						//map2.put("pvNum", 0);
						list1.add(0);
					}	
						
					list2.add(DateUtils.timeToString(newtime, "2"));
					//list1.add(map2);
				}
				map1.put("time",list2);
				map1.put(urls[i], list1);
			}
			
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
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
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getYearStatus(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
			//	List<TimeSlot> urlList = cd.getYearStatus(startTime, endTime, client_id, project_id,urls[i]);
				List list1 = new ArrayList();
				List list2 = new ArrayList();
				Map map2 = null;
				
				for (int m = 0; m < timeList.size(); m++) {
					map2 = new HashMap();
					long newtime = (long) timeList.get(m);
					long oldtime= 0;
					long pvNum=0l;
					for(int j = 0; j <urlList.size(); j++){
						if(newtime==urlList.get(j).getTime()){
							oldtime=newtime;
							pvNum=urlList.get(j).getPvNum();
							continue;
						}
					}
					if(oldtime!=0){
						list1.add(pvNum);
					}else{
						list1.add(0);
					}	
						
					list2.add(DateUtils.timeToString(newtime, "5"));
				}
				map1.put("time",list2);
				map1.put(urls[i], list1);
			}
			
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"4");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getMonthStatus(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getMonthStatus(startTime, endTime, client_id, project_id,urls[i]);
				List list1 = new ArrayList();
				List list2 = new ArrayList();
				Map map2 = null;
				
				for (int m = 0; m < timeList.size(); m++) {
					map2 = new HashMap();
					long newtime = (long) timeList.get(m);
					long oldtime= 0;
					long pvNum=0l;
					for(int j = 0; j <urlList.size(); j++){
						if(newtime==urlList.get(j).getTime()){
							oldtime=newtime;
							pvNum=urlList.get(j).getPvNum();
							continue;
						}
					}
					if(oldtime!=0){
						list1.add(pvNum);
					}else{
						list1.add(0);
					}	
						
					list2.add(DateUtils.timeToString(newtime, "4"));
				}
				map1.put("time",list2);
				map1.put(urls[i], list1);
			}
			
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st) && !st.equals("NaN")){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			List timeList = DateUtils.getBetweenDate(startTime, endTime,"daily");
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getDailyStatus(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getDailyStatus(startTime, endTime, client_id, project_id,urls[i]);
				List list1 = new ArrayList();
				List list2 = new ArrayList();
				Map map2 = null;
				
				for (int m = 0; m < timeList.size(); m++) {
					map2 = new HashMap();
					long newtime = (long) timeList.get(m);
					long oldtime= 0;
					long pvNum=0l;
					for(int j = 0; j <urlList.size(); j++){
						if(newtime==urlList.get(j).getTime()){
							oldtime=newtime;
							pvNum=urlList.get(j).getPvNum();
							continue;
						}
					}
					if(oldtime!=0){
						list1.add(pvNum);
					}else{
						list1.add(0);
					}	
						
					list2.add(DateUtils.timeToString(newtime, "3"));
				}
				map1.put("time",list2);
				map1.put(urls[i], list1);
			}
			
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			/*if(StringUtils.isNotEmpty(st)){
				startTime=Long.valueOf(st);
				endTime=Long.valueOf(et);
			}else{
				 startTime=(long) DateUtils.getInitDate("hour").get(0);
				 endTime=(long) DateUtils.getInitDate("hour").get(1);
			}*/
			if(StringUtils.isNotEmpty(st) && !st.equals("NaN")){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getHourStatus(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getHourStatus(startTime, endTime, client_id, project_id,urls[i]);
				List list1 = new ArrayList();
				List list2 = new ArrayList();
				Map map2 = null;
				
				for (int m = 0; m < timeList.size(); m++) {
					map2 = new HashMap();
					long newtime = (long) timeList.get(m);
					long oldtime= 0;
					long pvNum=0l;
					for(int j = 0; j <urlList.size(); j++){
						if(newtime==urlList.get(j).getTime()){
							oldtime=newtime;
							pvNum=urlList.get(j).getPvNum();
							continue;
						}
					}
					if(oldtime!=0){
						list1.add(pvNum);
					}else{
						list1.add(0);
					}	
						
					list2.add(DateUtils.timeToString(newtime, "2"));
				}
				map1.put("time",list2);
				map1.put(urls[i], list1);
			}
			
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
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
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			List timeList = DateUtils.getBetweenDate(startTime, endTime);
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getMinuteStatus(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getMinuteStatus(startTime, endTime, client_id, project_id,urls[i]);
				List list1 = new ArrayList();
				List list2 = new ArrayList();
				Map map2 = null;
				
				for (int m = 0; m < timeList.size(); m++) {
					map2 = new HashMap();
					long newtime = (long) timeList.get(m);
					long oldtime= 0;
					long pvNum=0l;
					for(int j = 0; j <urlList.size(); j++){
						if(newtime==urlList.get(j).getTime()){
							oldtime=newtime;
							pvNum=urlList.get(j).getPvNum();
							continue;
						}
					}
					if(oldtime!=0){
						list1.add(pvNum);
					}else{
						list1.add(0);
					}	
						
					list2.add(DateUtils.timeToString(newtime, "1"));
				}
				map1.put("time",list2);
				map1.put(urls[i], list1);
			}
			
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	
	private final class Part６ extends DefaultPlainBean {
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			/*String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");*/
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			String urls[]= {"/Apply/Index","/"};
			startTime = 2016;
			endTime = 2017;
			/*if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"5");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}*/
			
			List<TimeSlot> urlList = null;
			for(int i =0;i<urls.length;i++){
				urlList = cd.getYearReferrer(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				List list1 = new ArrayList();
				Map map2 = null;
				for(TimeSlot uList:urlList){
					map2 = new HashMap();
					map2.put("time", DateUtils.timeToString(uList.getTime(), "5"));
					map2.put("pvNum", uList.getPvNum());
					list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
			
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
			/*String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");*/
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			String urls[]= {"/Apply/Index","/"};
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
			
			List<TimeSlot> urlList = null;
			for(int i =0;i<urls.length;i++){
				urlList = cd.getMonthReferrer(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				List list1 = new ArrayList();
				Map map2 = null;
				for(TimeSlot uList:urlList){
					map2 = new HashMap();
					map2.put("time", DateUtils.timeToString(uList.getTime(), "4"));
					map2.put("pvNum", uList.getPvNum());
					list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
			
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
			/*String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");*/
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			String urls[]= {"/Apply/Index","/"};
			startTime = 20160301;
			endTime = 20160331;
			/*if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}*/
			
			List<TimeSlot> urlList = null;
			for(int i =0;i<urls.length;i++){
				urlList = cd.getDailyReferrer(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				List list1 = new ArrayList();
				Map map2 = null;
				for(TimeSlot uList:urlList){
					map2 = new HashMap();
					map2.put("time", DateUtils.timeToString(uList.getTime(), "3"));
					map2.put("pvNum", uList.getPvNum());
					list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
			
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
			/*String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");*/
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			String urls[]= {"/Apply/Index","/"};
			startTime = 2016030100;
			endTime = 2016030200;
			/*if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}*/
			
			List<TimeSlot> urlList = null;
			for(int i =0;i<urls.length;i++){
				urlList = cd.getHourReferrer(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				List list1 = new ArrayList();
				Map map2 = null;
				for(TimeSlot uList:urlList){
					map2 = new HashMap();
					map2.put("time", DateUtils.timeToString(uList.getTime(), "2"));
					map2.put("pvNum", uList.getPvNum());
					list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
			
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
			/*String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");*/
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			String urls[]= {"/Apply/Index","/"};
			startTime = 201603010000l;
			endTime = 201603010100l;
			/*if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"1");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}*/
			
			List<TimeSlot> urlList = null;
			for(int i =0;i<urls.length;i++){
				urlList = cd.getMinuteReferrer(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				List list1 = new ArrayList();
				Map map2 = null;
				for(TimeSlot uList:urlList){
					map2 = new HashMap();
					map2.put("time", DateUtils.timeToString(uList.getTime(), "1"));
					map2.put("pvNum", uList.getPvNum());
					list1.add(map2);
				}
				map1.put(urls[i], list1);
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
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
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			List browserList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getYearBrowser(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getYearBrowser(startTime, endTime, client_id, project_id, urls[i]);
				if(urlList !=null){
					browserList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setBrowserNum(0);
					t.setBrowserType("");
					browserList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getBrowserType());
				}
			}
			//取浏览器类型并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<browserList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) browserList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String broType = (String) typeList.get(j);
					String bro="";
					long browserNum=0;
						for(int m =0;m<urlList.size();m++){
							if(broType.equals(urlList.get(m).getBrowserType())){
								browserNum=urlList.get(m).getBrowserNum();
							}
						}
						map2.put("browserType", broType);
						if(browserNum!=0){
							map2.put("browserNum", browserNum);
						}else{
							map2.put("browserNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"4");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			List browserList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getMonthBrowser(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getMonthBrowser(startTime, endTime, client_id, project_id, urls[i]);
				if(urlList !=null){
					browserList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setBrowserNum(0);
					t.setBrowserType("");
					browserList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getBrowserType());
				}
			}
			//取浏览器类型并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<browserList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) browserList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String broType = (String) typeList.get(j);
					String bro="";
					long browserNum=0;
						for(int m =0;m<urlList.size();m++){
							if(broType.equals(urlList.get(m).getBrowserType())){
								browserNum=urlList.get(m).getBrowserNum();
							}
						}
						map2.put("browserType", broType);
						if(browserNum!=0){
							map2.put("browserNum", browserNum);
						}else{
							map2.put("browserNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st) && !st.equals("NaN")){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			
			List browserList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getDailyBrowser(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getDailyBrowser(startTime, endTime, client_id, project_id, urls[i]);
				if(urlList !=null){
					browserList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setBrowserNum(0);
					t.setBrowserType("");
					browserList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getBrowserType());
				}
			}
			//取浏览器类型并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<browserList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) browserList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String broType = (String) typeList.get(j);
					String bro="";
					long browserNum=0;
						for(int m =0;m<urlList.size();m++){
							if(broType.equals(urlList.get(m).getBrowserType())){
								browserNum=urlList.get(m).getBrowserNum();
							}
						}
						map2.put("browserType", broType);
						if(browserNum!=0){
							map2.put("browserNum", browserNum);
						}else{
							map2.put("browserNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			List browserList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getHourBrowser(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getHourBrowser(startTime, endTime, client_id, project_id, urls[i]);
				if(urlList !=null){
					browserList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setBrowserNum(0);
					t.setBrowserType("");
					browserList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getBrowserType());
				}
			}
			//取浏览器类型并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<browserList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) browserList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String broType = (String) typeList.get(j);
					String bro="";
					long browserNum=0;
						for(int m =0;m<urlList.size();m++){
							if(broType.equals(urlList.get(m).getBrowserType())){
								browserNum=urlList.get(m).getBrowserNum();
							}
						}
						map2.put("browserType", broType);
						if(browserNum!=0){
							map2.put("browserNum", browserNum);
						}else{
							map2.put("browserNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
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
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			List browserList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getMinuteBrowser(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getMinuteBrowser(startTime, endTime, client_id, project_id, urls[i]);
				if(urlList !=null){
					browserList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setBrowserNum(0);
					t.setBrowserType("");
					browserList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getBrowserType());
				}
			}
			//取浏览器类型并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<browserList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) browserList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String broType = (String) typeList.get(j);
					String bro="";
					long browserNum=0;
						for(int m =0;m<urlList.size();m++){
							if(broType.equals(urlList.get(m).getBrowserType())){
								browserNum=urlList.get(m).getBrowserNum();
							}
						}
						map2.put("browserType", broType);
						if(browserNum!=0){
							map2.put("browserNum", browserNum);
						}else{
							map2.put("browserNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
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
			
			List osList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getYearOs(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getYearOs(startTime, endTime, client_id, project_id, urls[i]);
				if(urlList !=null){
					osList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setOsNum(0);
					t.setOsType("");
					osList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getOsType());
				}
			}
			//取操作系统类型并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<osList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) osList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String osType = (String) typeList.get(j);
					long osNum=0;
						for(int m =0;m<urlList.size();m++){
							if(osType.equals(urlList.get(m).getOsType())){
								osNum=urlList.get(m).getOsNum();
							}
						}
						map2.put("osType", osType);
						if(osNum!=0){
							map2.put("osNum", osNum);
						}else{
							map2.put("osNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st)){
			 startTime = Long.valueOf(st);
			 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"4");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			
			List osList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getMonthOs(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
			//	List<TimeSlot> urlList = cd.getMonthOs(startTime, endTime, client_id, project_id,urls[i]);
				if(urlList !=null){
					osList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setOsNum(0);
					t.setOsType("");
					osList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getOsType());
				}
			}
			//取操作系统类型并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<osList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) osList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String osType = (String) typeList.get(j);
					long osNum=0;
						for(int m =0;m<urlList.size();m++){
							if(osType.equals(urlList.get(m).getOsType())){
								osNum=urlList.get(m).getOsNum();
							}
						}
						map2.put("osType", osType);
						if(osNum!=0){
							map2.put("osNum", osNum);
						}else{
							map2.put("osNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st) && !st.equals("NaN")){
			 startTime = Long.valueOf(st);
			 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			List osList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getDailyOs(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getDailyOs(startTime, endTime, client_id, project_id, urls[i]);
				if(urlList !=null){
					osList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setOsNum(0);
					t.setOsType("");
					osList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getOsType());
				}
			}
			//取操作系统类型并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<osList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) osList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String osType = (String) typeList.get(j);
					long osNum=0;
						for(int m =0;m<urlList.size();m++){
							if(osType.equals(urlList.get(m).getOsType())){
								osNum=urlList.get(m).getOsNum();
							}
						}
						map2.put("osType", osType);
						if(osNum!=0){
							map2.put("osNum", osNum);
						}else{
							map2.put("osNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st)){
			 startTime = Long.valueOf(st);
			 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			List osList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getHourOs(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getHourOs(startTime, endTime, client_id, project_id,urls[i]);
				if(urlList !=null){
					osList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setOsNum(0);
					t.setOsType("");
					osList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getOsType());
				}
			}
			//取操作系统类型并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<osList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) osList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String osType = (String) typeList.get(j);
					long osNum=0;
						for(int m =0;m<urlList.size();m++){
							if(osType.equals(urlList.get(m).getOsType())){
								osNum=urlList.get(m).getOsNum();
							}
						}
						map2.put("osType", osType);
						if(osNum!=0){
							map2.put("osNum", osNum);
						}else{
							map2.put("osNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
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
			String[] urls = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
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
			
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			
			List osList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getMinuteOs(startTime, endTime, client_id, project_id, UrlUtil.getUrl(urls[i]));
				//List<TimeSlot> urlList = cd.getMinuteOs(startTime, endTime, client_id, project_id, urls[i]);
				if(urlList !=null){
					osList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setOsNum(0);
					t.setOsType("");
					osList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getOsType());
				}
			}
			//取操作系统类型并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<osList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) osList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String osType = (String) typeList.get(j);
					long osNum=0;
						for(int m =0;m<urlList.size();m++){
							if(osType.equals(urlList.get(m).getOsType())){
								osNum=urlList.get(m).getOsNum();
							}
						}
						map2.put("osType", osType);
						if(osNum!=0){
							map2.put("osNum", osNum);
						}else{
							map2.put("osNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
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
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
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
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
		/*	List<TimeSlot> urlList = null;
			for(int i =0;i<urls.length;i++){
				urlList = cd.getYearGe(startTime, endTime, client_id, project_id,"中国",UrlUtil.getUrl(urls[i]));
				//urlList = cd.getYearGe(startTime, endTime, client_id, project_id,"中国",urls[i]);
				List list1 = new ArrayList();
				Map map2 = null;
				for(TimeSlot uList:urlList){
					map2 = new HashMap();
					map2.put("province", uList.getProvince());
					map2.put("Num", uList.getPvNum());
					list1.add(map2);
				}
				map1.put(urls[i], list1);
			}*/
			
			List geList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getYearGe(startTime, endTime, client_id, project_id,"中国",UrlUtil.getUrl(urls[i]));
				if(urlList !=null){
					geList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setPvNum(0);
					t.setProvince("");
					typeList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getProvince());
				}
			}
			//取省份信息并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<geList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) geList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String geType = (String) typeList.get(j);
					long geNum=0;
						for(int m =0;m<urlList.size();m++){
							if(geType.equals(urlList.get(m).getProvince())){
								geNum=urlList.get(m).getPvNum();
							}
						}
						map2.put("geType", geType);
						if(geNum!=0){
							map2.put("geNum", geNum);
						}else{
							map2.put("geNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	
	private final class Part22 extends DefaultPlainBean {
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"4");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
			/*List<TimeSlot> urlList = null;
			for(int i =0;i<urls.length;i++){
				urlList = cd.getMonthGe(startTime, endTime, client_id, project_id,"中国",UrlUtil.getUrl(urls[i]));
				//urlList = cd.getMonthGe(startTime, endTime, client_id, project_id,"中国",urls[i]);
				List list1 = new ArrayList();
				Map map2 = null;
				for(TimeSlot uList:urlList){
					map2 = new HashMap();
					map2.put("province", uList.getProvince());
					map2.put("Num", uList.getPvNum());
					list1.add(map2);
				}
				map1.put(urls[i], list1);
			}*/
			List geList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getMonthGe(startTime, endTime, client_id, project_id,"中国",UrlUtil.getUrl(urls[i]));
				if(urlList !=null){
					geList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setPvNum(0);
					t.setProvince("");
					typeList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getProvince());
				}
			}
			//取省份信息并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<geList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) geList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String geType = (String) typeList.get(j);
					long geNum=0;
						for(int m =0;m<urlList.size();m++){
							if(geType.equals(urlList.get(m).getProvince())){
								geNum=urlList.get(m).getPvNum();
							}
						}
						map2.put("geType", geType);
						if(geNum!=0){
							map2.put("geNum", geNum);
						}else{
							map2.put("geNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	
	private final class Part23 extends DefaultPlainBean {
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			
			if(StringUtils.isNotEmpty(st) && !st.equals("NaN")){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
	/*		List<TimeSlot> urlList = null;
			for(int i =0;i<urls.length;i++){
				urlList = cd.getDailyGe(startTime, endTime, client_id, project_id,"中国",UrlUtil.getUrl(urls[i]));
				//urlList = cd.getDailyGe(startTime, endTime, client_id, project_id,"中国",urls[i]);
				List list1 = new ArrayList();
				Map map2 = null;
				for(TimeSlot uList:urlList){
					map2 = new HashMap();
					map2.put("province", uList.getProvince());
					map2.put("Num", uList.getPvNum());
					list1.add(map2);
				}
				map1.put(urls[i], list1);
			}*/
			
			List geList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getDailyGe(startTime, endTime, client_id, project_id,"中国",UrlUtil.getUrl(urls[i]));
				if(urlList !=null){
					geList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setPvNum(0);
					t.setProvince("");
					typeList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getProvince());
				}
			}
			//取省份信息并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<geList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) geList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String geType = (String) typeList.get(j);
					long geNum=0;
						for(int m =0;m<urlList.size();m++){
							if(geType.equals(urlList.get(m).getProvince())){
								geNum=urlList.get(m).getPvNum();
							}
						}
						map2.put("geType", geType);
						if(geNum!=0){
							map2.put("geNum", geNum);
						}else{
							map2.put("geNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	
	private final class Part24 extends DefaultPlainBean {
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
		/*	List<TimeSlot> urlList = null;
			for(int i =0;i<urls.length;i++){
				urlList = cd.getHourGe(startTime, endTime, client_id, project_id,"中国",UrlUtil.getUrl(urls[i]));
				//urlList = cd.getHourGe(startTime, endTime, client_id, project_id,"中国",urls[i]);
				List list1 = new ArrayList();
				Map map2 = null;
				for(TimeSlot uList:urlList){
					map2 = new HashMap();
					map2.put("province", uList.getProvince());
					map2.put("Num", uList.getPvNum());
					list1.add(map2);
				}
				map1.put(urls[i], list1);
			}*/
			
			
			List geList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getHourGe(startTime, endTime, client_id, project_id,"中国",UrlUtil.getUrl(urls[i]));
				if(urlList !=null){
					geList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setPvNum(0);
					t.setProvince("");
					typeList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getProvince());
				}
			}
			//取省份信息并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<geList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) geList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String geType = (String) typeList.get(j);
					long geNum=0;
						for(int m =0;m<urlList.size();m++){
							if(geType.equals(urlList.get(m).getProvince())){
								geNum=urlList.get(m).getPvNum();
							}
						}
						map2.put("geType", geType);
						if(geNum!=0){
							map2.put("geNum", geNum);
						}else{
							map2.put("geNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}
	
	private final class Part25 extends DefaultPlainBean {
		Map map1 = new HashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
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
			/*如果urls为空，则自己去查询urls*/
			if (urls == null){
				Conversion conversion = new Conversion();
				int user_id=0;
				if(session.getAttribute("user_id") != null){
					user_id =(int) session.getAttribute("user_id");
				}
				conversion.setProject_id(Integer.valueOf(project_id));
				conversion.setUser_id(user_id);
				List<String> u = cd.getCvrUrlList(conversion);
				urls = (String[]) u.toArray(new String[u.size()]);
			}
		/*	List<TimeSlot> urlList = null;
			for(int i =0;i<urls.length;i++){
				urlList = cd.getMinuteGe(startTime, endTime, client_id, project_id,"中国",UrlUtil.getUrl(urls[i]));
				//urlList = cd.getMinuteGe(startTime, endTime, client_id, project_id,"中国",urls[i]);
				List list1 = new ArrayList();
				Map map2 = null;
				for(TimeSlot uList:urlList){
					map2 = new HashMap();
					map2.put("province", uList.getProvince());
					map2.put("Num", uList.getPvNum());
					list1.add(map2);
				}
				map1.put(urls[i], list1);
			}*/
			
			List geList = new ArrayList();
			List typeList  = new  ArrayList<String>();
			for(int i =0;i<urls.length;i++){
				List<TimeSlot> urlList = cd.getMinuteGe(startTime, endTime, client_id, project_id,"中国",UrlUtil.getUrl(urls[i]));
				if(urlList !=null){
					geList.add(urlList);
				}else{
					TimeSlot t = new TimeSlot();
					t.setPvNum(0);
					t.setProvince("");
					typeList.add(t);
				}
				for(TimeSlot uList:urlList){
					typeList.add(uList.getProvince());
				}
			}
			//取省份信息并集
			typeList=UrlUtil.removeDuplicates(typeList);
			for(int i =0;i<geList.size();i++){
				List<TimeSlot> urlList = (List<TimeSlot>) geList.get(i);
				List list1 = new ArrayList();
				for(int j=0;j<typeList.size();j++){
					Map map2 = new HashMap();
					String geType = (String) typeList.get(j);
					long geNum=0;
						for(int m =0;m<urlList.size();m++){
							if(geType.equals(urlList.get(m).getProvince())){
								geNum=urlList.get(m).getPvNum();
							}
						}
						map2.put("geType", geType);
						if(geNum!=0){
							map2.put("geNum", geNum);
						}else{
							map2.put("geNum", 0);
						}
						list1.add(map2);
				}
				map1.put(urls[i], list1);
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}

	
	
	
	private final class Part26 extends DefaultPlainBean {
		List list =new ArrayList();
		@Override
		protected boolean doConditionIsTrue() {
			Conversion conversion = new Conversion();
			String project_id = request.getParameter("project_id");
			int user_id=0;
			if(session.getAttribute("user_id") != null){
				user_id =(int) session.getAttribute("user_id");
			}
			int pId=0;
			if(StringUtils.isNotEmpty(project_id)){
				pId=Integer.valueOf(project_id);
				conversion.setProject_id(pId);
			}
			conversion.setUser_id(user_id);
			List<Conversion> cList = cd.getCvrInfo(conversion);
			
			int client_id=0;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			long startTime=(long) DateUtils.getInitDate("day").get(0);
			long endTime=(long) DateUtils.getInitDate("day").get(1);
			for(Conversion c:cList){
				Map m = new HashMap();
				Long pvCount = cd.getDailySumPageView(startTime, endTime, client_id, pId, UrlUtil.getUrl(c.getUrl()));
				m.put("pvCount", pvCount);
				m.put("url",c.getUrl());
				m.put("desc",c.getDescription());
				list.add(m);
			}
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(list, response));
			list.clear();
		}
	}
	
	private final class Part27 extends DefaultPlainBean {
		//Conversion conversion = new Conversion();
		List list = new ArrayList();
		@Override
		protected boolean doConditionIsTrue() {
			String project_id = request.getParameter("project_id");
			String url []= request.getParameterValues("url[]");
			String desc [] = request.getParameterValues("desc[]");
			int user_id = (int)session.getAttribute("user_id");
			
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			
			int pId=0;
			if(StringUtils.isNotEmpty(project_id)){
				pId = Integer.valueOf(project_id);
			}
			cd.deleteCvrInfo(user_id, pId);
			if(url !=null){
				for(int i =0;i<url.length;i++){
					Conversion c = new Conversion();
					c.setUser_id(user_id);
					c.setClient_id(client_id);
					c.setProject_id(pId);
					c.setUrl(url[i]);	
					c.setDescription(desc[i]);
					
					Map m = new HashMap();
					m.put("url",url[i]);
					m.put("desc",desc[i]);
					list.add(m);
					
					cd.insertCvrInfo(c);
				}
			}
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(list, response));
			list.clear();
		}
	}
	
	private final class Part28 extends DefaultPlainBean {
		Conversion conversion = new Conversion();
		@Override
		protected boolean doConditionIsTrue() {
			String project_id = request.getParameter("project_id");
			String url = request.getParameter("url");
			String desc = request.getParameter("desc");
			
			conversion.setUser_id(1);
			//conversion.setClient_id(11);
			if(StringUtils.isNotEmpty(project_id)){
				conversion.setProject_id(Integer.valueOf(project_id));
			}
			if(StringUtils.isNotEmpty(url)){
				conversion.setUrl(url);		
			}
			if(StringUtils.isNotEmpty(desc)){
				conversion.setDescription(desc);
			}
			conversion= cd.updateCvrInfo(conversion);
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(conversion, response));
		}
	}
	
	
	private final class Part31 extends DefaultPlainBean {
		Map map1 = new LinkedHashMap();
		@Override
		protected boolean doConditionIsTrue() {
			String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
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
			/*
			 * 获取urls
			 */
			Conversion conversion = new Conversion();
			int user_id=0;
			if(session.getAttribute("user_id") != null){
				user_id =(int) session.getAttribute("user_id");
			}
			conversion.setProject_id(Integer.valueOf(project_id));
			conversion.setUser_id(user_id);
			List<Conversion> cList = cd.getCvrInfo(conversion);
			Map map2 =new LinkedHashMap();
			for(Conversion c:cList){
				Long pvCount = cd.getYearSumPageView(startTime, endTime, client_id, project_id, UrlUtil.getUrl(c.getUrl()));
			//	Long pvCount = cd.getYearSumPageView(startTime, endTime, client_id, project_id, c.getUrl());
				map2.put(c.getUrl(), pvCount);
			}
			map1.put("funnelInfo", map2);
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
	private final class Part32 extends DefaultPlainBean {
		Map map1 = new LinkedHashMap();
		@Override
		protected boolean doConditionIsTrue() {
		//	String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"4");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			
			/*
			 * 获取urls
			 */
			Conversion conversion = new Conversion();
			int user_id=0;
			if(session.getAttribute("user_id") != null){
				user_id =(int) session.getAttribute("user_id");
			}
			conversion.setProject_id(Integer.valueOf(project_id));
			conversion.setUser_id(user_id);
			List<Conversion> cList = cd.getCvrInfo(conversion);
			Map map2 =new LinkedHashMap();
			for(Conversion c:cList){
				Long pvCount = cd.getMonthSumPageView(startTime, endTime, client_id, project_id, UrlUtil.getUrl(c.getUrl()));
		//		Long pvCount = cd.getMonthSumPageView(startTime, endTime, client_id, project_id, c.getUrl());
				map2.put(c.getUrl(), pvCount);
			}
			map1.put("funnelInfo", map2);
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
	private final class Part33 extends DefaultPlainBean {
		Map map1 = new LinkedHashMap();
		@Override
		protected boolean doConditionIsTrue() {
		//	String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"3");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			
			/*
			 * 获取urls
			 */
			Conversion conversion = new Conversion();
			int user_id=0;
			if(session.getAttribute("user_id") != null){
				user_id =(int) session.getAttribute("user_id");
			}
			conversion.setProject_id(Integer.valueOf(project_id));
			conversion.setUser_id(user_id);
			List<Conversion> cList = cd.getCvrInfo(conversion);
			Map map2 =new LinkedHashMap();
			for(Conversion c:cList){
				Long pvCount = cd.getDailySumPageView(startTime, endTime, client_id, project_id, UrlUtil.getUrl(c.getUrl()));
				//Long pvCount = cd.getDailySumPageView(startTime, endTime, client_id, project_id, c.getUrl());
				map2.put(c.getUrl(), pvCount);
			}
			map1.put("funnelInfo", map2);
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
	private final class Part34 extends DefaultPlainBean {
		Map map1 = new LinkedHashMap();
		@Override
		protected boolean doConditionIsTrue() {
		//	String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
			}
			long startTime=0;
			long endTime=0;
			if(StringUtils.isNotEmpty(st)){
				 startTime = Long.valueOf(st);
				 endTime =  Long.valueOf(et);
			}else{
				HashMap<String,Long> dayMap = DateUtils.getDay(dt,"2");
				startTime = dayMap.get("firstT");
				endTime = dayMap.get("nextT");
			}
			
			/*
			 * 获取urls
			 */
			Conversion conversion = new Conversion();
			int user_id=0;
			if(session.getAttribute("user_id") != null){
				user_id =(int) session.getAttribute("user_id");
			}
			conversion.setProject_id(Integer.valueOf(project_id));
			conversion.setUser_id(user_id);
			List<Conversion> cList = cd.getCvrInfo(conversion);
			Map map2 =new LinkedHashMap();
			for(Conversion c:cList){
				Long pvCount = cd.getHourSumPageView(startTime, endTime, client_id, project_id, UrlUtil.getUrl(c.getUrl()));
				//Long pvCount = cd.getHourSumPageView(startTime, endTime, client_id, project_id, c.getUrl());
				map2.put(c.getUrl(), pvCount);
			}
			map1.put("funnelInfo", map2);
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
	private final class Part35 extends DefaultPlainBean {
		Map map1 = new LinkedHashMap();
		@Override
		protected boolean doConditionIsTrue() {
		//	String urls[] = request.getParameterValues("urls[]"); 
			String dt = request.getParameter("date");
			String st = request.getParameter("startTime");
			String et = request.getParameter("endTime");
			int client_id=1;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			int project_id=0;
			String pId= request.getParameter("project_id");
			if(StringUtils.isNotEmpty(pId)){
				project_id=Integer.valueOf(pId);
			}else{
				project_id = 1001;
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
			
			/*
			 * 获取urls
			 */
			Conversion conversion = new Conversion();
			int user_id=0;
			if(session.getAttribute("user_id") != null){
				user_id =(int) session.getAttribute("user_id");
			}
			conversion.setProject_id(Integer.valueOf(project_id));
			conversion.setUser_id(user_id);
			List<Conversion> cList = cd.getCvrInfo(conversion);
			Map map2 =new LinkedHashMap();
			for(Conversion c:cList){
				Long pvCount = cd.getMinuteSumPageView(startTime, endTime, client_id, project_id, UrlUtil.getUrl(c.getUrl()));
				//Long pvCount = cd.getMinuteSumPageView(startTime, endTime, client_id, project_id, c.getUrl());
				map2.put(c.getUrl(), pvCount);
			}
			map1.put("funnelInfo", map2);
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
}
