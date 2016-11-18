package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.open01.logs.model.TimeSlot;

public class AnalysisDatabase extends Open01Database {

	private TreeMap<Long, List<TimeSlot>> timeSlots = new TreeMap<Long, List<TimeSlot>>();

	public static AnalysisDatabase instance = new AnalysisDatabase();

	private AnalysisDatabase() {

		super();
		

	}

	public TreeMap<Long, List<TimeSlot>> getTimeSlots() {

		return this.timeSlots;

	}

	public TreeMap<Long, List<TimeSlot>> getHourPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {

		if (null == this.timeSlots || this.timeSlots.isEmpty() || this.timeSlots.size() == 0) {

			ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
			PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();

			executeQuery(
					"SELECT 1, time, sum(counts) FROM stat_pv_hour WHERE time >= ? AND time < ?  AND client_id = ? AND project_id = ? GROUP BY time "
						+ "UNION SELECT  2, time, COUNT(DISTINCT c_ip) FROM stat_ip_hour WHERE time >= ? AND time < ? AND client_id = ? AND project_id =? GROUP BY time;",
					prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
					result_set_processor_1);

			return result_set_processor_1.counts;
		} else {
			return this.timeSlots;
		}

	}

	public TreeMap<Long, List<TimeSlot>> getDailyPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {

		if (null == this.timeSlots || this.timeSlots.isEmpty() || this.timeSlots.size() == 0) {

			ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
			PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();

			executeQuery(
					"SELECT 1, time, sum(counts) FROM stat_pv_daily WHERE time >= ? AND time < ?  AND client_id =? AND project_id =? GROUP BY time "
							+ "UNION  SELECT 2, time, COUNT(DISTINCT c_ip) FROM stat_ip_daily WHERE time >= ? AND time <? AND client_id =? AND project_id =? GROUP BY time;",
					prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
					result_set_processor_1);

			return result_set_processor_1.counts;
		} else {
			return this.timeSlots;
		}

	}

	public TreeMap<Long, List<TimeSlot>> getMinutePageviewCount(long startTime, long endTime, int client_id,
			int project_id) {

		if (null == this.timeSlots || this.timeSlots.isEmpty() || this.timeSlots.size() == 0) {

			ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
			PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();

			executeQuery(
					"SELECT 1, time, sum(counts) FROM stat_pv_minute WHERE time >= ? AND time < ?  AND client_id =? AND project_id =? GROUP BY time "
							+ "UNION  SELECT 2, time, COUNT(DISTINCT c_ip) FROM stat_ip_minute WHERE time >= ? AND time <? AND client_id =? AND project_id =? GROUP BY time ;",
					prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
					result_set_processor_1);

			return result_set_processor_1.counts;
		} else {
			return this.timeSlots;
		}

	}

	public TreeMap<Long, List<TimeSlot>> getMonthPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {

		if (null == this.timeSlots || this.timeSlots.isEmpty() || this.timeSlots.size() == 0) {

			ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
			PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();

			executeQuery(
					"SELECT 1, time, sum(counts) FROM stat_pv_month WHERE time >= ? AND time < ?  AND client_id =? AND project_id =? GROUP BY time "
							+ "UNION SELECT  2, time, COUNT(DISTINCT c_ip) FROM stat_ip_month WHERE time >= ? AND time <? AND client_id =? AND project_id =? GROUP BY time ;",
					prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
					result_set_processor_1);

			return result_set_processor_1.counts;
		} else {
			return this.timeSlots;
		}

	}

	public TreeMap<Long, List<TimeSlot>> getYearPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {

		if (null == this.timeSlots || this.timeSlots.isEmpty() || this.timeSlots.size() == 0) {

			ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
			PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();

			executeQuery(
					"SELECT 1, time, sum(counts) FROM stat_pv_year WHERE time >= ? AND time < ?  AND client_id =? AND project_id =? GROUP BY time "
							+ "UNION SELECT  2, time, COUNT(DISTINCT c_ip) FROM stat_ip_year WHERE time >= ? AND time <? AND client_id =? AND project_id =? GROUP BY time;",
					prepared_statement_processor_1.init(startTime, endTime, client_id, project_id),
					result_set_processor_1);

			return result_set_processor_1.counts;
		} else {
			return this.timeSlots;
		}

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
			statement.setLong(5, startTime);
			statement.setLong(6, endTime);
			statement.setInt(7, client_id);
			statement.setInt(8, project_id);
		}
	}

	
	private final class ResultSetProcessor_1 extends ResultSetProcessor {

		private TreeMap<Long, List<TimeSlot>> counts = new TreeMap<Long, List<TimeSlot>>();
		List<TimeSlot> tsList= new ArrayList<TimeSlot>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {

				int type = resultSet.getInt(1);
				long time = resultSet.getLong(2);
				//System.out.println(type+"\t"+time);
				TimeSlot ts = null;
				if (counts.containsKey(time)) {
					ts = (TimeSlot) counts.get(time);
				} else {
					ts = new TimeSlot();
					ts.setTime(time);
				}
			//	ts.setFlag(type);
				if (type == 1) {
					ts.setPvNum(resultSet.getLong(3));
					
				} else if (type == 2) {
					ts.setIpNum(resultSet.getLong(3));
				}
				tsList.add(ts);
			}
			counts.put(1l,(List<TimeSlot>) tsList);

		}
	}

	/*
	 * 查询新增IP总数（小时，天，月，年）
	 */
	public long getMinuteNewIp(int client_id, int project_id, long startTime,long endTime) {
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT  COUNT(DISTINCT c_ip) FROM stat_ip_minute WHERE client_id= ? AND project_id= ? AND "
						+ "time >=? AND time < ? AND c_ip NOT IN (SELECT c_ip FROM stat_ip_minute WHERE time < ?);",
						prepared_statement_processor_2.init(client_id, project_id, startTime,endTime), result_set_processor_2);
		return result_set_processor_2.newIpCount;
	}
	public long getHourNewIp(int client_id, int project_id, long startTime,long endTime) {
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT  COUNT(DISTINCT c_ip) FROM stat_ip_hour WHERE client_id= ? AND project_id= ? AND "
						+ "time >=? AND time < ? AND c_ip NOT IN (SELECT c_ip FROM stat_ip_hour WHERE time < ?);",
						prepared_statement_processor_2.init(client_id, project_id, startTime,endTime), result_set_processor_2);
		return result_set_processor_2.newIpCount;
	}


	public long getDailyNewIp(int client_id, int project_id,long startTime,long endTime) {
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();

		executeQuery(
				"SELECT  COUNT(DISTINCT c_ip) FROM stat_ip_daily WHERE client_id= ? AND project_id= ? AND "
						+ "time >=? AND time < ?  AND c_ip NOT IN (SELECT c_ip FROM stat_ip_daily WHERE time < ?);",
				prepared_statement_processor_2.init(client_id, project_id,  startTime,endTime), result_set_processor_2);
		return result_set_processor_2.newIpCount;
	}

	public long getMonthNewIp(int client_id, int project_id, long startTime,long endTime) {
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();

		executeQuery(
				"SELECT  COUNT(DISTINCT c_ip) FROM stat_ip_month WHERE client_id= ? AND project_id= ? AND "
						+ "time >=? AND time < ?  AND c_ip NOT IN (SELECT c_ip FROM stat_ip_daily WHERE time < ?);",
				prepared_statement_processor_2.init(client_id, project_id, startTime,endTime), result_set_processor_2);
		return result_set_processor_2.newIpCount;
	}

	public long getYearNewIp(int client_id, int project_id, long startTime,long endTime) {
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();

		executeQuery(
				"SELECT  COUNT(DISTINCT c_ip) FROM stat_ip_year WHERE client_id= ? AND project_id= ? AND "
						+ "time >=? AND time < ?  AND c_ip NOT IN (SELECT c_ip FROM stat_ip_month WHERE time < ?);",
				prepared_statement_processor_2.init(client_id, project_id, startTime,endTime), result_set_processor_2);
		return result_set_processor_2.newIpCount;
	}

	private final class PreparedStatementProcessor_2 extends PreparedStatementProcessor {
		long startTime,endTime;
		int client_id, project_id;

		private PreparedStatementProcessor init(int client_id, int project_id, long startTime, long endTime) {
			this.endTime = endTime;
			this.startTime = startTime;
			this.client_id = client_id;
			this.project_id = project_id;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setInt(1, client_id);
			statement.setInt(2, project_id);
			statement.setLong(3, startTime);
			statement.setLong(4, endTime);
			statement.setLong(5, startTime);
		}
	}

	private final class ResultSetProcessor_2 extends ResultSetProcessor {
		long newIpCount = 0;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {

			while (resultSet.next()) {
				newIpCount = resultSet.getLong(1);
			}
		}
	}

	/*
	 * 查询独立IP总数（小时，天，月，年）
	 */
	public long getAllMinuteIpCount(int client_id, int project_id, long startTime,long endTime) {
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();

		executeQuery("SELECT  COUNT(DISTINCT c_ip) FROM stat_ip_minute WHERE client_id= ? AND project_id= ? AND time >= ? AND time < ?;",
				prepared_statement_processor_3.init(client_id, project_id, startTime,endTime), result_set_processor_3);
		return result_set_processor_3.allIpCount;
	}
	public long getAllHourIpCount(int client_id, int project_id, long startTime,long endTime) {
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		
		executeQuery("SELECT  COUNT(DISTINCT c_ip) FROM stat_ip_hour WHERE client_id= ? AND project_id= ? AND time >= ? AND time < ?;",
				prepared_statement_processor_3.init(client_id, project_id, startTime,endTime), result_set_processor_3);
		return result_set_processor_3.allIpCount;
	}

	public long getAllDailyIpCount(int client_id, int project_id, long startTime,long endTime) {
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();

		executeQuery(
				"SELECT  COUNT(DISTINCT c_ip) FROM stat_ip_daily WHERE client_id= ? AND project_id= ? AND time >= ? AND time < ?;",
				prepared_statement_processor_3.init(client_id, project_id, startTime,endTime), result_set_processor_3);
		return result_set_processor_3.allIpCount;
	}

	public long getAllMonthIpCount(int client_id, int project_id, long startTime,long endTime) {
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();

		executeQuery(
				"SELECT  COUNT(DISTINCT c_ip) FROM stat_ip_month WHERE client_id= ? AND project_id= ? AND time >= ? AND time < ?;",
				prepared_statement_processor_3.init(client_id, project_id, startTime,endTime), result_set_processor_3);
		return result_set_processor_3.allIpCount;
	}

	public long getAllYearIpCount(int client_id, int project_id, long startTime,long endTime) {
		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();

		executeQuery("SELECT  COUNT(DISTINCT c_ip) FROM stat_ip_year WHERE client_id= ? AND project_id= ? AND time >= ? AND time < ?;",
				prepared_statement_processor_3.init(client_id, project_id, startTime,endTime), result_set_processor_3);
		return result_set_processor_3.allIpCount;
	}

	private final class PreparedStatementProcessor_3 extends PreparedStatementProcessor {
		long startTime,endTime;
		int client_id, project_id;

		private PreparedStatementProcessor init(int client_id, int project_id, long startTime, long endTime) {
			this.endTime = endTime;
			this.startTime = startTime;
			this.client_id = client_id;
			this.project_id = project_id;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setInt(1, client_id);
			statement.setInt(2, project_id);
			statement.setLong(3, startTime);
			statement.setLong(4, endTime);
		}
	}

	private final class ResultSetProcessor_3 extends ResultSetProcessor {
		long allIpCount = 0;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {

			while (resultSet.next()) {
				allIpCount = resultSet.getLong(1);
			}
		}
	}
	/*
	 * 获取urlTop50
	 */
	
	public List<TimeSlot>  getUrlMinutePageviewCount(long startTime, long endTime, int client_id,
			int project_id) {

			ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
			PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();

			executeQuery(
					" SELECT url,sum(counts) as  counts FROM stat_pv_minute WHERE time >= ? AND time < ? "
							+ "AND client_id = ? AND project_id = ? group by url ORDER BY counts desc limit 50;",
					prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
					result_set_processor_4);

			return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getUrlHourPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				" SELECT url,sum(counts) as  counts FROM stat_pv_hour WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id = ? group by url ORDER BY counts desc limit 50;",
				prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
				result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getUrlDailyPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				" SELECT url,sum(counts) as  counts FROM stat_pv_daily WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id = ? group by url ORDER BY counts desc limit 50;",
				prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
				result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getUrlMonthPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				" SELECT url,sum(counts) as  counts FROM stat_pv_month WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id = ? group by url ORDER BY counts desc limit 50;",
				prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
				result_set_processor_4);
		
		return result_set_processor_4.tsList;
	}
	public List<TimeSlot>  getUrlYearPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_4 result_set_processor_4 = new ResultSetProcessor_4();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				" SELECT url,sum(counts) as  counts FROM stat_pv_year WHERE time >= ? AND time < ? "
						+ "AND client_id = ? AND project_id = ? group by url ORDER BY counts desc limit 50;",
				prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
				result_set_processor_4);
		
		return result_set_processor_4.tsList;
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

	private final class ResultSetProcessor_4 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setUrl(resultSet.getString(1));
				timeSlot.setUrlNum(resultSet.getInt(2));
				tsList.add(timeSlot);
			}
		}
	}
	
	
	/*
	 * 获取上一段时间的PV统计
	 */
	public long getLastPvNumMinute(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();

		executeQuery(
				"SELECT  sum(counts) FROM stat_pv_minute WHERE time >= ? AND time < ? AND client_id=? AND project_id= ?;",
				prepared_statement_processor_5.init(client_id, project_id, startTime,endTime), result_set_processor_5);
		return result_set_processor_5.lastPvNum;
	}
	public long getLastPvNumHour(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT  sum(counts) FROM stat_pv_hour WHERE time >= ? AND time < ? AND client_id=? AND project_id= ?;",
				prepared_statement_processor_5.init(client_id, project_id, startTime,endTime), result_set_processor_5);
		return result_set_processor_5.lastPvNum;
	}
	public long getLastPvNumDaily(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT  sum(counts) FROM stat_pv_daily WHERE time >= ? AND time < ? AND client_id=? AND project_id= ?;",
				prepared_statement_processor_5.init(client_id, project_id, startTime,endTime), result_set_processor_5);
		return result_set_processor_5.lastPvNum;
	}
	public long getLastPvNumMonth(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT  sum(counts) FROM stat_pv_month WHERE time >= ? AND time < ? AND client_id=? AND project_id= ?;",
				prepared_statement_processor_5.init(client_id, project_id, startTime,endTime), result_set_processor_5);
		return result_set_processor_5.lastPvNum;
	}
	public long getLastPvNumYear(long startTime, long endTime, int client_id,int project_id) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT  sum(counts) FROM stat_pv_year WHERE time >= ? AND time < ? AND client_id=? AND project_id= ?;",
				prepared_statement_processor_5.init(client_id, project_id, startTime,endTime), result_set_processor_5);
		return result_set_processor_5.lastPvNum;
	}
	
	private final class PreparedStatementProcessor_5 extends PreparedStatementProcessor {
		long startTime,endTime;
		int client_id, project_id;

		private PreparedStatementProcessor init(int client_id, int project_id, long startTime, long endTime) {
			this.endTime = endTime;
			this.startTime = startTime;
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
		long lastPvNum = 0;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {

			while (resultSet.next()) {
				lastPvNum = resultSet.getLong(1);
			}
		}
	}
	
	
	private final class PreparedStatementProcessor_6 extends PreparedStatementProcessor {
		long startTime,endTime;
		int client_id, project_id;
		
		private PreparedStatementProcessor init(int client_id, int project_id, long startTime, long endTime) {
			this.endTime = endTime;
			this.startTime = startTime;
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
		long lastPvNum = 0;
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			
			while (resultSet.next()) {
				lastPvNum = resultSet.getLong(1);
			}
		}
	}
	
	
	public List<TimeSlot>  getIpMinutePageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_7 result_set_processor_7 = new ResultSetProcessor_7();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				" SELECT c_ip, sum(counts) as ipCount FROM stat_ip_minute  WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id = ? group by c_ip ORDER BY ipCount desc limit 50;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_7);
		
		return result_set_processor_7.tsList;
	}
	public List<TimeSlot>  getIpHourPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_7 result_set_processor_7 = new ResultSetProcessor_7();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				" SELECT c_ip, sum(counts) as ipCount FROM stat_ip_hour  WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id = ? group by c_ip ORDER BY ipCount desc limit 50;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_7);
		
		return result_set_processor_7.tsList;
		
	}
	public List<TimeSlot>  getIpDailyPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_7 result_set_processor_7 = new ResultSetProcessor_7();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				" SELECT c_ip, sum(counts) as ipCount FROM stat_ip_daily  WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id = ? group by c_ip ORDER BY ipCount desc limit 50;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_7);
		
		return result_set_processor_7.tsList;
	}
	public List<TimeSlot>  getIpMonthPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_7 result_set_processor_7 = new ResultSetProcessor_7();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				" SELECT c_ip, sum(counts) as ipCount FROM stat_ip_month  WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id = ? group by c_ip ORDER BY ipCount desc limit 50;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_7);
		
		return result_set_processor_7.tsList;
	}
	public List<TimeSlot>  getIpYearPageviewCount(long startTime, long endTime, int client_id,
			int project_id) {
		
		ResultSetProcessor_7 result_set_processor_7 = new ResultSetProcessor_7();
		PreparedStatementProcessor_4 prepared_statement_processor_4 = new PreparedStatementProcessor_4();
		
		executeQuery(
				" SELECT c_ip, sum(counts) as ipCount FROM stat_ip_year  WHERE time >= ? AND time < ? "
				+ "AND client_id = ? AND project_id = ? group by c_ip ORDER BY ipCount desc limit 50;",
						prepared_statement_processor_4.init(startTime, endTime, client_id, project_id),
						result_set_processor_7);
		
		return result_set_processor_7.tsList;
	}
	

	private final class ResultSetProcessor_7 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setIp(resultSet.getString(1));
				timeSlot.setIpNum(resultSet.getInt(2));
				tsList.add(timeSlot);
			}
		}
	}
	
	/*
	 * 查找每个url在各个时间点的页面访问次数
	 */
	public List<TimeSlot>  getPageviewByUrlMinute(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_8 result_set_processor_8 = new ResultSetProcessor_8();
		PreparedStatementProcessor_8 prepared_statement_processor_8 = new PreparedStatementProcessor_8();
		
		executeQuery(
				" SELECT time,counts FROM stat_pv_Minute  WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? "
				+ "AND url = ? order by time;",
						prepared_statement_processor_8.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_8);
		
		return result_set_processor_8.tsList;
		
	}
	public List<TimeSlot>  getPageviewByUrlHour(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_8 result_set_processor_8 = new ResultSetProcessor_8();
		PreparedStatementProcessor_8 prepared_statement_processor_8 = new PreparedStatementProcessor_8();
		
		executeQuery(
				" SELECT time,counts FROM stat_pv_hour  WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? "
						+ "AND url = ? order by time;",
						prepared_statement_processor_8.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_8);
		
		return result_set_processor_8.tsList;
		
	}
	public List<TimeSlot>  getPageviewByUrlDaily(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_8 result_set_processor_8 = new ResultSetProcessor_8();
		PreparedStatementProcessor_8 prepared_statement_processor_8 = new PreparedStatementProcessor_8();
		
		executeQuery(
				" SELECT time,counts FROM stat_pv_daily  WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? "
						+ "AND url = ? order by time;",
						prepared_statement_processor_8.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_8);
		
		return result_set_processor_8.tsList;
		
	}
	public List<TimeSlot>  getPageviewByUrlMonth(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_8 result_set_processor_8 = new ResultSetProcessor_8();
		PreparedStatementProcessor_8 prepared_statement_processor_8 = new PreparedStatementProcessor_8();
		
		executeQuery(
				" SELECT time,counts FROM stat_pv_month  WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? "
						+ "AND url = ? order by time;",
						prepared_statement_processor_8.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_8);
		
		return result_set_processor_8.tsList;
		
	}
	public List<TimeSlot>  getPageviewByUrlYear(long startTime, long endTime, int client_id,int project_id,String url) {
		
		ResultSetProcessor_8 result_set_processor_8 = new ResultSetProcessor_8();
		PreparedStatementProcessor_8 prepared_statement_processor_8 = new PreparedStatementProcessor_8();
		
		executeQuery(
				" SELECT time,counts FROM stat_pv_year  WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? "
						+ "AND url = ? order by time;",
						prepared_statement_processor_8.init(startTime, endTime, client_id, project_id,url),
						result_set_processor_8);
		
		return result_set_processor_8.tsList;
		
	}
	private final class PreparedStatementProcessor_8 extends PreparedStatementProcessor {
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

	private final class ResultSetProcessor_8 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setTime(resultSet.getLong(1));
				timeSlot.setUrlNum(resultSet.getLong(2));
				tsList.add(timeSlot);
			}
		}
	}
	
	
	/*
	 * 查找每个url在各个时间点的页面访问次数
	 */
	public List<TimeSlot>  getPageviewByIpMinute(long startTime, long endTime, int client_id,int project_id,String ip) {
		
		ResultSetProcessor_9 result_set_processor_9 = new ResultSetProcessor_9();
		PreparedStatementProcessor_9 prepared_statement_processor_9 = new PreparedStatementProcessor_9();
		
		executeQuery(
				" SELECT time,counts FROM stat_ip_minute  WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? "
				+ "AND c_ip = ? order by time;",
						prepared_statement_processor_9.init(startTime, endTime, client_id, project_id,ip),
						result_set_processor_9);
		
		return result_set_processor_9.tsList;
		
	}
	public List<TimeSlot>  getPageviewByIpHour(long startTime, long endTime, int client_id,int project_id,String ip) {
		
		ResultSetProcessor_9 result_set_processor_9 = new ResultSetProcessor_9();
		PreparedStatementProcessor_9 prepared_statement_processor_9 = new PreparedStatementProcessor_9();
		
		executeQuery(
				" SELECT time,counts FROM stat_ip_hour  WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? "
						+ "AND c_ip = ? order by time;",
						prepared_statement_processor_9.init(startTime, endTime, client_id, project_id,ip),
						result_set_processor_9);
		
		return result_set_processor_9.tsList;
		
	}
	public List<TimeSlot>  getPageviewByIpDaily(long startTime, long endTime, int client_id,int project_id,String ip) {
		
		ResultSetProcessor_9 result_set_processor_9 = new ResultSetProcessor_9();
		PreparedStatementProcessor_9 prepared_statement_processor_9 = new PreparedStatementProcessor_9();
		
		executeQuery(
				" SELECT time,counts FROM stat_ip_daily  WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? "
						+ "AND c_ip = ? order by time;",
						prepared_statement_processor_9.init(startTime, endTime, client_id, project_id,ip),
						result_set_processor_9);
		
		return result_set_processor_9.tsList;
		
	}
	public List<TimeSlot>  getPageviewByIpMonth(long startTime, long endTime, int client_id,int project_id,String ip) {
		
		ResultSetProcessor_9 result_set_processor_9 = new ResultSetProcessor_9();
		PreparedStatementProcessor_9 prepared_statement_processor_9 = new PreparedStatementProcessor_9();
		
		executeQuery(
				" SELECT time,counts FROM stat_ip_month  WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? "
						+ "AND c_ip = ? order by time;",
						prepared_statement_processor_9.init(startTime, endTime, client_id, project_id,ip),
						result_set_processor_9);
		
		return result_set_processor_9.tsList;
		
	}
	public List<TimeSlot>  getPageviewByIpYear(long startTime, long endTime, int client_id,int project_id,String ip) {
		
		ResultSetProcessor_9 result_set_processor_9 = new ResultSetProcessor_9();
		PreparedStatementProcessor_9 prepared_statement_processor_9 = new PreparedStatementProcessor_9();
		
		executeQuery(
				" SELECT time,counts FROM stat_ip_year  WHERE time >= ? AND time < ? AND client_id = ? AND project_id = ? "
						+ "AND c_ip = ? order by time;",
						prepared_statement_processor_9.init(startTime, endTime, client_id, project_id,ip),
						result_set_processor_9);
		
		return result_set_processor_9.tsList;
		
	}
	private final class PreparedStatementProcessor_9 extends PreparedStatementProcessor {
		long startTime, endTime;
		int client_id, project_id;
		String ip;
		
		private PreparedStatementProcessor init(long startTime, long endTime, int client_id, int project_id,String ip) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.client_id = client_id;
			this.project_id = project_id;
			this.ip=ip;
			return this;
		}
		
		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, startTime);
			statement.setLong(2, endTime);
			statement.setInt(3, client_id);
			statement.setInt(4, project_id);
			statement.setString(5, ip);
		}
	}
	
	private final class ResultSetProcessor_9 extends ResultSetProcessor {
		List<TimeSlot> tsList=new ArrayList<TimeSlot>();
		
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			TimeSlot timeSlot = null;
			while (resultSet.next()) {
				timeSlot =  new TimeSlot();
				timeSlot.setTime(resultSet.getLong(1));
				timeSlot.setIpNum(resultSet.getLong(2));
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