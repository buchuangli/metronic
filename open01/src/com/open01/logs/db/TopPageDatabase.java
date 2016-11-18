package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.open01.logs.model.TopPage;

public class TopPageDatabase extends Open01Database {

	public TopPageDatabase() {

		super();

	}
	/*
	 * 添加到首页
	 */
	public void  addTopPageInfo(TopPage topPage) {
		
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeUpdate(
				"INSERT INTO `stat_top_page`(`client_id`, `project_id`, `reportType`) VALUES (?,?,?)",
				prepared_statement_processor_1.init(topPage));
		
	}
	
	
	private final class PreparedStatementProcessor_1 extends PreparedStatementProcessor {
		TopPage topPage;

		private PreparedStatementProcessor init(TopPage topPage) {
			this.topPage = topPage;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setInt(1, topPage.getClient_id());
			statement.setInt(2, topPage.getProject_id());
			statement.setString(3, topPage.getReportType());
		}
	}
	/*
	 * 从首页删除首页
	 */
	public void  deleteTopPageInfo(TopPage topPage) {
		
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		//executeUpdate("TRUNCATE table stat_top_page;");
		executeUpdate(
				"delete from stat_top_page where client_id = ? and project_id = ?;",
				prepared_statement_processor_2.init(topPage));
		
	}
	private final class PreparedStatementProcessor_2 extends PreparedStatementProcessor {
		TopPage topPage;

		private PreparedStatementProcessor init(TopPage topPage) {
			this.topPage = topPage;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setInt(1, topPage.getClient_id());
			statement.setInt(2, topPage.getProject_id());
		}
	}
	/*
	 * 查找首页图表信息
	 */
	public String selectTopPageInfo(long client_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_3 prepared_statement_processor_3 = new PreparedStatementProcessor_3();
		executeQuery("Select reportType from stat_top_page where client_id = ?; ",prepared_statement_processor_3.init(client_id),
				result_set_processor_2);
		return result_set_processor_2.reportType;
	}
	
	private final class ResultSetProcessor_2 extends ResultSetProcessor {
		String reportType = "";
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				reportType = resultSet.getString(1);
			
			}
		}
	}
	private final class PreparedStatementProcessor_3 extends PreparedStatementProcessor {
		long client_id;

		private PreparedStatementProcessor init(long client_id) {
			this.client_id = client_id;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setLong(1, client_id);
		}
	}

}
