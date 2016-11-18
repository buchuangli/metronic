package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.open01.logs.model.TimeSlot;

public class IpCountDatabase extends Open01Database {

	public IpCountDatabase() {

		super();

	}

	public List<TimeSlot>  getIpPageViewByGeYear(long startTime, long endTime, int client_id, int project_id,String country) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT g.province, s.c_ip ,sum(counts) ipCounts FROM stat_ip_year s left join stat_ip_geography g on s.c_ip = g.c_ip "
				+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id = ? and "
				+ "g.country = ? group by s.c_ip order by ipCounts desc limit 10;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,country),
				result_set_processor_1);
		
		return result_set_processor_1.pList;
	}
	
	public List<TimeSlot>  getIpPageViewByGeMonth(long startTime, long endTime, int client_id, int project_id,String country) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT g.province, s.c_ip ,sum(counts) ipCounts FROM stat_ip_month s left join stat_ip_geography g on s.c_ip = g.c_ip "
						+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id = ? and "
						+ "g.country = ? group by s.c_ip order by ipCounts desc limit 10;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_1);
		
		return result_set_processor_1.pList;
	}
	
	public List<TimeSlot>  getIpPageViewByGeDaily(long startTime, long endTime, int client_id, int project_id,String country) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT g.province, s.c_ip ,sum(counts) ipCounts FROM stat_ip_daily s left join stat_ip_geography g on s.c_ip = g.c_ip "
						+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id = ? and "
						+ "g.country = ? group by s.c_ip order by ipCounts desc limit 10;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_1);
		
		return result_set_processor_1.pList;
	}
	
	public List<TimeSlot>  getIpPageViewByGeHour(long startTime, long endTime, int client_id, int project_id,String country) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT g.province, s.c_ip ,sum(counts) ipCounts FROM stat_ip_hour s left join stat_ip_geography g on s.c_ip = g.c_ip "
						+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id = ? and "
						+ "g.country = ? group by s.c_ip order by ipCounts desc limit 10;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_1);
		
		return result_set_processor_1.pList;
	}
	
	public List<TimeSlot>  getIpPageViewByGeMinute(long startTime, long endTime, int client_id, int project_id,String country) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT g.province, s.c_ip ,sum(counts) ipCounts FROM stat_ip_minute s left join stat_ip_geography g on s.c_ip = g.c_ip "
						+ "WHERE s.time >= ? AND s.time < ? AND s.client_id = ? AND s.project_id = ? and "
						+ "g.country = ? group by s.c_ip order by ipCounts desc limit 10;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id,country),
						result_set_processor_1);
		
		return result_set_processor_1.pList;
	}
	
	private final class PreparedStatementProcessor_1 extends PreparedStatementProcessor {
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

	private final class ResultSetProcessor_1 extends ResultSetProcessor {
		List<TimeSlot> pList=new ArrayList<TimeSlot>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setProvince(resultSet.getString(1));
				timeSlot.setIp(resultSet.getString(2));
				timeSlot.setIpNum(resultSet.getLong(3));
				pList.add(timeSlot);
			}
		}
	}
	
	
	public List<TimeSlot> getYearPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {

			ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
			PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
			executeQuery(
					"SELECT  time, COUNT(DISTINCT c_ip) FROM stat_ip_hour WHERE time >= ? AND time < ? AND client_id = ? AND project_id =? GROUP BY time;",
					prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
					result_set_processor_2);

			return result_set_processor_2.tsList;

	}
	public List<TimeSlot> getMonthPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		executeQuery(
				"SELECT  time, COUNT(DISTINCT c_ip) FROM stat_ip_month WHERE time >= ? AND time < ? AND client_id = ? AND project_id =? GROUP BY time;",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
				result_set_processor_2);
		
		return result_set_processor_2.tsList;
		
	}
	public List<TimeSlot> getDailyPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		executeQuery(
				"SELECT  time, COUNT(DISTINCT c_ip) FROM stat_ip_daily WHERE time >= ? AND time < ? AND client_id = ? AND project_id =? GROUP BY time;",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
				result_set_processor_2);
		
		return result_set_processor_2.tsList;
		
	}
	public List<TimeSlot> getHourPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		executeQuery(
				"SELECT  time, COUNT(DISTINCT c_ip) FROM stat_ip_hour WHERE time >= ? AND time < ? AND client_id = ? AND project_id =? GROUP BY time;",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
				result_set_processor_2);
		
		return result_set_processor_2.tsList;
		
	}
	public List<TimeSlot> getMinutePageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		executeQuery(
				"SELECT  time, COUNT(DISTINCT c_ip) FROM stat_ip_minute WHERE time >= ? AND time < ? AND client_id = ? AND project_id =? GROUP BY time;",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
				result_set_processor_2);
		
		return result_set_processor_2.tsList;
		
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

		List<TimeSlot> tsList= new ArrayList<TimeSlot>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				TimeSlot ts =new TimeSlot();	
				ts.setTime(resultSet.getLong(1));
				ts.setIpNum(resultSet.getLong(2));
				tsList.add(ts);
			}

		}
	}
}
