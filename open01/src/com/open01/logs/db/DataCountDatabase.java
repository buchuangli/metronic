package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.open01.logs.model.TimeSlot;

public class DataCountDatabase extends Open01Database {

	private TreeMap<Long, List<TimeSlot>> timeSlots = new TreeMap<Long, List<TimeSlot>>();

	public DataCountDatabase() {

		super();

	}
/*
 * 按时间段显示间隔时间内的流量统计
 */
	public List<TimeSlot>  getDailyData(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT time, SUM(bytes) FROM stat_data_daily WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? GROUP BY time;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getYearData(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT time, SUM(bytes) FROM stat_data_year WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? GROUP BY time;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMonthData(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT time, SUM(bytes) FROM stat_data_month WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? GROUP BY time;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getHourData(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT time, SUM(bytes) FROM stat_data_hour WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? GROUP BY time;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMinuteData(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT time, SUM(bytes) FROM stat_data_minute WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? GROUP BY time;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	
	
	
	
	
	private final class PreparedStatementProcessor_1 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id;

		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
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
				timeSlot.setDataNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	
	/*
	 * 统计（某年，月，日，十，分）的流量总数
	 */
	public Long getYearAllData(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT SUM(bytes) FROM stat_data_year WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ?",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
				result_set_processor_2);
		
		return result_set_processor_2.allBytes;
	}
	public Long getMonthAllData(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT SUM(bytes) FROM stat_data_month WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ?",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
				result_set_processor_2);
		
		return result_set_processor_2.allBytes;
	}
	public Long getDailyAllData(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT SUM(bytes) FROM stat_data_daily WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ?",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
				result_set_processor_2);
		
		return result_set_processor_2.allBytes;
	}
	public Long getHourAllData(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT SUM(bytes) FROM stat_data_hour WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ?",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
				result_set_processor_2);
		
		return result_set_processor_2.allBytes;
	}
	public Long getMinuteAllData(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT SUM(bytes) FROM stat_data_minute WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ?",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
				result_set_processor_2);
		
		return result_set_processor_2.allBytes;
	}
	private final class PreparedStatementProcessor_2 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id;
		
		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			return this;
		}
		
		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
		}
	}
	
	private final class ResultSetProcessor_2 extends ResultSetProcessor {
		long allBytes;
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				allBytes = resultSet.getLong(1);
			}
		}
	}
	
	
	/*
	 * 查询top50Ip的流量统计
	 */
	public List<TimeSlot>  getTopIpYear(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT c_ip, SUM(bytes) as ipBytes  FROM stat_data_year WHERE time >= ? AND time < ?"
				+ " AND client_id = ? AND project_id = ? GROUP BY c_ip order by ipBytes desc limit 50;",
				prepared_statement_processor_3.init(startTime, endTime, client_id, project_id),
				result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getTopIpMonth(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT c_ip, SUM(bytes) as ipBytes  FROM stat_data_month WHERE time >= ? AND time < ?"
						+ " AND client_id = ? AND project_id = ? GROUP BY c_ip order by ipBytes desc limit 50;",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getTopIpDaily(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT c_ip, SUM(bytes) as ipBytes  FROM stat_data_daily WHERE time >= ? AND time < ?"
						+ " AND client_id = ? AND project_id = ? GROUP BY c_ip order by ipBytes desc limit 50;",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getTopIpHour(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT c_ip, SUM(bytes) as ipBytes  FROM stat_data_hour WHERE time >= ? AND time < ?"
						+ " AND client_id = ? AND project_id = ? GROUP BY c_ip order by ipBytes desc limit 50;",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getTopIpMinute(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT c_ip, SUM(bytes) as ipBytes  FROM stat_data_minute WHERE time >= ? AND time < ?"
						+ " AND client_id = ? AND project_id = ? GROUP BY c_ip order by ipBytes desc limit 50;",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	private final class PreparedStatementProcessor_3 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id;

		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
		}
	}

	private final class ResultSetProcessor_3 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setIp(resultSet.getString(1));
				timeSlot.setIpNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	
	/*
	 * 通过url查询流量
	 */
	public List<TimeSlot>  getBytesByUrlYear(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT time,bytes FROM stat_data_url_year  WHERE time >= ? AND time < ? AND client_id = ? "
						+ "AND project_id = ? AND url = ? order by time;",
				prepared_statement_processor_4.init(startTime, endTime, client_id, project_id,url),
				result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getBytesByUrlMonth(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT time,bytes FROM stat_data_url_month  WHERE time >= ? AND time < ? AND client_id = ? "
						+ "AND project_id = ? AND url = ? order by time;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getBytesByUrlDaily(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT time,bytes FROM stat_data_url_daily  WHERE time >= ? AND time < ? AND client_id = ? "
						+ "AND project_id = ? AND url = ? order by time;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getBytesByUrlHour(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT time,bytes FROM stat_data_url_hour  WHERE time >= ? AND time < ? AND client_id = ? "
				+ "AND project_id = ? AND url = ? order by time;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getBytesByUrlMinute(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT time,bytes FROM stat_data_url_minute  WHERE time >= ? AND time < ? AND client_id = ? "
						+ "AND project_id = ? AND url = ? order by time;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	
	private final class PreparedStatementProcessor_4 extends PreparedStatementProcessor {
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
			statement.setString(5, url);
		}
	}
	
	private final class ResultSetProcessor_4 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setTime(resultSet.getLong(1));
				timeSlot.setDataNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	
	/*
	 * 通过url查询流量
	 */
	public List<TimeSlot>  getTopUrlYear(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT url, SUM(bytes) as urlBytes  FROM stat_data_url_year WHERE time >= ? AND time < ?"
						+ " AND client_id = ? AND project_id = ? GROUP BY url order by urlBytes desc limit 50;",
						prepared_statement_processor_5.init(startTime, endTime, client_id, project_id),
						result_set_processor_5);
		
		return result_set_processor_5.tsList;
	}
	public List<TimeSlot>  getTopUrlMonth(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT url, SUM(bytes) as urlBytes  FROM stat_data_url_month WHERE time >= ? AND time < ?"
						+ " AND client_id = ? AND project_id = ? GROUP BY url order by urlBytes desc limit 50;",
						prepared_statement_processor_5.init(startTime, endTime, client_id, project_id),
						result_set_processor_5);
		
		return result_set_processor_5.tsList;
	}
	public List<TimeSlot>  getTopUrlDaily(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT url, SUM(bytes) as urlBytes  FROM stat_data_url_daily WHERE time >= ? AND time < ?"
						+ " AND client_id = ? AND project_id = ? GROUP BY url order by urlBytes desc limit 50;",
						prepared_statement_processor_5.init(startTime, endTime, client_id, project_id),
						result_set_processor_5);
		
		return result_set_processor_5.tsList;
	}
	public List<TimeSlot>  getTopUrlHour(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT url, SUM(bytes) as urlBytes  FROM stat_data_url_hour WHERE time >= ? AND time < ?"
						+ " AND client_id = ? AND project_id = ? GROUP BY url order by urlBytes desc limit 50;",
						prepared_statement_processor_5.init(startTime, endTime, client_id, project_id),
						result_set_processor_5);
		
		return result_set_processor_5.tsList;
	}
	public List<TimeSlot>  getTopUrlMinute(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT url, SUM(bytes) as urlBytes  FROM stat_data_url_minute WHERE time >= ? AND time < ?"
						+ " AND client_id = ? AND project_id = ? GROUP BY url order by urlBytes desc limit 50;",
						prepared_statement_processor_5.init(startTime, endTime, client_id, project_id),
						result_set_processor_5);
		
		return result_set_processor_5.tsList;
	}
	private final class PreparedStatementProcessor_5 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id;
		
		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			return this;
		}
		
		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
		}
	}
	
	private final class ResultSetProcessor_5 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setUrl(resultSet.getString(1));
				timeSlot.setDataNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
public  List<TimeSlot> getGeographyPVYear(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_10 result_set_processor_10 = new ResultSetProcessor_10();
		PreparedStatementProcessor_10 prepared_statement_processor_10 = new PreparedStatementProcessor_10();
		
		executeQuery(
				"SELECT province, SUM(counts) FROM stat_ip_year WHERE TIME >= ? "
						+ "AND TIME < ? AND client_id = ? AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_10.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_10);
		
		return result_set_processor_10.tsList;
		
	}
	
	public  List<TimeSlot> getGeographyPVMonth(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_10 result_set_processor_10 = new ResultSetProcessor_10();
		PreparedStatementProcessor_10 prepared_statement_processor_10 = new PreparedStatementProcessor_10();
		
		executeQuery(
				"SELECT province, SUM(counts) FROM stat_ip_month WHERE TIME >= ? "
						+ "AND TIME < ? AND client_id = ? AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_10.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_10);
		
		return result_set_processor_10.tsList;
		
	}
	
	public  List<TimeSlot> getGeographyPVDaily(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_10 result_set_processor_10 = new ResultSetProcessor_10();
		PreparedStatementProcessor_10 prepared_statement_processor_10 = new PreparedStatementProcessor_10();
		
		executeQuery(
				"SELECT province, SUM(counts) FROM stat_ip_daily WHERE TIME >= ? "
						+ "AND TIME < ? AND client_id = ? AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_10.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_10);
		
		return result_set_processor_10.tsList;
		
	}
	
	public  List<TimeSlot> getGeographyPVHour(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_10 result_set_processor_10 = new ResultSetProcessor_10();
		PreparedStatementProcessor_10 prepared_statement_processor_10 = new PreparedStatementProcessor_10();
		
		executeQuery(
				"SELECT province, SUM(counts) FROM stat_ip_hour WHERE TIME >= ? "
						+ "AND TIME < ? AND client_id = ? AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_10.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_10);
		
		return result_set_processor_10.tsList;
		
	}
	
	public  List<TimeSlot> getGeographyPVMinute(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_10 result_set_processor_10 = new ResultSetProcessor_10();
		PreparedStatementProcessor_10 prepared_statement_processor_10 = new PreparedStatementProcessor_10();
		
		executeQuery(
				"SELECT province, SUM(counts) FROM stat_ip_minute WHERE TIME >= ? "
						+ "AND TIME < ? AND client_id = ? AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_10.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_10);
		
		return result_set_processor_10.tsList;
		
	}
	
	private final class PreparedStatementProcessor_10 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id;
		String country;
		
		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id,String country) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			this.country=country;
			return this;
		}
		
		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
			statement.setString(5, country);
		}
	}
	
	private final class ResultSetProcessor_10 extends ResultSetProcessor {
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
	
	
public  List<TimeSlot> getGeographyIpYear(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_11 result_set_processor_11 = new ResultSetProcessor_11();
		PreparedStatementProcessor_11 prepared_statement_processor_11 = new PreparedStatementProcessor_11();
		
		executeQuery(
						"SELECT province,count(DISTINCT c_ip) FROM stat_ip_year WHERE TIME >= ? "
								+ "AND TIME < ? AND client_id = ? AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_11.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_11);
		
		return result_set_processor_11.tsList;
		
	}
	
	public  List<TimeSlot> getGeographyIpMonth(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_11 result_set_processor_11 = new ResultSetProcessor_11();
		PreparedStatementProcessor_11 prepared_statement_processor_11 = new PreparedStatementProcessor_11();
		
		executeQuery(
				"SELECT province,count(DISTINCT c_ip) FROM stat_ip_month WHERE TIME >= ? "
						+ "AND TIME < ? AND client_id = ? AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_11.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_11);
		
		return result_set_processor_11.tsList;
		
	}
	
	public  List<TimeSlot> getGeographyIpDaily(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_11 result_set_processor_11 = new ResultSetProcessor_11();
		PreparedStatementProcessor_11 prepared_statement_processor_11 = new PreparedStatementProcessor_11();
		
		executeQuery(
				"SELECT province,count(DISTINCT c_ip) FROM stat_ip_daily WHERE TIME >= ? "
						+ "AND TIME < ? AND client_id = ? AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_11.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_11);
		
		return result_set_processor_11.tsList;
		
	}
	
	public  List<TimeSlot> getGeographyIpHour(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_11 result_set_processor_11 = new ResultSetProcessor_11();
		PreparedStatementProcessor_11 prepared_statement_processor_11 = new PreparedStatementProcessor_11();
		
		executeQuery(
				"SELECT province,SUM(bytes) FROM stat_data_hour WHERE TIME >= ? AND TIME < ? AND client_id = ? "
				+ "AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_11.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_11);
		
		return result_set_processor_11.tsList;
		
	}
	
	public  List<TimeSlot> getGeographyIpMinute(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_11 result_set_processor_11 = new ResultSetProcessor_11();
		PreparedStatementProcessor_11 prepared_statement_processor_11 = new PreparedStatementProcessor_11();
		
		executeQuery(
				"SELECT province,count(DISTINCT c_ip) FROM stat_ip_minute WHERE TIME >= ? "
						+ "AND TIME < ? AND client_id = ? AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_11.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_11);
		
		return result_set_processor_11.tsList;
		
	}
	private final class PreparedStatementProcessor_11 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id;
		String country;
		
		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id,String country) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			this.country=country;
			return this;
		}
		
		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
			statement.setString(5, country);
		}
	}
	
	private final class ResultSetProcessor_11 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setProvince(resultSet.getString(1));
				timeSlot.setIpNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	
public  List<TimeSlot> getGeographyDataYear(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_12 result_set_processor_12 = new ResultSetProcessor_12();
		PreparedStatementProcessor_12 prepared_statement_processor_11 = new PreparedStatementProcessor_12();
		
		executeQuery(
				"SELECT province,SUM(bytes) FROM stat_data_year WHERE TIME >= ? AND TIME < ? AND client_id = ? "
						+ "AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_11.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_12);
		
		return result_set_processor_12.tsList;
		
	}
	public  List<TimeSlot> getGeographyDataMonth(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_12 result_set_processor_12 = new ResultSetProcessor_12();
		PreparedStatementProcessor_12 prepared_statement_processor_11 = new PreparedStatementProcessor_12();
		
		executeQuery(
				"SELECT province,SUM(bytes) FROM stat_data_month WHERE TIME >= ? AND TIME < ? AND client_id = ? "
						+ "AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_11.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_12);
		
		return result_set_processor_12.tsList;
		
	}
	public  List<TimeSlot> getGeographyDataDaily(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_12 result_set_processor_12 = new ResultSetProcessor_12();
		PreparedStatementProcessor_12 prepared_statement_processor_11 = new PreparedStatementProcessor_12();
		
		executeQuery(
				"SELECT province,SUM(bytes) FROM stat_data_daily WHERE TIME >= ? AND TIME < ? AND client_id = ? "
						+ "AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_11.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_12);
		
		return result_set_processor_12.tsList;
		
	}
	public  List<TimeSlot> getGeographyDataHour(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_12 result_set_processor_12 = new ResultSetProcessor_12();
		PreparedStatementProcessor_12 prepared_statement_processor_11 = new PreparedStatementProcessor_12();
		
		executeQuery(
				"SELECT province,SUM(bytes) FROM stat_data_hour WHERE TIME >= ? AND TIME < ? AND client_id = ? "
						+ "AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_11.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_12);
		
		return result_set_processor_12.tsList;
		
	}
	public  List<TimeSlot> getGeographyDataMinute(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_12 result_set_processor_12 = new ResultSetProcessor_12();
		PreparedStatementProcessor_12 prepared_statement_processor_11 = new PreparedStatementProcessor_12();
		
		executeQuery(
				"SELECT province,SUM(bytes) FROM stat_data_minute WHERE TIME >= ? AND TIME < ? AND client_id = ? "
						+ "AND project_id = ? AND country =? GROUP BY province",
						prepared_statement_processor_11.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_12);
		
		return result_set_processor_12.tsList;
		
	}
	private final class PreparedStatementProcessor_12 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id;
		String country;
		
		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id,String country) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			this.country=country;
			return this;
		}
		
		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
			statement.setString(5, country);
		}
	}
	
	private final class ResultSetProcessor_12 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setProvince(resultSet.getString(1));
				timeSlot.setDataNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	
}