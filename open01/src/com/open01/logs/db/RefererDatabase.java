package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.open01.logs.model.TimeSlot;

public class RefererDatabase extends Open01Database {

	public RefererDatabase() {

		super();

	}

	public List<TimeSlot>  getDailyReferrer(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts)  from stat_referrer_daily WHERE time >= ? AND time < ?  "
				+ "AND client_id = ? AND project_id = ? GROUP BY types;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMonthReferrer(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) from stat_referrer_month WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getYearReferrer(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) from stat_referrer_year WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMinuteReferrer(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) from stat_referrer_minute WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getHourReferrer(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT types,sum(counts) from stat_referrer_hour WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY types;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	
	
	public List<TimeSlot>  getYearIndirectReferrer(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT referrer,sum(counts) as referrerCount from stat_referrer_year WHERE "
				+ "time >= ? AND time < ? AND client_id = ? AND project_id = ? "
				+ "and types=1 GROUP BY referrer order by referrerCount desc limit 10;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMonthIndirectReferrer(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT referrer,sum(counts) as referrerCount from stat_referrer_Month WHERE "
						+ "time >= ? AND time < ? AND client_id = ? AND project_id = ? "
						+ "and types=1 GROUP BY referrer order by referrerCount desc limit 10;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
						result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getDailyIndirectReferrer(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT referrer,sum(counts) as referrerCount from stat_referrer_daily WHERE "
						+ "time >= ? AND time < ? AND client_id = ? AND project_id = ? "
						+ "and types=1 GROUP BY referrer order by referrerCount desc limit 10;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
						result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getHourIndirectReferrer(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT referrer,sum(counts) as referrerCount from stat_referrer_Hour WHERE "
						+ "time >= ? AND time < ? AND client_id = ? AND project_id = ? "
						+ "and types=1 GROUP BY referrer order by referrerCount desc limit 10;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
						result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMinuteIndirectReferrer(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT referrer,sum(counts) as referrerCount from stat_referrer_minute WHERE "
						+ "time >= ? AND time < ? AND client_id = ? AND project_id = ? "
						+ "and types=1 GROUP BY referrer order by referrerCount desc limit 10;",
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
				timeSlot.setReferrerType(resultSet.getString(1));
				timeSlot.setReferrerNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	
	public List<TimeSlot>  getYearByUrlIndirect(long startTime, long endTime, int client_id,int project_id,String referrer) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_referrer_year WHERE  TIME >= ? AND "
				+ "TIME < ? AND client_id = ? AND project_id =?  AND referrer =? and types=1 GROUP BY TIME;",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,referrer),
				result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	public List<TimeSlot>  getMonthByUrlIndirect(long startTime, long endTime, int client_id,int project_id,String referrer) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_referrer_month WHERE  TIME >= ? AND "
						+ "TIME < ? AND client_id = ? AND project_id =?  AND referrer =? and types=1 GROUP BY TIME;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,referrer),
						result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	public List<TimeSlot>  getDailyByUrlIndirect(long startTime, long endTime, int client_id,int project_id,String referrer) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_referrer_daily WHERE  TIME >= ? AND "
						+ "TIME < ? AND client_id = ? AND project_id =?  AND referrer =? and types=1 GROUP BY TIME;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,referrer),
						result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	public List<TimeSlot>  getHourByUrlIndirect(long startTime, long endTime, int client_id,int project_id,String referrer) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_referrer_hour WHERE  TIME >= ? AND "
						+ "TIME < ? AND client_id = ? AND project_id =?  AND referrer =? and types=1 GROUP BY TIME;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,referrer),
						result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	public List<TimeSlot>  getMinuteByUrlIndirect(long startTime, long endTime, int client_id,int project_id,String referrer) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_referrer_minute WHERE  TIME >= ? AND "
						+ "TIME < ? AND client_id = ? AND project_id =?  AND referrer =? and types=1 GROUP BY TIME;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id,referrer),
						result_set_processor_2);
		
		return result_set_processor_2.tsList;
	}
	
	private final class PreparedStatementProcessor_2 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id;
		String referrer;

		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id,String referrer) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			this.referrer=referrer;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
			statement.setString(5, referrer);
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
				timeSlot.setPvNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	public List<TimeSlot>  getYearByUrlDirect(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_referrer_year WHERE  TIME >= ? AND"
				+ " TIME < ? AND client_id = ? AND project_id =?  AND TYPES =0 GROUP BY TIME;",
				prepared_statement_processor_3.init(startTime, endTime, client_id, project_id),
				result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getMonthByUrlDirect(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_referrer_month WHERE  TIME >= ? AND"
						+ " TIME < ? AND client_id = ? AND project_id =?  AND TYPES =0 GROUP BY TIME;",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getDailyByUrlDirect(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_referrer_daily WHERE  TIME >= ? AND"
						+ " TIME < ? AND client_id = ? AND project_id =?  AND TYPES =0 GROUP BY TIME;",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getHourByUrlDirect(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_referrer_hour WHERE  TIME >= ? AND"
						+ " TIME < ? AND client_id = ? AND project_id =?  AND TYPES =0 GROUP BY TIME;",
						prepared_statement_processor_3.init(startTime, endTime, client_id, project_id),
						result_set_processor_3);
		
		return result_set_processor_3.tsList;
	}
	public List<TimeSlot>  getMinuteByUrlDirect(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery(
				"SELECT TIME,SUM(counts) FROM stat_referrer_minute WHERE  TIME >= ? AND"
						+ " TIME < ? AND client_id = ? AND project_id =?  AND TYPES =0 GROUP BY TIME;",
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
				timeSlot.setTime(resultSet.getLong(1));
				timeSlot.setPvNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	
}
