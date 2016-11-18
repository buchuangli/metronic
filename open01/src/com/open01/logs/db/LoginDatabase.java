package com.open01.logs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.open01.logs.auth.User;

public class LoginDatabase extends Open01Database {

	public LoginDatabase() {

		super();

	}

	public User selectUser(String name) {

		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();


		executeQuery("SELECT user_id,client_id,name, password, phone, sitename,email,image,sectionName,user_token,company FROM user where name ='" +

				name + "';", result_set_processor_1);

		return result_set_processor_1.user;
	}
	public User selectByUserId(int user_id) {
		
		ResultSetProcessor_1 result_set_processor_1 = new ResultSetProcessor_1();
		
		executeQuery("SELECT user_id,client_id,name, password, phone, sitename,email,image,sectionName ,user_token ,company FROM user where user_id =" +
				
		user_id + ";", result_set_processor_1);
		
		return result_set_processor_1.user;
	}

	private final class ResultSetProcessor_1 extends ResultSetProcessor {

		private User user = null;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {

			while (resultSet.next()) {
				user = new User();
				user.setUser_id(resultSet.getInt("user_id"));
				user.setClient_id(resultSet.getInt("client_id"));
				user.setName(resultSet.getString("name"));
				user.setPassword(resultSet.getString("password"));
				user.setPhone(resultSet.getString("phone"));
				user.setSitename(resultSet.getString("sitename"));
				user.setEmail(resultSet.getString("email"));
				user.setImage(resultSet.getString("image"));
				user.setSectionName(resultSet.getString("sectionName"));
				user.setUser_token(resultSet.getString("user_token"));
				user.setCompany(resultSet.getString("company"));
			}

		}
	}
	
	
	public int getLastProject(int user_id) {
		
		ResultSetProcessor_2 result_set_processor_2 = new ResultSetProcessor_2();
		PreparedStatementProcessor_2 prepared_statement_processor_2 = new PreparedStatementProcessor_2();
		
		executeQuery(
				"SELECT last_project FROM USER WHERE user_id = ?;",prepared_statement_processor_2.init(user_id),
				result_set_processor_2);
		
		return result_set_processor_2.pId;
	}
	private final class PreparedStatementProcessor_2 extends PreparedStatementProcessor {
		int user_id;

		private PreparedStatementProcessor init(int user_id) {
			this.user_id = user_id;
			return this;
		}

		@Override
		protected void process(PreparedStatement statement) throws SQLException {
			statement.setInt(1, user_id);
		}
	}

	private final class ResultSetProcessor_2 extends ResultSetProcessor {
		int pId=0;

		@Override
		protected void process(ResultSet resultSet) throws SQLException {
			while (resultSet.next()) {
				pId=resultSet.getInt(1);
			}
		}
	}
	
	
	public void updateLastPeoject(int user_id,int project_id) {
		executeUpdate("UPDATE  USER SET last_project = "+project_id+" WHERE user_id = "+user_id+";");
		
	}


}
