package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.open01.logs.model.IpGeography;

public class IpGeographyDatabase extends Open01Database {

	public IpGeographyDatabase() {

		super();

	}

	public List<IpGeography>  getDailyStatus(String country) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeQuery(
				"SELECT distinct province from stat_ip_geography where country = ?;",
				prepared_statement_processor_1.init(country),
				result_set_processor_1);
		
		return result_set_processor_1.pList;
	}
	
	private final class PreparedStatementProcessor_1 extends PreparedStatementProcessor {
		String country;

		private PreparedStatementProcessor init(String country) {
			this.country=country;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setString(1, country);
		}
	}

	private final class ResultSetProcessor_1 extends ResultSetProcessor {
		List<IpGeography> pList=new ArrayList<IpGeography>();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			IpGeography ipGeography = null;
			while (resultSet.next()) {
				ipGeography =  new IpGeography();
				ipGeography.setProvince(resultSet.getString(1));
				pList.add(ipGeography);
			}
		}
	}
	
	
	
}
