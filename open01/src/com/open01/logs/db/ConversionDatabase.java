package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.open01.logs.model.Conversion;
import com.open01.logs.model.TimeSlot;

public class ConversionDatabase extends Open01Database {

	public ConversionDatabase() {

		super();

	}
	/*
	 * 转化率时间轨迹
	 * 
	 */
		public Long getYearSumPageView(long startTime, long endTime, int client_id,int project_id,String url) {
			
			ResultSetProcessor_0 result_set_processor_0 = new ResultSetProcessor_0();
			PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
			
			executeQuery(
					"SELECT SUM(counts) FROM stat_pv_year  WHERE  "
							+ " TIME >= ? AND TIME < ? AND client_id = ?"
							+ " AND project_id = ? AND url =? ",
					prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
					result_set_processor_0);
			
			return result_set_processor_0.pvCount;
		}
		public Long getMonthSumPageView(long startTime, long endTime, int client_id,int project_id,String url) {
			
			ResultSetProcessor_0 result_set_processor_0 = new ResultSetProcessor_0();
			PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
			
			executeQuery(
					"SELECT SUM(counts) FROM stat_pv_month  WHERE  "
							+ " TIME >= ? AND TIME < ? AND client_id = ?"
							+ " AND project_id = ? AND url =? ",
					prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
					result_set_processor_0);
			
			return result_set_processor_0.pvCount;
		}
		public Long getDailySumPageView(long startTime, long endTime, int client_id,int project_id,String url) {
			
			ResultSetProcessor_0 result_set_processor_0 = new ResultSetProcessor_0();
			PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
			
			executeQuery(
					"SELECT SUM(counts) FROM stat_pv_daily  WHERE  "
							+ " TIME >= ? AND TIME < ? AND client_id = ?"
							+ " AND project_id = ? AND url =? ",
					prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
					result_set_processor_0);
			
			return result_set_processor_0.pvCount;
		}
		public Long getHourSumPageView(long startTime, long endTime, int client_id,int project_id,String url) {
			
			ResultSetProcessor_0 result_set_processor_0 = new ResultSetProcessor_0();
			PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
			
			executeQuery(
					"SELECT SUM(counts) FROM stat_pv_hour  WHERE  "
							+ " TIME >= ? AND TIME < ? AND client_id = ?"
							+ " AND project_id = ? AND url =? ",
					prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
					result_set_processor_0);
			
			return result_set_processor_0.pvCount;
		}
		public Long getMinuteSumPageView(long startTime, long endTime, int client_id,int project_id,String url) {
			
			ResultSetProcessor_0 result_set_processor_0 = new ResultSetProcessor_0();
			PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
			
			executeQuery(
					"SELECT SUM(counts) FROM stat_pv_minute  WHERE  "
							+ " TIME >= ? AND TIME < ? AND client_id = ?"
							+ " AND project_id = ? AND url =? ",
					prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
					result_set_processor_0);
			
			return result_set_processor_0.pvCount;
		}

		
		
/*
 * 转化率时间轨迹
 * 
 */
	public List<TimeSlot>  getYearStatus(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_pv_year  WHERE  "
						+ " TIME >= ? AND TIME < ? AND client_id = ?"
						+ " AND project_id = ? AND url =?  GROUP BY TIME;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMonthStatus(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_pv_month  WHERE  "
						+ " TIME >= ? AND TIME < ? AND client_id = ?"
						+ " AND project_id = ? AND url =?  GROUP BY TIME;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getDailyStatus(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_pv_daily  WHERE  "
				+ " TIME >= ? AND TIME < ? AND client_id = ?"
				+ " AND project_id = ? AND url =?  GROUP BY TIME;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getHourStatus(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_pv_hour  WHERE  "
						+ " TIME >= ? AND TIME < ? AND client_id = ?"
						+ " AND project_id = ? AND url =?  GROUP BY TIME;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	
	public List<TimeSlot>  getMinuteStatus(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_pv_minute  WHERE  "
						+ " TIME >= ? AND TIME < ? AND client_id = ?"
						+ " AND project_id = ? AND url =?  GROUP BY TIME;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	
	/*
	 * 转化率访问来源
	 * 
	 */
	public List<TimeSlot>  getYearReferrer(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"select time,sum(counts) from stat_referrer_url_year  WHERE time >= ?"
						+ " AND time < ?  AND client_id = ? AND project_id = ? AND url like"
						+ " ? and url like '%.html%'  group by time;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMonthReferrer(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"select time,sum(counts) from stat_referrer_url_month  WHERE time >= ?"
						+ " AND time < ?  AND client_id = ? AND project_id = ? AND url like"
						+ " ? and url like '%.html%'  group by time;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getDailyReferrer(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"select time,sum(counts) from stat_referrer_url_daily  WHERE time >= ?"
						+ " AND time < ?  AND client_id = ? AND project_id = ? AND url like"
						+ " ? and url like '%.html%'  group by time;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getHourReferrer(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"select time,sum(counts) from stat_referrer_url_hour  WHERE time >= ?"
						+ " AND time < ?  AND client_id = ? AND project_id = ? AND url like"
						+ " ? and url like '%.html%'  group by time;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMinuteReferrer(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"select time,sum(counts) from stat_referrer_url_Minute  WHERE time >= ?"
						+ " AND time < ?  AND client_id = ? AND project_id = ? AND url like"
						+ " ? and url like '%.html%'  group by time;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	/*
	 * 浏览器访问来源
	 * 
	 */
	public List<TimeSlot>  getYearBrowser(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TYPES,SUM(counts) AS brCount FROM stat_browser_url_year WHERE TIME >= ?  AND TIME < ? AND client_id = ? "
						+ "AND project_id = ? AND url =? GROUP BY TYPES;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getMonthBrowser(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TYPES,SUM(counts) AS brCount FROM stat_browser_url_month WHERE TIME >= ?  AND TIME < ? AND client_id = ? "
				+ "AND project_id = ? AND url =? GROUP BY TYPES;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getDailyBrowser(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TYPES,SUM(counts) AS brCount FROM stat_browser_url_daily WHERE TIME >= ?  AND TIME < ? AND client_id = ? "
						+ "AND project_id = ? AND url =? GROUP BY TYPES;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getHourBrowser(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TYPES,SUM(counts) AS brCount FROM stat_browser_url_hour WHERE TIME >= ?  AND TIME < ? AND client_id = ? "
						+ "AND project_id = ? AND url =? GROUP BY TYPES;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getMinuteBrowser(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TYPES,SUM(counts) AS brCount FROM stat_browser_url_minute WHERE TIME >= ?  AND TIME < ? AND client_id = ? "
						+ "AND project_id = ? AND url =? GROUP BY TYPES;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	/*
	 * 操作系统访问来源
	 * 
	 */
	public List<TimeSlot>  getYearOs(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TYPES,SUM(counts) AS brCount FROM stat_os_url_year WHERE TIME >= ?  AND TIME < ?"
				+ " AND client_id = ? AND project_id = ? AND url =? GROUP BY TYPES;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getMonthOs(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TYPES,SUM(counts) AS brCount FROM stat_os_url_month WHERE TIME >= ?  AND TIME < ?"
				+ " AND client_id = ? AND project_id = ? AND url =? GROUP BY TYPES;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getDailyOs(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TYPES,SUM(counts) AS brCount FROM stat_os_url_daily WHERE TIME >= ?  AND TIME < ?"
				+ " AND client_id = ? AND project_id = ? AND url =? GROUP BY TYPES;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getHourOs(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TYPES,SUM(counts) AS brCount FROM stat_os_url_hour WHERE TIME >= ?  AND TIME < ?"
				+ " AND client_id = ? AND project_id = ? AND url =? GROUP BY TYPES;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getMinuteOs(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT TYPES,SUM(counts) AS brCount FROM stat_os_url_minute WHERE TIME >= ?  AND TIME < ?"
				+ " AND client_id = ? AND project_id = ? AND url =? GROUP BY TYPES;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	
	
	
	
	private final class PreparedStatementProcessor_1 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id;
		String url;

		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id,String url) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			this.url=url;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
			statement.setString(5,url);
		}
	}

	private final class ResultSetProcessor_0 extends ResultSetProcessor {
		Long  pvCount=0l;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				pvCount = resultSet.getLong(1);
			}
		}
	}
	
	private final class ResultSetProcessor_1 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setTime(resultSet.getLong(1));
				timeSlot.setPvNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	
	private final class ResultSetProcessor_3 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setBrowserType(resultSet.getString(1));
				timeSlot.setBrowserNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	
	private final class ResultSetProcessor_4 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setOsType(resultSet.getString(1));
				timeSlot.setOsNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	/*
	 * 访问来源地域分析
	 * 
	 */
	public List<TimeSlot>  getYearGe(long startTime, long endTime, int client_id,int project_id,String country,String url) {
		
		ResultSetProcessor_2 result_set_processor_２ = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT province,SUM(counts) FROM stat_pv_url_year WHERE TIME >= ? AND TIME < ? "
				+ "AND client_id =? AND project_id =? AND country= ? AND url =? GROUP BY province ORDER BY CONVERT(province USING gbk);",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,country,url),
						result_set_processor_２);
		
		return result_set_processor_２.tsList;
	}
	public List<TimeSlot>  getMonthGe(long startTime, long endTime, int client_id,int project_id,String country,String url) {
		
		ResultSetProcessor_2 result_set_processor_２ = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT province,SUM(counts) FROM stat_pv_url_month WHERE TIME >= ? AND TIME < ? "
						+ "AND client_id =? AND project_id =? AND country= ? AND url =? GROUP BY province ORDER BY CONVERT(province USING gbk);",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,country,url),
						result_set_processor_２);
		
		return result_set_processor_２.tsList;
	}
	public List<TimeSlot>  getDailyGe(long startTime, long endTime, int client_id,int project_id,String country,String url) {
		
		ResultSetProcessor_2 result_set_processor_２ = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT province,SUM(counts) FROM stat_pv_url_daily WHERE TIME >= ? AND TIME < ? "
						+ "AND client_id =? AND project_id =? AND country= ? AND url =? GROUP BY province ORDER BY CONVERT(province USING gbk);",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,country,url),
						result_set_processor_２);
		
		return result_set_processor_２.tsList;
	}
	public List<TimeSlot>  getHourGe(long startTime, long endTime, int client_id,int project_id,String country,String url) {
		
		ResultSetProcessor_2 result_set_processor_２ = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT province,SUM(counts) FROM stat_pv_url_hour WHERE TIME >= ? AND TIME < ? "
						+ "AND client_id =? AND project_id =? AND country= ? AND url =? GROUP BY province ORDER BY CONVERT(province USING gbk);",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,country,url),
						result_set_processor_２);
		
		return result_set_processor_２.tsList;
	}
	public List<TimeSlot>  getMinuteGe(long startTime, long endTime, int client_id,int project_id,String country,String url) {
		
		ResultSetProcessor_2 result_set_processor_２ = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT province,SUM(counts) FROM stat_pv_url_minute WHERE TIME >= ? AND TIME < ? "
						+ "AND client_id =? AND project_id =? AND country= ? AND url =? GROUP BY province ORDER BY CONVERT(province USING gbk);",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,country,url),
						result_set_processor_２);
		
		return result_set_processor_２.tsList;
	}
	

	private final class PreparedStatementProcessor_2 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id;
		String url,country;
		
		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id,String country,String url) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			this.country=country;
			this.url=url;
			return this;
		}
		
		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
			statement.setString(5, country);
			statement.setString(6,url);
		}
	}
	
	private final class ResultSetProcessor_2 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setProvince(resultSet.getString(1));
				timeSlot.setPvNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	


	public List<Conversion>  getCvrInfo(Conversion conversion) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT user_id ,client_id,project_id,url,description  FROM stat_cvr_info WHERE "
				+ "user_id = ? AND project_id = ? ;",
						prepared_statement_processor_5.init(conversion),
						result_set_processor_5);
		
		return result_set_processor_5.cList;
	}
	public List<String> getCvrUrlList(Conversion conversion) {
		
		ResultSetProcessor_5_1 result_set_processor_5_1 = new ResultSetProcessor_5_1();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT url FROM stat_cvr_info WHERE "
				+ "user_id = ? AND project_id = ? ;",
						prepared_statement_processor_5.init(conversion),
						result_set_processor_5_1);
		
		return result_set_processor_5_1.urlList;
	}
	
	private final class PreparedStatementProcessor_5 extends PreparedStatementProcessor {
		Conversion conversion;
		
		private PreparedStatementProcessor init(Conversion conversion) {
			this.conversion = conversion;
			return this;
		}
		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setInt(1, conversion.getUser_id());
			statement.setInt(2,conversion.getProject_id());
		}
	}
	
	private final class ResultSetProcessor_5 extends ResultSetProcessor {
		List<Conversion> cList=new ArrayList<Conversion>();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			Conversion conversion = null;
			while (resultSet.next()) {
				conversion =  new Conversion();
				conversion.setUser_id(resultSet.getInt(1));
				conversion.setClient_id(resultSet.getInt(2));
				conversion.setProject_id(resultSet.getInt(3));
				conversion.setUrl(resultSet.getString(4));
				conversion.setDescription(resultSet.getString(5));
				cList.add(conversion);
			}
		}
	}
	private final class ResultSetProcessor_5_1 extends ResultSetProcessor {
		List<String> urlList=new ArrayList<String>();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				urlList.add(resultSet.getString(1));
			}
		}
	}
	
	
	
	public Conversion insertCvrInfo(Conversion conversion) {
		ResultSetProcessor_6 result_set_processor_6 = new ResultSetProcessor_6();
		PreparedStatementProcessor_6 prepared_statement_processor_6 = new PreparedStatementProcessor_6();
		executeUpdate(
				"INSERT INTO stat_cvr_info (user_id ,client_id,project_id,url,description ) "
				+ "VALUES(?,?,?,?,?);",
				prepared_statement_processor_6.init(conversion),
				result_set_processor_6);
		return result_set_processor_6.conversion;
		
	}
	private final class PreparedStatementProcessor_6 extends PreparedStatementProcessor {
		Conversion conversion;
		
		private PreparedStatementProcessor init(Conversion conversion) {
			this.conversion = conversion;
			return this;
		}
		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setInt(1, conversion.getUser_id());
			statement.setInt(2,conversion.getClient_id());
			statement.setInt(3, conversion.getProject_id());
			statement.setString(4, conversion.getUrl());
			statement.setString(5, conversion.getDescription());
		}
	}
	
	private final class ResultSetProcessor_6 extends ResultSetProcessor {
		Conversion conversion =  new Conversion();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				conversion.setUser_id(resultSet.getInt(1));
				conversion.setClient_id(resultSet.getInt(2));
				conversion.setProject_id(resultSet.getInt(3));
				conversion.setUrl(resultSet.getString(4));
				conversion.setDescription(resultSet.getString(5));
			}
		}
	}
	
	public Conversion updateCvrInfo(Conversion conversion) {
		ResultSetProcessor_7 result_set_processor_7 = new ResultSetProcessor_7();
		PreparedStatementProcessor_7 prepared_statement_processor_7 = new PreparedStatementProcessor_7();
		executeUpdate(
				"UPDATE stat_cvr_info SET url = ?,description = ? WHERE user_id = ? AND project_id =?",
				prepared_statement_processor_7.init(conversion),
				result_set_processor_7);
		return result_set_processor_7.conversion;
		
	}
	private final class PreparedStatementProcessor_7 extends PreparedStatementProcessor {
		Conversion conversion;
		
		private PreparedStatementProcessor init(Conversion conversion) {
			this.conversion = conversion;
			return this;
		}
		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setString(1, conversion.getUrl());
			statement.setString(2, conversion.getDescription());
			statement.setInt(3, conversion.getUser_id());
			statement.setInt(4, conversion.getProject_id());
		}
	}
	
	private final class ResultSetProcessor_7 extends ResultSetProcessor {
		Conversion conversion =  new Conversion();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				conversion.setUser_id(resultSet.getInt(1));
				conversion.setClient_id(resultSet.getInt(2));
				conversion.setProject_id(resultSet.getInt(3));
				conversion.setUrl(resultSet.getString(4));
				conversion.setDescription(resultSet.getString(5));
			}
		}
	}
	
	public void deleteCvrInfo(int user_id,int project_id) {
		executeUpdate(" DELETE FROM  stat_cvr_info WHERE  user_id = "+user_id+" AND project_id ="+project_id);
		
	}
}
