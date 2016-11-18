package com.open01.logs.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.loader.JsonSettingsLoader;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
import org.junit.Test;

import com.open01.logs.model.Serch;
import com.open01.logs.util.CityUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Searchtest {
	static TransportClient client;
	private static final String BACKGROUND_COLOR = "FFFF00";
	SearchResponse response = null;
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
	 * 时间转化为yyyy-MM-dd'T'HH:mm:ss.SSS'Z
	 */
	public String getISO8601Timestamp(Date date) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(tz);
		String nowAsISO = df.format(date);
		return nowAsISO;
	}

	/**
	 * 3：查看集群信息 注意我的集群结构是： 111的elasticsearch.yml中指定为主节点能存储数据，
	 * 128的elasticsearch.yml中指定不为主节点只能存储数据。
	 * 所有控制台只打印了192.168.79.111,2,3,4,5,只能获取数据节点
	 * 
	 */
	public void searchdatastorage() {
		GetResponse responsere = client.prepareGet("libai8", "logs", "AVdPuwX7bCF6wQcOKsNY").execute().actionGet();
		System.out.println("完成读取--" + responsere.getSourceAsString());
		List<DiscoveryNode> connectedNodes = client.connectedNodes();
		for (DiscoveryNode node : connectedNodes) {
			System.out.println(node.getHostAddress());
		}
		GetResponse getResponse = client.prepareGet("libai8", "logs", "AVdPuwX7bCF6wQcOKsNY").get();
		System.out.println(getResponse.getSourceAsString());
	}

	/*
	 * c创建mapping
	 * 
	 */
	@Test
	public void buildIndexMapping() throws IOException {
		Map<String, Object> settings = new HashMap<>();  
        settings.put("number_of_shards", 3);//分片数量  
        settings.put("number_of_replicas", 0);//复制数量  
        //在本例中主要得注意,ttl及timestamp如何用java ,这些字段的具体含义,请去到es官网查看  
        CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate("wts");  
        cib.setSettings(settings);  
  
        XContentBuilder mapping = XContentFactory.jsonBuilder()  
                .startObject()
                        //properties下定义的name等等就是属于我们需要的自定义字段了,相当于数据库中的表字段 ,此处相当于创建数据库表  
                .startObject("properties")  
                .startObject("message").field("type", "string").field("index", "not_analyzed").endObject()  
                .endObject()
                .endObject();  
        cib.addMapping("wts", mapping);  
        cib.execute().actionGet();
		/*client.admin().indices().prepareCreate("wts").execute().actionGet();
		new XContentFactory();
	    XContentBuilder builder=XContentFactory.jsonBuilder()
	    	    .startObject()

	    	        .startObject("wts")

	    	            .startObject("properties")

	    	            .startObject("message").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()

	    	          //  .startObject("kw").field("type", "string").field("store", "yes").field("indexAnalyzer", "ik").field("searchAnalyzer", "ik").endObject()

	    	         //   .startObject("edate").field("type", "date").field("store", "yes").field("indexAnalyzer", "ik").field("searchAnalyzer", "ik").endObject()

	    	            .endObject()

	    	        .endObject()

	    	    .endObject();

	    	    PutMappingRequest mapping = Requests.putMappingRequest("wts").type("1").source(builder);

	    	    client.admin().indices().putMapping(mapping).actionGet();

	    	    client.close();*/
	}
	/*
	 * 求索引的数据总数
	 * 
	 */
	public void count() {
		long count = client.prepareCount("libai8").get().getCount();
		System.out.println(count);
	}

	/*
	 * 根据时间范围搜索不同的值 通过prepareSearch查询索引库
	 * setQuery(QueryBuilders.matchQuery("name", "jack"))
	 * setSearchType(SearchType.QUERY_THEN_FETCH)
	 */
	public void boolqueryANDHighlighted(String inputval, String starttime, String endtime) throws ParseException {
		SearchRequestBuilder requestBuilder=null;
	/*	String[] cArray = new String[]{"6","7","8"};
		System.out.println(cArray.toString());*/
		requestBuilder = client.prepareSearch("4").setTypes("4");
		MatchQueryBuilder termsQuery = QueryBuilders.matchQuery("message", inputval);
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		boolQuery.must(QueryBuilders.rangeQuery("@timestamp").from(starttime).to(endtime)).must(termsQuery);
		SearchResponse searchResponse = requestBuilder.setQuery(boolQuery)// 查询所有
				.setSearchType(SearchType.QUERY_THEN_FETCH)
				.addHighlightedField("message", 100000, 1000000)
				.addHighlightedField("@timestamp")
				.setHighlighterPreTags("<span style='background-color:" + BACKGROUND_COLOR + "'>")
				.setHighlighterPostTags("</span>").setFrom(0).setSize(10)// 分页
				.addSort("@timestamp", SortOrder.ASC)// 排序
				.get();
		SearchHits hits = searchResponse.getHits();
		long total = hits.getTotalHits();
		String of = String.valueOf(total);
	     System.out.println(of);
		String message = "";
		int i = 0;
		Serch serch = new Serch();
		SearchHit[] searchHits = hits.getHits();
			for (SearchHit hit : searchHits) {
				String sourceAsString = hit.getSourceAsString();
				JSONObject jsStr = JSONObject.fromObject(sourceAsString);
				String jsID =jsStr.getString("@timestamp");
				Searchtest searchtest=new Searchtest();
				String gtmToLocal = searchtest.GTMToLocal(jsID);
				  for (Map.Entry<String, HighlightField> hightlight :hit.getHighlightFields().entrySet()) { 
					  for (Text text :hightlight.getValue().fragments()) { 
						  message = text.string();
						  i++;
						  } 
					  }
				
				System.out.println(message);
				serch.setOrder(i);
				serch.setDatatime("2016-8-7 14:23:36");
				serch.setMessage(message);
			}
	}

	/*
	 * 相差
	 */
	public void countDistinctByField(String starttime, String endtime) {
		Searchtest searchtest = new Searchtest();
		SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf0.parse(starttime);
			date2 = sdf0.parse(endtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String iso8601Timestamp = searchtest.getISO8601Timestamp(date1);
		String iso8601Timestamp2 = searchtest.getISO8601Timestamp(date2);
		System.out.println(iso8601Timestamp);
		System.out.println(iso8601Timestamp2);
		Interval interval = new Interval(date1.getTime(), date2.getTime());
		Period period = interval.toPeriod();
		int year = period.getYears();
		int month = period.getMonths();
		int day = period.getDays() + period.getWeeks() * 7;
		int hour = period.getHours();
		int minute = period.getMinutes();
		System.out.print("相差:" + year + "年" + "\t" + month + "月\t" + day + "日\t" + hour + "小时\t" + minute + "分钟\t");
		SearchRequestBuilder search = client.prepareSearch("1").setTypes("6");
		search.setQuery(QueryBuilders.rangeQuery("@timestamp").from(iso8601Timestamp).to(iso8601Timestamp2));
		search.setSize(0);
		// 一级分组字段
		DateHistogramBuilder dateagg = AggregationBuilders.dateHistogram("dateagg");
		dateagg.field("@timestamp");// 聚合时间字段
		if (year > 1 || (year == 1 && (month != 0 || hour != 0 || day != 0 || minute != 0))) {
			// 年
			dateagg.interval(DateHistogramInterval.YEAR);
			dateagg.format("yyyy");
			dateagg.timeZone("Asia/Shanghai");
			search.addAggregation(dateagg);
		}	else if(year == 1 &&(month ==0 || hour ==0 || day ==0 || minute ==0)){
			//月
			dateagg.interval(DateHistogramInterval.MONTH);
			dateagg.format("yyyy-MM");
			dateagg.timeZone("Asia/Shanghai");
			search.addAggregation(dateagg);
		}else if(year == 0){
			if(month >1 || (month == 1 &&(hour !=0 || day !=0 || minute !=0))){
				//月
				dateagg.interval(DateHistogramInterval.MONTH);
				dateagg.format("yyyy-MM");
				dateagg.timeZone("Asia/Shanghai");
				search.addAggregation(dateagg);
			}
			else if(month ==1 &&(hour ==0 || day ==0 || minute ==0)){
				//天
				dateagg.interval(DateHistogramInterval.DAY);
				dateagg.format("yyyy-MM-dd");
				dateagg.timeZone("Asia/Shanghai");
				search.addAggregation(dateagg);
			}
			else if(month == 0){
				if(day >1 ||(day ==1 &&(hour !=0 || minute !=0))){
					//天
					dateagg.interval(DateHistogramInterval.DAY);
					dateagg.format("yyyy-MM-dd");
					dateagg.timeZone("Asia/Shanghai");
					search.addAggregation(dateagg);
				}
				else if(day == 1 &&(hour ==0 || minute ==0)){
					//小时
					dateagg.interval(DateHistogramInterval.HOUR);
					dateagg.format("yyyy-MM-dd HH");
					dateagg.timeZone("Asia/Shanghai");
					search.addAggregation(dateagg);
				}
				else if(day == 0){
					if(hour >1 ||(hour ==1 &&minute !=0)){
						//小时
						dateagg.interval(DateHistogramInterval.HOUR);
						dateagg.format("yyyy-MM-dd HH");
						dateagg.timeZone("Asia/Shanghai");
						search.addAggregation(dateagg);
					}
					else{
						//分钟
						dateagg.interval(DateHistogramInterval.MINUTE);
						dateagg.format("yyyy-MM-dd HH:mm"); 
						dateagg.timeZone("Asia/Shanghai");
						search.addAggregation(dateagg);
					}
				}
			}
		}
		// dateagg.interval(DateHistogramInterval.HOUR);//按小时聚合
		// dateagg.interval(DateHistogramInterval.DAY);//按天聚合
		// dateagg.interval(DateHistogramInterval.YEAR);//按年聚合
		// dateagg.interval(DateHistogramInterval.SECOND);//按秒聚合
		// dateagg.interval(DateHistogramInterval.MINUTE);//按分聚合
		// dateagg.interval(DateHistogramInterval.MONTH);//按月聚合
		// dateagg.format("yyyy-MM-dd HH"); //格式化时间 时
		// dateagg.format("yyyy-MM-dd"); //格式化时间 天
		// dateagg.format("yyyy-MM-dd HH:mm"); //格式化时间 分
		// dateagg.format("yyyy-MM-dd HH:mm:ss"); //格式化时间 秒
		// dateagg.format("yyyy-MM"); //格式化时间 月
		// dateagg.format("yyyy"); //格式化时间 年
		// dateagg.timeZone("Asia/Shanghai");//
		// 设置时区，注意如果程序部署在其他国家使用时，使用Joda-Time来动态获取时区
		// new DateTime().getZone()
		// 向search请求添加
		// search.addAggregation(dateagg);
		// 获取结果
		SearchResponse r = search.get();
		Histogram h = r.getAggregations().get("dateagg");
		// 得到一级聚合结果里面的分桶集合
		List<Histogram.Bucket> buckets = (List<Histogram.Bucket>) h.getBuckets();
		// 遍历分桶集
		for (Histogram.Bucket b : buckets) {
			// 如果设置日期的format的时候，需要使用keyAsString取出，否则获取的是UTC的标准时间
			System.out.println(b.getKeyAsString() + "  " + b.getDocCount());
		}
	}
	/*
	 * 
	 * setQuery(QueryBuilders.matchQuery("name", "jack"))
	 * setSearchType(SearchType.QUERY_THEN_FETCH)
	 * 地图
	 */
	public void baobiao(String inputval, String starttime, String endtime) throws ParseException {
		SearchRequestBuilder requestBuilder = client.prepareSearch("1").setTypes("6");
		JSONArray resultArray = new JSONArray();
		SearchResponse searchResponse = null;
		for (CityUtil cityName : CityUtil.values()) {
			JSONObject resultObj = new JSONObject();
			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
			MatchQueryBuilder termsQuery = QueryBuilders.matchQuery("message", inputval);
			MatchQueryBuilder termsQuery1 = QueryBuilders.matchQuery("geoip.real_region_name", cityName.getIndex());
			boolQuery.must(QueryBuilders.rangeQuery("@timestamp").from(starttime).to(endtime)).must(termsQuery).must(termsQuery1);;
		    searchResponse = requestBuilder.setQuery(boolQuery)// 查询所有
					.setSearchType(SearchType.QUERY_THEN_FETCH).addHighlightedField("message", 400, 20)
					.addHighlightedField("@timestamp")
					.setHighlighterPreTags("<span style='background-color:" + BACKGROUND_COLOR + "'>")
					.setHighlighterPostTags("</span>").setFrom(0).setSize(10000000)// 分页
					.addSort("@timestamp", SortOrder.DESC)// 排序
					.get();
		    DateHistogramBuilder dateagg = AggregationBuilders.dateHistogram("dateagg");
		    requestBuilder.addAggregation(dateagg);
			dateagg.field("geoip.real_region_name");
			dateagg.format(cityName.getIndex());
			resultObj.put("value", searchResponse.getHits().getHits().length);
			resultArray.add(resultObj);
		}
		
		System.out.println("returnList:" + resultArray.toString());
	}
	/*
	* GTM转本地时间
	*
	* @param GTMDate
	* @return
	*/
	public String GTMToLocal(String GTMDate) {
		int tIndex = GTMDate.indexOf("T");
		String dateTemp = GTMDate.substring(0, tIndex);
		String timeTemp = GTMDate.substring(tIndex + 1, GTMDate.length() - 6);
		String convertString = dateTemp + " " + timeTemp;

		SimpleDateFormat format;
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		Date result_date;
		long result_time = 0;

		if (null == GTMDate) {
		return GTMDate;
		} else {
		try {
		format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
		result_date = format.parse(convertString);
		result_time = result_date.getTime();
		format.setTimeZone(TimeZone.getDefault());
		return format.format(result_time);
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		return GTMDate;
		}
	public static void main(String[] args) throws ParseException {
		/**
		 * 
		 * 传入Data类型日期，返回字符串类型时间（ISO8601标准时间）
		 * 
		 * @param date
		 * @return
		 */
			Searchtest searchtest = new Searchtest();
			String abc = "2013-03-02 00:00:00";
			String ccc = "2016-12-25 00:00:17";
		  SimpleDateFormat dateFormat = new
		  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  SimpleDateFormat dateFormat1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date date2 = null,date1 = null;
		  try { 
			    date2 = dateFormat.parse(abc); 
		        date1 =dateFormat1.parse(ccc); 
		  }
		  catch (ParseException e1) {
		  e1.printStackTrace();
		  } 
		  String iso8601Timestamp =searchtest.getISO8601Timestamp(date2);
		  String iso8601Timestamp2 =searchtest.getISO8601Timestamp(date1); //
		  //searchtest.searchdatastorage(); 
		 // searchtest.count();
		  //System.out.println(iso8601Timestamp);
		  //System.out.println(iso8601Timestamp2);
		//searchtest.boolqueryANDHighlighted("200", iso8601Timestamp,iso8601Timestamp2);
		//时间聚合searchtest.countDistinctByField(abc, ccc);
		searchtest.boolqueryANDHighlighted("1", iso8601Timestamp, iso8601Timestamp2);
	}
}
