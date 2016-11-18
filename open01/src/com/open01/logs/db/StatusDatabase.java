package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.open01.logs.model.TimeSlot;

public class StatusDatabase extends Open01Database {

	public StatusDatabase() {

		super();

	}

	/*
	 * 查询所有状态在（年，月，日，十，分）上出现的次数
	 */
	public List<TimeSlot>  getDailyStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) from stat_status_daily WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMonthStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) from stat_status_month WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getYearStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) from stat_status_year WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMinuteStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) from stat_status_minute WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getHourStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) from stat_status_hour WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types;",
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
				timeSlot.setStatus(resultSet.getInt(1));
				timeSlot.setStatusNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	
	
	/*
	 * 查询某个状态,某个url在（年，月，日，十，分）上出现的次数
	 */
	
	public List<TimeSlot>  getInfoByStatusYear(long startTime, long endTime, int client_id,int project_id, int status,String url) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT time,sum(counts) from stat_status_url_year WHERE time >= ? AND time < ?  "
				+ "AND client_id = ? AND project_id = ?  and types=? and url= ? GROUP BY time;",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id, status,url),
				result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	public List<TimeSlot>  getInfoByStatusMonth(long startTime, long endTime, int client_id,int project_id, int status,String url) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT time,sum(counts) from stat_status_url_month WHERE time >= ? AND time < ?  "
						+ "AND client_id = ? AND project_id = ?  and types=? and url= ? GROUP BY types,time;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id, status,url),
						result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	public List<TimeSlot>  getInfoByStatusDaily(long startTime, long endTime, int client_id,int project_id, int status,String url) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT time,sum(counts) from stat_status_url_daily WHERE time >= ? AND time < ?  "
						+ "AND client_id = ? AND project_id = ?  and types=?  and url= ? GROUP BY time;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id, status,url),
						result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	public List<TimeSlot>  getInfoByStatusHour(long startTime, long endTime, int client_id,int project_id, int status,String url) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT time,sum(counts) from stat_status_url_hour WHERE time >= ? AND time < ?  "
						+ "AND client_id = ? AND project_id = ?  and types=? and url= ? GROUP BY time;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id, status,url),
						result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	public List<TimeSlot>  getInfoByStatusMinute(long startTime, long endTime, int client_id,int project_id, int status,String url) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT time,sum(counts) from stat_status_url_minute WHERE time >= ? AND time < ?  "
						+ "AND client_id = ? AND project_id = ?  and types=?  and url= ?  GROUP BY time;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id, status,url),
						result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	
	
	
	private final class PreparedStatementProcessor_2 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id,status;
		String url;

		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id, int status,String url) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			this.status = status;
			this.url=url;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
			statement.setInt(5, status);
			statement.setString(6, url);
		}
	}

	private final class ResultSetProcessor_2 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setTime(resultSet.getLong(1));
				timeSlot.setStatusNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	
	/*
	 * 查询所有独立IP在某个状态（年，月，日，十，分）出现的次数
	 */
	
	public List<TimeSlot>  getIpByStatusYear(long startTime, long endTime, int client_id,int project_id, int status,String ip) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT time ,sum(counts) as ipCount from stat_status_year WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id = ? and types= ? and c_ip = ? group by time",
				prepared_statement_processor_3.init(startTime, endTime, client_id, project_id, status,ip),
				result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getIpByStatusMonth(long startTime, long endTime, int client_id,int project_id, int status,String ip) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT time ,sum(counts) as ipCount from stat_status_month WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id = ? and types= ? and c_ip = ? group by time",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id, status,ip),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getIpByStatusDaily(long startTime, long endTime, int client_id,int project_id, int status,String ip) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT time ,sum(counts) as ipCount from stat_status_daily WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id = ? and types= ? and c_ip = ? group by time",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id, status,ip),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getIpByStatusHour(long startTime, long endTime, int client_id,int project_id, int status,String ip) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT time ,sum(counts) as ipCount from stat_status_hour WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id = ? and types= ? and c_ip = ? group by time",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id, status,ip),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getIpByStatusMinute(long startTime, long endTime, int client_id,int project_id, int status,String ip) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT time ,sum(counts) as ipCount from stat_status_minute WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id = ? and types= ? and c_ip = ? group by time",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id, status,ip),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	
	private final class PreparedStatementProcessor_3 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id,status;
		String ip;
		
		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id, int status,String ip) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			this.status = status;
			this.ip=ip;
			return this;
		}
		
		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
			statement.setInt(5, status);
			statement.setString(6, ip);
		}
	}
	
	private final class ResultSetProcessor_3 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setTime(resultSet.getLong(1));
				timeSlot.setStNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}

	/*
	 * 查询所有独立URL在某个状态（年，月，日，十，分）出现的次数
	 */
	
	public List<TimeSlot>  getUrlByStatusYear(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT  types,url ,sum(counts) as urlCount from stat_status_url_year WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id =?  group by url,types order by urlCount desc;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getUrlByStatusMonth(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT  types,url ,sum(counts) as urlCount from stat_status_url_month WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id =?  group by url,types order by urlCount desc;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getUrlByStatusDaily(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
			executeQuery(
				"SELECT  types,url ,sum(counts) as urlCount from stat_status_url_daily WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id =? group by url,types order by urlCount desc;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getUrlByStatusHour(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT  types,url ,sum(counts) as urlCount from stat_status_url_hour WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id =? group by url,types order by urlCount desc;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getUrlByStatusMinute(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT  types,url ,sum(counts) as urlCount from stat_status_url_minute WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id =? group by url,types order by urlCount desc;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	
	/*
	 * 查询所有独立IP在某个状态（年，月，日，十，分）出现的次数
	 */
	
	public List<TimeSlot>  getIpByStatusYear(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT  types,c_ip ,sum(counts) as ipCount from stat_status_year WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id =?  group by c_ip,types order by ipCount desc;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getIpByStatusMonth(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT  types,c_ip ,sum(counts) as ipCount from stat_status_Month WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id =?  group by c_ip,types order by ipCount desc;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getIpByStatusDaily(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT  types,c_ip ,sum(counts) as ipCount from stat_status_daily WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id =?  group by c_ip,types order by ipCount desc;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getIpByStatusHour(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT  types,c_ip ,sum(counts) as ipCount from stat_status_hour WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id =?  group by c_ip,types order by ipCount desc;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getIpByStatusMinute(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				"SELECT  types,c_ip ,sum(counts) as ipCount from stat_status_minute WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id =?  group by c_ip,types order by ipCount desc;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	
	
	private final class ResultSetProcessor_4 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setStatus(resultSet.getInt(1));
				timeSlot.setUrl(resultSet.getString(2));
				timeSlot.setUrlNum(resultSet.getLong(3));
				tsList.add(timeSlot);
			}
		}
	}
	
	private final class PreparedStatementProcessor_4 extends PreparedStatementProcessor {
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
				timeSlot.setProvince(resultSet.getString(1));
				timeSlot.setStatus(resultSet.getInt(2));
				timeSlot.setIp(resultSet.getString(3));
				timeSlot.setIpNum(resultSet.getLong(4));
				tsList.add(timeSlot);
			}
		}
	}
	

	/*
	 * 查询所有状态在地理位置（所有省份）（年，月，日，十，分）所有ip 的页面访问量WEL:GETTOPIPGEYEAR
	 */
	
	public List<TimeSlot>  getTopIpYear(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT  g.province,s.types,s.c_ip,sum(counts) as ipCount from stat_status_year s "
				+ "left join stat_ip_geography g on s.c_ip = g.c_ip WHERE s.time >= ? AND s.time < ? "
				+ "AND s.client_id = ? AND s.project_id =?  group by s.types,s.c_ip order by ipCount desc;",
						prepared_statement_processor_5.init(startTime, endTime, client_id, project_id),
						result_set_processor_5);
		
		return result_set_processor_5.tsList;
	}
	public List<TimeSlot>  getTopIpMonth(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT  g.province,s.types,s.c_ip,sum(counts) as ipCount from stat_status_month s "
						+ "left join stat_ip_geography g on s.c_ip = g.c_ip WHERE s.time >= ? AND s.time < ? "
						+ "AND s.client_id = ? AND s.project_id =?  group by s.types,s.c_ip order by ipCount desc;",
						prepared_statement_processor_5.init(startTime, endTime, client_id, project_id),
						result_set_processor_5);
		
		return result_set_processor_5.tsList;
	}
	public List<TimeSlot>  getTopIpDaily(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT  g.province,s.types,s.c_ip,sum(counts) as ipCount from stat_status_daily s "
						+ "left join stat_ip_geography g on s.c_ip = g.c_ip WHERE s.time >= ? AND s.time < ? "
						+ "AND s.client_id = ? AND s.project_id =?  group by s.types,s.c_ip order by ipCount desc;",
						prepared_statement_processor_5.init(startTime, endTime, client_id, project_id),
						result_set_processor_5);
		
		return result_set_processor_5.tsList;
	}
	public List<TimeSlot>  getTopIpHour(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT  g.province,s.types,s.c_ip,sum(counts) as ipCount from stat_status_hour s "
						+ "left join stat_ip_geography g on s.c_ip = g.c_ip WHERE s.time >= ? AND s.time < ? "
						+ "AND s.client_id = ? AND s.project_id =?  group by s.types,s.c_ip order by ipCount desc;",
						prepared_statement_processor_5.init(startTime, endTime, client_id, project_id),
						result_set_processor_5);
		
		return result_set_processor_5.tsList;
	}
	public List<TimeSlot>  getTopIpMinute(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT  g.province,s.types,s.c_ip,sum(counts) as ipCount from stat_status_minute s "
						+ "left join stat_ip_geography g on s.c_ip = g.c_ip WHERE s.time >= ? AND s.time < ? "
						+ "AND s.client_id = ? AND s.project_id =?  group by s.types,s.c_ip order by ipCount desc;",
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
	
	
	
	private final class ResultSetProcessor_6 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setStatus(resultSet.getInt(1));
				timeSlot.setTime(resultSet.getLong(2));
				timeSlot.setPvNum(resultSet.getLong(3));
				tsList.add(timeSlot);
			}
		}
	}
	
	/*
	 * 查询所有状态在（年，月，日，十，分）出现的次数
	 */
	
	
	public List<TimeSlot>  getByStatusYear(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_6 result_set_processor_6 = new ResultSetProcessor_6();
		PreparedStatementProcessor_6 prepared_statement_processor_6 = new PreparedStatementProcessor_6();
		
		executeQuery(
				"SELECT  types,time ,sum(counts) as typeCount from stat_status_year WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id = ?  group by time,types;",
						prepared_statement_processor_6.init(startTime, endTime, client_id, project_id),
						result_set_processor_6);
		
		return result_set_processor_6.tsList;
	}
	public List<TimeSlot>  getByStatusMonth(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_6 result_set_processor_6 = new ResultSetProcessor_6();
		PreparedStatementProcessor_6 prepared_statement_processor_6 = new PreparedStatementProcessor_6();
		
		executeQuery(
				"SELECT  types,time ,sum(counts) as typeCount from stat_status_month WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id = ?  group by time,types;",
						prepared_statement_processor_6.init(startTime, endTime, client_id, project_id),
						result_set_processor_6);
		
		return result_set_processor_6.tsList;
	}
	public List<TimeSlot>  getByStatusDaily(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_6 result_set_processor_6 = new ResultSetProcessor_6();
		PreparedStatementProcessor_6 prepared_statement_processor_6 = new PreparedStatementProcessor_6();
		
		executeQuery(
				"SELECT  types,time ,sum(counts) as typeCount from stat_status_daily WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id = ?  group by time,types;",
						prepared_statement_processor_6.init(startTime, endTime, client_id, project_id),
						result_set_processor_6);
		
		return result_set_processor_6.tsList;
	}
	public List<TimeSlot>  getByStatusHour(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_6 result_set_processor_6 = new ResultSetProcessor_6();
		PreparedStatementProcessor_6 prepared_statement_processor_6 = new PreparedStatementProcessor_6();
		
		executeQuery(
				"SELECT  types,time ,sum(counts) as typeCount from stat_status_hour WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id = ?  group by time,types;",
						prepared_statement_processor_6.init(startTime, endTime, client_id, project_id),
						result_set_processor_6);
		
		return result_set_processor_6.tsList;
	}
	public List<TimeSlot>  getByStatusMinute(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_6 result_set_processor_6 = new ResultSetProcessor_6();
		PreparedStatementProcessor_6 prepared_statement_processor_6 = new PreparedStatementProcessor_6();
		
		executeQuery(
				"SELECT  types,time ,sum(counts) as typeCount from stat_status_minute WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id = ?  group by time,types;",
						prepared_statement_processor_6.init(startTime, endTime, client_id, project_id),
						result_set_processor_6);
		
		return result_set_processor_6.tsList;
	}
	private final class PreparedStatementProcessor_6 extends PreparedStatementProcessor {
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
}
