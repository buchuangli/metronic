package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.open01.logs.model.TimeSlot;

public class PageViewDatabase extends Open01Database {


	public List<TimeSlot> getYearPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {

			ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
			PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();

			executeQuery(
					"SELECT  time, sum(counts) FROM stat_pv_year WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY time ;",
					prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
					result_set_processor_1);

			return result_set_processor_1.tsList;

	}
	public List<TimeSlot> getMonthPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT  time, sum(counts) FROM stat_pv_month WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY time ;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
		
	}
	public List<TimeSlot> getDailyPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT  time, sum(counts) FROM stat_pv_daily WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY time ;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
		
	}
	public List<TimeSlot> getHourPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT  time, sum(counts) FROM stat_pv_hour WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY time ;",
				prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
				result_set_processor_1);
		
		return result_set_processor_1.tsList;
		
	}
	public List<TimeSlot> getMinutePageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT  time, sum(counts) FROM stat_pv_minute WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY time ;",
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

		List<TimeSlot> tsList= new ArrayList<TimeSlot>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				TimeSlot ts =new TimeSlot();	
				ts.setTime(resultSet.getLong(1));
				ts.setPvNum(resultSet.getLong(2));
				tsList.add(ts);
			}

		}
	}
	
	
}