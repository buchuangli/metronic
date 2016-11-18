package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.open01.logs.auth.User;

public class UserDatabase extends Open01Database {

	public UserDatabase() {

		super();

	}

	public User insertUser(User user) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		PreparedStatementProcessor_1 prepared_statement_processor_1 = new PreparedStatementProcessor_1();
		
		executeUpdate(
				"INSERT INTO USER (client_id, user_token,NAME,PASSWORD,phone,sectionName,email,company,limit_data_size) VALUES(?,?,?,?,?,?,?,?,?);",
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
			statement.setInt(1, user.getClient_id());
			statement.setString(2, user.getUser_token());
			statement.setString(3, user.getName());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getPhone());
			statement.setString(6, user.getSectionName());
			statement.setString(7, user.getEmail());
			statement.setString(8, user.getCompany());
			statement.setInt(9, user.getLimit_data_size());
		}
	}

	private final class ResultSetProcessor_1 extends ResultSetProcessor {
		User user= new User();
		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				user.setClient_id(resultSet.getInt(1));
				user.setUser_token(resultSet.getString(2));
				user.setName(resultSet.getString(3));
				user.setPassword(resultSet.getString(4));
				user.setPhone(resultSet.getString(5));
				user.setSectionName(resultSet.getString(6));
				user.setEmail(resultSet.getString(7));
				user.setCompany(resultSet.getString(8));
				user.setLimit_data_size(resultSet.getInt(9));
			}
		}
	}
	
	
    public User selectUserName(String user_name) {
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT client_id, user_token,NAME,PASSWORD,phone,sectionName,email,company,limit_data_size FROM USER  WHERE  NAME= ?;",
				prepared_statement_processor_2.init(user_name),result_set_processor_2);
		
		return result_set_processor_2.user;
	}
	
	private final class PreparedStatementProcessor_2 extends PreparedStatementProcessor {
		
		String user_name;

		private PreparedStatementProcessor init(String user_name) {
			this.user_name = user_name;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setString(1, user_name);
		}
	}

	private final class ResultSetProcessor_2 extends ResultSetProcessor {
		User user= new User();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				user.setClient_id(resultSet.getInt(1));
				user.setUser_token(resultSet.getString(2));
				user.setName(resultSet.getString(3));
				user.setPassword(resultSet.getString(4));
				user.setPhone(resultSet.getString(5));
				user.setSectionName(resultSet.getString(6));
				user.setEmail(resultSet.getString(7));
				user.setCompany(resultSet.getString(8));
				user.setLimit_data_size(resultSet.getInt(9));
			}
		}
	}
	
	public void updatePassworByUserNameAndMail(String user_name, String mail) {
		executeUpdate("UPDATE USER SET PASSWORD = '111111'  WHERE NAME = '"+user_name+"' AND email = '"+mail+"';");
		
	}
	
	public User selectUserName(String user_name, String mail) {
		
		ResultSetProcessor_5 result_set_processor_5 = new ResultSetProcessor_5();
		PreparedStatementProcessor_5 prepared_statement_processor_5 = new PreparedStatementProcessor_5();
		
		executeQuery(
				"SELECT client_id, user_token,NAME,PASSWORD,phone,sectionName,email FROM USER  WHERE NAME =? AND EMAIL= ?;",
				prepared_statement_processor_5.init(user_name, mail),result_set_processor_5);
		
		return result_set_processor_5.user;
	}
	
	private final class ResultSetProcessor_5 extends ResultSetProcessor {
		User user= new User();

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				user.setClient_id(resultSet.getInt(1));
				user.setUser_token(resultSet.getString(2));
				user.setName(resultSet.getString(3));
				user.setPassword(resultSet.getString(4));
				user.setPhone(resultSet.getString(5));
				user.setSectionName(resultSet.getString(6));
				user.setEmail(resultSet.getString(7));
			}
		}
	}
	
	private final class PreparedStatementProcessor_5 extends PreparedStatementProcessor {
		String user_name;
		String mail;

		private PreparedStatementProcessor init(String user_name,String mail) {
			this.user_name = user_name;
			this.mail = mail;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setString(1, user_name);
			statement.setString(2, mail);
		}
	}

	
	public int selectUserIdByName(String user_name) {
		
		ResultSetProcessor_6 result_set_processor_6 = new ResultSetProcessor_6();
		PreparedStatementProcessor_6 prepared_statement_processor_6 = new PreparedStatementProcessor_6();
		
		executeQuery(
				"SELECT user_id FROM  USER WHERE NAME = ?;",
				prepared_statement_processor_6.init(user_name),result_set_processor_6);
		
		return result_set_processor_6.id;
	}
	
	private final class ResultSetProcessor_6 extends ResultSetProcessor {
		int id=0;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				id = resultSet.getInt(1);
			}
		}
	}
	
	private final class PreparedStatementProcessor_6 extends PreparedStatementProcessor {
		String user_name;

		private PreparedStatementProcessor init(String user_name) {
			this.user_name = user_name;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setString(1, user_name);
		}
	}
	
	public void updateClientIdByUserId(int id) {
		executeUpdate("UPDATE USER SET client_id = "+id+" WHERE user_id = "+id+";");
	}

}
