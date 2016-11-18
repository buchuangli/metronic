package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.open01.logs.model.TimeSlot;

public class StayTimeDatabase extends Open01Database {

	public StayTimeDatabase() {

		super();

	}

	/*public List<TimeSlot>  getDailyStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT url,staytime,sum(counts) stCount  from stat_tp_daily WHERE time >= ? AND time < ? "
				+ "AND client_id =? AND project_id =?  group by staytime order by stCount desc;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMonthStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT url,staytime,sum(counts) stCount  from stat_tp_month WHERE time >= ? AND time < ? "
						+ "AND client_id =? AND project_id =?  group by staytime order by stCount desc;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getYearStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT url,staytime,sum(counts) stCount  from stat_tp_year WHERE time >= ? AND time < ? "
						+ "AND client_id =? AND project_id =?  group by staytime order by stCount desc;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMinuteStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT url,staytime,sum(counts) stCount  from stat_tp_minute WHERE time >= ? AND time < ? "
						+ "AND client_id =? AND project_id =?  group by staytime order by stCount desc;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getHourStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT url,staytime,sum(counts) stCount  from stat_tp_hour WHERE time >= ? AND time < ? "
						+ "AND client_id =? AND project_id =?  group by staytime order by stCount desc;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}*/
	public List<TimeSlot>  getDailyStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT url,sum(staytime)  as staytime from stat_tp_daily WHERE time >= ? "
						+ "AND time < ?  AND client_id = ? AND project_id = ? group by url order by staytime desc;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
						result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMonthStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT url,sum(staytime) as staytime from stat_tp_month WHERE time >= ? "
						+ "AND time < ?  AND client_id = ? AND project_id = ? group by url order by staytime desc;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
						result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getYearStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT url,sum(staytime) as staytime from stat_tp_year WHERE time >= ? "
						+ "AND time < ?  AND client_id = ? AND project_id = ? group by url order by staytime desc;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
						result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getMinuteStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT url,sum(staytime)  as staytime from stat_tp_minute WHERE time >= ? "
						+ "AND time < ?  AND client_id = ? AND project_id = ? group by url order by staytime desc;",
						prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
						result_set_processor_1);
		
		return result_set_processor_1.tsList;
	}
	public List<TimeSlot>  getHourStatus(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT url,sum(staytime) as staytime from stat_tp_hour WHERE time >= ? "
						+ "AND time < ?  AND client_id = ? AND project_id = ? group by url order by staytime desc;",
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
				timeSlot.setUrl(resultSet.getString(1));
				timeSlot.setStayTime(resultSet.getLong(2));
				//timeSlot.setStNum(resultSet.getLong(3));
				tsList.add(timeSlot);
			}
		}
	}
	/*
	 * 计算所有停留时间总的次数
	 */
			
	public long  getAllNumYear(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT sum(staytime) from stat_tp_year WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id =? ;",
				prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
				result_set_processor_2);
		
		return result_set_processor_2.allNum;
	}
	public long  getAllNumMonth(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT sum(staytime) from stat_tp_month WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id =? ;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
						result_set_processor_2);
		
		return result_set_processor_2.allNum;
	}
	public long  getAllNumDaily(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT sum(staytime) from stat_tp_daily WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id =? ;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
						result_set_processor_2);
		
		return result_set_processor_2.allNum;
	}
	public long  getAllNumHour(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT sum(staytime) from stat_tp_hour WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id =? ;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
						result_set_processor_2);
		
		return result_set_processor_2.allNum;
	}
	public long  getAllNumMinute(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT sum(staytime) from stat_tp_minute WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id =? ;",
						prepared_statement_processor_2.init(startTime, endTime, client_id, project_id),
						result_set_processor_2);
		
		return result_set_processor_2.allNum;
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
		long allNum=0l;
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				allNum=resultSet.getLong(1);
			}
		}
	}
}
