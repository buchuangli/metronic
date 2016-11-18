package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.open01.logs.auth.User;

public class OptionsDatabase extends Open01Database {

	public OptionsDatabase() {

		super();

	}

	
	public User updateUser(User user) {
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		executeUpdate("update user set phone = ?,sectionName= ?,email= ?,image= ? where user_id = ?;",
				prepared_statement_processor_1.init(user));
		return result_set_processor_1.user;
	}
	
	private final class PreparedStatementProcessor_1 extends PreparedStatementProcessor {
		User user;

		private PreparedStatementProcessor init(User user) {
			this.user = user;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setString(1, user.getPhone());
			statement.setString(2, user.getSectionName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getImage());
			statement.setInt(5, user.getUser_id());
		}
	}

	private final class ResultSetProcessor_1 extends ResultSetProcessor {
		private User user = null;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				user = new User();
				user.setName(resultSet.getString("name"));
				user.setPhone(resultSet.getString("phone"));
				user.setSectionName(resultSet.getString("sectionName"));
				user.setEmail(resultSet.getString("email"));
				user.setImage(resultSet.getString("image"));
			}
		}
	}
	
	
public User insertUser(User user) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeUpdate(
				"INSERT INTO USER (client_id, user_token,NAME,PASSWORD,phone,sectionName,email) VALUES(?,?,?,?,?,?,?);",
				prepared_statement_processor_1.init(user));
		
		return result_set_processor_1.user;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public User updatePass(User u) {
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		executeUpdate("update user set password ='" + u.getPassword() + "'where user_id  = " + u.getUser_id() + ";",
				result_set_processor_2);
		return result_set_processor_2.user;
	}

	private final class ResultSetProcessor_2 extends ResultSetProcessor {
		private User user = null;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				user = new User();
				user.setPassword(resultSet.getString("password"));
			}
		}
	}

}
