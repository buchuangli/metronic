package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.open01.logs.model.TimeSlot;

public class BrowserDatabase extends Open01Database {

	public BrowserDatabase() {

		super();

	}

	public List<TimeSlot>  getDailyStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) as brCount from stat_browser_daily WHERE time >= ? "
				+ "AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types order by brCount;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMonthStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) as brCount  from stat_browser_month WHERE time >= ?"
				+ " AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types order by brCount;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getYearStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) as brCount from stat_browser_year WHERE time >= ? "
				+ "AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types order by brCount;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMinuteStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) as brCount  from stat_browser_minute WHERE time >= ?"
				+ " AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types order by brCount;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getHourStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) as brCount  from stat_browser_hour WHERE time >= ? "
				+ "AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types order by brCount ;",
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
				timeSlot.setBrowserType(resultSet.getString(1));
				timeSlot.setBrowserNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	/*
	 * 查询全国所有浏览器（每种浏览器）的页面访问量
	 */
	
	public List<TimeSlot>  getYearCountryStatus(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"select s.types,sum(s.counts) as bCount from  stat_browser_year s left join "
				+ "stat_ip_geography g on s.c_ip = g.c_ip "
				+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id =?  "
				+ "and  g.country =?  group by s.types order by bCount;",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,country),
				result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	public List<TimeSlot>  getMonthCountryStatus(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"select s.types,sum(s.counts) as bCount from  stat_browser_month s left join "
						+ "stat_ip_geography g on s.c_ip = g.c_ip "
						+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id =?  "
						+ "and  g.country =?  group by s.types order by bCount;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	public List<TimeSlot>  getDailyCountryStatus(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"select s.types,sum(s.counts) as bCount from  stat_browser_daily s left join "
						+ "stat_ip_geography g on s.c_ip = g.c_ip "
						+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id =?  "
						+ "and  g.country =?  group by s.types order by bCount;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	public List<TimeSlot>  getHourCountryStatus(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"select s.types,sum(s.counts) as bCount from  stat_browser_hour s left join "
						+ "stat_ip_geography g on s.c_ip = g.c_ip "
						+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id =?  "
						+ "and  g.country =?  group by s.types order by bCount;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	public List<TimeSlot>  getMinuteCountryStatus(long startTime, long endTime, int client_id,int project_id,String country) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"select s.types,sum(s.counts) as bCount from  stat_browser_minute s left join "
						+ "stat_ip_geography g on s.c_ip = g.c_ip "
						+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id =?  "
						+ "and  g.country =?  group by s.types order by bCount;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	
	private final class PreparedStatementProcessor_2 extends PreparedStatementProcessor {
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
	
	private final class ResultSetProcessor_2 extends ResultSetProcessor {
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
	
	/*
	 * 查询某个省份所有浏览器（每种浏览器）的页面访问量
	 */
	
	public List<TimeSlot>  getYearProvinceStatus(long startTime, long endTime, int client_id,int project_id,String country,String province) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"select s.types,sum(s.counts) as bCount from  stat_browser_year s left join "
				+ "stat_ip_geography g on s.c_ip = g.c_ip "
				+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id =?  "
				+ "and  g.country =? and g.province =? group by s.types order by bCount;",
				prepared_statement_processor_3.init(startTime, endTime, client_id, project_id,country,province),
				result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getMonthProvinceStatus(long startTime, long endTime, int client_id,int project_id,String country,String province) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"select s.types,sum(s.counts) as bCount from  stat_browser_month s left join "
						+ "stat_ip_geography g on s.c_ip = g.c_ip "
						+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id =?  "
						+ "and  g.country =? and g.province =? group by s.types order by bCount;",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id,country,province),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getDailyProvinceStatus(long startTime, long endTime, int client_id,int project_id,String country,String province) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"select s.types,sum(s.counts) as bCount from  stat_browser_daily s left join "
						+ "stat_ip_geography g on s.c_ip = g.c_ip "
						+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id =?  "
						+ "and  g.country =? and g.province =? group by s.types order by bCount;",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id,country,province),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getHourProvinceStatus(long startTime, long endTime, int client_id,int project_id,String country,String province) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"select s.types,sum(s.counts) as bCount from  stat_browser_hour s left join "
						+ "stat_ip_geography g on s.c_ip = g.c_ip "
						+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id =?  "
						+ "and  g.country =? and g.province =? group by s.types order by bCount;",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id,country,province),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getMinuteProvinceStatus(long startTime, long endTime, int client_id,int project_id,String country,String province) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"select s.types,sum(s.counts) as bCount from  stat_browser_minute s left join "
						+ "stat_ip_geography g on s.c_ip = g.c_ip "
						+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id =?  "
						+ "and  g.country =? and g.province =? group by s.types order by bCount;",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id,country,province),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	private final class PreparedStatementProcessor_3 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id;
		String country,province;
		
		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id,String country,String province) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			this.country=country;
			this.province=province;
			return this;
		}
		
		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
			statement.setString(5, country);
			statement.setString(6, province);
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
	
	
	
}
