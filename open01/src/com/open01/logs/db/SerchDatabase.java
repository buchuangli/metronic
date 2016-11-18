package com.open01.logs.db;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.Interval;
import org.joda.time.Period;

import com.open01.logs.model.SearchOuter;
import com.open01.logs.model.Serch;
import com.open01.logs.util.CityUtil;
import com.open01.logs.util.GTMDateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SerchDatabase {
	private static TransportClient client;
	private static GTMDateUtil gtmDateUtil = new GTMDateUtil();
	private static final String BACKGROUND_COLOR = "FFFF00";
	private static SerchDatabase database = new SerchDatabase();
	static {
		/**
		 * 1:通过 setting对象来指定集群配置信息
		 */
		Settings setting = Settings.settingsBuilder().put("cluster.name", "elasticsearch")// 指定集群名称
				.put("client.transport.sniff", true)// 启动嗅探功能
				.build();
		/**
		 * 2：创建客户端 通过setting来创建，若不指定则默认链接的集群名为elasticsearch 链接使用tcp协议即9300
		 */

		try {
			client = TransportClient.builder().settings(setting).build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("60.205.152.23"), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 表格的展示
	 * 
	 * 
	 */
	public List<Serch> boolqueryANDHighlighted(String inputval, String starttime, String endtime, String index,
			String[] fid, String limit, String page) {
		int parseInt = 0;
		int parse = 0;
		if (limit != null && starttime != null && endtime != null && page != null) {
			parseInt = Integer.parseInt(limit);
			parse = Integer.parseInt(page);
		} else {
			parseInt = 10;
			parse = 1;
		}
		int firstIndex = (parse - 1) * parseInt;
		SearchRequestBuilder requestBuilder = client.prepareSearch(index).setTypes(fid);
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		String[] split = inputval.split(",");
		for (String str : split) {
			// MatchQueryBuilder termsQuery =
			// QueryBuilders.matchQuery("message",str);
			MatchQueryBuilder termsQuery = QueryBuilders.matchPhraseQuery("message", str);// 完全匹配查询
			boolQuery.must(termsQuery);
		}
		boolQuery.must(QueryBuilders.rangeQuery("@timestamp").from(starttime).to(endtime));
		// boolQuery.must(termsQuery);
		SearchResponse searchResponse = requestBuilder.setQuery(boolQuery)// 查询所有
				.addHighlightedField("message", 100000, 100000)
				.setHighlighterPreTags("<span style='background-color:" + BACKGROUND_COLOR + ";color:#f00'>")
				.setHighlighterPostTags("</span>").setFrom(firstIndex).setSize(parseInt) // 分页
				.addSort("@timestamp", SortOrder.ASC)// 排序
				.get();
		SearchHits hits = searchResponse.getHits();
		int i = 0;
		SearchHit[] searchHits = hits.getHits();
		String messageval = "";
		List<Serch> list = new ArrayList<Serch>();
		for (SearchHit hit : searchHits) {
			String sourceAsString = hit.getSourceAsString();
			JSONObject jsStr = JSONObject.fromObject(sourceAsString);
			String jsID = jsStr.getString("@timestamp");
			String str = gtmDateUtil.GTMToLocal(jsID);
			int num = Integer.valueOf(parseInt) * (Integer.valueOf(parse) - 1);
			for (Map.Entry<String, HighlightField> hightlight : hit.getHighlightFields().entrySet()) {
				for (Text text : hightlight.getValue().fragments()) {
					Serch serch = new Serch();
					messageval = text.string();
					num += ++i;
					serch.setOrder(num);
					serch.setDatatime(str);
					serch.setMessage(messageval);
					System.out.println(messageval);
					list.add(serch);
				}
			}
		}
		return list;
	}
	/*
	 * 
	 * 地图总数
	 */
	public int Count(String inputval, String starttime, String endtime, String index, String[] fid) {
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		int total = 0;
		SearchRequestBuilder requestBuilder = client.prepareSearch(index).setTypes(fid);
		SearchResponse searchResponse = null;
		MatchQueryBuilder termsQuery = null;
		//进行文件切割多文件搜索es的type
		String[] split = inputval.split(",");
		for (String str : split) {
			// termsQuery = QueryBuilders.matchQuery("message",str);
			termsQuery = QueryBuilders.matchPhraseQuery("message", str);
			boolQuery.must(termsQuery);
		}
		boolQuery.must(QueryBuilders.rangeQuery("@timestamp").from(starttime).to(endtime));
		searchResponse = requestBuilder.setQuery(boolQuery).get();
		SearchHits hits = searchResponse.getHits();
		if (hits != null) {
			total = (int) hits.getTotalHits();
		} else {
			return total = 0;
		}
		return total;
	}

	/*
	 * 中国地图查询总数据
	 * 
	 */
	public SearchHits cityQueryData(String inputval, String starttime, String endtime, String index, String[] fid,
			BoolQueryBuilder boolQuery) {
		SearchRequestBuilder requestBuilder = client.prepareSearch(index).setTypes(fid);
		SearchResponse searchResponse = requestBuilder.setQuery(boolQuery).get();
		SearchHits hits = searchResponse.getHits();
		return hits;
	}

	public int cityCount(String inputval, String starttime, String endtime, String index, String[] fid,
			BoolQueryBuilder boolQuery) {
		SearchHits hits = database.cityQueryData(inputval, starttime, endtime, index, fid, boolQuery);
		int total = (int) hits.getTotalHits();
		return total;
	}

	/*
	 * 中国地图查询
	 */
	public Object cityQuery(String inputval, String starttime, String endtime, String indexval, String[] fid) {
		JSONArray resultArray = new JSONArray();
		if (starttime != null && endtime != null && fid != null && !fid.equals("")) {
			for (CityUtil cityName : CityUtil.values()) {
				JSONObject resultObj = new JSONObject();
				BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
				String[] split = inputval.split(",");
				for (String string : split) {
					MatchQueryBuilder termsQuery = QueryBuilders.matchPhraseQuery("message", string);
					boolQuery.must(termsQuery);
					MatchQueryBuilder termsQuery1 = QueryBuilders.matchQuery("geoip.real_region_name",
							cityName.getIndex());
					boolQuery.must(termsQuery1);
				}
				boolQuery.must(QueryBuilders.rangeQuery("@timestamp").from(starttime).to(endtime));
				int count = database.cityCount(inputval, starttime, endtime, indexval, fid, boolQuery);
				resultObj.put("name", cityName.getName());
				resultObj.put("value", count);
				resultArray.add(resultObj);
			}
		} else {
			return resultArray = null;
		}
		return resultArray;
	}

	/*
	 * 报表的展示
	 */
	public List<JSONArray> statement(String inputval, String starttime, String endtime, String indexval, String[] fid) {
		List<JSONArray> list = new ArrayList<JSONArray>();
		if (starttime != null && endtime != null && fid != null && !fid.equals("")) {
			SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date1 = null;
			Date date2 = null;
			try {
				date1 = sdf0.parse(starttime);
				date2 = sdf0.parse(endtime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String iso8601Timestamp = database.getISO8601Timestamp(date1);
			String iso8601Timestamp2 = database.getISO8601Timestamp(date2);
			Interval interval = new Interval(date1.getTime(), date2.getTime());
			Period period = interval.toPeriod();
			int year = period.getYears();
			int month = period.getMonths();
			int day = period.getDays() + period.getWeeks() * 7;
			int hour = period.getHours();
			int minute = period.getMinutes();
			int seconds = period.getSeconds();
			JSONArray resultArray = new JSONArray();
			JSONArray resultArray1 = new JSONArray();
			SearchRequestBuilder requestBuilder = client.prepareSearch(indexval).setTypes(fid);
			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
			String[] split = inputval.split(",");
			for (String string : split) {
				MatchQueryBuilder termsQuery = QueryBuilders.matchPhraseQuery("message", string);
				boolQuery.must(termsQuery);
			}
			boolQuery.must(QueryBuilders.rangeQuery("@timestamp").from(iso8601Timestamp).to(iso8601Timestamp2));
			SearchResponse search = requestBuilder.setQuery(boolQuery)// 查询所有
					.setSearchType(SearchType.QUERY_THEN_FETCH).addHighlightedField("message").get();
			DateHistogramBuilder dateagg = AggregationBuilders.dateHistogram("dateagg");
			dateagg.field("@timestamp");// 聚合时间字段
			if (year > 1 || (year == 1 && (month != 0 || hour != 0 || day != 0 || minute != 0))) {
				// 年
				dateagg.interval(DateHistogramInterval.YEAR);
				dateagg.format("yyyy");
				dateagg.timeZone("Asia/Shanghai");
				requestBuilder.addAggregation(dateagg);
			} else if (year == 1 && (month == 0 || hour == 0 || day == 0 || minute == 0)) {
				// 月
				dateagg.interval(DateHistogramInterval.MONTH);
				dateagg.format("yyyy-MM");
				dateagg.timeZone("Asia/Shanghai");
				requestBuilder.addAggregation(dateagg);
			} else if (year == 0) {
				if (month > 1 || (month == 1 && (hour != 0 || day != 0 || minute != 0))) {
					// 月
					dateagg.interval(DateHistogramInterval.MONTH);
					dateagg.format("yyyy-MM");
					dateagg.timeZone("Asia/Shanghai");
					requestBuilder.addAggregation(dateagg);
				} else if (month == 1 && (hour == 0 || day == 0 || minute == 0)) {
					// 天
					dateagg.interval(DateHistogramInterval.DAY);
					dateagg.format("yyyy-MM-dd");
					dateagg.timeZone("Asia/Shanghai");
					requestBuilder.addAggregation(dateagg);
				} else if (month == 0) {
					if (day > 1 || (day == 1 && (hour != 0 || minute != 0))) {
						// 天
						dateagg.interval(DateHistogramInterval.DAY);
						dateagg.format("yyyy-MM-dd");
						dateagg.timeZone("Asia/Shanghai");
						requestBuilder.addAggregation(dateagg);
					} else if (day == 1 && (hour == 0 || minute == 0)) {
						// 小时
						dateagg.interval(DateHistogramInterval.HOUR);
						dateagg.format("yyyy-MM-dd HH");
						dateagg.timeZone("Asia/Shanghai");
						requestBuilder.addAggregation(dateagg);
					} else if (day == 0) {
						if (hour > 1 || (hour == 1 && (minute != 0))) {
							// 小时
							dateagg.interval(DateHistogramInterval.HOUR);
							dateagg.format("yyyy-MM-dd HH");
							dateagg.timeZone("Asia/Shanghai");
							requestBuilder.addAggregation(dateagg);
						} else {
							// 分钟
							dateagg.interval(DateHistogramInterval.MINUTE);
							dateagg.format("yyyy-MM-dd HH:mm");
							dateagg.timeZone("Asia/Shanghai");
							requestBuilder.addAggregation(dateagg);
						}
					}
				}
			}
			// 获取结果
			SearchResponse r = requestBuilder.get();
			Histogram h = r.getAggregations().get("dateagg");
			// 得到一级聚合结果里面的分桶集合
			List<Histogram.Bucket> buckets = (List<Histogram.Bucket>) h.getBuckets();
			// 遍历分桶集
			for (Histogram.Bucket b : buckets) {
				System.out.println(b.getKeyAsString() + "  " + b.getDocCount());
				resultArray.add(b.getKeyAsString());
				resultArray1.add(b.getDocCount());
			}
			list.add(resultArray);
			list.add(resultArray1);
		} else {
			return list = null;
		}
		return list;
	}

	/*
	 *
	 * 时间转化为yyyy-MM-dd'T'HH:mm:ss.SSS'Z
	 */
	public String getISO8601Timestamp(Date date) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(tz);
		String nowAsISO = df.format(date);
		return nowAsISO;
	}

	/*
	 * 导出excel
	 * inputval,iso8601Timestamp,iso8601Timestamp2,indexval,type,file,String.
	 * valueOf(total),"1");
	 */
	public List<SearchOuter> boolqueryANDHighlightedOuter(String inputval, String starttime, String endtime,
			String index, String[] fid, int total) {
		SearchHits hits = database.querydataNoHighLight(inputval, starttime, endtime, index, fid, total);
		SearchHit[] searchHits = hits.getHits();
		List<SearchOuter> list =  new ArrayList<SearchOuter>();
		for (SearchHit hit : searchHits) { 
			String sourceAsString = hit.getSourceAsString(); 
			JSONObject jsStr = JSONObject.fromObject(sourceAsString);
			String jsID = jsStr.getString("@timestamp");
			String messageval = jsStr.getString("message");
			String str = gtmDateUtil.GTMToLocal(jsID);
			SearchOuter serchOuter = new SearchOuter();
			//messageval = text.string(); 
			serchOuter.setDatatime(str);
			serchOuter.setMessage(messageval);
			list.add(serchOuter);
		/*	for (Map.Entry<String, HighlightField> hightlight : hit.getHighlightFields().entrySet()) {
				for (Text text : hightlight.getValue().fragments()) {
					SearchOuter serchOuter = new SearchOuter();
					messageval = text.string();
					serchOuter.setDatatime(str);
					serchOuter.setMessage(messageval);
					list.add(serchOuter);
				}
			}*/
		}
		return list;
	}

	/*
	 * 根据时间范围搜索不同的值 通过prepareSearch查询索引库
	 * setQuery(QueryBuilders.matchQuery("name", "jack"))
	 * setSearchType(SearchType.QUERY_THEN_FETCH)
	 */
	public SearchHits querydataNoHighLight(String inputval, String starttime, String endtime, String index,
			String[] fid, int total) {
		SearchRequestBuilder requestBuilder = client.prepareSearch(index).setTypes(fid);
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		String[] split = inputval.split(",");
		for (String string : split) {
			MatchQueryBuilder termsQuery = QueryBuilders.matchPhraseQuery("message", string);
			boolQuery.must(termsQuery);
		}
		boolQuery.must(QueryBuilders.rangeQuery("@timestamp").from(starttime).to(endtime));
		SearchResponse searchResponse = requestBuilder.setQuery(boolQuery)// 查询所有
				.setSearchType(SearchType.DEFAULT).addHighlightedField("message", 100000, 100000).setFrom(0)
				.setSize(total) // 分页
				.addSort("@timestamp", SortOrder.ASC)// 排序
				.get();
		SearchHits hits = searchResponse.getHits();

		return hits;
	}
}
