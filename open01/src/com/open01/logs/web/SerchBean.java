package com.open01.logs.web;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.open01.logs.db.SerchDatabase;
import com.open01.logs.db.SerchInsertTermsDatabase;
import com.open01.logs.model.SearchOuter;
import com.open01.logs.model.Serch;
import com.open01.logs.model.SerchTerms;
import com.open01.logs.util.CSVUtils;
import com.open01.logs.util.Consant;
import com.open01.logs.util.JsonUtils;

import info.chenli.web.jsp.BooleanCondition;
import info.chenli.web.jsp.WholePartBean;
import net.sf.json.JSONArray;

public class SerchBean extends WholePartBean implements BooleanCondition {
static SerchDatabase pd = new SerchDatabase();
static SerchInsertTermsDatabase database=new SerchInsertTermsDatabase();
	/**
	 * Default constructor.
	 */
	public SerchBean() {
		super("cmd");
		addPart(null, new Part0());
		addPart("WEL:SELECTTERMS", new Part1());
		addPart("WEL:SELECTLISTINFO", new Part2());
		addPart("WEL:EXPORTEXCEL", new Part3());
	}
	@Override
	public boolean isTrue() {
		// TODO Auto-generated method stub
		return false;
	}
	private final class Part0 extends DefaultPlainBean {
		Map<String, Object> map=new HashMap<String, Object>();
		@Override
		protected boolean doConditionIsTrue() {
			String[] fileid = request.getParameterValues("fid[]");
			String inputval = request.getParameter("txt");
			String starttime = request.getParameter("start_time");
			String endtime = request.getParameter("end_time");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date2 = null, date1 = null;
			String indexval = session.getAttribute("user_id").toString();
			if(starttime!=null &&endtime!=null && fileid!=null && inputval!=null ){
				try {
					date2 = dateFormat.parse(starttime);
					date1 = dateFormat1.parse(endtime);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				database.insertTerms(inputval,indexval);
				String iso8601Timestamp = pd.getISO8601Timestamp(date2);
				String iso8601Timestamp2 = pd.getISO8601Timestamp(date1);
				List<JSONArray> list2 = pd.statement(inputval, starttime, endtime, indexval,fileid);
				JSONArray array = list2.get(0);
				JSONArray array1 = list2.get(1);
				Object cityquery = pd.cityQuery(inputval,iso8601Timestamp,iso8601Timestamp2,indexval,fileid);
				map.put("city", cityquery); 
				map.put("order", array);
				map.put("percent", array1);
			}else{
				String[] fileid1 = request.getParameterValues("fid[]");
				String inputval1 = request.getParameter("txt");
				String starttime1 = request.getParameter("start_time");
				String endtime1 = request.getParameter("end_time");
				try {
					date2 = dateFormat.parse(starttime1);
					date1 = dateFormat1.parse(endtime1);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				database.insertTerms(inputval,indexval);
				String iso8601Timestamp = pd.getISO8601Timestamp(date2);
				String iso8601Timestamp2 = pd.getISO8601Timestamp(date1);
				List<JSONArray> list2 = pd.statement(inputval1, starttime, endtime, indexval,fileid1);
				JSONArray array = list2.get(0);
				JSONArray array1 = list2.get(1);
				Object cityquery = pd.cityQuery(inputval1,iso8601Timestamp,iso8601Timestamp2,indexval,fileid1);
				map.put("city", cityquery); 
				map.put("order", array);
				map.put("percent", array1);
			}
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.write(JsonUtils.objectToJson(map, response));
		}
	}
	private final class Part1 extends DefaultPlainBean {
		List<String> listtermsname=new ArrayList<String>();
		@Override
		protected boolean doConditionIsTrue() {
		String indexval = session.getAttribute("user_id").toString();
		List<SerchTerms> list = database.queryTerms(indexval);
		listtermsname.clear();
		for (SerchTerms serchTerms : list) {
			listtermsname.add(serchTerms.getTerms_name());
		}
			return true;
		}
		@Override
		protected void writeConditionIsTrueOutput() {
			writer.write(JsonUtils.objectToJson(listtermsname, response));
		}
	}
	private final class Part2 extends DefaultPlainBean {
		Map<String, Object> map=new HashMap<String, Object>();
		@Override
		protected boolean doConditionIsTrue() {
			String[] fileid = request.getParameterValues("fid[]");
			String limit = request.getParameter("limit");
			String page = request.getParameter("page");
			String inputval = request.getParameter("txt");
			String starttime = request.getParameter("start_time");
			String endtime = request.getParameter("end_time");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String indexval = session.getAttribute("user_id").toString();
			Date date2 = null, date1 = null;
			int	total=0;
			List<Serch> list=null;
			if(starttime!=null &&endtime!=null && fileid!=null && inputval!=null && limit !=null && page!=null){
				try {
					date2 = dateFormat.parse(starttime);
					date1 = dateFormat1.parse(endtime);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				String iso8601Timestamp = pd.getISO8601Timestamp(date2);
				String iso8601Timestamp2 = pd.getISO8601Timestamp(date1);
				total = pd.Count(inputval,iso8601Timestamp,iso8601Timestamp2,indexval,fileid);
				list = pd.boolqueryANDHighlighted(inputval,iso8601Timestamp,iso8601Timestamp2,indexval,fileid,limit,page);
			}else{
				String[] fileid1 = request.getParameterValues("fid[]");
				String limit1 = request.getParameter("limit");
				String page1 = request.getParameter("page");
				String inputval1 = request.getParameter("txt");
				String starttime1 = request.getParameter("start_time");
				String endtime1 = request.getParameter("end_time");
				try {
					date2 = dateFormat.parse(starttime1);
					date1 = dateFormat1.parse(endtime1);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				String iso8601Timestamp = pd.getISO8601Timestamp(date2);
				String iso8601Timestamp2 = pd.getISO8601Timestamp(date1);
				total = pd.Count(inputval1,iso8601Timestamp,iso8601Timestamp2,indexval,fileid1);
				list = pd.boolqueryANDHighlighted(inputval1,iso8601Timestamp,iso8601Timestamp2,indexval,fileid1,limit1,page1);
			}
			map.put("total", total);
			map.put("data", list);
			return true;
		}
		
		@Override
		protected void writeConditionIsTrueOutput() {
				writer.write(JsonUtils.objectToJson(map, response));
		}
	}
	private final class Part3 extends DefaultPlainBean {
		Map<String, Object> map=new HashMap<String, Object>();
		@Override
		protected boolean doConditionIsTrue() {
			String[] fileid = request.getParameterValues("fid[]");
			String inputval = request.getParameter("text");
			String starttime = request.getParameter("start_time");
			String endtime = request.getParameter("end_time");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String indexval = session.getAttribute("user_id").toString();
			List<SearchOuter> list=null;
			int	total=0;
			Date date2=null,date1=null;
			if(fileid!=null && starttime!=null && endtime!=null && indexval!=null){
				try {
					date2 = dateFormat.parse(starttime);
					date1 = dateFormat1.parse(endtime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String iso8601Timestamp = pd.getISO8601Timestamp(date2);
				String iso8601Timestamp2 = pd.getISO8601Timestamp(date1); 
				total = pd.Count(inputval,iso8601Timestamp,iso8601Timestamp2,indexval,fileid);
				//最多可导出65535行数据
				/*if(total > 65535){
					total = 65535;
				}*/
				list = pd.boolqueryANDHighlightedOuter(inputval,iso8601Timestamp,iso8601Timestamp2,indexval,fileid,total);
			}else{
				String[] fileid1 = request.getParameterValues("fid[]");
				String inputval1 = request.getParameter("text");
				String starttime1 = request.getParameter("start_time");
				String endtime1 = request.getParameter("end_time");
				try {
					date2 = dateFormat.parse(starttime1);
					date1 = dateFormat1.parse(endtime1);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String iso8601Timestamp = pd.getISO8601Timestamp(date2);
				String iso8601Timestamp2 = pd.getISO8601Timestamp(date1); 
				list = pd.boolqueryANDHighlightedOuter(inputval1,iso8601Timestamp,iso8601Timestamp2,indexval,fileid1,total);
			}
			List<Map<String,String>> exportData = new ArrayList<Map<String,String>>();
			
			for(SearchOuter search : list){
				Map<String,String> row1 = new LinkedHashMap<String, String>();
				row1.put("1", search.getDatatime());
				row1.put("2", search.getMessage());
				exportData.add(row1);
			}
			 
			LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
			map.put("1", "时间");
			map.put("2", "日志信息");
			
			String path = Consant.EXPORT_FILE_PATH;
			String fileName = Consant.EXPORT_FILE_NAME; 
			CSVUtils.createCSVFile(exportData, map, path, fileName);
		    return true; 
		}
		@Override
		protected void writeConditionIsTrueOutput() {
				writer.write(JsonUtils.objectToJson(map, response));
		}
	} 
}