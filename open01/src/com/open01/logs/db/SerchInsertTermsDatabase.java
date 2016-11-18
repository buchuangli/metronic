package com.open01.logs.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.open01.logs.model.SerchTerms;

public class SerchInsertTermsDatabase extends Open01Database {
	public SerchInsertTermsDatabase() {
		super();

	}
//报存搜索过得值如果有次数+1没有为１
	public void insertTerms(String termsvalue,String use_id) {
		int userid = Integer.parseInt(use_id);
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();

		executeQuery("SELECT * FROM stat_serch_terms where terms_name ='"+termsvalue+"' and user_id='"+userid+"';", result_set_processor_1);
		SerchTerms serchTerm = result_set_processor_1.serchTerm;
			if (serchTerm.getTerms_count()>0) {
				int count = serchTerm.getTerms_count() + 1;
				executeUpdate("UPDATE stat_serch_terms SET terms_count = " + count + " WHERE terms_id = "+ serchTerm.getTerms_id() + " and user_id='"+serchTerm.getUser_id()+"';");
			} else {
				executeUpdate("insert into stat_serch_terms(terms_name,terms_count,user_id) values('" + termsvalue + "'," + 1 + ",'"+userid+"')");
			}
	}

	private final class ResultSetProcessor_1 extends ResultSetProcessor {

		private SerchTerms serchTerm = new SerchTerms();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {

			while (resultSet.next()) {
				serchTerm.setTerms_id(resultSet.getInt("terms_id"));
				serchTerm.setTerms_name(resultSet.getString("terms_name"));
				serchTerm.setTerms_count(resultSet.getInt("terms_count"));
				serchTerm.setUser_id(resultSet.getInt("user_id"));
			}

		}
	}
  //热搜词统计最多的次数
	public List<SerchTerms> queryTerms(String indexval) {
		int userid = Integer.parseInt(indexval);
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		executeQuery("SELECT terms_id,terms_count,terms_name,user_id FROM stat_serch_terms where user_id='"+userid+"' ORDER BY terms_count DESC LIMIT 6",
				result_set_processor_2);
		return result_set_processor_2.serchTerms;
	}

	private final class ResultSetProcessor_2 extends ResultSetProcessor {

		private List<SerchTerms> serchTerms = null;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			serchTerms = new ArrayList<SerchTerms>();
			while (resultSet.next()) {
				SerchTerms serchTerm = new SerchTerms();
				serchTerm.setTerms_id(resultSet.getInt("terms_id"));
				serchTerm.setTerms_name(resultSet.getString("terms_name"));
				serchTerm.setTerms_count(resultSet.getInt("terms_count"));
				serchTerm.setUser_id(resultSet.getInt("user_id"));
				serchTerms.add(serchTerm);
			}

		}
	}
	//搜索自动提示(暂未开发)
	public List<SerchTerms> querylist() {

		ResultSetProcessor_3 result_set_processor_3 = new ResultSetProcessor_3();

		executeQuery("SELECT * FROM stat_serch_terms",
				result_set_processor_3);
		return result_set_processor_3.serchTerms;
	}

	private final class ResultSetProcessor_3 extends ResultSetProcessor {

		private List<SerchTerms> serchTerms = null;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			serchTerms = new ArrayList<SerchTerms>();
			while (resultSet.next()) {
				SerchTerms serchTerm = new SerchTerms();
				serchTerm.setTerms_id(resultSet.getInt("terms_id"));
				serchTerm.setTerms_name(resultSet.getString("terms_name"));
				serchTerm.setTerms_count(resultSet.getInt("terms_count"));
				serchTerms.add(serchTerm);
			}

		}
	}
}