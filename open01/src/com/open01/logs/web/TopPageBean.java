package com.open01.logs.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.open01.logs.db.AnalysisDatabase;
import com.open01.logs.db.BrowserDatabase;
import com.open01.logs.db.DataCountDatabase;
import com.open01.logs.db.IpCountDatabase;
import com.open01.logs.db.LoginDatabase;
import com.open01.logs.db.OperateSystemDatabase;
import com.open01.logs.db.PageViewDatabase;
import com.open01.logs.db.ProjectsDatabase;
import com.open01.logs.db.RefererDatabase;
import com.open01.logs.db.RequestTypeDatabase;
import com.open01.logs.db.StatusDatabase;
import com.open01.logs.db.StayTimeDatabase;
import com.open01.logs.db.TopPageDatabase;
import com.open01.logs.model.TimeSlot;
import com.open01.logs.model.TopPage;
import com.open01.logs.util.DateUtils;
import com.open01.logs.util.JsonUtils;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;

public class TopPageBean extends WholePartBean implements BooleanCondition {

	public TopPageBean() {
		super("cmd");
		addPart(null, new Part0());//
		addPart("WEL:SELECTTOPINFO", new Part1());//
		addPart("WEL:GETTOPLIST", new Part2());//
		addPart("WEL:GETPROJECTID", new Part3());//
		
		addPart("WEL:UPDATEPROJECTID", new Part4());//
	}
 
	@Override
	public boolean isTrue() {
		return true;
	}

	TopPageDatabase tpd = new TopPageDatabase();
	/*
	 * 遍历首页信息
	 */

	private final class Part0 extends DefaultPlainBean {

		@Override
		protected boolean doConditionIsTrue() {
			String reportTypes[] = request.getParameterValues("storageArr[]");
			TopPage tp = new TopPage();
			HttpSession session = request.getSession();
			int client_id=0;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			
			tp.setProject_id(11);
			tp.setClient_id(client_id);
			String str = "";

			if (reportTypes != null) {
				for (int i = 0; i < reportTypes.length; i++) {
					str += reportTypes[i] + ",";
				}
				if (StringUtils.isNotEmpty(str)) {
					str = str.substring(0, str.length() - 1);
				}
				tp.setReportType(str);
				tpd.deleteTopPageInfo(tp);
				tpd.addTopPageInfo(tp);
			} else {
				tpd.deleteTopPageInfo(tp);
			}

			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			/*
			 * writer.print(JsonUtils.objectToJson(map1, response));
			 * map1.clear();
			 */
		}
	}

	private final class Part1 extends DefaultPlainBean {
		Map map1 = new HashMap();

		@Override
		protected boolean doConditionIsTrue() {
			HttpSession session = request.getSession();
			int client_id=0;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}
			String allCharts = tpd.selectTopPageInfo(client_id);
			map1.put("reportType", allCharts);

			return true;
		}

		@Override
		protected void writeConditionIsTrueOutput() {
			writer.print(JsonUtils.objectToJson(map1, response));
			map1.clear();
		}
	}

	// http://localhost:8080/open01/toppage.open?cmd=WEL:GETTOPLIST&date=20160208&leftType=PV&dateType=week&chartType=line
	// http://localhost:8080/open01/toppage.open?cmd=WEL:GETTOPLIST&date=20160308&leftType=OS&dateType=week&chartType=area
	private final class Part2 extends DefaultPlainBean {
		Map map1 = new HashMap();

		@Override
		protected boolean doConditionIsTrue() {
			String selectProId = request.getParameter("selectProId");
			int selectId = 0;
			if(StringUtils.isNotEmpty(selectProId)){
				selectId = Integer.valueOf(selectProId);
			}
			
			
			String date = request.getParameter("date");
			String leftType = "";
			String dateType = "";
			String chartType = "";	
			int project_id=0;
			if(StringUtils.isEmpty(date)){
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				date=sdf.format(d);
				//date = "20160401";
			}
			HttpSession session = request.getSession();
			int client_id=0;
			if(session.getAttribute("client_id") != null){
				client_id =(int) session.getAttribute("client_id");
			}

			String allCharts = tpd.selectTopPageInfo(client_id);
			if (StringUtils.isNotEmpty(allCharts)) {
				String chartIfo[] = allCharts.split(",");
				
				List sList = new ArrayList();
				List cList = new ArrayList();
				List nameList = new ArrayList();
				for (int m = 0; m < chartIfo.length; m++) {
					if (StringUtils.isNotEmpty(chartIfo[m])) {
						cList.add(chartIfo[m]);
						String reportInfo[] = chartIfo[m].split("\\|");
						System.out.print(reportInfo[0] + "\t" + reportInfo[1] + "\t" + reportInfo[2]);
						leftType = reportInfo[0];
						chartType = reportInfo[1];
						dateType = reportInfo[2];
						project_id=Integer.valueOf(reportInfo[3]);
						
						ProjectsDatabase pjd = new ProjectsDatabase();
						String projectName = pjd.getProjectNameByUserId(project_id);
						
						if(StringUtils.isNotEmpty(projectName)){
							nameList.add(projectName);
						}
						/*
						 * 计算开始时间和截止时间
						 */

						HashMap dateMap = new HashMap();
						if (dateType.equals("day")) {
							dateMap = DateUtils.getDay(dateType, "2", date);
						} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
							dateMap = DateUtils.getDay(dateType, "3", date);
						} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
								|| dateType.equals("year")) {
							dateMap = DateUtils.getDay(dateType, "4", date);
						} else if (dateType.equals("twoYear")) {
							dateMap = DateUtils.getDay(dateType, "5", date);
						}
						long startTime = (long) dateMap.get("firstT");
						long endTime = (long) dateMap.get("nextT");
						//首页根据项目ID 查询
						if(StringUtils.isNotEmpty(selectProId)){
							project_id=selectId;
						}

						/*
						 * Pv添加到首页
						 */
						if (leftType.equals("PV")) {
							PageViewDatabase pd = new PageViewDatabase();

							if (dateType.equals("day")) {
								if (chartType.equals("line")) {
									List<TimeSlot> pvList = pd.getHourPageviewCount(startTime, endTime, client_id,
											project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									/*
									 * 遍历时间
									 */
									List timeList = DateUtils.getBetweenDate(startTime, endTime);
									for (int i = 0; i < timeList.size(); i++) {
										map2 = new HashMap();
										long t = (long) timeList.get(i);
										long s= 0;
										long pvNum=0l;
										for(int j = 0; j < pvList.size(); j++){
											if(t==pvList.get(j).getTime()){
												s=t;
												pvNum=pvList.get(j).getPvNum();
											}
										}
										if(s!=0){
											map2.put("time", DateUtils.timeToString(s, "2"));
											map2.put("pvNum", pvNum);
										}else{
											map2.put("time", DateUtils.timeToString(t, "2"));
											map2.put("pvNum", 0);
										}	
										list1.add(map2);
									}
									sList.add(list1);
								} else if (chartType.equals("map")) {
									// 查询地图中每个省份的页面访问总量
									List<TimeSlot> geographyPVList = AnalysisDatabase.instance
											.getGeographyPVHour(startTime, endTime, client_id, project_id, "中国");
									List list1 = new ArrayList();
									Map map4 = null;
									for (int i = 0; i < geographyPVList.size(); i++) {
										map4 = new HashMap();
										map4.put("province", geographyPVList.get(i).getProvince());
										map4.put("pvNum", geographyPVList.get(i).getPvNum());
										list1.add(map4);
									}
								
									sList.add(list1);
								} else if (chartType.equals("vBar") || chartType.equals("area")) {
									List<TimeSlot> urlList = AnalysisDatabase.instance
											.getUrlHourPageviewCount(startTime, endTime, client_id, project_id);
									List list1 = new ArrayList();
									Map map4 = null;
									for (int i = urlList.size() - 1; i >= 0; i--) {
										map4 = new HashMap();
										map4.put("url", urlList.get(i).getUrl());
										map4.put("urlNum", urlList.get(i).getUrlNum());
										list1.add(map4);
									}
									
									sList.add(list1);
								}
							} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
								if (chartType.equals("line")) {
									List<TimeSlot> pvList = pd.getDailyPageviewCount(startTime, endTime, client_id,
											project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									/*
									 * 遍历时间
									 */
									List timeList = DateUtils.getBetweenDate(startTime, endTime);
									for (int i = 0; i < timeList.size(); i++) {
										map2 = new HashMap();
										long t = (long) timeList.get(i);
										long s= 0;
										long pvNum=0l;
										for(int j = 0; j < pvList.size(); j++){
											if(t==pvList.get(j).getTime()){
												s=t;
												pvNum=pvList.get(j).getPvNum();
											}
										}
										if(s!=0){
											map2.put("time", DateUtils.timeToString(s, "3"));
											map2.put("pvNum", pvNum);
										}else{
											map2.put("time", DateUtils.timeToString(t, "3"));
											map2.put("pvNum", 0);
										}	
										list1.add(map2);
									}
									sList.add(list1);
								} else if (chartType.equals("map")) {
									// 查询地图中每个省份的页面访问总量
									List<TimeSlot> geographyPVList = AnalysisDatabase.instance
											.getGeographyPVDaily(startTime, endTime, client_id, project_id, "中国");
									List list1 = new ArrayList();
									Map map4 = null;
									for (int i = 0; i < geographyPVList.size(); i++) {
										map4 = new HashMap();
										map4.put("province", geographyPVList.get(i).getProvince());
										map4.put("pvNum", geographyPVList.get(i).getPvNum());
										list1.add(map4);
									}
									
									sList.add(list1);

								} else if (chartType.equals("vBar") || chartType.equals("area")) {
									List<TimeSlot> urlList = AnalysisDatabase.instance
											.getUrlDailyPageviewCount(startTime, endTime, client_id, project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = urlList.size() - 1; i >= 0; i--) {
										map2 = new HashMap();
										map2.put("url", urlList.get(i).getUrl());
										map2.put("urlNum", urlList.get(i).getUrlNum());
										list1.add(map2);
									}
								
									sList.add(list1);
								}
							} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
									|| dateType.equals("year")) {
								if (chartType.equals("line")) {
									List<TimeSlot> pvList = pd.getMonthPageviewCount(startTime, endTime, client_id,
											project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									/*
									 * 遍历时间
									 */
									List timeList = DateUtils.getBetweenDate(startTime, endTime);
									for (int i = 0; i < timeList.size(); i++) {
										map2 = new HashMap();
										long t = (long) timeList.get(i);
										long s= 0;
										long pvNum=0l;
										for(int j = 0; j < pvList.size(); j++){
											if(t==pvList.get(j).getTime()){
												s=t;
												pvNum=pvList.get(j).getPvNum();
											}
										}
										if(s!=0){
											map2.put("time", DateUtils.timeToString(s, "4"));
											map2.put("pvNum", pvNum);
										}else{
											map2.put("time", DateUtils.timeToString(t, "4"));
											map2.put("pvNum", 0);
										}	
										list1.add(map2);
									}
									sList.add(list1);
								} else if (chartType.equals("map")) {
									// 查询地图中每个省份的页面访问总量
									List<TimeSlot> geographyPVList = AnalysisDatabase.instance
											.getGeographyPVMonth(startTime, endTime, client_id, project_id, "中国");
									List list1 = new ArrayList();
									Map map4 = null;
									for (int i = 0; i < geographyPVList.size(); i++) {
										map4 = new HashMap();
										map4.put("province", geographyPVList.get(i).getProvince());
										map4.put("pvNum", geographyPVList.get(i).getPvNum());
										list1.add(map4);
									}
									
									sList.add(list1);

								} else if (chartType.equals("vBar") || chartType.equals("area")) {
									List<TimeSlot> urlList = AnalysisDatabase.instance
											.getUrlMonthPageviewCount(startTime, endTime, client_id, project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = urlList.size() - 1; i >= 0; i--) {
										map2 = new HashMap();
										map2.put("url", urlList.get(i).getUrl());
										map2.put("urlNum", urlList.get(i).getUrlNum());
										list1.add(map2);
									}
								
									sList.add(list1);
								}
							} else if (dateType.equals("twoYear")) {
								if (chartType.equals("line")) {
									List<TimeSlot> pvList = pd.getYearPageviewCount(startTime, endTime, client_id,
											project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									/*
									 * 遍历时间
									 */
									List timeList = DateUtils.getBetweenDate(startTime, endTime);
									for (int i = 0; i < timeList.size(); i++) {
										map2 = new HashMap();
										long t = (long) timeList.get(i);
										long s= 0;
										long pvNum=0l;
										for(int j = 0; j < pvList.size(); j++){
											if(t==pvList.get(j).getTime()){
												s=t;
												pvNum=pvList.get(j).getPvNum();
											}
										}
										if(s!=0){
											map2.put("time", DateUtils.timeToString(s, "5"));
											map2.put("pvNum", pvNum);
										}else{
											map2.put("time", DateUtils.timeToString(t, "5"));
											map2.put("pvNum", 0);
										}	
										list1.add(map2);
									}
									sList.add(list1);
								} else if (chartType.equals("map")) {
									// 查询地图中每个省份的页面访问总量
									List<TimeSlot> geographyPVList = AnalysisDatabase.instance
											.getGeographyPVYear(startTime, endTime, client_id, project_id, "中国");
									List list1 = new ArrayList();
									Map map4 = null;
									for (int i = 0; i < geographyPVList.size(); i++) {
										map4 = new HashMap();
										map4.put("province", geographyPVList.get(i).getProvince());
										map4.put("pvNum", geographyPVList.get(i).getPvNum());
										list1.add(map4);
									}
									
									sList.add(list1);
								} else if (chartType.equals("vBar") || chartType.equals("area")) {
									List<TimeSlot> urlList = AnalysisDatabase.instance
											.getUrlYearPageviewCount(startTime, endTime, client_id, project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = urlList.size() - 1; i >= 0; i--) {
										map2 = new HashMap();
										map2.put("url", urlList.get(i).getUrl());
										map2.put("urlNum", urlList.get(i).getUrlNum());
										list1.add(map2);
									}
						
									sList.add(list1);
								}
							}
						}
						/*
						 * Ip添加到首页
						 */

						else if (leftType.equals("IP")) {
							IpCountDatabase ipd = new IpCountDatabase();
							if (dateType.equals("day")) {
								if (chartType.equals("line")) {
									List<TimeSlot> pvList = ipd.getHourPageviewCount(startTime, endTime, client_id,
											project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = 0; i < pvList.size(); i++) {
										map2 = new HashMap();
										map2.put("ipNum", pvList.get(i).getIpNum());
										map2.put("time", DateUtils.timeToString(pvList.get(i).getTime(), "2"));
										list1.add(map2);
									}
								
									sList.add(list1);
								} else if (chartType.equals("map")) {
									// 查询地图中每个省份的独立Ip数
									List<TimeSlot> geographyIpList = AnalysisDatabase.instance
											.getGeographyIpHour(startTime, endTime, client_id, project_id, "中国");
									List list2 = new ArrayList();
									Map map2 = null;
									for (int i = 0; i < geographyIpList.size(); i++) {
										map2 = new HashMap();
										map2.put("province", geographyIpList.get(i).getProvince());
										map2.put("pvNum", geographyIpList.get(i).getIpNum());
										list2.add(map2);
									}
									
									sList.add(list2);
								} else if (chartType.equals("vBar") || chartType.equals("area")) {
									List<TimeSlot> urlList = AnalysisDatabase.instance
											.getUrlHourPageviewCount(startTime, endTime, client_id, project_id);
									List list1 = new ArrayList();
									Map map4 = null;
									for (int i = urlList.size() - 1; i >= 0; i--) {
										map4 = new HashMap();
										map4.put("url", urlList.get(i).getUrl());
										map4.put("urlNum", urlList.get(i).getUrlNum());
										list1.add(map4);
									}
								
									sList.add(list1);
								}
							} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
								if (chartType.equals("line")) {
									List<TimeSlot> pvList = ipd.getDailyPageviewCount(startTime, endTime, client_id,
											project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = 0; i < pvList.size(); i++) {
										map2 = new HashMap();
										map2.put("ipNum", pvList.get(i).getIpNum());
										map2.put("time", DateUtils.timeToString(pvList.get(i).getTime(), "3"));
										list1.add(map2);
									}
								
									sList.add(list1);
								} else if (chartType.equals("map")) {
									// 查询地图中每个省份的独立Ip数
									List<TimeSlot> geographyIpList = AnalysisDatabase.instance
											.getGeographyIpDaily(startTime, endTime, client_id, project_id, "中国");
									List list2 = new ArrayList();
									Map map2 = null;
									for (int i = 0; i < geographyIpList.size(); i++) {
										map2 = new HashMap();
										map2.put("province", geographyIpList.get(i).getProvince());
										map2.put("pvNum", geographyIpList.get(i).getIpNum());
										list2.add(map2);
									}
								
									sList.add(list2);
								} else if (chartType.equals("vBar") || chartType.equals("area")) {
									List<TimeSlot> urlList = AnalysisDatabase.instance
											.getUrlDailyPageviewCount(startTime, endTime, client_id, project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = urlList.size() - 1; i >= 0; i--) {
										map2 = new HashMap();
										map2.put("url", urlList.get(i).getUrl());
										map2.put("urlNum", urlList.get(i).getUrlNum());
										list1.add(map2);
									}
									
									sList.add(list1);
								}
							} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
									|| dateType.equals("year")) {
								if (chartType.equals("line")) {
									List<TimeSlot> pvList = ipd.getMonthPageviewCount(startTime, endTime, client_id,
											project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = 0; i < pvList.size(); i++) {
										map2 = new HashMap();
										map2.put("ipNum", pvList.get(i).getIpNum());
										map2.put("time", DateUtils.timeToString(pvList.get(i).getTime(), "4"));
										list1.add(map2);
									}
									
									sList.add(list1);
								} else if (chartType.equals("map")) {
									// 查询地图中每个省份的独立Ip数
									List<TimeSlot> geographyIpList = AnalysisDatabase.instance
											.getGeographyIpMonth(startTime, endTime, client_id, project_id, "中国");
									List list2 = new ArrayList();
									Map map2 = null;
									for (int i = 0; i < geographyIpList.size(); i++) {
										map2 = new HashMap();
										map2.put("province", geographyIpList.get(i).getProvince());
										map2.put("pvNum", geographyIpList.get(i).getIpNum());
										list2.add(map2);
									}
									
									sList.add(list2);
								} else if (chartType.equals("vBar") || chartType.equals("area")) {
									List<TimeSlot> urlList = AnalysisDatabase.instance
											.getUrlMonthPageviewCount(startTime, endTime, client_id, project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = urlList.size() - 1; i >= 0; i--) {
										map2 = new HashMap();
										map2.put("url", urlList.get(i).getUrl());
										map2.put("urlNum", urlList.get(i).getUrlNum());
										list1.add(map2);
									}
								
									sList.add(list1);
								}
							} else if (dateType.equals("twoYear")) {
								if (chartType.equals("line")) {
									List<TimeSlot> pvList = ipd.getYearPageviewCount(startTime, endTime, client_id,
											project_id);
									List list1 = new ArrayList();
									Map map4 = null;
									for (int i = 0; i < pvList.size(); i++) {
										map4 = new HashMap();
										map4.put("ipNum", pvList.get(i).getPvNum());
										map4.put("time", DateUtils.timeToString(pvList.get(i).getTime(), "5"));
										list1.add(map4);
									}
							
									sList.add(list1);
								} else if (chartType.equals("map")) {
									// 查询地图中每个省份的独立Ip数
									List<TimeSlot> geographyIpList = AnalysisDatabase.instance
											.getGeographyIpYear(startTime, endTime, client_id, project_id, "中国");
									List list2 = new ArrayList();
									Map map2 = null;
									for (int i = 0; i < geographyIpList.size(); i++) {
										map2 = new HashMap();
										map2.put("province", geographyIpList.get(i).getProvince());
										map2.put("pvNum", geographyIpList.get(i).getIpNum());
										list2.add(map2);
									}
							
									sList.add(list2);

								} else if (chartType.equals("vBar") || chartType.equals("area")) {
									List<TimeSlot> urlList = AnalysisDatabase.instance
											.getUrlYearPageviewCount(startTime, endTime, client_id, project_id);
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = urlList.size() - 1; i >= 0; i--) {
										map2 = new HashMap();
										map2.put("url", urlList.get(i).getUrl());
										map2.put("urlNum", urlList.get(i).getUrlNum());
										list1.add(map2);
									}
								
									sList.add(list1);
								}
							}
						
						}
						
					/*	 流量BYTES添加到首页*/
						 
						else if (leftType.equals("DATA")) {
							DataCountDatabase dc = new DataCountDatabase();
							if (dateType.equals("day")) {
								if (chartType.equals("line")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsList = dc.getHourData(startTime, endTime, client_id, project_id);
									Map map2 = null;
									List timeList = DateUtils.getBetweenDate(startTime, endTime);
									for (int i = 0; i < timeList.size(); i++) {
										map2 = new HashMap();
										long t = (long) timeList.get(i);
										long s= 0;
										long bytes=0l;
										for(int j = 0; j <tsList.size(); j++){
											if(t==tsList.get(j).getTime()){
												s=t;
												bytes=tsList.get(j).getDataNum();
											}
										}
										if(s!=0){
											map2.put("time", DateUtils.timeToString(s, "2"));
											map2.put("bytes", bytes);
										}else{
											map2.put("time", DateUtils.timeToString(t, "2"));
											map2.put("bytes", 0);
										}	
										list1.add(map2);
									}
									sList.add(list1);
								} else if (chartType.equals("map")) {
									// 查询地图中每个省份的字节数
									List<TimeSlot> geographyByteList = AnalysisDatabase.instance
											.getGeographyDataHour(startTime, endTime, client_id, project_id, "中国");
									List list3 = new ArrayList();
									Map map3 = null;
									for (int i = 0; i < geographyByteList.size(); i++) {
										map3 = new HashMap();
										map3.put("province", geographyByteList.get(i).getProvince());
										map3.put("pvNum", geographyByteList.get(i).getDataNum());
										list3.add(map3);
									}
									//dataMapList.add(list3);
									sList.add(list3);
								} else if (chartType.equals("vBar")) {
									Map map2 = null;
									List list1 = new ArrayList();
									List<TimeSlot> urlList = dc.getTopUrlHour(startTime, endTime, client_id,
											project_id);
									for (int i = urlList.size() - 1; i >= 0; i--) {
										map2 = new HashMap();
										map2.put("url", urlList.get(i).getUrl());
										map2.put("bytes", urlList.get(i).getDataNum());
										list1.add(map2);
									}
								//	dataBarList.add(list1);
									sList.add(list1);
								} else if (chartType.equals("area")) {
									Map map2 = null;
									List list1 = new ArrayList();
									List<TimeSlot> ipList = dc.getTopIpHour(startTime, endTime, client_id, project_id);
									for (int i = 0; i < ipList.size(); i++) {
										map2 = new HashMap();
										map2.put("ip", ipList.get(i).getIp());
										map2.put("bytes", ipList.get(i).getIpNum());
										list1.add(map2);
									}
									//dataAeraList.add(list1);
									sList.add(list1);
								}
							} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
								if (chartType.equals("line")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsList = dc.getDailyData(startTime, endTime, client_id, project_id);
									Map map2 = null;
									List timeList = DateUtils.getBetweenDate(startTime, endTime);
									for (int i = 0; i < timeList.size(); i++) {
										map2 = new HashMap();
										long t = (long) timeList.get(i);
										long s= 0;
										long bytes=0l;
										for(int j = 0; j <tsList.size(); j++){
											if(t==tsList.get(j).getTime()){
												s=t;
												bytes=tsList.get(j).getDataNum();
											}
										}
										if(s!=0){
											map2.put("time", DateUtils.timeToString(s, "3"));
											map2.put("bytes", bytes);
										}else{
											map2.put("time", DateUtils.timeToString(t, "3"));
											map2.put("bytes", 0);
										}	
										list1.add(map2);
									}
									sList.add(list1);
								} else if (chartType.equals("map")) {
									// 查询地图中每个省份的字节数
									List<TimeSlot> geographyByteList = AnalysisDatabase.instance
											.getGeographyDataDaily(startTime, endTime, client_id, project_id, "中国");
									List list3 = new ArrayList();
									Map map3 = null;
									for (int i = 0; i < geographyByteList.size(); i++) {
										map3 = new HashMap();
										map3.put("province", geographyByteList.get(i).getProvince());
										map3.put("pvNum", geographyByteList.get(i).getDataNum());
										list3.add(map3);
									}
									//dataMapList.add(list3);
									sList.add(list3);
								} else if (chartType.equals("vBar")) {
									Map map2 = null;
									List list1 = new ArrayList();
									List<TimeSlot> urlList = dc.getTopUrlDaily(startTime, endTime, client_id,
											project_id);
									for (int i = urlList.size() - 1; i >= 0; i--) {
										map2 = new HashMap();
										map2.put("url", urlList.get(i).getUrl());
										map2.put("bytes", urlList.get(i).getDataNum());
										list1.add(map2);
									}
								//	dataBarList.add(list1);
									sList.add(list1);
								} else if (chartType.equals("area")) {
									Map map2 = null;
									List list1 = new ArrayList();
									List<TimeSlot> ipList = dc.getTopIpDaily(startTime, endTime, client_id, project_id);
									for (int i = 0; i < ipList.size(); i++) {
										map2 = new HashMap();
										map2.put("ip", ipList.get(i).getIp());
										map2.put("bytes", ipList.get(i).getIpNum());
										list1.add(map2);
									}
								//	dataAeraList.add(list1);
									sList.add(list1);
								}
							} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
									|| dateType.equals("year")) {
								if (chartType.equals("line")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsList = dc.getYearData(startTime, endTime, client_id, project_id);
									Map map2 = null;
									List timeList = DateUtils.getBetweenDate(startTime, endTime);
									for (int i = 0; i < timeList.size(); i++) {
										map2 = new HashMap();
										long t = (long) timeList.get(i);
										long s= 0;
										long bytes=0l;
										for(int j = 0; j <tsList.size(); j++){
											if(t==tsList.get(j).getTime()){
												s=t;
												bytes=tsList.get(j).getDataNum();
											}
										}
										if(s!=0){
											map2.put("time", DateUtils.timeToString(s, "4"));
											map2.put("bytes", bytes);
										}else{
											map2.put("time", DateUtils.timeToString(t, "4"));
											map2.put("bytes", 0);
										}	
										list1.add(map2);
									}
									sList.add(list1);
								} else if (chartType.equals("map")) {
									// 查询地图中每个省份的字节数
									List<TimeSlot> geographyByteList = AnalysisDatabase.instance
											.getGeographyDataMonth(startTime, endTime, client_id, project_id, "中国");
									List list3 = new ArrayList();
									Map map3 = null;
									for (int i = 0; i < geographyByteList.size(); i++) {
										map3 = new HashMap();
										map3.put("province", geographyByteList.get(i).getProvince());
										map3.put("pvNum", geographyByteList.get(i).getDataNum());
										list3.add(map3);
									}
									//dataMapList.add(list3);
									sList.add(list3);
								} else if (chartType.equals("vBar")) {
									Map map2 = null;
									List list1 = new ArrayList();
									List<TimeSlot> urlList = dc.getTopUrlMonth(startTime, endTime, client_id,
											project_id);
									for (int i = urlList.size() - 1; i >= 0; i--) {
										map2 = new HashMap();
										map2.put("url", urlList.get(i).getUrl());
										map2.put("bytes", urlList.get(i).getDataNum());
										list1.add(map2);
									}
									sList.add(list1);
								} else if (chartType.equals("area")) {
									Map map2 = null;
									List list1 = new ArrayList();
									List<TimeSlot> ipList = dc.getTopIpMonth(startTime, endTime, client_id, project_id);
									for (int i = 0; i < ipList.size(); i++) {
										map2 = new HashMap();
										map2.put("ip", ipList.get(i).getIp());
										map2.put("bytes", ipList.get(i).getIpNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							} else if (dateType.equals("twoYear")) {
								if (chartType.equals("line")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsList = dc.getYearData(startTime, endTime, client_id, project_id);
									Map map2 = null;
									List timeList = DateUtils.getBetweenDate(startTime, endTime);
									for (int i = 0; i < timeList.size(); i++) {
										map2 = new HashMap();
										long t = (long) timeList.get(i);
										long s= 0;
										long bytes=0l;
										for(int j = 0; j <tsList.size(); j++){
											if(t==tsList.get(j).getTime()){
												s=t;
												bytes=tsList.get(j).getDataNum();
											}
										}
										if(s!=0){
											map2.put("time", DateUtils.timeToString(s, "5"));
											map2.put("bytes", bytes);
										}else{
											map2.put("time", DateUtils.timeToString(t, "5"));
											map2.put("bytes", 0);
										}	
										list1.add(map2);
									}
									sList.add(list1);
								} else if (chartType.equals("map")) {
									// 查询地图中每个省份的字节数
									List<TimeSlot> geographyByteList = AnalysisDatabase.instance
											.getGeographyDataYear(startTime, endTime, client_id, project_id, "中国");
									List list3 = new ArrayList();
									Map map3 = null;
									for (int i = 0; i < geographyByteList.size(); i++) {
										map3 = new HashMap();
										map3.put("province", geographyByteList.get(i).getProvince());
										map3.put("pvNum", geographyByteList.get(i).getDataNum());
										list3.add(map3);
									}
									//dataMapList.add(list3);
									sList.add(list3);
								} else if (chartType.equals("vBar")) {
									Map map2 = null;
									List list1 = new ArrayList();
									List<TimeSlot> urlList = dc.getTopUrlYear(startTime, endTime, client_id,
											project_id);
									for (int i = urlList.size() - 1; i >= 0; i--) {
										map2 = new HashMap();
										map2.put("url", urlList.get(i).getUrl());
										map2.put("bytes", urlList.get(i).getDataNum());
										list1.add(map2);
									}
								//	dataBarList.add(list1);
									sList.add(list1);
								} else if (chartType.equals("area")) {
									Map map2 = null;
									List list1 = new ArrayList();
									List<TimeSlot> ipList = dc.getTopIpYear(startTime, endTime, client_id, project_id);
									for (int i = 0; i < ipList.size(); i++) {
										map2 = new HashMap();
										map2.put("ip", ipList.get(i).getIp());
										map2.put("bytes", ipList.get(i).getIpNum());
										list1.add(map2);
									}
									//dataAeraList.add(list1);
									sList.add(list1);
								}
							}
						}
						
						 /*STATUS添加到首页*/
						 
						else if (leftType.equals("STATUS")) {
							StatusDatabase sd = new StatusDatabase();
							if (dateType.equals("day")) {
								List<TimeSlot> tsLit = sd.getHourStatus(startTime, endTime, client_id, project_id);
								if (chartType.equals("bar1")) {
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("status", tsLit.get(i).getStatus());
										map2.put("statusNum", tsLit.get(i).getStatusNum());
										list1.add(map2);
									}
									sList.add(list1);
								}else if (chartType.equals("bar2")) {
									List<TimeSlot> statusList = sd.getByStatusHour(startTime, endTime, client_id, project_id);
									List timeList = DateUtils.getBetweenDate(startTime, endTime);
									Map map6 = new HashMap();
									List list5 = new ArrayList();
									for(int i = tsLit.size()-1; i>=0; i--){
										List list2 = new ArrayList();
										Map map4 = null;
										for (int n = 0; n < timeList.size(); n++) {
											map4 = new HashMap();
											long t = (long) timeList.get(n);
											long p= 0;
											long pvNum=0l;
											for(int j = 0; j < statusList.size(); j++){
												if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
													p=t;
													pvNum=statusList.get(j).getPvNum();
												}
											}
											if(p!=0){
												map4.put("time", DateUtils.timeToString(p, "2"));
												map4.put("pvNum", pvNum);
											}else{
												map4.put("time", DateUtils.timeToString(t, "2"));
												map4.put("pvNum", "0");
											}
											list2.add(map4);
										}
										map6.put(tsLit.get(i).getStatus(), list2);
										list5.add(list5);
									}
									sList.add(map6);
								}else if (chartType.equals("vBar1")) {
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
									sList.add(map3);
								}
								else if (chartType.equals("vBar2")) {
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
									sList.add(map5);
								}	
									
									
							} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
								

								List<TimeSlot> tsLit = sd.getDailyStatus(startTime, endTime, client_id, project_id);
								if (chartType.equals("bar1")) {
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("status", tsLit.get(i).getStatus());
										map2.put("statusNum", tsLit.get(i).getStatusNum());
										list1.add(map2);
									}
									sList.add(list1);
								}else if (chartType.equals("bar2")) {
									List<TimeSlot> statusList = sd.getByStatusDaily(startTime, endTime, client_id, project_id);
									List timeList = DateUtils.getBetweenDate(startTime, endTime);
									Map map6 = new HashMap();
									List list5 = new ArrayList();
									for(int i = tsLit.size()-1; i>=0; i--){
										List list2 = new ArrayList();
										Map map4 = null;
										for (int n = 0; n < timeList.size(); n++) {
											map4 = new HashMap();
											long t = (long) timeList.get(n);
											long p= 0;
											long pvNum=0l;
											for(int j = 0; j < statusList.size(); j++){
												if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
													p=t;
													pvNum=statusList.get(j).getPvNum();
												}
											}
											if(p!=0){
												map4.put("time", DateUtils.timeToString(p, "3"));
												map4.put("pvNum", pvNum);
											}else{
												map4.put("time", DateUtils.timeToString(t, "3"));
												map4.put("pvNum", "0");
											}
											list2.add(map4);
										}
										map6.put(tsLit.get(i).getStatus(), list2);
										list5.add(list5);
									}
									sList.add(map6);
								}else if (chartType.equals("vBar1")) {
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
									sList.add(map3);
								}
								else if (chartType.equals("vBar2")) {
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
									sList.add(map5);
								}	
									
									
							
							} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
									|| dateType.equals("year")) {
								

								List<TimeSlot> tsLit = sd.getMonthStatus(startTime, endTime, client_id, project_id);
								if (chartType.equals("bar1")) {
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("status", tsLit.get(i).getStatus());
										map2.put("statusNum", tsLit.get(i).getStatusNum());
										list1.add(map2);
									}
									sList.add(list1);
								}else if (chartType.equals("bar2")) {
									List<TimeSlot> statusList = sd.getByStatusMonth(startTime, endTime, client_id, project_id);
									List timeList = DateUtils.getBetweenDate(startTime, endTime);
									Map map6 = new HashMap();
									List list5 = new ArrayList();
									for(int i = tsLit.size()-1; i>=0; i--){
										List list2 = new ArrayList();
										Map map4 = null;
										for (int n = 0; n < timeList.size(); n++) {
											map4 = new HashMap();
											long t = (long) timeList.get(n);
											long p= 0;
											long pvNum=0l;
											for(int j = 0; j < statusList.size(); j++){
												if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
													p=t;
													pvNum=statusList.get(j).getPvNum();
												}
											}
											if(p!=0){
												map4.put("time", DateUtils.timeToString(p, "4"));
												map4.put("pvNum", pvNum);
											}else{
												map4.put("time", DateUtils.timeToString(t, "4"));
												map4.put("pvNum", "0");
											}
											list2.add(map4);
										}
										map6.put(tsLit.get(i).getStatus(), list2);
										list5.add(list5);
									}
									sList.add(map6);
								}else if (chartType.equals("vBar1")) {
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
									sList.add(map3);
								}
								else if (chartType.equals("vBar2")) {
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
									sList.add(map5);
								}	
									
									
							
							} else if (dateType.equals("twoYear")) {
								

								List<TimeSlot> tsLit = sd.getYearStatus(startTime, endTime, client_id, project_id);
								if (chartType.equals("bar1")) {
									List list1 = new ArrayList();
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("status", tsLit.get(i).getStatus());
										map2.put("statusNum", tsLit.get(i).getStatusNum());
										list1.add(map2);
									}
									sList.add(list1);
								}else if (chartType.equals("bar2")) {
									List<TimeSlot> statusList = sd.getByStatusYear(startTime, endTime, client_id, project_id);
									List timeList = DateUtils.getBetweenDate(startTime, endTime);
									Map map6 = new HashMap();
									List list5 = new ArrayList();
									for(int i = tsLit.size()-1; i>=0; i--){
										List list2 = new ArrayList();
										Map map4 = null;
										for (int n = 0; n < timeList.size(); n++) {
											map4 = new HashMap();
											long t = (long) timeList.get(n);
											long p= 0;
											long pvNum=0l;
											for(int j = 0; j < statusList.size(); j++){
												if(tsLit.get(i).getStatus()==statusList.get(j).getStatus() && t==statusList.get(j).getTime()){
													p=t;
													pvNum=statusList.get(j).getPvNum();
												}
											}
											if(p!=0){
												map4.put("time", DateUtils.timeToString(p, "5"));
												map4.put("pvNum", pvNum);
											}else{
												map4.put("time", DateUtils.timeToString(t, "5"));
												map4.put("pvNum", "0");
											}
											list2.add(map4);
										}
										map6.put(tsLit.get(i).getStatus(), list2);
										list5.add(list5);
									}
									sList.add(map6);
								}else if (chartType.equals("vBar1")) {
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
									sList.add(map3);
								}
								else if (chartType.equals("vBar2")) {
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
									sList.add(map5);
								}	
									
									
							
							}
						}
						
						/*  浏览器Browser添加到首页*/
						 
 						else if (leftType.equals("BR")) {
							BrowserDatabase bro = new BrowserDatabase();
							if (dateType.equals("day")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsLit = bro.getHourStatus(startTime, endTime, client_id, project_id);
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("browserType", tsLit.get(i).getBrowserType());
										map2.put("browserNum", tsLit.get(i).getBrowserNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsLit = bro.getDailyStatus(startTime, endTime, client_id,
											project_id);
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("browserType", tsLit.get(i).getBrowserType());
										map2.put("browserNum", tsLit.get(i).getBrowserNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
									|| dateType.equals("year")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsLit = bro.getMonthStatus(startTime, endTime, client_id,
											project_id);
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("browserType", tsLit.get(i).getBrowserType());
										map2.put("browserNum", tsLit.get(i).getBrowserNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							} else if (dateType.equals("twoYear")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsLit = bro.getYearStatus(startTime, endTime, client_id, project_id);
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("browserType", tsLit.get(i).getBrowserType());
										map2.put("browserNum", tsLit.get(i).getBrowserNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							}
						} else if (leftType.equals("OS")) {
							OperateSystemDatabase ops = new OperateSystemDatabase();
							if (dateType.equals("day")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsLit = ops.getHourStatus(startTime, endTime, client_id, project_id);
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("osType", tsLit.get(i).getOsType());
										map2.put("osNum", tsLit.get(i).getOsNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsLit = ops.getDailyStatus(startTime, endTime, client_id,
											project_id);
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("osType", tsLit.get(i).getOsType());
										map2.put("osNum", tsLit.get(i).getOsNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
									|| dateType.equals("year")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsLit = ops.getMonthStatus(startTime, endTime, client_id,
											project_id);
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("osType", tsLit.get(i).getOsType());
										map2.put("osNum", tsLit.get(i).getOsNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							} else if (dateType.equals("twoYear")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsLit = ops.getYearStatus(startTime, endTime, client_id, project_id);
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("osType", tsLit.get(i).getOsType());
										map2.put("osNum", tsLit.get(i).getOsNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							}
						}
						
						 /* 请求类型REQUEST添加到首页*/
						 
						else if (leftType.equals("REQUEST")) {
							RequestTypeDatabase rtb = new RequestTypeDatabase();
							if (dateType.equals("day")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsLit = rtb.getHourStatus(startTime, endTime, client_id, project_id);
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("requestType", tsLit.get(i).getRequestType());
										map2.put("requestNum", tsLit.get(i).getRequestNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsLit = rtb.getDailyStatus(startTime, endTime, client_id,
											project_id);
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("requestType", tsLit.get(i).getRequestType());
										map2.put("requestNum", tsLit.get(i).getRequestNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
									|| dateType.equals("year")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsLit = rtb.getMonthStatus(startTime, endTime, client_id,
											project_id);
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("requestType", tsLit.get(i).getRequestType());
										map2.put("requestNum", tsLit.get(i).getRequestNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							} else if (dateType.equals("twoYear")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List<TimeSlot> tsLit = rtb.getYearStatus(startTime, endTime, client_id, project_id);
									Map map2 = null;
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("requestType", tsLit.get(i).getRequestType());
										map2.put("requestNum", tsLit.get(i).getRequestNum());
										list1.add(map2);
									}
									sList.add(list1);
								}
							}
						} else if (leftType.equals("REFERRER")) {
							RefererDatabase rfd = new RefererDatabase();
							if (dateType.equals("day")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List list2 = new ArrayList();
									Map map2 = null;
									Map map3 = null;
									List<TimeSlot> tsLit = rfd.getHourReferrer(startTime, endTime, client_id,
											project_id);
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("referrerType", tsLit.get(i).getReferrerType());
										map2.put("referrerNum", tsLit.get(i).getReferrerNum());
										list1.add(map2);
									}
									List<TimeSlot> indirectLit = rfd.getHourIndirectReferrer(startTime, endTime,
											client_id, project_id);
									for (int i = 0; i < indirectLit.size(); i++) {
										map3 = new HashMap();
										map3.put("url", indirectLit.get(i).getReferrerType());
										map3.put("urlNum", indirectLit.get(i).getReferrerNum());
										list2.add(map3);
									}

									List list6 =new ArrayList();
									list6.add(list1);
									list6.add(list2);
									sList.add(list6);
								}
							} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List list2 = new ArrayList();
									Map map2 = null;
									Map map3 = null;
									List<TimeSlot> tsLit = rfd.getDailyReferrer(startTime, endTime, client_id,
											project_id);
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("referrerType", tsLit.get(i).getReferrerType());
										map2.put("referrerNum", tsLit.get(i).getReferrerNum());
										list1.add(map2);
									}
									List<TimeSlot> indirectLit = rfd.getDailyIndirectReferrer(startTime, endTime,
											client_id, project_id);
									for (int i = 0; i < indirectLit.size(); i++) {
										map3 = new HashMap();
										map3.put("url", indirectLit.get(i).getReferrerType());
										map3.put("urlNum", indirectLit.get(i).getReferrerNum());
										list2.add(map3);
									}

									List list6 =new ArrayList();
									list6.add(list1);
									list6.add(list2);
									sList.add(list6);
								}
							} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
									|| dateType.equals("year")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List list2 = new ArrayList();
									Map map2 = null;
									Map map3 = null;
									List<TimeSlot> tsLit = rfd.getMonthReferrer(startTime, endTime, client_id,
											project_id);
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("referrerType", tsLit.get(i).getReferrerType());
										map2.put("referrerNum", tsLit.get(i).getReferrerNum());
										list1.add(map2);
									}
									List<TimeSlot> indirectLit = rfd.getMonthIndirectReferrer(startTime, endTime,
											client_id, project_id);
									for (int i = 0; i < indirectLit.size(); i++) {
										map3 = new HashMap();
										map3.put("url", indirectLit.get(i).getReferrerType());
										map3.put("urlNum", indirectLit.get(i).getReferrerNum());
										list2.add(map3);
									}

									List list6 =new ArrayList();
									list6.add(list1);
									list6.add(list2);
									sList.add(list6);
								}
							} else if (dateType.equals("twoYear")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list1 = new ArrayList();
									List list2 = new ArrayList();
									Map map2 = null;
									Map map3 = null;
									List<TimeSlot> tsLit = rfd.getYearReferrer(startTime, endTime, client_id,
											project_id);
									for (int i = 0; i < tsLit.size(); i++) {
										map2 = new HashMap();
										map2.put("referrerType", tsLit.get(i).getReferrerType());
										map2.put("referrerNum", tsLit.get(i).getReferrerNum());
										list1.add(map2);
									}
									List<TimeSlot> indirectLit = rfd.getYearIndirectReferrer(startTime, endTime,
											client_id, project_id);
									for (int i = 0; i < indirectLit.size(); i++) {
										map3 = new HashMap();
										map3.put("url", indirectLit.get(i).getReferrerType());
										map3.put("urlNum", indirectLit.get(i).getReferrerNum());
										list2.add(map3);
									}
									List list6 =new ArrayList();
									list6.add(list1);
									list6.add(list2);
									sList.add(list6);
								}
							}
						}else if (leftType.equals("TP")) {
							StayTimeDatabase std  = new StayTimeDatabase ();
							if (dateType.equals("day")) {
								List list2 = new ArrayList();
								if (chartType.equals("vBar") || chartType.equals("area")) {
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
									Map map3 =new HashMap();
									map3.put("allNum", allNum);
									map3.put("timeSlot", list1);
									list2.add(map3);
								}
								sList.add(list2);
							} else if (dateType.equals("week") || dateType.equals("oneMonth")) {
								List list2 = new ArrayList();
								if (chartType.equals("vBar") || chartType.equals("area")) {
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
									Map map3 =new HashMap();
									map3.put("allNum", allNum);
									map3.put("timeSlot", list1);
									list2.add(map3);
								}
								sList.add(list2);
							} else if (dateType.equals("threeMonth") || dateType.equals("sixMonth")
									|| dateType.equals("year")) {
								List list2 = new ArrayList();
								if (chartType.equals("vBar") || chartType.equals("area")) {
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
									Map map3 =new HashMap();
									map3.put("allNum", allNum);
									map3.put("timeSlot", list1);
									list2.add(map3);
								}
								sList.add(list2);
							} else if (dateType.equals("twoYear")) {
								if (chartType.equals("vBar") || chartType.equals("area")) {
									List list2 = new ArrayList();
									if (chartType.equals("vBar") || chartType.equals("area")) {
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
										Map map3 =new HashMap();
										map3.put("allNum", allNum);
										map3.put("timeSlot", list1);
										list2.add(map3);
									}
									sList.add(list2);
								}
							}
						}

					}
				}
				map1.put("cList", cList);
				map1.put("sList", sList);
				map1.put("nameList", nameList);
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
			int proId=0;
			LoginDatabase ld  = new LoginDatabase();
			int user_id = (int) session.getAttribute("user_id");
			int pId = ld.getLastProject(user_id);
			if (pId!=0){
				proId=pId;
			}
			map1.put("projectID", proId);
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
			LoginDatabase ld  = new LoginDatabase();
			int user_id = (int) session.getAttribute("user_id");
			String projectID = request.getParameter("projectID");
			int project_id = 0;
			if(StringUtils.isNotEmpty("projectID")){
				project_id = Integer.valueOf(projectID);
				ld.updateLastPeoject(user_id, project_id);
			}
			map1.put("projectID", "");
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			 writer.print(JsonUtils.objectToJson(map1, response));
			 map1.clear();
			 
		}
	}
}
